<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Question Entry Screen</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="<c:url value="/resources/js/addNewMCQquestion.js"/>"></script>
<script src="<c:url value="/resources/js/addNewFreeTextquestion.js"/>"></script>
<script src="<c:url value="/resources/js/searchQuestion.js"/>"></script>
<script src="<c:url value="/resources/js/updateQuestion.js"/>"></script>
<script src="<c:url value="/resources/js/updateOption.js"/>"></script>
<script src="<c:url value="/resources/js/latex.js"/>"></script>
<script src="<c:url value="/resources/js/jquery.iframe-transport.js" />"  type='text/javascript'></script>
<script>
var myContextPath = "${pageContext.request.contextPath}";
$(document).ready(function(){
	$("#mSubmit").click(function(){
		addNewMCQQuestion();
	});

	$("#ffSubmit").click(function(){
		addNewFreeTextQuestion();
	});

	$("#questionIdSearchButton").click(function(){
		searchQuestion();
	});

	$("#questiontextUpdate").click(function(){
		updateQuestionText();
	});

	$("#updatePreTextForFreeTextQustionButton").click(function(){
		updatePreTextForFreeTextQuestion();
	});

	$("#updatePostTextForFreeTextQustionButton").click(function(){
		updatePostTextForFreeTextQuestion();
	});

	$("#updateQuestionTypeButton").click(function(){
		updateQuestionType();
	});

	
	$("#updateTimeAllocationButton").click(function(){
		updateTimeAllocation();
	});
	$("#updateDiffLevelButton").click(function(){
		updateDifficultyLevel();
	});
	$("#updateStateEnable").click(function(){
		updateStateEnable();
	});
	$("#updateStateDisable").click(function(){
		updateStateDisable();
	});
	$("#updateStateSoftDelete").click(function(){
		updateStateSoftDelete();
	});

	$("#updateOption1Text").click(function(){
		updateOption1Text();
	});
	$("#updateOption1Enable").click(function(){
		updateOption1Enable();
	});
	$("#updateOption1Disable").click(function(){
		updateOption1Disable();
	});
	$("#updateOption1SoftDelete").click(function(){
		updateOption1SoftDelete();
	});

	$("#updateOption2Text").click(function(){
		updateOption2Text();
	});
	$("#updateOption2Enable").click(function(){
		updateOption2Enable();
	});
	$("#updateOption2Disable").click(function(){
		updateOption2Disable();
	});
	$("#updateOption2SoftDelete").click(function(){
		updateOption2SoftDelete();
	});

	$("#updateOption3Text").click(function(){
		updateOption3Text();
	});
	$("#updateOption3Enable").click(function(){
		updateOption3Enable();
	});
	$("#updateOption3Disable").click(function(){
		updateOption3Disable();
	});
	$("#updateOption3SoftDelete").click(function(){
		updateOption3SoftDelete();
	});

	$("#updateOption4Text").click(function(){
		updateOption4Text();
	});
	$("#updateOption4Enable").click(function(){
		updateOption4Enable();
	});
	$("#updateOption4Disable").click(function(){
		updateOption4Disable();
	});
	$("#updateOption4SoftDelete").click(function(){
		updateOption4SoftDelete();
	});


	$("#updateOption5Text").click(function(){
		updateOption5Text();
	});
	$("#updateOption5Enable").click(function(){
		updateOption5Enable();
	});
	$("#updateOption5Disable").click(function(){
		updateOption5Disable();
	});
	$("#updateOption5SoftDelete").click(function(){
		updateOption5SoftDelete();
	});

	$("#updateScreenPreviousSearchButton").click(function(){
		var pageNo = $("#updateScreenPageNo").val();
		if(pageNo != 0){
			pageNo--;
			$("#updateScreenPageNo").val(pageNo);
				searchQuestionbyState();
		}
	});

	$("#updateScreenNextSearchButton").click(function(){
		var pageNo = $("#updateScreenPageNo").val();
		pageNo++;
		$("#updateScreenPageNo").val(pageNo);
			searchQuestionbyState();
	});

	$("#searchStateOnUpdateScreen").change(function(){
		resetPagination();
		searchQuestionbyState();
	});

	
	
});

</script>
<style type="text/css">
    h3{
        margin: 30px 0;
        padding: 0 200px 15px 0;
        border-bottom: 1px solid #E5E5E5;
    }
   
	.bs-example{
    	margin: 20px;
    }
.form-horizontal .control-label {
    text-align: right;
}
</style>
</head>
<body>
<div class="bs-example">
<div class="" align="center" style="color:green"><h4><b> Data Entry for : ${examName}  -  ${examSectionName} - ${topicName}</b> </h4></div>
<input type="hidden" id="topicId" name="topicId" value="${topicId}"></input>

    <ul class="nav nav-tabs">
		<li class="dropdown">
            <a data-toggle="dropdown" class="dropdown-toggle" href="#">Add a New Question<b class="caret"></b></a>
            <ul class="dropdown-menu">
                <li class="active"><a data-toggle="tab" href="#dropdown1"> MCQ </a></li>
                <li><a data-toggle="tab" href="#dropdown2"> Free Text </a></li>
            </ul>
        </li>
        <li><a data-toggle="tab" href="#sectionB">Update Question</a></li>
        <li><a data-toggle="tab" href="#sectionA">Latex Editor</a></li>
        <li><a href="../../../rooms/content"> Change Topic</a></li>
    </ul>
    <div class="tab-content">
	<div id="dropdown1" class="tab-pane fade in active">
     <jsp:include page="addNewMCQQuestion.jsp" flush="true"/>
        </div>
        <div id="dropdown2" class="tab-pane fade">
          <jsp:include page="addNewFreeTextQuestion.jsp" flush="true"/>
        </div>
        <div id="sectionB" class="tab-pane fade">
        <jsp:include page="searchQuestionFilter.jsp" flush="true"/>
        <jsp:include page="updateQuestion.jsp" flush="true"/>
        </div>
        <div id="sectionA" class="tab-pane fade">
            <iframe id="latex_iframe" src="http://www.hostmath.com" width="1000" height="800"></iframe><br/><br/>            
        </div>
                
    </div>
</div>
</body>
</html>