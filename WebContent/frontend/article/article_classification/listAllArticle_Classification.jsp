<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.article.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
//	QuestionService questionSvc = new QuestionService();
//   List<QuestionVO> list = questionSvc.getAll();
//    pageContext.setAttribute("list",list);
%>
<jsp:useBean id="article_classificationSvc" scope="page" class="com.article.model.Article_ClassificationService" />

<html>
<head>
<title>所有文章分類 - listAllArticle_Classification.jsp</title>
</head>
<body bgcolor='white'>
<b><font color=red>此頁練習採用 EL 的寫法取值:</font></b>
<table border='1' cellpadding='5' cellspacing='0' width='800'>
	<tr bgcolor='orange' align='center' valign='middle' height='20'>
		<td>
		<h3>所有問題分類 - listAllArticle_Classification.jsp</h3>
		<a href="<%=request.getContextPath()%>/frontend/article/article/select_page.jsp"><img src="<%=request.getContextPath()%>/wu/images/back1.gif" width="100" height="32" border="0">回首頁</a>
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
		<th>文章分類編號</th>
		<th>文章分類名稱</th>
		<th>修改</th>
		<th>刪除<font color=red>(關聯測試與交易-小心)</font></th>
		<th>查詢各類別的文章</th>
	</tr>
	
	<c:forEach var="article_classificationVO" items="${article_classificationSvc.all}">
		<tr align='center' valign='middle'>
			<td>${article_classificationVO.artc_no}</td>
			<td>${article_classificationVO.artc_name}</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/article_classification/article_classification.do">
			    <input type="submit" value="修改" disabled="true"> 
			    <input type="hidden" name="artc_no" value="${article_classificationVO.artc_no}">
			    <input type="hidden" name="action" value="getOne_For_Update_Dept">
			</td></FORM>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/article_classification/article_classification.do">
			    <input type="submit" value="刪除">
			    <input type="hidden" name="artc_no" value="${article_classificationVO.artc_no}">
			    <input type="hidden" name="action" value="delete_Article_Classification">
			</td></FORM>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/article_classification/article_classification.do">
			    <input type="submit" value="送出查詢"> 
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
