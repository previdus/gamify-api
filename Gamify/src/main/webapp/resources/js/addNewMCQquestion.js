function addNewMCQQuestion(){
	$("#mFormStatus").html('');
	$("#mFormStatus").css("background-color","white");
	
	var topicId = $( "#topicId" ).val();
	var questionText = sanitizeForLatex($( "#inputMQuestionText" ).val());
	var option1Text=sanitizeForLatex($( "#inputMOpt1" ).val());
	var option2Text=sanitizeForLatex($( "#inputMOpt2" ).val());
	var option3Text=sanitizeForLatex($( "#inputMOpt3" ).val());
	var option4Text=sanitizeForLatex($( "#inputMOpt4" ).val());
	var option5Text=sanitizeForLatex($( "#inputMOpt5" ).val());
	var maxTimeAllocatedInSecs =$( "#timeAllocated" ).val();
	var difficultyLevel = $( "#Mdifficulty" ).val();
	var correctOption = $('input[name=mcqcorrectOpt]:radio:checked');
	var	correctOptionNumber = "";
	if (correctOption.length > 0) {
		correctOptionNumber = correctOption.val();
	}
	var image_url =	'';
	
	var ajaxURL = myContextPath+ "/api/content/questions/add/MCQ";
	$.ajax({
	    url: ajaxURL,
	    data: {topicId:topicId,
	    	questionText:questionText,
	    	difficultyLevel:difficultyLevel,
	    	maxTimeAllocatedInSecs:maxTimeAllocatedInSecs,
	    	option1Text:option1Text,
	    	option2Text:option2Text,
	    	option3Text:option3Text,
	    	option4Text:option4Text,
	    	option5Text:option5Text,
	    	correctOptionNumber:correctOptionNumber, 
	    	image_url:image_url},
	    type: "POST",
	    dataType : "json",
	    async: false,
	    success: function( json ) {
	    	if(json != null){ 
		    	$("#mFormStatus").html(json.message);
		    	if(json.status == 1){
		    		$("#mFormStatus").css("background-color","green");
		    		$("#mFormStatus").css("color","white");
		    		resetMCQQuestionForm();
			    	}
		    	else{	
		    		$("#mFormStatus").css("background-color","red");
		    		$("#mFormStatus").css("color","white");
		    		resetMCQQuestionForm();
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

function resetMCQQuestionForm(){
	$( "#inputMQuestionText" ).val('');
	$( "#inputMOpt1" ).val('');
	$( "#inputMOpt2" ).val('');
	$( "#inputMOpt3" ).val('');
	$( "#inputMOpt4" ).val('');
	$( "#inputMOpt5" ).val('');
	$( "#timeAllocated").val('');
}