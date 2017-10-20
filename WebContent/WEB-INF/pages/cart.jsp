<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<script type="text/javascript" src="script/jquery-3.2.1.min.js"></script>
<%@ include file="/commons/queryCondition.jsp" %>   
<script type="text/javascript" >
	$(function(){
		//删除？此处有一个bug，删除功能在点“否”验证时，仍然执行了操作
		$(".delete").click(function(){
				//记住 取文本 一定要去空格
				var title = $(this).parent().parent().find("td:first").text();
				title = $.trim(title);
				var flag = confirm("你确定要删除《" +title + "》吗？");
				return flag;
		});
		
		//应用Ajax修改购物车单品数量
		//对修改的数量进行验证
		//1.获取页面中所有的text，并为其添加onchange事件
		$(":text").change(function(){
			var title = $(this).parent().parent().find("td:first").text();
			title = $.trim(title);
			//修改后的数量
			var val = $(this).val();
			val = $.trim(val);
			//修改前的数量表单值
			var beforeVal = $(this).attr("class");
			
			var reg = /^\d+$/ 
			var flag = false;
			if(reg.test(val)){
				if(parseInt(val) >= 0){
					flag = true;
				}
			}
			if(!flag){
				//若验证没有通过
				$(this).val(beforeVal);
				alert("您输入的数字不合法");
				return;
			}
			
			if(parseInt(val) == 0){
				var title = $(this).parent().parent().find("td:first").text();
				title = $.trim(title);
				var flag = confirm("你确定要删除《" +title + "》吗？");
				if(!flag){
					$(this).val(beforeVal);
					return;
				}
				var $a = $(this).parent().parent().find("td:last a")
				$a[0].onclick();
				return;
			}
			
			var flag = confirm("确定将《 "+title+" 》的数量改为" +val+"?"); 
			if(!flag){
				$(this).val(beforeVal);
				return false;
			}
		//2.页面的请求地址为bookServlet
			var url = "bookServlet";
		//3.请求参数。method:updateQuantity;id:name属性值(也可以用隐藏域的方法  但是会与他们默认include指令起冲突)；time：new Date（）[jquery里的date]；quantity：val
			var method = "updateQuantity",
				id = this.name,
				quantity =$.trim($(this).val()),
				time = new Date();
			var args = {"method":method,"id":id,"quantity":quantity,"time":time};//参数是个Json对象
		//4.在servlet中 调用updateItemQuery的方法中，获取quantity,id 在获取购物车对象，调用service方法修改
		//5.在servlet中传回Json数据，bookNumber,totalMoney
		//6.更新当前页面的bookNumber和totalMoney,将Map转为Json对象，（导入google-gson-2.2.4 jar包）
		$.post(url,args,function(data){
			var bookNumber = data.bookNumber;
			var totalMoney = data.totalMoney;
			$("#bookNumber").text("您的购物车中有" + bookNumber + "本书");
			$("#totalMoney").text("总金额：￥" + totalMoney);
			
		},"JSON");
			
			
		})
		
	})

</script>
	<center>
	<br><br><br><br>
		<div id = "bookNumber">您的购物车中有 ${sessionScope.shoppingCart.bookNumber }本书</div>
		<table cellpadding="10" cellspacing="0">
			<tr>
				<td>书名</td>
				<td>数量</td>
				<td>价格</td>
				<td></td>

			</tr>
			<c:forEach items="${sessionScope.shoppingCart.items}" var="item">
				<tr>
					<td>${item.book.title}</td>
					<td><input type="text" size = "1" name="${item.book.id}" class="${item.quantity}" value="${item.quantity}"></td>
					<td>${item.book.price}</td>
					<td><a href="bookServlet?method=remove&pageNo=${param.pageNo}&id=${item.book.id}" class="delete">删除</a></td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan = "4" id="totalMoney">
				总金额：￥${sessionScope.shoppingCart.totalMoney}
				</td>
			</tr>
			<tr>
				<td colspan = "4">
				<a href="bookServlet?method=getBooks&pageNo=${param.pageNo}">继续购物</a> &nbsp;&nbsp;
				<a href="bookServlet?method=clear">清空购物车</a>&nbsp;&nbsp;
				<a href="bookServlet?method=forwardPage&page=cash">结账</a>
				</td>
			</tr>
		</table>
	</center>

</body>
</html>