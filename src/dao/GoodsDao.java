package dao;

import java.util.ArrayList;
import java.util.List;

import tableBean.Goods;
import tableBean.pageBean.GoodsEasyUI;
import tableBean.pageBean.GoodsPage;
import tableBean.pageBean.PageInformation;
import tableBean.pageBean.PageInformationEasyUI;

public class GoodsDao {
	public boolean add(Goods goods){
		
		goods.setImgUrl(goods.getImgUrl().replace("\\", "/"));
		String sql = "INSERT INTO goods(name,price,imgUrl) VALUES ('"
				+ goods.getName()+ "',"
				+ goods.getPrice()+ ",'"
				+ goods.getImgUrl()+ "') ";
		boolean returnValue = false;
		
		DBAccess db = new DBAccess();
		if (db.createConn()) {
			if(db.update(sql)>0)
				returnValue = true;//成功插入
			db.closeRs();
			db.closeStm();
			db.closeConn();
		}
		return returnValue;
	}
	
	public Integer getAllRecordCount(String queryCondition){//查询失败返回-1
		String sql = "select count(*)  as c from goods " + queryCondition;
		Integer count = -1;
		
		DBAccess db = new DBAccess();
		if (db.createConn()) {
			db.query(sql);
			while (db.next()) {
				count=db.getIntValue("c");
			}
			db.closeRs();
			db.closeStm();
			db.closeConn();
		}
		return count;
	}
	
	
	public GoodsEasyUI listNextPageEasyUI(PageInformationEasyUI pageInformationEasyUI){
		GoodsEasyUI goodsEasyUI=new GoodsEasyUI();
		List<Goods> goodsList=new ArrayList<Goods>();
		String searchSql="";
		String queryCondition="";
		
		
		Integer allRecordCount=getAllRecordCount(searchSql);//符合条件的总记录数
		Integer totalPageCount=(int) Math.ceil(1.0* allRecordCount / pageInformationEasyUI.getRows());//总页数
		
		Integer from=(pageInformationEasyUI.getPage()-1) * pageInformationEasyUI.getRows();//需查询的 起始记录
		
		//queryCondition+=searchSql;//加上查询条件
		String sql="select * from goods " +queryCondition +" limit " + from.toString()+ ","
				+ pageInformationEasyUI.getRows().toString();
		
		DBAccess db = new DBAccess();
		if (db.createConn()) {
			db.query(sql);//分页查询
			while (db.next()) {
				Goods goods=new Goods();
				goods.setGoodsId(new Long(db.getIntValue("goodsId")));
				goods.setName(db.getStringValue("name"));
				goods.setPrice(db.getFloatValue("price"));
				goods.setImgUrl(db.getStringValue("imgUrl"));
				
				goodsList.add(goods);	
			}
			db.closeRs();
			db.closeStm();
			db.closeConn();
			
			goodsEasyUI.setRows(goodsList);
			goodsEasyUI.setTotal(allRecordCount);			
		}
		
		return goodsEasyUI;
		
	}
	
	
	public GoodsPage listNextPage(PageInformation pageInformation){//page表示第几页，pageSize表示一页最多呈现多少记录
		
		GoodsPage goodsPage= new GoodsPage();
		List<Goods> goodsList=new ArrayList<Goods>();
		
		//拼出完整的sql语句
		String queryCondition="";
		String searchSql="";
		//查询
		if(pageInformation.getSearchSql()!=null){
			searchSql=pageInformation.getSearchSql();
			searchSql=searchSql.replace("-", "'").replace("_", " ").replace("|", "%");
			searchSql=" where "+searchSql;
			queryCondition=searchSql;	
		}
		
		//排序
		if(pageInformation.getOrderField()!=null )
			queryCondition+=" order by "+pageInformation.getOrderField()+" "+ pageInformation.getOrder();
		
		
		Integer allRecordCount=getAllRecordCount(searchSql);//符合条件的总记录数
		pageInformation.setAllRecordCount(allRecordCount);
		Integer totalPageCount=(int) Math.ceil(1.0* allRecordCount / pageInformation.getPageSize());//总页数
		pageInformation.setTotalPageCount(totalPageCount);
		
		//防止页码越界  删除时有可能页码越界
		if(pageInformation.getPage()<1)
			pageInformation.setPage(1);
		if(pageInformation.getPage()>totalPageCount)
			pageInformation.setPage(totalPageCount);
		
		Integer from=(pageInformation.getPage()-1) * pageInformation.getPageSize();//需查询的 起始记录
		
		//queryCondition+=searchSql;//加上查询条件
		String sql="select * from goods " +queryCondition +" limit " + from.toString()+ ","
				+ pageInformation.getPageSize().toString();
		
		DBAccess db = new DBAccess();
		if (db.createConn()) {
			db.query(sql);//分页查询
			while (db.next()) {
				Goods goods=new Goods();
				goods.setGoodsId(new Long(db.getIntValue("goodsId")));
				goods.setName(db.getStringValue("name"));
				goods.setPrice(db.getFloatValue("price"));
				goods.setImgUrl(db.getStringValue("imgUrl"));
				
				goodsList.add(goods);	
			}
			db.closeRs();
			db.closeStm();
			db.closeConn();
			
			goodsPage.setGoodsList(goodsList);
			goodsPage.setPageInformation(pageInformation);
			
			
		}		
		
		return goodsPage;
	}	
	
	public boolean delete(String ids){
		String sql = "delete from goods where goodsId in (" + ids + ")";
		boolean returnValue = false;
		
		DBAccess db = new DBAccess();
		if (db.createConn()) {
			if(db.update(sql)>0)
				returnValue = true;//成功删除
			db.closeRs();
			db.closeStm();
			db.closeConn();
		}
		return returnValue;
	}
	
	public boolean update(Goods goods){
		
		String sql = "update goods set name='"+goods.getName()+ 
				"',price="+String.valueOf(goods.getPrice())+
				" where goodsId="+String.valueOf(goods.getGoodsId());
		
		boolean returnValue = false;
		
		DBAccess db = new DBAccess();
		if (db.createConn()) {
			if(db.update(sql)>0)
				returnValue = true;//成功插入
			db.closeRs();
			db.closeStm();
			db.closeConn();
		}
		return returnValue;
	}	
	
}
