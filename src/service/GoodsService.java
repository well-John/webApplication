package service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import dao.GoodsDao;

import tableBean.Goods;
import tableBean.pageBean.GoodsEasyUI;
import tableBean.pageBean.GoodsPage;
import tableBean.pageBean.PageInformation;
import tableBean.pageBean.PageInformationEasyUI;
import tools.FileOperator;
import tools.WebProperties;

public class GoodsService {
	
	public Integer add(HttpServletRequest request,  Goods goods){
		Integer result=0;
		File tempDir=new File(FileOperator.tempDir);
		
		try{
			if (ServletFileUpload.isMultipartContent(request)) {
				DiskFileItemFactory dff = new DiskFileItemFactory();// 创建该对象
				dff.setRepository(tempDir);// 指定上传文件的临时目录
				dff.setSizeThreshold(10000000);// 指定在内存中缓存数据大小,单位为byte
				ServletFileUpload sfu = new ServletFileUpload(dff);// 创建该对象
				sfu.setHeaderEncoding("UTF-8");
				sfu.setFileSizeMax(10000000);// 指定单个上传文件的最大尺寸
				sfu.setSizeMax(10000000);// 指定一次上传多个文件的总尺寸
				List items = sfu.parseRequest(request);;// 解析request
				
				
				Iterator iter = items.iterator();
				// 遍历FileItemIterator集合
				while (iter.hasNext()) {
					FileItem item =(FileItem) iter.next();// 从集合中获得一个文件流
					if (!item.isFormField() && item.getName().length() > 0) {// 过滤掉表单中   非文件 域
						String fileName=item.getName();
						//fileName=new String(fileName.toString().getBytes("UTF-8"));  
						File goodsImageFile=new File(WebProperties.propertiesMap.get("goodsImageFullDir")+
											"\\"+fileName);
						//判断是否有重名文件
						if(goodsImageFile.exists()){//文件有重名
							return -3;
						}else{
							//保存文件
							BufferedInputStream in = new BufferedInputStream(item
									.getInputStream());// 获得文件输入流
							BufferedOutputStream out = new BufferedOutputStream(
									new FileOutputStream(goodsImageFile));// 获得文件输出流
							Streams.copy(in, out, true);// 开始把文件写到你指定的上传文件夹						
							
							
							String imageUrl="\\"+WebProperties.propertiesMap.get("projectName")+
									WebProperties.propertiesMap.get("goodsImageDir")+
									"\\"+fileName;
							goods.setImgUrl(imageUrl);
	
						}
					}else if(item.isFormField()){//非文件域
						if(item.getFieldName().equals("name"))
							goods.setName(item.getString("utf-8"));
						else if(item.getFieldName().equals("price"))
							goods.setPrice(Double.parseDouble(item.getString("utf-8")));
					}
				}
				
				
				GoodsDao goodsDao=new GoodsDao();
				
				if(goodsDao.add(goods))							
					result=1;
				else
					result=-2;//文件保存成功，但数据库数据保存失败
			}	
		}catch(Exception e){
			result=-1;//出现异常
		}
		
		return result;
	}
	
	
	public GoodsEasyUI listNextPageEasyUI(PageInformationEasyUI pageInformationEasyUI){
		GoodsDao goodsDao=new GoodsDao();
		return goodsDao.listNextPageEasyUI(pageInformationEasyUI);		
	}
	
	public GoodsPage listNextPage(PageInformation pageInformation){
		GoodsDao goodsDao=new GoodsDao();
		return goodsDao.listNextPage(pageInformation);		
	}
	
	public GoodsPage delete(PageInformation pageInformation){
		GoodsDao goodsDao=new GoodsDao();
		
		if(	goodsDao.delete( pageInformation.getIds()))
			pageInformation.setResult(1);
		else
			pageInformation.setResult(-1);
		
		if(pageInformation.getResult()==1)//删除成功
			return listNextPage(pageInformation);
		else{//删除失败
			GoodsPage goodsPage= new GoodsPage();
			goodsPage.setGoodsList(new ArrayList<Goods>());
			goodsPage.setPageInformation(pageInformation);
			
			return goodsPage;
		}
	}	
	
	public Integer update(Goods goods){
		GoodsDao goodsDao=new GoodsDao();
		if (goodsDao.update(goods))
			return 1;
		else
			return -1;
		
	}

}
