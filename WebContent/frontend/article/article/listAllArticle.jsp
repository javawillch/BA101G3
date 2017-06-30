<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.article.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
	session.setAttribute("mem_no", "M0000004");
	String mem_no = (String)session.getAttribute("mem_no");

	ArticleService articleSvc = new ArticleService();
    List<ArticleVO> list = articleSvc.getAll();
    pageContext.setAttribute("list",list);
    
	Article_FavoriteService article_favoriteSvc = new Article_FavoriteService();
    List<Article_FavoriteVO> listArticleFavorite_ByMem_no = article_favoriteSvc.findByMemNo(mem_no);
    pageContext.setAttribute("listArticleFavorite_ByMem_no",listArticleFavorite_ByMem_no);

%>
<jsp:useBean id="article_classificationSvc" scope="page" class="com.article.model.Article_ClassificationService" />
<jsp:useBean id="article_messageSvc" scope="page" class="com.article.model.Article_MessageService" />
<html>
<head>
<title>所有文章資料 - listAllArticle.jsp</title>
</head>
<body bgcolor='white'>
<b><font color=red>此頁練習採用 EL 的寫法取值:</font></b>
<table border='1' cellpadding='5' cellspacing='0' width='1200'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>所有文章資料 - listAllArticle.jsp</h3>
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

<table border='1' bordercolor='#CCCCFF' width='1200'>
	<tr>
		<th>文章編號</th>
		<th>會員編號(提問者)</th>
		<th>文章分類編號</th>
		<th>文章主旨</th>
		<th>文章發表時間</th>
		<th>文章內容</th>
		<th>文章留言</th>
		<th>文章瀏覽數</th>
		<th>文章收藏</th>
		<th>修改</th>
		<th>刪除</th>
	</tr>
	<%@ include file="page1.file" %> 
	<c:forEach var="articleVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">

		<tr align='center' valign='middle' ${(articleVO.art_no==param.art_no)? 'bgcolor=#FFCCCC':''}>
			<td>${articleVO.art_no}</td>
			<td>${articleVO.mem_no}</td>
			<td>
				<c:forEach var="article_classificationVO" items="${article_classificationSvc.all}">
                    <c:if test="${articleVO.artc_no==article_classificationVO.artc_no}">
	                    ${article_classificationVO.artc_no}【${article_classificationVO.artc_name}】
                    </c:if>
                </c:forEach>
           	</td>
			<td>${articleVO.art_title}</td>
			<td>${articleVO.art_date}</td>
			<td>${articleVO.art_cnt}</td>


			<td>
				<c:forEach var="article_messageVO" items="${article_messageSvc.all}">
                    <c:if test="${articleVO.art_no==article_messageVO.art_no}">
	                    ${article_messageVO.amsg_cnt}【${article_messageVO.art_no}】
                    </c:if>
                </c:forEach>
            </td>

			<td>${articleVO.art_vcnt}</td>
			<td>
			<!--加入收藏文章 -->
			<% boolean flag = true; %>
				<c:forEach var="article_favoriteVO" items="${listArticleFavorite_ByMem_no}" >
					<FORM METHOD="post" id="form1" name="form1" ACTION="<%=request.getContextPath()%>/frontend/article_favorite/article_favorite.do">
						<input type="hidden" name="mem_no" value="${sessionScope.mem_no}">   
						<input type="hidden" name="art_no" value="${articleVO.art_no}">
						<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
						<c:if test="${article_favoriteVO.art_no==articleVO.art_no}">
		                    <input type="image" name="action" class="star" title="取消收藏" value="delete_ByListAllArticle" img src="<%=request.getContextPath()%>/pic/star_red.png" onClick="document.form1.submit()">
		                    <% flag = false; %>
	                    </c:if>
						<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
						<input type="hidden" name="whichPage"	value="<%=whichPage%>">               <!--送出當前是第幾頁給Controller-->		
				</c:forEach>
				<c:if test="<%=flag%>">
					<input type="image" name="action" class="star" title="加入收藏" value="insert_ByListAllArticle"  img src="<%=request.getContextPath()%>/pic/star.png" onClick="document.form1.submit()">
				</c:if>	
					</FORM> 
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/article/article.do">
			     <input type="submit" value="修改"> 
			     <input type="hidden" name="art_no" value="${articleVO.art_no}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
			     <input type="hidden" name="whichPage"	value="<%=whichPage%>">               <!--送出當前是第幾頁給Controller-->
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/article/article.do">
			    <input type="submit" value="刪除">
			    <input type="hidden" name="art_no" value="${articleVO.art_no}">
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



<script>
 	//目前沒有用到 到時候要改成AJAX
	//加入收藏 或 取消收藏
	function switchFavorite(e){

		
		
    	if(e.target.title == "加入收藏"){
    		e.target.src = "<%=request.getContextPath()%>/pic/star_red.png";
    		e.target.title = "取消收藏";
    		e.target.value = "delete_ByListAllArticle";
		}else{
			e.target.src = "<%=request.getContextPath()%>/pic/star.png";
			e.target.title = "加入收藏";
			e.target.value = "insert_ByListAllArticle";
		}
	   
		
	}
	
	function init(){
	  //設定[加入收藏 或 取消收藏]的點按事件
	  var stars = document.getElementsByClassName("star");
	  var numOfStars = stars.length;
	  for(i=0 ; i<numOfStars; i++){
		  //stars[i].onclick = switchFavorite;
	  }
	
	}//init
	window.onload = init;
</script>