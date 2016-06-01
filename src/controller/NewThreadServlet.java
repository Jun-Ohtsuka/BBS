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

import beans.Thread;
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

		int inputCategory = 0;

		if(submit != null){
			if(submit.equals("自由入力")){
				inputCategory = 1;
			}else if(submit.equals("一覧から選択")){
				inputCategory = 0;
			}
		}else{
			if(session.getAttribute("inputCategory") != null){
				String categoryNum = session.getAttribute("inputCategory").toString();
				inputCategory = Integer.valueOf(categoryNum);
			}
		}

		ThreadDao getCategory = new ThreadDao();
		List<String> categorys = getCategory.getCategory();
		session.setAttribute("categorys", categorys);
		session.setAttribute("inputCategory", inputCategory);

		request.getRequestDispatcher("/newThread.jsp").forward(request, response);
		session.removeAttribute("editThread");
		session.removeAttribute("inputCategory");
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();

		Thread editThread = getEditThread(request);
		int inputCategory = Integer.valueOf(request.getParameter("inputCategory"));
		session.setAttribute("inputCategory", inputCategory);

		List<String> messages = new ArrayList<>();
		if(isValid(request, messages) == true){
			User user = (User) session.getAttribute("loginUser");
			Thread thread = new Thread();
			thread.setText(request.getParameter("text"));
			thread.setTitle(request.getParameter("title"));
			thread.setCategory(request.getParameter("category"));
			thread.setUserId(user.getId());

			new ThreadService().register(thread);
			messages.add("投稿に成功しました");

			session.setAttribute("messages", messages);
			response.sendRedirect("home");
		}else{
			session.setAttribute("messages", messages);
			session.setAttribute("editThread", editThread);
			response.sendRedirect("newThread");
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages){
		String text = request.getParameter("text");
		String title = request.getParameter("title");
		String category = request.getParameter("category");

		if(StringUtils.isEmpty(title)){
			messages.add("件名を入力してください");
		}else if(title.length() > 50){
			messages.add("件名は50文字以下で入力してください");
		}

		if(StringUtils.isEmpty(category)){
			messages.add("カテゴリーを入力してください");
		}else if(category.length() > 10){
			messages.add("カテゴリーは10文字以下で入力してください");
		}

		if(StringUtils.isEmpty(text)){
			messages.add("本文を入力してください");
		}else if(text.length() > 1000){
			messages.add("本文は1000文字以下で入力してください");
		}

		if(messages.size() == 0){
			return true;
		}else{
			return false;
		}
	}

	private Thread getEditThread(HttpServletRequest request) throws IOException, ServletException{
		Thread editThread = new Thread();

		editThread.setTitle(request.getParameter("Titlle"));
		editThread.setCategory(request.getParameter("category"));
		editThread.setText(request.getParameter("text"));

		return editThread;
	}

}
