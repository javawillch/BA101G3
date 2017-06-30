<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.article.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<jsp:useBean id="article_messageSvc" scope="page" class="com.article.model.Article_MessageService" />
<%
	Article_ClassificationService article_classificationSvc = new Article_ClassificationService();
	ArticleVO articleVO = (ArticleVO) request.getAttribute("articleVO"); //ArticleServlet.java(Concroller), 存入req的articleVO物件
%>
<%-- 取出 對應的Article_ClassificationVO物件--%>
<%
	Article_ClassificationVO article_classificationVO = article_classificationSvc.getOneArticle_Classifiction(articleVO.getArtc_no());
	request.setAttribute("article_classificationVO", article_classificationVO);
%>


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



<!DOCTYPE html>
<html lang="">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
		<title>文章資料 - listOneQArticle.jsp</title>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
			<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/frontend/css/babeq.css">	
		<!--[if lt IE 9]>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->
	</head>
	<body>
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
		<div class="container listOneArtical">
			<div class="row">
				
				<div class="avoidHeader">
					<div class="topic">
						<!--留言/新增文章/回文章首頁/修改文章-->
						<a href="#textarea" onclick="getFocus()" class="btn btn-info"><i class="fa fa-commenting-o"></i>文章留言</a>	 
						<a href="<%=request.getContextPath()%>/frontend/article/article/addArticle.jsp" class="btn btn-info"><i class="fa fa-pencil"></i> 發表新文章</a>
						<a href="<%=request.getContextPath()%>/frontend/article/article/articleHome.jsp" class="btn btn-info"><i class="fa fa-arrow-left" aria-hidden="true"></i>回文章首頁</a>
						<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/article/article.do" style="display:inline">
					     <input type="submit" value="修改文章" class="btn btn-info"> 
					     <input type="hidden" name="art_no" value="${articleVO.art_no}">
					     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
					     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
		
						<div class="articalBorder">
							<!--文章標題及分類-->
							<table class="tborder" align="center" id="article" cellpadding="0" cellspacing="6" border="0" width="100%">
								<tbody>
									<tr>
										<td>
											<h2 style="font: bold 30px 微軟正黑體;" ><strong>${articleVO.art_title}</strong></h2>
										</td>
										<td align="left">
											<!--加入收藏文章 -->
											<% boolean flag = true; %>
											<c:forEach var="article_favoriteVO" items="${listArticleFavorite_ByMem_no}" >
												<FORM METHOD="post" id="form1" name="form1" ACTION="<%=request.getContextPath()%>/frontend/article_favorite/article_favorite.do">
													<input type="hidden" name="mem_no" value="${sessionScope.mem_no}">   
													<input type="hidden" name="art_no" value="${articleVO.art_no}">
													<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
													<c:if test="${article_favoriteVO.art_no==articleVO.art_no}">
									                    <input type="image" name="action" class="star" title="取消收藏" value="delete_ByListOneArticle" img src="<%=request.getContextPath()%>/frontend/pic/article/star_red.png" onClick="document.form1.submit()">
									                    <% flag = false; %>
								                    </c:if>
													<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->	
											</c:forEach>
											<c:if test="<%=flag%>">
												<input type="image" name="action" class="star" title="加入收藏" value="insert_ByListOneArticle"  img src="<%=request.getContextPath()%>/frontend/pic/article/star.png" onClick="document.form1.submit()">
											</c:if>	
											</FORM> 
										</td>
										<td align="right">
											瀏覽數:${articleVO.art_vcnt}
										</td>
										<td align="center">
											<h5>【${article_classificationVO.artc_name}】</h5>
										</td>
		
									</tr>
								</tbody>	
							</table>
							<!--發表文章作者資訊-->
						    <div class="media">
						      <div class="media-left">
						        <img src="<%=request.getContextPath()%>/frontend/pic/test/expert_002.png" class="img-circle" style="width:60px">
						      </div>
						      <div class="media-body">
						        <h4 class="media-heading">${articleVO.mem_no}</h4>
