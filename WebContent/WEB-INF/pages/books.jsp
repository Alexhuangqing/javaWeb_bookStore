<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<script type="text/javascript" src="script/jquery-3.2.1.min.js"></script>
<%@ include file="/commons/queryCondition.jsp" %>   
	<center>
		<br> <br> <br> 
		<c:if test="${!empty param.title}">
		你已将${param.title}加入购物车中<br>
		</c:if>
		<c:if test="${!empty sessionScope.shoppingCart.books}">
		您的购物车有 ${sessionScope.shoppingCart.bookNumber}件商品，<a href="bookServlet?method=forwardPage&pageNo=${page.pageNo}&page=cart">请查看购物车</a>
		</c:if>
		<br> <br>
		<form action="bookServlet?method=getBooks" method="post">
			price:<input type="text" size="1" name="minPrice">- 
			      <input type="text" size="1" name="maxPrice"> 
			      <input type="submit"  value="查找"> <br> <br> <br> <br>
		</form>
		<table cellpadding="10" cellspacing="0">
			<c:forEach items="${page.list}" var="book">
				<tr>
					<td><a href="bookServlet?method=getBook&id=${book.id}&pageNo=${page.pageNo}">${book.title}</a><br> ${book.author}</td>
					<td>${book.price}</td>
					<td><a href="bookServlet?method=addToCart&id=${book.id}&pageNo=${page.pageNo}&title=${book.title}">加入购物车</a></td>
				</tr>

			</c:forEach>
		</table>
		<br> <br> <br> 共${page.totalPageNumber}页 &nbsp;&nbsp;
		当前第 ${page.pageNo}页 &nbsp;&nbsp;
		<c:if test="${page.hasPrev }">
			<a href="bookServlet?method=getBooks&pageNo=1">首页</a>
				 &nbsp;&nbsp;
				<a href="bookServlet?method=getBooks&pageNo=${page.pageNo-1}">上一页</a>
		</c:if>
		<c:if test="${page.hasNext }">
				 &nbsp;&nbsp;
				<a href="bookServlet?method=getBooks&pageNo=${page.pageNo+1}">下一页</a>
				 &nbsp;&nbsp;
				<a href="bookServlet?method=getBooks&pageNo=${page.totalPageNumber}">末页</a>
				 &nbsp;&nbsp;
		</c:if>
				跳转到<input type="text" size= "1" name="pageNo" id="pageId" >
	</center>
	
	<script type="text/javascript">
		
		$(function() {
			/*将此段代码写到通用查询页面queryCondition.jsp
			$("a").click(function() {
				var serializeVal = $("input:hidden").serialize();
				//动态生成href
				var href = $(this).attr("href") +"&" +serializeVal;
				//该点将要访问的地址
				window.location.href = href;
				return false;
			})
			*/
			$("#pageId").change(function(){
				var pageNo = $(this).val();
				//表单字符串 去掉前后空格
				pageNo = pageNo.trim();
				//字符串 验证是否是数字开头
				var reg = /^\d+$/;
				
				/*以下来个更紧凑的方法
				if(!reg.test(pageNo)){
					alert("你输入的页码不合法");
					$(this).val("");
					return;
				}
				//字符串转化成数字，进行范围验证
				var pageNo2 = parseInt(pageNo);
				if(pageNo<1||pageNo>parseInt("${page.totalPageNumber}")){
					alert("你输入的页码不合法");
					$(this).val("");
					return;
				}
				*/
				var flag = false;
				
				if(reg.test(pageNo)){
					if(pageNo>=1&&pageNo<=parseInt("${page.totalPageNumber}")){
						//通过双层考验是合法字符
						flag = true;
					}
				}
				if(!flag){
					alert("你输入的页码不合法");
					$(this).val("");
					return;
				}
				
				
				//翻页,拼地址串，赋地址值
				var serializeVal = $("input:hidden").serialize();
				window.location.href = "bookServlet?method=getBooks&pageNo="+pageNo+"&"+serializeVal;
				
			})

		})
		
		
	</script>
</body>
</html>