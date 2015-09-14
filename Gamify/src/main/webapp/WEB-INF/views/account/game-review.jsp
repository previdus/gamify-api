<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ page isELIgnored="false" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>LastManStanding</title>
</head>
<link rel="stylesheet" href="<c:url value="/resources/blueprint/screen.css" />" type="text/css" media="screen, projection">
		<link rel="stylesheet" href="<c:url value="/resources/blueprint/print.css" />" type="text/css" media="print">
		<!--[if lt IE 8]>
			<link rel="stylesheet" href="<c:url value="/resources/blueprint/ie.css" />" type="text/css" media="screen, projection">
		<![endif]-->
		<link rel="stylesheet" href="<c:url value="/resources/css/popup.css" />" type="text/css" media="screen, projection">
		<!-- vendor styles -->
		 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
 <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css"/>">
	<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap-theme.min.css"/>">
	<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap-theme.min.css"/>">
	<link rel="stylesheet" href="<c:url value="/resources/css/sidebar.css"/>">
	<link rel="stylesheet" href="<c:url value="/resources/css/login_page.css"/>">
		<link rel="stylesheet" href="<c:url value="/resources/css/page_four.css"/>">
	
	<!-- custom styles -->
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/page_four.css"/>">
	
	<!-- fonts -->
	<link href='http://fonts.googleapis.com/css?family=Lato:300,400' rel='stylesheet' type='text/css'>
	
		
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/lmsgame.css" />" media="screen" />
<!-- vendor scripts -->
	<!-- Latest compiled and minified JavaScript -->
	 <script type="text/javascript" src="<c:url value="/resources/js/jquery-1.11.3.min.js" />" > </script>
	<script type="text/javascript" src="<c:url value="/resources/js/bootstrap.min.js" />" > </script>
	
	
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js">
</script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.cookie.js" /> "></script>
<script id="mathjax" type="text/javascript" src="http://www.hostmath.com/Math/MathJax.js?config=OK"></script>

<body>
<!-- navbar -->
<nav class="navbar navbar-default navbar-fixed-top">
<div class="container-fluid lms">
    <div class="navbar-header">
	      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
	        <span class="sr-only"></span>
	        <span class="icon-bar"></span>
	      </button>
	      <div class="logo-section">
                  <h1>lastman<span class="half">standing</span></h1>
                </div>
 </div>
 <!-- Collect the nav links, forms, and other content for toggling -->
 <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
     <ul class="nav navbar-nav navbar-right">
        
      </ul>
  </div><!-- /.navbar-collapse -->    
</div><!-- /.container-fluid -->
</nav>

<form id="backToMainRoom"  action="${pageContext.request.contextPath}/rooms/changeroom"></form>
<form id="logoutform"  action="${pageContext.request.contextPath}/logout"></form>


<div id="wrapper">
    <div id="sidebar-wrapper">
    </div>
</div>
<div id="page-content-wrapper">
   <div class="container-fluid">
		<!-- timer -->
		<div class="row">
			 <div class="col-lg-12">
			 <div class="col-md-offset-2 col-md-8">
				<div class="timer">
				<form:label path="gi.id">${gi.id}</form:label>
					
				</div>
			</div>
		</div>
		<br/>
		<br/>
		 
		 <div id="questionSection"></div> 
	</div>	 
	</div>
</div> 
 
</body>
</html>