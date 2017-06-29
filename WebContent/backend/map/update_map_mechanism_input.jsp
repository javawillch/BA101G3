<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.map.model.*"%>
<%
	Map_MechanismVO map_mechanismVO = (Map_MechanismVO) request.getAttribute("map_mechanismVO"); //EmpServlet.java (Concroller), �s�Jreq��empVO���� (�]�A�������X��empVO, �]�]�A��J��ƿ��~�ɪ�empVO����)
%>

<html>
<head>
	<script src="/BA101G3/utility/ckeditor/ckeditor.js"></script>
	<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
	<link rel="stylesheet" href="/BA101G3/utility/ckeditor/contents.css">
	<script src="/BA101G3/utility/ckfinder/ckfinder.js"></script>
	<script>
		$(document).ready(function() {
			var editor = CKEDITOR.replace( 'map_cnt', {
				width:680,
				filebrowserBrowseUrl : '/BA101G3/utility/ckfinder/ckfinder.html',
				filebrowserImageBrowseUrl : '/BA101G3/utility/ckfinder/ckfinder.html?type=Images', 
				filebrowserFlashBrowseUrl : '/BA101G3/utility/ckfinder/ckfinder.html?type=Flash',
				filebrowserUploadUrl : '/BA101G3/utility/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files', 
				filebrowserImageUploadUrl : '/BA101G3/utility/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images', 
				filebrowserFlashUploadUrl : '/BA101G3/utility/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash' 
			});
			CKFinder.setupCKEditor( editor, '/ckfinder/' );
			//CKEDITOR.instances.map_cnt.setData( '<p>This is the editor data.</p>' );
		});
		
	</script>
<title>�a�Ͼ��c��ƭק� - update_map_mechanism_input.jsp</title></head>

<body bgcolor='white'>

<table border='1' cellpadding='5' cellspacing='0' width='800'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>�a�Ͼ��c��ƭק� - update_map_mechanism_input.jsp</h3>
		<a href="<%=request.getContextPath()%>/backend/map/select_map_mechanism_page.jsp">�^����</a></td>
	</tr>
</table>

<h3>��ƭק�:</h3>
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
		<td>�a�Ͼ��c�s��:<font color=red><b>*</b></font></td>
		<td><%=map_mechanismVO.getMap_no()%></td>
	</tr>
<%-- 	<tr>
		<td>���c����:
		<td><select size="1" name="mapc_no">
			<c:forEach var="map_mechanismVO" items="${map_mechanismSvc.all}">
				<option value="${map_mechanismVO.mapc_no}" ${(empVO.deptno==deptVO.deptno)?'selected':'' } >${deptVO.dname}
			</c:forEach>
		    <option value="${map_mechanismVO.mapc_no == '0'}">�ˤl�\�U
            <option value="${map_mechanismVO.mapc_no == '1'}">������Ϋ~��
            <option value="${map_mechanismVO.mapc_no == '2'}">��|�B�åͩ�
		</select></td>
	</tr> --%>
	<tr>
		<td>���c�W��:</td>
		<td><input type="TEXT" name="map_name" size="45" value="${map_mechanismVO.map_name}" /></td>
	</tr>
	<tr>
		<td>���c�a�}(����):</td>
		<td><input type="TEXT" name="map_adds" size="45"	value="<%=map_mechanismVO.getMap_adds()%>" /></td>
	</tr>
	<tr>
		<td>���c�a�}(�Բ�):</td>
		<td><input type="TEXT" name="map_addc" size="45"	value="<%=map_mechanismVO.getMap_addc()%>" /></td>
	</tr>
	<tr>
		<td>���c�q��:</td>
		<td><input type="TEXT" name="map_phone" size="45" value="<%=map_mechanismVO.getMap_phone()%>" /></td>
	</tr>
	<tr>
		<td>���c�q�l�H�c:</td>
		<td><input type="TEXT" name="map_mail" size="45" value="${map_mechanismVO.map_mail}" /></td>
	</tr>
	<tr>
		<td>���c����:</td>
		<td><textarea rows="10" cols="80" name="map_cnt" id="map_cnt" >
			<%=map_mechanismVO.getMap_cnt()%>
			</textarea>
		</td>
	</tr>
	<tr>
		<td>���c�Ӥ�:</td>
		<td>
			<input type="file" name="map_photo">
			<img src="<%=request.getContextPath()%>/utility/map_mechanismreader?map_no=${map_mechanismVO.map_no}" width='250'>
		</td>
	</tr>

	

</table>
<br>
<input type="hidden" name="action" value="update">
<input type="hidden" name="map_no" value="<%=map_mechanismVO.getMap_no()%>">
<input type="hidden" name="mem_no" value="<%=map_mechanismVO.getMem_no()%>">
<input type="hidden" name="mapc_no" value="<%=map_mechanismVO.getMapc_no()%>">
<input type="hidden" name="map_long" value="<%=map_mechanismVO.getMap_long()%>">
<input type="hidden" name="map_lat" value="<%=map_mechanismVO.getMap_lat()%>">
<input type="hidden" name="map_photo1" value="<%=map_mechanismVO.getMap_photo1()%>">
<input type="hidden" name="map_photo2" value="<%=map_mechanismVO.getMap_photo2()%>">
<input type="hidden" name="map_photo3" value="<%=map_mechanismVO.getMap_photo3()%>">
<input type="hidden" name="map_photo4" value="<%=map_mechanismVO.getMap_photo4()%>">
<input type="hidden" name="map_photo5" value="<%=map_mechanismVO.getMap_photo5()%>">
<input type="hidden" name="requestURL" value="<%=request.getParameter("requestURL")%>"><!--������e�X�ק諸�ӷ��������|��,�A�e��Controller�ǳ���椧��-->
<input type="hidden" name="whichPage" value="<%=request.getParameter("whichPage")%>">  <!--�u�Ω�:istAllEmp.jsp-->
<input type="submit" value="�e�X�ק�"></FORM>

<br>�e�X�ק諸�ӷ��������|:<br><b>
   <font color=blue>request.getParameter("requestURL"):</font> <%= request.getParameter("requestURL")%><br>
   <font color=blue>request.getParameter("whichPage"):</font> <%= request.getParameter("whichPage")%> (���d�ҥثe�u�Ω�:istAllEmp.jsp))</b>
</body>
</html>
