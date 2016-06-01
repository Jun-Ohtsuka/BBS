package filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.User;
import service.LoginService;

@WebFilter("/*")
public class FreezeCheckFilter implements Filter{

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{

		List<String> messages = new ArrayList<>();
		HttpSession session = ((HttpServletRequest)request).getSession();

		User user = ((User)session.getAttribute("loginUser"));
		if(user != null){
			int id = user.getId();
			LoginService loginService = new LoginService();
			User changeUser = loginService.changeUserLogin(id);

			if(changeUser.getFreeze() == 1){
				messages.add("アカウントは停止されています");
				session.removeAttribute("loginUser");
				session.setAttribute("messages", messages);
				((HttpServletResponse)response).sendRedirect("signin");
				return;
			}
		}
		chain.doFilter(request, response);
	}

	public void redirectToLoginPage(HttpServletRequest request, HttpServletResponse response){

	}

	public void init(FilterConfig config){

	}

	public void destroy(){

	}

}
