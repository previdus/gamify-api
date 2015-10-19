function updateOption1Text(){
	updateOptionText('#updateOption1Id','#updateinputMOpt1');
}
	
function updateOption1Enable(){
	updateOptionState('#updateOption1Id', 'enable');
}

function updateOption1Disable(){
	updateOptionState('#updateOption1Id', 'disable');
}

function updateOption1SoftDelete(){
	updateOptionState('#updateOption1Id', 'softDelete');
}


function updateOption2Text(){
	updateOptionText('#updateOption2Id','#updateinputMOpt2');
}
	
function updateOption2Enable(){
	updateOptionState('#updateOption2Id', 'enable');
}

function updateOption2Disable(){
	updateOptionState('#updateOption2Id', 'disable');
}

function updateOption2SoftDelete(){
	updateOptionState('#updateOption2Id', 'softDelete');
}



function updateOption3Text(){
	updateOptionText('#updateOption3Id','#updateinputMOpt3');
}
	
function updateOption3Enable(){
	updateOptionState('#updateOption3Id', 'enable');
}

function updateOption3Disable(){
	updateOptionState('#updateOption3Id', 'disable');
}

function updateOption3SoftDelete(){
	updateOptionState('#updateOption3Id', 'softDelete');
}


function updateOption4Text(){
	updateOptionText('#updateOption4Id','#updateinputMOpt4');
}
	
function updateOption4Enable(){
	updateOptionState('#updateOption4Id', 'enable');
}

function updateOption4Disable(){
	updateOptionState('#updateOption4Id', 'disable');
}

function updateOption4SoftDelete(){
	updateOptionState('#updateOption4Id', 'softDelete');
}


function updateOption5Text(){
	updateOptionText('#updateOption5Id','#updateinputMOpt5');
}
	
function updateOption5Enable(){
	updateOptionState('#updateOption5Id', 'enable');
}

function updateOption5Disable(){
	updateOptionState('#updateOption5Id', 'disable');
}

function updateOption5SoftDelete(){
	updateOptionState('#updateOption5Id', 'softDelete');
}

function updateOptionText(optionElementId, optionTextElementId){
	var questionId = $("#displayQuestionIdOnupdate").val();
	var optionId =  $(optionElementId).val();
	var optionText = sanitizeForLatex($(optionTextElementId).val());
	$("#updateStatus").html("");
	$("#updateStatus").css("background-color","white");
	$.ajax({
	    url: myContextPath + "/api/content/option/update/text",
	    data: {questionId:questionId,optionId:optionId,optionText:optionText},
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
	    	$("#updateStatus").html('Updating Option Text....');
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


function updateOptionState(optionElementId, action){
	var questionId = $("#displayQuestionIdOnupdate").val();
	var optionId =  $(optionElementId).val();
	$("#updateStatus").html("");
	$("#updateStatus").css("background-color","white");
	$.ajax({
	    url: myContextPath + "/api/content/option/update/" + action,
	    data: {optionId:optionId},
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
	    	$("#updateStatus").html('Updating Option status ....');
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