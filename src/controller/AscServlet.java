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

@WebServlet("/asc")
public class AscServlet extends BaseServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		ServletContext context = req.getServletContext();
		//모든 서블릿을 관리하는 서블릿 컨텍스트를 가져와 변수로 정의

		String action = req.getParameter("style");

		String HomePath = req.getContextPath();
		//기본 경로를 변수로 지정

		String dbUser = context.getInitParameter("dbUser");
		String dbPass = context.getInitParameter("dbPass");
		//DB계정을 web.xml에 파라미터로 지정해놓았고
		//그것을 문자열로 받아옴.

		GuestDao dao = new GuestDaoImpl(dbUser, dbPass);
		//Dao를 사용하기 위해 DaoImpl 객체 생성

		List<GuestVo> list = dao.getListAsc();
		//방명록 목록을 띄우기 위해 list 생성
		//getList 메서드를 이용해 리스트 받아와 넣어줌.

		req.setAttribute("HomePath", HomePath);
		req.setAttribute("list",list);

		RequestDispatcher rd = req.getServletContext().getRequestDispatcher("/WEB-INF/views/asc.jsp");
		rd.forward(req,resp);

	}

}
