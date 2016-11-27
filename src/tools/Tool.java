package tools;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Tool {
	static public Random random=new Random();
	static public  void dispatch(HttpServlet servlet, String localUrl, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		ServletContext context = servlet.getServletContext();//ServletContext存储全局数据
		RequestDispatcher rd = context.getRequestDispatcher("/userManage/login.jsp");//设置分派的网址
		rd.forward(request,response);	
	}
		
	//给ajax请求返回json格式的数据
	static public  void returnIntResult(HttpServletResponse response, Integer result) throws ServletException, IOException{
		//response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.write("{\"result\":"+result.toString()+"}");
		out.flush();
	}
	
	//给ajax请求返回json格式的数据
	static public  void returnJsonString(HttpServletResponse response, String jsonString) throws ServletException, IOException{
		response.setHeader("Content-type", "text/html;charset=UTF-8");  
		PrintWriter out = response.getWriter();
		out.write(jsonString  );
		out.flush();
	}
	
	static public Integer getRandomInteger(){
		Integer rand=random.nextInt();
		return rand;		
	}
	
	static public Integer getRandomInRangeInteger(Integer min, Integer max){
		int rand = min + (int)(Math.random() * ((max - min) + 1));
		return rand;		
	}
	
	static public Long getSecondFromNow(Date old){//当前时间领先old多少秒
		Date now=new Date();
		long between = (now.getTime()-old.getTime())/1000;//得到间隔秒数
		return between;		
	}	
}
