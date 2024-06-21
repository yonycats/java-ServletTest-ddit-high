package kr.or.ddit.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class T04ServletCookieTest extends HttpServlet {
	
	/*
	 	쿠키(Cookie)정보에 대하여
	 	
	 	- HTTP의 특징이 커넥션리스이기 때문에 연결이 계속적으로 유지되지 않는다. 그래서 쿠키 정보를 사용
	 	=> 웹서버와 브라우저는 애플리케이션을 사용하는 동안 필요한 값을 쿠키를 통해 공유하여 상태를 유지한다.
	 	
	 	1. 구성 요소?
	 	- 이름
	 	- 값
	 	- 유효시간(초)
	 	- 도메인 : ex) www.somehost.com, .somehost.com => 쿠키의 도메인이 쿠키를 생성한 
	 	 		서버의 도메인을 벗어나면 브라우저는 쿠키를 저장(생성)하지 않는다. (보안 위험 때문에)
	 	- 경로 : 쿠키를 공유할 기준경로를 지정한다. (도메인 이후 부분으로 디렉토리 수준) 
	 		   => 지정하지 않으면 URL경로 부분이 사용된다. 
	 	
	 	2. 동작 방식
	 	- 쿠키 생성단계 : 생성한 쿠키를 응답메시지의 헤더에 저장하여 웹브라우저에 전송한다.
	 	- 쿠키 저장단계 : 웹브라우저는 응답데이터에 포함된 쿠키를 쿠키저장소에 보관한다. (웹브라우저(클라이언트 쪽)에 저장됨)
	 	- 쿠키 전송단계 : 웹브라우저는 저장한 쿠키를 요청이 있을 때마다 웹서버에 전송한다. (삭제되기 전까지)
	 				  웹서버는 브라우저가 전송한 쿠키를 사용해서 필요한 작업을 수행한다.
	 				  
	 	// 쿠키를 많이 쓰는 곳 : 팝업창 (오늘 하루만 열지 않음 내용 저장 등), 북마크 기능 등, 보안상 중요하지 않은 곳들
	 	 * 클라이언트에 저장하는 방법의 장점 : 서버에 부담을 주지 않음
	 	 * 클라이언트에 저장하는 방법의 단점 : 데이터 변질의 위험성, 데이터 삭제 위험성, 보안 위험성 등
	 				  
	 */

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 쿠키 생성 예제 만들기 -> 쿠키 만드는게 잘 되는 걸 확인했으니 주석 처리함
		setCookieExam(req, resp);
		
		// 쿠키 정보 읽기 예제 만들기
//		readCookieExam(req, resp);
		
		//쿠키 정보 삭제 예제 만들기
//		deleteCookieExam(req, resp);
	}
	
	
	private void deleteCookieExam(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		/*
		 	 사용중인 쿠키 정보를 삭제하는 방법
		 	 
		 	 1. 사용 중인 쿠키정보를 이용하여 쿠키객체를 생성한다.
		 	 2. 쿠키객체의 최대지속시간을 0으로 설정한다.
		 	 3. 설정한 쿠키객체를 응답헤더에 추가하여 전송한다.
		 	 
		 */
		
		Cookie[] cookies = req.getCookies();
		
		/////////////////////////////////////////
				
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		
		PrintWriter out = resp.getWriter();
		
		String title = "쿠키정보 삭제 예제";
		
		out.print("<!DOCTYPE html><head><title>" + title + "</title><head>"
				+ "<body>");
				
		if (cookies != null) {
			out.print("<h2>" + title + "</h2>");
			
			for (Cookie cookie : cookies) {
				if ( cookie.getName().equals("userId") ) {
					// 쿠키 삭제하기
					// setMaxAge : 초단위, 쿠키를 저장하는 시간, 0으로 세팅하면 0초만 저장하겠다는 뜻 = 삭제
					cookie.setMaxAge(0);
					
					resp.addCookie(cookie);
					System.out.println("<p> 삭제 처리한 쿠키 : " + cookie.getName() + "</p><br>");
				}
			}
			
			for (Cookie cookie : cookies) {
				out.print("<p>name : " + cookie.getName() + "</p><br>");
				out.print("<p>value : " + URLDecoder.decode(cookie.getValue(), "UTF-8") + "</p><br>");
				out.print("<hr>");
			} 
		} else {
			out.print("<h2>쿠키 정보가 없습니다. </h2>");
		}
		out.print("</body></html>");
	}


	// 쿠키 정보 읽기 예제 만들기
	// setCookieExam에서 만들었던 쿠키 정보가 웹에 남아있고, 그걸 불러와서 정보를 띄움
	// 브라우저에서 쿠키정보를 삭제하면 쿠키 정보가 뜨지 않음
	private void readCookieExam(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		// 현재 도메인에서 사용 중인 쿠키객체 배열 가져오기
		Cookie[] cookies = req.getCookies();
		
		/////////////////////////////////////////
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		
		PrintWriter out = resp.getWriter();
		
		String title = "쿠키정보 읽기 예제";
		
		out.print("<!DOCTYPE html><head><title>" + title + "</title><head>"
				+ "<body>");
				
		if (cookies != null) {
			out.print("<h2>" + title + "</h2>");
			
			for (Cookie cookie : cookies) {
				out.print("<p>name : " + cookie.getName() + "</p><br>");
				out.print("<p>value : " + URLDecoder.decode(cookie.getValue(), "UTF-8") + "</p><br>");
				out.print("<hr>");
			} 
		} else {
			out.print("<h2>쿠키 정보가 없습니다. </h2>");
		}
		out.print("</body></html>");
	}


	// 쿠키 생성 예제 만들기
	private void setCookieExam(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		// 특별한 문제가 없다면 항상 처음에 작성한다고 보면 됨, 한글 깨짐 방지
		req.setCharacterEncoding("UTF-8");
		
		// 아래 생성한 쿠키 2개로 
		// localhost:8888/ServletTest/T04ServletCookieTest?name=홍길동&userId=apple
		// 처럼 검색할 수 있음
		// 쿠키 생성하기 1
		Cookie userId = new Cookie("userId", req.getParameter("userId"));
		
		// 쿠키 생성하기 2
		// 쿠키값에 한글을 사용시 인코딩 처리를 해준다.
		Cookie name = new Cookie("name", URLEncoder.encode(req.getParameter("name"), "UTF-8"));
		
		// 쿠키 소멸시간 설정하기(초단위) => 지정하지 않으면 웹브라우저를 종료할 때 쿠키를 함께 삭제한다.
		userId.setMaxAge(60 *60 *24); // 1일
		
		// 콘솔에 document.cookie를 검색하면 name은 검색되지만, 
		// 보안 처리된 userId는 아예 뜨지 않는다.
		userId.setHttpOnly(true); // JavaScript를 이용한 직접 접근 방지(XSS 공격 대비)
		
		name.setMaxAge(60 * 60 * 48);
		
		////////////////////////////////////////////
		
		// 응답 헤더에 쿠키 추가하기
		resp.addCookie(userId);
		resp.addCookie(name);
		
		// 응답 헤더에 인코딩 및 Content-Type 설정
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		
		PrintWriter out = resp.getWriter();
		
		String title = "쿠키 설정 예제";
		
		out.println("<!DOCTYPE html><html><head><title>" + title + "</title><head>"
				  + "<body>"
				  + "<h1 align=\"center\">" + title + "</h1>"
				  + "<ul>"
				  + "<li><b>ID : </b>"
				  + req.getParameter("userId") + "</li>"
				  + "<li><b>이름 : </b>"
				  + req.getParameter("name") + "</li>"
				  + "</ul></body></html>");
	}


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
