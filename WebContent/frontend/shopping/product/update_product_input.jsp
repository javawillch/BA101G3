<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.shopping.model.*"%>
<%
	ProductVO productVO = (ProductVO) request.getAttribute("productVO"); //ProductServlet.java (Concroller), 存入req的empVO物件 (包括幫忙取出的empVO, 也包括輸入資料錯誤時的empVO物件)
%>
<html>
<head>
<title>商品資料修改 - update_product_input.jsp</title></head>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/wu/js/calendar.css">
<script language="JavaScript" src="<%=request.getContextPath()%>/wu/js/calendarcode.js"></script>
<div id="popupcalendar" class="text"></div>

<body bgcolor='white'>

<table border='1' cellpadding='5' cellspacing='0' width='400'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>修改問題資料 - update_question_input.jsp</h3>
		<a href="<%=request.getContextPath()%>/frontend/shopping/product/select_page.jsp"><img src="<%=request.getContextPath()%>/wu/images/back1.gif" width="100" height="32" border="0">回首頁</a></td>
	</tr>
</table>

<h3>資料修改:</h3>
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

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/product/product.do" name="form1">
<table border="0">
	<tr>
		<td>商品編號:<font color=red><b>*</b></font></td>
		<td><%=productVO.getPro_no()%></td>
	</tr>
	<tr>
		<td>會員編號(賣家):</td>
		<td><input type="TEXT" name="mem_no" size="45" value="<%=productVO.getMem_no()%>" /></td>
	</tr>
	<jsp:useBean id="product_classificationSvc" scope="page" class="com.shopping.model.Product_ClassificationService" />
	<tr>
		<td>商品分類標題:</td>
		<td><select size="1" name="proc_no">
			<c:forEach var="product_classificationVO" items="${product_classificationSvc.all}">
				<option value="${product_classificationVO.proc_no}" ${(productVO.proc_no==product_classificationVO.proc_no)? 'selected':'' } >${product_classificationVO.proc_name}
			</c:forEach>
		</select></td>
	</tr>
	<tr>
		<td>商品上架日期:</td>
		<td bgcolor="#CCCCFF">
		    <input class="cal-TextBox"
			onFocus="this.blur()" size="9" readonly type="text" name="qu_date" value="<%=productVO.getPro_date()%>">
			<a class="so-BtnLink"
			href="javascript:calClick();return false;"
			onmouseover="calSwapImg('BTN_date', 'img_Date_OVER',true);"
			onmouseout="calSwapImg('BTN_date', 'img_Date_UP',true);"
			onclick="calSwapImg('BTN_date', 'img_Date_DOWN');showCalendar('form1','hiredate','BTN_date');return false;">
		    <img align="middle" border="0" name="BTN_date"	src="images/btn_date_up.gif" width="22" height="17" alt="開始日期"></a>
		</td>
	</tr>
	<tr>
		<td>商品名稱:</td>
		<td><input type="TEXT" name="pro_name" size="45"	value="<%=productVO.getPro_name()%>" /></td>
	</tr>
	<tr>
		<td>商品價格:</td>
		<td><input type="TEXT" name="pro_price" size="45"	value="<%=productVO.getPro_price()%>" /></td>
	</tr>

	<tr>
		<td>商品介紹:</td>
		<td>
			<textarea id="content" name="pro_intro" rows="15" cols="30"><%=productVO.getPro_intro()%></textarea>
			<script>
				CKEDITOR.replace( 'content', {});
			</script>
		</td>
	</tr>	
	
		<tr>
		<td>商品狀態:</td>
		<td><input type="TEXT" name="pro_stat" size="45"	value="<%=productVO.getPro_stat()%>" /></td>
	</tr>
	
	<tr>
		<td>商品付款:</td>
		<td><input type="TEXT" name="pro_pay" size="45"	value="<%=productVO.getPro_pay()%>" /></td>
	</tr>
	
	<tr>
		<td>送貨方式:</td>
		<td><input type="TEXT" name="pro_get" size="45"	value="<%=productVO.getPro_get()%>" /></td>
	</tr>


</table>
<br>
<input type="hidden" name="action" value="update">
<input type="hidden" name="pro_no" value="<%=productVO.getPro_no()%>">
<input type="hidden" name="requestURL" value="<%=request.getParameter("requestURL")%>"><!--接收原送出修改的來源網頁路徑後,再送給Controller準備轉交之用-->
<input type="hidden" name="whichPage" value="<%=request.getParameter("whichPage")%>">  <!--只用於:listAllProduct.jsp-->
<input type="submit" value="送出修改"></FORM>

<br>送出修改的來源網頁路徑:<br><b>
   <font color=blue>request.getParameter("requestURL"):</font> <%= request.getParameter("requestURL")%><br>
   <font color=blue>request.getParameter("whichPage"):</font> <%= request.getParameter("whichPage")%> (此範例目前只用於:istAllEmp.jsp))</b>

</body>
</html>
