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

@WebFilter(urlPatterns = {"/newThread", "/home", "/home.jsp", "/index.jsp", "/userManagement"})
public class LoginCheckFilter implements Filter{

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{


		List<String> messages = new ArrayList<>();
        HttpSession session = ((HttpServletRequest)request).getSession();
		if(session.getAttribute("loginUser") == null){
			messages.add("ログインしてください");
			request.setAttribute("errorMessages", messages);
			request.getRequestDispatcher("/login").forward(request, response);
			return;
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
