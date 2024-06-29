package kr.or.ddit.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class T05ServletSessionTest extends HttpServlet {

	// *** 중요 *** 중간 프로젝트 때, 로그인 확인 여부 처리할 때 사용해야 함
	
	
/*
 	세션 (HttpSession) 객체에 대하여
 	
 		- 사용자당 1개씩 만들어짐
 		- 사용자별 저장할 정보나 내용이 있을 때 세션에 저장하면 됨. 세션이 끝나면 내용은 날아감
 		- 세션 객체를 이용하여 사용자 (웹 브라우저)별로 구분하여 정보를 관리할 수 있다. (세션ID 이용)
 		- 쿠키를 사용할 때보다 보안이 향상된다. (정보가 서버에 저장되기 때문에)
 		
 		- 세션 객체를 가져오는 (생성하는) 방법
 		// getSession인 이유 -> 사용자별로 웹페이지에 접속했을 때 1번씩만 만들기 때문에 가져오기만 하면 됨
 		 					  사용자로 방문할 때 1번 만들어져서 브라우저를 끄기 전까지 계속 유지됨
 		 					  세션이 끊길 때 : (클라이언트 입장에서) 브라우저를 완전히 종료했을 때
 		 					  			(서버 입장에서는(애플리케이션 제공자)) 서버 컴퓨터가 꺼질 때
 		 					  			(예: 네이버 로그인 유지 )
 		 					  			
 		HttpSession session = req.getSession(boolean값);
 		boolean값 : true인 경우 => 세션 객체가 존재하지 않으면 새로 생성하여 반환한다.
 				   false인 경우 => 세션 객체가 존재하지 않으면 null을 리턴한다. 
 				   				존재 여부만 확인하고 싶을 때도 사용
 				   
 		- 세션 객체를 삭제처리 하는 방법
 		// 삭제 처리를 할 때 -> 세션이 종료될 때 (브라우저를 닫을 때, 로그아웃을 할 때 등등)
 		 * 
 		1. invaildate() 메서드 이용하기 
 			-> 멀쩡한 세션을 유효하지 않게(무효하게) 만드는 메서드
 			
 		2. setMaxInactiveInterval(int interval) 메서드 이용하기 => (초단위)
 			-> 최대 비활성화 시간 간격을 설정해주는 메서드, 
 			       파라미터로 넣은 시간 동안 조작을 하지 않으면 (세션을 쓰지 않으면) 비활성화시킴
 			       
 		3. web.xml 파일에 <session-config> 설정 이용하기 => (분단위)
 			-> 위 두 방법과 다르게 애플리케이션 설정 파일을 통해서 모든 세션에 적용을 함
 			       위  두 방법은 특정 사용자에게 적용을 하는 방식
 			       전체 사용자에게 적용을 할 때는 해당 방법을 사용 
 */
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 세션 객체를 가져오는데 없으면 새로 생성한다.
		HttpSession httpSession = req.getSession(true);
		
		// 세션 생성시간 가져오기
		Date createTime = new Date(httpSession.getCreationTime());
		
		// 세션에 마지막 접근한 시간 가져오기
		Date lastAccessTime = new Date(httpSession.getLastAccessedTime());
		
		String title = "재방문을 환영합니다.";
		int visitCnt = 0; // 방문 횟수
		String userId = "김연희"; // 사용자 ID
		
		if (httpSession.isNew()) { // 새로 만들어진 세션 객체인지 확인
			// 세션이 지금 처음 만들어지면(첫 방문이면) 아래 내용 출력
			title = "처음 방문을 환영합니다.";
			httpSession.setAttribute("userId", userId);
		} else {
			visitCnt = (Integer) httpSession.getAttribute("visitCnt");
			visitCnt++;
			userId = (String) httpSession.getAttribute("userId");
		}
		httpSession.setAttribute("visitCnt", visitCnt);
		
		
		
//		1. invaildate() 메서드 이용하기
		// 기존 세션을 무효화하여 쓸모 없게 만듬 -> 새로고침 할 때마다
		// 유효한 세션이 없다고 판단하여 계속 새로운 세션을 만들어서 반환함
//		httpSession.invalidate();
		
		
// 		2. setMaxInactiveInterval(int interval) 메서드 이용하기
		// 파라미터로 넣은 시간 동안 조작을 하지 않으면 (세션을 쓰지 않으면) 비활성화하고,
		// 새로운 세션을 만들어서 반환한다. 초단위
//		httpSession.setMaxInactiveInterval(10);
		
		
		
		
		
		
		
		///////////////////////////////////////////////////////////
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		
		PrintWriter out = resp.getWriter();
		out.print("<!DOCTYPE html><head><title>" + title + "</title><head>"
				+ "<body><h1 align=\"center\">" + title + "</h1>"
				+ "<h2 align=\"center\">세션 정보</h2>"
				+ "<table border=\"1\" align=\"center\">"
				+ "<tr bgcolor=\"orange\"><th>구분</th><th>값</th><tr>"
				+ "<tr><td>세션ID</td><td>" + httpSession.getId() + "</td></tr>"
				+ "<tr><td>생성시간</td><td>" + createTime + "</td></tr>"
				+ "<tr><td>마지막 접근시간</td><td>" + lastAccessTime + "</td></tr>"
				+ "<tr><td>사용자ID</td><td>" + userId + "</td></tr>"
				+ "<tr><td>방문횟수</td><td>" + visitCnt + "</td></tr>"
				+ "</table></body></html>");
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
