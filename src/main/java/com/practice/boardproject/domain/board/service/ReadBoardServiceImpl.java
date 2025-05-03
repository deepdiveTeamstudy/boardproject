package com.practice.boardproject.domain.board.service;

import com.practice.boardproject.domain.board.dto.request.SearchBoardRequest;
import com.practice.boardproject.domain.board.dto.response.DetailBoardResponse;
import com.practice.boardproject.domain.board.dto.response.ListBoardDto;
import com.practice.boardproject.domain.board.dto.response.ListBoardResponse;
import com.practice.boardproject.domain.board.repository.DetailBoardRepository;
import com.practice.boardproject.domain.board.repository.ListBoardRepository;
import com.practice.boardproject.global.api_response.exception.GlobalException;
import com.practice.boardproject.global.api_response.status.ErrorStatus;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReadBoardServiceImpl implements ReadBoardService {

  private final ListBoardRepository listBoardRepository;
  private final DetailBoardRepository detailBoardRepository;

  @Override
  public ListBoardResponse getBoardList(SearchBoardRequest request) {
    Page<ListBoardDto> boardList = listBoardRepository.getBoardList(request);

    return ListBoardResponse.builder()
      .boardList(boardList.getContent())
      .totalCount(boardList.getTotalElements())
      .build();
  }

  @Override
  public DetailBoardResponse getBoard(Long boardId) {
    return Optional.ofNullable(detailBoardRepository.getBoard(boardId))
      .orElseThrow(() -> new GlobalException(ErrorStatus.NOT_FOUND, "존재하지 않는 게시글입니다."));
  }
}
