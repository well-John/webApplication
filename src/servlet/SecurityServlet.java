package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.SecurityService;
import tableBean.User;
import tools.EMailTool;
import tools.Tool;

public class SecurityServlet extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取数据
		String type = request.getParameter("type");

		User user=new User();
		user.setUserName(request.getParameter("userName"));
		user.setPassword(request.getParameter("password"));
		user.setEmail(request.getParameter("email"));
		System.out.println(request.getParameter("userName"));
		System.out.println(request.getParameter("password"));
		
		SecurityService securityService=new SecurityService();//创建服务层对象
		Integer result;
		
		if(type.equals("login")){//登录
			HttpSession session=request.getSession();
			result = securityService.login(user, session);//调用服务层对象完成任务
			
			if(result==1)
				//登录成功，进入后台主页（进入后台主页时，会经过SecurityFilter 来判断是否能访问后台主页
				response.sendRedirect("/webApplication/admin/1.jsp");
			else{
				request.setAttribute("result", result);//结果记录到request中，用于登录网页提供错误信息
				//登录失败，重新进入登录网页，并显示错误提示
				ServletContext context = getServletContext();//ServletContext存储全局数据
				Random rand = new Random();
				RequestDispatcher rd = context.getRequestDispatcher("/userManage/login.jsp?a="+String.valueOf(rand.nextInt()));//设置分派的网址
				//RequestDispatcher rd = context.getRequestDispatcher("/index.jsp");//设置分派的网址
				rd.forward(request,response);				
			}
				
		}else if(type.equals("register")){//注册
			String checkCode = request.getParameter("checkCode");
			HttpSession session=request.getSession();
			String severCheckCode=(String)session.getAttribute("checkCode");//获取session中的验证码
			
			if(severCheckCode==null ){//服务器端验证图片验证码不存在
				result=-3;			
			}else if(!severCheckCode.equals(checkCode)){//服务器端验证图片验证码验证失败
				result=-4;
			}else{//验证码验证正确	
				result=securityService.register(user);//注册用户
			}
			//将result返回客户端的  ajax 请求			
			Tool.returnIntResult(response, result);//使用工具类的方法给ajax请求返回json格式的数据
		}else if(type.equals("byEmail")){//找回密码			
			user.setEmail(request.getParameter("content1"));
			Integer rand=Tool.getRandomInRangeInteger(10, 100000);//随机数作为验证修改密码用
			result=securityService.findPasswordByEmail(user,rand);
			if(result==1){//发送邮件成功
				HttpSession session=request.getSession();
				session.setAttribute("email", user.getEmail());
				session.setAttribute("rand", rand);
				session.setAttribute("time", new Date());
			}	
			
			Tool.returnIntResult(response, result);
		}else if(type.equals("newPassword")){//设置新密码			
			String rand=(String)request.getParameter("rand");
			
			HttpSession session=request.getSession();
			Integer trueRand=(Integer)session.getAttribute("rand");			
			user.setEmail((String)session.getAttribute("email"));			
			Date old=(Date)session.getAttribute("time");
			
			if(!rand.equals(trueRand.toString()))//rand值不对，无权限修改密码
				result=-3;
			else if(old==null || Tool.getSecondFromNow(old)>300 )
				result= -2;//修改密码超时
			else {			
				result=securityService.updatePassword(user);
			}
			
			session.removeAttribute("email");//删除session数据
			session.removeAttribute("rand");
			session.removeAttribute("time");	
			Tool.returnIntResult(response, result);
		}
	}	

}
