<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>   
    <title>注册</title>
    <meta name="content-type" content="text/html; charset=UTF-8">
    <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link rel="stylesheet" type="text/css" href="../css/all.css">
    <link rel="stylesheet" type="text/css" href="../js/jquery-easyUI/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../js/jquery-easyUI/themes/icon.css">
    <script type="text/javascript" src="../js/jquery-easyUI/jquery.min.js"></script>
    <script type="text/javascript" src="../js/jquery-easyUI/jquery.easyui.min.js"></script> 
	<script type="text/javascript" src="../js/jquery-easyUI/locale/easyui-lang-zh_CN.js"></script> 
	<script src="../js/my/easyui-validate.js" type="text/javascript"></script>	
	<style type="text/css">
		.hand {
			cursor: pointer;//鼠标变成手的形状
		}	
	</style> 
	<script type="text/javascript">
	
		$(document).ready(function() {//资源加载后才执行的 代码，就放到这个函数中，jquery能保证网页所有资源（html代码，图片，js文件，css文件等）都加载后，才执行此函数

			$("#checkImg").click(function(){//为id是checkImg的标签绑定  鼠标单击事件  的处理函数
				//$(selector).attr(attribute,value)  设置被选元素的属性值
				//网址后加如一个随机值rand，表示了不同的网址，防止缓存导致的图片内容不变
				$("#checkImg").attr("src","/webApplication/servlet/ImageCheckCodeServlet?rand="+Math.random());
  			});		
			
			$('#form1').form({    
				url:"/webApplication/servlet/SecurityServlet?type=register",    
				onSubmit: function(){		
					a="d";
					return true;
				},    
				success : function(data) {//ajax请求成功时，会执行此回调函数
					data=eval('(' + data + ')'); 
					if(data.result==1){//注册成功
						var newHtml="注册成功！<br/>"+
									"<a href='login.jsp'>登录</a><br/>"+
									"<a href='/webApplication/index.jsp'>返回前端主页</a>";
						$("#myDiv").html(newHtml);
					}else if(data.result==0){//数据库操作失败
						alert("数据库操作失败！");
					}else if(data.result==-1){//有同名用户
						alert("用户名已注册，请换一个用户名！");
					}else if(data.result==-10){//emai已被注册
						alert("emai已被注册！");
					}else if(data.result==-11){//重名用户且email被注册
						alert("有同名用户且emai已被注册！");
					}else if(data.result==-3){//服务器端验证图片验证码不存在
						alert("验证码不存在！请点击验证码生成新的验证码");
						$("#checkCode").val("");//清空文本框
					}else if(data.result==-4){//验证码错误
						alert("验证码错误，请重新输入验证码！");
						$("#checkCode").val("");
					}								
				}
			});	
		});
		
        function submitForm(){
            $('#form1').form('submit');
        }
	</script>
  </head>
  
  <body>
  	<div class="hcenter" style="width:500px;padding:10px;">
	  	<div id="myDiv" class="easyui-panel" title="注册" style="width:500px;padding:10px;">
		 	<form id="form1" name="form1" method="post">
			  <table>
			    <tr>
			      <td align="right">用户名：</td>
			      <td>
			      	<input name="userName" type="text" id="userName" class="easyui-textbox" size="25" maxlength="15" data-options="required:true,validType:'userName'"/>
			      </td>
			    </tr>
			    <tr>
			      <td align="right">密码：</td>
			      <td>
			      	<input name="password" type="password" id="password" class="easyui-textbox" data-options="required:true,validType:'password'" size="25" maxlength="15"/>
			      </td>
			    </tr>
			    <tr>
			      <td align="right">电子邮箱：</td>
			      <td>
			      	<input name="email" type="text"  class="easyui-textbox" id="email" size="25" maxlength="15" data-options="required:true,validType:'email'"/>
			      </td>
			    </tr>            
			    <tr>
			      <td align="right">图形验证码：</td>
			      <td valign="middle"><input type="text" class="easyui-textbox" name="checkCode" id="checkCode" size="25" maxlength="15" data-options="required:true">
			      						<img id="checkImg" align="middle"  src="/webApplication/servlet/ImageCheckCodeServlet?rand=-1" class="hand" /></td>
			    </tr>		    
			    <tr>
			      <td colspan="2" align="center"><a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">注册</a></td>
		        </tr>
		        
			  </table>
			</form>  
		</div>
	</div>
  </body>
</html>
