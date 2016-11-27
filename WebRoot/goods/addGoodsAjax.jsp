<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>添加商品</title>
    <meta name="content-type" content="text/html; charset=UTF-8">
	<script type="text/javascript" src="/webApplication/js/jquery/jquery-2.1.4.min.js"></script>
    
    <script type="text/javascript">
		$(document).ready(function() {

		});
		
        function readAsDataURL() {
        	//获取文件对象
        	var file = document.getElementById("imgFile").files[0]; 
		    //检验是否为图像文件  		     
		    if(/image\/\w+/.test(file.type)){ //正则表达式
			    var reader = new FileReader();  
			    //将文件以Data URL形式读入页面  
			    reader.readAsDataURL(file);  
			    reader.onload=function(e){//设置load事件的处理函数  
			        var result=document.getElementById("showImg");  
			        //显示文件  
			        result.innerHTML='<img src="' + this.result +'" alt="" />'; 
			    };
		    }else{//不是图片
		    	 alert("不是图片图片！"); 
		    } 
        }  		
	</script>
    
  </head>
  
  <body>
    <form action="/webApplication/servlet/GoodsServlet?type=add" method="post" enctype="multipart/form-data">
   	  <table border="2" cellspacing="0" cellpadding="0">
    	  <tr>
    	    <td colspan="2" align="center">添加商品</td>
   	    </tr>
    	  <tr>
    	    <td align="right">名称：</td>
    	    <td align="left"> <input type="text" name="name" id="name"></td>
  	    </tr>
    	  <tr>
    	    <td align="right">单价：</td>
    	    <td align="left"><input name="price" type="text" id="price"></td>
  	    </tr>
    	  <tr>
    	    <td align="right">商品图片：</td>
    	    <td align="left"><input type="file" name="imgFile" id="imgFile" onChange="readAsDataURL()"></td>
  	    </tr>
      	  <tr>
    	    <td align="right">图片预览：</td>
    	    <td align="left"  id="showImg"></td>
  	    </tr>  
    	  <tr>
    	    <td colspan="2" align="center"><input value="添加商品" type="submit"> </td>
   	    </tr>            
  	  </table>
    </form>
  </body>
</html>
