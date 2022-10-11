package com.ce.spring2.chat.model.service;

import java.util.List;

import com.ce.spring2.chat.model.dto.ChatLog;
import com.ce.spring2.chat.model.dto.ChatMember;

public interface ChatService {

	ChatMember findChatMemberByMemberId(String memberId);

	void insertChatMembers(List<ChatMember> chatMembers);

	int insertChatLog(ChatLog chatlog);

	List<ChatLog> findChatlogByChatroomId(String chatroomId);

	List<ChatLog> findRecentChatLogs();
	
}
