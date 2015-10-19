function updateAns(optionnum){
	var correctOptionKeyElement = '#updateOption' + optionnum + 'Id';
	var correctOptionId = $(correctOptionKeyElement).val();
	updateAnswerKey(correctOptionId);
}

function updateAnswerKey(correctOptionId){
	
	var questionId = $("#displayQuestionIdOnupdate").val();
	$("#updateStatus").html("");
	$("#updateStatus").css("background-color","white");
	$.ajax({
	    url: myContextPath + "/api/content/question/update/answer",
	    data: {questionId:questionId, correctOptionId:correctOptionId},
	    type: "POST",
	    dataType : "json",
	    async: false,
	    success: function( json ) {
	    	if(json != null){ 
		    	$("#updateStatus").html(json.message);
		    	if(json.status == 1){
		    		$("#updateStatus").css("background-color","green");
		    		$("#updateStatus").css("color","white");
			    	}
		    	else{
		    		$("#updateStatus").css("background-color","red");
		    		$("#updateStatus").css("color","white");
		    		}	
	    	}    	
	    },
	    beforeSend: function() {
	    	$("#updateStatus").html('Updating Question ....');
	    	$("#updateStatus").css("background-color","blue");
    		$("#updateStatus").css("color","white");
	    },
	    error: function( xhr, status, errorThrown ) {
	    	$("#updateStatus").html('Somthing Went wrong on Server. Please try again!');
	    	$("#updateStatus").css("background-color","red");
    		$("#updateStatus").css("color","white");	
	    },
	    // Code to run regardless of success or failure
	    complete: function( xhr, status ) {
	    	var questionId = $("#displayQuestionIdOnupdate").val();
	    	$("#questionIdForSearch").val(questionId);
	    	searchQuestion();
	    }
	});
}


function updateQuestionText(){
	var questionId = $("#displayQuestionIdOnupdate").val();
	var questionText = $("#updateMQuestionText").val();
	$("#updateStatus").html("");
	$("#updateStatus").css("background-color","white");
	$.ajax({
	    url: myContextPath + "/api/content/question/update/text",
	    data: {questionId:questionId, questionText:questionText},
	    type: "POST",
	    dataType : "json",
	    async: false,
	    success: function( json ) {
	    	if(json != null){ 
		    	$("#updateStatus").html(json.message);
		    	if(json.status == 1){
		    		$("#updateStatus").css("background-color","green");
		    		$("#updateStatus").css("color","white");
			    	}
		    	else{
		    		$("#updateStatus").css("background-color","red");
		    		$("#updateStatus").css("color","white");
		    		}	
	    	}    	
	    },
	    beforeSend: function() {
	    	$("#updateStatus").html('Updating Question ....');
	    	$("#updateStatus").css("background-color","blue");
    		$("#updateStatus").css("color","white"); 
	    },
	    error: function( xhr, status, errorThrown ) {
	    	$("#updateStatus").html('Somthing Went wrong on Server. Please try again!');
	    	$("#updateStatus").css("background-color","red");
    		$("#updateStatus").css("color","white");	
	    },
	    // Code to run regardless of success or failure
	    complete: function( xhr, status ) {
	    	var questionId = $("#displayQuestionIdOnupdate").val();
	    	$("#questionIdForSearch").val(questionId);
	    	searchQuestion();
	    }
	});
}

