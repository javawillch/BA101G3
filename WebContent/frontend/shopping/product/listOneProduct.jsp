<%@ page contentType="text/html; charset=Big5"%>
<%@ page import="com.shopping.model.*"%>
<%
ProductVO productVO = (ProductVO) request.getAttribute("productVO"); //QuestionServlet.java(Concroller), 存入req的questionVO物件
%>
<%-- 取出 對應的Question_ClassificationVO物件--%>
<%
  Product_ClassificationService product_classificationSvc = new Product_ClassificationService();
  Product_ClassificationVO product_classificationVO = product_classificationSvc.getOneProduct_Classifiction(productVO.getProc_no());
%>
<%request.setAttribute("product_classificationVO", product_classificationVO); %>
<html>
<head>
<title>商品資料 - listOneProduct.jsp</title>
</head>
<body bgcolor='white'>

<table border='1' cellpadding='5' cellspacing='0' width='1000'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>商品資料 - ListOneProduct.jsp</h3>
		<a href="<%=request.getContextPath()%>/frontend/shopping/product/select_page.jsp"><img src="<%=request.getContextPath()%>/wu/images/back1.gif" width="100" height="32" border="0">回首頁</a>
		</td>
	</tr>
</table>

<table border='1' bordercolor='#CCCCFF' width='1000'>
	<tr>
		<th>商品編號</th>
		<th>會員編號(賣家)</th>
		<th>商品分類</th>
		<th>上假日期</th>
		<th>商品名稱</th>
		<th>商品價錢</th>
		<th>商品介紹</th>
		<th>商品圖片</th>
		<th>商品圖片1</th>
		<th>商品圖片2</th>
		<th>商品圖片3</th>
		<th>商品圖片4</th>
		<th>商品圖片5</th>
		<th>商品狀態</th>
		<th>付款方式</th>
		<th>送貨方式</th>
		
	</tr>
	<tr align='center' valign='middle'>
			<td>${productVO.pro_no}</td>
			<td>${productVO.mem_no}</td>
			<td>${productVO.proc_no}【${product_classificationVO.proc_name}】</td>
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
