<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
 <link rel="stylesheet" href="<c:url value="/resources/blueprint/screen.css" />" type="text/css" media="screen, projection">
        <link rel="stylesheet" href="<c:url value="/resources/blueprint/print.css" />" type="text/css" media="print">
        <link rel="stylesheet" href="<c:url value="/resources/css/login_page.css"/>">
        <link rel="stylesheet" href="<c:url value="/resources/css/roomPage.css"/>">
        <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap-theme.min.css"/>">
            <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css"/>">
        <!--[if lt IE 8]>
            <link rel="stylesheet" href="<c:url value="/resources/blueprint/ie.css" />" type="text/css" media="screen, projection">
        <![endif]-->
        <link rel="stylesheet" href="<c:url value="/resources/css/popup.css" />" type="text/css" media="screen, projection">
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/json.min.js" /> "></script>
         <script type="text/javascript" src="<c:url value="/resources/js/jquery-1.11.3.min.js" />" > </script>
	<script type="text/javascript" src="<c:url value="/resources/js/bootstrap.min.js" />" > </script>
	

<script type="text/javascript">
var userId;
$(document).ready(function() {
    $("#menu-toggle").click(function(e) {
          e.preventDefault();
          $("#wrapper").toggleClass("toggled");
          });
    var navbarHeight = $('.navbar').height();
    $("#wrapper").css("margin", navbarHeight);

     userId = "${userId}";           
     $("#displayUserName").html(userId); 
    

});
</script>
<html>
    <head>
        <title>generic Error Page</title>
    </head>
    <body id="roomPage">
        <!-- navbar -->
<nav class="navbar navbar-default navbar-fixed-top top-panel">
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
         
      
            <li id="logout_pane">
				<form id="logoutform"  action="${pageContext.request.contextPath}/logout">
				<a href="#" onClick="$('#logoutform').submit()">Logout</a>
				</form>
			</li>
	</ul>
  </div><!-- /.navbar-collapse -->    
</div><!-- /.container-fluid -->
</nav>



   
    <div id="error">There has been an internal error while trying to access this page. We apologise for the inconvenience caused. Please check back later. 
    The exception is: ${exception}</div> 

       
		    
        	
                        
     

</html>