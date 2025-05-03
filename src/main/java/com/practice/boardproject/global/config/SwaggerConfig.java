package com.practice.boardproject.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class SwaggerConfig implements WebMvcConfigurer {

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
      .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .build();
    converters.add(new ByteArrayHttpMessageConverter());
    converters.add(new MappingJackson2HttpMessageConverter(objectMapper));
  }

  @Bean
  public OpenAPI KoalaServerAPI() {
    Info info = new Info()
      .title("백수탈출 게시판 토이 프로젝트 API")
      .description("백수탈출 파이팅!")
      .version("v3");

    return new OpenAPI().info(info);
  }
}
