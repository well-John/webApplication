package service;

import tableBean.Article;
import dao.ArticleDao;

public class ArticleService {
	public Integer add(  Article article){
		ArticleDao articleDao=new ArticleDao();
		
		return articleDao.add(article);
	}

}
