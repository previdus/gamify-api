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
		<script type="text/javascript" src="http://www.hostmath.com/Math/MathJax.js?config=OK"></script>
		<script type="text/javascript">
			
		

			
			function enableEditOptionText(optionId){
				
				$("#editOption"+optionId).show();
				
		    }
			function disableEditOptionText(optionId){
				$("#editOption"+optionId).hide();
		    }
		    function updateOption(optionId){
			    var newName = $("#editableOptionText"+optionId).val();
			    if(newName == null || newName ==""){
				   alert("option text cannot be empty"); 
				   return;   
				} 
				newName = $.trim(newName);
				var oldName = $("#optionTextDisplay"+optionId).text();  
				if(oldName == newName){
				   alert("option text cannot be the same"); 
				   return;  
				}
				$("#editOptionForm"+optionId).submit();			 
				
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
	    <div id="iframe_for_latex_editor">
	        <iframe src="http://hostmath.com"></iframe>
	    </div>
		<div id="maincontent" class="container">
			<h1>
				You can add, edit, enable or disable an option of a question <br/><i>${questionText}</i><br/> for a topic <i>${topicName}</i> of the exam section <i> ${examSectionName}</i> for the exam <i>${examName} </i>here
			</h1>
			
			<span style="text-decoration:underline">Options</span>
					<br/>
		     <form:form action="${pageContext.request.contextPath}/content/options/addOption" method="post">
		           <input type="hidden" name="questionId" value="${questionId}"/>
	               <input id="addOption" type="text" name="addOption" placeholder="enter an option"/>&nbsp; Order: 
	               <select name="order" >
		               <option selected>1</option>
		               <option>2</option>
		               <option>3</option>
		               <option>4</option>
		               <option>5</option>		               
	               </select>
	               <input type="submit" value="add option" /><br/>
	               <span id="error" style="color:red">${error}</span><span id="success" style="color:green">${success}</span>	               
	        </form:form>
	        
			<div class="span-12 last" style="width: 800px; height: 500px; overflow-y: scroll; border:solid">					  	
	               <br/>
	               <c:if test="${not empty options}">
					<c:forEach var="option" items="${options}" step="1">
					    <c:if test="${option.state == 'ACTIVE'}">	
					    <c:choose>
	           	         <c:when test="${not empty answerKey and answerKey.optionId == option.id}">
	           	             <b>(This is the answer key)</b>	           	             
	           	         </c:when>
	           	         <c:otherwise>
	           	         <form:form id="makeOptionAnswerKey${option.id}" action="${pageContext.request.contextPath}/content/options/chooseAnswerKey" method="get">
	           	             <input type="hidden" name="questionId" value="${questionId}"></input>	            	         
	           	             <input type="hidden" name="optionId" value="${option.id}"></input>	           	            
	           	             <input type="submit" value="choose this option as answer key"></input>&nbsp;
	           	         </form:form>
	           	         </c:otherwise>
	           	         </c:choose>  
	           	         </c:if>
	           	         <br/>
	           	         <span id="optionDisplay${option.id}">${option.text}</span>	           	                   	         
	           	         <br/>
	           	                   	         
	           	         <span id="optionOrderDisplay${option.id}"><b>order:</b>${option.ordr}</span>
	           	         <br/>
	           	         
	           	         
	           	         <c:set var="status_disable"  value="${option.state == 'ACTIVE' ?'':'disabled'}"/>
	           	         <c:set var="status_enable"  value="${option.state == 'INACTIVE' ?'':'disabled'}"/>	
	           	         <form:form id="disableOptionState${option.id}" action="${pageContext.request.contextPath}/content/options/disableOption" method="get">
	           	             <input type="hidden" name="questionId" value="${questionId}"></input>	            	         
	           	             <input type="hidden" name="optionId" value="${option.id}"></input>
	           	             <input type="submit" value="disable" ${status_disable}></input>&nbsp;
	           	         </form:form>
	           	         <br/>
	           	         <form:form id="enableOptionState${option.id}" action="${pageContext.request.contextPath}/content/options/enableOption" method="get">
	           	             <input type="hidden" name="questionId" value="${questionId}"></input>	            	         
	           	             <input type="hidden" name="optionId" value="${option.id}"></input>	           	            
	           	             <input type="submit" value="enable" ${status_enable}></input>&nbsp;
	           	         </form:form>
	           	         
	           	         
	           	         
	           	         <br/>
	           	         <form:form id="editOptionForm${option.id}" 
	           	         action="${pageContext.request.contextPath}/content/options/editOption" method="post">	           	             
	           	             <input type="hidden" name="questionId" value="${questionId}"></input>
	           	             <input type="hidden" name="optionId" value="${option.id}"></input>
		           	         <input type="button" onclick="enableEditOptionText(${option.id})" value="edit option"></input>&nbsp;	           	         
		           	         
		           	         <div id="editOption${option.id}" style="display:none">
		           	         <input id="editableOptionText${option.id}" name="editOption" type="text" class="editableOptionText" placeholder="${option.text}"></input>
		           	         <input type="button" value="save" onclick="updateOption(${option.id})"></input>
	           	             <input type="button" value="cancel" onclick="disableEditOptionText(${option.id})"></input>
	           	             </div>
	           	         </form:form>
	           	         <br/>
	           	          
	           	         <form:form id="editOptionOrder${option.id}" action="${pageContext.request.contextPath}/content/options/editOptionOrder" method="post">	           	             
	           	             <input type="hidden" name="optionId" value="${option.id}"></input>		           	         	           	         
		           	         <input type="hidden" name="questionId" value="${questionId}"/>
		           	         <div id="editOptionOrder${option.id}">
		           	         <select name="order">
			           	         <option selected>1</option>
			           	         <option>2</option>
			           	         <option>3</option>
			           	         <option>4</option>
			           	         <option>5</option>		           	         
		           	         </select>
		           	         
		           	         <input type="submit" value="save order" ></input>
		           	         	           	            
	           	             </div>
	           	         </form:form>
	           	           <br/>
	           	           
	           	           
				         <hr/>           
	               </c:forEach>
	               </c:if>
	               <br/>
			</div>
			
			<hr>	
			</div>
	
</html>
