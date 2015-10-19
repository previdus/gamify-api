<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/json.min.js" /> "></script>
        <script type="text/javascript" src="<c:url value="/resources/js/jquery-1.11.3.min.js" />" > </script>
	    <script type="text/javascript" src="<c:url value="/resources/js/bootstrap.min.js" />" > </script>
<html>
    <head>
        <title>Choose A Exam Category</title>
    </head>
    <body>
    		</br></br>
    	<h1>Choose A Topic To Navigate to Question Management Screen</h1>
			</br></br></br></br>
    	<div class="container-fluid">
    	<table class="table" style="width:100%" draggable="true" border="1" >
			<c:forEach var="exam" items="${room.exams}" step="1">
			    <tr>
			    	<td align="center" valign="middle"> 
		        		<label>  <c:out value="${exam.examName}"></c:out></label>
		        	</td>
		        	<td>
		       			<c:if test="${not empty exam.examSections}">
		       			    <table draggable="true" style="width:100%" border="1">
		               			<c:forEach var="examSection" items="${exam.examSections}" step="1">
		               			 	<tr>
		                    			<td align="center" valign="middle"> <label>  <c:out value="${examSection.name}"></c:out></label></td>
		                   				<c:if test="${not empty examSection.topics}">
											<td>
												<table draggable="true" style="width:100%" border="1">
													<c:forEach var="topic" items="${examSection.topics}" step="1">
														<tr><td align="center" valign="middle" class="active">  <a href="../content/question/add/${topic.id}"> <label>  <c:out value="${topic.name}"> </c:out> ( Click Me )</label></a></td></tr>
													</c:forEach>
												</table>
											</td>
			                			</c:if>
		              				</tr>
		              			</c:forEach>
		              		</table>
		          		</c:if>		                       
					</td>
				</tr>
			</c:forEach>
		</table>
		</div>
	</body>			
</html>