<!-- 格式化後得時間 -->				<p><fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${articleVO.art_date}"/></p>
						      </div>
						    </div>
							<!--文章內容-->
							<div id="articleContent">
								${articleVO.art_cnt}
							</div>
						</div>
					</div>
				</div>
				
				<c:forEach var="article_messageVO" items="${article_messageSvc.all}">
					<c:if test="${articleVO.art_no==article_messageVO.art_no}">
						<div class="articalBorder">
							<!--留言者資訊-->
						  <div class="media">
						    <div class="media-left">
						      <img src="<%=request.getContextPath()%>/frontend/pic/test/expert_004.jpg" class="img-circle" width="50">
						    </div>
						    <div class="media-body">
						      <c:if test="${articleVO.art_no==article_messageVO.art_no}">
						        <h4 class="media-heading">${article_messageVO.mem_no}</h4>
<!-- 格式化後得時間 -->				<p><fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${article_messageVO.amsg_date}"/></p>
						      </c:if>
						    </div>
						    <div class="media-right">
						      <button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#myModal"><i class="fa fa-flag" aria-hidden="true"></i>檢舉</button>
						    </div>
						    <div>
						      <input type="hidden" name="amsg_no" value="${article_messageVO.amsg_no}"> 
							  <c:set var="amsg_no" value="${article_messageVO.amsg_no}" />
						    </div>
						  </div>

							
							<!--留言內容-->
							<div id="articleMessageContent">
			                    <c:if test="${articleVO.art_no==article_messageVO.art_no}">
				                    ${article_messageVO.amsg_cnt}
			                    </c:if>
							</div>			
						</div>
					</c:if>
				</c:forEach>
									
				<!--文章留言-->
				<form METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/article_message/article_message.do" name="amsg">
					<div class="form-group">
						<label><i class="fa fa-commenting-o"></i>文章留言</label>
						<div class="col-sm-12">
							<textarea id="content" rows="5" name="amsg_cnt" class="form-control"></textarea>
			<!--					<script>
								CKEDITOR.replace( 'content', {});
							</script>-->
						</div>
					</div>
	
					<input type="hidden" name="art_no"      value="<%= (articleVO==null)? "ART11111" : articleVO.getArt_no()%>">
					<input type="hidden" name="mem_no"      value="${sessionScope.mem_no}">  
					<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
					<input type="hidden" name="action"      value="insert">
					
					<a href="javascript:document.amsg.submit()" class="btn btn-info" style="font-size:12px"><i class="glyphicon glyphicon-ok-sign"></i>送出文章留言</a>
				</form>
				

					  <!-- 檢舉燈箱Modal hidden -->
					  <div class="modal fade" id="myModal" role="dialog">
					    <div class="modal-dialog modal-lg">
					      <form METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/article_message_report/article_message_report.do" name="amrpt">
						      <div class="modal-content">
						        <div class="modal-header">
						          <button type="button" class="close" data-dismiss="modal">&times;</button>
						          <h4 class="modal-title">檢舉此篇文章</h4>
						        </div>
						        <div class="modal-body">
		        					<input type="hidden" name="mem_no" value="${sessionScope.mem_no}">   
									<input type="hidden" name="amsg_no" value="${pageScope.amsg_no}">
									<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
									<input type="hidden" name="action"   value="insert">
						          	<textarea id="reportContent" rows="5" name="amrpt_rsn" class="form-control"></textarea>
						        </div>
						        <div class="modal-footer">
						       	  <p align="left">注意: 只用於檢舉爭吵，濫發廣告，和有問題(色情，容易引起爭鬥，或者粗俗)的文章。</p>
						       	  <button type="button" class="btn btn-default" data-dismiss="modal" onClick="document.amrpt.submit()">送出檢舉</button>
						          <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
						        </div>
						      </div>
					      </form>
					    </div>
					  </div>
					  
			</div>		        
		</div>



		
<% out.println("articleVO.getArtc_no():" +articleVO.getArtc_no()); %><br>
<% out.println("article_classificationSvc.getOneArticle_Classifictio():" +article_classificationSvc.getOneArticle_Classifiction(articleVO.getArtc_no())); %><br>
<% out.println("article_classificationVO:" +article_classificationVO); %><br>
		
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
		<script>
			//文章回覆得到焦點
			function getFocus(){
				document.getElementById("content").focus();
			}
		</script>

	</body>
</html>