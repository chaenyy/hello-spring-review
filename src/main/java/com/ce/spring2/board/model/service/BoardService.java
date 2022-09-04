package com.ce.spring2.board.model.service;

import java.util.List;
import java.util.Map;

import com.ce.spring2.board.model.dto.Attachment;
import com.ce.spring2.board.model.dto.Board;

public interface BoardService {

	List<Board> selectAll(Map<String, Integer> param);

	int getTotalContent();

	int insertBoard(Board board);

	Board selectOneBoard(int no);

	Attachment selectOneAttachment(int no);

	int deleteAttachment(int attachNo);

	int updateBoard(Board board);

}
