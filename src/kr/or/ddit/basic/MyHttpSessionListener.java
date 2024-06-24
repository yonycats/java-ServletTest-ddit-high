package kr.or.ddit.basic;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class MyHttpSessionListener implements HttpSessionAttributeListener, HttpSessionListener {

	// 이벤트 리스너 만들어보기
	// 속성 추가 이벤트, 속성 삭제 이벤트, 속성  변경 이벤트, 세션 생성, 세션 삭제 이벤트 발생
	
	@Override
	public void attributeAdded(HttpSessionBindingEvent hsbe) {
		System.out.println("[MyHttpSessionListener] attributeAdded 호출됨 : " 
						+ hsbe.getName() + "=" + hsbe.getValue() + "추가됨");
		
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent hsbe) {
		System.out.println("[MyHttpSessionListener] attributeRemoved 호출됨 : " 
						+ hsbe.getName() + "삭제됨");
		
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent hsbe) {
		System.out.println("[MyHttpSessionListener] attributeReplaced 호출됨 : " 
						+ hsbe.getName() + "=" + hsbe.getValue() + "변경됨");
		
	}

	// 세션이 생성되었을 때 호출됨.
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		System.out.println("[MyHttpSessionListener] sessionCreated 호출됨");
		
	}

	// 세션이 제거되었을 때 호출됨.
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		System.out.println("[MyHttpSessionListener] sessionDestroyed 호출됨");
		
	}

}
