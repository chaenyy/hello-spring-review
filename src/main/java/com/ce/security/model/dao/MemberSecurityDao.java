package com.ce.security.model.dao;

import com.ce.spring2.member.model.dto.Member;

public interface MemberSecurityDao {
	
	Member loadUserByUsername(String username);

}
