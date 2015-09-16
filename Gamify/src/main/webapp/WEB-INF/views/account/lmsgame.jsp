<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ page isELIgnored="false" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
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
<script  type="text/javascript">
var pollGameInstance;
var userId;

var timerInterval = null;
//This variable is also used in the backedn in GameConstants.java
//It is called as TIME_NEEDED_TO_WAIT_BEFORE_AUTO_RESPOND_TO_UNANSWERED_QUESTION. And it is 10 seconds more than this value to allow some network delays
var timeNeededToWaitBeforeAutoRespondTowrongAnswer;
var timeNeededToWaitBeforePollingForGame = 5000;
var timeAtWhichQuestionWasDisplayedToTheUser;
var cookieToStoreKeyForUserGameQuestionTime = "LMSuserGameQuestion"
var countDown;
var timeToUpdateTimerDivAndTimerCookie = 1000;
var messageToDisplayWhenWaitingForOtherPlayersToRespond = "You have already responded. Waiting for other players to respond";
var messageToDsisplayWhenTimeHasElapsedAndWaitingForOtherPlayersToRespond = "Sorry, time has elapsed. Loading next question after all other players have responded";
var sorryMessageForTheLoser = "Sorry dude! You ain't the last man standing. Good luck next time.<br/>";
var winnerMessage = 'Congrats!! You are the last man standing<br/>';
var waitingForOtherPlayersToJoinMessage = "Waiting for  other players to join";
var currentUserExistsInTheGame = false;
function handleMenuAndWrapper(){
	$("#menu-toggle").click(function(e) {
	      e.preventDefault();
	      $("#wrapper").toggleClass("toggled");
		  });
  var navbarHeight = $('.navbar').height();
	$("#wrapper").css("margin", navbarHeight);
}

function setAndDisplayUser(){
	 userId = "${userId}";           
     $("#displayUserName").html(userId); 
}

function displayInitialMessageBeforeTheGameLoads(){
	$("#timer").html("Please wait for a moment while  we prepare the game");
}

$(document).ready(function() {
	handleMenuAndWrapper();
	setAndDisplayUser(); 
	displayInitialMessageBeforeTheGameLoads();
	
	 $.ajaxSetup({ cache: false });
	 pollGameInstance = function pollGameInstance()
		{
			
			//$("#game").html("");
			
			$.getJSON( "play/pollGame?userId="+userId, function( data ) {
                if(!data){
					
 					 return showFinalMessage('looks like you are still in an expired game<br/>',false);
 					 
                }
                			  
				  renderHtml(data,true);
				  MathJax.Hub.Queue(["Typeset",MathJax.Hub]);
				});
		 }
	 setInterval(pollGameInstance,timeNeededToWaitBeforePollingForGame);
	 //$('a').live('click', function() { clearInterval(pollGameInstance); });

	 //TODO: verify that this clearInterval actually works
	 $('ul.dropdown-menu').on("click", "li.clearTimeInterval", function(){
		 clearInterval(pollGameInstance);
	 });	 

	 $(window).bind("beforeunload", function() { 
	 	clearInterval(pollGameInstance); 
	 });

	 

});



function loadPlayersHtml(players,  checkForCurrentUser){
	
    var playerHtml = "";
	  	$(players).each(function(index , element) {
	 	 
		$.each(element, function(index1, element1) 
		 {
	 		 
 			 playerHtml += loadOnePlayerHtmlAtATime(element1,checkForCurrentUser);   
	        

		 });
			    
		});

	return playerHtml;
}

