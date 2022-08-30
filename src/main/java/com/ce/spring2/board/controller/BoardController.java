package com.ce.spring2.board.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ce.spring2.board.model.dto.Attachment;
import com.ce.spring2.board.model.dto.Board;
import com.ce.spring2.board.model.service.BoardService;
import com.ce.spring2.common.HelloSpringUtils;

import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.proxy.annotation.Post;

@Controller
@Slf4j
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	// spring의 빈으로 등록 되어있음
	@Autowired
	ServletContext application;
	
	@RequestMapping("/boardList.do")
	public void boardList(@RequestParam(defaultValue = "1") int cPage, Model model, HttpServletRequest request) {
		// 1. content 영역
		Map<String, Integer> param = new HashMap<>();
		int limit = 10;
		param.put("cPage", cPage);
		param.put("limit", 10);
		
		List<Board> list = boardService.selectAll(param);
		log.debug("list = {}", list);
		model.addAttribute("list", list);
		
		// 2. pagebar 영역
		int totalContent = boardService.getTotalContent();
		String url = request.getRequestURI();
		String pagebar = HelloSpringUtils.getPagebar(cPage, limit, totalContent, url);
		log.debug(pagebar);
		model.addAttribute("pagebar", pagebar);
	}
	
	@GetMapping("/boardForm.do")
	public void boardForm() {}
	
	@PostMapping("/boardEnroll.do")
	public String boardEnroll(Board board, @RequestParam(name = "upFile") List<MultipartFile> upFileList, RedirectAttributes redirectAttr) throws IllegalStateException, IOException {
		for(MultipartFile upFile : upFileList) {
//			log.debug("upFile = {}", upFile);
//			log.debug("upFile#name = {}", upFile.getName());
//			log.debug("upFile#originalFilename = {}", upFile.getOriginalFilename());
//			log.debug("upFile#size = {}", upFile.getSize());
			
			// 파일이 없어도 빈 객체가 넘어오므로 분기처리!
			if(!upFile.isEmpty()) {				
				// a. 서버컴퓨터 저장
				String saveDirectory = application.getRealPath("/resources/upload/board");
				String renamedFilename = HelloSpringUtils.getRenamedFilename(upFile.getOriginalFilename()); // 20220830_141822222_123.txt
				File destFile = new File(saveDirectory, renamedFilename); // 해당 경로에 해당 이름을 가진 파일객체
				upFile.transferTo(destFile); // 해당 경로에 파일을 저장
				
				// b. DB저장을 위해 Attachment객체 생성
				Attachment attachment = new Attachment(upFile.getOriginalFilename(), renamedFilename);
				board.add(attachment);
			}
		}
		
		log.debug("board = {}", board);
		
		int result = boardService.insertBoard(board);
		redirectAttr.addFlashAttribute("msg", "게시글을 성공적으로 등록했습니다.");
		
		return "redirect:/board/boardList.do";
	}
}
