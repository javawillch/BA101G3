<%@ page contentType="text/html; charset=Big5"%>
<%@ page import="com.shopping.model.*"%>
<%
ProductVO productVO = (ProductVO) request.getAttribute("productVO"); //QuestionServlet.java(Concroller), �s�Jreq��questionVO����
%>
<%-- ���X ������Question_ClassificationVO����--%>
<%
  Product_ClassificationService product_classificationSvc = new Product_ClassificationService();
  Product_ClassificationVO product_classificationVO = product_classificationSvc.getOneProduct_Classifiction(productVO.getProc_no());
%>
<%request.setAttribute("product_classificationVO", product_classificationVO); %>
<html>
<head>
<title>�ӫ~��� - listOneProduct.jsp</title>
</head>
<body bgcolor='white'>

<table border='1' cellpadding='5' cellspacing='0' width='1000'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>�ӫ~��� - ListOneProduct.jsp</h3>
		<a href="<%=request.getContextPath()%>/frontend/shopping/product/select_page.jsp"><img src="<%=request.getContextPath()%>/wu/images/back1.gif" width="100" height="32" border="0">�^����</a>
		</td>
	</tr>
</table>

<table border='1' bordercolor='#CCCCFF' width='1000'>
	<tr>
		<th>�ӫ~�s��</th>
		<th>�|���s��(��a)</th>
		<th>�ӫ~����</th>
		<th>�W�����</th>
		<th>�ӫ~�W��</th>
		<th>�ӫ~����</th>
		<th>�ӫ~����</th>
		<th>�ӫ~�Ϥ�</th>
		<th>�ӫ~�Ϥ�1</th>
		<th>�ӫ~�Ϥ�2</th>
		<th>�ӫ~�Ϥ�3</th>
		<th>�ӫ~�Ϥ�4</th>
		<th>�ӫ~�Ϥ�5</th>
		<th>�ӫ~���A</th>
		<th>�I�ڤ覡</th>
		<th>�e�f�覡</th>
		
	</tr>
	<tr align='center' valign='middle'>
			<td>${productVO.pro_no}</td>
			<td>${productVO.mem_no}</td>
			<td>${productVO.proc_no}�i${product_classificationVO.proc_name}�j</td>
			<td>${productVO.pro_date}</td>
			<td>${productVO.pro_name}</td>
			<td>${productVO.pro_price}</td>
			<td>${productVO.pro_intro}</td>
			<td><img src="<%=request.getContextPath()%>/frontend/DBPicReader/DBPicReader.do?id_no=${productVO.pro_no}&action=product"></td>
			<td>${productVO.pro_photo1}</td>
			<td>${productVO.pro_photo2}</td>
			<td>${productVO.pro_photo3}</td>
			<td>${productVO.pro_photo4}</td>
			<td>${productVO.pro_photo5}</td>
			<td>${productVO.pro_stat}</td>
			<td>${productVO.pro_pay}</td>
			<td>${productVO.pro_get}</td>
			
	</tr>
</table>

</body>
</html>
