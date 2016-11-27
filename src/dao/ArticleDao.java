package dao;

import tableBean.Article;

public class ArticleDao {
	public Integer add(  Article article){
		String sql = "INSERT INTO article(title,content) VALUES ('"
				+ article.getTitle()+ "','"
				+ article.getContent()+ "') ";
		int returnValue = -1;
		
		DBAccess db = new DBAccess();
		if (db.createConn()) {
			if(db.update(sql)>0)
				returnValue = 1;//成功插入
			db.closeRs();
			db.closeStm();
			db.closeConn();
		}
		return returnValue;
	}
}
