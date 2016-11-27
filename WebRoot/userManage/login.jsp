<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>   
  	<title>登录</title> 
  	<meta name="content-type" content="text/html; charset=UTF-8">
  	
    <link rel="stylesheet" type="text/css" href="../js/jquery-easyUI/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../js/jquery-easyUI/themes/icon.css">
    <script type="text/javascript" src="../js/jquery-easyUI/jquery.min.js"></script>
    <script type="text/javascript" src="../js/jquery-easyUI/jquery.easyui.min.js"></script> 
	<script type="text/javascript" src="../js/jquery-easyUI/locale/easyui-lang-zh_CN.js"></script> 
	<script src="../js/my/easyui-validate.js" type="text/javascript"></script>
	
	<script type="text/javascript">

        function clearForm(){
            $('#form1').form('clear');
        }			
	</script>

  </head>

  <body>
  	<div class="easyui-panel" title="登录" style="width:400px">
	    <form id="form1" name="form1" method="post" action="/webApplication/servlet/SecurityServlet?type=login" onsubmit="return submit1()">
	     	<table cellpadding="5" align="center">
		     	<tr>
		  			<td colspan="2" align="center">	      
						<c:if test="${requestScope.result==-1}" var="b">
		   					<c:out value="用户名或密码错误！"> </c:out>
						</c:if>
		  			</td></tr>
				<tr>
				    <td>用户名：</td>
				    <td><input class="easyui-textbox" type="text" name="name" data-options="required:true,validType:'userName'"></input></td>
				</tr>
				<tr>
				    <td>密码：</td>
				    <td><input class="easyui-textbox" type="text" name="password" data-options="required:true,validType:'password'"></input></td>
				</tr>
	       </table>	 
		 
		   <div style="text-align:center;padding:5px">
		   		
	            <input type='submit' value='登录' class="easyui-linkbutton"></a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">重置</a>
	            &nbsp; &nbsp;<a href="register.jsp">注册</a>
		      	&nbsp; &nbsp;<a href="findPassword.jsp">找回密码</a>
	        </div>	 
		</form>
	</div>
 
  </body>
</html>
