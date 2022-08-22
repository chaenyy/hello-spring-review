package com.ce.spring2.member.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ce.spring2.member.model.dto.Member;

@Mapper
public interface MemberDao {

	int insertMember(Member member);

	Member selectOneMember(String memberId);
	
}
