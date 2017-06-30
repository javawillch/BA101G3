<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="java.util.regex.*" %>
<%@ page import="com.article.model.*"%>
<%-- �����m�߱ĥ� EL ���g�k���� --%>

<jsp:useBean id="article_classificationSvc" scope="page" class="com.article.model.Article_ClassificationService" />
<jsp:useBean id="article_messageSvc" scope="page" class="com.article.model.Article_MessageService" />

<%
	session.setAttribute("mem_no", "M0000004");
	String mem_no = (String)session.getAttribute("mem_no");
	//���F�P�_�n�q���@��service�h���o�峹s
	ArticleService articleSvc = new ArticleService();
	String artc_no = request.getParameter("artc_no");    
	List<ArticleVO> list = null;
	if("all".equalsIgnoreCase(artc_no)||artc_no==null){
		list = articleSvc.getAll();
		pageContext.setAttribute("list",list);
	}else if("pattern".equalsIgnoreCase(artc_no)){
		if( request.getAttribute("list") instanceof List){
			list =  (List<ArticleVO>)request.getAttribute("list");
		}
	}else{
		list = articleSvc.getAllByArtc_no(artc_no);
		pageContext.setAttribute("list",list);
	}
 
    
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
		<title>Article</title>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
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
					<!-- ������ÿ��� -->
					<div><a class="navbar-brand" href="#"><img src="<%=request.getContextPath()%>/frontend/pic/babeq3.png" width="230"></a></div>
					<div class="collapse navbar-collapse navbar-ex1-collapse">
						<!-- �k��� -->
			
						<ul class="nav navbar-nav navbar-right">
							<li class="dropdown"><a href="#" class="navbarIcon"><img class="headerIcon" src="<%=request.getContextPath()%>/frontend/pic/list_01.png" width="80"></a></li>
							<li><a href="#" class="navbarIcon"><img class="headerIcon" src="<%=request.getContextPath()%>/frontend/pic/ring_01.png" width="80"></a></li>
							<li><a href="#" class="navbarIcon"><img class="headerIcon" src="<%=request.getContextPath()%>/frontend/pic/login_01.png" width="80"></a></li>
						</ul>
					</div>
					<img src="<%=request.getContextPath()%>/frontend/pic/pencilline.png" class="navbarLine" style="margin-top: 0px">
					<!-- ������ÿ��ϵ��� -->
				</div>
			</nav>
		</header>
		
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
		
		<div class="container hotArtical">
			<div class="row">

				<form name="formPattern" method="post" action="<%=request.getContextPath()%>/frontend/article/article.do">
					<div class="input-group searchbar">
						<input type="text" name="pattern" class="form-control" placeholder="�п�J����r">
						<input type="hidden" name="action" value="pattern">
						<input type="hidden" name="artc_no" value="pattern">
						<span class="input-group-btn">
							<button class="btn btn-success" type="button" onClick="document.formPattern.submit()"><i class="glyphicon glyphicon-search"></i></button>
						</span>
					</div>
				</form>
				<div class="topic">
					<h1>�������O�峹</h1>
					<%@ include file="page1.file" %> 
