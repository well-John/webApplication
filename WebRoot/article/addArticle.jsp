<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://ckfinder.com" prefix="ckfinder"%>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor"%>
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
			$("#next").click( function() {
				var params={				
				        title :  $("#title").val(),	
						content  :  CKEDITOR.instances.content1.getData()
					}; 
				$.ajax({
					url : "/webApplication/servlet/ArticleServlet?type=add",
					type: "post", 
					data :  params,
					dataType : "json",
					cache : false,
					error : function(textStatus, errorThrown) {
						alert("系统ajax交互错误: " + textStatus);
					},
					success : function(data, textStatus) {
						if(data.result==1){
							$("#form1")[0].reset();
							CKEDITOR.instances.content1.setData("");
							$("#title").attr("value","");
						}else{
							alert("添加文章失败！");
						}						
					}
				});	
			});
			
		});
	
	</script>
	
  </head>
  
  <body>
      	<%@ include file = "/header.jsp"%>
  	<div class="frameBody">	
	  	<div class="easyui-panel" data-options="noheader:true,width:800,height:400">
		  	<div class="easyui-panel" title="登录" data-options="fit:true">
		  		<form id="form1">
			  		<table id="table1" width="600">
			  			<tr><td>
			  				<input type="text" id="title" class="easyui-textbox" name="title" data-options="required:true,prompt:'标题'" size="40"></td></tr>
						<tr><td>
							<textarea cols="400" id="content1"  name="content1" rows="150" ></textarea></td></tr>
							<ckfinder:setupCKEditor basePath="/webApplication/ckfinder/" editor="content1" />
							<ckeditor:replace replace="content1" basePath="/webApplication/ckeditor/" />					
		    			<tr><td><input type="button" value="添加"  id="next" class="easyui-linkbutton"/></td></tr>
			  		</table>
		  		</form>
	</div></div></div>		  	
  </body>
</html>
