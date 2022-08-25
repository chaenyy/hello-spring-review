package com.ce.spring2.common.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect // AOP적으로 활용되기 위해 어노테이션 추가!
@Slf4j
public class LogAspect {
	
	// 모든리턴타입 todo패키지하위.모든클래스.모든메소드(타입이 있거나없거나)
	@Pointcut("execution(* com.ce.spring2.todo..*(..))") 
	public void pointcut() {}
	
	@Around("pointcut()")
	public Object logAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
		Signature signature = joinPoint.getSignature(); // 메소드 시그니처
		String typeName = signature.getDeclaringTypeName(); // 클래스명
		String methodName = signature.getName(); // 메소드명
		
		// Before
		log.debug("{}.{} 실행 전!", typeName, methodName);
		
		// 주업무 메소드 실행
		Object obj = joinPoint.proceed();
		
		// After
		log.debug("{}.{} 실행 후!", typeName, methodName);
		
		return obj;
	}
}
