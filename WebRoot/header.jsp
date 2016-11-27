<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<link rel="stylesheet" type="text/css" href="/webApplication/css/all.css">
  </head>
  
  <body>
		<div class="frameCenter">
	    	<div style="background:#fafafa;width:798px;border:1px solid #ccc">
				<a href="#" class="easyui-menubutton" menu="#user" iconCls="icon-edit">用户</a>
				<a href="#" class="easyui-menubutton" menu="#goods" iconCls="icon-help">商品</a>
				<a href="#" class="easyui-menubutton" menu="#article" iconCls="icon-help">文章</a>
			</div>
			<div id="user" style="width:150px;">
				<div>登录</div>
				<div>注销</div>
				<div class="menu-sep"></div>
				<div>注册</div>
				<div>找回密码</div>
			</div>
			<div id="goods" style="width:100px;">
				<div>商品添加</div>
				<div>商品管理</div>
			</div>
			<div id="article" style="width:100px;">
				<div>文章添加</div>
				<div>商品管理</div>
			</div>			
		</div>
	
  </body>
</html>
