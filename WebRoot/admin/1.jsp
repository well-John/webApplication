<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    <title>后台主页</title>

  </head>
  
  <body>
   <form name="form1" method="post" action="/webApplication/servlet/SecurityServlet?type=logout">
    <input type="submit" name="button" id="button" value="注销">
   </form>   
  </body>
</html>
