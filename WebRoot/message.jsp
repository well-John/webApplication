<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>消息</title>
     	<c:if test="${! empty requestScope.message.redirectTime}" var="adminchock">
      			<meta http-equiv="refresh" content="${requestScope.message.redirectTime};url=${requestScope.message.redirectUrl}"> 
 		</c:if>
  </head>
  
  <body>
    	${ requestScope.message.message }
    	<br>
    	过${requestScope.message.redirectTime }秒钟将自动跳转到添加商品网页！
    	或点击链接直接跳转到<a href="/webApplication/goods/addGoods.jsp">添加商品网页</a>
  </body>
</html>
