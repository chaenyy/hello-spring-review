package com.ce.spring2.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ce.spring2.board.model.dto.Board;
import com.ce.spring2.board.model.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("/boardList.do")
	public void boardList(@RequestParam(defaultValue = "1") int cPage, Model model) {
		Map<String, Integer> param = new HashMap<>();
		param.put("cPage", cPage);
		param.put("limit", 10);
		
		List<Board> list = boardService.selectAll(param);
		log.debug("list = {}", list);
		model.addAttribute("list", list);
	}
}
