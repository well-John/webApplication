// JavaScript Document
$(document).ready(function() {//资源加载后才执行的 代码，就放到这个函数中，jquery能保证网页所有资源（html代码，图片，js文件，css文件等）都加载后，才执行此函数
	$('#user').menu({
	    onClick:function(item){
			if(item.text=="登录"){
				location.href="/webApplication/userManage/login1.jsp";
			}
	    }
	});
	
	$('#goods').menu({
	    onClick:function(item){
			if(item.text=="商品管理"){
				location.href="/webApplication/goods/easyui-goodsManagement.jsp";
			}
	    }
	});	
	
	$('#article').menu({
	    onClick:function(item){
			if(item.text=="文章添加"){
				location.href="/webApplication/article/addArticle.jsp";
			}
	    }
	});		
});