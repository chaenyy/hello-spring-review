package com.ce.spring2.chat.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ce.spring2.chat.model.dao.ChatDao;
import com.ce.spring2.chat.model.dto.ChatLog;
import com.ce.spring2.chat.model.dto.ChatMember;

@Service
@Transactional(rollbackFor = Exception.class)
public class ChatServiceImpl implements ChatService {
	@Autowired
	ChatDao chatDao;
	
	@Override
	public ChatMember findChatMemberByMemberId(String memberId) {
		return chatDao.findChatMemberByMemberId(memberId);
	}
	
	@Override
	public void insertChatMembers(List<ChatMember> chatMembers) {
		for(ChatMember member : chatMembers) {
			chatDao.insertChatMember(member);
		}
	}
	
	@Override
	public int insertChatLog(ChatLog chatlog) {
		return chatDao.insertChatLog(chatlog);
	}
	
	@Override
	public List<ChatLog> findChatlogByChatroomId(String chatroomId) {
		return chatDao.findChatlogByChatroomId(chatroomId);
	}
	
	@Override
	public List<ChatLog> findRecentChatLogs() {
		return chatDao.findRecentChatLogs();
	}
}
