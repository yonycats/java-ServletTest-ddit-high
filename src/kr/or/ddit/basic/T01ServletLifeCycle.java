package kr.or.ddit.basic;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// servlet-api.jar를 빌드패스하면 HttpServlet 관련 클래스들이 뜸
public class T01ServletLifeCycle extends HttpServlet{

	/*
	 	서블릿이란? 서블릿 컨테이너(서블릿 엔진)에 의해 관리되는 자바기반 웹 컴포넌트로서, 동적인 웹 컨텐츠 생성을 가능하게 해준다.
	 	동적인 페이지란 -> 인자에 따라 바뀌는 웹 페이지 
	 	// (예 : http://localhost:8888/ServletTest/T04ServletCookieTest?name=hodu&userId=apple)
	 */
	
	public T01ServletLifeCycle() {
		System.out.println("T01ServletLifeCycle 생성자 호출됨.");
	}
	
	 
	// 처음 실행할 때 1번만 실행됨
	// 1번만 호출되는 작업들을 넣으면 됨
	@Override
	public void init() throws ServletException {
		// 초기화 코드 작성
		System.out.println("[T01ServletLifeCycle] init() 호출됨");
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 실제적인 작업 수행이 시작되는 지점 (자바의 메인 메서드 역할)
		System.out.println("[T01ServletLifeCycle] service() 호출됨");
		super.service(req, resp);
	}
	
	// 주 사용 메서드
	// 주소창에서 url을 입력해서 Get 방식으로 열면 브라우저를 service()와 doGet()이 호출됨
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("[T01ServletLifeCycle] doGet() 호출됨");
	}
	
	// 주 사용 메서드
	// 주소창에서 url을 입력해서 Post 방식으로 열면 브라우저를 service()와 doPost()이 호출됨
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("[T01ServletLifeCycle] doPost() 호출됨");
	}
	
	// 서버(톰캣)을 중지시키면 컨테이너가 사라지고, destory()가 호출됨
	// 서버를 종료할 때 해야할 작업들을 넣으면 됨
	@Override
	public void destroy() {
		// 객체 소멸시(컨테이너로부터 서블릿 객체 제거시) 필요한 코드 작성
		System.out.println("[T01ServletLifeCycle] destory() 호출됨");
	}
	
}
