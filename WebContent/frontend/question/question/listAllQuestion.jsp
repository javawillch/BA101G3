<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.question.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
	QuestionService questionSvc = new QuestionService();
    List<QuestionVO> list = questionSvc.getAll();
    pageContext.setAttribute("list",list);
%>
<jsp:useBean id="question_classificationSvc" scope="page" class="com.question.model.Question_ClassificationService" />

<html>
<head>
<title>所有員工資料 - listAllEmp1_byDAO.jsp</title>
</head>
<body bgcolor='white'>
<b><font color=red>此頁練習採用 EL 的寫法取值:</font></b>
<table border='1' cellpadding='5' cellspacing='0' width='1200'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>所有員工資料 - listAllQuestion.jsp</h3>
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

<table border='1' bordercolor='#CCCCFF' width='1200'>
	<tr>
		<th>問題編號</th>
		<th>會員編號(提問者)</th>
		<th>問題分類編號</th>
		<th>問題主旨</th>
		<th>問題詢問時間</th>
		<th>問題內容</th>
		<th>修改</th>
		<th>刪除</th>
	</tr>
	<%@ include file="page1.file" %> 
	<c:forEach var="questionVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		<tr align='center' valign='middle' ${(questionVO.qu_no==param.qu_no)? 'bgcolor=#CCFFCC':''}>
			<td>${questionVO.qu_no}</td>
			<td>${questionVO.mem_no}</td>
			<td><c:forEach var="question_classificationVO" items="${question_classificationSvc.all}">
                    <c:if test="${questionVO.quec_no==question_classificationVO.quec_no}">
	                    ${question_classificationVO.quec_no}【${question_classificationVO.quec_name}】
                    </c:if>
                </c:forEach></td>
<%-- 			<td>${questionVO.mem_no}</td> --%>
			<td>${questionVO.qu_title}</td>
			<td>${questionVO.qu_date}</td>
			<td>${questionVO.qu_cnt}</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/question/question.do">
			     <input type="submit" value="修改"> 
			     <input type="hidden" name="qu_no" value="${questionVO.qu_no}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
			    <input type="hidden" name="whichPage"	value="<%=whichPage%>">               <!--送出當前是第幾頁給Controller-->
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/question/question.do">
			    <input type="submit" value="刪除">
			    <input type="hidden" name="qu_no" value="${questionVO.qu_no}">
			    <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
			    <input type="hidden" name="whichPage"	value="<%=whichPage%>">               <!--送出當前是第幾頁給Controller-->
			    <input type="hidden" name="action"value="delete"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>
<%@ include file="page2.file" %>

<br>本網頁的路徑:<br><b>
   <font color=blue>request.getServletPath():</font> <%= request.getServletPath()%><br>
   <font color=blue>request.getRequestURI(): </font> <%= request.getRequestURI()%> </b>

</body>
</html>