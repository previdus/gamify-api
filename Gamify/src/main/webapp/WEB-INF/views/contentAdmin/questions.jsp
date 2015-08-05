<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
	<head>
		<title>Content add/edit/disable</title>
		<link rel="stylesheet" href="<c:url value="/resources/blueprint/screen.css" />" type="text/css" media="screen, projection">
		<link rel="stylesheet" href="<c:url value="/resources/blueprint/print.css" />" type="text/css" media="print">
		<link rel="stylesheet" href="<c:url value="/resources/css/questions.css" />" type="text/css" media="print">
		<!--[if lt IE 8]>
			<link rel="stylesheet" href="<c:url value="/resources/blueprint/ie.css" />" type="text/css" media="screen, projection">
		<![endif]-->
		<link rel="stylesheet" href="<c:url value="/resources/css/popup.css" />" type="text/css" media="screen, projection">
		<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
		  
		
		
		 
		<script src="<c:url value="/resources/js/jquery.ui.widget.js" />"  type='text/javascript'></script>
		
		<script type="text/javascript" src="http://www.hostmath.com/Math/MathJax.js?config=OK"></script>
		
		<script src="<c:url value="/resources/js/jquery.iframe-transport.js" />"  type='text/javascript'></script>
		
		<script src="<c:url value="/resources/js/jquery.fileupload.js" />"  type='text/javascript'></script>
		
		<script src="<c:url value="/resources/js/jquery.cloudinary.js" />"  type='text/javascript'></script>
		<script type="text/javascript">
			
		$.cloudinary.config({ cloud_name: 'previdus', api_key: '465561835822868'})

		
		

		    function showMathEditor(){
		        
		        $("#math_editor").show();
			}

            function hideMathEditor(){
		        
		        $("#math_editor").hide();
			}

        
			
			function enableEditQuestionText(questionId){
				
				$("#editQuestion"+questionId).show();
				
		    }
			function disableEditQuestionText(questionId){
				$("#editQuestion"+questionId).hide();
		    }
		    function updateQuestion(questionId){
			    var newName = $("#editableQuestionText"+questionId).val();
			    if(newName == null || newName ==""){
				   alert("question text cannot be empty"); 
				   return;   
				} 
				newName = $.trim(newName);
				var oldName = $("#questionTextDisplay"+questionId).text();  
				if(oldName == newName){
				   alert("question text cannot be the same"); 
				   return;  
				}
				$("#editQuestionForm"+questionId).submit();			 
				
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
				You can add, edit, enable or disable a question for a topic ${topicName} of the exam section  ${examSectionName} for the exam ${examName} here
			</h1>
			
			<span style="text-decoration:underline">Questions</span>
					<br/>
		     <form:form action="${pageContext.request.contextPath}/content/questions/addQuestion" method="post">
		           <input type="hidden" name="topicId" value="${topicId}"/>
		            
	              
	               <b>difficulty level: </b>
	               <select name="difficultyLevel" title="difficulty level">
		               <option selected>1</option>
		               <option>2</option>
		               <option>3</option>
	               </select>
	               &nbsp; Type the question here. If it is a math question with formulae, 
	               please use the math editor to type the question. Use \hspace{0.1cm} for horizontal spaces and adjust
	               the cm unit for spacing. Copy the question from "Get Embedded Code" link in this math editor and paste it in the text area below. 
	               Strip away the javascript call to MathJax.js in the line below the math formula generated.
	               
	               <a id="math_editor_link" href="javascript:void(0);"  onclick="showMathEditor();"> math editor</a>
	               <div id="verbal_editor">
		               <textarea name="addQuestion" cols=250 rows=50></textarea><br/><br/>
		               <input type="submit" value="save question"/>
		               &nbsp;&nbsp;&nbsp; Upload Image ->
		               <input name="file" type="file" 
	                           class="cloudinary-fileupload" data-cloudinary-field="image_url" 
	                             data-form-data="${htmlEscapedJson }"></input>
	                             <br/>
	                             <br/>
	               </div>
	               <div id="math_editor" style="display: none">
	                   <iframe id="latex_iframe" src="http://www.hostmath.com" width="800" height="800"></iframe><br/><br/>
	                   <br/>
		               <input type="button" value="cancel" onClick="hideMathEditor();"/>
		               &nbsp;&nbsp;&nbsp;
		               <br/><br/><br/>		                
	               </div>
	               
	               <span id="error" style="color:red">${error}</span><span id="success" style="color:green">${success}</span>	               
	        </form:form>
	        
			<div class="span-12 last" style="width: 800px; height: 500px; overflow-y: scroll; border:solid">					  	
	               <br/>
					<c:forEach var="question" items="${questions}" step="1">	
	           	         <span id="questionDisplay${question.id}">${question.questionText}</span>
	           	         <br/>
	           	         <span id="questionDifficultyLevelDisplay${question.id}"><b>difficulty level:</b>${question.difficultyLevel}</span>
	           	         <br/>
	           	         <c:set var="status_disable"  value="${question.state == 'ACTIVE' ?'':'disabled'}"/>
	           	         <c:set var="status_enable"  value="${question.state == 'INACTIVE' ?'':'disabled'}"/>	
	           	         <form:form id="disableQuestionState${question.id}" action="${pageContext.request.contextPath}/content/questions/disableQuestion" method="get">
	           	             <input type="hidden" name="topicId" value="${topicId}"></input>	            	         
	           	             <input type="hidden" name="questionId" value="${question.id}"></input>
	           	             <input type="submit" value="disable" ${status_disable}></input>&nbsp;
	           	         </form:form>
	           	         <br/>
	           	         <form:form id="enableQuestionState${question.id}" action="${pageContext.request.contextPath}/content/questions/enableQuestion" method="get">
	           	             <input type="hidden" name="topicId" value="${topicId}"></input>	            	         
	           	             <input type="hidden" name="questionId" value="${question.id}"></input>	           	            
	           	             <input type="submit" value="enable" ${status_enable}></input>&nbsp;
	           	         </form:form>
	           	         <br/>
	           	         <form:form id="editQuestionImageForm${question.id}" action="${pageContext.request.contextPath}/content/questions/editQuestionImage" method="post">
	           	         <input type="hidden" name="topicId" value="${topicId}"></input>	            	         
	           	         <input type="hidden" name="questionId" value="${question.id}"></input>
	           	         <input name="file" type="file" 
	                           class="cloudinary-fileupload" data-cloudinary-field="image_url" 
	                             data-form-data="${htmlEscapedJson}"></input>
	                     </form:form>
	                     <input type="submit" value="submit new image"></input>&nbsp;
	                      <br/>
	           	         
	           	         <br/>
	           	         <form:form id="editQuestionForm${question.id}" action="${pageContext.request.contextPath}/content/questions/editQuestion" method="post">	           	             
	           	             <input type="hidden" name="topicId" value="${topicId}"></input>
	           	             <input type="hidden" name="questionId" value="${question.id}"></input>
		           	         <input type="button" onclick="enableEditQuestionText(${question.id})" value="edit question"></input>&nbsp;	           	         
		           	         
		           	         <div id="editQuestion${question.id}" style="display:none">
		           	         <textarea id="editableQuestionText${question.id}" name="editQuestion" class="editableQuestionText"
		           	          placeholder="${question.questionText}" rows=50 cols=250></textarea>
		           	         <input type="button" value="save" onclick="updateQuestion(${question.id})"></input>
	           	             <input type="button" value="cancel" onclick="disableEditQuestionText(${question.id})"></input>
	           	             </div>
	           	         </form:form>
	           	         <br/>
	           	         
	           	         <form:form id="editQuestionLevel${question.id}" action="${pageContext.request.contextPath}/content/questions/editQuestionLevel" method="post">	           	             
	           	             <input type="hidden" name="questionId" value="${question.id}"></input>		           	         	           	         
		           	         <input type="hidden" name="topicId" value="${topicId}"/>
		           	         <div id="editQuestionLevel${question.id}">
		           	         <select name="difficultyLevel">
		           	         <option selected>1</option>
		           	         <option>2</option>
		           	         <option>3</option>
		           	         </select>
		           	         <input type="submit" value="save difficulty level" ></input>
	           	            
	           	             </div>
	           	         </form:form>
	           	         <br/>
	           	         <form:form action="${pageContext.request.contextPath}/content/options" method="get">
				               <input type="hidden" name="questionId" value="${question.id}"/>
				               <input type="submit" value="go to the options for this question" /><br/>				              	               
				        </form:form>
				       
				         <br/>  
				         <hr/>           
	               </c:forEach>
	               <br/>
		
			
			<hr>	
		</div>
		
	    
	  </div>
	  </body>
</html>
