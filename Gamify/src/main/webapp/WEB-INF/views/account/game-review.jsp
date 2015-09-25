<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ page isELIgnored="false" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<style>
.options-correct {
  background-color: #D0F5A9;
  border: 1px solid #213F00;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>LastManStanding</title>
</head>
<link rel="stylesheet" href="<c:url value="/resources/blueprint/screen.css" />" type="text/css" media="screen, projection">
		<link rel="stylesheet" href="<c:url value="/resources/blueprint/print.css" />" type="text/css" media="print">
		<!--[if lt IE 8]>
			<link rel="stylesheet" href="<c:url value="/resources/blueprint/ie.css" />" type="text/css" media="screen, projection">
		<![endif]-->
		<link rel="stylesheet" href="<c:url value="/resources/css/popup.css" />" type="text/css" media="screen, projection">
		<!-- vendor styles -->
		 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
 <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css"/>">
	<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap-theme.min.css"/>">
	<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap-theme.min.css"/>">
	<link rel="stylesheet" href="<c:url value="/resources/css/sidebar.css"/>">
	<link rel="stylesheet" href="<c:url value="/resources/css/login_page.css"/>">
	<link rel="stylesheet" href="<c:url value="/resources/css/page_four.css"/>">
	
	<!-- custom styles -->
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/page_four.css"/>">
	
	<!-- fonts -->
	<link href='http://fonts.googleapis.com/css?family=Lato:300,400' rel='stylesheet' type='text/css'>
	
		
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/lmsgame.css" />" media="screen" />
<!-- vendor scripts -->
	<!-- Latest compiled and minified JavaScript -->
	 <script type="text/javascript" src="<c:url value="/resources/js/jquery-1.11.3.min.js" />" > </script>
	<script type="text/javascript" src="<c:url value="/resources/js/bootstrap.min.js" />" > </script>
	
	
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js">
</script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.cookie.js" /> "></script>
<script id="mathjax" type="text/javascript" src="http://www.hostmath.com/Math/MathJax.js?config=OK"></script>

<script type="text/javascript">

$(document).ready(function() {
   
               
     //$("#gameReview").css("opacity","0.5"); 
     function convertHex(hex,opacity){
    	    hex = hex.replace('#','');
    	    r = parseInt(hex.substring(0,2), 16);
    	    g = parseInt(hex.substring(2,4), 16);
    	    b = parseInt(hex.substring(4,6), 16);
    	    result = 'rgba('+r+','+g+','+b+','+opacity/100+')';
    	    return result;
    	}

    	$('#gameReview').css('background', convertHex('#A7D136',50));
    	$('.label').css('font-weight', "900");
    	$('.label').css('color', "blue");
    	
     


});</script>
<body id="gameReview">
<!-- navbar -->
<nav class="navbar navbar-default navbar-fixed-top">
<div class="container-fluid lms">
    <div class="navbar-header">
	      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
	        <span class="sr-only"></span>
	        <span class="icon-bar"></span>
	      </button>
	      <div class="logo-section">
                  <h1>lastman<span class="half">standing</span></h1>
                </div>
 </div>
 <!-- Collect the nav links, forms, and other content for toggling -->
 <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
     <ul class="nav navbar-nav navbar-right">
        <li><a class="clearTimeInterval" href="#" onClick="$('#backToMainRoom').submit()"><span class="leave-game">Leave Game</span></a></li>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
          <span id="displayUserName"></span> 
          <span class="caret"></span>
          </a>
          <ul class="dropdown-menu">
            <li class="clearTimeInterval"><a href="#"><i class="glyphicon glyphicon-user">&nbsp;Profile</i></a></li>
            <li><a  href="#menu-toggle" id="menu-toggle"><i class="glyphicon glyphicon-indent-left" >&nbsp;Toggle Pane</i></a></li>
            <li class="clearTimeInterval"><a href="#"><i class="glyphicon glyphicon-cog">&nbsp;Settings</i></a></li>
            <li role="separator" class="divider"></li>
            <li class="clearTimeInterval"><a  href="#" onClick="$('#logoutform').submit()"><i class="glyphicon glyphicon-log-out">&nbsp;Logout</i></a></li>
          </ul>
        </li>
      </ul>
  </div><!-- /.navbar-collapse -->    
</div><!-- /.container-fluid -->
</nav>

<form id="backToMainRoom"  action="${pageContext.request.contextPath}/rooms/changeroom"></form>
<form id="logoutform"  action="${pageContext.request.contextPath}/logout"></form>
<div id="page-content-wrapper">
   <div class="container-fluid">
		<!-- timer -->
		<div class="row">
			 <div class="col-lg-2">
			 	<div class="col-md-offset-2 col-md-8">
					
				</div>
			</div>
		<!-- /timer -->
		<div>
	<div>
<div>				
		 <div id="questionSection" class="col-xs-200 col-sm-200 col-md-80 col-lg-20">
          	<c:forEach items="${gi.previousQuestionLogs}" var="questionLog">
		 		<div>
		 		<br/>
          	<br/>
          	<table>
          				<tr><td></td></tr>
  <tr>
    <td colspan="5" class="question"><span class="label">Question:</span>  <c:out value="${questionLog.question.questionText}"></c:out> </td>
  </tr>
  
          				<tr><td>
          				
          				</td>
          				</tr>
  <tr>
  <td><span class="label">Options:</span> </td>  			
     <c:forEach items="${questionLog.question.options}" var="option">
     						<c:if test="${questionLog.answerKey.optionId == option.id}"><td  class="options-correct"><c:out value="${option.text}"></c:out></td></c:if>
     						<c:if test="${questionLog.answerKey.optionId != option.id}"><td  class="option"><c:out value="${option.text}"></c:out></td></c:if>
          					 
          				</c:forEach>
          				</tr>
          				<tr>
          					<td></td>
          				</tr>
         				<tr>
          				<c:forEach items="${questionLog.playersResponses}" var="playerResponse">
          					
          					<td colspan="4" class="players">
	    						 <div><i class="glyphicon glyphicon-user"></i> </div>
	    					<div><c:out value="${playerResponse.user.name}"></c:out></div>
	    						<div>Time Taken: <c:out value="${playerResponse.timeTakenToAnswer}"> milli seconds</c:out></div>
	    						<div>Response Given: <c:out value="${playerResponse.response.text}"></c:out></div>
	    					</td>
	    					
          				</c:forEach>
          				</tr>
          				</table>
          		</div>
          </c:forEach>
	</div>
</div> 
		 
	</div>
</div> 
 <div id="jsonresponse"></div>
</body>
</html>