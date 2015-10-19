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
    	<h1>Choose A Topic To Navigate to Content Entry Screen</h1>
    	<table>
			<c:forEach var="exam" items="${room.exams}" step="1">
			    <tr>
			    	<td> 
		        		<label>  <c:out value="${exam.examName}"></c:out></label>
		        	</td>
		        	<td>
		       			 <c:if test="${not empty exam.examSections}">
		       			 	<table>
		               			<c:forEach var="examSection" items="${exam.examSections}" step="1">
		               			 	<tr>
		                    			<td> <label>  <c:out value="${examSection.name}"></c:out></label></td>
		                   				<c:if test="${not empty examSection.topics}">
		                   					<table>
		                           				<c:forEach var="topic" items="${examSection.topics}" step="1">
				                                	<tr><td>   <label>  <c:out value="${topic.name}"></c:out></label></td></tr>
				                    			</c:forEach>
				                 			</table>                       
			                			</c:if>
		              				</tr>
		              			</c:forEach>
		              		</table>
		          		</c:if>		                       
			</td>
			</tr>
			</c:forEach>
			</table>
			</body>
			
				
</html>