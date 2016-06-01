package controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Thread;
import beans.User;
import beans.UserComment;
import beans.UserThread;
import dao.ThreadDao;
import service.CommentService;
import service.ThreadService;

@WebServlet(urlPatterns = {"/index.jsp","/home"})
public class HomeServlet extends HttpServlet{
	private static final long serialVersionUD = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		String category = request.getParameter("category");
		String startYear = request.getParameter("startYear");
		String startMonth = request.getParameter("startMonth");
		String startDay = request.getParameter("startDay");
		String endYear = request.getParameter("endYear");
		String endMonth = request.getParameter("endMonth");
		String endDay = request.getParameter("endDay");
		String submit = request.getParameter("submit");
		List<String> messages = new ArrayList<>();
		HttpSession session = request.getSession();

		String[] threadStartTime = new ThreadService().getThreadStartTime();
		String[] threadEndTime = new ThreadService().getThreadEndTime();

		if(submit == null || submit.equals("日付リセット")){
			startYear = threadStartTime[0];
			startMonth = threadStartTime[1];
			startDay = threadStartTime[2];
			endYear = threadEndTime[0];
			endMonth = threadEndTime[1];
			endDay = threadEndTime[2];
		}

		String startTime = startYear + "-" + startMonth + "-" + startDay;
		int day = Integer.valueOf(endDay) + 1;
		String endTime = endYear + "-" + endMonth + "-" + day;
		SimpleDateFormat formatDay = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date startTimeDate = formatDay.parse(startTime);
			Date endTimeDate = formatDay.parse(endTime);
			long dateStartTime = startTimeDate.getTime();
			long dateEndTime = endTimeDate.getTime();

			if(dateStartTime >= dateEndTime){
				messages.add("開始日時が終了日時より大きい日にちを設定されました。日時検索を初期値で表示します");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if(isTimeValid(messages, startYear, startMonth, startDay, endYear, endMonth, endDay) == false ){
			startYear = threadStartTime[0];
			startMonth = threadStartTime[1];
			startDay = threadStartTime[2];
			endYear = threadEndTime[0];
			endMonth = threadEndTime[1];
			endDay = threadEndTime[2];
			session.setAttribute("messages", messages);
		}

		ThreadDao getCategory = new ThreadDao();
		List<String> categorys = getCategory.getCategory();

		List<UserThread> searchThread = null;
		searchThread = new ThreadService().getThread(category,startYear,startMonth,startDay,endYear,endMonth,endDay);

		List<UserComment> userComments = new CommentService().getUserComment();


		session.setAttribute("checkStartYear", startYear);
		session.setAttribute("checkStartMonth", startMonth);
		session.setAttribute("checkStartDay", startDay);
		session.setAttribute("checkEndYear", endYear);
		session.setAttribute("checkEndMonth", endMonth);
		session.setAttribute("checkEndDay", endDay);
		session.setAttribute("categorys", categorys);
		session.setAttribute("categorySelect", category);
		request.setAttribute("threads", searchThread);
		request.setAttribute("userComments", userComments);
		request.getRequestDispatcher("/home.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		HttpSession session = request.getSession();
		List<String> messages = new ArrayList<>();
		User user = (User) request.getSession().getAttribute("loginUser");

		int threadId = Integer.valueOf(request.getParameter("thread_id"));
		String comment = request.getParameter("comment");

		if(isValid(request, messages, user, comment) == true){
			Thread thread = new Thread();
			thread.setText(request.getParameter("comment"));
			thread.setUserId(user.getId());
			new CommentService().registerComment(thread, threadId);
			messages.add("コメントを投稿しました");
			session.setAttribute("messages", messages);
			response.sendRedirect("./");
		}else{
			session.setAttribute("messages", messages);
			response.sendRedirect("./");
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages, User user, String comment){
		comment = request.getParameter("comment");

		if(comment.equals("コメント")){
			messages.add("コメントを入力してください");
		}else if(comment.length() > 500){
			messages.add("コメントは500文字以下で入力してください");
		}

		if(messages.size() == 0){
			return true;
		}else{
			return false;
		}
	}

	private boolean isTimeValid(List<String> messages, String startYear, String startMonth, String startDay,
			String endYear, String endMonth, String endDay){

		int checkStartYear = Integer.valueOf(startYear);

		if((checkStartYear % 4 == 0) && (checkStartYear % 100 != 0) || (checkStartYear % 400 == 0)){
			if(startMonth.equals("2") && (Integer.valueOf(startDay) > 29)){
				messages.add("うるう年なので日付は29日までしか選択できません");
			}
		}else{
			if(startMonth.equals("2") && (Integer.valueOf(startDay) > 28)){
				messages.add("2月を選択した場合、日付は28日までしか選択できません");
			}
		}
		if(startMonth.equals("4") || startMonth.equals("6") || startMonth.equals("9") || startMonth.equals("11") ){
			if(Integer.valueOf(startDay) > 30){
				messages.add("4,6,9,11月を選択した場合、日付は30日までしか選択できません");
			}
		}


		int checkEndYear = Integer.valueOf(endYear);

		if((checkEndYear % 4 == 0) && (checkEndYear % 100 != 0) || (checkEndYear % 400 == 0)){
			if(endMonth.equals("2") && (Integer.valueOf(endDay) > 29)){
				messages.add("うるう年なので日付は29日までしか選択できません");
			}
		}else{
			if(endMonth.equals("2") && (Integer.valueOf(endDay) > 28)){
				messages.add("2月を選択した場合、日付は28日までしか選択できません");
			}
		}
		if(endMonth.equals("4") || endMonth.equals("6") || endMonth.equals("9") || endMonth.equals("11") ){
			if(Integer.valueOf(endDay) > 30){
				messages.add("4,6,9,11月を選択した場合、日付は30日までしか選択できません");
			}
		}

		if(messages.size() == 0){
			return true;
		}else{
			return false;
		}
	}
}
