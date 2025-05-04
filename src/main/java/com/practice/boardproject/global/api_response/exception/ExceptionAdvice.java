package com.practice.boardproject.global.api_response.exception;

import com.practice.boardproject.global.api_response.ApiResponse;
import com.practice.boardproject.global.api_response.dto.ApiDto;
import com.practice.boardproject.global.api_response.status.ErrorStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice(annotations = {RestController.class})
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

  @ExceptionHandler
  public ResponseEntity<Object> validation(ConstraintViolationException e, WebRequest request) {
    String errorMessage = e.getConstraintViolations().stream()
      .map(ConstraintViolation::getMessage)
      .findFirst()
      .orElseThrow(() -> new RuntimeException("ConstraintViolationException 추출 도중 에러 발생"));

    return handleExceptionInternalConstraint(e, ErrorStatus.valueOf(errorMessage), request);
  }

  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatusCode status,
    WebRequest request) {

    Map<String, String> errors = new LinkedHashMap<>();

    e.getBindingResult().getFieldErrors()
      .forEach(fieldError -> {
        String fieldName = fieldError.getField();
        String errorMessage = Optional.ofNullable(fieldError.getDefaultMessage()).orElse("");
        errors.merge(fieldName, errorMessage, (existingErrorMessage, newErrorMessage) -> existingErrorMessage + ", " + newErrorMessage);
      });

    return handleExceptionInternalArgs(e, ErrorStatus.BAD_REQUEST, request, errors);
  }

  @ExceptionHandler(value = GlobalException.class)
  public ResponseEntity<Object> onThrowException(GlobalException globalException, HttpServletRequest request) {
    ApiDto errorReasonHttpStatus = globalException.getErrorReasonHttpStatus();
    return handleExceptionInternal(globalException, errorReasonHttpStatus, request);
  }

  private ResponseEntity<Object> handleExceptionInternal(Exception e, ApiDto reason,
    HttpServletRequest request) {

    ApiResponse<Object> body = ApiResponse.onFailure(reason.getCode(), reason.getMessage(), null);
    e.printStackTrace();

    WebRequest webRequest = new ServletWebRequest(request);
    return super.handleExceptionInternal(
      e,
      body,
      null,
      reason.getHttpStatus(),
      webRequest
    );
  }


  private ResponseEntity<Object> handleExceptionInternalArgs(Exception e, ErrorStatus errorCommonStatus,
    WebRequest request, Map<String, String> errorArgs) {
    ApiResponse<Object> body = ApiResponse.onFailure(errorCommonStatus.getCode(), errorCommonStatus.getMessage(), errorArgs);
    return super.handleExceptionInternal(
      e,
      body,
      HttpHeaders.EMPTY,
      errorCommonStatus.getHttpStatus(),
      request
    );
  }

  private ResponseEntity<Object> handleExceptionInternalConstraint(Exception e, ErrorStatus errorCommonStatus,
    WebRequest request) {
    ApiResponse<Object> body = ApiResponse.onFailure(errorCommonStatus.getCode(), errorCommonStatus.getMessage(), null);
    return super.handleExceptionInternal(
      e,
      body,
      HttpHeaders.EMPTY,
      errorCommonStatus.getHttpStatus(),
      request
    );
  }


}
