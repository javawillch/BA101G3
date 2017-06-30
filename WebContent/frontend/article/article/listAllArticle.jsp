<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.article.model.*"%>
<%-- �����m�߱ĥ� EL ���g�k���� --%>

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
<title>�Ҧ��峹��� - listAllArticle.jsp</title>
</head>
<body bgcolor='white'>
<b><font color=red>�����m�߱ĥ� EL ���g�k����:</font></b>
<table border='1' cellpadding='5' cellspacing='0' width='1200'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>�Ҧ��峹��� - listAllArticle.jsp</h3>
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

<table border='1' bordercolor='#CCCCFF' width='1200'>
	<tr>
		<th>�峹�s��</th>
		<th>�|���s��(���ݪ�)</th>
		<th>�峹�����s��</th>
		<th>�峹�D��</th>
		<th>�峹�o��ɶ�</th>
		<th>�峹���e</th>
		<th>�峹�d��</th>
		<th>�峹�s����</th>
		<th>�峹����</th>
		<th>�ק�</th>
		<th>�R��</th>
	</tr>
	<%@ include file="page1.file" %> 
	<c:forEach var="articleVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">

		<tr align='center' valign='middle' ${(articleVO.art_no==param.art_no)? 'bgcolor=#FFCCCC':''}>
			<td>${articleVO.art_no}</td>
			<td>${articleVO.mem_no}</td>
			<td>
				<c:forEach var="article_classificationVO" items="${article_classificationSvc.all}">
                    <c:if test="${articleVO.artc_no==article_classificationVO.artc_no}">
	                    ${article_classificationVO.artc_no}�i${article_classificationVO.artc_name}�j
                    </c:if>
                </c:forEach>
           	</td>
			<td>${articleVO.art_title}</td>
			<td>${articleVO.art_date}</td>
			<td>${articleVO.art_cnt}</td>


			<td>
				<c:forEach var="article_messageVO" items="${article_messageSvc.all}">
                    <c:if test="${articleVO.art_no==article_messageVO.art_no}">
	                    ${article_messageVO.amsg_cnt}�i${article_messageVO.art_no}�j
                    </c:if>
                </c:forEach>
            </td>

			<td>${articleVO.art_vcnt}</td>
			<td>
			<!--�[�J���ä峹 -->
			<% boolean flag = true; %>
				<c:forEach var="article_favoriteVO" items="${listArticleFavorite_ByMem_no}" >
					<FORM METHOD="post" id="form1" name="form1" ACTION="<%=request.getContextPath()%>/frontend/article_favorite/article_favorite.do">
						<input type="hidden" name="mem_no" value="${sessionScope.mem_no}">   
						<input type="hidden" name="art_no" value="${articleVO.art_no}">
						<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
						<c:if test="${article_favoriteVO.art_no==articleVO.art_no}">
		                    <input type="image" name="action" class="star" title="��������" value="delete_ByListAllArticle" img src="<%=request.getContextPath()%>/pic/star_red.png" onClick="document.form1.submit()">
		                    <% flag = false; %>
	                    </c:if>
						<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--�e�X�����������|��Controller-->
						<input type="hidden" name="whichPage"	value="<%=whichPage%>">               <!--�e�X��e�O�ĴX����Controller-->		
				</c:forEach>
				<c:if test="<%=flag%>">
					<input type="image" name="action" class="star" title="�[�J����" value="insert_ByListAllArticle"  img src="<%=request.getContextPath()%>/pic/star.png" onClick="document.form1.submit()">
				</c:if>	
					</FORM> 
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/article/article.do">
			     <input type="submit" value="�ק�"> 
			     <input type="hidden" name="art_no" value="${articleVO.art_no}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--�e�X�����������|��Controller-->
			     <input type="hidden" name="whichPage"	value="<%=whichPage%>">               <!--�e�X��e�O�ĴX����Controller-->
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/article/article.do">
			    <input type="submit" value="�R��">
			    <input type="hidden" name="art_no" value="${articleVO.art_no}">
			    <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--�e�X�����������|��Controller-->
			    <input type="hidden" name="whichPage"	value="<%=whichPage%>">               <!--�e�X��e�O�ĴX����Controller-->
			    <input type="hidden" name="action"value="delete"></FORM>
			</td>
		</tr>	
	</c:forEach>
</table>
<%@ include file="page2.file" %>

<br>�����������|:<br><b>
   <font color=blue>request.getServletPath():</font> <%= request.getServletPath()%><br>
   <font color=blue>request.getRequestURI(): </font> <%= request.getRequestURI()%> </b>

</body>
</html>



<script>
 	//�ثe�S���Ψ� ��ɭԭn�令AJAX
	//�[�J���� �� ��������
	function switchFavorite(e){

		
		
    	if(e.target.title == "�[�J����"){
    		e.target.src = "<%=request.getContextPath()%>/pic/star_red.png";
    		e.target.title = "��������";
    		e.target.value = "delete_ByListAllArticle";
		}else{
			e.target.src = "<%=request.getContextPath()%>/pic/star.png";
			e.target.title = "�[�J����";
			e.target.value = "insert_ByListAllArticle";
		}
	   
		
	}
	
	function init(){
	  //�]�w[�[�J���� �� ��������]���I���ƥ�
	  var stars = document.getElementsByClassName("star");
	  var numOfStars = stars.length;
	  for(i=0 ; i<numOfStars; i++){
		  //stars[i].onclick = switchFavorite;
	  }
	
	}//init
	window.onload = init;
</script>