<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>商品管理</title>
      <link href="/webApplication/css/goods.css" rel="stylesheet" type="text/css">
      <script type="text/javascript" src="/webApplication/js/jquery/jquery-2.1.4.min.js"></script>
      
      <script type="text/javascript">
//       http://www.css88.com/jqapi-1.9/

		var data1;//全局变量
		var searchSql="";//查询
		
      	function newPage( obj,page,pageSize,orderField,order, ids){
    	  	var url1;
  			var param="";
			
			if(obj!=null && obj.innerHTML== "商品单价"){//切换排序
				if(order == "asc")//切换排序
					order="desc";
				else				
					order="asc";							
			}
			
			var urlPart1="/webApplication/servlet/GoodsServlet?type=";
			var urlPart2,urlPart3;
			//排序
    	  	if(orderField=="$no$" || orderField==undefined || orderField=="undefined" ){//不排序
    	  		urlPart2="showPage";
    	  		urlPart3="&page="+page+"&pageSize="+pageSize;//提交网址
    	  	}else{
    	  		urlPart2="showPage";
    	  		urlPart3="&page="+page+"&pageSize="+pageSize+"&orderField="+orderField+"&order="+order;
    	  	}
    	  	
    	  	//删除
    	  	if(ids!=""){
    	  		param={"ids":ids};
    	  		urlPart2="deleteRecord";    	  		
    	  	}
    	  	
    	  	urlAll=urlPart1+urlPart2+urlPart3+searchSql;
    	  		
			$.ajax({
				type : "POST",  //提交类型
				url : urlAll,
				data : param,//收集表单元素的数据
			    dataType : "json",//服务器端返回数据的格式
			    cache : false,//不缓存
				error : function(textStatus, errorThrown) {//ajax请求失败时执行（比如服务器关了）
					alert("系统ajax交互错误: " + textStatus);
				},
				success : function(data, textStatus) {	//ajax请求成功并接受到服务器返回信息，其中data中记录了服务器端返回的数据
					//data是对象，通过它可以访问服务器端返回的数据（均作为data对象的属性）
					var htmlString=""; 
					var i;
					data1=data;
					
					htmlString+="<div id='header'>"+
						"<div class='checkDiv'><input name='' type='checkbox' value='-1'></div>"+
						"<div>商品Id</div>"+
						"<div>商品图片</div>"+
						"<div>商品名称</div>"+
						"<div><a href='javascript:void(0);' onclick=\"newPage(this,1,"
								+pageSize+",'price','"+order+"')\" >商品单价</a></div>"+
						"<div class='clear'></div></div>";
					
					htmlString+="<div id='recordBody'>";
					for(i=0;i<data.goodsList.length;i++){
						htmlString+="<div id='row'>";
						var row="";
						row="<div class='checkDiv'><input name='' type='checkbox' value='"+data.goodsList[i].goodsId+"'></div>";
						row+="<div>"+data.goodsList[i].goodsId+"</div>";
						row+="<div><img src='"+data.goodsList[i].imgUrl+"' alt='图片失效' class='goodsIcon'></div>";
						row+="<div>"+data.goodsList[i].name+"</div>";
						
// 						js1.5以上可以利用toFixed(x) ，可指定数字截取小数点后 x位
						row+="<div>"+data.goodsList[i].price.toFixed(2)+"</div>";
						row+="<div class='clear'></div>";
						htmlString+=row+"</div>";
					}
					htmlString+="</div>";
					
					var $goodsGrid=$("#goodsGrid");//缓存jquery对象，提高效率
					var $goodsGrid_row=$("#goodsGrid #row");//缓存jquery对象，提高效率
				
					$goodsGrid.html(htmlString);
					$goodsGrid.addClass("goodsGrid");
					
					$("#goodsGrid #row div").not( $("div.clear")).addClass("col");
					$("#goodsGrid #row:nth-child(even)").addClass("goodsEven");//偶数行设置单独样式
					$("#goodsGrid #header div").not( $("div.clear")).addClass("goodsHeader");
					
					//绑定事件
					//绑定事件
					$("#goodsGrid #row").click(function(){ 
						$("#goodsGrid #row").removeClass("goodsSelectedRecord"); 
						$(this).addClass("goodsSelectedRecord"); 
					}); 
					
					$("#goodsGrid #row").mouseout(function(){ 
						$(this).removeClass("goodsCurrentRecord"); 
						$("#goodsGrid #row:nth-child(even)").addClass("goodsEven");//偶数行设置单独样式
					}); 					
					
					$("#goodsGrid #row").mouseover(function(){ 
						$(this).removeClass("goodsEven"); 
						$(this).addClass("goodsCurrentRecord"); 
					}); 
					
					$("#goodsGrid #row").mouseout(function(){ 
						$(this).removeClass("goodsCurrentRecord"); 
						$("#goodsGrid #row:nth-child(even)").addClass("goodsEven");//偶数行设置单独样式
					}); 
					
					
					$("#goodsGrid #header input[type=checkbox]").change(function(){ 
						$check=$("#goodsGrid #recordBody  input[type=checkbox]");
						
						if($(this).prop("checked")) {
							$check.prop("checked",true);
						}else{
							$check.removeAttr("checked");
						}
					}); 			
					
					//管理
					htmlString="<div>";
					
					//分页
					htmlString+="<div class='floatLeft'>";
					var pageInfo=data.pageInformation;
					
					if(pageInfo.page!=1){//非第一页'
						 /* href 属性设为 javascript:void(0);，可以防止不必要的页面跳动； */
						htmlString+="<a href='javascript:void(0);' onclick=\"newPage(this,1,"+pageInfo.pageSize+",'"+orderField+"','"+order+"','')\">"+"第一页</a>&nbsp;&nbsp;";
						htmlString+="<a href='javascript:void(0);' onclick=\"newPage(this,"+(pageInfo.page-1)+","+pageInfo.pageSize+",'"+orderField+"','"+order+"','')\">"+"前一页</a>&nbsp;&nbsp;";
					}
					if(pageInfo.page!=pageInfo.totalPageCount){//非最后一页
						htmlString+="<a href='javascript:void(0);' onclick=\"newPage(this,"+(pageInfo.page+1)+","+pageInfo.pageSize+",'"+orderField+"','"+order+"','')\">"+"下一页</a>&nbsp;&nbsp;";
						htmlString+="<a href='javascript:void(0);' onclick=\"newPage(this,"+pageInfo.totalPageCount+","+pageInfo.pageSize+",'"+orderField+"','"+order+"','')\">"+"最后一页</a>&nbsp;&nbsp;";
					}
					htmlString+="</div>";
					
					//删除、修改
					htmlString+="<div class='floatRight'>";
					
					htmlString+="<a href='javascript:void(0);' onclick='searchRecord()'>查询</a>&nbsp;&nbsp;";
					htmlString+="<a href='javascript:void(0);' onclick='updateRecord()'>修改</a>&nbsp;&nbsp;";
										
					
					htmlString+="<a href='javascript:void(0);' onclick='showDeleteCheck()'>启用删除</a>&nbsp;&nbsp;";
					htmlString+="<a href='javascript:void(0);' onclick='deleteRecord(this)'>开始删除</a>&nbsp;&nbsp;";
					
					htmlString+="</div>";
					
					htmlString+="</div>";					
					
					$goodsGrid.append(htmlString);
					
					$("#goodsGrid input:checkbox").attr("disabled","disabled");
					
			     }
			});	    	  
    	  
      	}
		
		//允许删除
		function showDeleteCheck(){			
			$("#goodsGrid input:checkbox").removeAttr('disabled');	
		}
      
		//删除记录
		function deleteRecord(obj){
			var ids="";
			$("#goodsGrid input:checked").each(function() {
		        if($(this).val()!=-1){//选中
					ids+=$(this).val()+",";
	           	}

			});
			
			if(ids.length<1){
				alert("请选择至少一个需删除的元素！");
				return;
			}

			ids=ids.substring(0,ids.length-1);
			
			newPage( obj,data1.pageInformation.page,
					data1.pageInformation.pageSize,
					data1.pageInformation.orderField,
					data1.pageInformation.order, ids);
		}		
		
		function updateRecord(){
			var modelHtml="<div class='model'><div><form>";			
			modelHtml+="<p>商品名称：<input type='text' name='name' value='"+$("#goodsGrid .goodsSelectedRecord div:nth-child(4)").html()+"'></p>";
			modelHtml+="<p>商品价格：<input type='text' name='price' value='"+$("#goodsGrid .goodsSelectedRecord div:nth-child(5)").html()+"'></p>";	
			modelHtml+="<p><input type='button' value='修改'></p>";	
			modelHtml+="<a href='javascript:void(0);' onclick=\"$('#goodsGrid .model').remove();\">关闭窗口</a>&nbsp;&nbsp;";
			modelHtml+="</form></div></div>";
			
			$("#goodsGrid").append(modelHtml);
			
			
			
			$("#goodsGrid .model input[type='button']").click(function(){ 
				$.ajax({
					type : "POST",  //提交类型
					url : "/webApplication/servlet/GoodsServlet?type=updateRecord&goodsId="+$("#goodsGrid .goodsSelectedRecord div:nth-child(2)").html(),
					data : $("#goodsGrid .model form").serialize(),//收集表单元素的数据
				    dataType : "json",//服务器端返回数据的格式
				    cache : false,//不缓存
					error : function(textStatus, errorThrown) {//ajax请求失败时执行（比如服务器关了）
						alert("系统ajax交互错误: " + textStatus);
					},
					success : function(data, textStatus) {	//ajax请求成功并接受到服务器返回信息，其中data中记录了服务器端返回的数据
						if(data.result==1){
							$("#goodsGrid .goodsSelectedRecord div:nth-child(4)").html($("#goodsGrid .model input[name='name']").val());
							$("#goodsGrid .goodsSelectedRecord div:nth-child(5)").html($("#goodsGrid .model input[name='price']").val());
							//$("#goodsGrid model input[type='button']").unbind("click");//解除事件绑定
							$("#goodsGrid .model").remove();
						}else{
							alert("更新失败！");
							
						}
					
					}
				});
			}); 
		}
		
		function searchRecord(){
			var modelHtml="<div class='model'><div><form>";			
			modelHtml+="<p>商品名称：&nbsp;&nbsp; 包含<input type='text' name='name'></p>";
			modelHtml+="<p>商品价格：&nbsp;&nbsp; 大于等于<input type='text' name='priceLow' value='0' size='6'>";
			modelHtml+="<p>商品价格：&nbsp;&nbsp; 小于等于<input type='text' name='priceHigh' value='100' size='6'>";
			modelHtml+="<p><input type='button' value='开始查询'></p>";	
			modelHtml+="<a href='javascript:void(0);' onclick=\"$('#goodsGrid .model').remove();\">关闭窗口</a>&nbsp;&nbsp;";
			modelHtml+="</form></div></div>";
			
			$("#goodsGrid").append(modelHtml);	
			
			$("#goodsGrid .model input[type='button']").click(function(){ 
				searchSql="&searchSql=";
				var goodsName=$("#goodsGrid input[name='name']").val();
				var priceLow=$("#goodsGrid input[name='priceLow']").val();
				var priceHigh=$("#goodsGrid input[name='priceHigh']").val();
				
				var first=true
				if(goodsName.length>0){
					searchSql+="_name_like_-|"+goodsName+"|-";
					first=false;
				}
				
				if(priceLow.length>0){
					if(first==false)
						searchSql+="_and";
					searchSql+="_price>_"+priceLow;
					first=false;
				}
				
				if(priceHigh.length>0){
					if(first==false)
						searchSql+="_and";					
					searchSql+="_price<_"+priceHigh;					
				}
				
				if(first==true)//无查询条件
					searchSql="";
				
				newPage( null,data1.pageInformation.page,
						data1.pageInformation.pageSize,
						data1.pageInformation.orderField,
						data1.pageInformation.order, "");					
				
			}); 		
		}
		
      
		$(document).ready(function() {
		 	newPage(null,1,3,"$no$","desc","");	    	 
		});
      
      
      </script>
      
      
  </head>
  
  <body>
  		<div id="goodsGrid" class="grid"></div>
  </body>
</html>
