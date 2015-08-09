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
		 
		<script src="<c:url value="/resources/js/jquery.ui.widget.js" />"  type='text/javascript'></script>
		
		<script type="text/javascript" src="http://www.hostmath.com/Math/MathJax.js?config=OK"></script>
		
		<script src="<c:url value="/resources/js/jquery.iframe-transport.js" />"  type='text/javascript'></script>
		
		<script src="<c:url value="/resources/js/jquery.fileupload.js" />"  type='text/javascript'></script>
		
		<script src="<c:url value="/resources/js/jquery.cloudinary.js" />"  type='text/javascript'></script>
		<script type="text/javascript">
			
		
		$.cloudinary.config({ cloud_name: 'previdus', api_key: '465561835822868'});

		  function showMathEditor(){
		        
		        $("#math_editor").show();
			}

          function hideMathEditor(){
		        
		        $("#math_editor").hide();
			}
		
			
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
	    <div id="maincontent" class="container">
			<h1>
				You can add, edit, enable or disable an option of a question <br/><i>${questionText}</i><br/> for a topic <i>${topicName}</i> of the exam section <i> ${examSectionName}</i> for the exam <i>${examName} </i>here
			</h1>
			
			<span style="text-decoration:underline">Options</span>
					<br/>
		     <form:form action="${pageContext.request.contextPath}/content/options/addOption" method="post">
		           <input type="hidden" name="questionId" value="${questionId}"/>
                    Order of option: 
	               <select name="order" >
		               <option selected>1</option>
		               <option>2</option>
		               <option>3</option>
		               <option>4</option>
		               <option>5</option>		               
	               </select>
	               &nbsp; Type the question here. If it is a math question with formulae, 
	               please use the math editor to type the question. Use \hspace{0.1cm} for horizontal spaces and adjust
	               the cm unit for spacing. Copy the question from "Get Embedded Code" link in this math editor and paste it in the text area below. 
	               Strip away the javascript call to MathJax.js in the line below the math formula generated.
	               
	               <a id="math_editor_link" href="javascript:void(0);"  onclick="showMathEditor();"> math editor</a>
	               <div id="verbal_editor">
		               <textarea name="addOption" cols=250 rows=50></textarea><br/><br/>
		               <input type="submit" value="save option"/>
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
	           	         
	           	         <form:form id="editOptionImageForm${question.id}" action="${pageContext.request.contextPath}/content/options/editOptionImage" method="post">
	           	         <input type="hidden" name="questionId" value="${questionId}"></input>	            	         
	           	         <input type="hidden" name="optionId" value="${option.id}"></input>
	           	         <input name="file" type="file" 
	                           class="cloudinary-fileupload" data-cloudinary-field="image_url" 
	                             data-form-data="${htmlEscapedJson}"></input>
	                     </form:form>
	                     <input type="submit" value="submit new image"></input>&nbsp;
	                      <br/>
	           	         
	           	         <br/> 
	           	         
	           	         
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
