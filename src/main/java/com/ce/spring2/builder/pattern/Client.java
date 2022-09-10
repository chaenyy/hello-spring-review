package com.ce.spring2.builder.pattern;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client {
	private long code; // pk
	private String username; // 유저아이디
	private String password;
	private String name;
	private LocalDate birthday;
	private String phone;
	private boolean married;
}
