<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.shopping.model.*"%>
<%-- �����m�߱ĥ� EL ���g�k���� --%>

<%
	ProductService productSvc = new ProductService();
    List<ProductVO> list = productSvc.getAll();
    pageContext.setAttribute("list",list);
%>
<jsp:useBean id="product_classificationSvc" scope="page" class="com.shopping.model.Product_ClassificationService" />

<html>
<head>
<title>�Ҧ��ӫ~��� - listAllProduct.jsp</title>
</head>
<body bgcolor='white'>
<b><font color=red>�����m�߱ĥ� EL ���g�k����:</font></b>
<table border='1' cellpadding='5' cellspacing='0' width='1200'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>�Ҧ��ӫ~��� - listAllProduct.jsp</h3>
		<a href="<%=request.getContextPath()%>/frontend/shopping/product/select_page.jsp"><img src="<%=request.getContextPath()%>/wu/images/back1.gif" width="100" height="32" border="0">�^����</a>
		</td>
	</tr>
</table>

<%-- ���~��C --%>
<c:if test="${not empty errorMsgs}">
	<font color='red'>�Эץ��H�U���~:
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li>${message}</li>
		</c:forEach>
	</ul>
	</font>
</c:if>

<table border='1' bordercolor='#CCCCFF' width='1200'>
	<tr>
		<th>�ӫ~�s��</th>
		<th>�|���s��(��a)</th>
		<th>�ӫ~�����s��</th>
		<th>�W�����</th>
		<th>�ӫ~�W��</th>
		<th>�ӫ~����</th>
		<th>�ӫ~����</th>
		<th>�ӫ~�Ϥ�</th>
		<th>�ӫ~�Ϥ�1</th>
		<th>�ӫ~�Ϥ�2</th>
		<th>�ӫ~�Ϥ�3</th>
		<th>�ӫ~�Ϥ�4</th>
		<th>�ӫ~�Ϥ�5</th>
		<th>�ӫ~���A</th>
		<th>�I�ڤ覡</th>
		<th>�e�f�覡</th>
		<th>�ק�</th>
		<th>�R��</th>
	</tr>
	<%@ include file="page1.file" %> 
	<c:forEach var="productVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		<tr align='center' valign='middle' ${(productVO.pro_no==param.pro_no)? 'bgcolor=#CCFFCC':''}>
			<td>${productVO.pro_no}</td>
			<td>${productVO.mem_no}</td>
			<td><c:forEach var="product_classificationVO" items="${product_classificationSvc.all}">
                    <c:if test="${productVO.proc_no==product_classificationVO.proc_no}">
	                    ${product_classificationVO.proc_no}�i${product_classificationVO.proc_name}�j
                    </c:if>
                </c:forEach></td>
			<td>${productVO.pro_date}</td>
			<td>${productVO.pro_name}</td>
			<td>${productVO.pro_price}</td>
			<td>${productVO.pro_intro}</td>
			<td><img src="<%=request.getContextPath()%>/frontend/DBPicReader/DBPicReader.do?id_no=${productVO.pro_no}&action=product" width="120"></td>
			<td>${productVO.pro_photo1}</td>
			<td>${productVO.pro_photo2}</td>
			<td>${productVO.pro_photo3}</td>
			<td>${productVO.pro_photo4}</td>
			<td>${productVO.pro_photo5}</td>
			<td>${productVO.pro_stat}</td>
			<td>${productVO.pro_pay}</td>
			<td>${productVO.pro_get}</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/product/product.do">
			     <input type="submit" value="�ק�"> 
			     <input type="hidden" name="pro_no" value="${productVO.pro_no}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--�e�X�����������|��Controller-->
			    <input type="hidden" name="whichPage"	value="<%=whichPage%>">               <!--�e�X��e�O�ĴX����Controller-->
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/product/product.do">
			    <input type="submit" value="�R��">
			    <input type="hidden" name="pro_no" value="${productVO.pro_no}">
			    <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--�e�X�����������|��Controller-->
			    <input type="hidden" name="whichPage"	value="<%=whichPage%>">               <!--�e�X��e�O�ĴX����Controller-->
			    <input type="hidden" name="action"value="delete"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>
<%@ include file="page2.file" %>

<br>�����������|:<br><b>
   <font color=blue>request.getServletPath():</font> <%= request.getServletPath()%><br>
   <font color=blue>request.getRequestURI(): </font> <%= request.getRequestURI()%> </b>

</body>
</html>