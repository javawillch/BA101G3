<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.question.model.*"%>
<%
QuestionVO questionVO = (QuestionVO) request.getAttribute("questionVO");
%>

<html>
<head>
	<title>問題新增 - addQuestion.jsp</title>
	<!--CKEditor-->
	<script src="../../../utility/ckeditor/ckeditor.js"></script>
	<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
	<link rel="stylesheet" href="../../../utility/ckeditor/contents.css">
	<script src="../../../utility/ckfinder/ckfinder.js"></script>
	<script>
		$(document).ready(function() {
			var editor = CKEDITOR.replace( 'qu_cnt', {
				width:680,
				filebrowserBrowseUrl : '../../../utility/ckfinder/ckfinder.html',
				filebrowserImageBrowseUrl : '../../../utility/ckfinder/ckfinder.html?type=Images', 
				filebrowserFlashBrowseUrl : '../../../utility/ckfinder/ckfinder.html?type=Flash',
				filebrowserUploadUrl : '../../../utility/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files', 
				filebrowserImageUploadUrl : '../../../utility/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images', 
				filebrowserFlashUploadUrl : '../../../utility/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash' 
			});
			CKFinder.setupCKEditor( editor, '/ckfinder/' );
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
		<h3>問題新增 - addQuestion.jsp</h3>
		</td>
		<td>
		   <a href="<%=request.getContextPath()%>/frontend/question/question/select_page.jsp"><img src="<%=request.getContextPath()%>/wu/images/tomcat.gif" width="100" height="100" border="1">回首頁</a>
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

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/question/question.do" name="form1">
<table border="0">

	<tr>
		<td>問題編號:</td>
		<td><input type="TEXT" name="qu_no" size="45" 
			value="<%= (questionVO==null)? "Q1000001" : questionVO.getQu_no()%>" /></td>
	</tr>
	<tr>
		<td>會員編號(提問者):</td>
		<td><input type="TEXT" name="mem_no" size="45"
			value="<%= (questionVO==null)? "M0000001" : questionVO.getMem_no()%>" /></td>
	</tr>
	<tr>
		<%java.sql.Timestamp timestamp_SQL = new java.sql.Timestamp(System.currentTimeMillis());%>
		<td>問題詢問時間:</td>
		<td bgcolor="#CCCCFF">
		    <input class="cal-TextBox"
			onFocus="this.blur()" size="9" readonly type="text" name="qu_date" value="<%= (questionVO==null)? timestamp_SQL : questionVO.getQu_date()%>">
			<a class="so-BtnLink"
			href="javascript:calClick();return false;"
			onmouseover="calSwapImg('BTN_date', 'img_Date_OVER',true);"
			onmouseout="calSwapImg('BTN_date', 'img_Date_UP',true);"
			onclick="calSwapImg('BTN_date', 'img_Date_DOWN');showCalendar('form1','hiredate','BTN_date');return false;">
		    <img align="middle" border="0" name="BTN_date"	src="images/btn_date_up.gif" width="22" height="17" alt="開始日期"></a>
		</td>
	</tr>
	<tr>
		<td>問題主旨:</td>
		<td><input type="TEXT" name="qu_title" size="45"
			value="<%= (questionVO==null)? "(預設)問題標題請新增" : questionVO.getQu_title()%>" /></td>
	</tr>
	
  	<tr>
  		<td>選擇照片:</td>
		<td><input id="file_selector" type="file" value="" onchange="file_viewer.load();"/></td>
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
		<td>問題內容:</td>
		<td>
			<textarea id="qu_cnt" name="qu_cnt" rows="15" cols="30"></textarea>
			<script>
				CKEDITOR.replace( 'content', {});
			</script>
		</td>
	</tr>

	<jsp:useBean id="question_classificationSvc" scope="page" class="com.question.model.Question_ClassificationService" />
	<tr>
		<td>問題分類:<font color=red><b>*</b></font></td>
		<td><select size="1" name="quec_no">
			<c:forEach var="question_classificationVO" items="${question_classificationSvc.all}">
				<option value="${question_classificationVO.quec_no}" ${(questionVO.quec_no==question_classificationVO.quec_no)? 'selected':'' } >${question_classificationVO.quec_name}
			</c:forEach>
		</select></td>
	</tr>

</table>
<br>
<input type="hidden" name="action" value="insert">
<input type="submit" value="送出新增"></FORM>
</body>

</html>
