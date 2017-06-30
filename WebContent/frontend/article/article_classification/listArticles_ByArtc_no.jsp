<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<%-- �����m�߱ĥ� EL ���g�k���� --%>

<jsp:useBean id="listArticles_ByArtc_no" scope="request" type="java.util.Set" />
<jsp:useBean id="article_classificationSvc" scope="page" class="com.article.model.Article_ClassificationService" />
<html>
<head>
<title>���D�������D - listQuestions_ByQuec_no.jsp</title>
</head>
<body bgcolor='white'>

<b><font color=red>�����m�߱ĥ� EL ���g�k����:</font></b>
<table border='1' cellpadding='5' cellspacing='0' width='1000'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>�U�����峹- listArticles_ByArtc_no.jsp</h3>
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

<table border='1' bordercolor='#CCCCFF' width='1000'>
	<tr>
		<th>�峹�s��</th>
		<th>�|���s��(�o���)</th>
		<th>�峹�����s��</th>
		<th>�峹�D��</th>
		<th>�峹�o��ɶ�</th>
		<th>�峹���e</th>
		<th>�峹�s����</th>
		<th>�ק�</th>
		<th>�R��</th>
	</tr>

	<c:forEach var="articleVO" items="${listArticles_ByArtc_no}">
		<tr align='center' valign='middle' ${(articleVO.art_no==param.art_no)? 'bgcolor=#FFCCCC':''}>
			<td>${articleVO.art_no}</td>
			<td>${articleVO.mem_no}</td>
			<td><c:forEach var="article_classificationVO" items="${article_classificationSvc.all}">
                    <c:if test="${articleVO.artc_no==article_classificationVO.artc_no}">
	                    ${article_classificationVO.artc_no}�i${article_classificationVO.artc_name}�j
                    </c:if>
                </c:forEach></td>
			<td>${articleVO.art_title}</td>
			<td>${articleVO.art_date}</td>
			<td>${articleVO.art_cnt}</td>
			<td>${articleVO.art_vcnt}</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/article/article.do">
			     <input type="submit" value="�ק�"> 
			     <input type="hidden" name="art_no" value="${articleVO.art_no}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--�e�X�����������|��Controller-->
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/article/article.do">
			    <input type="submit" value="�R��">
			    <input type="hidden" name="art_no" value="${articleVO.art_no}">
			    <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--�e�X�����������|��Controller-->
			   <input type="hidden" name="action"value="delete"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>

<br>�����������|:<br><b>
   <font color=blue>request.getServletPath():</font> <%= request.getServletPath()%><br>
   <font color=blue>request.getRequestURI(): </font> <%= request.getRequestURI()%> </b>
</body>
</html>
