package com.practice.boardproject.domain.board.service;

import com.practice.boardproject.domain.board.dto.request.CreateBoardRequest;
import com.practice.boardproject.domain.board.dto.request.DeleteBoardRequest;
import com.practice.boardproject.domain.board.dto.request.UpdateBoardRequest;

public interface CommandBoardService {

  void createBoard(CreateBoardRequest request);

  void updateBoard(long boardId, UpdateBoardRequest request);

  void deleteBoard(long boardId, DeleteBoardRequest request);
}
