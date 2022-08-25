package com.ce.spring2.proxy;

public class ProxyMain {
	
	FooService fooService = new FooProxy(new FooServiceImpl(), new Aspect()); // FooServiceImpl을 의존주입 받는다고 가정!
	
	public static void main(String[] args) {
		new ProxyMain().test();
	}

	private void test() {
		String name = fooService.getName();
		System.out.println(name);
	}
	
}

class FooProxy implements FooService {
	FooService fooService;
	Aspect aspect;
	
	public FooProxy(FooService fooService, Aspect aspect) {
		this.fooService = fooService;
		this.aspect = aspect;
	}
	
	@Override
	public String getName() {
		// before
		aspect.beforeAspect();
		
		// 주업무
		String name = fooService.getName(); // 주기능 joinpoint 실행
		
		// after
		return aspect.afterAspect(name);
		
//		return name;
	}
}

class Aspect {
	public void beforeAspect() {
		System.out.println("before!!!");
	}
	
	public String afterAspect(String str) {
		System.out.println("after!!!");
		return str.toUpperCase();
	}
}

interface FooService {
	String getName();
	
}

class FooServiceImpl implements FooService {
	@Override
	public String getName() {
		return "abcde";
	}
}
