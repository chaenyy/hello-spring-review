package com.ce.spring2.board.model.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ce.spring2.board.model.dao.BoardDao;
import com.ce.spring2.board.model.dto.Attachment;
import com.ce.spring2.board.model.dto.Board;

import lombok.extern.slf4j.Slf4j;

// 기본값이 RuntimeException
@Transactional(rollbackFor = Exception.class)
@Service
@Slf4j
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
	
	@Override
	public int getTotalContent() {
		return boardDao.getTotalContent();
	}
	
	@Override
	public int insertBoard(Board board) {
		// insert board
		int result = boardDao.insertBoard(board);
		log.debug("board#no = ", board.getNo());
		
		// insert Attachment
		List<Attachment> attachments = board.getAttachments();
		if(!attachments.isEmpty()) {
			for(Attachment attach : attachments) {
				attach.setBoardNo(board.getNo());
				result = boardDao.insertAttachment(attach);
			}
		}
		return result;
	}
	
	@Override
	public Board selectOneBoard(int no) {
//		Board board = boardDao.selectOntBoard(no);
//		List<Attachment> attachments = boardDao.selectAttachmentByBoardNo(no);
//		board.setAttachments(attachments);
//		
//		return board;
		
		return boardDao.selectOneBoardCollection(no);
	}
	
	@Override
	public Attachment selectOneAttachment(int no) {
		return boardDao.selectOneAttachment(no);
	}
	
	@Override
	public int deleteAttachment(int attachNo) {
		return boardDao.deleteAttachment(attachNo);
	}
	
	@Override
	public int updateBoard(Board board) {
		// 게시글 수정
		int result = boardDao.updateBoard(board);
		
		// Attachment 추가
		List<Attachment> attachment = board.getAttachments();
		if(!attachment.isEmpty() && attachment != null) {
			for(Attachment attach : attachment) {
				result = boardDao.insertAttachment(attach);
			}
		}
		return result;
	}
}
