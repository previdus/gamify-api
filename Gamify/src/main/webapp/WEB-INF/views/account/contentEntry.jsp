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
			

			function enableCorrespondingExamSectionRadio(examId){
				$(".examSectionRadioGroup").hide();
				$(".topicRadioGroup").hide();
				$("#examSection"+examId).show();
			}
			function enableCorrespondingTopicRadio(examSectionId){
				$(".topicRadioGroup").hide();
				$("#topic"+examSectionId).show();
			}
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
	<div id="exams"></div> 
<div id="logout_pane">
<form id="logoutform"  action="${pageContext.request.contextPath}/logout">
<a href="#" onClick="$('#logoutform').submit()">Logout</a>
</form>
</div>
		<div class="container">
			<h1>
				Exam->Exam Sections->Topics->Questions->Options
			</h1>
			
			<span style="text-decoration:underline">Exams</span>
					<br/>
				   <form:form action="${pageContext.request.contextPath}/content/addExam" method="post">
	               <input id="addExam" type="text" name="addexam" placeholder="enter an exam name"/>&nbsp;
	               <input type="submit" value="add exam" /><br/>
	               <span id="error" style="color:red">${error}</span><span id="success" style="color:green">${success}</span>	               
	        </form:form>
			<div class="span-12 last" style="width: 800px; height: 400px; overflow-y: scroll;">	
				
				    
				  	
	               <br/>
					<c:forEach var="exam" items="${room.exams}" step="1">					
				         <input type="radio" id="exam-id${exam.id}" name="exam-id" value="${exam.id}" onclick="enableCorrespondingExamSectionRadio(${exam.id})">
	           	         <span id="examNameDisplay${exam.id}">${exam.examName}</span>
	           	         <br/>
	           	         <c:set var="status_disable"  value="${exam.state == 'ACTIVE' ?'':'disabled'}"/>
	           	         <c:set var="status_enable"  value="${exam.state == 'INACTIVE' ?'':'disabled'}"/>	
	           	         <form:form id="disableExamState${exam.id}" action="${pageContext.request.contextPath}/content/disableExam" method="post">
	           	             <input type="hidden" name="examId" value="${exam.id}"></input>	            	         
	           	             <input type="submit" value="disable" ${status_disable}></input>&nbsp;
	           	         </form:form>
	           	         <br/>
	           	         <form:form id="enableExamState${exam.id}" action="${pageContext.request.contextPath}/content/enableExam" method="post">
	           	             <input type="hidden" name="examId" value="${exam.id}"></input>
	           	             <input type="submit" value="enable" ${status_enable}></input>&nbsp;
	           	         </form:form>
	           	         
	           	         <br/>
	           	         <form:form id="editExamForm${exam.id}" action="${pageContext.request.contextPath}/content/editExam" method="post">	           	             
	           	             <input type="hidden" name="examId" value="${exam.id}"></input>
		           	         <input type="button" onclick="enableEditExamText(${exam.id})" value="edit"></input>&nbsp;	           	         
		           	         
		           	         <div id="editExam${exam.id}" style="display:none">
		           	         <input id="editableExamName${exam.id}" name="editexam" type="text" class="editableExamName" placeholder="${exam.examName}"></input>
		           	         <input type="button" value="save" onclick="updateExam(${exam.id})"></input>
	           	             <input type="button" value="cancel" onclick="disableEditExamText(${exam.id})"></input>
	           	             </div>
	           	         </form:form>
	           	         
				         <div id="examSection${exam.id}" class="examSectionRadioGroup" style="display:none; float:right">
				         <br/><span style="text-decoration:underline">${exam.examName} exam sections</span><br/>
				         <c:forEach var="examSection" items="${exam.examSections}" step="1">
				         	
				         	<input type="radio" name="examSection" value="${examSection.id}" 
				         	onclick="enableCorrespondingTopicRadio(${examSection.id})">
				         	  <a href="#">Edit</a>&nbsp;<a href="#">disable</a>&nbsp;<span></span>${examSection.name}</span>
				         	         <div id="topic${examSection.id}" class="topicRadioGroup" style="display:none; float:right">
				         	         <br/> <span style="text-decoration:underline">${exam.examName} ${examSection.name} topics</span><br/>
							         <c:forEach var="topic" items="${examSection.topics}" step="1">
							         	
							         	<input type="radio" name="topic" value="${topic.id}" 
							         	onclick="enableCorrespondingQuestions(${topic.id})">
							         	  <a href="#">Edit</a>&nbsp;<a href="#">disable</a>&nbsp;<span>${topic.name}</span>
							         	<br/>
							         </c:forEach>
							         <br/>
						               <a href="#">Add Topic</a>
						              <br/>
							         </div>
							         	<br/>
				         </c:forEach>
				          <br/>
			               <a href="#">Add Exam Section</a>
			              <br/>
				         </div>
				         <br/>             
	               </c:forEach>
	               <br/>
	              
	               
	               
					
					
					
				
			</div>
			
			<hr>	
			</div>
	
</html>
