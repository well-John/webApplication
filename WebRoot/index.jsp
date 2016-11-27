<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    <title>前台主页</title>
  	<meta name="content-type" content="text/html; charset=UTF-8">
  	<link rel="stylesheet" type="text/css" href="/webApplication/css/all.css">
    <link rel="stylesheet" type="text/css" href="/webApplication/js/jquery-easyUI/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="/webApplication/js/jquery-easyUI/themes/icon.css">
    <script type="text/javascript" src="/webApplication/js/jquery-easyUI/jquery.min.js"></script>
    <script type="text/javascript" src="/webApplication/js/jquery-easyUI/jquery.easyui.min.js"></script> 
	<script type="text/javascript" src="/webApplication/js/jquery-easyUI/locale/easyui-lang-zh_CN.js"></script> 
	<script src="/webApplication/js/my/easyui-validate.js" type="text/javascript"></script>
	
	<script src="/webApplication/js/my/header.js" type="text/javascript"></script>

  </head>
  
  <body>
  
		<%@ include file = "header.jsp"%>
		<div class="frameBody">		
			<div class="easyui-panel" data-options="noheader:true,width:800,height:400">
				 欢迎使用
				<a href="/webApplication/goods/easyui-goodsManagement.jsp">商品查询</a>
			</div>
		</div>
		
		
  </body>
</html>
