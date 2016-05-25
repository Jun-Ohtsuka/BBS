package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.User;
import beans.UserView;
import service.LoginService;
import service.UserService;

@WebServlet(urlPatterns = {"/userManagement"})
public class UserManagementServlet extends HttpServlet{
	private static final long serialVersionUD = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		User user = (User) request.getSession().getAttribute("loginUser");

		String account = user.getAccount();
		LoginService loginService = new LoginService();
		User changeUser = loginService.changeUserLogin(account);
		HttpSession session = request.getSession();
		List<String> messages = new ArrayList<>();

		if(session.getAttribute("loginUser") == null){
			messages.add("ログインしてください。");
			session.setAttribute("errorMessages", messages);
			request.getRequestDispatcher("/login").forward(request, response);
			return;
		}

		if(changeUser.getFreeze() == 1){
			messages.add("アカウントは停止されています");
			session.removeAttribute("loginUser");
			session.setAttribute("errorMessages", messages);
			request.getRequestDispatcher("/login").forward(request, response);
			return;
		}

		int loginUserPosition = user.getPositionId();

		if(loginUserPosition != 1){
			messages.add("このアカウントは行われた操作の権限を所持していません");
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("./");
			return;
		}

		List<UserView> users = new UserService().getUserView();

		request.setAttribute("users", users);

		request.getRequestDispatcher("/userManagement.jsp").forward(request, response);
	}
}
