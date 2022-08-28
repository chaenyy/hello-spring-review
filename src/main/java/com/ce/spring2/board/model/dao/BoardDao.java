package com.ce.spring2.board.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import com.ce.spring2.board.model.dto.Board;

@Mapper
public interface BoardDao {
	
	@Select("select b.*, (select count(*) from attachment where board_no = b.no) attach_count from board b order by no desc")
	List<Board> selectAll(RowBounds rowBounds);
	
}
