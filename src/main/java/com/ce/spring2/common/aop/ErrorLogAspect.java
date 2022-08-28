package com.ce.spring2.common.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class ErrorLogAspect {
	// @Pointcut('execution(* com.ce.spring2..*Controller.*(..))") 도 가능!
	@Pointcut("within(com.ce.spring2.*.controller.*)") // com.ce.spring2.어느패키지.controller 하위의 모든 클래스
	public void pc() {}
	
	@AfterThrowing(pointcut = "pc()", throwing = "e")
	public void errorLogAdvice(JoinPoint joinPoint, Exception e) {
		log.debug("ErrorLogAspect 실행!");
		log.error(e.getMessage(), e);
	}
}
