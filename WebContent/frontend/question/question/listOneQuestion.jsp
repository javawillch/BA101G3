<%@ page contentType="text/html; charset=Big5"%>
<%@ page import="com.question.model.*"%>
<%
QuestionVO questionVO = (QuestionVO) request.getAttribute("questionVO"); //QuestionServlet.java(Concroller), �s�Jreq��questionVO����
%>
<%-- ���X ������Question_ClassificationVO����--%>
<%
  Question_ClassificationService question_classificationSvc = new Question_ClassificationService();
  Question_ClassificationVO question_classificationVO = question_classificationSvc.getOneQuestion_Classifiction(questionVO.getQuec_no());
%>
<%request.setAttribute("question_classificationVO", question_classificationVO); %>
<html>
<head>
<title>���D��� - listOneQuestion.jsp</title>
</head>
<body bgcolor='white'>

<table border='1' cellpadding='5' cellspacing='0' width='1000'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>���D��� - ListOneQuestion.jsp</h3>
		<a href="<%=request.getContextPath()%>/frontend/question/question/select_page.jsp"><img src="<%=request.getContextPath()%>/wu/images/back1.gif" width="100" height="32" border="0">�^����</a>
		</td>
	</tr>
</table>

<table border='1' bordercolor='#CCCCFF' width='1000'>
	<tr>
		<th>���D�s��</th>
		<th>�|���s��(���ݪ�)</th>
		<th>���D�����s��</th>
		<th>���D�D��</th>
		<th>���D�߰ݮɶ�</th>
		<th>���D���e</th>
	</tr>
	<tr align='center' valign='middle'>
			<td>${questionVO.qu_no}</td>
			<td>${questionVO.mem_no}</td>
			<td>${questionVO.quec_no}�i${question_classificationVO.quec_name}�j</td>
			<td>${questionVO.qu_title}</td>
			<td>${questionVO.qu_date}</td>
			<td>${questionVO.qu_cnt}</td>
	</tr>
</table>

</body>
</html>
