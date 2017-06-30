<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.question.model.*"%>
<%
	QuestionVO questionVO = (QuestionVO) request.getAttribute("questionVO"); //QuestionServlet.java (Concroller), �s�Jreq��empVO���� (�]�A�������X��empVO, �]�]�A��J��ƿ��~�ɪ�empVO����)
%>
<html>
<head>
<title>���D��ƭק� - update_question_input.jsp</title></head>
<link rel="stylesheet" type="text/css" href="js/calendar.css">
<script language="JavaScript" src="<%=request.getContextPath()%>/wu/js/calendarcode.js"></script>
<div id="popupcalendar" class="text"></div>

<body bgcolor='white'>

<table border='1' cellpadding='5' cellspacing='0' width='400'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>�ק���D��� - update_question_input.jsp</h3>
		<a href="<%=request.getContextPath()%>/frontend/question/question/select_page.jsp"><img src="<%=request.getContextPath()%>/wu/images/back1.gif" width="100" height="32" border="0">�^����</a></td>
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

<FORM METHOD="post" ACTION=""<%=request.getContextPath()%>/frontend/question/question.do"" name="form1">
<table border="0">
	<tr>
		<td>���D�s��:<font color=red><b>*</b></font></td>
		<td><%=questionVO.getQu_no()%></td>
	</tr>
	<tr>
		<td>�|���s��:</td>
		<td><input type="TEXT" name="mem_no" size="45" value="<%=questionVO.getMem_no()%>" /></td>
	</tr>
	<tr>
		<td>�峹���D:</td>
		<td><input type="TEXT" name="qu_title" size="45"	value="<%=questionVO.getQu_title()%>" /></td>
	</tr>
	<tr>
		<td>���D���ݤ��:</td>
		<td bgcolor="#CCCCFF">
		    <input class="cal-TextBox"
			onFocus="this.blur()" size="9" readonly type="text" name="qu_date" value="<%=questionVO.getQu_date()%>">
			<a class="so-BtnLink"
			href="javascript:calClick();return false;"
			onmouseover="calSwapImg('BTN_date', 'img_Date_OVER',true);"
			onmouseout="calSwapImg('BTN_date', 'img_Date_UP',true);"
			onclick="calSwapImg('BTN_date', 'img_Date_DOWN');showCalendar('form1','hiredate','BTN_date');return false;">
		    <img align="middle" border="0" name="BTN_date"	src="images/btn_date_up.gif" width="22" height="17" alt="�}�l���"></a>
		</td>
	</tr>
	<tr>
		<td>�峹���e:</td>
		<td><input type="TEXT" name="qu_cnt" size="45"	value="<%=questionVO.getQu_cnt()%>" /></td>
	</tr>


		<jsp:useBean id="question_classificationSvc" scope="page" class="com.question.model.Question_ClassificationService" />
	<tr>
		<td>���D����:<font color=red><b>*</b></font></td>
		<td><select size="1" name="quec_no">
			<c:forEach var="question_classificationVO" items="${question_classificationSvc.all}">
				<option value="${question_classificationVO.quec_no}" ${(questionVO.quec_no==question_classificationVO.quec_no)? 'selected':'' } >${question_classificationVO.quec_name}
			</c:forEach>
		</select></td>
	</tr>

</table>
<br>
<input type="hidden" name="action" value="update">
<input type="hidden" name="qu_no" value="<%=questionVO.getQu_no()%>">
<input type="hidden" name="requestURL" value="<%=request.getParameter("requestURL")%>"><!--������e�X�ק諸�ӷ��������|��,�A�e��Controller�ǳ���椧��-->
<input type="hidden" name="whichPage" value="<%=request.getParameter("whichPage")%>">  <!--�u�Ω�:listAllQuestion.jsp-->
<input type="submit" value="�e�X�ק�"></FORM>

<br>�e�X�ק諸�ӷ��������|:<br><b>
   <font color=blue>request.getParameter("requestURL"):</font> <%= request.getParameter("requestURL")%><br>
   <font color=blue>request.getParameter("whichPage"):</font> <%= request.getParameter("whichPage")%> (���d�ҥثe�u�Ω�:istAllEmp.jsp))</b>

</body>
</html>
