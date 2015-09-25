<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
 <link rel="stylesheet" href="<c:url value="/resources/blueprint/screen.css" />" type="text/css" media="screen, projection">
        <link rel="stylesheet" href="<c:url value="/resources/blueprint/print.css" />" type="text/css" media="print">
        <link rel="stylesheet" href="<c:url value="/resources/css/login_page.css"/>">
        <link rel="stylesheet" href="<c:url value="/resources/css/roomPage.css"/>">
        <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap-theme.min.css"/>">
            <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css"/>">
        <!--[if lt IE 8]>
            <link rel="stylesheet" href="<c:url value="/resources/blueprint/ie.css" />" type="text/css" media="screen, projection">
        <![endif]-->
        <link rel="stylesheet" href="<c:url value="/resources/css/popup.css" />" type="text/css" media="screen, projection">
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/json.min.js" /> "></script>
         <script type="text/javascript" src="<c:url value="/resources/js/jquery-1.11.3.min.js" />" > </script>
	<script type="text/javascript" src="<c:url value="/resources/js/bootstrap.min.js" />" > </script>
	

<script type="text/javascript">
var userId;
$(document).ready(function() {
    $("#menu-toggle").click(function(e) {
          e.preventDefault();
          $("#wrapper").toggleClass("toggled");
          });
    var navbarHeight = $('.navbar').height();
    $("#wrapper").css("margin", navbarHeight);

     userId = "${userId}";           
     $("#displayUserName").html(userId); 
     $(".room-category").css("padding-left","0px");
     $(".row").css("background-color", "#FF7043");
     $(".row").css("font-weight", "bold");

     $(".each-room").css("margin", "1.5em -125px");
     $("#submitForm").css("margin-left","0px");
     $("#waitMessage").css("margin-left","-120px");
     		

});</script>
<html>
    <head>
        <title>Choose A Exam Category</title>

        <script type="text/javascript">
            function enableCurrentDropDown(examId){                
                $(".examSectionDropDown").hide();
                $('.examSectionDropDown').attr('name','');
                $('#examSection'+examId).attr('name','examSection');
                $("#examSection"+examId).show();
                
            }

            function showRatedPlayersForTheChosenTopic(){
            	$("#waitMessage").html("Please wait for a few moments while we show you a list of rated players for the chosen topic. Then you can play a game on the chosen topic");
            	var topicId = $('.examSectionDropDown').val();
            	$.ajax({
        		    url: "api/topic_elo",
        		    data: {topic:topicId},
        		    type: "GET",
        		    dataType : "json",
        		    success: function( json ) {
        		    	if(json != null){ 
        		    		$("#leaderboard").show();
        		        	$(json.topRatedUsers).each( function(index,element)
        		     	    	{
        		     	    	     index = +index + +1;
        		        		     var leaderboardid = "leaderboardpos" + index;
        		        		     var leaderBoardName ='#'+ leaderboardid + " #" +leaderboardid +"name";
        		        		     var leaderBoardWins ='#'+ leaderboardid + " #" +leaderboardid +"wins";
        		        		     var leaderBoardImage ='#'+ leaderboardid + " #" +leaderboardid +"img";
        		        		     $('#' +leaderboardid).show();
        		        		     $(leaderBoardName).text(element.displayName);
        		        		     $(leaderBoardWins).text(" ( " + element.eloRating + " Elo Rating ) ");
        		        		     if(element.imageUrl != null)
        		        		     	$(leaderBoardImage).attr("src",element.imageUrl);
        		     	    	});
        		    	}
        		    },
        		    error: function( xhr, status, errorThrown ) {
        		    },
        		    // Code to run regardless of success or failure
        		    complete: function( xhr, status ) {
        		    	$("#submitForm").show(); 
        		    	$("#waitMessage").hide();
        		    }
        		});
            	
            	     
            }
        </script>
    </head>
    <body id="roomPage">
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
          <li class="choose-category"><b>Choose Exam Category</b></li>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
          <span id="displayUserName"></span> 
          <span class="caret"></span>
          </a>
          <ul class="dropdown-menu">
            <li class="clearTimeInterval"><a href="#"><i class="glyphicon glyphicon-user">&nbsp;Profile</i></a></li>
            <li class="clearTimeInterval"><a href="#"><i class="glyphicon glyphicon-cog">&nbsp;Settings</i></a></li>
            <li role="separator" class="divider"></li>
            <li class="clearTimeInterval"><a  href="#" onClick="$('#logoutform').submit()"><i class="glyphicon glyphicon-log-out">&nbsp;Logout</i></a></li>
          </ul>
        </li>
      </ul>
  </div><!-- /.navbar-collapse -->    
