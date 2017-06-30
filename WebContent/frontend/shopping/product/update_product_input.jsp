<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.shopping.model.*"%>
<%
	ProductVO productVO = (ProductVO) request.getAttribute("productVO"); //ProductServlet.java (Concroller), �s�Jreq��empVO���� (�]�A�������X��empVO, �]�]�A��J��ƿ��~�ɪ�empVO����)
%>
<html>
<head>
<title>�ӫ~��ƭק� - update_product_input.jsp</title></head>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/wu/js/calendar.css">
<script language="JavaScript" src="<%=request.getContextPath()%>/wu/js/calendarcode.js"></script>
<div id="popupcalendar" class="text"></div>

<body bgcolor='white'>

<table border='1' cellpadding='5' cellspacing='0' width='400'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>�ק���D��� - update_question_input.jsp</h3>
		<a href="<%=request.getContextPath()%>/frontend/shopping/product/select_page.jsp"><img src="<%=request.getContextPath()%>/wu/images/back1.gif" width="100" height="32" border="0">�^����</a></td>
	</tr>
</table>

<h3>��ƭק�:</h3>
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

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/product/product.do" name="form1">
<table border="0">
	<tr>
		<td>�ӫ~�s��:<font color=red><b>*</b></font></td>
		<td><%=productVO.getPro_no()%></td>
	</tr>
	<tr>
		<td>�|���s��(��a):</td>
		<td><input type="TEXT" name="mem_no" size="45" value="<%=productVO.getMem_no()%>" /></td>
	</tr>
	<jsp:useBean id="product_classificationSvc" scope="page" class="com.shopping.model.Product_ClassificationService" />
	<tr>
		<td>�ӫ~�������D:</td>
		<td><select size="1" name="proc_no">
			<c:forEach var="product_classificationVO" items="${product_classificationSvc.all}">
				<option value="${product_classificationVO.proc_no}" ${(productVO.proc_no==product_classificationVO.proc_no)? 'selected':'' } >${product_classificationVO.proc_name}
			</c:forEach>
		</select></td>
	</tr>
	<tr>
		<td>�ӫ~�W�[���:</td>
		<td bgcolor="#CCCCFF">
		    <input class="cal-TextBox"
			onFocus="this.blur()" size="9" readonly type="text" name="qu_date" value="<%=productVO.getPro_date()%>">
			<a class="so-BtnLink"
			href="javascript:calClick();return false;"
			onmouseover="calSwapImg('BTN_date', 'img_Date_OVER',true);"
			onmouseout="calSwapImg('BTN_date', 'img_Date_UP',true);"
			onclick="calSwapImg('BTN_date', 'img_Date_DOWN');showCalendar('form1','hiredate','BTN_date');return false;">
		    <img align="middle" border="0" name="BTN_date"	src="images/btn_date_up.gif" width="22" height="17" alt="�}�l���"></a>
		</td>
	</tr>
	<tr>
		<td>�ӫ~�W��:</td>
		<td><input type="TEXT" name="pro_name" size="45"	value="<%=productVO.getPro_name()%>" /></td>
	</tr>
	<tr>
		<td>�ӫ~����:</td>
		<td><input type="TEXT" name="pro_price" size="45"	value="<%=productVO.getPro_price()%>" /></td>
	</tr>

	<tr>
		<td>�ӫ~����:</td>
		<td>
			<textarea id="content" name="pro_intro" rows="15" cols="30"><%=productVO.getPro_intro()%></textarea>
			<script>
				CKEDITOR.replace( 'content', {});
			</script>
		</td>
	</tr>	
	
		<tr>
		<td>�ӫ~���A:</td>
		<td><input type="TEXT" name="pro_stat" size="45"	value="<%=productVO.getPro_stat()%>" /></td>
	</tr>
	
	<tr>
		<td>�ӫ~�I��:</td>
		<td><input type="TEXT" name="pro_pay" size="45"	value="<%=productVO.getPro_pay()%>" /></td>
	</tr>
	
	<tr>
		<td>�e�f�覡:</td>
		<td><input type="TEXT" name="pro_get" size="45"	value="<%=productVO.getPro_get()%>" /></td>
	</tr>


</table>
<br>
<input type="hidden" name="action" value="update">
<input type="hidden" name="pro_no" value="<%=productVO.getPro_no()%>">
<input type="hidden" name="requestURL" value="<%=request.getParameter("requestURL")%>"><!--������e�X�ק諸�ӷ��������|��,�A�e��Controller�ǳ���椧��-->
<input type="hidden" name="whichPage" value="<%=request.getParameter("whichPage")%>">  <!--�u�Ω�:listAllProduct.jsp-->
<input type="submit" value="�e�X�ק�"></FORM>

<br>�e�X�ק諸�ӷ��������|:<br><b>
   <font color=blue>request.getParameter("requestURL"):</font> <%= request.getParameter("requestURL")%><br>
   <font color=blue>request.getParameter("whichPage"):</font> <%= request.getParameter("whichPage")%> (���d�ҥثe�u�Ω�:istAllEmp.jsp))</b>

</body>
</html>