function updateDifficultyLevel(){
	var questionId = $("#displayQuestionIdOnupdate").val();
	var difficultyLevel = $("#updateMdifficulty").val();
	$("#updateStatus").html("");
	$("#updateStatus").css("background-color","white");
	$.ajax({
	    url: myContextPath + "/api/content/question/update/difficultyLevel",
	    data: {questionId:questionId, difficultyLevel:difficultyLevel},
	    type: "POST",
	    dataType : "json",
	    async: false,
	    success: function( json ) {
	    	if(json != null){ 
		    	$("#updateStatus").html(json.message);
		    	if(json.status == 1){
		    		$("#updateStatus").css("background-color","green");
		    		$("#updateStatus").css("color","white");
			    	}
		    	else{
		    		$("#updateStatus").css("background-color","red");
		    		$("#updateStatus").css("color","white");
		    		}	
	    	}    	
	    },
	    beforeSend: function() {
	    	$("#updateStatus").html('Updating Question ....');
	    	$("#updateStatus").css("background-color","blue");
    		$("#updateStatus").css("color","white"); 
	    },
	    error: function( xhr, status, errorThrown ) {
	    	$("#updateStatus").html('Somthing Went wrong on Server. Please try again!');
	    	$("#updateStatus").css("background-color","red");
    		$("#updateStatus").css("color","white");	
	    },
	    // Code to run regardless of success or failure
	    complete: function( xhr, status ) {
	    	var questionId = $("#displayQuestionIdOnupdate").val();
	    	$("#questionIdForSearch").val(questionId);
	    	searchQuestion();
	    }
	});
}

function updateTimeAllocation(){

	var questionId = $("#displayQuestionIdOnupdate").val();
	var timeAllocated = $("#updatetimeAllocated").val();
	$("#updateStatus").html("");
	$("#updateStatus").css("background-color","white");
	$.ajax({
	    url: myContextPath + "/api/content/question/update/time",
	    data: {questionId:questionId, timeAllocated:timeAllocated},
	    type: "POST",
	    dataType : "json",
	    async: false,
	    success: function( json ) {
	    	if(json != null){ 
		    	$("#updateStatus").html(json.message);
		    	if(json.status == 1){
		    		$("#updateStatus").css("background-color","green");
		    		$("#updateStatus").css("color","white");
			    	}
		    	else{
		    		$("#updateStatus").css("background-color","red");
		    		$("#updateStatus").css("color","white");
		    		}	
	    	}    	
	    },
	    beforeSend: function() {
	    	$("#updateStatus").html('Updating Question ....');
	    	$("#updateStatus").css("background-color","blue");
    		$("#updateStatus").css("color","white");  
	    },
	    error: function( xhr, status, errorThrown ) {
	    	$("#updateStatus").html('Somthing Went wrong on Server. Please try again!');
	    	$("#updateStatus").css("background-color","red");
    		$("#updateStatus").css("color","white");	
	    },
	    // Code to run regardless of success or failure
	    complete: function( xhr, status ) {
	    	var questionId = $("#displayQuestionIdOnupdate").val();
	    	$("#questionIdForSearch").val(questionId);
	    	searchQuestion();
	    }
	});
}

function updateStateEnable(){
	updateState('enable');
}
function updateStateDisable(){
	updateState('disable');
}
function updateStateSoftDelete(){
	updateState('softDelete');
}

function updateState(action){
	alert('updating state to : ' + action);
	var questionId = $("#displayQuestionIdOnupdate").val();
	$("#updateStatus").html("");
	$("#updateStatus").css("background-color","white");
	$.ajax({
	    url: myContextPath + "/api/content/question/update/" + action,
	    data: {questionId:questionId},
	    type: "POST",
	    dataType : "json",
	    async: false,
	    success: function( json ) {
	    	if(json != null){ 
		    	$("#updateStatus").html(json.message);
		    	if(json.status == 1){
		    		$("#updateStatus").css("background-color","green");
		    		$("#updateStatus").css("color","white");
			    	}
		    	else{
		    		$("#updateStatus").css("background-color","red");
		    		$("#updateStatus").css("color","white");
		    		}	
	    	}    	
	    },
	    beforeSend: function() {
	    	$("#updateStatus").html('Updating Question ....');
	    	$("#updateStatus").css("background-color","blue");
    		$("#updateStatus").css("color","white");  
	    },
	    error: function( xhr, status, errorThrown ) {
	    	$("#updateStatus").html('Somthing Went wrong on Server. Please try again!');
	    	$("#updateStatus").css("background-color","red");
    		$("#updateStatus").css("color","white");	
	    },
	    // Code to run regardless of success or failure
	    complete: function( xhr, status ) {
	    	var questionId = $("#displayQuestionIdOnupdate").val();
	    	$("#questionIdForSearch").val(questionId);
	    	searchQuestion();
	    }
	});
}
