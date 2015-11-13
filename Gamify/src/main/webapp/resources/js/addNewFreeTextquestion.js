function addNewFreeTextQuestion(){
	var topicId = $("#topicId").val();
	var questionText = sanitizeForLatex($("#freeQuestionText").val());
	var correctAns= sanitizeForLatex($("#freeAnswer").val());
	var difficultyLevel = $( "#freedifficulty" ).val();
	var preTextForFreeTextQustion  = $( "#freePreTextForFreeTextQustion" ).val();
	var postTextForFreeTextQustion = $( "#freePostTextForFreeTextQustion" ).val();
	var maxTimeAllocatedInSec =$( "#ffftimeAllocated" ).val();
	var image_url =	'';	
	$("#freeStatus").html("");
	$("#freeStatus").css("background-color","white");
	$.ajax({
	    url: myContextPath + "/api/content/questions/add/freeText",
	    data: {topicId:topicId,
	    	   questionText:questionText,
	    	   preTextForFreeTextQustion:preTextForFreeTextQustion,
	    	   postTextForFreeTextQustion:postTextForFreeTextQustion,
	    	   correctAnswer:correctAns,
	    	   difficultyLevel:difficultyLevel,
	    	   maxTimeAllocatedInSecs:maxTimeAllocatedInSec,
	    	   image_url:image_url},
	    type: "POST",
	    dataType : "json",
	    async: false,
	    success: function( json ) {
	    	if(json != null){ 
		    	console.log('post new Free Text question');
		    	console.log(json.status);
		    	$("#freeStatus").html(json.message);
		    	if(json.status == 1){
		    		$("#freeStatus").css("background-color","green");
		    		$("#freeStatus").css("color","white");
		    		resetFreeTextForm();
			    	}
		    	else{
		    		$("#freeStatus").css("background-color","red");
		    		$("#freeStatus").css("color","white");
		    		}	
	    	}    	
	    },
	    error: function( xhr, status, errorThrown ) {
	    	$("#freeStatus").html('Somthing Went wrong on Server. Please try again!');
	    	$("#freeStatus").css("background-color","red");
    		$("#freeStatus").css("color","white");	
	    },
	    // Code to run regardless of success or failure
	    complete: function( xhr, status ) {
	    }
	});
}	

function resetFreeTextForm(){
	$("#freeQuestionText").val('');
	$("#freeAnswer").val('');
	$("#freePreTextForFreeTextQustion").val('');
	$("#freePostTextForFreeTextQustion").val('');
	$( "#ffftimeAllocated").val('');
}