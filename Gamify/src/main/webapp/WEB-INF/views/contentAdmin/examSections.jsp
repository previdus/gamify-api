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
			

			
			function enableEditExamSectionText(examSectionId){
				
				$("#editExamSection"+examSectionId).show();
				
		    }
			function disableEditExamSectionText(examSectionId){
				$("#editExamSection"+examSectionId).hide();
		    }
		    function updateExamSection(examSectionId){
			    var newName = $("#editableExamSectionName"+examSectionId).val();
			    if(newName == null || newName ==""){
				   alert("exam section name cannot be empty"); 
				   return;   
				} 
				newName = $.trim(newName);
				var oldName = $("#examSectionNameDisplay"+examSectionId).text();  
				if(oldName == newName){
				   alert("exam section name cannot be the same"); 
				   return;  
				}
				$("#editExamSectionForm"+examSectionId).submit();			 
				
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
				You can add, edit, enable or disable an exam section for ${examName} here
			</h1>
			
			<span style="text-decoration:underline">Exam Sections</span>
					<br/>
		     <form:form action="${pageContext.request.contextPath}/content/examSections/addExamSection" method="post">
		           <input type="hidden" name="examId" value="${examId}"/>
	               <input id="addExamSection" type="text" name="addexamSection" placeholder="enter an exam section name"/>&nbsp;
	               <input type="submit" value="add exam section" /><br/>
	               <span id="error" style="color:red">${error}</span><span id="success" style="color:green">${success}</span>	               
	        </form:form>
	        
			<div class="span-12 last" style="width: 800px; height: 500px; overflow-y: scroll; border:solid">					  	
	               <br/>
					<c:forEach var="examSection" items="${examSections}" step="1">	
	           	         <span id="examNameDisplay${examSection.id}">${examSection.name}</span>
	           	         <br/>
	           	         <c:set var="status_disable"  value="${examSection.state == 'ACTIVE' ?'':'disabled'}"/>
	           	         <c:set var="status_enable"  value="${examSection.state == 'INACTIVE' ?'':'disabled'}"/>	
	           	         <form:form id="disableExamSectionState${examSection.id}" action="${pageContext.request.contextPath}/content/examSections/disableExamSection" method="post">
	           	             <input type="hidden" name="examId" value="${examId }"></input>	            	         
	           	             <input type="hidden" name="examSectionId" value="${examSection.id}"></input>
	           	             <input type="submit" value="disable" ${status_disable}></input>&nbsp;
	           	         </form:form>
	           	         <br/>
	           	         <form:form id="enableExamSectionState${examSection.id}" action="${pageContext.request.contextPath}/content/examSections/enableExamSection" method="post">
	           	             <input type="hidden" name="examId" value="${examId}"></input>	 
	           	             <input type="hidden" name="examSectionId" value="${examSection.id}"></input>
	           	             <input type="submit" value="enable" ${status_enable}></input>&nbsp;
	           	         </form:form>
	           	         
	           	         <br/>
	           	         <form:form id="editExamSectionForm${examSection.id}" action="${pageContext.request.contextPath}/content/examSections/editExamSection" method="post">	           	             
	           	             <input type="hidden" name="examId" value="${examId}"></input>
	           	             <input type="hidden" name="examSectionId" value="${examSection.id}"></input>
		           	         <input type="button" onclick="enableEditExamSectionText(${examSection.id})" value="edit"></input>&nbsp;	           	         
		           	         
		           	         <div id="editExamSection${examSection.id}" style="display:none">
		           	         <input id="editableExamSectionName${examSection.id}" name="editexamSection" type="text" class="editableExamSectionName" placeholder="${examSection.name}"></input>
		           	         <input type="button" value="save" onclick="updateExamSection(${examSection.id})"></input>
	           	             <input type="button" value="cancel" onclick="disableEditExamSectionText(${examSection.id})"></input>
	           	             </div>
	           	         </form:form>
	           	         <br/>
	           	         <form:form action="${pageContext.request.contextPath}/content/topics" method="get">
				               <input type="hidden" name="examSectionId" value="${examSection.id}"/>
				               <input type="submit" value="go to topics for this examSection" /><br/>				              	               
				        </form:form>
				       
				         <br/>  
				         <hr/>           
	               </c:forEach>
	               <br/>
			</div>
			
			<hr>	
			</div>
	
</html>
