<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.shopping.model.*"%>
<%-- �����m�߱ĥ� EL ���g�k���� --%>

<%
//	ProductService productSvc = new ProductService();
//   List<ProductVO> list = productSvc.getAll();
//    pageContext.setAttribute("list",list);
%>
<jsp:useBean id="product_classificationSvc" scope="page" class="com.shopping.model.Product_ClassificationService" />

<html>
<head>
<title>�Ҧ��ӫ~���� - listAllProduct_Classification.jsp</title>
</head>
<body bgcolor='white'>
<b><font color=red>�����m�߱ĥ� EL ���g�k����:</font></b>
<table border='1' cellpadding='5' cellspacing='0' width='800'>
	<tr bgcolor='orange' align='center' valign='middle' height='20'>
		<td>
		<h3>�Ҧ��ӫ~����  - listAllProduct_Classification.jsp</h3>
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

<table border='1' bordercolor='#CCCCFF' width='800'>
	<tr>
		<th>�ӫ~�����s��</th>
		<th>�ӫ~�����W��</th>
		<th>�ק�</th>
		<th>�R��<font color=red>(���p���ջP���-�p��)</font></th>
		<th>�d�ߦU���O���ӫ~</th>
	</tr>
	
	<c:forEach var="product_classificationVO" items="${product_classificationSvc.all}">
		<tr align='center' valign='middle'>
			<td>${prodcut_classificationVO.proc_no}</td>
			<td>${prodcut_classificationVO.proc_name}</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/product_classification/product_classification.do">
			    <input type="submit" value="�ק�" disabled="true"> 
			    <input type="hidden" name="proc_no" value="${product_classificationVO.proc_no}">
			    <input type="hidden" name="action" value="getOne_For_Update_Product_Classification">
			</td></FORM>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/product_classification/product_classification.do">
			    <input type="submit" value="�R��">
			    <input type="hidden" name="proc_no" value="${product_classificationVO.proc_no}">
			    <input type="hidden" name="action" value="delete_Product_Classification">
			</td></FORM>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/product_classification/prodcut_classification.do">
			    <input type="submit" value="�e�X�d��"> 
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
