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
<title>LMS-Review</title>
</head>
		<!-- vendor styles -->
		 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
 <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css"/>">
	<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap-theme.min.css"/>">
	<link rel="stylesheet" href="<c:url value="/resources/css/sidebar.css"/>">
	
	<!-- custom styles -->
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/page_four.css"/>">

	
	
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

    	$('#gameReview').css('background');
    	$('.label').css('font-weight', "900");
    	$('.label').css('color', "white");
    	$('.showDivision').css('border-bottom', "10px solid #000");
    	
     


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
<br><br><br>
<div>
<div id="questionSection"  >
<nav class="breadcrumb ">
<h3>Game Review Screen</h3>
<c:if test="${gi.topic != null}">
Topic:  <c:out value="${gi.topic.name}"></c:out>
</c:if>
<c:if test="${gi.examSection != null}">
Subject:  <c:out value="${gi.examSection.name}"></c:out>
</c:if>
<c:if test="${gi.gameWinner != null}">
<div>
Winner:  <c:out value="${gi.gameWinner.displayName}"></c:out>
Winning Bonus Points:  <c:out value="${gi.gameWinningPoints}"></c:out>
</div>
</c:if>
<c:if test="${gi.gameWinner == null}">
<div>No Winner</div>
</c:if>
<div>
Played On: <c:out value="${gi.gameCreationTime}"></c:out>
</div>
</nav>
     <c:forEach items="${gi.previousQuestionLogs}" var="questionLog">
		<div class="panel panel-default">
          	<div class="panel-heading">
    			<h4>Q) <c:out value="${questionLog.question.questionText}"></c:out></h4>
					Best Time: <i class="glyphicon glyphicon-hourglass"></i> <c:out value="${questionLog.bestTime}"></c:out>
  			</div>
  			<div class="panel-body">	
  			<table class="table"> 
  					 <tr>
  					 <td>			
  					<p><span >Options:</span> </p>
  					 </td>
     				<c:forEach items="${questionLog.question.options}" var="option" varStatus="stat">
     				<td>
     				<p>
     					<c:if test="${questionLog.answerKey.optionId == option.id}">
     						<div>
     						<span class="label label-success"><i class="glyphicon glyphicon-ok"></i><c:out value="${option.text}"></c:out></span>
     						</div></c:if>
     						<c:if test="${questionLog.answerKey.optionId != option.id}">
     						<div >
     						<span class="label label-danger"><c:out value="${option.text}"></c:out></span>
     						</div></c:if>
     						</p>
     						</td>
          			</c:forEach>
          			</tr>
          			</table>
          		<nav class="breadcrumb ">
					Player Responses
				</nav>
         		
         		<table class="table">
         		<tr>
          			<c:forEach items="${questionLog.playersResponses}" var="playerResponse">
          			<td>	
          			<c:if test="${questionLog.answerKey.optionId == playerResponse.response.id}">
	    					<div class="row">
	    						<div class="col-sm-6 col-md-4">
    								<div class="thumbnail">
      									<img src="https://s3.amazonaws.com/uifaces/faces/twitter/vladarbatov/73.jpg" style="border-radius: 50%; border: 3px solid #FFF;opacity: 1;" alt="...">
	    								<div class="caption">
	    									<h2></h2><p><c:out value="${playerResponse.user.name}"></c:out></p></h2>
	    									<p><i class="glyphicon glyphicon-hourglass"></i> 
	    									<c:out value="${playerResponse.timeTakenToAnswer}"></c:out> (ms)
	    									<div><span class="label label-danger">Response Given: <c:out value="${playerResponse.response.text}"></c:out>
	    										</span>
	    										<div><span class="label">Points Earned: <c:out value="${playerResponse.pointsEarned}"></c:out></span></div>
	    											<c:if test="${playerResponse.questionWinner == true}">
															Winner:<i class="glyphicon glyphicon-ok"></i> 
													</c:if>
													<c:if test="${playerResponse.questionWinner == false}">
															Winner:<i class="glyphicon glyphicon-remove"></i> 
													</c:if>
	    									</div>
	    							    </div>
	    						    </div>
	    					     </div>
	    				    </div>
	    			</c:if>
	    			<c:if test="${questionLog.answerKey.optionId != playerResponse.response.id}">
	    					<div class="row">
	    						<div class="col-sm-6 col-md-4">
    								<div class="thumbnail">
      									<img src="https://s3.amazonaws.com/uifaces/faces/twitter/vladarbatov/73.jpg" style="border-radius: 50%; border: 3px solid #FFF;opacity: 1;" alt="...">
	    								<div class="caption">
	    									<h2></h2><p><c:out value="${playerResponse.user.name}"></c:out></p></h2>
	    									<p><i class="glyphicon glyphicon-hourglass"></i> 
	    									<c:out value="${playerResponse.timeTakenToAnswer}"></c:out> (ms)
	    									<div><span class="label label-danger">Response Given: <c:out value="${playerResponse.response.text}"></c:out>
	    										</span>
	    										<div>Points Earned: <c:out value="${playerResponse.pointsEarned}"></c:out></div>
	    										<c:if test="${playerResponse.questionWinner == true}">
															Winner:<i class="glyphicon glyphicon-ok"></i> 
													</c:if>
													<c:if test="${playerResponse.questionWinner == false}">
															Winner:<i class="glyphicon glyphicon-remove"></i> 
													</c:if>
	    									</div>
	    							    </div>
	    						    </div>
	    					     </div>
	    				    </div>
	    			</c:if>
	    			</td>
          			</c:forEach>
          			</tr>
          			</table>
          	</div>
          	
          </div>
        </c:forEach>
        </div>
	</div> 
 
</body>
</html>