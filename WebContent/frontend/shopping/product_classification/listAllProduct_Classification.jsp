<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.shopping.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
//	ProductService productSvc = new ProductService();
//   List<ProductVO> list = productSvc.getAll();
//    pageContext.setAttribute("list",list);
%>
<jsp:useBean id="product_classificationSvc" scope="page" class="com.shopping.model.Product_ClassificationService" />

<html>
<head>
<title>所有商品分類 - listAllProduct_Classification.jsp</title>
</head>
<body bgcolor='white'>
<b><font color=red>此頁練習採用 EL 的寫法取值:</font></b>
<table border='1' cellpadding='5' cellspacing='0' width='800'>
	<tr bgcolor='orange' align='center' valign='middle' height='20'>
		<td>
		<h3>所有商品分類  - listAllProduct_Classification.jsp</h3>
		<a href="<%=request.getContextPath()%>/frontend/shopping/product/select_page.jsp"><img src="<%=request.getContextPath()%>/wu/images/back1.gif" width="100" height="32" border="0">回首頁</a>
		</td>
	</tr>
</table>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
  <font color='red'>請修正以下錯誤:
  <ul>
  <c:forEach var="message" items="${errorMsgs}">
    <li>${message}</li>
  </c:forEach>
  </ul>
  </font>
</c:if>

<table border='1' bordercolor='#CCCCFF' width='800'>
	<tr>
		<th>商品分類編號</th>
		<th>商品分類名稱</th>
		<th>修改</th>
		<th>刪除<font color=red>(關聯測試與交易-小心)</font></th>
		<th>查詢各類別的商品</th>
	</tr>
	
	<c:forEach var="product_classificationVO" items="${product_classificationSvc.all}">
		<tr align='center' valign='middle'>
			<td>${prodcut_classificationVO.proc_no}</td>
			<td>${prodcut_classificationVO.proc_name}</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/product_classification/product_classification.do">
			    <input type="submit" value="修改" disabled="true"> 
			    <input type="hidden" name="proc_no" value="${product_classificationVO.proc_no}">
			    <input type="hidden" name="action" value="getOne_For_Update_Product_Classification">
			</td></FORM>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/product_classification/product_classification.do">
			    <input type="submit" value="刪除">
			    <input type="hidden" name="proc_no" value="${product_classificationVO.proc_no}">
			    <input type="hidden" name="action" value="delete_Product_Classification">
			</td></FORM>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/product_classification/prodcut_classification.do">
			    <input type="submit" value="送出查詢"> 
			    <input type="hidden" name="proc_no" value="${product_classificationVO.proc_no}">
			    <input type="hidden" name="action" value="listProducts_ByProc_no_B">
			</td></FORM>
		</tr>
	</c:forEach>
</table>

<%if (request.getAttribute("listProdcuts_ByProc_no")!=null){%>
       <jsp:include page="listProducts_ByProc_no.jsp" />
<%} %>

</body>
</html>
