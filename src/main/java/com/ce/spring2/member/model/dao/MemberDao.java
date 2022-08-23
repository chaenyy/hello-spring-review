package com.ce.spring2.member.model.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import com.ce.spring2.member.model.dto.Member;

@Mapper
public interface MemberDao {

	int insertMember(Member member);

	Member selectOneMember(String memberId);

	@Update("update member "
			+ "set name = #{name}, gender = #{gender}, birthday = #{birthday}, email = #{email}, phone = #{phone}, address = #{address}, hobby = #{hobby}, updated_at = sysdate "
			+ "where member_id = #{memberId}")
	int updateMember(Member member);
	
}
