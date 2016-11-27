package tableBean.pageBean;

import java.util.List;

import tableBean.Goods;

public class GoodsPage {
	private List<Goods> goodsList;
	private PageInformation pageInformation;
	
	public List<Goods> getGoodsList() {
		return goodsList;
	}
	public void setGoodsList(List<Goods> goodsList) {
		this.goodsList = goodsList;
	}
	public PageInformation getPageInformation() {
		return pageInformation;
	}
	public void setPageInformation(PageInformation pageInformation) {
		this.pageInformation = pageInformation;
	}
}
