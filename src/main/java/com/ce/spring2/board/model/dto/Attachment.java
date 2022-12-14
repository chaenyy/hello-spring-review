package com.ce.spring2.board.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Attachment {
	private int no;
	private int boardNo;
	@NonNull
	private String originalFilename;
	@NonNull
	private String renamedFilename;
	private int downloadCount;
	private LocalDateTime createdAt;
}
