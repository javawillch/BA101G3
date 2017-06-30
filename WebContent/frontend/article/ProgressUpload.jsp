<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
	<title>Insert title here</title>
	<style type="text/css">
		body, td, div{
			font-size: 24px; 
			font-family: �L�n������;
		}
		#progressBar{
			width:400px; 
			height:100%;
			background:#FFF;
			border:1px solid #000000;
			padding: 1px;
		}
		#progressBarItem{
			width:30%;
			height:100%;
			background:#FF0000;
		}
	</style>
</head>
<body>
	<iframe name="upload_iframe" width="0" height="0"></iframe>
	
	<form action="<%=request.getContextPath()%>/progressUpload/progressUpload.do" method="post" enctype="multipart/form-data" target="upload_iframe" onsubmit="showStatus();">
		<input type="file" name="file1" style="width: 350px;"><br/>
		<input type="file" name="file2" style="width: 350px;"><br/>
		<input type="file" name="file3" style="width: 350px;"><br/>
		<input type="file" name="file4" style="width: 350px;"><br/>
		<input type="submit" value="�}�l�W��" id="btnSubmit">
	</form>
	
	<div id="status" style="display:none;">
		<div id="progressBar"><div id="progressBarItem"></div></div>
		<div id="statusInfo"></div>
	</div>
	
	
	<script type="text/javascript">
		var _finished = true;
		function $(obj){
			return document.getElementById(obj);
		}
		
		
		function showStatus(){
			_finished = false;
			$('status').style.display = 'block';
			$('progressBarItem').style.width = '1%';
			$('btnSubmit').disabled = true;
			setTimeout("requestStatus()", 1000);
		}
		
		function requestStatus(){
			if(_finished) return;
			var req = createRequest();
			console.log(req);
			req.open("GET", "<%=request.getContextPath()%>/progressUpload/progressUpload.do");
			req.onreadystatechange = function(){callback(req);}
			req.send(null);
			setTimeout("requestStatus()", 1000);
		}
		
		function createRequest(){
			if(window.XMLHttpRequest){
				return new XMLHttpRequest();
			}else{
				
			}
			return null;
		}
		
		function callback(req){
			if(req.readyState==4){
				if(req.status!=200){
					_debug("�o�Ϳ��~�C req.status: "+ req.status + "");
					return;
				}
				_debug("status.jsp �^�ǭ�"+ req.responseText);
				var ss = req.response.split("||");
				$('progressBarItem').style.width = '' + ss[0] + '%';
				$('statusInfo').innerHTML = '�w�����ʤ���: ' + ss[0] + '%<br/>�w������(M):' + ss[1] +'<br/>�ɮ��`����(M):'+
					ss[2] + '<br/>�ǿ�t�v(K)' +ss[3] + '<br/>�w�ήɶ�(s):' +ss[4]+'<br/>���p�`�ɶ�'+ss[5]+'<br/>���p�Ѿl�ɶ�'+
					ss[6] + '<br/>���b�W�ǲĴX�Ӥ��' + ss[7];
				
				if(ss[1] == ss[2]){
					_finished =true;
					$('statusInfo').innerHtml += "<br><br><br> �W�Ǥw�����C";
					$('btnSubmit').disabled = false;
				}
			}
		}
		
		function _debug(obj){
			var div = document.createElement("DIV");
			div.innerHTML = "[debug]:" + obj;
			document.body.appendChild(div);
		}
		
	
	</script>
	
	
</body>
</html>