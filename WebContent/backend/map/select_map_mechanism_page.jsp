<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.map.model.*"%>
<%@ page import="java.util.*"%>

<html>
<head><title>BaBeQ Map_Mechanism: Home</title></head>
<body bgcolor='white'>

<table border='1' cellpadding='5' cellspacing='0' width='400'>
  <tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
    <td><h3>BaBeQ Map_Mechanism: Home</h3><font color=red>( MVC )</font></td>
  </tr>
</table>

<p>This is the Home page for BaBeQ Map_Mechanism: Home</p>

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
  <li><a href='<%=request.getContextPath()%>/backend/map/listAllMap_Mechanism.jsp'>List</a> all Map_Mechanisms. </li> <br><br>
  
  <li>
    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/map/map_mechanism.do" >
        <b>��J�a�Ͼ��c�s�� (�pMAP00001):</b>
        <input type="text" name="map_no">
        <input type="submit" value="�e�X">
        <input type="hidden" name="action" value="getOne_For_Display">
    </FORM>
  </li>

  <jsp:useBean id="map_mechanismSvc" scope="page" class="com.map.model.Map_MechanismService" />
   
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/map/map_mechanism.do" >
       <b>��ܦa�Ͼ��c����:</b>
       <select size="1" name="mapc_no"> 
          <option value="${map_mechanismVO.mapc_no == '0'}">�ˤl�\�U
          <option value="${map_mechanismVO.mapc_no == '1'}">������Ϋ~��
          <option value="${map_mechanismVO.mapc_no == '2'}">��|�B�åͩ�
       </select>
       <input type="submit" value="�e�X">
       <input type="hidden" name="action" value="getOne_For_Display">
    </FORM>
  </li>
  
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/map/map_mechanism.do" >
       <b>��ܾ��c�W��:</b>
       <select size="1" name="map_no">
         <c:forEach var="map_mechanismVO" items="${map_mechanismSvc.all}" > 
          <option value="${map_mechanismVO.map_no}">${map_mechanismVO.map_name}
         </c:forEach>   
       </select>
       <input type="submit" value="�e�X">
       <input type="hidden" name="action" value="getOne_For_Display">
     </FORM>
  </li>
  
  	<% 
        List<Map_MechanismVO> list = map_mechanismSvc.getAll();
        Set<String> map_addsSet = new HashSet<String>(); 
        for(Map_MechanismVO map_mechanismVO : list) {
        	String map_add = map_mechanismVO.getMap_adds();
        	System.out.println(map_add);
        	map_addsSet.add(map_add);
        }
        
    %>
  
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/map/map_mechanism.do" >
       <b><font color=orange>��ܾ��c�a��:</font></b>
       <select size="1" name="map_adds">
         <c:forEach var="map_add" items="<%=map_addsSet %>" > 
          <option value="${map_add}">${map_add}
         </c:forEach>   
       </select>
       <input type="submit" value="�e�X">
       <input type="hidden" name="action" value="listEmps_ByMap_adds">
     </FORM>
  </li>
</ul>


<h3>�a�Ͼ��c�޲z</h3>

<ul>
  <li><a href='<%=request.getContextPath()%>/backend/map/addMap_Mechanism.jsp'>Add</a> a new Map_Mechanism.</li>
</ul>

</body>

</html>
