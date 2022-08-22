package com.ce.spring2.member.model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.ce.spring2.demo.model.dto.Gender;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

// @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode. 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
	@NonNull
	private String memberId;
	@NonNull
	private String password;
	@NonNull
	private String name;
	private Gender gender;
	@DateTimeFormat(pattern = "yyyy-MM-dd") //클라이언트에서 온 값을 커맨드객체에 대입하기 위함!
	private LocalDate birthday;
	private String email;
	@NonNull
	private String phone;
	private String address;
	private String[] hobby;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private boolean enabled;
}
