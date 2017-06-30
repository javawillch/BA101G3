<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.article.model.*"%>
<%
ArticleVO articleVO = (ArticleVO) request.getAttribute("articleVO");
%>


<!DOCTYPE html>
<html lang="">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
		<title>addArticle</title>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
		<!--[if lt IE 9]>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->
			<!--CKEditor-->
			<script src="<%=request.getContextPath()%>/utility/ckeditor/ckeditor.js"></script>
			<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
			<link rel="stylesheet" href="<%=request.getContextPath()%>/utility/ckeditor/contents.css">
			<script src="<%=request.getContextPath()%>/utility/ckfinder/ckfinder.js"></script>
			<script>
				$(document).ready(function() {
					var editor = CKEDITOR.replace( 'art_cnt', {
						width:680,
						filebrowserBrowseUrl : '<%=request.getContextPath()%>/utility/ckfinder/ckfinder.html',
						filebrowserImageBrowseUrl : '<%=request.getContextPath()%>/utility/ckfinder/ckfinder.html?type=Images', 
						filebrowserFlashBrowseUrl : '<%=request.getContextPath()%>/utility/ckfinder/ckfinder.html?type=Flash',
						filebrowserUploadUrl : '<%=request.getContextPath()%>/utility/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files', 
						filebrowserImageUploadUrl : '<%=request.getContextPath()%>/utility/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images', 
						filebrowserFlashUploadUrl : '<%=request.getContextPath()%>/utility/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash' 
					});
					CKFinder.setupCKEditor( editor, '/ckfinder/' );
				});
			</script>
		
		<script src="drap_drag.js"></script>
		<style type="text/css">
			#dropZone{
				border: 5px  dotted #aaa;
				width: 400px;
				margin:50px auto;
				text-aligh:center;
				
				padding: 100px 0px;
			}
			
			img{
				max-width:400px;
				max-height:300px;
			}
			.identity{
				display:inline;
				width:100px;
				margin-left:16px;
			}
		</style>
		
		
	</head>
	<body>
		
	
		<div class="container">
			<div class="row">
				<div class="col-xs-12 col-sm-8 col-sm-offset-2">

					<!-- 待刪掉 -->
					<table border='1' cellpadding='5' cellspacing='0' width='700'>
						<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
							<td>
							<h3>文章修改 - updateArticle.jsp</h3>
							</td>
							<td>
							   <a href="<%=request.getContextPath()%>/frontend/article/article/articleHome.jsp"><img src="<%=request.getContextPath()%>/wu/images/tomcat.gif" width="100" height="100" border="1">回首頁</a>
						    </td>
						</tr>
					</table>				

					<h3>修改文章資料:</h3>
						<%-- 錯誤列表--%>
						<c:if test="${not empty errorMsgs}">
							<font color='red'>請修正以下錯誤:
							<ul>
								<c:forEach var="message" items="${errorMsgs}">
									<li>${message}</li>
								</c:forEach>
							</ul>
							</font>
						</c:if>
					


					<form METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/article/article.do" name="form1">
						<div >
							<div class="form-group">
								<label><i class="glyphicon glyphicon-list-alt"></i>文章主旨</label>
								<div class="col-sm-12">
									<input type="text" class="form-control" name="art_title" value="${articleVO.art_title}">
								</div>
							</div>
						
							<jsp:useBean id="article_classificationSvc" scope="page" class="com.article.model.Article_ClassificationService" />
							<div class="form-group">
								<label><i class="glyphicon glyphicon-bookmark"></i>文章分類</label>
								<div class="col-sm-12">
									<select size="1" name="artc_no">
										<c:forEach var="article_classificationVO" items="${article_classificationSvc.all}">
											<option value="${article_classificationVO.artc_no}" ${(articleVO.artc_no==article_classificationVO.artc_no)? 'selected':'' } >${article_classificationVO.artc_name}
										</c:forEach>
									</select>
								</div>
							</div>

							<div class="form-group">
								<label><i class="glyphicon glyphicon-pencil"></i>文章內容</label>
								<div class="col-sm-12">
									<textarea id="art_cnt" rows="50" name="art_cnt" class="form-control">
										<%= (articleVO==null)? "" : articleVO.getArt_cnt()%>
									</textarea>
								</div>
							</div>
						</div>
						
					   
					    <input type="hidden" name="art_no"   value="<%= (articleVO==null)? "ART11111" : articleVO.getArt_no()%>">
					    <input type="hidden" name="mem_no"   value="${sessionScope.mem_no}">
					    <input type="hidden" name="art_vcnt"   value="${articleVO.art_vcnt}">
						<input type="hidden" name="requestURL" value="<%=request.getParameter("requestURL")%>"><!--接收原送出修改的來源網頁路徑後,再送給Controller準備轉交之用-->
						<input type="hidden" name="whichPage" value="<%=request.getParameter("whichPage")%>">  <!--只用於:listAllQuestion.jsp-->
						<input type="hidden" name="action" value="update">
						
						<!-- 驗證圖片 -->
						<input type="text" class="form-control identity" name="identity"></input>
						<img src="<%=request.getContextPath()%>/IdentityImage/IdentityImage.do" id="identity" onload="btn.disabled=falsel" />
						<input type="button" value="換圖片" onclick="reloadImage()" id="btn">
						
						<p class="text-center">

							<a href="javascript:document.form1.submit()" class="btn btn-info"><i class="glyphicon glyphicon-ok-sign"></i>送出</a>
							<a href="<%=request.getContextPath()%>/frontend/article/article/articleHome.jsp" class="btn btn-info"><i class="glyphicon glyphicon-remove-sign"></i>取消</a>
						</p>					

					</form>
					<!-- 拖拉圖片區 -->
					<div id="dropZone">
					</div>
					
					
				</div>
			</div>
		</div>
		
		<br>送出修改的來源網頁路徑:<br><b>
		   <font color=blue>request.getParameter("requestURL"):</font> <%= request.getParameter("requestURL")%><br>
		   <font color=blue>request.getParameter("whichPage"):</font> <%= request.getParameter("whichPage")%> (此範例目前只用於:istAllEmp.jsp))</b>
				
		
		<script src="https://code.jquery.com/jquery.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	</body>
</html>

<script>
	function reloadImage(){
		document.getElementById('btn').disable = true;
		document.getElementById('identity').src ='<%=request.getContextPath()%>/IdentityImage/IdentityImage.do?ts=' +new Date().getTime();
	}

</script>