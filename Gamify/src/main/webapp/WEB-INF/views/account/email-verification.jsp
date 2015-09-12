<!DOCTYPE html>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
	<head>
		 <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Email Verification Page</title>
		 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
  <link rel="stylesheet" type="text/css" href="resources/css/common.css">
  <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
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
				</form:form> Need help signing in? Click <a href="#">here</a>
				<br/><br/>
        </div>    
    </header>
    <section class="row">
			<div class="col-md-5 pull-left sign-up">
				<div class="light-bg"></div>
				<div id="leaderboard" class="">
				<form:label path="verificationMessage">${verificationMessage}</form:label>
				</div>	
				</div>
			<div class="col-md-5 pull-right sign-up">
				<div class="light-bg"></div>
				

			</div>
		</section>
		
  
</div> 
	</body>
</html>