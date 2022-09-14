package com.ce.spring2.ws.controller;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EchoHandler extends TextWebSocketHandler{
	
	List<WebSocketSession> sessionList = new CopyOnWriteArrayList<>(); // 멀티쓰레딩 환경에서 사용하는 리스트(쓰기 작업용을 별도로 복사해서 만들어놓음 -> 동기화 성능저하를 방지해줌)
	
	/**
	 * @OnOpen
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessionList.add(session);
		log.debug("[add 현재 세션수 {}] {}", sessionList.size(), session.getId());
	}
	
	/**
	 * @OnClose
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessionList.remove(session);
		log.debug("[remove 현재 세션수 {}] {}", sessionList.size(), session.getId());
	}
	
	/**
	 * @OnMessage
	 */
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		log.debug("[message] {} : {}", session.getId(), message.getPayload());
		
		for(WebSocketSession sess : sessionList) {
			sess.sendMessage(new TextMessage(session.getId() + " : " + message.getPayload()));
		}
	}
}
