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

import org.apache.commons.lang.StringUtils;

import beans.User;
import service.LoginService;

@WebServlet(urlPatterns = {"/signin"})
public class SignInServlet extends HttpServlet{
	private static final long serialVersionUD = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.getRequestDispatcher("/signin.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		List<String> messages = new ArrayList<>();
		HttpSession session = request.getSession();
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		User editUser = new User();
		editUser.setAccount(account);
		session.setAttribute("editUser", editUser);

		if(StringUtils.isEmpty(account)){
			messages.add("ログインIDを入力してください");
		}
		if(StringUtils.isEmpty(password)){
			messages.add("パスワードを入力してください");
		}

		if(messages.size() <= 0){
			LoginService loginService = new LoginService();
			User user = loginService.login(account, password);

			if(user != null){
				session.setAttribute("loginUser", user);
				response.sendRedirect("./");
				session.removeAttribute("editUser");
			}else{
				messages.add("ログインに失敗しました");
				session.setAttribute("messages", messages);
				response.sendRedirect("signin");
			}
		}else{
			session.setAttribute("messages", messages);
			response.sendRedirect("signin");
		}
	}
}