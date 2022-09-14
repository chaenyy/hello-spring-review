package com.ce.spring2.ws.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class StompController {
	
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
}
