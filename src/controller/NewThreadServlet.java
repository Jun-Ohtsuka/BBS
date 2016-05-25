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

import beans.Message;
import beans.User;
import dao.ThreadDao;
import service.ThreadService;

@WebServlet(urlPatterns = {"/newThread"})
public class NewThreadServlet extends HttpServlet{
	private static final long serialVersionUD = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		HttpSession session = request.getSession();
		String submit = request.getParameter("submit");
		String category = request.getParameter("inputCategory");
		int inputCategory = 0;

		if(category != null){
			inputCategory = Integer.valueOf(request.getParameter("inputCategory"));
		}
		if(submit != null){
			if(submit.equals("自由入力")){
				inputCategory = 1;
			}else if(submit.equals("一覧から選択")){
				inputCategory = 0;
			}
		}

		ThreadDao getCategory = new ThreadDao();
		List<String> categorys = getCategory.getCategory();
		session.setAttribute("categorys", categorys);
		session.setAttribute("inputCategory", inputCategory);

		request.getRequestDispatcher("/newThread.jsp").forward(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		HttpSession session = request.getSession();

		List<String> messages = new ArrayList<>();
		if(isValid(request, messages) == true){
			User user = (User) session.getAttribute("loginUser");
			Message message = new Message();
			message.setText(request.getParameter("text"));
			message.setTitle(request.getParameter("title"));
			message.setCategory(request.getParameter("category"));
			message.setUserId(user.getId());

			new ThreadService().register(message);

			response.sendRedirect("./");
		}else{
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("newThread");
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages){
		String text = request.getParameter("text");
		String title = request.getParameter("title");
		String category = request.getParameter("category");

		if(StringUtils.isEmpty(title) == true){
			messages.add("件名を入力してください。");
		}else if(title.length() > 50){
			messages.add("件名は50文字以下で入力してください。");
		}

		if(StringUtils.isEmpty(category) == true){
			messages.add("カテゴリーを入力してください");
		}else if(category.length() > 10){
			messages.add("カテゴリーは10文字以下で入力してください");
		}

		if(StringUtils.isEmpty(text) == true){
			messages.add("本文を入力してください。");
		}else if(text.length() > 1000){
			messages.add("本文は1000文字以下で入力してください。");
		}

		if(messages.size() == 0){
			return true;
		}else{
			return false;
		}
	}
}
