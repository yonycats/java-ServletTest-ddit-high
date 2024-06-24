package kr.or.ddit.basic;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

// 서블릿의 사용자와 수행시간을 필터로 알아내기

public class T08ServletFilter implements Filter {
	
	// 서블릿의 수행속도 넣을 것임
		
	@Override
	public void destroy() {
		System.out.println("[T08ServletFilter] destroy() 호출됨");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("[T08ServletFilter] doFilter() 시작");
		
		// 서블릿 수행 시간 계산하기
		long startTime = System.nanoTime(); // 좀 더 정밀한 시간 계산
		
		// 필터 체인을 실행한다.
		chain.doFilter(req, resp);
		
		System.out.println("서블릿 수행시간(ns) : " + (System.nanoTime() - startTime) );
		
		System.out.println("[T08ServletFilter] doFilter() 끝");
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("[T08ServletFilter] init() 호출됨");
	}

}
