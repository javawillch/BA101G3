<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<%-- �����m�߱ĥ� EL ���g�k���� --%>

<jsp:useBean id="listProducts_ByProc_no" scope="request" type="java.util.Set" />
<jsp:useBean id="product_classificationSvc" scope="page" class="com.shopping.model.Product_ClassificationService" />
<html>
<head>
<title>�ӫ~�������D - listProducts_ByProc_no.jsp</title>
</head>
<body bgcolor='white'>

<b><font color=red>�����m�߱ĥ� EL ���g�k����:</font></b>
<table border='1' cellpadding='5' cellspacing='0' width='1000'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>�ӫ~�������D - listProducts_ByProc_no.jsp</h3>
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

<table border='1' bordercolor='#CCCCFF' width='1000'>
	<tr>
		<th>�ӫ~�s��</th>
		<th>�|���s��(��a)</th>
		<th>�ӫ~�����s��</th>
		<th>�ӫ~�W��</th>
		<th>�ӫ~�W�[�ɶ�</th>
		<th>�ӫ~���e</th>
		<th>�ק�</th>
		<th>�R��</th>
	</tr>

	<c:forEach var="productVO" items="${listProducts_ByProc_no}">
		<tr align='center' valign='middle' ${(productVO.pro_no==param.pro_no)? 'bgcolor=#CCFFCC':''}>
			<td>${productVO.pro_no}</td>
			<td>${productVO.mem_no}</td>
			<td><c:forEach var="product_classificationVO" items="${product_classificationSvc.all}">
                    <c:if test="${productVO.proc_no==product_classificationVO.proc_no}">
	                    ${prodcut_classificationVO.proc_no}�i${product_classificationVO.proc_name}�j
                    </c:if>
                </c:forEach></td>
			<td>${productVO.pro_name}</td>
			<td>${productVO.pro_date}</td>
			<td>${productVO.pro_intro}</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/product/product.do">
			     <input type="submit" value="�ק�"> 
			     <input type="hidden" name="pro_no" value="${productVO.pro_no}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--�e�X�����������|��Controller-->
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/product/product.do">
			    <input type="submit" value="�R��">
			    <input type="hidden" name="pro_no" value="${productVO.pro_no}">
			    <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--�e�X�����������|��Controller-->
			   <input type="hidden" name="action"value="delete"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>

<br>�����������|:<br><b>
   <font color=blue>request.getServletPath():</font> <%= request.getServletPath()%><br>
   <font color=blue>request.getRequestURI(): </font> <%= request.getRequestURI()%> </b>
</body>
</html>
