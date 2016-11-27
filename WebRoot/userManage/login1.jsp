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

        function clearForm(){
            $('#form1').form('clear');
        }			
	</script>

  </head>

  <body>
  	<%@ include file = "/header.jsp"%>
  	<div class="frameBody">	
	  	<div class="easyui-panel" data-options="noheader:true,width:800,height:400">
		  	<div class="easyui-panel" title="登录" data-options="fit:true">
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
						    <td><input class="easyui-textbox" type="text" name="userName" data-options="required:true,validType:'userName'"></input></td>
						</tr>
						<tr>
						    <td>密码：</td>
						    <td><input class="easyui-textbox" type="text" name="password" data-options="required:true,validType:'password'"></input></td>
						</tr>
			       </table>	 
				 
				   <div>
			            <input type='submit' value='登录' class="easyui-linkbutton"></a>
			            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">重置</a>
			            &nbsp; &nbsp;<a href="register.jsp">注册</a>
				      	&nbsp; &nbsp;<a href="findPassword.jsp">找回密码</a>
			        </div>	 
				</form>
			</div>

		</div>
 	</div>
  </body>
</html>
