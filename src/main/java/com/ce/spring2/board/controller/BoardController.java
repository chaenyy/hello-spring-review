package com.ce.spring2.board.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ce.spring2.board.model.dto.Attachment;
import com.ce.spring2.board.model.dto.Board;
import com.ce.spring2.board.model.service.BoardService;
import com.ce.spring2.common.HelloSpringUtils;
import com.ce.spring2.ws.model.service.NoticeService;

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
	
	@Autowired
	ResourceLoader resourceLoader;
	
	@Autowired
	NoticeService noticeService;
	
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
	
	@GetMapping("/boardDetail.do")
	public void boardDetail(@RequestParam int no, Model model) {
		Board board = boardService.selectOneBoard(no);
		log.debug("board = {}", board);
		noticeService.sendNotice(board);
		model.addAttribute("board", board);
	}
	
	/**
	 * Resource 
	 * 다음 구현체들의 추상화레이어를 제공
	 * 
	 * 웹상 자원 : UrlResource
	 * classpath 자원 : ClassPathResource
	 * 서버컴퓨터 자원 : FileSystemResource
	 * ServletContext (web root) 자원 : SerlvetContextResource
	 * 입출력 자원 : InputStreamResource
	 * 이진데이터 자원 : ByteArrayResource  
	 * 
	 * file: -> 프로토콜 접두사를 사용하면 FileSystemResource 실행!!
	 * @throws IOException 
	 *  
	 * @ResponseBody
	 * - 핸들러의 반환된 자바객체를 응답메세지 바디에 직접 출력하는 경우
	 *  
	 */
	// produces : 핸들러가 어떤 것을 생성하는 지 명시해줌
	@GetMapping(path = "/fileDownload.do", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	// 이진데이터가 작성된 응답이 올 것을 기대하고 브라우저는 그것을 읽어서 본인 컴퓨터에 저장!!
	@ResponseBody
	public Resource fileDownload(@RequestParam int no, HttpServletResponse response) throws IOException {
		Attachment attach = boardService.selectOneAttachment(no);
		log.debug("attach = {}", attach);
		
		String saveDirectory = application.getRealPath("/resources/upload/board");
		File downFile = new File(saveDirectory, attach.getRenamedFilename());
		String location = "file:" + downFile;	// File#toString은 파일의 절대경로를 반환
		log.debug("location = {}", location);
		Resource resource = resourceLoader.getResource(location); // 해당 자원에 대한 실제 구현체를 만들어서 반환!
		log.debug("resource = {}", resource);
		log.debug("resource#file = {}", resource.getFile());
		
		// 응답헤더 작성
		response.setContentType("application/octet-stream; charset=utf-8"); // 이진데이터 임을 명시
		String filename = new String(attach.getOriginalFilename().getBytes("utf-8"), "iso-8859-1");
		response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename +"\"");
		
		return resource;
	}
	
	@GetMapping("/boardUpdate.do")
	public void boardUpdate(@RequestParam int no, Model model) {
		Board board = boardService.selectOneBoard(no);
		model.addAttribute("board", board);
	}
	
	/**
	 * - 게시글 수정
	 * - 첨부파일 삭제 (파일삭제 && attachment row 제거)
	 * - 첨부파일 추가
	 * @throws IOException 
	 * @throws IllegalStateException 
	 * 
	 */
	@PostMapping("/boardUpdate.do")
	public String boardUpdate(Board board, @RequestParam(name = "delFile", required = false) int[] delFiles, @RequestParam(name = "upFile") List<MultipartFile> upFileList, RedirectAttributes redirectAttr) throws IllegalStateException, IOException {
		String saveDirectory = application.getRealPath("/resources/upload/board");
		int result = 0;
		
		// 1. 첨부파일 삭제
		if(delFiles != null) {
			for(int attachNo : delFiles) {				
				// 서버에 저장된 파일 삭제
				Attachment attachment = boardService.selectOneAttachment(attachNo);
				File delFile = new File(saveDirectory, attachment.getRenamedFilename());
				boolean deleted = delFile.delete();
				
				// DB 레코드 삭제
//				if(deleted) {
					result = boardService.deleteAttachment(attachNo);
//				}
			}
		}
		
		// 2. 업로드 파일 등록
		for(MultipartFile upFile : upFileList) {
			if(!upFile.isEmpty()) {
				// 서버 컴퓨터 저장
				String renamedFilename = HelloSpringUtils.getRenamedFilename(upFile.getOriginalFilename());
				File destFile = new File(saveDirectory, renamedFilename);
				upFile.transferTo(destFile);
				
				// db 저장
				Attachment attach = new Attachment(upFile.getOriginalFilename(), renamedFilename);
				attach.setBoardNo(board.getNo());
				board.add(attach);
			}
		}
		
		// 3. 게시글 수정
		result = boardService.updateBoard(board);
		
		redirectAttr.addFlashAttribute("msg", "게시글이 수정되었습니다.");
		return "redirect:/board/boardDetail.do?no=" + board.getNo();
	}
}
