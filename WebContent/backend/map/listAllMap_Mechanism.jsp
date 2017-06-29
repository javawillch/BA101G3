<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.map.model.*"%>
<%-- �����m�߱ĥ� EL ���g�k���� --%>

<%
	Map_MechanismService map_mechanismSvc = new Map_MechanismService();
	List<Map_MechanismVO> list = map_mechanismSvc.getAll();
	pageContext.setAttribute("list",list);
%>

<html>
<head>
<title>�Ҧ��a�Ͼ��c��� - listAllMap_Mechanism.jsp</title>
</head>
<body bgcolor='white'>
<table border='1' cellpadding='5' cellspacing='0' width='1000'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>�Ҧ��a�Ͼ��c��� - ListAllMap_Mechanism.jsp</h3>
		<a href="<%=request.getContextPath()%>/backend/map/select_map_mechanism_page.jsp">�^����</a>
		</td>
	</tr>
</table>

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
	<%@ include file="pages/page1.file" %> 
	<c:forEach var="map_mechanismVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		<tr align='center' valign='middle' ${(map_mechanismVO.map_no==param.map_no) ? 'bgcolor=#CCCCFF':''}><!--�N�ק諸���@���[�J����Ӥw-->
			<td>${map_mechanismVO.map_no}</td>
			<td>
				<c:choose>
					<c:when test="${map_mechanismVO.mapc_no == '0'}">�ˤl�\�U</c:when>
					<c:when test="${map_mechanismVO.mapc_no == '1'}">������Ϋ~��</c:when>
					<c:when test="${map_mechanismVO.mapc_no == '2'}">��|�B�åͩ�</c:when>	
				</c:choose>
			</td>
			<td>${map_mechanismVO.map_name}</td>
			<td>${map_mechanismVO.map_adds}${map_mechanismVO.map_addc}</td>
			<td>${map_mechanismVO.map_phone}</td>
			<td>${map_mechanismVO.map_mail}</td>			
			<td>${map_mechanismVO.map_cnt}</td>
			<td>
			<img src="<%=request.getContextPath()%>/utility/map_mechanismreader?map_no=${map_mechanismVO.map_no}">
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/map/map_mechanism.do">
			     <input type="submit" value="�ק�"> 
			     <input type="hidden" name="map_no" value="${map_mechanismVO.map_no}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--�e�X�����������|��Controller-->
			     <input type="hidden" name="whichPage"	value="<%=whichPage%>">               <!--�e�X��e�O�ĴX����Controller-->
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/map/map_mechanism.do">
			     <input type="submit" value="�R��">
			     <input type="hidden" name="map_no" value="${map_mechanismVO.map_no}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--�e�X�����������|��Controller-->
			     <input type="hidden" name="whichPage"	value="<%=whichPage%>">               <!--�e�X��e�O�ĴX����Controller-->
			     <input type="hidden" name="action"value="delete"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>
<%@ include file="pages/page2.file" %>

<br>�����������|:<br><b>
   <font color=blue>request.getServletPath():</font> <%= request.getServletPath()%><br>
   <font color=blue>request.getRequestURI(): </font> <%= request.getRequestURI()%> </b>
</body>
</html>
