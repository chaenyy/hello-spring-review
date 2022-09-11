package com.ce.spring2.member.controller;

import java.util.Collection;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ce.spring2.member.model.dto.Member;
import com.ce.spring2.member.model.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/member")
@Slf4j
public class MemberSecurityController {
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
	
	@PostMapping("/memberLoginSuccess.do")
	public String memberLoginSuccess(HttpSession session) {
		log.debug("memberLoginSuccess 호출!");
		
		// 로그인 후처리
		String location = "/";		
		
		// security가 관리하는 다음 리다이렉트 url
		SavedRequest savedRequest = (SavedRequest) session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
		if(savedRequest != null) {
			location = savedRequest.getRedirectUrl();
		}
		log.debug("location = {}", location);
		return "redirect:" + location;
	}
	
	/**
	 * security가 관리하는 인증된 사용자 정보
	 */
	//@GetMapping("/memberDetail.do")
	public ModelAndView memberDetail(ModelAndView mav) {
		// SecurityContextHolder로부터 직접 가져오는 방법
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		Object principal = authentication.getPrincipal();
		Object credentials = authentication.getCredentials();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		log.debug("principal = {}", principal);
		log.debug("credentials = {}", credentials);
		log.debug("authorities = {}", authorities);
		
		mav.setViewName("member/memberDetail");
		return mav;
	}
	
	@GetMapping("/memberDetail.do")
	public ModelAndView memberDetail(Authentication authentication, ModelAndView mav) {
		Object principal = authentication.getPrincipal();
		Object credentials = authentication.getCredentials();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		log.debug("principal = {}", principal);
		log.debug("credentials = {}", credentials);
		log.debug("authorities = {}", authorities);
		
		mav.setViewName("member/memberDetail");
		return mav;
	}
}
