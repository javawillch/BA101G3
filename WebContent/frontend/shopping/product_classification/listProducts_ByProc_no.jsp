<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<%-- 此頁練習採用 EL 的寫法取值 --%>

<jsp:useBean id="listProducts_ByProc_no" scope="request" type="java.util.Set" />
<jsp:useBean id="product_classificationSvc" scope="page" class="com.shopping.model.Product_ClassificationService" />
<html>
<head>
<title>商品分類問題 - listProducts_ByProc_no.jsp</title>
</head>
<body bgcolor='white'>

<b><font color=red>此頁練習採用 EL 的寫法取值:</font></b>
<table border='1' cellpadding='5' cellspacing='0' width='1000'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>商品分類問題 - listProducts_ByProc_no.jsp</h3>
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

<table border='1' bordercolor='#CCCCFF' width='1000'>
	<tr>
		<th>商品編號</th>
		<th>會員編號(賣家)</th>
		<th>商品分類編號</th>
		<th>商品名稱</th>
		<th>商品上架時間</th>
		<th>商品內容</th>
		<th>修改</th>
		<th>刪除</th>
	</tr>

	<c:forEach var="productVO" items="${listProducts_ByProc_no}">
		<tr align='center' valign='middle' ${(productVO.pro_no==param.pro_no)? 'bgcolor=#CCFFCC':''}>
			<td>${productVO.pro_no}</td>
			<td>${productVO.mem_no}</td>
			<td><c:forEach var="product_classificationVO" items="${product_classificationSvc.all}">
                    <c:if test="${productVO.proc_no==product_classificationVO.proc_no}">
	                    ${prodcut_classificationVO.proc_no}【${product_classificationVO.proc_name}】
                    </c:if>
                </c:forEach></td>
			<td>${productVO.pro_name}</td>
			<td>${productVO.pro_date}</td>
			<td>${productVO.pro_intro}</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/product/product.do">
			     <input type="submit" value="修改"> 
			     <input type="hidden" name="pro_no" value="${productVO.pro_no}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/product/product.do">
			    <input type="submit" value="刪除">
			    <input type="hidden" name="pro_no" value="${productVO.pro_no}">
			    <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
			   <input type="hidden" name="action"value="delete"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>

<br>本網頁的路徑:<br><b>
   <font color=blue>request.getServletPath():</font> <%= request.getServletPath()%><br>
   <font color=blue>request.getRequestURI(): </font> <%= request.getRequestURI()%> </b>
</body>
</html>
