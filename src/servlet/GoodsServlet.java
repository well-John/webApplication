package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import otherBean.Message;

import service.GoodsService;
import tableBean.Goods;
import tableBean.pageBean.GoodsEasyUI;
import tableBean.pageBean.GoodsPage;
import tableBean.pageBean.PageInformation;
import tableBean.pageBean.PageInformationEasyUI;
import tools.Tool;
import tools.WebProperties;

public class GoodsServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
		
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String type = request.getParameter("type");
		
		Goods goods=new Goods();
		
		GoodsService goodsService=new GoodsService();
		
		Message message=new Message();
		
		if(type.equals("add")){//添加商品		
			message.setResult(goodsService.add(request,goods));
			
			if(message.getResult()==1){
				message.setMessage("添加商品成功！");				
			}else if(message.getResult()==-1){
				message.setMessage("添加商品失败！");
			}else if(message.getResult()==-2){
				message.setMessage("图片文件保存成功，但数据库数据保存失败！");	
			}else if(message.getResult()==-3){
				message.setMessage("图片文件重名！");	
			}
			message.setRedirectUrl("\\webApplication\\goods\\addGoods.jsp");
			message.setRedirectTime(WebProperties.propertiesMap.get("redirectTime"));
			
			request.setAttribute("message", message);
			
			ServletContext sc = this.getServletContext();//ServletContext是所有servlet共享的,
			RequestDispatcher rd = sc.getRequestDispatcher("/message.jsp");//设置分派地址
			rd.forward(request, response);
		}else if(type.equals("showPage")){//展现商品
			String rows=request.getParameter("rows");
			String page=request.getParameter("page");
			GoodsEasyUI goodsEasyUI=new GoodsEasyUI();
			
			PageInformationEasyUI pageInformationEasyUI=new PageInformationEasyUI();
			pageInformationEasyUI.setPage(Integer.parseInt(page));
			pageInformationEasyUI.setRows(Integer.parseInt(rows));

			goodsEasyUI=goodsService.listNextPageEasyUI(pageInformationEasyUI);
			
			Gson gson = new Gson();  
			String jsonString= gson.toJson(goodsEasyUI);
			
			Tool.returnJsonString(response, jsonString);
		}else if(type.equals("deleteRecord")){//删除
			String pageSize=request.getParameter("pageSize");
			String page=request.getParameter("page");
			GoodsPage goodsPage=new GoodsPage();
			
			PageInformation pageInformation=new PageInformation();
			pageInformation.setPage(Integer.parseInt(page));
			pageInformation.setPageSize(Integer.parseInt(pageSize));
			pageInformation.setIds(request.getParameter("ids"));
			
			//排序
			pageInformation.setOrderField(request.getParameter("orderField"));
			pageInformation.setOrder(request.getParameter("order"));			
			//查询
			pageInformation.setSearchSql(request.getParameter("searchSql"));
			
			if(pageInformation.getIds()!=null)//删除
				goodsPage=goodsService.delete(pageInformation);
			else//分页显示
				goodsPage=goodsService.listNextPage(pageInformation);
			
			Gson gson = new Gson();  
			String jsonString= gson.toJson(goodsPage);
			
			Tool.returnJsonString(response, jsonString);
		}else if(type.equals("updateRecord")){//修改
			goods.setGoodsId(Long.parseLong(request.getParameter("goodsId")));
			goods.setName(request.getParameter("name"));
			goods.setPrice(Double.parseDouble(request.getParameter("price")));			
			
			Integer result=goodsService.update(goods);
			Tool.returnIntResult(response,result);
		}
			
		
	}

}
