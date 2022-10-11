package com.ce.spring2.chat.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ce.spring2.chat.model.dto.ChatLog;
import com.ce.spring2.chat.model.dto.ChatMember;
import com.ce.spring2.chat.model.service.ChatService;
import com.ce.spring2.member.model.dto.Member;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/chat")
public class ChatController {
	@Autowired
	ChatService chatService;
	
	/**
	 * 1. 채팅방유무 조회
	 * 
	 * 2.a 처음 입장한 경우
	 * 		- 채팅방 아이디 생성
	 * 2.b 재입장인 경우
	 * 		- 기존 채팅방아이디
	 */
	@GetMapping("/chat.do")
	public void chat(Authentication authentication, Model model) {
		// 1. 채팅방 유무 조회
		Member loginMember = (Member)authentication.getPrincipal();
		ChatMember chatMember = chatService.findChatMemberByMemberId(loginMember.getMemberId());
		log.debug("chatMember = {}", chatMember);
		
		String chatroomId = null;
		if(chatMember == null) {
			// 처음 입장한 경우
			chatroomId = generateChatroomId();
			log.debug("chatroomId = {}", chatroomId);
			// chatmember insert 2행! (관리자/로그인회원)
			List<ChatMember> chatMembers = Arrays.asList(
						new ChatMember(chatroomId, loginMember.getMemberId()),
						new ChatMember(chatroomId, "admin") // ROLE로 admin을 구별하지만, 일단 'admin' 아이디로 고정!
					);
			chatService.insertChatMembers(chatMembers);
		} else {
			// 재입장한 경우
			chatroomId = chatMember.getChatroomId();
			List<ChatLog> chatLogs = chatService.findChatlogByChatroomId(chatroomId);
			log.debug("chatLogs = {}", chatLogs);
			model.addAttribute("chatLogs", chatLogs);
		}
		model.addAttribute("chatroomId", chatroomId);
	}

	private String generateChatroomId() {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		final int len = 8;
		
		for(int i = 0; i < len; i++) {
			if(random.nextBoolean()) {
				// 영문자
				if(random.nextBoolean()) {
					// 대문자
					sb.append((char)(random.nextInt(26) + 'A')); // A-Z
				} else {
					// 소문자
					sb.append((char)(random.nextInt(26) + 'a')); // a-z
				}
			} else {
				// 숫자
				sb.append(random.nextInt(10)); // 0 ~ 9
			}
		}
		return sb.toString();
	}
}
