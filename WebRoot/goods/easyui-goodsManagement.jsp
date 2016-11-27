<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head> 
  	<meta name="content-type" content="text/html; charset=UTF-8">
  	<link rel="stylesheet" type="text/css" href="/webApplication/css/all.css">
    <link rel="stylesheet" type="text/css" href="/webApplication/js/jquery-easyUI/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="/webApplication/js/jquery-easyUI/themes/icon.css">
    <script type="text/javascript" src="/webApplication/js/jquery-easyUI/jquery.min.js"></script>
    <script type="text/javascript" src="/webApplication/js/jquery-easyUI/jquery.easyui.min.js"></script> 
	<script type="text/javascript" src="/webApplication/js/jquery-easyUI/locale/easyui-lang-zh_CN.js"></script> 
	<script src="/webApplication/js/my/easyui-validate.js" type="text/javascript"></script>
	<script src="/webApplication/js/my/header.js" type="text/javascript"></script> 
	<script type="text/javascript">
		$(document).ready(function() {
			$("#goodsManage").datagrid({
			    url:"/webApplication/servlet/GoodsServlet?type=showPage",
			    columns:[[
					{field:'goodsId',title:'商品id',width:100},
					{field:'name',title:'用户名',width:100},
					{field:'price',title:'价格',width:100,align:'right'},
					{field:'imgUrl',title:'图片',width:300}
			    ]],
			    width: 'auto',
			    striped: true, 
			    singleSelect : true,
                pagination: true,  
                rownumbers: true                

			});
		});
	</script>
  </head>
  <body>
  	<%@ include file = "/header.jsp"%>
  	<div class="frameBody">	
	  	<div class="easyui-panel" data-options="noheader:true,width:800,height:400">
		  	<div class="easyui-panel" title="商品管理" data-options="fit:true">
    				<table id="goodsManage"></table>
   </div></div></div> 	
   
  </body>
</html>
