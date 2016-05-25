package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.User;
import service.UserService;

@WebServlet(urlPatterns = {"/freeze"})
public class FreezeServlet extends HttpServlet{
	private static final long serialVersionUD = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		int id = Integer.valueOf(request.getParameter("id"));
		UserService getUser = new UserService();
		User editUser = getUser.getEditedUser(id);
		new UserService().updateFreeze(editUser,id);

		response.sendRedirect("userManagement");
	}
}
