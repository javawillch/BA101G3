<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><title>BA101G3_BaBeQ Product: Home</title></head>
<body bgcolor='white'>

<table border='1' cellpadding='5' cellspacing='0' width='400'>
  <tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
    <td><h3>BaBeQ Product: Home</h3><font color=red>( MVC )</font></td>
  </tr>
</table>

<p>This is the Home page for BaBeQ Product: Home</p>

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
  <li><a href='<%=request.getContextPath()%>/frontend/shopping/product/listAllProduct.jsp'>List</a> all Products. </li> <br><br>
  
  <li>
    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/product/product.do" >
        <b>��J�ӫ~�s�� (�pPRO00001):</b>
        <input type="text" name="pro_no">
        <input type="submit" value="�e�X">
        <input type="hidden" name="action" value="getOne_For_Display">
    </FORM>
  </li>

  <jsp:useBean id="productSvc" scope="page" class="com.shopping.model.ProductService" />
   
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/product/product.do" >
       <b>��ܰӫ~�s��:</b>
       <select size="1" name="pro_no">
         <c:forEach var="productVO" items="${productSvc.all}" > 
          <option value="${productVO.pro_no}">${productVO.pro_no}
         </c:forEach>   
       </select>
       <input type="submit" value="�e�X">
       <input type="hidden" name="action" value="getOne_For_Display">
    </FORM>
  </li>
  
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/product/product.do" >
       <b>��ܰ��D�D��:</b>
       <select size="1" name="pro_no">
         <c:forEach var="productVO" items="${productSvc.all}" > 
          <option value="${productVO.pro_no}">${productVO.pro_name}
         </c:forEach>   
       </select>
       <input type="submit" value="�e�X">
       <input type="hidden" name="action" value="getOne_For_Display">
     </FORM>
  </li>
  
  
	<jsp:useBean id="product_classificationSvc" scope="page" class="com.shopping.model.Product_ClassificationService" />
 
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/product_classification/product_classification.do" >
       <b><font color=orange>��ܰӫ~����:</font></b>
       <select size="1" name="proc_no">
         <c:forEach var="product_classificationVO" items="${product_classificationSvc.all}" > 
          <option value="${product_classificationVO.proc_no}">${product_classificationVO.proc_name}
         </c:forEach>   
       </select>
       <input type="submit" value="�e�X">
       <input type="hidden" name="action" value="listProducts_ByProc_no_A">
     </FORM>
  </li>
</ul>


<h3>���D�޲z</h3>

<ul>
  <li><a href='<%=request.getContextPath()%>/frontend/shopping/product/addProduct.jsp'>Add</a> a new Product.</li>
</ul>

<h3><font color=orange>���D�����޲z</font></h3>

<ul>
  <li><a href='<%=request.getContextPath()%>/frontend/shopping/product_classification/listAllProduct_Classification.jsp'>List</a> all product_classifications. </li>
</ul>

</body>

</html>
