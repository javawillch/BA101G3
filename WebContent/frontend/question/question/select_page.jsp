<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><title>BA101G3_BaBeQ Question: Home</title></head>
<body bgcolor='white'>

<table border='1' cellpadding='5' cellspacing='0' width='400'>
  <tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
    <td><h3>BaBeQ Question: Home</h3><font color=red>( MVC )</font></td>
  </tr>
</table>

<p>This is the Home page for BaBeQ Question: Home</p>

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
  <li><a href='<%=request.getContextPath()%>/frontend/question/question/listAllQuestion.jsp'>List</a> all Questions. </li> <br><br>
  
  <li>
    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/question/question.do" >
        <b>輸入問題編號 (如Q0000001):</b>
        <input type="text" name="qu_no">
        <input type="submit" value="送出">
        <input type="hidden" name="action" value="getOne_For_Display">
    </FORM>
  </li>

  <jsp:useBean id="questionSvc" scope="page" class="com.question.model.QuestionService" />
   
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/question/question.do" >
       <b>選擇問題編號:</b>
       <select size="1" name="qu_no">
         <c:forEach var="questionVO" items="${questionSvc.all}" > 
          <option value="${questionVO.qu_no}">${questionVO.qu_no}
         </c:forEach>   
       </select>
       <input type="submit" value="送出">
       <input type="hidden" name="action" value="getOne_For_Display">
    </FORM>
  </li>
  
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/question/question.do" >
       <b>選擇問題主旨:</b>
       <select size="1" name="qu_no">
         <c:forEach var="questionVO" items="${questionSvc.all}" > 
          <option value="${questionVO.qu_no}">${questionVO.qu_title}
         </c:forEach>   
       </select>
       <input type="submit" value="送出">
       <input type="hidden" name="action" value="getOne_For_Display">
     </FORM>
  </li>
  
  
	<jsp:useBean id="question_classificationSvc" scope="page" class="com.question.model.Question_ClassificationService" />
 
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/question_classification/question_classification.do" >
       <b><font color=orange>選擇問題分類:</font></b>
       <select size="1" name="quec_no">
         <c:forEach var="question_classificationVO" items="${question_classificationSvc.all}" > 
          <option value="${question_classificationVO.quec_no}">${question_classificationVO.quec_name}
         </c:forEach>   
       </select>
       <input type="submit" value="送出">
       <input type="hidden" name="action" value="listQuestions_ByQuec_no_A">
     </FORM>
  </li>
</ul>


<h3>問題管理</h3>

<ul>
  <li><a href='<%=request.getContextPath()%>/frontend/question/question/addQuestion.jsp'>Add</a> a new Question.</li>
</ul>

<h3><font color=orange>問題分類管理</font></h3>

<ul>
  <li><a href='<%=request.getContextPath()%>/frontend/question/question_classification/listAllQuestion_Classification.jsp'>List</a> all Depts. </li>
</ul>

</body>

</html>
