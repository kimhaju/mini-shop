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
<title>����Ȯ��</title>
</head>

<body>

<form name="addPurchase" method="post">

������ ���� ���Ű� �Ǿ����ϴ�.

<% System.out.println("�Ǹ� jsp���� �� �߰� �Ȱ��� Ȯ��."+purchaseVO); %>

<table border=1>
	<tr>
		<td>��ǰ��ȣ</td>
		<td><%=purchaseVO.getProdNo() %></td>
		<td></td>
	</tr>
	<tr>
		<td>�����ھ��̵�</td>
		<td><%=user.getUserId() %></td>
		<td></td>
	</tr>
	<tr>
		<td>���Ź��</td>
		<td>
			<%if (purchaseVO.getPaymentOption().equals("0"))  {%>
				���ݱ���
			<%}else{ %>
				�ſ뱸��
			<%} %>		
		</td>
		<td></td>
	</tr>
	<tr>
		<td>�������̸�</td>
		<td><%=purchaseVO.getReceiverName() %></td>
		<td></td>
	</tr>
	<tr>
		<td>�����ڿ���ó</td>
		<td><%=purchaseVO.getReceiverPhone() %></td>
		<td></td>
	</tr>
	<tr>
		<td>�������ּ�</td>
		<td><%=purchaseVO.getDivyAddr() %></td>
		<td></td>
	</tr>
		<tr>
		<td>���ſ�û����</td>
		<td><%=purchaseVO.getDivyRequest() %></td>
		<td></td>
	</tr>
	<tr>
		<td>����������</td>
		<td><%=purchaseVO.getDivyDate() %></td>
		<td></td>
	</tr>
</table>
</form>

</body>
</html>