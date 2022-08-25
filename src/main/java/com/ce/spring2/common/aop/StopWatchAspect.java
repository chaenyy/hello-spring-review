package com.ce.spring2.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class StopWatchAspect {
	
	@Pointcut("execution(* com.ce.spring2.todo.controller.TodoController.insertTodo(..))")
	public void pointcut() {}
	
	@Around("pointcut()")
	public Object stopWatch(ProceedingJoinPoint joinPoint) throws Throwable {
		StopWatch stopWatch = new StopWatch();

		// before
		stopWatch.start();	
		
		// 주업무 실행 (insertTodo)
		Object obj = joinPoint.proceed();
		
		// after
		stopWatch.stop();
		
		long millis = stopWatch.getTotalTimeMillis();
		log.debug("소요시간 - {}", millis);
		
		return obj;
	}
}
