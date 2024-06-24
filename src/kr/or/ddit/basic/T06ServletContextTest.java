package kr.or.ddit.basic;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class T06ServletContextTest extends HttpServlet {

	/*
	 	서블릿 컨텍스트 객체에 대하여
	 	
	 	- 브라우저가 계속 유지되는 동안 저장해둬야할 데이터를 저장해두면 좋음, 전역변수 느낌 
	 	
	 	1. 서블릿 프로그램이 컨테이너와 정보를 주고받기 위한 메서드 및 유틸 기능을 제공한다.
	 	ex) 파일의 MINE TYPE 정보 가져오기, 요청정보 보내기, 로깅 처리 등
	 	
	 	2. 웹 애플리케이션당 1개씩 생성된다. 전체를 대표하는 객체
	 	
	 	*** 서블릿에서 가장 중요한 객체 4개 ***
	 	HttpServletRequest, HttpServletResponse -> 요청별로 하나씩 만들어졌다가 사라짐
	 	HttpSession -> 브라우저별 하나씩 만들어졌다가 사라짐
	 	ServletContext -> 애플리케이션 1개당 1개씩 만들어짐, 서비스(서버)를 시작할 때 만들어지고, 서비스(서버)가 내려가면 사라짐
	 					    
	 */
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		ServletContext ctx = req.getServletContext();
		
		// 해당 메서드를 호출하면 가장 최상단의 웹 프로그램의 이름(어플리케이션의 대표 경로)가 반환된다.
		// 어딘가에 웹 프로그램의 이름을 하드코딩할 때 해당 메서드로 불러와서 확인하고 하드코딩을 하거나
		// web.xml 같은 곳에서 하드코딩이 아니라 메서드로 코딩하면 하드코딩할 필요가 없음
		System.out.println("서블릿 컨텍스트의 기본 경로 : " + ctx.getContextPath()); // 제일 많이씀, 대표 경로 호출
		// 자주쓰는 메소드는 Request객체 안에 구현되어 있음
		System.out.println("서블릿 컨텍스트의 기본 경로 : " + req.getContextPath()); // 대표 경로 호출
		
		System.out.println("서버 정보 : " + ctx.getServerInfo());
		System.out.println("서블릿 API의 메이저 버전 정보 : " + ctx.getMajorVersion());
		System.out.println("서블릿 API의 마이너 버전 정보 : " + ctx.getMinorVersion());
		// web.xml 문서를 배포 기술자(Deployment)라고 부름
		// web.xml의 <display-name>을 가져오는 메서드
		System.out.println("배포 기술자에 기술된 컨텍스트명 : " + ctx.getServletContextName());
		// 알고 싶은 파일의 정보를 넣으면 해당 타입을 반환해줌, 조건문에 활용 가능
		System.out.println("파일에 대한 MINE 타입 정보 : " + ctx.getMimeType("abc.mp3"));
		// 파일 시스템 상의 /(루트)는 실제 파일 시스템에서 어느 경로를 거쳐서 실행되는지를 알려줌
		System.out.println("파일 시스템 상의 실제 경로 : " + ctx.getRealPath("/"));
		
		// 속성값 저장
		ctx.setAttribute("attr1", "속성1");
		
		// 속성값 변경
		ctx.setAttribute("attr1", "속성11");
	
		// 속성값 가져오기
		System.out.println("attr1의 속성값 : " + ctx.getAttribute("attr1"));
		
		// 속성값 제거하기
		ctx.removeAttribute("attr1");
		
		// 로깅 작업하기 (로그 파일)
		ctx.log("서블릿 컨텍스트 객체를 이용한 로깅 작업 중입니다.");
		
		// 포워딩(전달) 처리하기
		// RequestDispatcher 클래스로 포장된(형태가 정해진)dispatcher 객체를 얻어서
		// dispatcher 객체에 있는 forward() 메서드를 통해서 포워딩을 함
		// forward()은 사용자 요청에 의해 컨테이너에서 생성된 request와 response를
		// 다른 리소스(서블릿, jsp, html)로 넘겨주는 역할을 한다.
//		ctx.getRequestDispatcher("/T05ServletSessionTest").forward(req, resp);
		
		
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