function loadOnePlayerHtmlAtATime(element1, checkForCurrentUser){
	if(checkForCurrentUser && element1.user.id == userId)
 	 {
		currentUserExistsInTheGame = true;
		currentUserClass = "highlight-current-user ";
 	 }
	 else{
		currentUserClass = "";
 	 }
	 		 
     


   playerHtml = "<hr class=\"sidebar-hr col-md-9 col-md-offset-1\"></hr>"+

	"<li class=\""+currentUserClass+"sidebar-user-info col-md-12\">"+
	    "<div class=\"col-xs-2 col-sm-2 col-md-2 col-lg-2 sidebar-user-picture\">"+
	    	"<i class=\"glyphicon glyphicon-user\"></i>"+
	    "</div>"+
	    "<div class=\"col-xs-6 col-sm-6 col-md-6 col-lg-6 sidebar-user-name\">"+
		 		"<span class=\"sidebar-user-name\">"+element1.user.displayName+"</span>"+              	
	    "</div>"+
	    "<div class=\"col-xs-2 col-sm-2 col-md-2 col-lg-2 heart\">"+
	    	"<i class=\"glyphicon glyphicon-heart\"></i>"+
	    "</div>"+
	    "<div class=\"col-xs-1 col-sm-1 col-md-1 col-lg-1 life-count\">"+
	    	"<span>"+element1.noOfLife+"</span>"+
	    "</div>"+
	"</li>";
	return playerHtml;	
 }

function isArrayEmpty(x) {
	   for(var i in x) {
	       return false;
	   }
	   return true;
	}


