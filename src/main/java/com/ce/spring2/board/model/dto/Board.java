package com.ce.spring2.board.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class Board extends BoardEntity {
	private int attachCount;

	public Board(int no, String title, String memberId, String content, int readCount, LocalDateTime createdAt,
			LocalDateTime updatedAt, int attachCount) {
		super(no, title, memberId, content, readCount, createdAt, updatedAt);
		this.attachCount = attachCount;
	}
}
