<%@ page contentType="text/html; charset=Big5"%>
<%@ page import="com.article.model.*"%>
<%
ArticleVO articleVO = (ArticleVO) request.getAttribute("articleVO"); //ArticleServlet.java(Concroller), �s�Jreq��articleVO����
%>
<%-- ���X ������Article_ClassificationVO����--%>
<%
  Article_ClassificationService article_classificationSvc = new Article_ClassificationService();
  Article_ClassificationVO article_classificationVO = article_classificationSvc.getOneArticle_Classifiction(articleVO.getArtc_no());
%>
<%request.setAttribute("article_classificationVO", article_classificationVO); %>
<html>
<head>
<title>�峹��� - listOneQArticle.jsp</title>
</head>
<body bgcolor='white'>

<table border='1' cellpadding='5' cellspacing='0' width='1000'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>���D��� - ListOneArticle.jsp</h3>
		<a href="<%=request.getContextPath()%>/frontend/article/article/select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">�^����</a>
		</td>
	</tr>
</table>

<table border='1' bordercolor='#CCCCFF' width='1000'>
	<tr>
		<th>�峹�s��</th>
		<th>�|���s��(�o���)</th>
		<th>�峹�����s��</th>
		<th>�峹�D��</th>
		<th>�峹�o��ɶ�</th>
		<th>�峹���e</th>
		<th>�峹�s����</th>
	</tr>
	<tr align='center' valign='middle'>
			<td>${articleVO.art_no}</td>
			<td>${articleVO.mem_no}</td>
			<td>${articleVO.artc_no}�i${article_classificationVO.artc_name}�j</td>
			<td>${articleVO.art_title}</td>
			<td>${articleVO.art_date}</td>
			<td>${articleVO.art_cnt}</td>
			<td>${articleVO.art_vcnt}</td>
	</tr>
</table>

</body>
</html>