function renderHtml(obj,fromAjax){
	
	$("#jsonresponse").html(JSON.stringify(obj, undefined, 2));
	if(obj.state == "EXPIRED"){
		
		return showFinalMessage('looks like everyone has left the game<br/>',false);
	}
	
	if(obj.state == "WAITING" || obj.state == "NEW"){	
		$("#timer").html(waitingForOtherPlayersToJoinMessage);
	}

	currentUserExistsInTheGame = false;
	
	var winnerPlayerHtml = "";
	var playerHtml = "";
	var loserPlayerHtml = "";
	var quitPlayerHtml = "";
	if(obj.gameWinnerPlayerObject != null)winnerPlayerHtml = "<li class=\"sidebar-headline col-md-12\">Winner</li>"+loadOnePlayerHtmlAtATime(obj.gameWinnerPlayerObject,true);
	if(obj.state !="DONE" && obj.state != "EXPIRED")playerHtml="<li class=\"sidebar-headline col-md-12\">Active Players</li>"+loadPlayersHtml(obj.players, true);
	if(!isArrayEmpty(obj.looserPlayers))loserPlayerHtml = "<li class=\"sidebar-headline col-md-12\">Lost</li>"+loadPlayersHtml(obj.looserPlayers,  false);
	if(!isArrayEmpty(obj.quittingPlayers))quitPlayerHtml = "<li class=\"sidebar-headline col-md-12\">Quit, timed out, or inactive</li>"+ loadPlayersHtml(obj.quittingPlayers, false);
 	var totalPlayerHtml = "<ul class=\"sidebar-nav\">"+winnerPlayerHtml+playerHtml+loserPlayerHtml+quitPlayerHtml+"</ul>";	
 	
 	$("#sidebar-wrapper").html(totalPlayerHtml);


 	
 	

      
    if(!currentUserExistsInTheGame){
        return showFinalMessage(sorryMessageForTheLoser,true);
        
                 
     }		
    else{
    	loadQuestionHtml(obj);
     }
    if(obj.state == "DONE"){
        
    	if( obj.gameWinner != null && obj.gameWinner.id == userId){
    	 return showFinalMessage( winnerMessage,true);
    	}
    	else
        {
    		return showFinalMessage( sorryMessageForTheLoser,true);
        }
    	 
    }

    function loadQuestionHtml(obj){
    	var questionHtml = "";
        timeAtWhichQuestionWasDisplayedToTheUser = $.now();	
        if(obj.currentQuestion != null){    
    	    if($("#currentQuestion"+obj.currentQuestion.id).length == 0){
    	    	
    	    	timeNeededToWaitBeforeAutoRespondTowrongAnswer =  obj.currentQuestion.maxTimeToAnswerInSeconds*1000;
    	    	questionHtml += "<div id=\"currentQuestion"+obj.currentQuestion.id+"\" class=\"question-number\">"+obj.currentQuestion.questionText+"</div><br/>";	    	
    	    	
    	    	questionHtml +="<div id = \"options\"><br/>";
     	    	$(obj.currentQuestion.options).each( function(index,element)
     	    	{
                      questionHtml += "<input type=\"radio\" name=\"option\" id=\"option" + element.id+ 
                      "\" onClick=\"$('#submitOption').removeAttr('disabled')\" value=\""
                      +element.id+"\">"+element.text+"<span id=\"optionCorrect"+element.id+"\" style=\"display:none\">&nbsp;&nbsp;Correct</span><span id=\"optionWrong"
                      +element.id+"\"  style=\"display:none\">&nbsp;&nbsp;Wrong</span></input><br/>";
    	    		   
     	    	});
     	    	questionHtml += "<input type=\"submit\" class=\"btn answer answer-1\"name=\"submitOption\" id=\"submitOption\""+ 
     	 	    	" value=\"Submit\" onClick=\"submitOption("+obj.currentQuestion.id+","+userId+","+timeAtWhichQuestionWasDisplayedToTheUser+","+obj.bang+")\"></input><br/>";
     	    	questionHtml +="</div>";

     	    	$("#questionSection").addClass("question-active");	 
     	    	$("#questionSection").html(questionHtml);
     	    	$("#submitOption").attr("disabled", "disabled");
     	    	MathJax.Hub.Queue(["Typeset",MathJax.Hub]);
     	    	//if(!fromAjax)handleRefreshPage(obj);
               
                 
     	    	
     	    	
     	    	//start the timer for 2 minutes and then when the time elapses submit the question with -1 as the chosen option to indicate that the player did not answer within
     	    	//the stipulated time
     	    	//Do this only if the user has not yet responded to the question
     	    	if(timeNeededToWaitBeforeAutoRespondTowrongAnswer > 0){
     	    	    countDown = timeAtWhichQuestionWasDisplayedToTheUser;
     	    	    

     	    	}
     	    	else
     	 	    {
     	 	 	    
     	    		jQuery("input[name='option']").attr('disabled',true);

     	    		
     	    		if($.cookie($.cookie(cookieToStoreKeyForUserGameQuestionTime)) == -1){
     	    			$("#timer").html(messageToDisplayWhenWaitingForOtherPlayersToRespond);
     	 	    	}
     	    		else if($.cookie($.cookie(cookieToStoreKeyForUserGameQuestionTime)) == -2){
     	    			$("#timer").html(messageToDsisplayWhenTimeHasElapsedAndWaitingForOtherPlayersToRespond);
     	 	    	}
     	 	    }

                if(timeNeededToWaitBeforeAutoRespondTowrongAnswer > 0){ 	    	
     	    	     timerInterval = setInterval(function(){updateTimerDiv(obj.id,obj.currentQuestion.id, obj.currentQuestion.maxTimeToAnswerInSeconds);},timeToUpdateTimerDivAndTimerCookie);
                } 	    	

    		}
        }
        else{
        	$("#questionSection").html(questionHtml);
        }
     }

    function showFinalMessage(message, reviewGame){

   	    
   	    
   	    var reviewGameMessage = "<br/>";
   	    
   	    if(reviewGame)
   	   	{
   	    	reviewGameMessage = "Review the game <a href=\"#\" onClick=\"$(\'#reviewPage\').submit()\">here</a><br/>";
   	   	}
   	    
    	var finalMessage = message +reviewGameMessage+
    	"Go back to the <a  href=\"#\" onClick=\"$(\'#backToMainRoom\').submit()\">Main room</a><br/>";
    	$('#questionSection').hide();
    	$("#timer").hide();
    	$("#timer").html(finalMessage);
    	$("#timer").show();    	
    	clearInterval(pollGameInstance);
   	    clearInterval(timerInterval);
     	 return;
    }
    
}

// function handleRefreshPage(obj){
// 	 //delete and replace previous question timer cookie only if currentQuestion Is Not same as Previous Question
//     if(userId+obj.id+obj.currentQuestion.id != $.cookie(cookieToStoreKeyForUserGameQuestionTime)){

        

//     	$.removeCookie($.cookie(cookieToStoreKeyForUserGameQuestionTime), { path: '/Gamify/' });
//     	$.cookie(cookieToStoreKeyForUserGameQuestionTime,userId+obj.id+obj.currentQuestion.id);
//      }
//     //else since this page has been refreshed set the variable timeNeededToWaitBeforeAutoRespondTowrongAnswer
//     //to the value from that cookie
//     else
//     {
        
