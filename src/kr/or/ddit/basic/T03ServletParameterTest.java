package kr.or.ddit.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class T03ServletParameterTest extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		/*
		 	요청 객체로부터 파라미터 데이터 가져오는 방법
		 	
		 	- getParameter() : 파라미터 값이 한개인 경우
		 	- getParameterValues() : 파라미터 값이 여러개인 경우  ex) checkbox 등
		 	- getParameterNames() : 요청 메시지에 존재하는 모든 파라미터 이름을 가져올 때 사용함
		 */
		
		// POST 방식으로 넘어오는 Body 데이터를 가져올 때 적절한 인코딩 처리를 해야 한다.
		// 반드시 값을 꺼내오기 전에 설정해야 한다.
		// 출력의 한글 깨짐 방지
		
		// 바디에서 꺼내오는 시점에 처리할 인코딩 정보를 알리기 위함
		// 반드시 값을 꺼내오기 전에 설정해야하며, 하나라도 꺼낸 이후에는 인코딩 정보가 바뀌지 않는다.
		req.setCharacterEncoding("UTF-8");
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String gender = req.getParameter("gender");
		// getParameter 메서드이기 때문에 여러개를 받아도 값을 하나만 가져옴
		String hobby = req.getParameter("hobby"); 
		String rlgn = req.getParameter("rlgn");
		
		String[] foods = req.getParameterValues("food");
		
		////////////////////////////////////////////////
		
		// 정보를 나중에 꺼낼 때 바이트 배열을 어떻게 인코딩을 할지 알리는 용도의 인코딩 처리
		resp.setCharacterEncoding("UTF-8");
		
		// html 형식으로 출력하도록 지정, plain으로 쓰면 단순 텍스트로 출력됨
		resp.setContentType("text/html");
		
		PrintWriter out = resp.getWriter();
		
		out.println("<!DOCTYPE html><head><title>파라미터 데이터</title><head>"
				  + "<body>"
				  + "<p>username : " + username + "</p>"
				  + "<p>password : " + password + "</p>"
				  + "<p>gender : " + gender + "</p>"
				  + "<p>hobby : " + hobby + "</p>"
				  + "<p>rlgn : " + rlgn + "</p>");
		
		if (foods != null) {
			for (String food : foods) {
				out.println("<p>foods : " + food + "</p>");
			}
		}
		
		// 모든 파라미터 이름 정보 가져오기
		Enumeration<String> paramNames = req.getParameterNames();
		
		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			out.print("<p>파라미터 이름 : " + paramName + "</p>");
		}
		out.print("</body></html>");
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
