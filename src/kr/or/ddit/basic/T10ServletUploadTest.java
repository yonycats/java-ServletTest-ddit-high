package kr.or.ddit.basic;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/*
 * 서블릿 3부터 지원하는 part 인터페이스를 이용한 파일 업로드 예제
 */
//@MultipartConfig

//바이트 단위로 알려주면 됨, 안써도 됨, 안쓰면 제한을 안두겠다는 의미
// 1024*1024*3 -> 3메가바이트를 의미 (1024를 곱하면 한 단위씩 올라감)
@MultipartConfig(
		// fileSizeThreshold : 임계/한계치, 메모리를 아끼기 위한 메모리의 크기를 지정해주는 목적으로 사용
		fileSizeThreshold = 1024*1024*3,  // 메모리 임계크기 (이 크기가 넘어가면 레파지토리 위치에 임시파일로 저장됨)
		// 60메가짜리 첨부 불가 -> 최대 크기를 넘어가면 예외(Exception)가 발생함
		maxFileSize = 1024*1024*40, 	  // 파일 1개당 최대 크기
		// 20메가짜리 두개를 첨부하면 가능, 40메가/30메가를 첨부하면 안됨 -> 1회 첨부용량 초과
		maxRequestSize = 1024*1024*50	  // 요청파일 최대 크기
		) 

@WebServlet("/uploadTest")
public class T10ServletUploadTest extends HttpServlet {
	// get으로 파일 업로드 하려는 놈은 이상한 놈이기 때문에 신경 안써도 됨 
	
	// 업로드 폴더용 변수 만든 것임
	private static final String UPLOAD_DIR = "upload_files";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		System.out.println("=========================================================");
//		
//		// 인풋스트림을 문자기반으로 바꿔주기 위해 InputStreamReader로 감싸고 이걸 다시 효율성(성능향상)을 위해 BufferedReader로 변환함
//		// BufferedReader를 쓰지 않으면 1바이트씩 보내는데, BufferedReader를 쓰면 해당 객체에 모았다가 한번에 보내기 때문에 효율성(성능)이 더 좋음
//		BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
//		
//		String readLine = "";
//		
//		// 인풋스트림에 내용이 있으면 출력함
//		
//		// 예시) multiPartServlet=KakaoTalk_20240614_120114738.jpg&sender=apple 출력
//		while ( (readLine = br.readLine()) != null ) {
//			System.out.println(readLine);
//		}
//		System.out.println("=========================================================");
		
		// 한글이 바디 안에 들어있으면 오류 나지 않도록 인코딩하도록 세팅
		req.setCharacterEncoding("UTF-8");
		
		// Multipart 파싱전 파라미터 정보 가져오기
		System.out.println("Multipart 파싱 전 sender : " + req.getParameter("sender"));
		
		// getRealPath에 줄이 그어지는 이유는 권장되지 않는 사용이라서 가급적 지양하라고 알리는 용도임, 다른 방식으로 접근할 것
//		String uploadPath = req.getRealPath("/") + File.separator + UPLOAD_DIR;
		
		// getRealPath : 실제로 저장할 위치
		// File.separator : /를 뜻함, 구분자 역할
		// 보통 /를 경로 사이에 적어서 코딩하지만 경로 사이에 있는 구분자는 운영체제마다 조금씩 다르기 때문에 적절하지 않은 방식이므로
		// File.separator로 파일 구분자를 삽입
//		String uploadPath = req.getServletContext().getRealPath("/") + File.separator + UPLOAD_DIR;
		String uploadPath = req.getServletContext().getRealPath("/") + UPLOAD_DIR; // 슬래시 2개가 나와서 구분자 지움
		
		File uploadDir = new File(uploadPath);
		
		// 해당 파일이 없으면 파일을 만듬
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}
		
		try {
			String fileName = "";
			
			// 서블렛3부터는 .getParts() 메소드를 사용해서 파트로 나누어서 접근할 수 있음
			// 실행 --> Multipart 파싱 전 sender : null -> null로 뜸, 정보가 멀티파트로 오고 있다는 것을 알려줘야함.
			
			// 클래스 위에 @MultipartConfig 를 붙이면 해당 클래스는 멀티파트를 처리하기 위한 클래스가 되는 것임
			// 실행 --> Multipart 파싱 전 sender : apple
			//		  form-data; name="multiPartServlet"; filename="고양이 아이콘.png"
			// 	      form-data; name="sender"
			for (Part part : req.getParts()) {
				System.out.println(part.getHeader("Content-Disposition")); // Content-Disposition(내용 배치/분산)
				// 파일명을 가져올 수 있도록 메서드를 만들어서 쓰는 작업을 할 것임
//				fileName = getFileName(part);
				
				// getFileName -> 구조와 내용을 알고 싶어서 메서드를 직접 만들어서 작성해본 것이고
				// getSubmittedFileName() 메서드를 사용하면 똑같은 기능을 한다.
				fileName = part.getSubmittedFileName();
				
				// 파일이름이 null이 아니거나 파일이름이 무제면(파일을 넣지 않고 업로드 했을 때)
				// 즉, 파일을 정상적으로 업로드 했을 때
				if (fileName != null && !fileName.equals("")) {
					// 파일을 어디에 저장할 것인지 경로 지정해주기
					// part.write(파일경로 + 구분자 + 파일명);
					// part.write() : 파일 업로드를 하는 가장 핵심적인 메서드
					part.write(uploadPath + File.separator + fileName); 
					
					System.out.println("업로드 처리완료");
					
					// 실제 업로드되는 경로 출력해보기
					System.out.println(uploadPath + File.separator + fileName);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	// 파일명을 리턴하는 메서드 만들기
	/**
	 * Part객체를 파싱하여 파일이름 추출하기
	 * @param part 파일이름 파싱할 part객체
	 * @return 파일명 : 파일명이 존재하지 않으면 NULL값 리턴함
	 */
	private String getFileName(Part part) {
		// 반환값 형태 참고용
		// Content-Disposition(내용 배치/분산) : form-data; name="multiPartServlet"; filename="고양이 아이콘.png"
		
		// filename이 존재하지 않으면 null을 반환
		// 컨텐트의 filename으로 시작한다면, 파일이름을 =부터 서브스트링을 해라 -> "고양이 아이콘.png"
		// 그리고 "를 제거시킴 -> 고양이 아이콘.png 
		// 파일이름만 반환시킴
		for (String content : part.getHeader("Content-Disposition").split(";")) {
			// filename부터 읽기 시작
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf("=") + 1).trim().replace("\"", "");				
			}
		}
		return null;
	}
}
