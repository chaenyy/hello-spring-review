package com.ce.spring2.board.model.service;

import java.util.List;
import java.util.Map;

import com.ce.spring2.board.model.dto.Board;

public interface BoardService {

	List<Board> selectAll(Map<String, Integer> param);

}
