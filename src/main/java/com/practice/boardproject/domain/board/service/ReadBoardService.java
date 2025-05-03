package com.practice.boardproject.domain.board.service;

import com.practice.boardproject.domain.board.dto.request.SearchBoardRequest;
import com.practice.boardproject.domain.board.dto.response.DetailBoardResponse;
import com.practice.boardproject.domain.board.dto.response.ListBoardResponse;

public interface ReadBoardService {

  ListBoardResponse getBoardList(SearchBoardRequest request);

  DetailBoardResponse getBoard(Long boardId);
}
