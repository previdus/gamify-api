function searchQuestion(){
	var questionId = $("#questionIdForSearch").val();
	$("#searchStatus").html("");
	$("#searchStatus").css("background-color","white");
	$.ajax({
	    url: myContextPath + "/api/content/questions/search",
	    data: {questionId:questionId},
	    type: "GET",
	    dataType : "json",
	    async: false,
	    success: function( json ) {
	    	if(json != null){ 
	    		$("#displayQuestionIdOnupdate").val(json.question.id);
	    		$("#updateMQuestionText").val(json.question.questionText);
	    		$("#updatetimeAllocated").val(json.question.maxTimeToAnswerInSeconds);
	    		$("#updateMdifficulty").val(json.question.difficultyLevel);
	    		$("#stateOnQuestionUpdate").val(json.question.state);
	    		$("#deletedOptions").html('');
	    		var coun =0;
	    		if(json.question.options != null){
	    			var dontDisplayDeleted = false;
	    			if(json.question.options.length > 5)
	    				dontDisplayDeleted = true;
	    			var i =0;
	    			for(coun = 0; coun < json.question.options.length; coun++){
	    				var option = json.question.options[coun];
	    				if(option.state == 'SOFT_DELETE' && dontDisplayDeleted == true){
	    					var deletedHtml = 'Option: ' + option.text + '<br/>';
	    					$("#deletedOptions").append(deletedHtml);
	    				}else{
	    					 i++;
	    					 var updateOptionIdElement = '#updateOption' + i+ 'Id';
	    					 var updateOptionStateElement = '#updateOption' + i+ 'State';
	    					 var updateOptionTextElement = '#updateinputMOpt' + i;
	    					 var updateCorrectOptionRadio = '#updateCorrectOption' + i;
	    					$(updateOptionIdElement).val(option.id);
	    					$(updateOptionStateElement).val(option.state);
	    					$(updateOptionTextElement).val(option.text);
	    					$(updateCorrectOptionRadio).prop("checked", false);
	    					 if(json.answerKey != null && json.answerKey.optionId == option.id){
	    						$(updateCorrectOptionRadio).prop("checked", true);
	    					}
	    				}
	    				
	    			}
	    			
	    			while(i < 5)
	    			{
	    				 i++;
    					 var updateOptionIdElement = '#updateOption' + i+ 'Id';
    					 var updateOptionStateElement = '#updateOption' + i+ 'State';
    					 var updateOptionTextElement = '#updateinputMOpt' + i;
    					 var updateCorrectOptionRadio = '#updateCorrectOption' + i;
    					$(updateOptionIdElement).val('');
    					$(updateOptionStateElement).val('');
    					$(updateOptionTextElement).val('');
    					$(updateCorrectOptionRadio).prop("checked", false);
	    			}
	    		}
	    		
		    	$("#searchStatus").html(json.message);
		    	if(json.status == 1){
		    		$("#searchStatus").css("background-color","green");
		    		$("#searchStatus").css("color","white");
			    	}
		    	else{
		    		$("#searchStatus").css("background-color","red");
		    		$("#searchStatus").css("color","white");
		    		}	
	    	}    	
	    },
	    beforeSend: function() {
	    	$("#searchStatus").html('Searching ....');
	    	$("#searchStatus").css("background-color","blue");
    		$("#searchStatus").css("color","white");
	    	
	    },
	    error: function( xhr, status, errorThrown ) {
	    	$("#searchStatus").html('Somthing Went wrong on Server. Please try again!');
	    	$("#searchStatus").css("background-color","red");
    		$("#searchStatus").css("color","white");	
	    },
	    // Code to run regardless of success or failure
	    complete: function( xhr, status ) {
	    }
	});
}

