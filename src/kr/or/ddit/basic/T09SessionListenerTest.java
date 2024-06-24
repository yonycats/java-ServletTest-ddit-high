package kr.or.ddit.basic;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class T09SessionListenerTest extends HttpServlet {

	// MyHttpSessionListener 이벤트 테스트용 클래스
	
	// 세션 생성 이벤트 발생, 속성 추가 이벤트, 속성 변경 이벤트, 속성  삭제 이벤트 발생시킴
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// HttpSession 객체 생성하기
		HttpSession httpSession = req.getSession();
		
		// 세션에 데이터 저장 및 수정, 삭제하기
		httpSession.setAttribute("ATTR1", "속성1");
		httpSession.setAttribute("ATTR1", "속성11");
		httpSession.setAttribute("ATTR2", "속성2");
		httpSession.removeAttribute("ATTR1");
		
		// 세션 삭제하기
		httpSession.invalidate();
		
		// 세션이 삭제되기 때문에 내부의 모든 데이터들이 삭제됨 -> 삭제를 하지 않은 ATTR2도 삭제가 된 후에 세션이 삭제된다.
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
