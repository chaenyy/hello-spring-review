package com.ce.spring2.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class EscapeXmlAspect {
	
	@Pointcut("execution(* com.ce.spring2.todo.controller.TodoController.todoList(..))")
	public void pc() {}
	
	// returning -> AfterReturning은 리턴값에 접근할 수 있는데, 해당 리턴값을 어느 변수명에 담아줄 지 결정!
	@AfterReturning(pointcut = "pc()", returning = "returnObj")
	public void escapeXml(JoinPoint joinPoint, Object returnObj) {
		ModelAndView mav = (ModelAndView) returnObj;
		log.debug("mav = {}", mav);
	}
}	
