package com.ce.spring2.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ce.spring2.member.model.dto.Member;
import com.ce.spring2.member.model.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/member")
@Slf4j
@SessionAttributes({"loginMember"})
public class MemberController {
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	@GetMapping("/memberEnroll.do")
	public String memberEnroll() {
		
		return "member/memberEnroll";
	}
	
	/**
	 * $2a$10$G7F3EfcXE8wJWUAsCznaoOqttim4MwJBNlWey5yuL5ZRMwRSdPvEi
	 * - $2a$ : 알고리즘 타입
	 * - 10$ : 옵션 (비용이 높을수록 속도가 오래걸리고, 메모리 사용량이 많음)
	 * - G7F3EfcXE8wJWUAsCznaoO(22자리) : random salt
	 * - qttim4MwJBNlWey5yuL5ZRMwRSdPvEi(31자리) : hashing + encoding 처리
	 */
	@PostMapping("/memberEnroll.do")
	public String memberEnroll(Member member, RedirectAttributes redirectAttr) {
		try {
			// 비밀번호 암호화
			String rawPassword = member.getPassword();
			String encodePassword = bcryptPasswordEncoder.encode(rawPassword);
			member.setPassword(encodePassword);
			log.debug("encodePassword = {}", encodePassword);
			
			int result = memberService.insertMember(member);
			redirectAttr.addFlashAttribute("msg", "회원가입이 정상적으로 처리 되었습니다.");
			
			return "redirect:/";
		} catch(Exception e) {
			log.error("회원 가입 오류 : " + e.getMessage(), e);
			throw e;
		}
	}
	
	/**
	 * viewName이 null인 경우, 요청 url을 기준으로 jsp위치를 추론
	 * 
	 *	/member/memberLogin.do
	 *	-> member/memberLogin으로 추론
	 */
	@GetMapping("/memberLogin.do")
	public void memberLogin() {
		
	}
	
	@PostMapping("/memberLogin.do")
	public String memberLogin(@RequestParam String memberId, @RequestParam String password, Model model, RedirectAttributes redirectAttr) {
		// 1. memberId로 조회
		Member member = memberService.selectOneMember(memberId);
		
		String location = "/";
		// 2. member가 null이 아니면서, 비밀번호가 일치하면 로그인 성공
		if(member != null && bcryptPasswordEncoder.matches(password, member.getPassword())) {
			// model을 통해 session scope에 속성 저장 : 클래스레벨에 @SessionAttributes로 등록
			model.addAttribute("loginMember", member);
		}	
		// 로그인실패
		else {
			redirectAttr.addFlashAttribute("msg", "아이디 또는 비밀번호가 일치하지 않습니다.");
			location += "member/memberLogin.do";
		}
		
		return "redirect:" + location;
	}
	
	/**
	 * @SessionAttributes로 세션 관리를 한다면, SessionStatus#setComplete으로 만료처리! 
	 */
	@GetMapping("/memberLogout.do") 
	public String memberLogout(SessionStatus sessionStatus) {
		if(!sessionStatus.isComplete()) {
			// 세션을 다 사용했다는 의미 - 만료 처리(세션 객체는 유지하되, 안에 내용만 제거)
			sessionStatus.setComplete();
		}
		
		return "redirect:/";
	}
}







