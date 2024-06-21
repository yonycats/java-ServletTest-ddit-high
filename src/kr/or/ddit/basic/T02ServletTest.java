package kr.or.ddit.basic;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class T02ServletTest extends HttpServlet{

	/*
	 	서블릿(Servlet) 동작 방식에 대하여
	 	
	 	1. 사용자(클라이언트)가 URL을 클릭하면 HTTP 요청 메시지를 서블릿 컨테이너로 전송한다.
	 	2. 서블릿 컨테이너는 web.xml에 설정된 url 패턴 정보를 확인하여 어느 서블릿을 통해 처리할 것인지를 검색한다.
	 	   (서블릿이 컨테이너에 로딩이 안된 경우에는 먼저 생성하여 로딩 처리함. init()메서드도 호출된다.)
	 	3. 서블릿 컨테이너는 요청을 처리할 개별 스레드를 생성하여 해당 서블릿 객체의 service()메서드를 호출한다.
	 	   (HttpServletRequest 및 HttpServletResponse 객체를 생성하여 파라미터로 넘겨준다.)
	 	4. service() 메서드는 메서드 타입을 확인하여 적절한 메서드를 호출한다.(doGet, doPost, doPut, doDelete 등)
	 	4-1. 동적 페이지 생성 후 ServletResponse 객체에 응답 전송
	 	5. 사용자의 응답 요청 처리가 완료되면, HttpServletRequest 및 HttpServletResponse 객체는 소멸된다.
	 	   (저장한 내용들도 모두 사라짐, 유의할 것)
	 	   - 중요한 점은 request는 응답 전까지 계속 여러번 사용할 수 있고,
	 	   	  서블릿은 클라이언트의 요청인 request 객체를 response하기 전까지 여러 서블릿에서 계속 돌려쓸 수 있다.
	 	6. 컨테이너로부터 서블릿이 제거되는 경우에는 destroy() 메서드가 호출된다.
	 	
	 	
	 	우리가 주로 사용할 작업과 메서드는 (doGet, doPost)임, Get과 Post의 요청별 다른 응답 반환 등
	 */
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// Request 객체의 메서드 확인하기
		System.out.println("getCharacterEncoding() : " + req.getCharacterEncoding());
		System.out.println("getContentLength() : " + req.getContentLength());
		System.out.println("getQueryString() : " + req.getQueryString());
		System.out.println("getProtocol() : " + req.getProtocol());
		System.out.println("getMethod() : " + req.getMethod());
		System.out.println("getRequestURI() : " + req.getRequestURI());
		System.out.println("getHeaderNames() : " + req.getHeaderNames());
		System.out.println("getRemoteAddr() : " + req.getRemoteAddr());
		System.out.println("getRemotePort() : " + req.getRemotePort());
		
		/////////////////////////////////////////////////////
		
		// 요청 메시지로부터 name 값 가져오기
		// 주고 받는 값들의 기본타입은 String이다.
		// 원하는 파라미터 값을 넣어서 요청하면 됨
		String name = req.getParameter("name");
		
		System.out.println("name : " + name);
		
		// 요청 객체에 데이터 저장하기
		req.setAttribute("tel", "1234-5678");
		req.setAttribute("addr", "대전시 중구 오류동");
		
		// 요청 객체에 저장된 데이터 꺼내오기
		System.out.println("tel => " + req.getAttribute("tel"));
		System.out.println("addr => " + req.getAttribute("addr"));
		
		////////////////////////////////////////////////////
		// 응답 메시지를 생성하기 위한 작업 시작
		
		// 응답 메시지의 컨텐트 타입 정보 설정
		// mine 타입의 "text/plain" 일반적인 평범한 텍스트 파일을 의미
		// charset=UTF-8 은 한글이 깨지지 않도록 넣는 것
		resp.setContentType("text/plain; charset=UTF-8");
		
		// 위 1줄을 2줄로 분리해서 쓰면 이렇게
//		resp.setCharacterEncoding("charset=UTF-8");
//		resp.setContentType("text/plain");
		
		// 응답 내용 작성하기
		// 응답을 요청한 브라우저에 아래 내용을 출력함
		PrintWriter out = resp.getWriter();
		
		out.println("name => " + name);
		out.println("서블릿 경로 : " + req.getServletPath());
		out.println("서블릿 컨텍스트 경로 : " + req.getContextPath());
		
		// 응답 메시지를 생성하기 위한 작업 끝
		
		
		/* 실행시 출력값 =>
		    getCharacterEncoding() : null
			getContentLength() : -1  => get방식이라서 body가 없기 때문에 -1이 옴
			getQueryString() : null
			getProtocol() : HTTP/1.1
			getMethod() : GET
			getRequestURI() : /ServletTest/T02ServletTest
			getHeaderNames() : org.apache.tomcat.util.http.NamesEnumerator@1f793ca4
			getRemoteAddr() : 0:0:0:0:0:0:0:1  => 정보를 요청한 원격호스트의 IP주소를 가져옴, 현재는 로컬 기준값
			getRemotePort() : 53356
			name : null
			tel => 1234-5678
			addr => 대전시 중구 오류동
		 */
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 아래처럼 코딩을 할 때의 의미 : doGet이든, doPost이든 동일한 작업을 하고 싶을 때 아래처럼 코딩함
		doGet(req, resp);
	}
	
}
