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
			

			
			function enableEditTopicText(topicId){
				
				$("#editTopic"+topicId).show();
				
		    }
			function disableEditTopicText(topicId){
				$("#editTopic"+topicId).hide();
		    }
		    function updateTopic(topicId){
			    var newName = $("#editableTopicName"+topicId).val();
			    if(newName == null || newName ==""){
				   alert("topic name cannot be empty"); 
				   return;   
				} 
				newName = $.trim(newName);
				var oldName = $("#topicNameDisplay"+topicId).text();  
				if(oldName == newName){
				   alert("topic name cannot be the same"); 
				   return;  
				}
				$("#editTopicForm"+topicId).submit();			 
				
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
				You can add, edit, enable or disable a topic for the exam section  ${examSectionName} for the exam ${examName} here
			</h1>
			
			<span style="text-decoration:underline">Topics</span>
					<br/>
		     <form:form action="${pageContext.request.contextPath}/content/topics/addTopic" method="post">
		           <input type="hidden" name="examSectionId" value="${examSectionId}"/>
	               <input id="addTopic" type="text" name="addTopic" placeholder="enter a topic name"/>&nbsp;
	               <input type="submit" value="add topic" /><br/>
	               <span id="error" style="color:red">${error}</span><span id="success" style="color:green">${success}</span>	               
	        </form:form>
	        
			<div class="span-12 last" style="width: 800px; height: 500px; overflow-y: scroll; border:solid">					  	
	               <br/>
					<c:forEach var="topic" items="${topics}" step="1">	
	           	         <span id="topicNameDisplay${topic.id}">${topic.name}</span>
	           	         <br/>
	           	         <c:set var="status_disable"  value="${topic.state == 'ACTIVE' ?'':'disabled'}"/>
	           	         <c:set var="status_enable"  value="${topic.state == 'INACTIVE' ?'':'disabled'}"/>	
	           	         <form:form id="disableTopicState${topic.id}" action="${pageContext.request.contextPath}/content/topics/disableTopic" method="post">
	           	             <input type="hidden" name="examSectionId" value="${topic.examSection.id}"></input>	            	         
	           	             <input type="hidden" name="topicId" value="${topic.id}"></input>
	           	             <input type="submit" value="disable" ${status_disable}></input>&nbsp;
	           	         </form:form>
	           	         <br/>
	           	         <form:form id="enableTopicState${topic.id}" action="${pageContext.request.contextPath}/content/topics/enableTopic" method="post">
	           	             <input type="hidden" name="examSectionId" value="${topic.examSection.id}"></input>	 
	           	             <input type="hidden" name="topicId" value="${topic.id}"></input>
	           	             <input type="submit" value="enable" ${status_enable}></input>&nbsp;
	           	         </form:form>
	           	         
	           	         <br/>
	           	         <form:form id="editTopicForm${topic.id}" action="${pageContext.request.contextPath}/content/topics/editTopic" method="post">	           	             
	           	             <input type="hidden" name="examSectionId" value="${topic.examSection.id}"></input>
	           	             <input type="hidden" name="topicId" value="${topic.id}"></input>
		           	         <input type="button" onclick="enableEditTopicText(${topic.id})" value="edit"></input>&nbsp;	           	         
		           	         
		           	         <div id="editTopic${topic.id}" style="display:none">
		           	         <input id="editableTopicName${topic.id}" name="editTopic" type="text" class="editableTopicName" placeholder="${topic.name}"></input>
		           	         <input type="button" value="save" onclick="updateTopic(${topic.id})"></input>
	           	             <input type="button" value="cancel" onclick="disableEditTopicText(${topic.id})"></input>
	           	             </div>
	           	         </form:form>
	           	         <br/>
	           	         <form:form action="${pageContext.request.contextPath}/content/questions" method="get">
				               <input type="hidden" name="topicId"/>
				               <input type="submit" value="go to the questions for this topic" /><br/>				              	               
				        </form:form>
				       
				         <br/>  
				         <hr/>           
	               </c:forEach>
	               <br/>
			</div>
			
			<hr>	
			</div>
	
</html>
