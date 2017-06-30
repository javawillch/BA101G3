<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.question.model.*"%>
<%-- �����m�߱ĥ� EL ���g�k���� --%>

<%
//	QuestionService questionSvc = new QuestionService();
//   List<QuestionVO> list = questionSvc.getAll();
//    pageContext.setAttribute("list",list);
%>
<jsp:useBean id="question_classificationSvc" scope="page" class="com.question.model.Question_ClassificationService" />

<html>
<head>
<title>�Ҧ����D���� - listAllQuestion_Classification.jsp</title>
</head>
<body bgcolor='white'>
<b><font color=red>�����m�߱ĥ� EL ���g�k����:</font></b>
<table border='1' cellpadding='5' cellspacing='0' width='800'>
	<tr bgcolor='orange' align='center' valign='middle' height='20'>
		<td>
		<h3>�Ҧ����D���� - listAllQuestion_Classification.jsp</h3>
		<a href="<%=request.getContextPath()%>/frontend/question/question/select_page.jsp"><img src="<%=request.getContextPath()%>/wu/images/back1.gif" width="100" height="32" border="0">�^����</a>
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
		<th>���D�����s��</th>
		<th>���D�����W��</th>
<%--		<th>������a</th>--%>
		<th>�ק�</th>
		<th>�R��<font color=red>(���p���ջP���-�p��)</font></th>
		<th>�d�ߦU���O�����D</th>
	</tr>
	
	<c:forEach var="question_classificationVO" items="${question_classificationSvc.all}">
		<tr align='center' valign='middle'>
			<td>${question_classificationVO.quec_no}</td>
			<td>${question_classificationVO.quec_name}</td>
<%--			<td>${deptVO.loc}</td>--%>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/question_classification/question_classification.do">
			    <input type="submit" value="�ק�" disabled="true"> 
			    <input type="hidden" name="quec_no" value="${question_classificationVO.quec_no}">
			    <input type="hidden" name="action" value="getOne_For_Update_Dept">
			</td></FORM>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/question_classification/question_classification.do">
			    <input type="submit" value="�R��">
			    <input type="hidden" name="quec_no" value="${question_classificationVO.quec_no}">
			    <input type="hidden" name="action" value="delete_Question_Classification">
			</td></FORM>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/question_classification/question_classification.do">
			    <input type="submit" value="�e�X�d��"> 
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
