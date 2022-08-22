package com.ce.spring2.member.model.service;

import com.ce.spring2.member.model.dto.Member;

public interface MemberService {

	int insertMember(Member member);

	Member selectOneMember(String memberId);

}
