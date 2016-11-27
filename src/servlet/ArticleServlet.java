package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.ArticleService;
import tableBean.Article;
import tools.Tool;

public class ArticleServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String type = request.getParameter("type");
		
		Article article=new Article();	
		ArticleService articleService=new ArticleService();
		
		if(type.equals("add")){//添加文章
			article.setTitle(request.getParameter("title"));
			article.setContent(request.getParameter("content"));
			
			//将result返回客户端的  ajax 请求			
			Tool.returnIntResult(response, articleService.add(article));//使用工具类的方法给ajax请求返回json格式的数据	
			
		}
		

	}

}
