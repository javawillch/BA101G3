<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.map.model.*"%>
<%
	Map_MechanismVO map_mechanismVO = (Map_MechanismVO) request.getAttribute("map_mechanismVO");
%>

<html>
<head>
	<script src="../../utility/ckeditor/ckeditor.js"></script>
	<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
	<link rel="stylesheet" href="../../utility/ckeditor/contents.css">
	<script src="../../utility/ckfinder/ckfinder.js"></script>
	<script>
		$(document).ready(function() {
			var editor = CKEDITOR.replace( 'map_cnt', {
				width:680,
				filebrowserBrowseUrl : '../../utility/ckfinder/ckfinder.html',
				filebrowserImageBrowseUrl : '../../utility/ckfinder/ckfinder.html?type=Images', 
				filebrowserFlashBrowseUrl : '../../utility/ckfinder/ckfinder.html?type=Flash',
				filebrowserUploadUrl : '../../utility/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files', 
				filebrowserImageUploadUrl : '../../utility/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images', 
				filebrowserFlashUploadUrl : '../../utility/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash' 
			});
			CKFinder.setupCKEditor( editor, '/ckfinder/' );
			//CKEDITOR.instances.map_cnt.setData( '<p>This is the editor data.</p>' );
		});
		
	</script>
<title>�a�Ͼ��c��Ʒs�W - addMap_Mechanism.jsp</title></head>

<body bgcolor='white'>

<table border='1' cellpadding='5' cellspacing='0' width='800'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>�a�Ͼ��c��Ʒs�W - addMap_Mechanism.jsp</h3>
		</td>
		<td>
		   <a href="<%=request.getContextPath()%>/backend/map/select_map_mechanism_page.jsp">�^����</a>
	    </td>
	</tr>
</table>

<h3>�a�Ͼ��c���:</h3>
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

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/map/map_mechanism.do" name="form1" enctype="multipart/form-data">
<table border="0">
	<tr>
		<td>���c����:
		<td><select size="1" name="mapc_no">
		    <option value="0">�ˤl�\�U
            <option value="1">������Ϋ~��
            <option value="2">��|�B�åͩ�
		</select></td>
	</tr>
	<tr>
		<td>���c�W��:</td>
		<td><input type="TEXT" name="map_name" size="45" 
			value="<%= (map_mechanismVO==null)? "" : map_mechanismVO.getMap_name()%>" /></td>
	</tr>
	<tr>
		<td>���c�a�}(����):</td>
		<td><input type="TEXT" name="map_adds" size="45"
			value="<%= (map_mechanismVO==null)? "" : map_mechanismVO.getMap_adds()%>" /></td>
	</tr>
	<tr>
		<td>���c�a�}(�Բ�):</td>
		<td><input type="TEXT" name="map_addc" size="45"
			value="<%= (map_mechanismVO==null)? "" : map_mechanismVO.getMap_addc()%>" /></td>
	</tr>
	<tr>
		<td>���c�q��:</td>
		<td><input type="TEXT" name="map_phone" size="45"
			value="<%= (map_mechanismVO==null)? "" : map_mechanismVO.getMap_phone()%>" /></td>
	</tr>
	<tr>
		<td>���c�q�l�H�c:</td>
		<td><input type="TEXT" name="map_mail" size="45"
			value="<%= (map_mechanismVO==null)? "" : map_mechanismVO.getMap_mail()%>" /></td>
	</tr>
	<tr>
		<td>���c����:</td>
		<td><textarea rows="10" cols="80" name="map_cnt" id="map_cnt" >
			<%= (map_mechanismVO==null)? "" : map_mechanismVO.getMap_cnt()%>
			</textarea>
		</td>
	</tr>
	<tr>
		<td>���c�Ӥ�:</td>
		<td>
			<input type="file" name="map_photo">
			<img width="200" src="<%= (map_mechanismVO==null)? "" : map_mechanismVO.getMap_photo()%>">
		</td>
	</tr>

</table>
<br>
<input type="hidden" name="action" value="insert">
<input type="submit" value="�e�X�s�W"></FORM>
</body>

</html>
