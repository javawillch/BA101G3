<%@ page contentType="text/html; charset=Big5"%>
<%@ page import="com.question.model.*"%>
<%
QuestionVO questionVO = (QuestionVO) request.getAttribute("questionVO"); //QuestionServlet.java(Concroller), 存入req的questionVO物件
%>
<%-- 取出 對應的Question_ClassificationVO物件--%>
<%
  Question_ClassificationService question_classificationSvc = new Question_ClassificationService();
  Question_ClassificationVO question_classificationVO = question_classificationSvc.getOneQuestion_Classifiction(questionVO.getQuec_no());
%>
<%request.setAttribute("question_classificationVO", question_classificationVO); %>
<html>
<head>
<title>問題資料 - listOneQuestion.jsp</title>
</head>
<body bgcolor='white'>

<table border='1' cellpadding='5' cellspacing='0' width='1000'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>問題資料 - ListOneQuestion.jsp</h3>
		<a href="<%=request.getContextPath()%>/frontend/question/question/select_page.jsp"><img src="<%=request.getContextPath()%>/wu/images/back1.gif" width="100" height="32" border="0">回首頁</a>
		</td>
	</tr>
</table>

<table border='1' bordercolor='#CCCCFF' width='1000'>
	<tr>
		<th>問題編號</th>
		<th>會員編號(提問者)</th>
		<th>問題分類編號</th>
		<th>問題主旨</th>
		<th>問題詢問時間</th>
		<th>問題內容</th>
	</tr>
	<tr align='center' valign='middle'>
			<td>${questionVO.qu_no}</td>
			<td>${questionVO.mem_no}</td>
			<td>${questionVO.quec_no}【${question_classificationVO.quec_name}】</td>
			<td>${questionVO.qu_title}</td>
			<td>${questionVO.qu_date}</td>
			<td>${questionVO.qu_cnt}</td>
	</tr>
</table>

</body>
</html>
