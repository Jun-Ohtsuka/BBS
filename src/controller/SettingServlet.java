package controller;

import static utils.DBUtil.*;

import java.io.IOException;
import java.sql.Connection;
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
import dao.UserDao;
import service.BranchService;
import service.PositionService;
import service.UserService;

@WebServlet(urlPatterns = {"/setting"})
public class SettingServlet extends HttpServlet{
	private static final long serialVersionUD = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();

		int id = 0;
		List<String> messages = new ArrayList<>();

		if(request.getParameter("id") == null && session.getAttribute("id") == null){
			messages.add("無効な入力です");
			session.setAttribute("messages", messages);
			response.sendRedirect("userManagement");
			return;
		}else if(request.getParameter("id") != null){
			if(request.getParameter("id").matches("^[0-9]{1,10}$")){
				id = Integer.valueOf(request.getParameter("id"));
			}else{
				messages.add("無効なIDが入力されました");
				session.setAttribute("messages", messages);
				response.sendRedirect("userManagement");
				return;
			}
		}else{
			String sessionId = session.getAttribute("id").toString();
			id = new Integer(sessionId).intValue();
		}
		User editedUser = getEditedUser(request, id);

		if(editedUser == null){
			messages.add("指定されたIDは存在しません");
			session.setAttribute("messages", messages);
			response.sendRedirect("userManagement");
			return;
		}

		PositionService getPosition = new PositionService();
		List<String> positions = getPosition.getPosition();

		BranchService getBranch = new BranchService();
		List<String> branchs = getBranch.getBranch();

		session.setAttribute("editedUser", editedUser);
		session.setAttribute("positions", positions);
		session.setAttribute("branchs", branchs);
		request.getRequestDispatcher("/setting.jsp").forward(request, response);
		session.removeAttribute("editUser");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		List<String> messages = new ArrayList<>();
		HttpSession session = request.getSession();

		User editUser = getEditUser(request);
		int id = Integer.valueOf(request.getParameter("id"));

		if(isValid(request, messages, id) == true){
			new UserService().update(editUser, id);
			messages.add("ユーザー情報の編集が正常に完了しました");

			session.setAttribute("messages", messages);
			response.sendRedirect("userManagement");
		}else{
			session.setAttribute("editUser", editUser);
			session.setAttribute("messages", messages);
			session.setAttribute("id", id);
			response.sendRedirect("setting");
		}
	}


	private User getEditUser(HttpServletRequest request) throws IOException, ServletException{
		User editUser = new User();

		editUser.setName(request.getParameter("name"));
		editUser.setAccount(request.getParameter("account"));
		editUser.setPassword(request.getParameter("password"));
		editUser.setBranchId(Integer.valueOf(request.getParameter("branch")));
		editUser.setPositionId(Integer.valueOf(request.getParameter("position")));

		return editUser;
	}

	private User getEditedUser(HttpServletRequest request, int id) throws IOException, ServletException{
		UserService getUser = new UserService();
		User editedUser = getUser.getEditedUser(id);

		return editedUser;
	}

	private boolean isValid(HttpServletRequest request, List<String> messages, int id){

		String name = request.getParameter("name");
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		String checkPassword = request.getParameter("checkPassword");
		int branch = Integer.valueOf(request.getParameter("branch"));
		int position= Integer.valueOf(request.getParameter("position"));

		if(StringUtils.isEmpty(name) == true){
			messages.add("名前を入力してください");
		}
		if(name.length() > 10){
			messages.add("名前は10文字以下で入力してください");
		}
		if(StringUtils.isEmpty(account) == true){
			messages.add("ログインIDを入力してください");
		}
		if(account.matches("^\\p{Alnum}{6,20}$") == false){
			messages.add("ログインIDは半角英数字6文字以上20文字以内で入力してください");
		}
		if(password.isEmpty() == false){
			if(password.matches("^[\\p{Alnum}!-~]{6,255}$") == false){
				messages.add("パスワードは半角文字6文字以上255文字以内で入力してください");
			}
		}
		if(password.equals(checkPassword) == false){
			messages.add("パスワードが一致しません");
		}

		if((branch == 1) && (position != 1) && (position != 2)){
			messages.add("所属が「本店」の時に選択できる項目は「総務人事担当」か「情報管理担当」だけです");
		}
		if((branch == 2) && (position != 3) && (position != 4)){
			messages.add("所属が「支店A」の時に選択できる項目は「支店長」か「社員」だけです");
		}
		if((branch == 3) && (position != 3) && (position != 4)){
			messages.add("所属が「支店B」の時に選択できる項目は「支店長」か「社員」だけです");
		}
		if((branch == 4) && (position != 3) && (position != 4)){
			messages.add("所属が「支店C」の時に選択できる項目は「支店長」か「社員」だけです");
		}

		Connection connection = null;
		connection = getConnection();

		UserDao userDao = new UserDao();
		boolean checkAccount = userDao.checkEditAccount(connection, account, id);

		if(checkAccount == false){
			messages.add("入力されたログインIDは既に使用されています");
		}


		if(messages.size() == 0){
			return true;
		}else{
			return false;
		}
	}
}