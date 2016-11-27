package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SecurityFilter implements Filter {

	public void destroy() {}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		System.out.println("before SecurityFilter");
		
		if (session.getAttribute("userName") != null) {
			//已登录			
			chain.doFilter(request, response);	//传给下个filter处理		
		} else {//未登录
			String originalUrl=req.getRequestURI();//获取用户请求的原始网址
			req.getSession().setAttribute("originalUrl", originalUrl);//保存原始网址到session
			res.sendRedirect("/webApplication/userManage/login.jsp");//跳转到登录网页，至此中断了过滤链
		}
		System.out.println("after SecurityFilter");
	}

	public void init(FilterConfig filterConfig) throws ServletException {}

}
