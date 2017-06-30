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

<h3>��Ƭd��:</h3>
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

<ul>
  <li><a href='<%=request.getContextPath()%>/frontend/question/question/listAllQuestion.jsp'>List</a> all Questions. </li> <br><br>
  
  <li>
    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/question/question.do" >
        <b>��J���D�s�� (�pQ0000001):</b>
        <input type="text" name="qu_no">
        <input type="submit" value="�e�X">
        <input type="hidden" name="action" value="getOne_For_Display">
    </FORM>
  </li>

  <jsp:useBean id="questionSvc" scope="page" class="com.question.model.QuestionService" />
   
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/question/question.do" >
       <b>��ܰ��D�s��:</b>
       <select size="1" name="qu_no">
         <c:forEach var="questionVO" items="${questionSvc.all}" > 
          <option value="${questionVO.qu_no}">${questionVO.qu_no}
         </c:forEach>   
       </select>
       <input type="submit" value="�e�X">
       <input type="hidden" name="action" value="getOne_For_Display">
    </FORM>
  </li>
  
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/question/question.do" >
       <b>��ܰ��D�D��:</b>
       <select size="1" name="qu_no">
         <c:forEach var="questionVO" items="${questionSvc.all}" > 
          <option value="${questionVO.qu_no}">${questionVO.qu_title}
         </c:forEach>   
       </select>
       <input type="submit" value="�e�X">
       <input type="hidden" name="action" value="getOne_For_Display">
     </FORM>
  </li>
  
  
	<jsp:useBean id="question_classificationSvc" scope="page" class="com.question.model.Question_ClassificationService" />
 
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/question_classification/question_classification.do" >
       <b><font color=orange>��ܰ��D����:</font></b>
       <select size="1" name="quec_no">
         <c:forEach var="question_classificationVO" items="${question_classificationSvc.all}" > 
          <option value="${question_classificationVO.quec_no}">${question_classificationVO.quec_name}
         </c:forEach>   
       </select>
       <input type="submit" value="�e�X">
       <input type="hidden" name="action" value="listQuestions_ByQuec_no_A">
     </FORM>
  </li>
</ul>


<h3>���D�޲z</h3>

<ul>
  <li><a href='<%=request.getContextPath()%>/frontend/question/question/addQuestion.jsp'>Add</a> a new Question.</li>
</ul>

<h3><font color=orange>���D�����޲z</font></h3>

<ul>
  <li><a href='<%=request.getContextPath()%>/frontend/question/question_classification/listAllQuestion_Classification.jsp'>List</a> all Depts. </li>
</ul>

</body>

</html>
