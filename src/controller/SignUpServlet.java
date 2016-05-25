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
import dao.BranchDao;
import dao.PositionDao;
import dao.UserDao;
import exception.NoRowsUpdatedRuntimeException;
import service.UserService;

@WebServlet(urlPatterns = {"/signup"})
public class SignUpServlet extends HttpServlet{
	private static final long serialVersionUD = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		PositionDao getPosition = new PositionDao();
		List<String> positions = getPosition.getPosition();

		BranchDao getBranch = new BranchDao();
		List<String> branchs = getBranch.getBranch();

		HttpSession session = request.getSession();
		session.setAttribute("positions", positions);
		session.setAttribute("branchs", branchs);
		request.getRequestDispatcher("/signup.jsp").forward(request, response);
		session.removeAttribute("editUser");
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		List<String> messages = new ArrayList<>();
		HttpSession session = request.getSession();

		User editUser = getEditUser(request);
		session.setAttribute("editUser", editUser);

		if(isValid(request, messages) == true){
			try{
				new UserService().register(editUser);
				response.sendRedirect("userManagement");
			}catch (NoRowsUpdatedRuntimeException e){
				session.removeAttribute("editUser");
				messages.add("他の人によって更新されています。最新のデータを表示しましたデータを確認してください。");
				session.setAttribute("errorMessages", messages);
				session.removeAttribute("signup");
			}
		}else{
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("signup");
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


	private boolean isValid(HttpServletRequest request, List<String> messages){
		String name = request.getParameter("name");
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		String checkPassword = request.getParameter("checkPassword");
		int branch = Integer.valueOf(request.getParameter("branch"));
		int position= Integer.valueOf(request.getParameter("position"));

		if(StringUtils.isEmpty(name) == true){
			messages.add("名前を入力してください");
		}else if(name.length() > 10){
			messages.add("名前は10文字以下で入力してください");
		}

		if(StringUtils.isEmpty(account) == true){
			messages.add("ログインIDを入力してください");
		}else if(account.matches("^\\p{Alnum}{6,20}$") == false){
			messages.add("ログインIDは半角英数字6文字以上20文字以内で入力してください");
		}

		if(StringUtils.isEmpty(password) == true){
			messages.add("パスワードを入力してください");
		}else if(password.matches("^[\\p{Alnum}!-~]{6,255}$") == false){
			messages.add("パスワードは半角文字6文字以上255文字以内で入力してください");
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
		boolean checkAccount = userDao.checkAccount(connection, account);

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
