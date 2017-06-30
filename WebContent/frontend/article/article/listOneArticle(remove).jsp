<%@ page contentType="text/html; charset=Big5"%>
<%@ page import="com.article.model.*"%>
<%
ArticleVO articleVO = (ArticleVO) request.getAttribute("articleVO"); //ArticleServlet.java(Concroller), 存入req的articleVO物件
%>
<%-- 取出 對應的Article_ClassificationVO物件--%>
<%
  Article_ClassificationService article_classificationSvc = new Article_ClassificationService();
  Article_ClassificationVO article_classificationVO = article_classificationSvc.getOneArticle_Classifiction(articleVO.getArtc_no());
%>
<%request.setAttribute("article_classificationVO", article_classificationVO); %>
<html>
<head>
<title>文章資料 - listOneQArticle.jsp</title>
</head>
<body bgcolor='white'>

<table border='1' cellpadding='5' cellspacing='0' width='1000'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>問題資料 - ListOneArticle.jsp</h3>
		<a href="<%=request.getContextPath()%>/frontend/article/article/select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a>
		</td>
	</tr>
</table>

<table border='1' bordercolor='#CCCCFF' width='1000'>
	<tr>
		<th>文章編號</th>
		<th>會員編號(發文者)</th>
		<th>文章分類編號</th>
		<th>文章主旨</th>
		<th>文章發表時間</th>
		<th>文章內容</th>
		<th>文章瀏覽數</th>
	</tr>
	<tr align='center' valign='middle'>
			<td>${articleVO.art_no}</td>
			<td>${articleVO.mem_no}</td>
			<td>${articleVO.artc_no}【${article_classificationVO.artc_name}】</td>
			<td>${articleVO.art_title}</td>
			<td>${articleVO.art_date}</td>
			<td>${articleVO.art_cnt}</td>
			<td>${articleVO.art_vcnt}</td>
	</tr>
</table>

</body>
</html>