//         if($.cookie(userId+obj.id+obj.currentQuestion.id) != null 
//                 && $.cookie(userId+obj.id+obj.currentQuestion.id) != 'undefined')
                    
//         {
//         	timeNeededToWaitBeforeAutoRespondTowrongAnswer = $.cookie(userId+obj.id+obj.currentQuestion.id);
//             if(timeNeededToWaitBeforeAutoRespondTowrongAnswer > 0){
//             	timeNeededToWaitBeforeAutoRespondTowrongAnswer *= 1000;
//             }
            
        	
//         }
    	
//     }
// }

function submitOptionWhenTimeElapsed(questionId){
  	
	
  	$.ajaxSetup({ cache: false });  	
  	jQuery("input[name='option']").attr('disabled',true);  	
  	//$.cookie($.cookie(cookieToStoreKeyForUserGameQuestionTime),-2);
 	$.getJSON( "play/respondToQuestion?userId="+userId+"&questionId="+questionId+"&optionId=-1&timeTakenToRespond=0", function( data ) {	    	

	    	$("#timer").html(messageToDsisplayWhenTimeHasElapsedAndWaitingForOtherPlayersToRespond);
	    	clearInterval(timerInterval);	    	
		    
	     });
 	
	}

function updateTimerDiv(gameId,currentQuestionId, maxTimeAllocatedToRespond){
	maxTimeAllocatedToRespond = maxTimeAllocatedToRespond *1000;
	var internalCountDown = Math.round((maxTimeAllocatedToRespond - ($.now() - countDown))/1000);
	if(internalCountDown < 0) return;
	
	if(internalCountDown > 0){
		
           $("#timer").html(internalCountDown + " seconds remaining to respond!!");
          // $.cookie(userId+gameId+currentQuestionId,internalCountDown);    
        if(internalCountDown > 10)
		{
			$('#timer').css({"background":"#FF9000"});
		}
		else
		{
			$('#timer').css({"background":"#FF0000"});
		}      
          
	}
	else {
		console.log(internalCountDown);
		submitOptionWhenTimeElapsed(currentQuestionId);
		
	}		
}

function submitOption(questionId,userId, timeAtWhichQuestionWasDisplayedToTheUser,bang){
	
	var selectedOptionId = $("input[name='option']:checked").val();
	//$.cookie($.cookie(cookieToStoreKeyForUserGameQuestionTime),-1);
	$("#submitOption").attr("disabled", "disabled");
	clearInterval(timerInterval);
	$("#timer").html(messageToDisplayWhenWaitingForOtherPlayersToRespond);
  	jQuery("input[name='option']").attr('disabled',true);

  	//selected option red
  	if(bang != selectedOptionId){
  	     $("#optionWrong"+selectedOptionId).css("background-color","red");
  	   $("#optionWrong"+selectedOptionId).show();
    }
  	//  	
  	$("#optionCorrect"+bang).css("background-color","#7FFF00");
  	$("#optionCorrect"+bang).show();
  	
	$.getJSON( "play/respondToQuestion?userId="+userId+"&questionId="+questionId+"&optionId="+selectedOptionId+"&timeTakenToRespond="+($.now() - timeAtWhichQuestionWasDisplayedToTheUser), function( data ) {
		
		  renderHtml(data,true);
	});
	
}


</script>
<body>
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
<form id="reviewPage"  action="${pageContext.request.contextPath}/review-game/${gameId}"></form>

<div id="wrapper">
    <div id="sidebar-wrapper">
    </div>
</div>
<div id="page-content-wrapper">
   <div class="container-fluid">
		<!-- timer -->
		<div class="row">
			 <div class="col-lg-12">
			 <div class="col-md-offset-2 col-md-8">
				<div class="timer">
					<div class="clock-wrapper">

					</div>
					<div id="timer" class="col-xs-8 col-sm-8 col-md-8 col-lg-8 text"></div>
					<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2 space"></div>
				</div>
			</div>
		</div>
		<!-- /timer -->
		<br/>
		<br/>
		 
		 <div id="questionSection"></div> 
		 
	</div>
</div> 
 <div id="jsonresponse"></div>
</body>
</html>