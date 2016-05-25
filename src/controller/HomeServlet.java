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

import beans.Message;
import beans.User;
import beans.UserComment;
import beans.UserThread;
import dao.ThreadDao;
import service.CommentService;
import service.ThreadService;
import service.UserService;

@WebServlet(urlPatterns = {"/index.jsp","/home"})
public class HomeServlet extends HttpServlet{
	private static final long serialVersionUD = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		HttpSession session = request.getSession();
		List<String> messages = new ArrayList<>();

		ThreadDao getCategory = new ThreadDao();
		List<String> categorys = getCategory.getCategory();

		List<UserComment> userComments = new CommentService().getUserComment();
		String submit = request.getParameter("submit");

		String category = request.getParameter("category");
		String startYear = request.getParameter("startYear");
		String startMonth = request.getParameter("startMonth");
		String startDay = request.getParameter("startDay");
		String endYear = request.getParameter("endYear");
		String endMonth = request.getParameter("endMonth");
		String endDay = request.getParameter("endDay");
		List<UserThread> searchThread = null;

		searchThread = new ThreadService().getThread(category,startYear,startMonth,startDay,endYear,endMonth,endDay);
		if(isTimeValid(messages, startMonth, startDay, endMonth, endDay) == false){
			session.setAttribute("errorMessages", messages);
		}

		if(submit != null && submit.equals("日付リセット")){
			startYear = "2016";
			startMonth = "1";
			startDay = "1";
			endYear = null;
			endMonth = null;
			endDay = null;
		}

		request.setAttribute("messages", searchThread);
		request.setAttribute("checkStartYear", startYear);
		request.setAttribute("checkStartMonth", startMonth);
		request.setAttribute("checkStartDay", startDay);
		request.setAttribute("checkEndYear", endYear);
		request.setAttribute("checkEndMonth", endMonth);
		request.setAttribute("checkEndDay", endDay);
		request.setAttribute("categorys", categorys);
		request.setAttribute("categorySelect", category);
		request.setAttribute("userComments", userComments);
		request.getRequestDispatcher("/home.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		HttpSession session = request.getSession();
		List<String> messages = new ArrayList<>();
		User user = (User) request.getSession().getAttribute("loginUser");

		String submit = request.getParameter("submit");
		int threadId = Integer.valueOf(request.getParameter("thread_id"));

		if(isValid(request, messages, user) == true){
			if(submit.equals("コメントする")){
				Message message = new Message();
				message.setText(request.getParameter("comment"));
				message.setUserId(user.getId());
				new CommentService().registerComment(message, threadId);
				response.sendRedirect("./");
			}else if(submit.equals("この投稿を削除する")){
				ThreadService deleteThread = new ThreadService();
				deleteThread.deleteThread(threadId);
				response.sendRedirect("./");
			}else if(submit.equals("このコメントを削除する")){
				int commentId = Integer.valueOf(request.getParameter("comment_id"));
				CommentService deleteComment = new CommentService();
				deleteComment.deleteComment(commentId);
				response.sendRedirect("./");
			}
		}else{
			System.out.println(messages);
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("home");
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages, User user){
		String comment = request.getParameter("comment");
		String submit = request.getParameter("submit");

		if(user == null){
			return false;
		}
		if(submit.equals("コメントする")) {
			if(comment.isEmpty()){
				messages.add("コメントを入力してください");
			}else if(comment.length() > 500){
				messages.add("コメントは500文字以下で入力してください");
			}
		}


		int loginUserPosition = user.getPositionId();
		int loginUserBranch = user.getBranchId();
		int loginUserId = user.getId();

		if(submit.equals("この投稿を削除する")){
			int messageUserId = Integer.valueOf(request.getParameter("user_id"));
			User getUserBranch = new UserService().getEditedUser(messageUserId);
			int deleteUserBranchId = getUserBranch.getBranchId();

			if((loginUserId != messageUserId) && (loginUserPosition != 2) &&
					((loginUserPosition == 3) && (loginUserBranch == deleteUserBranchId)) == false) {
				messages.add("このアカウントは行われた操作の権限を所持していません");
			}
		}

		if(submit.equals("このコメントを削除する")){
			int commentUserId = Integer.valueOf(request.getParameter("user_id"));
			User getUserBranch = new UserService().getEditedUser(commentUserId);
			int deleteUserBranchId = getUserBranch.getBranchId();

			if((loginUserId != commentUserId) && (loginUserPosition != 2) &&
					((loginUserPosition == 3) && (loginUserBranch == deleteUserBranchId)) == false){
				messages.add("このアカウントは行われた操作の権限を所持していません");
			}
		}

		if(messages.size() == 0){
			return true;
		}else{
			return false;
		}
	}

	private boolean isTimeValid(List<String> messages, String startMonth, String startDay, String endMonth, String endDay){

		if((startMonth != null && startDay != null) && (startMonth.equals("") == false && startDay.equals("") == false)){
			if(startMonth.equals("2") && (Integer.valueOf(startDay) > 28)){
				messages.add("2月を選択した場合、日付は28日までしか選択できません");
			}
			if(startMonth.equals("4") || startMonth.equals("6") || startMonth.equals("9") || startMonth.equals("11") ){
				if(Integer.valueOf(startDay) > 30){
					messages.add("4,6,9,11月を選択した場合、日付は30日までしか選択できません");
				}
			}
		}

		if((endMonth != null && endDay != null) && (endMonth.equals("") == false && endDay.equals("") == false)){
			if(endMonth.equals("2") && (Integer.valueOf(endDay) > 28)){
				messages.add("2月を選択した場合、日付は28日までしか選択できません");
			}
			if(endMonth.equals("4") || endMonth.equals("6") || endMonth.equals("9") || endMonth.equals("11") ){
				if(Integer.valueOf(endDay) > 30){
					messages.add("4,6,9,11月を選択した場合、日付は30日までしか選択できません");
				}
			}
		}

		if(messages.size() == 0){
			return true;
		}else{
			return false;
		}
	}
}
