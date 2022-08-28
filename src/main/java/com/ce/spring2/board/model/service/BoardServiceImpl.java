package com.ce.spring2.board.model.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ce.spring2.board.model.dao.BoardDao;
import com.ce.spring2.board.model.dto.Board;

@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardDao boardDao;
	
	@Override
	public List<Board> selectAll(Map<String, Integer> param) {
		// mybatis에서 제공하는 페이징처리객체 RowBounds
		// offset, limit
		int limit = param.get("limit"); // 한 페이지에서 보여줄 개수
		int offset = (param.get("cPage") - 1) * limit; // 건너뛸 개수
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		return boardDao.selectAll(rowBounds);
	}
}
