<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.article.model.*"%>
<%-- �����m�߱ĥ� EL ���g�k���� --%>

<%
//	QuestionService questionSvc = new QuestionService();
//   List<QuestionVO> list = questionSvc.getAll();
//    pageContext.setAttribute("list",list);
%>
<jsp:useBean id="article_classificationSvc" scope="page" class="com.article.model.Article_ClassificationService" />

<html>
<head>
<title>�Ҧ��峹���� - listAllArticle_Classification.jsp</title>
</head>
<body bgcolor='white'>
<b><font color=red>�����m�߱ĥ� EL ���g�k����:</font></b>
<table border='1' cellpadding='5' cellspacing='0' width='800'>
	<tr bgcolor='orange' align='center' valign='middle' height='20'>
		<td>
		<h3>�Ҧ����D���� - listAllArticle_Classification.jsp</h3>
		<a href="<%=request.getContextPath()%>/frontend/article/article/select_page.jsp"><img src="<%=request.getContextPath()%>/wu/images/back1.gif" width="100" height="32" border="0">�^����</a>
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
		<th>�峹�����s��</th>
		<th>�峹�����W��</th>
		<th>�ק�</th>
		<th>�R��<font color=red>(���p���ջP���-�p��)</font></th>
		<th>�d�ߦU���O���峹</th>
	</tr>
	
	<c:forEach var="article_classificationVO" items="${article_classificationSvc.all}">
		<tr align='center' valign='middle'>
			<td>${article_classificationVO.artc_no}</td>
			<td>${article_classificationVO.artc_name}</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/article_classification/article_classification.do">
			    <input type="submit" value="�ק�" disabled="true"> 
			    <input type="hidden" name="artc_no" value="${article_classificationVO.artc_no}">
			    <input type="hidden" name="action" value="getOne_For_Update_Dept">
			</td></FORM>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/article_classification/article_classification.do">
			    <input type="submit" value="�R��">
			    <input type="hidden" name="artc_no" value="${article_classificationVO.artc_no}">
			    <input type="hidden" name="action" value="delete_Article_Classification">
			</td></FORM>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/article_classification/article_classification.do">
			    <input type="submit" value="�e�X�d��"> 
			    <input type="hidden" name="artc_no" value="${article_classificationVO.artc_no}">
			    <input type="hidden" name="action" value="listArticles_ByArtc_no_B">
			</td></FORM>
		</tr>
	</c:forEach>
</table>

<%if (request.getAttribute("listArticles_ByArtc_no")!=null){%>
       <jsp:include page="listArticles_ByArtc_no.jsp" />
<%} %>

</body>
</html>
