<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.map.model.*"%>
<%-- �����m�߱ĥ� Script ���g�k���� --%>

<%-- ���X Concroller EmpServlet.java�w�s�Jrequest��EmpVO����--%>
<%Map_MechanismVO map_mechanismVO = (Map_MechanismVO) request.getAttribute("map_mechanismVO");%>
<html>
<head>
<title>�a�Ͼ��c��� - listOneMap_Mechanism.jsp</title>
</head>
<body bgcolor='white'>
<b><font color=red>�����m�߱ĥ� Script ���g�k����:</font></b>
<table border='1' cellpadding='5' cellspacing='0' width='1000'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>�a�Ͼ��c��� - listOneMap_Mechanism.jsp</h3>
		<a href="<%=request.getContextPath()%>/backend/map/select_map_mechanism_page.jsp">�^����</a>
		</td>
	</tr>
</table>

<table border='1' bordercolor='#CCCCFF' width='1000'>
	<tr>
		<th>�a�Ͼ��c�s��</th>
		<th>���c����</th>
		<th>���c�W��</th>
		<th>���c�a�}</th>
		<th>���c�q��</th>
		<th>���c�q�l�H�c</th>
		<th>���c����</th>
		<th>���c�Ӥ�</th>
	</tr>
	<tr align='center' valign='middle'>
			<td>${map_mechanismVO.map_no}</td>
			<td><c:choose>
					<c:when test="${map_mechanismVO.mapc_no == 0}">�ˤl�\�U</c:when>
					<c:when test="${map_mechanismVO.mapc_no == 1}">������Ϋ~��</c:when>
					<c:when test="${map_mechanismVO.mapc_no == 2}">��|�B�åͩ�</c:when>	
				</c:choose>
			</td>
			<td>${map_mechanismVO.map_name}</td>
			<td>${map_mechanismVO.map_adds}${map_mechanismVO.map_addc}</td>
			<td>${map_mechanismVO.map_phone}</td>
			<td>${map_mechanismVO.map_mail}</td>			
			<td>${map_mechanismVO.map_cnt}</td>
			<td>${map_mechanismVO.map_photo}</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/map/map_mechanism.do">
			     <input type="submit" value="�ק�"> 
			     <input type="hidden" name="map_no" value="${map_mechanismVO.map_no}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--�e�X�����������|��Controller-->
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/map/map_mechanism.do">
			    <input type="submit" value="�R��">
			    <input type="hidden" name="map_no" value="${map_mechanismVO.map_no}">
			    <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--�e�X�����������|��Controller-->
			    <input type="hidden" name="action"value="delete"></FORM>
			</td>
	</tr>
</table>

</body>
</html>
