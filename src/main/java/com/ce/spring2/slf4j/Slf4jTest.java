package com.ce.spring2.slf4j;

import org.slf4j.LoggerFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Slf4jTest {
	//private static final Logger log = LoggerFactory.getLogger(Slf4jTest.class);
	
	public static void main(String[] args) {
//		log.fatal("fatal"); // log4j에 있는 fatal 레벨이 slf4j에는 존재하지 않음!
		log.error("error");
		log.warn("warn");
		log.info("info");
		log.debug("debug");
		log.trace("trace");
	}
}
