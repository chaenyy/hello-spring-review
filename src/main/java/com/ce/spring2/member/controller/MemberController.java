package com.ce.spring2.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ce.spring2.member.model.dto.Member;
import com.ce.spring2.member.model.service.MemberService;

import lombok.extern.slf4j.Slf4j;
/**
 * <pre>
 * @Controller 클래스의 handler메소드가 가질수 있는 매개변수 타입
 * 
 * HttpServletRequest
 * HttpServletResponse
 * HttpSession
 * 
 * java.util.Locale : 요청에 대한 Locale
 * InputStream/Reader : 요청에 대한 입력스트림
 * OutputStream/Writer : 응답에 대한 출력스트림. ServletOutputStream, PrintWriter
 * 
 * 사용자입력값처리
 * Command객체 : http요청 파라미터를 커맨드객체에 저장한 VO객체
 * CommandMap :  HandlerMethodArgumentResolver에 의해 처리된 사용자입력값을 가진 Map객체
 * @Valid : 커맨드객체 유효성 검사객체
 * Error, BindingResult : Command객체에 저장결과(Command객체 바로 다음위치시킬것.)
 * @PathVariable : 요청url중 일부를 매개변수로 취할 수 있다.
 * @RequestParam : 사용자입력값을 자바변수에 대입처리(필수여부 설정)
 * @RequestHeader : 헤더값
 * @CookieValue : 쿠키값
 * @RequestBody : http message body에 작성된 json을 vo객체로 변환처리
 * 
 * 뷰에 전달할 모델 데이터 설정
 * ModelAndView
 * ModelMap 
 * Model
 
 * @ModelAttribute : model속성에 대한 getter
 * @SessionAttribute : session속성에 대한 getter(required여부 선택가능)
 * @SessionAttributes : session에서 관리될 속성명을 class-level에 작성
 * SessionStatus: @SessionAttributes로 등록된 속성에 대하여 사용완료(complete)처리. 세션을 폐기하지 않고 재사용한다.
 * 
 * 기타
 * MultipartFile : 업로드파일 처리 인터페이스. CommonsMultipartFile
 * RedirectAttributes : DML처리후 요청주소 변경을 위한 redirect시 속성처리 지원
 * 
 * Model
 * - mvc의 model이 아닌, view단에 데이터를 전달하기 위한 임시 저장소
 * - Map 객체
 * 
 * - ModelAndView
 * 		- model 기능 : addObject
 * 		- view 기능 : setView(View) | setViewName(String)
 * - ModelMap
 * 		- model : addAttribute
 * 		- view 없음 : handler(Controller의 메소드)에서 view 정보를 문자열로 반환해야함
 * - Model
 * 		- model : addAttribute
 * 		- view 없음 : handler(Controller의 메소드)에서 view 정보를 문자열로 반환해야함
 * 
 * 관련 어노테이션
 * @ModelAttribute
 * 		- 메소드레벨 : 해당 controller의 전역 모델 속성 등록
 * 		- 메소드 매개변수에 작성 : 모델 속성에 대한 getter 
 * @SessionAttribute - 메소드 매개변수에 작성. session scope에 저장된 속성에 대한 getter
 * @SessionAttributes - 클래스 레벨 작성. session scope에 저장될 속성명 관리
 * 
 * </pre>
 * 
 */
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
	
	@GetMapping("/memberDetail.do")
	public ModelAndView memberDetail(ModelAndView mav, @SessionAttribute Member loginMember) {
		log.debug("loginMember = {}", loginMember);
		
		mav.setViewName("member/memberDetail");
		return mav;
	}
	
	@PostMapping("/memberUpdate.do")
	public String memberUpdate(@ModelAttribute Member member, RedirectAttributes redirectAttr, Model model) {
		log.debug("loginMember = {}", member);
		
		int result = memberService.updateMember(member);
		model.addAttribute("loginMember", memberService.selectOneMember(member.getMemberId()));
		redirectAttr.addFlashAttribute("msg", "회원정보를 성공적으로 수정했습니다.");
		
		return "redirect:/member/memberDetail.do";
	}
	
	/**
	 * jsonView 빈을 통해 ajax 응답하기
	 * - model에 담긴 속성을 json 문자열로 변환해서 응답메세지 body에 출력
	 * - BeanNameViewResolver를 통해서 viewName에 해당하는 빈을 찾는다
	 */
	@GetMapping("/checkIdDuplicate.do")
	public String checkIdDuplicate(@RequestParam String memberId, Model model) {
		Member member = memberService.selectOneMember(memberId);
		boolean available = member == null;
		
		model.addAttribute("memberId", memberId);
		model.addAttribute("available", available);
		
		return "jsonView";
	}
}







