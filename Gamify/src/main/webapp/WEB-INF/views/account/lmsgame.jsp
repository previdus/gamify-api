<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page isELIgnored="false" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>LMS</title>
</head>
<link rel="stylesheet" href="<c:url value="/resources/blueprint/screen.css" />" type="text/css" media="screen, projection">
		<link rel="stylesheet" href="<c:url value="/resources/blueprint/print.css" />" type="text/css" media="print">
		<!--[if lt IE 8]>
			<link rel="stylesheet" href="<c:url value="/resources/blueprint/ie.css" />" type="text/css" media="screen, projection">
		<![endif]-->
		<link rel="stylesheet" href="<c:url value="/resources/css/popup.css" />" type="text/css" media="screen, projection">
<link rel="stylesheet" type="text/css" href="/resources/css/lmsgame.css" media="screen" />
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js">
</script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.cookie.js" /> "></script>
<script  type="text/javascript">
var pollGameInstance;
var userId;

var timerInterval = null;
var timeNeededToWaitBeforeAutoRespondTowrongAnswer = 7000;
var timeNeededToWaitBeforePollingForGame = 5000;
var timeAtWhichQuestionWasDisplayedToTheUser;
var cookieToStoreKeyForUserGameQuestionTime = "LMSuserGameQuestion"
var countDown;
var timeToUpdateTimerDivAndTimerCookie = 1000;
var messageToDisplayWhenWaitingForOtherPlayersToRespond = "You have already responded. Waiting for other players to respond";
var messageToDsisplayWhenTimeHasElapsedAndWaitingForOtherPlayersToRespond = "Sorry, time has elapsed. Loading next question after all other players have responded";

$(document).ready(function() {

	 
     userId = "${userId}";           
     $("#displayUserName").html(userId); 
     var uToken = '<%= request.getAttribute("token") %>'; 
     var json = '<%= request.getAttribute("gi") %>';     
    // var gi = $.parseJSON(json);
	// renderHtml(gi,false);
	 $.ajaxSetup({ cache: false });
	 pollGameInstance = function pollGameInstance()
		{
			
			//$("#game").html("");
			
			$.getJSON( "play/pollGame?userId="+userId, function( data ) {
                if(!data){
					alert('looks like you are still in an expired game. Redirecting you to the main room');
 					clearInterval(pollGameInstance);	                
 					$('#backToMainRoom').submit();
 					return;
                }				  
				  renderHtml(data,true);
				});
		 }
	 setInterval(pollGameInstance,timeNeededToWaitBeforePollingForGame);
	 $('a').live('click', function() { clearInterval(pollGameInstance); });


	 $(window).bind("beforeunload", function() { 
	 	clearInterval(pollGameInstance); 
	 });

});






function renderHtml(obj,fromAjax){
	$("#jsonresponse").html(JSON.stringify(obj, undefined, 2));
if(obj.state == "EXPIRED"){
	alert('looks like everyone has left the game. Redirecting you to the main room');
	clearInterval(pollGameInstance); 
	$('#backToMainRoom').submit();
	return;
}

if(obj.state == "WAITING" || obj.state == "NEW"){	
	$("#timer").html("Waiting for  other players to join");
}

	
	 var playerHtml = "<table>";
     var currentUserExistsInTheGame = false;
     
 	  	$(obj.players).each(function(index , element) {
 	 	 
 		$.each(element, function(index1, element1) 
 		 {
 	 		 if(element1.user.id == userId){
 	 			currentUserExistsInTheGame = true;
 	 			
 	 	 	  }
 	 		 
         
          
          playerHtml += "<td><div id=\"player"+ index1 +"\" style=\"width:100%; height:100%; top:10%; background-color:rgb(234,252,250);overflow-y:scroll;\">"+
// 	        "<a href=\"form.jsp\"> <img src=\"images/"+element.examImageName+".jpg\" alt=\"lms\" width=\"150\" height=\"100\" /></br>"
 	        element1.user.displayName+"&nbsp;&nbsp;&nbsp;<img src=\""+element1.user.imageUrl+"\"></img>&nbsp;&nbsp;&nbsp;(Lives :"+element1.noOfLife+" )</div></td>";
 	        

 		    });
			    
 		});


 	playerHtml += "</table>";
 	$("#playerSection").html(playerHtml);
 	var questionHtml = "";
    timeAtWhichQuestionWasDisplayedToTheUser = $.now();	
    if(obj.currentQuestion != null){    
	    if($("#currentQuestion"+obj.currentQuestion.id).length == 0){
	    	
	    	
	    	questionHtml += "<br><div id=\"currentQuestion"+obj.currentQuestion.id+"\">"+obj.currentQuestion.questionText+"</div><br/>";	    	
	    	
	    	questionHtml +="<div id = \"options\"><br/>";
 	    	$(obj.currentQuestion.options).each( function(index,element)
 	    	{
                  questionHtml += "<input type=\"radio\" name=\"option\" id=\"option" + element.id+ "\" onClick=\"$('#submitOption').removeAttr('disabled')\" value=\""+element.id+"\">"+element.text+"</input><br/>";
	    		   
 	    	});
 	    	questionHtml += "<input type=\"submit\" name=\"submitOption\" id=\"submitOption\" value=\"Submit\" onClick=\"submitOption("+obj.currentQuestion.id+","+userId+","+timeAtWhichQuestionWasDisplayedToTheUser+")\"></input><br/>";
 	    	questionHtml +="</div>";
 	    		 
 	    	$("#questionSection").html(questionHtml);
 	    	$("#submitOption").attr("disabled", "disabled");

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
 	    	     timerInterval = setInterval(function(){updateTimerDiv(obj.id,obj.currentQuestion.id);},timeToUpdateTimerDivAndTimerCookie);
            } 	    	

		}
    }
    else{
    	$("#questionSection").html(questionHtml);
    }
	    
    if(!currentUserExistsInTheGame){
        alert("Sorry dude! You ain't the last man standing. Good luck next time. Redirecting you to the main room");
        clearInterval(pollGameInstance); 
        $('#backToMainRoom').submit();
        return;
      
      }		
    if(obj.state == "DONE"){
        
    	alert('Congrats!! You are the last man standing');
    	clearInterval(pollGameInstance); 
    	$('#backToMainRoom').submit();
    	return;
    }
	
}

