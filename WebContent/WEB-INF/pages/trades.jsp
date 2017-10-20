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
	<center>
		<br> <br> <br> <br>
		<c:if test="${empty requestScope.user }">
			<h3>对不起，没有该用户交易记录！！！</h3>
		</c:if>
		<c:if test="${!empty requestScope.user }">
		<h4>user:${user.username}</h4>
			<c:forEach items="${user.trades}" var="trade">
				<table border = "1" cellpadding="10" cellspacing= "0">
					<tr>
						<td>TradeTime</td>
						<td colspan="2">${trade.tradeTime}</td>
					</tr>
					<tr>
						<td>书名</td>
						<td>单价</td>
						<td>数量</td>
					</tr>
					<c:forEach items="${trade.items}" var="item">
						<tr>
							<td>${item.book.title}</td>
							<td>${item.book.price}</td>
							<td>${item.quantity}</td>
						</tr>
					</c:forEach>
				</table>
				<br><br><br>
			</c:forEach>



		</c:if>



	</center>
</body>
</html>