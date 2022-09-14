package com.ce.spring2.ws.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/ws")
public class WebsocketController {
	
	@GetMapping("/ws.do")
	public void ws() {}
}
