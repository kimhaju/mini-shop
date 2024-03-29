<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
 <%-- <%@ page import="com.model2.mvc.service.product.vo.ProductVO" %>
 <%@ page import="com.model2.mvc.common.util.CommonUtil"%>
 <%@ page import="com.model2.mvc.common.Search" %>
 <%@ page import="com.model2.mvc.service.domain.User" %>
 
 
<%
String menu = CommonUtil.null2str(request.getParameter("menu"));
%>
 
<%
	ProductVO productVO=(ProductVO)request.getAttribute("productVO");
%>--%>	
 
<html>
<head>
<title>상품정보조회</title>
 
<link rel="stylesheet" href="/css/admin.css" type="text/css">
 
</head>
 
<body bgcolor="#ffffff" text="#000000">
 
<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37">
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">상품정보조회</td>
					<td width="20%" align="right">&nbsp;</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37"><img src="/images/ct_ttl_img03.gif" width="12" height="37"></td>
	</tr>
</table>
 
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:13px;">
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">
			상품번호 <img src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"/>
		</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="105">${productVO.prodNo}</td>
					<td></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	
	<tr>
		<td width="104" class="ct_write">
			상품명 <img src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"/>
		</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">${productVO.prodName}</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">상품 이미지</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">${productVO.fileName}</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">상품 상세정보</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">${productVO.prodDetail}</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">제조일자 </td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="26">${productVO.manuDate}</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">등록일자</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">${productVO.regDate}</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
</table>
 
<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top:10px;">
	<tr>
		<td width="53%"></td>
		<td align="right">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<c:if test="${menu!=null}">
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23"/>
					</td> 			 
					 <c:if test="${menu.equals('manage')}">
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
					 <a href="/updateProductView.do?prodNo=${productVO.prodNo}">수정</a></td>
					 </c:if>
					 <c:if test="${menu.equals('search')}">
					 <td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
                     <a href="/purchaseProductView.do?prodNo=${productVO.prodNo}">구매</a></td>
					</c:if>
					
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23">
					</td>
					</c:if>
					
					
					<td width="30"></td>					
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						<a href="javascript:history.go(-1);">확인</a>
					</td>
					<td width="14" height="23"><img src="/images/ct_btnbg03.gif" width="14" height="23"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
 
</body>
</html>