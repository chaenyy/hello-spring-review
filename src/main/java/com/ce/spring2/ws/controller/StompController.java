package com.ce.spring2.ws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import com.ce.spring2.chat.model.dto.ChatLog;
import com.ce.spring2.chat.model.service.ChatService;
import com.ce.spring2.ws.payload.Payload;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class StompController {
	@Autowired
	ChatService chatService;
	
	/**
	 * @MessageMapping
	 * - url은 prefix를 제외하고 작성
	 * - /app/a -> /a
	 * 
	 * @SendTo
	 * - prefix부터 모두 작성
	 * - simpleBroker에게 전달
	 */
	@MessageMapping("/a")
	@SendTo("/app/a")
	public String simpleMessage(String message) {
		log.debug("message = {}", message);
		return message;
	}
	
	/**
	 * 전체공지
	 * @RequestBody -> json을 자바 객체로 변환!
	 */
	@MessageMapping("/admin/notice")
	@SendTo("/app/notice")
	public Payload notice(@RequestBody Payload payload) {
		log.debug("payload = {}", payload);
		
		return payload;
	}
	
	@MessageMapping("/admin/notice/{memberId}")
	@SendTo("/app/notice/{memberId}")
	public Payload notice(@RequestBody Payload payload, @DestinationVariable String memberId) {
		log.debug("payload = {}", payload);
		log.debug("memberId = {}", memberId);
		
		return payload;
	}
	
	@MessageMapping("/chat/{chatroomId}")
	@SendTo({"/app/chat/{chatroomId}", "/app/admin/chatList"})
	public ChatLog chatLog(@RequestBody ChatLog chatlog) {
		log.debug("chatlog = {}", chatlog);
		int result = chatService.insertChatLog(chatlog);
		
		return chatlog;
	}
}
