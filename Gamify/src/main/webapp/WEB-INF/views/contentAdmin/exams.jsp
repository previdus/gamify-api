<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
	<head>
		<title>Content add/edit/disable</title>
		<link rel="stylesheet" href="<c:url value="/resources/blueprint/screen.css" />" type="text/css" media="screen, projection">
		<link rel="stylesheet" href="<c:url value="/resources/blueprint/print.css" />" type="text/css" media="print">
		<!--[if lt IE 8]>
			<link rel="stylesheet" href="<c:url value="/resources/blueprint/ie.css" />" type="text/css" media="screen, projection">
		<![endif]-->
		<link rel="stylesheet" href="<c:url value="/resources/css/popup.css" />" type="text/css" media="screen, projection">
		<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
		<script type="text/javascript" src="<c:url value="/resources/js/json.min.js" /> "></script>
		<script type="text/javascript">
			

			
			function enableEditExamText(examId){
				
				$("#editExam"+examId).show();
				
		    }
			function disableEditExamText(examId){
				$("#editExam"+examId).hide();
		    }
		    function updateExam(examId){
			    var newName = $("#editableExamName"+examId).val();
			    if(newName == null || newName ==""){
				   alert("exam name cannot be empty"); 
				   return;   
				} 
				newName = $.trim(newName);
				var oldName = $("#examNameDisplay"+examId).text();  
				if(oldName == newName){
				   alert("exam name cannot be the same"); 
				   return;  
				}
				$("#editExamForm"+examId).submit();			 
				
			}
		</script>
	</head>
	<body>
	<div id="welcomeUser">Welcome ${user.displayName}&nbsp;&nbsp;&nbsp;<img src="${user.imageUrl}"></img></div>
	 
<div id="logout_pane">
<form id="logoutform"  action="${pageContext.request.contextPath}/logout">
<a href="#" onClick="$('#logoutform').submit()">Logout</a>
</form>
</div>
		<div class="container">
			<h1>
				You can add, edit, enable or disable an exam here
			</h1>
			
			<span style="text-decoration:underline">Exams</span>
					<br/>
		     <form:form action="${pageContext.request.contextPath}/content/exams/addExam" method="post">
	               <input id="addExam" type="text" name="addexam" placeholder="enter an exam name"/>&nbsp;
	               <input type="submit" value="add exam" /><br/>
	               <span id="error" style="color:red">${error}</span><span id="success" style="color:green">${success}</span>	               
	        </form:form>
	        
			<div class="span-12 last" style="width: 800px; height: 500px; overflow-y: scroll; border:solid">					  	
	               <br/>
					<c:forEach var="exam" items="${room.exams}" step="1">	
	           	         <span id="examNameDisplay${exam.id}">${exam.examName}</span>
	           	         <br/>
	           	         <c:set var="status_disable"  value="${exam.state == 'ACTIVE' ?'':'disabled'}"/>
	           	         <c:set var="status_enable"  value="${exam.state == 'INACTIVE' ?'':'disabled'}"/>	
	           	         <form:form id="disableExamState${exam.id}" action="${pageContext.request.contextPath}/content/exams/disableExam" method="post">
	           	             <input type="hidden" name="examId" value="${exam.id}"></input>	            	         
	           	             <input type="submit" value="disable" ${status_disable}></input>&nbsp;
	           	         </form:form>
	           	         <br/>
	           	         <form:form id="enableExamState${exam.id}" action="${pageContext.request.contextPath}/content/exams/enableExam" method="post">
	           	             <input type="hidden" name="examId" value="${exam.id}"></input>
	           	             <input type="submit" value="enable" ${status_enable}></input>&nbsp;
	           	         </form:form>
	           	         
	           	         <br/>
	           	         <form:form id="editExamForm${exam.id}" action="${pageContext.request.contextPath}/content/exams/editExam" method="post">	           	             
	           	             <input type="hidden" name="examId" value="${exam.id}"></input>
		           	         <input type="button" onclick="enableEditExamText(${exam.id})" value="edit"></input>&nbsp;	           	         
		           	         
		           	         <div id="editExam${exam.id}" style="display:none">
		           	         <input id="editableExamName${exam.id}" name="editexam" type="text" class="editableExamName" placeholder="${exam.examName}"></input>
		           	         <input type="button" value="save" onclick="updateExam(${exam.id})"></input>
	           	             <input type="button" value="cancel" onclick="disableEditExamText(${exam.id})"></input>
	           	             </div>
	           	         </form:form>
	           	         <br/>
	           	         <form:form action="${pageContext.request.contextPath}/content/examSections" method="get">
				               <input type="hidden" name="examId" value="${exam.id}"/>
				               <input type="submit" value="go to exam sections" /><br/>				              	               
				        </form:form>
				       
				         <br/>  
				         <hr/>           
	               </c:forEach>
	               <br/>
			</div>
			
			<hr>	
			</div>
	
</html>