function searchQuestionbyState(){
	
	var topicId =  $("#topicId").val();
	var pageNo = $("#updateScreenPageNo").val();
	var limit = $("#updateScreenFetchSize").val();
	var state = $("#searchStateOnUpdateScreen").val();
	
	
	$.ajax({
	    url: myContextPath + "/api/content/questions/search-paginated",
	    data: {topicId:topicId,pageNo:pageNo,limit:limit,state:state},
	    type: "GET",
	    dataType : "json",
	    async: false,
	    success: function( json ) {
	    	if(json != null && json.questions != null && json.questions.length > 0){ 
	    		var quest = json.questions[0].question;
	    		var answerkey = json.questions[0].answerKey;
	    		$("#displayQuestionIdOnupdate").val(quest.id);
	    		$("#updateMQuestionText").val(quest.questionText);
	    		$("#updatetimeAllocated").val(quest.maxTimeToAnswerInSeconds);
	    		$("#updateMdifficulty").val(quest.difficultyLevel);
	    		$("#stateOnQuestionUpdate").val(quest.state);
	    		$("#deletedOptions").html('');
	    		var coun =0;
	    		if(quest.options != null){
	    			var dontDisplayDeleted = false;
	    			if(quest.options.length > 5)
	    				dontDisplayDeleted = true;
	    			var i =0;
	    			for(coun = 0; coun < quest.options.length; coun++){
	    				var option = quest.options[coun];
	    				if(option.state == 'SOFT_DELETE' && dontDisplayDeleted == true){
	    					var deletedHtml = 'Option: ' + option.text + '<br/>';
	    					$("#deletedOptions").append(deletedHtml);
	    				}else{
	    					 i++;
	    					 var updateOptionIdElement = '#updateOption' + i+ 'Id';
	    					 var updateOptionStateElement = '#updateOption' + i+ 'State';
	    					 var updateOptionTextElement = '#updateinputMOpt' + i;
	    					 var updateCorrectOptionRadio = '#updateCorrectOption' + i;
	    					$(updateOptionIdElement).val(option.id);
	    					$(updateOptionStateElement).val(option.state);
	    					$(updateOptionTextElement).val(option.text);
	    					$(updateCorrectOptionRadio).prop("checked", false);
	    					 if(answerkey != null && answerkey.optionId == option.id){
	    						$(updateCorrectOptionRadio).prop("checked", true);
	    					}
	    				}
	    				
	    			}
	    			
	    			while(i < 5)
	    			{
	    				 i++;
    					 var updateOptionIdElement = '#updateOption' + i+ 'Id';
    					 var updateOptionStateElement = '#updateOption' + i+ 'State';
    					 var updateOptionTextElement = '#updateinputMOpt' + i;
    					 var updateCorrectOptionRadio = '#updateCorrectOption' + i;
    					$(updateOptionIdElement).val('');
    					$(updateOptionStateElement).val('');
    					$(updateOptionTextElement).val('');
    					$(updateCorrectOptionRadio).prop("checked", false);
	    			}
	    		}
	    		
		    	$("#searchStatus").html(json.message);
		    	if(json.status == 1){
		    		$("#searchStatus").css("background-color","green");
		    		$("#searchStatus").css("color","white");
			    	}
		    	else{
		    		$("#searchStatus").css("background-color","red");
		    		$("#searchStatus").css("color","white");
		    		}	
	    	}else{
	    		$("#searchStatus").html(json.message);
	    		$("#searchStatus").css("background-color","red");
	    		$("#searchStatus").css("color","white");
	    	}    	
	    },
	    beforeSend: function() {
	    	$("#searchStatus").html('Searching ....');
	    	$("#searchStatus").css("background-color","blue");
    		$("#searchStatus").css("color","white");
    		//alert('d');    	
	    },
	    error: function( xhr, status, errorThrown ) {
	    	$("#searchStatus").html('Somthing Went wrong on Server. Please try again!');
	    	$("#searchStatus").css("background-color","red");
    		$("#searchStatus").css("color","white");	
	    },
	    // Code to run regardless of success or failure
	    complete: function( xhr, status ) {
	    }
	});
}


function resetPagination(){
	$("#updateScreenPageNo").val(0);
	$("#updateScreenFetchSize").val(1);
}