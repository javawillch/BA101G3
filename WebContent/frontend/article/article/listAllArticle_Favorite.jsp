<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.article.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
	Article_FavoriteService article_favoriteSvc = new Article_FavoriteService();
    List<Article_FavoriteVO> list = article_favoriteSvc.getAll();
    pageContext.setAttribute("list",list);
%>

<jsp:useBean id="article_classificationSvc" scope="page" class="com.article.model.Article_ClassificationService" />
<jsp:useBean id="article_messageSvc" scope="page" class="com.article.model.Article_MessageService" />
<html>
<head>
<title>所有文章收藏資料 - listAllArticle_Favorite.jsp</title>
</head>
<body bgcolor='white'>
<b><font color=red>此頁練習採用 EL 的寫法取值:</font></b>
<table border='1' cellpadding='5' cellspacing='0' width='1200'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>所有文章收藏資料 - listAllArticle_Favorite.jsp</h3>
		<a href="<%=request.getContextPath()%>/frontend/article/article/select_page.jsp"><img src="../../../wu/images/back1.gif" width="100" height="32" border="0">回首頁</a>
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
		<th>文章編號</th>
		<th>會員編號</th>

	</tr>
	<%@ include file="page1.file" %> 
	<c:forEach var="article_favoriteVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		<tr align='center' valign='middle'>
			<td>${article_favoriteVO.art_no}</td>
			<td>${article_favoriteVO.mem_no}</td>

		</tr>
	</c:forEach>
</table>
<%@ include file="page2.file" %>

<br>本網頁的路徑:<br><b>
   <font color=blue>request.getServletPath():</font> <%= request.getServletPath()%><br>
   <font color=blue>request.getRequestURI(): </font> <%= request.getRequestURI()%> </b>

</body>
</html>


