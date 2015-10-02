<!DOCTYPE html>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
	<head>
		 <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Login Page</title>
		 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
  <link rel="stylesheet" type="text/css" href="resources/css/common.css">
  <link rel="stylesheet" type="text/css" href="resources/css/login_page.css">
  <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script type="text/javascript" src="http://www.hostmath.com/Math/MathJax.js?config=OK"></script>
		
		<script>


		function handleRegistrationValidationErrors(json){
			$("#registrationStatus").css("background-color","red");
    		$("#registrationStatus").css("color","white");
    		
    		
    		if(json.status == -1){
	    		$("#memberFullName").css("background-color","red");
	    	}
    		else if(json.status == -2){
    			$(".radioButton").css("background-color","red");
    		}
    		else if(json.status == -3){
    			$("#userName").css("background-color","red");
    		}
    		else if(json.status == -4){
    			$("#mpassword").css("background-color","red");
    		}
    		else if(json.status == -5){
    			$("#cpassword").css("background-color","red");
	    		
    		}else if(json.status == -6){
    			$("#memberEmail").css("background-color","red");						    		
    		}
    		else if(json.status == -7){
    			$("#parentsEmail").css("background-color","red");
    		}
		}
		

		function signup(){
			var userName = $( "#userName" ).val();
			var memberName = $( "#memberFullName" ).val();
			var memberEmail = $( "#memberEmail" ).val();
			var parentsEmail = $( "#parentsEmail" ).val();
			var mpassword = $( "#mpassword" ).val();
			var cpassword = $( "#cpassword" ).val();
			var selectedGender = $('input[name=gendergrp]:radio:checked');
			var	selectedGen = "";
			if (selectedGender.length > 0) {
				selectedGen = selectedGender.val();
			}
			$.ajax({
			    url: "api/register",
			    data: {displayName:memberName,gender:selectedGen,userName:userName,password:mpassword,cpassword:cpassword,parentsEmail:parentsEmail,email:memberEmail},
			    type: "POST",
			    dataType : "json",
			    success: function( json ) {
			    	if(json != null){ 
				    	console.log('post registration');
				    	console.log(json.status);
				    	
				    	$(".errorColor").css("background-color","");
			    		$("#registrationStatus").css("background-color","");
			    		$("#registrationStatus").css("color","");
				    	if(json.status == 1){
					    	// section for successfully signup.
					    	// display json.message 
				    		$("#registrationStatus").css("background-color","orange");
				    		$("#registrationStatus").css("color","white");
				    		
					    	
					    	}
				    	else{
					    		
					    		handleRegistrationValidationErrors(json);
					    		
					    		
						    	// validation failure// show error in red
						    }
				    	$("#registrationStatus").html(json.message);
			    	}
			    },
			    error: function( xhr, status, errorThrown ) {
				    // request cannot be served
			    },
			    // Code to run regardless of success or failure
			    complete: function( xhr, status ) {
			    }
			})
			}
		 
		$(document).ready(function() {
		     $("#status").html("${status}");
		     var loginStatus = "${loginStatus}";
		     if(loginStatus != null && loginStatus.length != 0){
		    	 $("#status").html(loginStatus +"<br/>");
		    	 $("#status").css("background-color","red");
		    	 $("#status").css("color","white");
			 }
		      
             
		     
			$("#signup").click(function(){
				signup();
		    });
			$.ajax({
			    url: "api/leader-board/maxwin",
			    data: {},
			    type: "GET",
			    dataType : "json",
			    success: function( json ) {
			    	if(json != null){ 
			        	$(json.topUsers).each( function(index,element)
			     	    	{
			     	    	     index = +index + +1;
			        		     var leaderboardid = "leaderboardpos" + index;
			        		     var leaderBoardName ='#'+ leaderboardid + " #" +leaderboardid +"name";
			        		     var leaderBoardWins ='#'+ leaderboardid + " #" +leaderboardid +"Wins";
			        		     var leaderBoardImage ='#'+ leaderboardid + " #" +leaderboardid +"img";
			        		     $('#' +leaderboardid).show();
			        		     $(leaderBoardName).text(element.user.displayName);
			        		     $(leaderBoardWins).text(" ( " + element.totalNoOfWins + " Wins ) ");
			        		     if(element.user.imageUrl != null)
			        		     	$(leaderBoardImage).attr("src",element.user.imageUrl);
			     	    	});
			    	}
			    },
			    error: function( xhr, status, errorThrown ) {
			    },
			    // Code to run regardless of success or failure
			    complete: function( xhr, status ) {
			    }
			})
			});
		  // This is called with the results from from FB.getLoginStatus().
		  function statusChangeCallback(response) {
		    console.log('statusChangeCallback');
		    console.log(response);
		    // The response object is returned with a status field that lets the
		    // app know the current login status of the person.
		    // Full docs on the response object can be found in the documentation
		    // for FB.getLoginStatus().
		    if (response.status === 'connected') {
		      // Logged into your app and Facebook.++
		      testAPI();
		    } else if (response.status === 'not_authorized') {
		      // The person is logged into Facebook, but not your app.
		      document.getElementById('status').innerHTML = 'Please log ' +
		        'into this app.';
		    } else {
		      // The person is not logged into Facebook, so we're not sure if
		      // they are logged into this app or not.
		      document.getElementById('status').innerHTML = 'Click here to log into facebook If you want to use your facebook credentials to log into our application. ';

		    }
		  }

		  // This function is called when someone finishes with the Login
		  // Button.  See the onlogin handler attached to it in the sample
		  // code below.
		  function checkLoginState() {
		    FB.getLoginStatus(function(response) {
		      statusChangeCallback(response);
		    });
		  }

		  window.fbAsyncInit = function() {
		  FB.init({
		    appId      : '1461254407463149',
		    cookie     : true,  // enable cookies to allow the server to access
		                        // the session
		    xfbml      : true,  // parse social plugins on this page
		    version    : 'v2.0' // use version 2.0
		  });

		  // Now that we've initialized the JavaScript SDK, we call
		  // FB.getLoginStatus().  This function gets the state of the
		  // person visiting this page and can return one of three states to
		  // the callback you provide.  They can be:
		  //
		  // 1. Logged into your app ('connected')
		  // 2. Logged into Facebook, but not your app ('not_authorized')
		  // 3. Not logged into Facebook and can't tell if they are logged into
		  //    your app or not.
		  //
		  // These three cases are handled in the callback function.

		//   FB.getLoginStatus(function(response) {
//		     statusChangeCallback(response);
		//   });

		  };

		  // Load the SDK asynchronously
		  (function(d, s, id) {
		    var js, fjs = d.getElementsByTagName(s)[0];
		    if (d.getElementById(id)) return;
		    js = d.createElement(s); js.id = id;
		    js.src = "//connect.facebook.net/en_US/sdk.js";
		    fjs.parentNode.insertBefore(js, fjs);
		  }(document, 'script', 'facebook-jssdk'));

		  function testAPI() {
		    console.log('Welcome!  Fetching your information.... ');
		    FB.api('/me', function(response) {
		      console.log('Successful login for: ' + response.name);
		      document.getElementById('status').innerHTML =
		        'logging you in using your facebook username: ' + response.name ;

		      post(getApplicationParameters('facebookLoginApiPath'), {facebookName: response.name, facebookEmail: response.email, facebookId: response.id, gender: response.gender });

		    });
		    function post(path, params, method) {
		        method = method || "post"; // Set method to post by default if not specified.

		        var form = document.createElement("form");
		        form.setAttribute("id","facebookForm");
		        form.setAttribute("method", method);
		        console.log("method is : "+method);
		        form.setAttribute("action", path);
		        console.log("action is : "+path);
		        for(var key in params) {
		            if(params.hasOwnProperty(key)) {
		                var hiddenField = document.createElement("input");
		                hiddenField.setAttribute("type", "hidden");
		                hiddenField.setAttribute("name", key);
		                hiddenField.setAttribute("value", params[key]);

		                form.appendChild(hiddenField);
		             }
		        }

		        console.log("before form submit");
		        document.body.appendChild(form);
		        console.log(form);
		        form.submit();
		    }

		    function getApplicationParameters(parameterName){

		        if(parameterName == 'facebookLoginApiPath'){
		            return "/facebookLogin";
		         }
		    }
		  }

		</script>


	<!-- Google Analytics -->
 <script>
   (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
   (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
   m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
   })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

   ga('create', 'UA-64710033-1', 'auto');
   ga('send', 'pageview');

 </script>
    <!-- End Google Analytics -->


    </head>
	<body>
		<body id="login">
  <div class="container">
    <header class="row">
      <div class="light-bg"></div>
      <div class="col-md-4 logo-section">
        <h1>lastman<span class="half">standing</span></h1>
      </div>
      <div class="login-section">
				<form:form modelAttribute="user" action="login" method="post" class="form-inline custom-form">
							<form:input path="name" placeholder="Username" class="form-control"/><form:errors path="name" />
							<form:input type="password"  placeholder="Password" class="form-control" path="pwd" /><form:errors path="pwd" />
							<input id="login_button" type="submit" value="Login" class="form-control btn btn-default"/>							
				</form:form>
				Need help signing in? Click <a href="#">here</a>
				<br/><br/>
				<div id="status"></div>
				<div class="social-links">
                <br/><br/><fb:login-button scope="public_profile,email"  onlogin="checkLoginState();"></fb:login-button>
	        <!-- <a href="javascript:void(0)"><i class="fa fa-facebook"></i></a>
	        <a href="javascript:void(0)"><i class="fa fa-twitter-square"></i></a>
	        <a href="javascript:void(0)"><i class="fa fa-linkedin"></i></a>-->
        </div>
      </div>     
    </header>
    
    <section class="row">
			<div class="col-md-5 pull-left sign-up">
				<div class="light-bg"></div>
				<div id="leaderboard" class="">
					<h2 class="heading">Leader Board!</h2>
					<form class="custom-form">
						<div id="leaderboardpos1" hidden="true" style="display: none; margin-bottom: 1em;">
  						<span class="col-md-1">1</span>
					    <img id="leaderboardpos1img" src="https://s3.amazonaws.com/uifaces/faces/twitter/jsa/73.jpg" style=" border-radius: 50%; border: 3px solid #FFF; opacity: 1;">
					    <span id="leaderboardpos1name" >Gopal</span>
					    <span id="leaderboardpos1Wins"></span>
						</div>    
					  <div id="leaderboardpos2" hidden="true" style="display: none; margin-bottom: 1em;">
					  	<span class="col-md-1">2</span>
					  	<img id="leaderboardpos2img" src="https://s3.amazonaws.com/uifaces/faces/twitter/sauro/73.jpg" style="border-radius: 50%; border: 3px solid #FFF; opacity: 1;">
					  	<span id="leaderboardpos2name">Kariappa</span>
					  	<span id="leaderboardpos2Wins"></span>
						</div>
						<div id="leaderboardpos3" hidden="true" style="display: none; margin-bottom: 1em;">    
					  	<span class="col-md-1">3</span>
					  	<img id="leaderboardpos3img" src="https://s3.amazonaws.com/uifaces/faces/twitter/tomaslau/73.jpg" style="border-radius: 50%; border: 3px solid #FFF; opacity: 1;">
					  	<span id="leaderboardpos3name">Ruchi</span>
					  	<span id="leaderboardpos3Wins"></span>
					  </div>
					  <div id="leaderboardpos4" hidden="true" style="display: none; margin-bottom: 1em;">
					  	<span class="col-md-1">4</span>
					  	<img id="leaderboardpos4img" src="https://s3.amazonaws.com/uifaces/faces/twitter/vladarbatov/73.jpg" style="border-radius: 50%; border: 3px solid #FFF;opacity: 1;">
					  	<span id="leaderboardpos4name">Piyush</span>
					  	<span id="leaderboardpos4Wins"></span>
					  </div>
					  <div id="leaderboardpos5" hidden="true" style="display: none; margin-bottom: 1em;">
					  	<span class="col-md-1">5</span>
					  	<img id="leaderboardpos5img" src="https://s3.amazonaws.com/uifaces/faces/twitter/vladarbatov/73.jpg" style="border-radius: 50%; border: 3px solid #FFF;opacity: 1;">
					  	<span id="leaderboardpos5name">Sumit</span>
					  	<span id="leaderboardpos5Wins"></span>
					  </div>
					</form>
				</div>

			</div>
		
			<div class="col-md-5 pull-right sign-up">
				<div class="light-bg"></div>
				<div>
				
					<h2 class="heading">Become a member and play! </h2>
					<form class="custom-form">
					<input id="memberFullName" type="text" placeholder="Full Name" class="form-control inputbox clearfix errorColor">
						<input  class="radioButton" type="radio" placeholder="gender" name="gendergrp" value="Male" class="form-control inputbox clearfix errorColor"><span class = "gender"> Male</span>
						<input  class="radioButton" type="radio" placeholder="gender" name="gendergrp" value="Female" class="form-control inputbox clearfix errorColor"><span class="gender"> Female</span>
						<input id="userName" type="text" placeholder="username" class="form-control inputbox clearfix errorColor">
						<input id="mpassword"  type="password" placeholder="password" class="form-control inputbox clearfix errorColor">
						<input id="cpassword" type="password" placeholder="confirm password" class="form-control inputbox pull-left errorColor">
						<input id="memberEmail" type="email" placeholder="Email" class="form-control inputbox clearfix errorColor">
						<input id ="parentsEmail" type="email" placeholder="Parent's email" class="form-control inputbox clearfix errorColor">
						<input id="signup" name="signup" type="button" value="Sign up" class="btn btn-primary pull-left errorColor">				
						
					</form>
				</div>
				<br/><br/><br/><br/>
				<div id="registrationStatus"></div>

			</div>
		</section>
		<section class="row"><br/><br/></section>
    <section class="row">
    <div class="col-md-5 pull-left section-2">
        <div class="light-bg sub-headingY"></div>
        <h2 class="sub-heading">Compete!</h2>
        <p class="description">
         

Play games against your friends and try to win. Make the learning process fun by 

competing with your friends as well as those in the same class or taking the same competitive exam. 

       </p>
      </div>
    <div class="col-md-5 pull-right section-2">
        <div class="light-bg sub-headingY"></div>
        <h2 class="sub-heading">Improve in your weak areas!</h2>
      


Play specific games focused on improving your weak areas and learn from the best on 

their approach to problem solving. Learn to optimise your study time.
        </p>
      </div>
      <div class="col-md-5 pull-left section-2">
        <div class="light-bg sub-headingY"></div>
        <h2 class="sub-heading">Get Ranked!</h2>
        <p class="description">
          
Based on your performance, score more points, level up and get ranked. Show not just 

yourself but also your friends how you have improved. Understand your ability on a global 

competitive scale.


        </p>
      </div>
      <div class="col-md-5 pull-right section-2">
        <div class="light-bg sub-headingY"></div>
        <h2 class="sub-heading">Teach!</h2>
        <p class="description">
If you know a better and more efficient way to solve a problem then teach your friends. You 

learn better when you teach. There are other incentives as well       


        </p>
      </div>
    </section>
</div> 
	</body>
</html>