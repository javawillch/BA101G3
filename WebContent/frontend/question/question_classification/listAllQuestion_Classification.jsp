<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.question.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
//	QuestionService questionSvc = new QuestionService();
//   List<QuestionVO> list = questionSvc.getAll();
//    pageContext.setAttribute("list",list);
%>
<jsp:useBean id="question_classificationSvc" scope="page" class="com.question.model.Question_ClassificationService" />

<html>
<head>
<title>所有問題分類 - listAllQuestion_Classification.jsp</title>
</head>
<body bgcolor='white'>
<b><font color=red>此頁練習採用 EL 的寫法取值:</font></b>
<table border='1' cellpadding='5' cellspacing='0' width='800'>
	<tr bgcolor='orange' align='center' valign='middle' height='20'>
		<td>
		<h3>所有問題分類 - listAllQuestion_Classification.jsp</h3>
		<a href="<%=request.getContextPath()%>/frontend/question/question/select_page.jsp"><img src="<%=request.getContextPath()%>/wu/images/back1.gif" width="100" height="32" border="0">回首頁</a>
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
		<th>問題分類編號</th>
		<th>問題分類名稱</th>
<%--		<th>部門基地</th>--%>
		<th>修改</th>
		<th>刪除<font color=red>(關聯測試與交易-小心)</font></th>
		<th>查詢各類別的問題</th>
	</tr>
	
	<c:forEach var="question_classificationVO" items="${question_classificationSvc.all}">
		<tr align='center' valign='middle'>
			<td>${question_classificationVO.quec_no}</td>
			<td>${question_classificationVO.quec_name}</td>
<%--			<td>${deptVO.loc}</td>--%>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/question_classification/question_classification.do">
			    <input type="submit" value="修改" disabled="true"> 
			    <input type="hidden" name="quec_no" value="${question_classificationVO.quec_no}">
			    <input type="hidden" name="action" value="getOne_For_Update_Dept">
			</td></FORM>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/question_classification/question_classification.do">
			    <input type="submit" value="刪除">
			    <input type="hidden" name="quec_no" value="${question_classificationVO.quec_no}">
			    <input type="hidden" name="action" value="delete_Question_Classification">
			</td></FORM>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/question_classification/question_classification.do">
			    <input type="submit" value="送出查詢"> 
			    <input type="hidden" name="quec_no" value="${question_classificationVO.quec_no}">
			    <input type="hidden" name="action" value="listQuestions_ByQuec_no_B">
			</td></FORM>
		</tr>
	</c:forEach>
</table>

<%if (request.getAttribute("listQuestions_ByQuec_no")!=null){%>
       <jsp:include page="listQuestions_ByQuec_no.jsp" />
<%} %>

</body>
</html>