<!-- XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX -->	
				    <div class="dropdown">
					    <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Dropdown Example
					    <span class="caret"></span></button>
				         <select size="1" name="artc_no" class="dropdown-menu">
				           <c:forEach var="article_classificationVO" items="${article_classificationSvc.all}" > 
				            <option value="${article_classificationVO.artc_no}">${article_classificationVO.artc_name}
				           </c:forEach>   
				         </select>
					</div>

				    <div>
				    						
				    <div class="dropdown">
					    <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Dropdown Example
					    <span class="caret"></span></button>
					    <ul class="dropdown-menu">
					      <li><a href="#">HTML</a></li>
					      <li><a href="#">CSS</a></li>
					      <li><a href="#">JavaScript</a></li>
					    </ul>
					</div>

			
				    <div>
				       <FORM METHOD="post" action="<%=request.getContextPath()%>/frontend/article/article.do" >
				         <b><font color=orange>��ܤ峹����:</font></b>
				         <select size="1" name="artc_no">
				            
						     <option ${artc_no=='all' ? 'selected="selected"':''} value="all">�����峹
						     <option ${artc_no=='AC01'? 'selected="selected"':''} value="AC01">��������
						     <option ${artc_no=='AC02'? 'selected="selected"':''} value="AC02">�|��оi
						     <option ${artc_no=='AC03'? 'selected="selected"':''} value="AC03">�e�f���@
						     <option ${artc_no=='AC04'? 'selected="selected"':''} value="AC04">�������i
						     <option ${artc_no=='AC05'? 'selected="selected"':''} value="AC05">�_�_����
						     <option ${artc_no=='AC06'? 'selected="selected"':''} value="AC06">�Ϋ~����
	   
				         </select>
				         <input type="submit" value="�e�X">
				         <input type="hidden" name="action" value="listArticlesByArtc_no">
				       </FORM>
				    </div>
<!-- XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX -->				
					<c:forEach var="articleVO" items="${list}"  begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
						<div class="articalBorderwill">
							<div class="col-xs-12 col-sm-6 contentHeight">
<!-- �ʺA�z�J�Ϥ� -->					<div class="articalImg">
									<c:set var="art_cnt" value="${articleVO.art_cnt}" />
								  <%String s= String.valueOf(pageContext.getAttribute("art_cnt"));
							        Pattern p = Pattern.compile("<img[^>]*src=[\\\"']([^\\\"^']*)");
							        Matcher m = p.matcher(s);
							        String srcTag = request.getContextPath()+"/pic/baby/baby02.jpg";
							        while (m.find()) {
							            String src = m.group();
							            int startIndex = src.indexOf("src=") + 5;
							            srcTag = src.substring(startIndex, src.length()); }%>
									<img class="img-responsive" src="<%=srcTag%>">
								</div>
							</div>
							<div class="col-xs-12 col-sm-6 contentHeight">
								

<!-- �峹���D�i��@�峹 -->		<form name="${articleVO.art_no}"  method="post"  action="<%=request.getContextPath()%>/frontend/article/article.do">  
				                  <c:set var="art_vcnt2" value="${articleVO.art_vcnt}" /> 
				                  <% Integer art_vcnt= Integer.parseInt(String.valueOf(pageContext.getAttribute("art_vcnt2")));%>
<!--���F�֥[�峹�s����-->			  <input type="hidden" name="art_vcnt" value="<%=++art_vcnt%>">
				                  <input type="hidden" name="action" value="getOne_For_Display">
				                  <input type="hidden" name="art_no" value="${articleVO.art_no}">
						          <a href="javascript:document.${articleVO.art_no}.submit()" style="font: bold 30px �L�n������;" >${articleVO.art_title}</a>
						        </form>
						        
						        
<!-- �z���s���¤�r -->				<div>
									<c:set var="art_cnt" value="${articleVO.art_cnt}" />
									<% String art_cnt2= String.valueOf(pageContext.getAttribute("art_cnt"));
									  
								       String tagPattern = "<{1}[^>]{1,}>{1}";               //��Ҧ�<>���Ҥ����F��а_��
								       String art_cnt = art_cnt2.replaceAll(tagPattern, "");
								       int length = art_cnt.length();
								       
									   String showPart = null;
									   if(length<250){
										   showPart = art_cnt;
									   }else{
										   showPart = art_cnt.substring(0,250);
									   }
									%>
							 		<%=showPart%>
<!--...�i��@�峹(�ϥ�108�檺submit)-->  <a href="javascript:document.${articleVO.art_no}.submit()" >...(�s������)</a>
								</div>
							</div>
						</div>
					</c:forEach>	
				
					</div>
				</div>
			</div>
		</div>
		<%@ include file="page2.file" %>

		
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