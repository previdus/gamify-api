<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
	<head>
		<title>Login Page</title>
		<link rel="stylesheet" href="<c:url value="/resources/blueprint/screen.css" />" type="text/css" media="screen, projection">
		<link rel="stylesheet" href="<c:url value="/resources/blueprint/print.css" />" type="text/css" media="print">
		<!--[if lt IE 8]>
			<link rel="stylesheet" href="<c:url value="/resources/blueprint/ie.css" />" type="text/css" media="screen, projection">
		<![endif]-->
		<link rel="stylesheet" href="<c:url value="/resources/css/popup.css" />" type="text/css" media="screen, projection">
		<script type="text/javascript" src="<c:url value="/resources/js/jquery-1.4.min.js" /> "></script>
		<script type="text/javascript" src="<c:url value="/resources/js/json.min.js" /> "></script>
		<script type="text/javascript" src="<c:url value="/resources/js/facebook.js" /> "></script>
		<script>
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

		  // Here we run a very simple test of the Graph API after login is
		  // successful.  See statusChangeCallback() for when this call is made.
		  function testAPI() {
		    console.log('Welcome!  Fetching your information.... ');
		    FB.api('/me', function(response) {
		      console.log('Successful login for: ' + response.name);
		      document.getElementById('status').innerHTML =
		        'Or you can login using your facebook, ' + response.name ;


		   
		      post(getApplicationParameters('facebookLoginApiPath'), {facebookName: response.name, facebookEmail: response.email, facebookId: response.id, gender: response.gender });
		      
		    });

		      

		    function post(path, params, method) {
		        method = method || "post"; // Set method to post by default if not specified.

		        // The rest of this code assumes you are not using a library.
		        // It can be made less wordy if you use one.
		        var form = document.createElement("form");
		        form.setAttribute("id","facebookForm");
		        form.setAttribute("method", method);
		        form.setAttribute("action", path);       

		        for(var key in params) {
		            if(params.hasOwnProperty(key)) {
		                
		                
		                var hiddenField = document.createElement("input");
		                hiddenField.setAttribute("type", "hidden");
		                hiddenField.setAttribute("name", key);
		                hiddenField.setAttribute("value", params[key]);

		                form.appendChild(hiddenField);
		             }
		        }

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
	</head>
	<body>
		<div class="container">
			<h1>
				Login
			</h1>
			<div class="span-12 last">	
				<form:form modelAttribute="user" action="login" method="post">
				  	<fieldset>		
						<legend>Please enter your credentials</legend>
						<p>
							<form:label id="nameLabel" for="name" path="name" cssErrorClass="error">Name</form:label><br/>
							<form:input path="name" /><form:errors path="name" />
						</p>
						<p>	
							<form:label for="password" path="pwd" cssErrorClass="error">Password</form:label><br/>
							<form:input type="password" path="pwd" /><form:errors path="pwd" />
						</p>
											
						<p>	
							<input id="login" type="submit" value="Login" />
						</p>
					</fieldset>
				</form:form>
			</div>
			<fb:login-button scope="public_profile,email"  onlogin="checkLoginState();">
			</fb:login-button>
			
			<div id="status">
			</div>
			<hr>	
				
		</div>
		
	</body>


	
</html>
