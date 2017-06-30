<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.shopping.model.*"%>
<%
ProductVO productVO = (ProductVO) request.getAttribute("productVO");
%>

<html>
<head>
	<title>商品新增 - addProduct.jsp</title>
	<script src="ckeditor/ckeditor.js"></script>
	<script
	  src="https://code.jquery.com/jquery-1.12.4.js"
	  integrity="sha256-Qw82+bXyGq6MydymqBxNPYTaUXXq7c8v3CwiYwLLNXU="
	  crossorigin="anonymous">
	</script>
	<script type="text/javascript">
		$(document).ready(function(){
			var editor = CKEDITOR.replace('content');
			CKFinder.setupCKEditor(editor,'/ckfinder/');
			console.log("ready");
		});
	</script>
	<script src="upload_1.js"></script> 
</head>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/wu/js/calendar.css">
<script language="JavaScript" src="<%=request.getContextPath()%>/wu/js/calendarcode.js"></script>
<div id="popupcalendar" class="text"></div>

<body bgcolor='white' onload="init();">

<table border='1' cellpadding='5' cellspacing='0' width='400'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>商品新增 - addProduct.jsp</h3>
		</td>
		<td>
		   <a href="<%=request.getContextPath()%>/frontend/shopping/product/select_page.jsp"><img src="<%=request.getContextPath()%>/wu/images/tomcat.gif" width="100" height="100" border="1">回首頁</a>
	    </td>
	</tr>
</table>

<h3>問題資料:</h3>
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

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/product/product.do" name="form1" enctype="multipart/form-data">
<table border="0">

	<tr>
		<td>商品編號:</td>
		<td><input type="TEXT" name="pro_no" size="45" 
			value="<%= (productVO==null)? "PRO10001" : productVO.getPro_no()%>" /></td>
	</tr>
	<tr>
		<td>會員編號(賣家):</td>
		<td><input type="TEXT" name="mem_no" size="45"
			value="<%= (productVO==null)? "M0000001" : productVO.getMem_no()%>" /></td>
	</tr>

	<tr>
		<%java.sql.Timestamp timestamp_SQL = new java.sql.Timestamp(System.currentTimeMillis());%>
		<td>商品上架時間:</td>
		<td bgcolor="#CCCCFF">
		    <input class="cal-TextBox"
			onFocus="this.blur()" size="9" readonly type="text" name="pro_date" value="<%= (productVO==null)? timestamp_SQL : productVO.getPro_date()%>">
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
		<td><input type="TEXT" name="pro_name" size="45"
			value="<%= (productVO==null)? "(預設)商品標題請新增" : productVO.getPro_name()%>" /></td>
	</tr>
	
	<tr>
		<td>商品價格:</td>
		<td><input type="TEXT" name="pro_price" size="45"
			value="<%= (productVO==null)? "1000" : productVO.getPro_price()%>" /></td>
	</tr>
	
  	<tr>
  		<td>選擇照片:</td>
		<td><input name="pro_photo" id="file_selector" type="file" value="" onchange="file_viewer.load();"/>
		    <input name="pro_photo" type="file" id="myFile" multiple>
		</td>
	</tr>

	<div>
		Name: <input id="show_filename" type="text"readonly="true" value=""/><br/>
		Size: <input id="show_filesize" type="text"readonly="true" value=""/>Bytes
		<br/>
<!-- 	<textarea id="show_data" readonly="true" cols="60" rows="2">
		</textarea>
 		<br/>
-->		<img id="show_image" alt="Show Image" />
	</div>
	
	<tr>
		<td>商品內容:</td>
		<td>
			<textarea id="content" name="pro_intro" rows="15" cols="30"></textarea>
			<script>
				CKEDITOR.replace( 'content', {});
			</script>
		</td>
	</tr>

	<jsp:useBean id="product_classificationSvc" scope="page" class="com.shopping.model.Product_ClassificationService" />
	<tr>
		<td>商品分類:<font color=red><b>*</b></font></td>
		<td><select size="1" name="proc_no">
			<c:forEach var="product_classificationVO" items="${product_classificationSvc.all}">
				<option value="${product_classificationVO.proc_no}" ${(productVO.proc_no==product_classificationVO.proc_no)? 'selected':'' } >${product_classificationVO.proc_name}
			</c:forEach>
		</select></td>
	</tr>
	<tr>
		<td>商品狀態:</td>
		<td><input type="TEXT" name="pro_stat" size="45"
			value="<%= (productVO==null)? "0" : productVO.getPro_stat()%>" /></td>
	</tr>
	<tr>
		<td>付款方式:</td>
		<td><input type="TEXT" name="pro_pay" size="45"
			value="<%= (productVO==null)? "0" : productVO.getPro_pay()%>" /></td>
	</tr>
	<tr>
		<td>送貨方式:</td>
		<td><input type="TEXT" name="pro_get" size="45"
			value="<%= (productVO==null)? "0" : productVO.getPro_get()%>" /></td>
	</tr>

</table>
<br>
<input type="hidden" name="action" value="insert">
<input type="submit" value="送出新增"></FORM>
</body>

</html>
