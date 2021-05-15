<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    
<%@ page import="com.model2.mvc.service.purchase.vo.*" %>    
<%@ page import="com.model2.mvc.service.domain.User" %>

<%
	PurchaseVO purchaseVO = (PurchaseVO)request.getAttribute("purchaseVO");
	User user = (User)request.getAttribute("user");
%>	    
<html>
<head>
<title>구매확인</title>
</head>

<body>

<form name="addPurchase" method="post">

다음과 같이 구매가 되었습니다.

<% System.out.println("판매 jsp에도 값 추가 된것을 확인."+purchaseVO); %>

<table border=1>
	<tr>
		<td>물품번호</td>
		<td><%=purchaseVO.getProdNo() %></td>
		<td></td>
	</tr>
	<tr>
		<td>구매자아이디</td>
		<td><%=user.getUserId() %></td>
		<td></td>
	</tr>
	<tr>
		<td>구매방법</td>
		<td>
			<%if (purchaseVO.getPaymentOption().equals("0"))  {%>
				현금구매
			<%}else{ %>
				신용구매
			<%} %>		
		</td>
		<td></td>
	</tr>
	<tr>
		<td>구매자이름</td>
		<td><%=purchaseVO.getReceiverName() %></td>
		<td></td>
	</tr>
	<tr>
		<td>구매자연락처</td>
		<td><%=purchaseVO.getReceiverPhone() %></td>
		<td></td>
	</tr>
	<tr>
		<td>구매자주소</td>
		<td><%=purchaseVO.getDivyAddr() %></td>
		<td></td>
	</tr>
		<tr>
		<td>구매요청사항</td>
		<td><%=purchaseVO.getDivyRequest() %></td>
		<td></td>
	</tr>
	<tr>
		<td>배송희망일자</td>
		<td><%=purchaseVO.getDivyDate() %></td>
		<td></td>
	</tr>
</table>
</form>

</body>
</html>