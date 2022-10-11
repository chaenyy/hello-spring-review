package com.ce.spring2.chat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatLog {
	private long no;
	private String chatroomId;
	private String memberId;
	private String msg;
	private long time;
}
