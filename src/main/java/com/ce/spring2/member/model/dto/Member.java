package com.ce.spring2.member.model.dto;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Member extends MemberEntity implements UserDetails {
	/**
	 * SimpleGrantedAuthority
	 * - 문자열로 권한을 관리
	 * "ROLE_USER" -> new SimpleGrantedAUthority("ROLE_USER")
	 */
	private List<SimpleGrantedAuthority> authorities;
	
	/**
	 * 사용자가 어떤 권한을 가지고 있는 지 조회
	 * GrantedAuthority -> 실제 권한 정보를 가진 객체
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return memberId;
	}

	/**
	 * 계정 만료 여부
	 */
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return enabled;
	}

	/**
	 * 계정 잠김 여부
	 */
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return enabled;
	}

	/**
	 * 비밀번호 만료 여부
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return enabled;
	}
}
