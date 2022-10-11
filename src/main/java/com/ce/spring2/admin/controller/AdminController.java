package com.ce.spring2.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ce.spring2.chat.model.dto.ChatLog;
import com.ce.spring2.chat.model.service.ChatService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {
	
	@Autowired
	ChatService chatService;
	
	@GetMapping("/memberList.do")
	public void memberList() {}
	
	@GetMapping("/chatList.do") 
	public void chatList(Model model) {
		// 채팅방 별 최근 1건 조회
		List<ChatLog> chatList = chatService.findRecentChatLogs();
		log.debug("chatList = {}", chatList);
		model.addAttribute("chatList", chatList);
	}
	
	@GetMapping("/chat.do")
	public void chat(@RequestParam String chatroomId, Model model) {
		List<ChatLog> chatLogs = chatService.findChatlogByChatroomId(chatroomId);
		log.debug("chatLogs = {}", chatLogs);
		model.addAttribute("chatLogs", chatLogs);
	}
}
