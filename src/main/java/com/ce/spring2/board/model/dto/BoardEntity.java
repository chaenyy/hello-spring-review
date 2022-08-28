package com.ce.spring2.board.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DB랑 구조가 같은 클래스
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardEntity {
	private int no;
	private String title;
	private String memberId;
	private String content;
	private int readCount;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
