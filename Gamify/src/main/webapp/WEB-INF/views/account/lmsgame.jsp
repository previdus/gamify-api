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

//CONSTANTS
var TIME_NEEDED_TO_WAIT_BEFORE_POLLING = 5000;
var COOKIE_QUESTION_TIME_KEY = "LMSuserGameQuestion";
var TIME_TO_UPDATE_TIMER = 1000;
var WAITING_FOR_OTHER_PLAYERS_TO_RESPON_MESSAGE = "You have already responded. Waiting for other players to respond";
var TIME_ELAPSED_TO_ANSWER_QUESTION_MESSAGE = "Sorry, time has elapsed. Loading next question after all other players have responded";
var LOSER_SORRY_MESSAGE = "Sorry dude! You ain't the last man standing. Good luck next time.<br/>";
var WINNER_MESSAGE = 'Congrats!! You are the last man standing<br/>';
var WAITING_FOR_OTHER_PLAYERS_TO_JOIN_MESSAGE = "Waiting for  other players to join";
var EXPIRED_GAME_MESSAGE = 'looks like you are in an expired game where others have quit or got disconnected and the game cannot be completed<br/>';
var WAIT_UNTIL_WE_PREPARE_THE_GAME_MESSAGE = "Please wait for a moment while  we prepare the game";
var BACKEND_SERVER_ERROR_MESSAGE = "We apologise for the inconvenience. The game has abruptly stopped due to an internal error at the server<br/>";
var GAME_IS_OVER_EXPIRED_MESSAGE = 'This game is over and there are no players currently active<br/>';
var LIMIT_FOR_NUMBER_OF_CONSECUTIVE_TIMES_POLL_HAS_FAILED = 3;

//variables
var pollGameInstance;
var userId;
var timerInterval = null;
/*This variable is also used in the backedn in GameConstants.java
It is called as TIME_NEEDED_TO_WAIT_BEFORE_AUTO_RESPOND_TO_UNANSWERED_QUESTION. And it is 10 seconds more than this value to allow some network delays*/
var timeNeededToWaitBeforeAutoRespondTowrongAnswer;
var timeAtWhichQuestionWasDisplayedToTheUser;
var countDown;
var currentUserExistsInTheGame = false;
var numberOfConsecutiveTimesPollGameFailed = 0;

$(document).ready(function() {
	handleMenuAndWrapper();
	setAndDisplayUser(); 
	displayInitialMessageBeforeTheGameLoads();
	
	 $.ajaxSetup({ cache: false });
	 pollGameInstance = function pollGameInstance()
		{
			
			//$("#game").html("");
			
			$.getJSON( "pollGame?userId="+userId, function( data ) {
                if(!data){
					
 					 return showFinalMessage(EXPIRED_GAME_MESSAGE,false,false);
 					 
                }            
                numberOfConsecutiveTimesPollGameFailed = 0;  			  
			  renderHtml(data,true);
			  MathJax.Hub.Queue(["Typeset",MathJax.Hub]);
			}).error(function(){
				numberOfConsecutiveTimesPollGameFailed++;
				if(numberOfConsecutiveTimesPollGameFailed == LIMIT_FOR_NUMBER_OF_CONSECUTIVE_TIMES_POLL_HAS_FAILED)
					{
					return showFinalMessage(BACKEND_SERVER_ERROR_MESSAGE,false,false);  				
					}
				});
		 }
	 setInterval(pollGameInstance,TIME_NEEDED_TO_WAIT_BEFORE_POLLING);
	 //$('a').live('click', function() { clearInterval(pollGameInstance); });

	 //TODO: verify that this clearInterval actually works
	 $('ul.dropdown-menu').on("click", "li.clearTimeInterval", function(){
		 clearInterval(pollGameInstance);
	 });	 

	 $(window).bind("beforeunload", function() { 
	 	clearInterval(pollGameInstance); 
	 });

	 

});



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
	$("#timer").html(WAIT_UNTIL_WE_PREPARE_THE_GAME_MESSAGE);
}




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



function loadWinnerHtml(gameWinner){
	 winnerHtml = "<hr class=\"sidebar-hr col-md-9 col-md-offset-1\"></hr>"+

		"<li class=\"sidebar-user-info col-md-12\">"+
		    "<div class=\"col-xs-2 col-sm-2 col-md-2 col-lg-2 sidebar-user-picture\">"+
		    	"<i class=\"glyphicon glyphicon-user\"></i>"+
		    "</div>"+
		    "<div class=\"col-xs-6 col-sm-6 col-md-6 col-lg-6 sidebar-user-name\">"+
			 		"<span class=\"sidebar-user-name\">"+gameWinner.displayName+"</span>"+              	
		    "</div>"+
		"</li>";
	return winnerHtml;
}

function isArrayEmpty(x) {
	   for(var i in x) {
	       return false;
	   }
	   return true;
	}


