<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% session.setAttribute("mem_no", "M0000006"); %>
<html>
<head><title>BA101G3_BaBeQ Article: Home</title></head>
<body bgcolor='white'>

	<table border='1' cellpadding='5' cellspacing='0' width='400'>
	  <tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
	    <td><h3>BaBeQ Article: Home</h3><font color=red>( MVC )</font></td>
	  </tr>
	</table>
	
	<p>This is the Home page for BaBeQ Article: Home</p>
	
	<h3>資料查詢:</h3>
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
	
	<ul>
	  <li><a href='<%=request.getContextPath()%>/frontend/article/article/listAllArticle.jsp'>List</a> all Articles. </li> <br><br>
	  
	  <li>
	    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/article/article.do" >
	        <b>輸入文章編號 (如ART00001):</b>
	        <input type="text" name="art_no">
	        <input type="submit" value="送出">
	        <input type="hidden" name="action" value="getOne_For_Display">
	    </FORM>
	  </li>
	
	  <jsp:useBean id="articleSvc" scope="page" class="com.article.model.ArticleService" />
	   
	  <li>
	     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/article/article.do" >
	       <b>選擇文章編號:</b>
	       <select size="1" name="art_no">
	         <c:forEach var="articleVO" items="${articleSvc.all}" > 
	          <option value="${articleVO.art_no}">${articleVO.art_no}
	         </c:forEach>   
	       </select>
	       <input type="submit" value="送出">
	       <input type="hidden" name="action" value="getOne_For_Display">
	    </FORM>
	  </li>
	  
	  <li>
	     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/article/article.do" >
	       <b>選擇文章主旨:</b>
	       <select size="1" name="art_no">
	         <c:forEach var="articleVO" items="${articleSvc.all}" > 
	          <option value="${articleVO.art_no}">${articleVO.art_title}
	         </c:forEach>   
	       </select>
	       <input type="submit" value="送出">
	       <input type="hidden" name="action" value="getOne_For_Display">
	     </FORM>
	  </li>
	  
	  
		<jsp:useBean id="article_classificationSvc" scope="page" class="com.article.model.Article_ClassificationService" />
	 
	  <li>
	     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/article_classification/article_classification.do" >
	       <b><font color=orange>選擇文章分類:</font></b>
	       <select size="1" name="artc_no">
	         <c:forEach var="article_classificationVO" items="${article_classificationSvc.all}" > 
	          <option value="${article_classificationVO.artc_no}">${article_classificationVO.artc_name}
	         </c:forEach>   
	       </select>
	       <input type="submit" value="送出">
	       <input type="hidden" name="action" value="listArticles_ByArtc_no_A">
	     </FORM>
	  </li>
	</ul>
	
	
	<h3>文章管理</h3>
	
	<ul>
	  <li><a href='<%=request.getContextPath()%>/frontend/article/article/addArticle.jsp'>Add</a> a new Article.</li>
	</ul>
	
	<h3><font color=orange>文章分類管理</font></h3>
	
	<ul>
	  <li><a href='<%=request.getContextPath()%>/frontend/article/article_classification/listAllArticle_Classification.jsp'>List</a> all Article_Classifications. </li>
	</ul>
	
	<h3>收藏文章列表</h3>
	<ul>
	  <li><a href='<%=request.getContextPath()%>/frontend/article/article/listAllArticle_Favorite.jsp'>List</a> all Article_Favorite. </li>
	</ul>

</body>
</html>