</div><!-- /.container-fluid -->
</nav>
<form id="logoutform"  action="${pageContext.request.contextPath}/logout"></form>


    <div id="welcomeUser">Welcome ${user.displayName}&nbsp;&nbsp;&nbsp;<img src="${user.imageUrl}"></img></div>
    <div id="exams"></div> 
<div id="logout_pane">
<form id="logoutform"  action="${pageContext.request.contextPath}/logout">
<a href="#" onClick="$('#logoutform').submit()">Logout</a>
</form>
</div>
        <div class="container sub-container">
        <section class="row">
			<div class="col-md-5 pull-left sign-up">
				
				<div id="leaderboard" class="" style="display:none">
					<h2 class="heading">Rated Players!</h2>
					<form class="custom-form">
						<div id="leaderboardpos1" hidden="true" style="display: none; margin-bottom: 1em;">
  						<span class="col-md-1">1</span>
					    <img id="leaderboardpos1img" src="https://s3.amazonaws.com/uifaces/faces/twitter/jsa/73.jpg" style=" border-radius: 50%; border: 3px solid #FFF; opacity: 1;">
					    <span id="leaderboardpos1name" >Gopal</span>
					    <span id="leaderboardpos1Wins"></span>
						</div>    
					  <div id="leaderboardpos2" hidden="true" style="display: none; margin-bottom: 1em;">
					  	<span class="col-md-1">2</span>
					  	<img id="leaderboardpos2img" src="https://s3.amazonaws.com/uifaces/faces/twitter/sauro/73.jpg" style="border-radius: 50%; border: 3px solid #FFF; opacity: 1;">
					  	<span id="leaderboardpos2name">Kariappa</span>
					  	<span id="leaderboardpos2Wins"></span>
						</div>
						<div id="leaderboardpos3" hidden="true" style="display: none; margin-bottom: 1em;">    
					  	<span class="col-md-1">3</span>
					  	<img id="leaderboardpos3img" src="https://s3.amazonaws.com/uifaces/faces/twitter/tomaslau/73.jpg" style="border-radius: 50%; border: 3px solid #FFF; opacity: 1;">
					  	<span id="leaderboardpos3name">Ruchi</span>
					  	<span id="leaderboardpos3Wins"></span>
					  </div>
					  <div id="leaderboardpos4" hidden="true" style="display: none; margin-bottom: 1em;">
					  	<span class="col-md-1">4</span>
					  	<img id="leaderboardpos4img" src="https://s3.amazonaws.com/uifaces/faces/twitter/vladarbatov/73.jpg" style="border-radius: 50%; border: 3px solid #FFF;opacity: 1;">
					  	<span id="leaderboardpos4name">Piyush</span>
					  	<span id="leaderboardpos4Wins"></span>
					  </div>
					  <div id="leaderboardpos5" hidden="true" style="display: none; margin-bottom: 1em;">
					  	<span class="col-md-1">5</span>
					  	<img id="leaderboardpos5img" src="https://s3.amazonaws.com/uifaces/faces/twitter/vladarbatov/73.jpg" style="border-radius: 50%; border: 3px solid #FFF;opacity: 1;">
					  	<span id="leaderboardpos5name">Sumit</span>
					  	<span id="leaderboardpos5Wins"></span>
					  </div>
					</form>
				</div>

			</div>
		
			<div class="col-md-5 sign-up">
				
				<div>
				
					<form:form modelAttribute="room" action="play" method="post">

		            <div class="span-12 last room-category">    
		                <c:out value="${room.roomName}"></c:out><br/>
		
		
		                    <c:forEach var="exam" items="${room.exams}" step="1"> 
		                           <div class="each-room">            
		                         <input type="radio" class="exam-category" id="exam-id${exam.id}" name="exam-id" value="${exam.id}" onclick="enableCurrentDropDown(${exam.id})">
		                          <label>  <c:out value="${exam.examName}"></c:out></label>
		                         <select id="examSection${exam.id}" class="examSectionDropDown"  onchange="showRatedPlayersForTheChosenTopic()"  style="display:none" >
		                         <option selected="selected" disabled="disabled">Select a topic</option>
		                         <c:forEach var="examSection" items="${exam.examSections}" step="1">
		                             <option value="${examSection.id}">${examSection.name}</option>
		                         </c:forEach>
		                         </select>
		                         <br/>    <br>  
		                         </div>             
		                   </c:forEach>
		                   <div id="waitMessage"></div>
		                   <input id="submitForm" type="submit" value="Play a game on this topic" style="display:none"> 
		             </div>
		  
                    <br>
                    <br/>
		
		                   
		
		          
		            </form:form>

				</div>
				

			</div>
		</section>
        
        	
                        
      </div>

</html>