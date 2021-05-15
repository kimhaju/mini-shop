<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    
<%@ page import="java.util.List"  %>
<%@ page import="com.model2.mvc.common.*" %>
<%@ page import="com.model2.mvc.service.purchase.vo.*" %>

<%
List<PurchaseVO> list2= (List<PurchaseVO>)request.getAttribute("list2");
Page resultPage=(Page)request.getAttribute("resultPage");
%>
<html>
<head>
<title>판매 목록조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
	function fncGetSaleList(currentPage) {
		document.getElementById("currentPage").value = currentPage;
		document.detailForm.submit();
	}
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width: 98%; margin-left: 10px;">

<form name="detailForm" action="/listSale.do" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"width="15" height="37"></td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">판매 목록조회</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"	width="12" height="37"></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">
	<tr>
		<td colspan="11">
		전체  <%= resultPage.getTotalCount() %> 건수, 현재 <%= resultPage.getCurrentPage() %> 페이지
		</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">회원ID</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">회원명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">전화번호</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">배송현황</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">정보수정</td>
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	<% 	
		int no=list2.size();
		for(int i=0; i<list2.size(); i++) {
			PurchaseVO purchaseVO = (PurchaseVO)list2.get(i);
	%>
	
	<tr class="ct_list_pop">
		<td align="center">
		<a href="/getPurchase.do?tranNo=<%=purchaseVO.getTranNo()%>"><%=no--%></a>
		</td>
		<td></td>
		<td align="left">
			<a href="/getPurchase.do?tranNo=<%=purchaseVO.getTranNo()%>"><%=purchaseVO.getBuyer().getUserId()%></a>
		</td>
		<td></td>
		<td align="left"><%=purchaseVO.getReceiverName()%></td>
		<td></td>
		<td align="left"><%=purchaseVO.getReceiverPhone()%></td>
		<td></td>
		<td align="left">
		<%System.out.println("현재 페이지에 들어오는 판매 상태 값: "+purchaseVO.getTranCode()); %>
		<% String trancode ="1";
		if(trancode.equals("1")){
			System.out.println("trancode: "+trancode);
		}
		%>
		<%if(purchaseVO.getTranCode().equals("1")) {%>
		
		구매완료
		<%} %>
		
		<%if(purchaseVO.getTranCode().equals("2")) {%>
		
		배송중
		<%} %>
		
		<%if(purchaseVO.getTranCode().equals("3")) {%>
		
		배송완료 
		<%} %>
		
		<td></td>
		<td align ="left">	
		<%if((purchaseVO.getTranCode()).equals("2")) {%>
			<a href="/updateTranCodeByProd.do?tranNo=<%=purchaseVO.getTranNo()%>&tranCode=<%=purchaseVO.getTranCode()%>">물건도착</a>
			<%} %>
		</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>
	<% } %>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 10px;">
	<tr>
		<td align="center">
		 
		<input type="hidden" id="currentPage" name="currentPage" value=""/>
		   
			<% if( resultPage.getCurrentPage() <= resultPage.getPageUnit() ){ %>
					◀ 이전
			<% }else{ %>
					<a href="javascript:fncGetSaleList('<%=resultPage.getCurrentPage()-1%>')">◀ 이전</a>
			<% } %>

			<%	for(int i=resultPage.getBeginUnitPage();i<= resultPage.getEndUnitPage() ;i++){	%>
					<a href="javascript:fncGetSaleList('<%=i %>');"><%=i %></a>
			<% 	}  %>
	
			<% if( resultPage.getEndUnitPage() >= resultPage.getMaxPage() ){ %>
					이후 ▶
			<% }else{ %>
					<a href="javascript:fncGetSaleList('<%=resultPage.getEndUnitPage()+1%>')">이후 ▶</a>
			<% } %>
		</td>
	</tr>
</table>

<!--  페이지 Navigator 끝 -->
</form>

</div>

</body>
</html>