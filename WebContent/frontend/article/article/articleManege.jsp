<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.article.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
	session.setAttribute("mem_no", "M0000004");
	String mem_no = (String)session.getAttribute("mem_no");

	ArticleService articleSvc = new ArticleService();
    List<ArticleVO> list = articleSvc.getAllByMem_no(mem_no);
    pageContext.setAttribute("list",list);
    
	Article_FavoriteService article_favoriteSvc = new Article_FavoriteService();
    List<Article_FavoriteVO> listArticleFavorite_ByMem_no = article_favoriteSvc.findByMemNo(mem_no);
    pageContext.setAttribute("listArticleFavorite_ByMem_no",listArticleFavorite_ByMem_no);

%>
<jsp:useBean id="article_classificationSvc" scope="page" class="com.article.model.Article_ClassificationService" />
<jsp:useBean id="article_messageSvc" scope="page" class="com.article.model.Article_MessageService" />

<!DOCTYPE html>
<html lang="">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
		<title>Title Page</title>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
			<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/frontend/css/babeq.css">			
		<!--[if lt IE 9]>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->
		<style type="text/css">
			.table-striped tbody tr:nth-of-type(odd){
				background-color: #aaa;
			}
			.table-hover tbody tr:hover{
				background-color: #fa0;
			}
		</style>
	</head>
	<body>
	
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

		<header>
			<nav class="navbar navbar-default navbar-fixed-top " role="navigation">
				<div class="container">
					<!-- 手機隱藏選單區 -->
					<div><a class="navbar-brand" href="#"><img src="<%=request.getContextPath()%>/frontend/pic/babeq3.png" width="230"></a></div>
					<div class="collapse navbar-collapse navbar-ex1-collapse">
						<!-- 右選單 -->
			
						<ul class="nav navbar-nav navbar-right">
							<li class="dropdown"><a href="#" class="navbarIcon"><img class="headerIcon" src="<%=request.getContextPath()%>/frontend/pic/list_01.png" width="80"></a></li>
							<li><a href="#" class="navbarIcon"><img class="headerIcon" src="<%=request.getContextPath()%>/frontend/pic/ring_01.png" width="80"></a></li>
							<li><a href="#" class="navbarIcon"><img class="headerIcon" src="<%=request.getContextPath()%>/frontend/pic/login_01.png" width="80"></a></li>
						</ul>
					</div>
					<img src="<%=request.getContextPath()%>/frontend/pic/pencilline.png" class="navbarLine" style="margin-top: 0px">
					<!-- 手機隱藏選單區結束 -->
				</div>
			</nav>
		</header>	

		<div class="container">
			<div class="row">	
				<div class="col-xs-12 col-sm-12 avoidHeader">
					
					<div class="topic">
						我的文章管理
					</div>
					
					<table class="table table-hover table-condensed table-striped table-bordered">
						<thead>
							<tr>
								<th width="25">No.</th>
								<th>文章標題</th>
								<th width="75">文章類型</th>
								<th width="330">文章內容</th>
								<th width="90">發表時間</th>
								<th width="60">瀏覽數</th>
								<th width="32">修改</th>
								<th width="32">刪除</th>
							</tr>
						</thead>
						<tbody>
							<%@ include file="page1.file" %> 
							<c:forEach var="articleVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>" varStatus="s">
							<tr ${(articleVO.art_no==param.art_no)? 'bgcolor=#FFCCCC':''}>
								<td>
<!-- 計數 -->						<c:out value="${s.index}"/>
								</td>
								<td>${articleVO.art_title}</td>
								<td>
									<c:forEach var="article_classificationVO" items="${article_classificationSvc.all}">
					                    <c:if test="${articleVO.artc_no==article_classificationVO.artc_no}">
						                    ${article_classificationVO.artc_name}
					                    </c:if>
					                </c:forEach>
								</td>
								<td>
									<c:set var="art_cnt" value="${articleVO.art_cnt}" />
									<% String art_cnt2= String.valueOf(pageContext.getAttribute("art_cnt"));
									  
								       String tagPattern = "<{1}[^>]{1,}>{1}";               //把所有<>標籤內的東西標起來
								       String art_cnt = art_cnt2.replaceAll(tagPattern, "");
								       int length = art_cnt.length();
								       
									   String showPart = null;
									   if(length<30){
										   showPart = art_cnt;
									   }else{
										   showPart = art_cnt.substring(0,18)+"...";
									   }
									%>
							 		<%=showPart%>
								</td>
								<td>
									<fmt:formatDate  pattern="yyyy-MM-dd" value="${articleVO.art_date}"/>
								</td>
								<td>${articleVO.art_vcnt}</td>

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
								    	<input type="hidden" name="action"      value="delete"></FORM>
								</td>
							</tr>

						
						</c:forEach>
					</table>
					<%@ include file="page2.file" %>
					</tbody>
				</div>
			</div>
		</div>

		<br>本網頁的路徑:<br><b>
	    <font color=blue>request.getServletPath():</font> <%= request.getServletPath()%><br>
	    <font color=blue>request.getRequestURI(): </font> <%= request.getRequestURI()%> </b>
		
		<footer>
			<div class="container">
				<img src="<%=request.getContextPath()%>/frontend/pic/pencilline.png" class="navbarLine">
				<div class="footerBottom navbar-right navbar-nav">
					<a href="#" class="navbarIcon"><img class="headerIcon" src="<%=request.getContextPath()%>/frontend/pic/chat_01.png" width="80"></a>
				</div>
			</div>
		</footer>	
		
		<script src="https://code.jquery.com/jquery.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	</body>
</html>



