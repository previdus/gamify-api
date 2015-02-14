<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
	<head>
		<title>Choose A Exam Category</title>
		<link rel="stylesheet" href="<c:url value="/resources/blueprint/screen.css" />" type="text/css" media="screen, projection">
		<link rel="stylesheet" href="<c:url value="/resources/blueprint/print.css" />" type="text/css" media="print">
		<!--[if lt IE 8]>
			<link rel="stylesheet" href="<c:url value="/resources/blueprint/ie.css" />" type="text/css" media="screen, projection">
		<![endif]-->
		<link rel="stylesheet" href="<c:url value="/resources/css/popup.css" />" type="text/css" media="screen, projection">
		<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
		<script type="text/javascript" src="<c:url value="/resources/js/json.min.js" /> "></script>
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
	<body>
	<div id="welcomeUser">Welcome ${user.displayName}&nbsp;&nbsp;&nbsp;<img src="${user.imageUrl}"></img></div>
	<div id="exams"></div> 
<div id="logout_pane">
<form id="logoutform"  action="${pageContext.request.contextPath}/logout">
<a href="#" onClick="$('#logoutform').submit()">Logout</a>
</form>
</div>
		<div class="container">
			<h1>
				Available Categories
			</h1>
			<form:form modelAttribute="room" action="play" method="post">
			
			<div class="span-12 last">	
				<c:out value="${room.roomName}"></c:out><br/>
				  	
					
					<c:forEach var="exam" items="${room.exams}" step="1">					
				         <input type="radio" id="exam-id${exam.id}" name="exam-id" value="${exam.id}" onclick="enableCurrentDropDown(${exam.id})">
	           	         <c:out value="${exam.examName}"></c:out>
				         <select id="examSection${exam.id}" class="examSectionDropDown" style="display:none">
				         <c:forEach var="examSection" items="${exam.examSections}" step="1">
				         	<option value="${examSection.id}">${examSection.name}</option>
				         </c:forEach>
				         </select>
				         <br/>	               
	               </c:forEach>
	               
	               
					
					
			<input id="submitForm" type="submit" value="Submit" style="display:none">		
				
			</div>
			</form:form>
			<hr>	
			</div>
	
</html>
