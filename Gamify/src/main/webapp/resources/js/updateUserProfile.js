function getUserDetails(){
	var url = "view";
	var data = {};
	fireAjax(url,data);
	
}

function updateDisplayName(){
	var displayName = $("#displayName").val();
	var url = "edit/displayName";
	var data = {displayName:displayName};
	fireAjax(url,data);
}


function updateEmailAddress(){
	var email = $("#email").val();
	var url = "edit/email";
	var data = {email:email};
	fireAjax(url,data);
}

function updateParentsEmailAddress(){
	var parentEmail = $("#parentEmail").val();
	var url = "edit/parentEmail";
	var data = {parentEmail:parentEmail};
	fireAjax(url,data);
	
}

function updatePhoneNumberAddress(){
	var phoneNumber = $("#phoneNumber").val();
	var url = "edit/phone";
	var data = {phone:phoneNumber};
	fireAjax(url,data);
}

function updateGender(){
	var gender = $("#gender").val();
	var url = "edit/gender";
	var data = {gender:gender};
	fireAjax(url,data);

}

function changePassword(){
	var currentPassword = $("#currentPassword").val();
	var newPassword = $("#newPassword").val();
	var confirmNewPassword = $("#confirmNewPassword").val();
	
	var url = "edit/pwd";
	 data = {currentPassword:currentPassword ,
			 newPassword:newPassword,
			confirmPassword:confirmNewPassword};
	fireAjax(url,data);
}

function fireAjax(url, dataToBeSent){
	$("#updateStatus").html("");
	$("#updateStatus").css("background-color","white");
	$.ajax({
	    url: myContextPath + "/api/profile/" + url,
	    data: dataToBeSent,
	    type: "POST",
	    dataType : "json",
	    async: false,
	    success: function( json ) {
	    	if(json != null){ 
		    	$("#updateStatus").html(json.message);
		    	if(json.status == 1){
		    		$("#updateStatus").css("background-color","green");
		    		$("#updateStatus").css("color","white");
		    		renderResult(json.user);
			    	}
		    	else{
		    		$("#updateStatus").css("background-color","red");
		    		$("#updateStatus").css("color","white");
		    		}	
	    	}    	
	    },
	    error: function( xhr, status, errorThrown ) {
	    	$("#updateStatus").html('Somthing Went wrong on Server. Please try again!');
	    	$("#updateStatus").css("background-color","red");
    		$("#updateStatus").css("color","white");	
	    },
	    // Code to run regardless of success or failure
	    complete: function( xhr, status ) {
	    }
	});
}	

function renderResult(user){
	$("#displayName").val(user.displayName);
	$( "#loginId").val(user.name);
	$("#email").val(user.emailId);
	$("#phoneNumber").val(user.phoneNo);
	$("#gender").val(user.gender);
	$( "#parentEmail").val(user.parentEmailId);
	
}

getUserDetails();