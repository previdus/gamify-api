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
</div>
    </header>

   <section class="row">
      <div class="col-md-5 pull-right sign-up">
        <div class="light-bg"></div>
        <div>
          <h2 class="heading">Be a member and start playing!</h2>
          <form class="custom-form">
            <input type="text" placeholder="New member name" class="form-control inputbox  clearfix">
            <input type="email" placeholder="email" class="form-control inputbox clearfix">
            <input type="password" placeholder="password" class="form-control inputbox clearfix">
            <input type="password" placeholder="confirm password" class="form-control inputbox pull-left">
            <input type="button" value="Sign up" class="btn btn-primary pull-left">
          </form>
        </div>

      </div>
    </section>
    <section class="row">
      <div class="col-md-5 pull-right section-2">
        <div class="light-bg"></div>
        <h2 class="sub-heading">Sub-heading!</h2>
        <p class="description">
          "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
        </p>
        <div class="social-links">
        <fb:login-button scope="public_profile,email"  onlogin="checkLoginState();"></fb:login-button>
         <!-- <a href="javascript:void(0)"><i class="fa fa-facebook"></i></a>
          <a href="javascript:void(0)"><i class="fa fa-twitter-square"></i></a>
          <a href="javascript:void(0)"><i class="fa fa-linkedin"></i></a>-->
        </div>

      </div>
    </section>
</div> 
	</body>
</html>