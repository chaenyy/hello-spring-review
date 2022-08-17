package com.ce.spring2.demo.controller;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ce.spring2.demo.model.dto.Dev;
import com.ce.spring2.demo.model.dto.Gender;
import com.ce.spring2.demo.model.service.DemoService;

/**
 * DispatcherServlet이 먼저 받아서 DemoController를 호출!
 */
@Controller
public class DemoController {
	
	static final Logger log = LoggerFactory.getLogger(DemoController.class);
	
	@Autowired
	private DemoService demoService;
	
	/**
	 * value : path에 대한 별칭
	 * method : 전송방식 - 작성하지 않으면, 모든 전송방식 허용 
	 */
	@RequestMapping(path = "/demo/devForm.do", method = RequestMethod.GET)
	public String devForm() {
		log.info("{} 요청!", "/demo/devForm.do");
		return "demo/devForm";
	}
	
	@RequestMapping(path = "/demo/dev1", method = RequestMethod.POST)
	public String dev1(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("name");
		int career = Integer.parseInt(request.getParameter("career"));
		String email = request.getParameter("email");
		String _gender = request.getParameter("gender");
		Gender gender = _gender != null ? Gender.valueOf(_gender) : null;
		String[] langs = request.getParameterValues("lang");
		
		Dev dev = new Dev(0, name, career, email, gender, langs, LocalDateTime.now());
		
		request.setAttribute("dev", dev);
		
		return "demo/devResult";
	}
	
	@RequestMapping(path = "/demo/dev2", method = RequestMethod.POST)
	public String dev2(
			@RequestParam(required = false, defaultValue = "홍길동") String nm,
			@RequestParam int career,
			@RequestParam String email,
			@RequestParam Gender gender,
			@RequestParam String[] lang,
			Model model
			) {
		Dev dev = new Dev(0, nm, career, email, gender, lang, LocalDateTime.now());
		log.info("dev = {}", dev);
		
		model.addAttribute("dev", dev);	
		
		return "demo/devResult";
	}
	
	/**
	 * 커맨드 객체
	 * - 사용자 입력 name값 - property(setter)가 일치하면 값 대입
	 * - 자동으로 model 속성으로 등록 
	 */
	@RequestMapping(path = "/demo/dev3", method = RequestMethod.POST)
	public String dev3(Dev dev) {
		
		log.info("dev = {}", dev);
		
		return "demo/devResult";
	}
}