function renderHtml(obj,fromAjax){
	
	$("#jsonresponse").html(JSON.stringify(obj, undefined, 2));
	if(obj.state == "WAITING" || obj.state == "NEW"){	
		$("#timer").html(WAITING_FOR_OTHER_PLAYERS_TO_JOIN_MESSAGE);
	}

	currentUserExistsInTheGame = false;
	console.log(obj.state);
	console.log(obj.gameWinner);
	

	var winnerPlayerHtml = "";
	var playerHtml = "";
	var loserPlayerHtml = "";
	var quitPlayerHtml = "";

	if((obj.state !="DONE" && obj.state != "EXPIRED")){
		playerHtml="<li class=\"sidebar-headline col-md-12\">Active Players</li>"+loadPlayersHtml(obj.players, true);
	}
	else{
		if(!isArrayEmpty(obj.players) && obj.gameWinner != null){
			playerHtml="<li class=\"sidebar-headline col-md-12\">Winner</li>"+loadWinnerHtml(obj.gameWinner);
		}
	}
	if(!isArrayEmpty(obj.looserPlayers))loserPlayerHtml = "<li class=\"sidebar-headline col-md-12\">Lost</li>"+loadPlayersHtml(obj.looserPlayers,  false);
	if(!isArrayEmpty(obj.quittingPlayers))quitPlayerHtml = "<li class=\"sidebar-headline col-md-12\">Quit, timed out, or inactive</li>"+ loadPlayersHtml(obj.quittingPlayers, false);
	

 	var totalPlayerHtml = "<ul class=\"sidebar-nav\">"+winnerPlayerHtml+playerHtml+loserPlayerHtml+quitPlayerHtml+"</ul>";	
 	
 	$("#sidebar-wrapper").html(totalPlayerHtml);

  

	if(obj.state == "DONE" || obj.state == "EXPIRED"){
        
    	if( obj.gameWinner != null && obj.gameWinner.id == userId){
    	 return showFinalMessage( WINNER_MESSAGE,true,true);
    	}
    	else if(obj.gameWinner == null)
        {
    		return showFinalMessage( GAME_IS_OVER_EXPIRED_MESSAGE,true,false);
        }
    	else{
    		return showFinalMessage( LOSER_SORRY_MESSAGE,true,false);
        }
    	 
    }    
     
    if(!currentUserExistsInTheGame){
        return showFinalMessage(LOSER_SORRY_MESSAGE,true,false);
        
                 
     }		
    else{
    	loadQuestionHtml(obj);
     }

    
}

    function loadQuestionHtml(obj){
    	var questionHtml = "";
        timeAtWhichQuestionWasDisplayedToTheUser = $.now();	
        console.log("again 1");
        if(obj.currentQuestion != null){
        	console.log("again 2");    
    	    if($("#currentQuestion"+obj.currentQuestion.id).length == 0){
    	    	console.log("again 3");
    	    	timeNeededToWaitBeforeAutoRespondTowrongAnswer =  obj.currentQuestion.maxTimeToAnswerInSeconds*1000;
    	    	questionHtml += "<div id=\"currentQuestion"+obj.currentQuestion.id+"\" class=\"question-number\">"+obj.currentQuestion.questionText+"</div><br/>";	    	
    	    	
    	    	questionHtml +="<div id = \"options\"><br/>";
    	    	console.log("obj.currentQuestion.options.length:"+obj.currentQuestion.options.length);
    	    	
    	    	if(obj.currentQuestion.options.length > 1 ){
	     	    	$(obj.currentQuestion.options).each( function(index,element)
	     	    	{
	                      questionHtml += "<input type=\"radio\" name=\"option\" id=\"option" + element.id+ 
	                      "\" onClick=\"$('#submitOption').removeAttr('disabled')\" value=\""
	                      +element.id+"\">"+element.text+"<span id=\"optionCorrect"+element.id+"\" style=\"display:none\">&nbsp;&nbsp;Correct</span><span id=\"optionWrong"
	                      +element.id+"\"  style=\"display:none\">&nbsp;&nbsp;Wrong</span></input><br/>";
	    	    		   
	     	    	});
    	    	}
    	    	else {
        	    	questionHtml += "<br/><input class=\"freeResponseText\" id=\"freeResponseText\" type=\"text\" name=\"freeResponseText\" onkeyup=\"test()\"></input><br/>";
        	    	// Enable #x
        	    	$( "#freeResponseText" ).val('test');
        	    	
        	    	
        	    }
     	    	
     	    	questionHtml += "<input type=\"submit\" class=\"btn answer answer-1\"name=\"submitOption\" id=\"submitOption\""+ 
     	 	    	" value=\"Submit\" onClick=\"submitOption("+obj.currentQuestion.id+","+userId+","+timeAtWhichQuestionWasDisplayedToTheUser+","+obj.bang+")\"/><br/>";
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

     	    		
     	    		if($.cookie($.cookie(COOKIE_QUESTION_TIME_KEY)) == -1){
     	    			$("#timer").html(WAITING_FOR_OTHER_PLAYERS_TO_RESPON_MESSAGE);
     	 	    	}
     	    		else if($.cookie($.cookie(COOKIE_QUESTION_TIME_KEY)) == -2){
     	    			$("#timer").html(TIME_ELAPSED_TO_ANSWER_QUESTION_MESSAGE);
     	 	    	}
     	 	    }

                if(timeNeededToWaitBeforeAutoRespondTowrongAnswer > 0){ 	    	
     	    	     timerInterval = setInterval(function(){updateTimerDiv(obj.id,obj.currentQuestion.id, obj.currentQuestion.maxTimeToAnswerInSeconds);},TIME_TO_UPDATE_TIMER);
                } 	    	

    		}
        }
        else{
        	$("#questionSection").html(questionHtml);
        }
     }

    function test(){
        console.log("onkeyup captured:"+$("#freeResponseText").val());
        $("#submitOption").removeAttr("disabled");
       
        
    }
    function showFinalMessage(message, reviewGame,timerCssGreen){

   	    
   	    
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
    	if(timerCssGreen) $('#timer').css({"background":"background-color:rgb(245, 245, 245)"});
    	$("#timer").show();    	
    	clearInterval(pollGameInstance);
   	    clearInterval(timerInterval);   	    
     	 return;
    }
    


