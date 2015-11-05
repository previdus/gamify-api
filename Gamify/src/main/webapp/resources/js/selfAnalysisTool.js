function addIncorrectReason(){	
	var gameId = $( "#gameId" ).val();
	var questionId = '';
	var reason = '';
	var ajaxURL = myContextPath+ "/api/tool/self-analysis/incorrect-reason";
	$.ajax({
	    url: ajaxURL,
	    data: {gameId:gameId,
	    	questionId:questionId,
	    	reason:reason},
	    type: "POST",
	    dataType : "json",
	    async: true,
	    success: function( json ) {
	    	if(json != null){ 
		    	$("#mFormStatus").html(json.message);
		    	if(json.status == 1){
		    		$("#mFormStatus").css("background-color","green");
		    		$("#mFormStatus").css("color","white");
		    		
			    	}
		    	else{	
		    		$("#mFormStatus").css("background-color","red");
		    		$("#mFormStatus").css("color","white");
		    		//resetMCQQuestionForm();
		    		}	
	    	}    	
	    },
	    error: function( xhr, status, errorThrown ) {
	    	$("#mFormStatus").html('Somthing Went wrong on Server. Please try again!');
	    	$("#mFormStatus").css("background-color","red");
    		$("#mFormStatus").css("color","white");	
	    },
	    // Code to run regardless of success or failure
	    	complete: function( xhr, status ) {
	    }
	});
}	


function starMarkQuestion(){	
	var gameId = $( "#gameId" ).val();
	var questionId = '';
	
	var ajaxURL = myContextPath+ "/api/tool/self-analysis/star-mark";
	$.ajax({
	    url: ajaxURL,
	    data: {gameId:gameId,
	    	questionId:questionId},
	    type: "POST",
	    dataType : "json",
	    async: true,
	    success: function( json ) {
	    	if(json != null){ 
		    	$("#mFormStatus").html(json.message);
		    	if(json.status == 1){
		    		$("#mFormStatus").css("background-color","green");
		    		$("#mFormStatus").css("color","white");
		    		
			    	}
		    	else{	
		    		$("#mFormStatus").css("background-color","red");
		    		$("#mFormStatus").css("color","white");
		    		//resetMCQQuestionForm();
		    		}	
	    	}    	
	    },
	    error: function( xhr, status, errorThrown ) {
	    	$("#mFormStatus").html('Somthing Went wrong on Server. Please try again!');
	    	$("#mFormStatus").css("background-color","red");
    		$("#mFormStatus").css("color","white");	
	    },
	    // Code to run regardless of success or failure
	    	complete: function( xhr, status ) {
	    }
	});
}	


function requestSolution(){	
	var gameId = $( "#gameId" ).val();
	var questionId = '';
	
	var ajaxURL = myContextPath+ "/api/tool/self-analysis/request-solution";
	$.ajax({
	    url: ajaxURL,
	    data: {gameId:gameId,
	    	questionId:questionId},
	    type: "POST",
	    dataType : "json",
	    async: true,
	    success: function( json ) {
	    	if(json != null){ 
		    	$("#mFormStatus").html(json.message);
		    	if(json.status == 1){
		    		$("#mFormStatus").css("background-color","green");
		    		$("#mFormStatus").css("color","white");
		    		
			    	}
		    	else{	
		    		$("#mFormStatus").css("background-color","red");
		    		$("#mFormStatus").css("color","white");
		    		//resetMCQQuestionForm();
		    		}	
	    	}    	
	    },
	    error: function( xhr, status, errorThrown ) {
	    	$("#mFormStatus").html('Somthing Went wrong on Server. Please try again!');
	    	$("#mFormStatus").css("background-color","red");
    		$("#mFormStatus").css("color","white");	
	    },
	    // Code to run regardless of success or failure
	    	complete: function( xhr, status ) {
	    }
	});
}	