function handleRefreshPage(obj){
	 //delete and replace previous question timer cookie only if currentQuestion Is Not same as Previous Question
    if(userId+obj.id+obj.currentQuestion.id != $.cookie(cookieToStoreKeyForUserGameQuestionTime)){

        alert(userId);
        alert(obj.id);
        alert(obj.currentQuestion.id);

        
        alert($.cookie(cookieToStoreKeyForUserGameQuestionTime));
    	$.removeCookie($.cookie(cookieToStoreKeyForUserGameQuestionTime), { path: '/Gamify/' });
    	$.cookie(cookieToStoreKeyForUserGameQuestionTime,userId+obj.id+obj.currentQuestion.id);
     }
    //else since this page has been refreshed set the variable timeNeededToWaitBeforeAutoRespondTowrongAnswer
    //to the value from that cookie
    else
    {
        
        if($.cookie(userId+obj.id+obj.currentQuestion.id) != null 
                && $.cookie(userId+obj.id+obj.currentQuestion.id) != 'undefined')
                    
        {
        	timeNeededToWaitBeforeAutoRespondTowrongAnswer = $.cookie(userId+obj.id+obj.currentQuestion.id);
            if(timeNeededToWaitBeforeAutoRespondTowrongAnswer > 0){
            	timeNeededToWaitBeforeAutoRespondTowrongAnswer *= 1000;
            }
            
        	
        }
    	
    }
}

function submitOptionWhenTimeElapsed(questionId){
  	
	
  	$.ajaxSetup({ cache: false });  	
  	jQuery("input[name='option']").attr('disabled',true);  	
  	//$.cookie($.cookie(cookieToStoreKeyForUserGameQuestionTime),-2);
 	$.getJSON( "play/respondToQuestion?userId="+userId+"&questionId="+questionId+"&optionId=-1&timeTakenToRespond=0", function( data ) {	    	

	    	$("#timer").html(messageToDsisplayWhenTimeHasElapsedAndWaitingForOtherPlayersToRespond);
	    	clearInterval(timerInterval);	    	
		    
	     });
 	
	}

function updateTimerDiv(gameId,currentQuestionId){
	var internalCountDown = Math.round((timeNeededToWaitBeforeAutoRespondTowrongAnswer - ($.now() - countDown))/1000);
	if(internalCountDown > 0){
           $("#timer").html(internalCountDown + " seconds remaining to respond");
          // $.cookie(userId+gameId+currentQuestionId,internalCountDown);          
          
	}
	else{
		
		submitOptionWhenTimeElapsed(currentQuestionId);
		
	}		
}

function submitOption(questionId,userId, timeAtWhichQuestionWasDisplayedToTheUser){
	var selectedOptionId = $("input[name='option']:checked").val();
	//$.cookie($.cookie(cookieToStoreKeyForUserGameQuestionTime),-1);
	$("#submitOption").attr("disabled", "disabled");
	clearInterval(timerInterval);
	$("#timer").html(messageToDisplayWhenWaitingForOtherPlayersToRespond);
  	jQuery("input[name='option']").attr('disabled',true);  
	$.getJSON( "play/respondToQuestion?userId="+userId+"&questionId="+questionId+"&optionId="+selectedOptionId+"&timeTakenToRespond="+($.now() - timeAtWhichQuestionWasDisplayedToTheUser), function( data ) {
		  renderHtml(data,true);
	});
	
}
</script>
<body>
<form id="backToMainRoom"  action="${pageContext.request.contextPath}/rooms/changeroom"></form>
<form id="logoutform"  action="${pageContext.request.contextPath}/logout"></form>
<div id="topPane">
<span id="mainRoomLink">
     <a href="#" onClick="$('#backToMainRoom').submit()">Back to main room</a>
</span>
<span id="welcomeMessage"> Hello <span id="displayUserName"></span>. Welcome to Last Man Standing </span> 
<span id="logoutLink">
<a href="#" onClick="$('#logoutform').submit()">Logout</a>
</span>
</div>
 <div id="timer"></div>
<div id="game">
     
   <div id="playerSection"></div> 
   <div id="questionSection"></div>
   <div id="jsonresponse"></div>
</div>


</body>
</html>