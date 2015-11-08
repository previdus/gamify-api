function addIncorrectReason(questionId, reason, isSelect, code){	
	var gameId = $( "#gameId" ).val();
	
		 $('#Reason'+ questionId + "A1").hide();
		 $('#Reason'+ questionId + "A2").show();
		 $('#Reason'+ questionId + "B1").hide();
		 $('#Reason'+ questionId + "B2").show();
		 $('#Reason'+ questionId + "C1").hide();
		 $('#Reason'+ questionId + "C2").show();
		 $('#Reason'+ questionId + "D1").hide();
		 $('#Reason'+ questionId + "D2").show();
		 $('#Reason'+ questionId + "E1").hide();
		 $('#Reason'+ questionId + "E2").show();
		 $('#Reason'+ questionId + code + "1").show();
		 $('#Reason'+ questionId + code + "2").hide();
	
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
		    	$("#reviewFormStatus").html(json.message);
		    	if(json.status == 1){
		    		$("#reviewFormStatus").css("background-color","green");
		    		$("#reviewFormStatus").css("color","white");
		    		
			    	}
		    	else{	
		    		$("#reviewFormStatus").css("background-color","red");
		    		$("#reviewFormStatus").css("color","white");
		    		//resetMCQQuestionForm();
		    		}	
	    	}    	
	    },
	    error: function( xhr, status, errorThrown ) {
	    	$("#reviewFormStatus").html('Somthing Went wrong on Server. Please try again!');
	    	$("#reviewFormStatus").css("background-color","red");
    		$("#reviewFormStatus").css("color","white");	
	    },
	    // Code to run regardless of success or failure
	    	complete: function( xhr, status ) {
	    }
	});
}	


function starMarkQuestion(questionId){	
	var gameId = $( "#gameId" ).val();
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
		    	$("#reviewFormStatus").html(json.message);
		    	if(json.status == 1){
		    		$("#reviewFormStatus").css("background-color","green");
		    		$("#reviewFormStatus").css("color","white");
		    		
			    	}
		    	else{	
		    		$("#reviewFormStatus").css("background-color","red");
		    		$("#reviewFormStatus").css("color","white");
		    		//resetMCQQuestionForm();
		    		}	
	    	}    	
	    },
	    error: function( xhr, status, errorThrown ) {
	    	$("#reviewFormStatus").html('Somthing Went wrong on Server. Please try again!');
	    	$("#reviewFormStatus").css("background-color","red");
    		$("#reviewFormStatus").css("color","white");	
	    },
	    // Code to run regardless of success or failure
	    	complete: function( xhr, status ) {
	    }
	});
}	


function requestSolution(questionId){	
	var gameId = $( "#gameId" ).val();
	
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
		    	$("#reviewFormStatus").html(json.message);
		    	if(json.status == 1){
		    		$("#reviewFormStatus").css("background-color","green");
		    		$("#reviewFormStatus").css("color","white");
		    		
			    	}
		    	else{	
		    		$("#reviewFormStatus").css("background-color","red");
		    		$("#reviewFormStatus").css("color","white");
		    		//resetMCQQuestionForm();
		    		}	
	    	}    	
	    },
	    error: function( xhr, status, errorThrown ) {
	    	$("#reviewFormStatus").html('Somthing Went wrong on Server. Please try again!');
	    	$("#reviewFormStatus").css("background-color","red");
    		$("#reviewFormStatus").css("color","white");	
	    },
	    // Code to run regardless of success or failure
	    	complete: function( xhr, status ) {
	    }
	});
}	


