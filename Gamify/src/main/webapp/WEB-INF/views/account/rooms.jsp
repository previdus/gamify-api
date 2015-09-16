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
     


});</script>
<html>
    <head>
        <title>Choose A Exam Category</title>

        <script type="text/javascript">
            function enableCurrentDropDown(examId){                
                $(".examSectionDropDown").hide();
                $('.examSectionDropDown').attr('name','');
                $('#examSection'+examId).attr('name','examSection');
                $("#examSection"+examId).show();
                $("#submitForm").show();
            }
        </script>
    </head>
    <body id="roomPage">
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
          <li class="choose-category"><b>Choose Exam Category</b></li>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
          <span id="displayUserName"></span> 
          <span class="caret"></span>
          </a>
          <ul class="dropdown-menu">
            <li class="clearTimeInterval"><a href="#"><i class="glyphicon glyphicon-user">&nbsp;Profile</i></a></li>
            <li class="clearTimeInterval"><a href="#"><i class="glyphicon glyphicon-cog">&nbsp;Settings</i></a></li>
            <li role="separator" class="divider"></li>
            <li class="clearTimeInterval"><a  href="#" onClick="$('#logoutform').submit()"><i class="glyphicon glyphicon-log-out">&nbsp;Logout</i></a></li>
          </ul>
        </li>
      </ul>
  </div><!-- /.navbar-collapse -->    
</div><!-- /.container-fluid -->
</nav>
<form id="logoutform"  action="${pageContext.request.contextPath}/logout"></form>


    <div id="welcomeUser">Welcome ${user.displayName}&nbsp;&nbsp;&nbsp;<img src="${user.imageUrl}"></img></div>
    <div id="exams"></div> 
<div id="logout_pane">
<form id="logoutform"  action="${pageContext.request.contextPath}/logout">
<a href="#" onClick="$('#logoutform').submit()">Logout</a>
</form>
</div>
        <div class="container sub-container">
        	<br><br>
            <form:form modelAttribute="room" action="play" method="post">

            <div class="span-12 last room-category">    
                <c:out value="${room.roomName}"></c:out><br/>


                    <c:forEach var="exam" items="${room.exams}" step="1"> 
                           <div class="each-room">            
                         <input type="radio" class="exam-category" id="exam-id${exam.id}" name="exam-id" value="${exam.id}" onclick="enableCurrentDropDown(${exam.id})">
                          <label>  <c:out value="${exam.examName}"></c:out></label>
                         <select id="examSection${exam.id}" class="examSectionDropDown" style="display:none">
                         <c:forEach var="examSection" items="${exam.examSections}" step="1">
                             <option value="${examSection.id}">${examSection.name}</option>
                         </c:forEach>
                         </select>
                         <br/>    <br>  
                         </div>             
                   </c:forEach>

             </div>
  
                    <br>
                    <br/>

            <input id="submitForm" type="submit" value="Submit" style="display:none">        

          
            </form:form>
            
            </div>

</html>
