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

@WebFilter("/*")
public class URLMoveFilter implements Filter{

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{

		String path = ((HttpServletRequest)request).getServletPath();
		List<String> checkPath = new ArrayList<>();

		checkPath.add("/delete");
		checkPath.add("/home");
		checkPath.add("/newThread");
		checkPath.add("/index.jsp");
		checkPath.add("/setting");
		checkPath.add("/signin");
		checkPath.add("/signout");
		checkPath.add("/signup");
		checkPath.add("/userManagement");
		checkPath.add("/css/style.css");
		System.out.println("ServletPath : " + path);//デバッグ用

		if(checkPath.contains(path) == false){
			List<String> messages = new ArrayList<>();
			messages.add("入力されたURLは存在しないため、ホーム画面へ戻りました");
			HttpSession session = ((HttpServletRequest)request).getSession();
			session.setAttribute("messages", messages);
			((HttpServletResponse)response).sendRedirect("/BBS");
			return;
		}

		chain.doFilter(request, response);
	}

	public void init(FilterConfig config){

	}

	public void destroy(){

	}

}
