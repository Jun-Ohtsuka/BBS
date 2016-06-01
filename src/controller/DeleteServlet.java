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
import service.CommentService;
import service.ThreadService;

@WebServlet(urlPatterns = {"/delete"})
public class DeleteServlet extends HttpServlet{
	private static final long serialVersionUD = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.sendRedirect("./");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		HttpSession session = request.getSession();
		List<String> messages = new ArrayList<>();
		User user = (User) request.getSession().getAttribute("loginUser");

		String submit = request.getParameter("submit");
		int threadId = Integer.valueOf(request.getParameter("thread_id"));

		if(submit.equals("この投稿を削除する")){
			ThreadService deleteThread = new ThreadService();
			deleteThread.deleteThread(threadId);
			messages.add("投稿を削除しました");
		}else if(submit.equals("このコメントを削除する")){
			int commentId = Integer.valueOf(request.getParameter("comment_id"));
			CommentService deleteComment = new CommentService();
			deleteComment.deleteComment(commentId);
			messages.add("コメントを削除しました");
		}
		session.setAttribute("messages", messages);
		response.sendRedirect("./");
	}
}
