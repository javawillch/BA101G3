<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.question.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
	QuestionService questionSvc = new QuestionService();
    List<QuestionVO> list = questionSvc.getAll();
    pageContext.setAttribute("list",list);
%>
<jsp:useBean id="question_classificationSvc" scope="page" class="com.question.model.Question_ClassificationService" />

<!DOCTYPE html>
<html lang="">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
		<title>Question</title>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/frontend/css/babeq.css">	
	
		<!--[if lt IE 9]>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->
		<style type="text/css">
		 	.aa{
				margin-top: 20px;   /*為了不要讓Header擋住*/
			}
			.bb{
				margin-top: 20px;   /*為了不要讓Header擋住*/
			}
			img{
				float: left;
			}
			#username{
				float: left;
			}
			#username, #ansDate{
				margin-left: 8px;
				font: bold 14px "微軟正黑體";
			}
			#question{
				margin: 5px 5px 0px 5px;      /*上右下左*/
				font: bold 20px "微軟正黑體";
				text-align: justify;          /*左右對齊*/
				text-justify:inter-ideograph;
			}
			#answer{
				margin: 0px 5px 5px 5px ;     /*上右下左*/
				font: 16px "微軟正黑體";
				text-align: justify;          /*左右對齊*/
				text-justify:inter-ideograph;
			}
			.left_list_group{
				border: solid 15px;
    			border-image: url("pic/borderimg100.png") 27 27 round stretch;
			}

			.list-group{
				font: bold 12px "微軟正黑體";
			}
			.questionBlock{
				border: solid 15px;
    			border-image: url("pic/borderimg100.png") 27 27 round stretch;
			}
			.aaa{
				font-size:20px;
				font-weight: bold;
				text-align: center;
				margin-top: 20px;

			}	
			hr{
				border: solid 15px;
    			border-image: url("pic/l_gl.bmp") 27 27 round stretch;
			}

			h1{
				display:inline-block; 
				font: bold 24px "微軟正黑體";
				padding: 10px;
				margin: 0px;
				border: solid 15px;
    			border-image: url("pic/borderimg100.png") 27 27 round stretch;
			}
			#lii{
				border: solid 8px;
    			border-image: url("pic/borderimg100.png") 27 27 round stretch;
			}

			.searchbar{
				margin-top: 20px;
				border: solid 10px;
    			border-image: url("pic/borderimg100.png") 25 25 round stretch;		
			}
			.btn-success, .btn-success:hover{
				background-color: #888;
				border-color: #888;
			}

				
		</style>	

	</head>
	<body>	
		<header>
			<nav class="navbar navbar-default navbar-fixed-top " role="navigation">
				<div class="container">
					<!-- 手機隱藏選單區 -->
					<div><a class="navbar-brand" href="#"><img src="<%=request.getContextPath()%>/frontend/pic/babeq3.png" width="230"></a></div>
					<div class="collapse navbar-collapse navbar-ex1-collapse">
						<!-- 右選單 -->
			
						<ul class="nav navbar-nav navbar-right">
							<li class="dropdown"><a href="#" class="navbarIcon"><img class="headerIcon" src="<%=request.getContextPath()%>/frontend/pic/list_01.png" width="80"></a></li>
							<li><a href="#" class="navbarIcon"><img class="headerIcon" src="<%=request.getContextPath()%>/frontend/pic/ring_01.png" width="80"></a></li>
							<li><a href="#" class="navbarIcon"><img class="headerIcon" src="<%=request.getContextPath()%>/frontend/pic/login_01.png" width="80"></a></li>
						</ul>
					</div>
					<img src="<%=request.getContextPath()%>/frontend/pic/pencilline.png" class="navbarLine" style="margin-top: 0px">
					<!-- 手機隱藏選單區結束 -->
				</div>
			</nav>
		</header>

		<!-- 左邊filter部分 -->
		<div class="container">
			<div class="input-group searchbar">
				<input type="text" class="form-control" placeholder="請輸入關鍵字">
				<span class="input-group-btn">
					<button class="btn btn-success" type="button"><i class="glyphicon glyphicon-search"></i></button>
				</span>
			</div>				
			<div class="row">
				<div class="col-xs-12 col-sm-2 aa">
					<div class="left_list_group">
						<div class="list-group aaa">
							依照類型
						</div>
						<img src="pic/lgl.bmp" style="margin: 0px; width: 100%" >
						<div class="list-group">
							<button class="list-group-item">全部類型</button>
							<button class="list-group-item">問題</button>
							<button class="list-group-item">回答</button>
							<button class="list-group-item">Post</button>
						</div>
					</div>


					<div class="left_list_group">
						<div class="list-group aaa">
							依照時間
						</div>
						<img src="pic/lgl.bmp" style="margin: 0px; width: 100%" >
						<div class="list-group">
							<button class="list-group-item">全部時間</button>
							<button class="list-group-item">過去一小時內</button>
							<button class="list-group-item">過去一天內</button>
							<button class="list-group-item">過去一禮拜內</button>
							<button class="list-group-item">過去一個月內</button>
							<button class="list-group-item">過去一年內</button>	
						</div>
					</div>
<!-- 
					<ul class="list-group">
					  <li class="list-group-item">
					    <span class="badge">14</span>
					    Cras
					  </li>
					</ul> -->


				</div>
				<div class="col-xs-12 col-sm-10 bb">
					<h1>Question</h1>
<c:forEach var="questionVO" items="${list}" >
					<div class="questionBlock">
						<div id="question">
							${questionVO.qu_title}
						</div><br>
						<div>
							<img src="pic/test/expert_002.png" class="img-circle" width="50">
							<div id=username>${questionVO.mem_no}</div><br>
							<span id=ansDate>${questionVO.qu_date}</span>
						</div><br>
						<div id="answer">
							${questionVO.qu_cnt}
						</div>
					</div>
</c:forEach>
											
				</div>
				<div class="text-center">
					<ul class="pagination ">
						<li><a href="#" id="lii">&laquo;</a></li>
						<li><a href="#" id="lii">1</a></li>
						<li><a href="#" id="lii">2</a></li>
						<li><a href="#" id="lii">3</a></li>
						<li><a href="#" id="lii">4</a></li>
						<li><a href="#" id="lii">5</a></li>
						<li><a href="#" id="lii">6</a></li>
						<li><a href="#" id="lii">7</a></li>
						<li><a href="#" id="lii">8</a></li>
						<li><a href="#" id="lii">9</a></li>
						<li><a href="#" id="lii">10</a></li>
						<li><a href="#" id="lii">&raquo;</a></li>
					</ul>
				</div>
				
				
			</div>
		</div>
		<footer>
			<div class="container">
				<img src="<%=request.getContextPath()%>/frontend/pic/pencilline.png" class="navbarLine">
				<div class="footerBottom navbar-right navbar-nav">
					<a href="#" class="navbarIcon"><img class="headerIcon" src="<%=request.getContextPath()%>/frontend/pic/chat_01.png" width="80"></a>
				</div>
			</div>
		</footer>	
		
		<script src="https://code.jquery.com/jquery.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	</body>
</html>