// function handleRefreshPage(obj){
// 	 //delete and replace previous question timer cookie only if currentQuestion Is Not same as Previous Question
//     if(userId+obj.id+obj.currentQuestion.id != $.cookie(COOKIE_QUESTION_TIME_KEY)){

        

//     	$.removeCookie($.cookie(COOKIE_QUESTION_TIME_KEY), { path: '/Gamify/' });
//     	$.cookie(COOKIE_QUESTION_TIME_KEY,userId+obj.id+obj.currentQuestion.id);
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
  

 	respondToQuestion(userId,questionId,-1,"",0,true);
 	$("#freeResponseText").attr("disabled","disabled");
 	
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
        	//$('#timer').css({"background":"background-color:rgb(245, 245, 245)"});
        	//$("#page-content-wrapper").css("opacity","");
		}
		else
		{
			$('#timer').css({"background":"#FF0000"});
			//$("#page-content-wrapper").css("background-color","#FF2000");
		//	$("#page-content-wrapper").css("opacity","0.7");
		}      
          
	}
	else {
		console.log(internalCountDown);
		submitOptionWhenTimeElapsed(currentQuestionId);
		
	}		
}

function submitOption(questionId,userId, timeAtWhichQuestionWasDisplayedToTheUser,bang){
	
	var selectedOptionId = -1;
	//$.cookie($.cookie(COOKIE_QUESTION_TIME_KEY),-1);
	$("#submitOption").attr("disabled", "disabled");
	clearInterval(timerInterval);
	$("#timer").html(WAITING_FOR_OTHER_PLAYERS_TO_RESPON_MESSAGE);
	var freeResponseText = "";
	if(!($("#freeResponseText").val() === undefined) && !($("#freeResponseText").val() === null)){
		freeResponseText = $("#freeResponseText").val();
	}
	else{
	
		selectedOptionId = $("input[name='option']:checked").val();
	
	  	jQuery("input[name='option']").attr('disabled',true);
	
	  	//selected option red
	  	if(bang != selectedOptionId){
	  	     $("#optionWrong"+selectedOptionId).css("background-color","red");
	  	   $("#optionWrong"+selectedOptionId).show();
	    }
	  	//  	
	  	$("#optionCorrect"+bang).css("background-color","#7FFF00");
	  	$("#optionCorrect"+bang).show();
	}

	var timeTakenToRespond = $.now() - timeAtWhichQuestionWasDisplayedToTheUser;

	respondToQuestion(userId,questionId,selectedOptionId,freeResponseText,timeTakenToRespond,false);
	$("#freeResponseText").attr("disabled","disabled");
	
}

function respondToQuestion(userId, questionId,selectedOptionId,freeResponseText,timeTakenToRespond, fromElapsed){
	console.log('just before posting respondToQuestion');
	console.log('userId:'+userId);
	console.log('questionId:'+questionId);
	console.log('optionId:'+selectedOptionId);
	console.log('freeResponseText'+freeResponseText);
	console.log('timeTakenToRespond:'+timeTakenToRespond);
	$.ajax({
	    url: "respondToQuestion",
	    data: {userId:userId,questionId:questionId,optionId:selectedOptionId,freeResponseText:freeResponseText,timeTakenToRespond:timeTakenToRespond},
	    type: "POST",
	    dataType : "json",
	    success: function( json ) {
	    	if(json != null){ 
		    	console.log('post respondtoquestion');
		    	console.log(json);		    			    	
		    	if(fromElapsed){
			    	$("#timer").html(TIME_ELAPSED_TO_ANSWER_QUESTION_MESSAGE);
			    	clearInterval(timerInterval);	
			    }    
		    	else{
		    		renderHtml(json,true);
			    }	
		    	

	    	}
	    },
	    error: function( xhr, status, errorThrown ) {
		    // request cannot be served
		    console.log('respondToQuestion failed');
	    },
	    // Code to run regardless of success or failure
	    complete: function( xhr, status ) {
		    
	    }
	})
	
}


</script>
<!-- <body onkeydown="return (event.keyCode == 154)"> -->
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