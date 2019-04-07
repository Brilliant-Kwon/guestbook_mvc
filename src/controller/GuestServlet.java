package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.GuestDao;
import dao.GuestDaoImpl;
import vo.GuestVo;

@WebServlet("/")
public class GuestServlet extends BaseServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		ServletContext context = req.getServletContext();
		//모든 서블릿을 관리하는 서블릿 컨텍스트를 가져와 변수로 정의

		String HomePath = req.getContextPath();
		//기본 경로를 변수로 지정

		String dbUser = context.getInitParameter("dbUser");
		String dbPass = context.getInitParameter("dbPass");
		//DB계정을 web.xml에 파라미터로 지정해놓았고
		//그것을 문자열로 받아옴.

		GuestDao dao = new GuestDaoImpl(dbUser, dbPass);
		//Dao를 사용하기 위해 DaoImpl 객체 생성

		List<GuestVo> list = dao.getList();
		//방명록 목록을 띄우기 위해 list 생성
		//getList 메서드를 이용해 리스트 받아와 넣어줌.

		req.setAttribute("HomePath", HomePath);
		req.setAttribute("list",list);

		RequestDispatcher rd = req.getServletContext().getRequestDispatcher("/WEB-INF/views/index.jsp");
		rd.forward(req,resp);
		
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		String action = req.getParameter("do");
		
		if(action.equals("write")) {
			//요청 부분 인코딩을 해줌

			ServletContext context = req.getServletContext();
			String dbUser = context.getInitParameter("dbUser");
			String dbPass = context.getInitParameter("dbPass");

			String name = req.getParameter("name");
			String password = req.getParameter("password");
			String content = req.getParameter("content");
			//파라미터를 요청해서 각각 변수로 만들어줌

			GuestDao dao = new GuestDaoImpl(dbUser, dbPass);
			GuestVo vo = new GuestVo(name, password, content);
			boolean success = dao.write(vo);

			String HomePath = req.getContextPath();
			req.setAttribute("HomePath", HomePath);

			if (success) {
				//추가 성공시 
				System.out.println("추가 성공");
				//콘솔에 성공했다고 출력
				resp.sendRedirect(HomePath + "/");
				//리스트 화면으로 돌려보냄
			} else {
				System.out.println("추가 실패");
				resp.sendRedirect(HomePath + "/");
			}
		}else if(action.equals("deleteform")) {
			String no = req.getParameter("no");//삭제할 때 no를 기준으로 찾아야함
			String password = req.getParameter("password");//비밀번호를 가져와서 비교할 것임
			
			req.setAttribute("no", no);
			req.setAttribute("password", password);

			System.out.println(no+", "+password);
			
			RequestDispatcher rd = req.getServletContext().getRequestDispatcher("/WEB-INF/views/deleteform.jsp");
			rd.forward(req,resp);
		}else if(action.equals("delete")) {
			ServletContext context = req.getServletContext();
		    String dbUser = context.getInitParameter("dbUser");
		    String dbPass = context.getInitParameter("dbPass");

		    String no = req.getParameter("no");
		    String password = req.getParameter("password");
		    String inpass = req.getParameter("inpass");
		    
		    System.out.println("삭제 파라미터 : "+no+","+password+","+inpass);

		    GuestDao dao = new GuestDaoImpl(dbUser, dbPass);
		    
		    String HomePath = req.getContextPath();
			req.setAttribute("HomePath", HomePath);
		    
		    if (password.equals(inpass)) {
		    	//입력한 비밀번호가 해당 방명록 비밀번호와 같을 때 삭제 수행
		        boolean success = dao.delete(Long.valueOf(no));
		    	//삭제 결과를 success 에 담음
				
		        if (success) {
		            System.out.println("삭제 성공");
		            resp.sendRedirect(HomePath + "/");
		        } else {
		            System.out.println("삭제 실패");
		            resp.sendRedirect(HomePath + "/");
		        }
		    }
		}
	}
}
