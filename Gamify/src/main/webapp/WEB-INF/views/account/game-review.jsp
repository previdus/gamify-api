<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<style>
.options-correct {
	background-color: #D0F5A9;
	border: 1px solid #213F00;
}

.mainHeader {
	background-color: #D0F5A9;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>LMS-Review</title>
</head>
<!-- vendor styles -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet"
	href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="<c:url value="/resources/css/bootstrap.min.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/css/bootstrap-theme.min.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/css/sidebar.css"/>">

<!-- custom styles -->
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/page_four.css"/>">

<script type="text/javascript"
	src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
<script src="<c:url value="/resources/js/selfAnalysisTool.js"/>"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery.cookie.js" /> "></script>
<script id="mathjax" type="text/javascript"
	src="http://www.hostmath.com/Math/MathJax.js?config=OK"></script>

<script type="text/javascript">
	var myContextPath = "${pageContext.request.contextPath}";
	$(document).ready(
			function() {

				//$("#gameReview").css("opacity","0.5"); 
				function convertHex(hex, opacity) {
					hex = hex.replace('#', '');
					r = parseInt(hex.substring(0, 2), 16);
					g = parseInt(hex.substring(2, 4), 16);
					b = parseInt(hex.substring(4, 6), 16);
					result = 'rgba(' + r + ',' + g + ',' + b + ',' + opacity
							/ 100 + ')';
					return result;
				}

				$('#gameReview').css('background');
				$('.label').css('font-weight', "900");
				$('.label').css('color', "white");
				$('.showDivision').css('border-bottom', "10px solid #000");

				$("#previousButton").click(function() {
					var pageNo = $("#currentPageNo").val();
					if (pageNo != 1) {
						var currentDiv = '#logs' + pageNo;
						$(currentDiv).hide();
						pageNo--;
						var prevDiv = '#logs' + pageNo;
						$(prevDiv).show();
						$("#currentPageNo").val(pageNo);

					} else {
						alert('No more questions');

					}
				});

				$("#nextButton").click(function() {
					var pageNo = $("#currentPageNo").val();
					var maxPageNo = $("#totalNoOfQuestions").val();
					if (pageNo == maxPageNo) {
						alert('No more questions');
					} else {
						var currentDiv = '#logs' + pageNo;
						$(currentDiv).hide();
						pageNo++;
						var nextDiv = '#logs' + pageNo;
						$(nextDiv).show();

						$("#currentPageNo").val(pageNo);
					}
				});

			});

</script>
<body id="gameReview">
	<nav class="navbar navbar-default navbar-fixed-top">
		<div class="container-fluid lms">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
					aria-expanded="false">
					<span class="sr-only"></span> <span class="icon-bar"></span>
				</button>
				<div class="logo-section">
					<h1>
						lastman<span class="half">standing</span>
					</h1>
				</div>
			</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav navbar-right">
					<li><a class="clearTimeInterval" href="#"
						onClick="$('#backToMainRoom').submit()"> <span
							class="leave-game">Leave Game </span>
					</a></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false"> <span id="displayUserName"></span> <span
						class="caret"></span>
					</a>
					<ul class="dropdown-menu">
						<li class="clearTimeInterval"><a href="#"><i
								class="glyphicon glyphicon-user">&nbsp;Profile</i></a></li>
						<li><a href="#menu-toggle" id="menu-toggle"><i
								class="glyphicon glyphicon-indent-left">&nbsp;Toggle Pane</i></a></li>
						<li class="clearTimeInterval"><a href="#"><i
								class="glyphicon glyphicon-cog">&nbsp;Settings</i></a></li>
						<li role="separator" class="divider"></li>
						<li class="clearTimeInterval"><a href="#"
							onClick="$('#logoutform').submit()"><i
								class="glyphicon glyphicon-log-out">&nbsp;Logout</i></a></li>
					</ul></li>
				</ul>
			</div>
		<!-- /.navbar-collapse -->
		</div>
	<!-- /.container-fluid --> 
	</nav>

	<form id="backToMainRoom"
		action="${pageContext.request.contextPath}/rooms/changeroom"></form>
	<form id="logoutform"
		action="${pageContext.request.contextPath}/logout"></form>
	<div id="page-content-wrapper">
		<input type="hidden" id="gameId" name="gameId" value="${gi.id}"></input>
		<br> <br>
		<div>
			<div id="questionSection">
				<div class="text-center bg-danger text-capitalize mainHeader">

					<h3>
						<b>Game Review Screen - <c:if test="${gi.examSection != null}">
								<c:out value="${gi.examSection.name}"></c:out>
							</c:if> <c:if test="${gi.topic != null}">
								( <c:out value="${gi.topic.name}"></c:out> )
							</c:if>
						</b>
					</h3>

				</div>

				<nav> 
					<div class="text-center bg-danger">
						<c:if test="${gi.gameWinner != null}">
						    <div class='row'>
						        <div class="col-sm-4 text-left  text-danger">
									<span class="badge"> Game Winner: 
									</span>
									<c:out value="${gi.gameWinner.displayName}"></c:out>
								</div>
								<div class="col-sm-8 text-right text-danger ">
									Winning Bonus Points:<span class="badge">
									<c:out value=" ${gi.gameWinningPoints}"></c:out>
									</span>
								</div>
							</div>
						</c:if>
						<c:if test="${gi.gameWinner == null}">
									No Winner
						</c:if>
						<div style="display: none">
							Played On:
							<c:out value="${gi.gameCreationTime}"></c:out>
						</div>
					</div>
				</nav>
				<div id="reviewFormStatus" ></div>
				<ul class="pager">
					<li class="previous text-capitalize"><a id="previousButton"
						href="#">&larr; Previous Question</a></li>
					<li class="next text-capitalize"><a id="nextButton" href="#">Next
							Question&rarr;</a></li>
				</ul>
				<input type="hidden" id="totalNoOfQuestions"
					value="${fn:length(gi.previousQuestionLogs)}" /> <input
					type="hidden" id="currentPageNo" value="1">
				<div class="container">
					<c:forEach items="${gi.previousQuestionLogs}" var="questionLog"
						varStatus="loopCounter">
						<c:choose>
							<c:when test="${loopCounter.count == 1}">
						<div id="logs${loopCounter.count}" class="panel panel-default">
							</c:when>
							<c:otherwise>
						<div id="logs${loopCounter.count}" style="display:none"  class="panel panel-default">
							</c:otherwise>
						</c:choose>
							<div class="">
								<nav class="bg-danger text-danger text-center">
									<div class="row">
										<div class="col-sm-2 text-left label label-success" onclick='requestSolution(${questionLog.question.id})'>
											<b> <c:out value=" Request Solution" /> <i class="glyphicon glyphicon-hand-up"></i></b>
										</div>
										<div class="col-sm-8 text-center"> Question </div>
										<div class="col-sm-2 text-right label label-success" onclick='starMarkQuestion(${questionLog.question.id})'><b> Mark Review <i class="glyphicon glyphicon-star"></i></b>
										</div>
								
									</div>
								</nav>
								</br>
								<div class="">
									<h4>
									<p>
										<c:out value="${questionLog.question.questionText}">
										</c:out>
									</p>
									</h4>
								</div>
								<nav class="bg-danger text-danger text-right"> Best <i
									class="glyphicon glyphicon-hourglass"></i> 
									<c:choose>
										<c:when test="${questionLog.bestTime <= 0}">
											<c:out value="None of you answered it correct!"></c:out>
										</c:when>
										<c:otherwise>
											<c:out value="${questionLog.bestTime/ 1000}(sec)"></c:out>
										</c:otherwise>
									</c:choose> 
								</nav>
								<nav class="bg-danger text-danger text-center ">
									<b> Options </b> 
								</nav>
								</br>
								<div class="row text-capitalize">
									<div class="col-sm-2">
									</div>
									<c:forEach items="${questionLog.question.options}" var="option" varStatus="stat">
										<div class="col-sm-2 text-capitalize">
											<p>
												<c:if test="${questionLog.answerKey.optionId == option.id}">
													<div>
														<span class="label label-success text-capitalize"><i
															class="glyphicon glyphicon-ok"></i> <c:out
															value="${option.text}"></c:out> </span>
													</div>
												</c:if>
												<c:if test="${questionLog.answerKey.optionId != option.id}">
													<div>
														<span class="label label-danger text-capitalize"><c:out
															value="${option.text}"></c:out></span>
													</div>
												</c:if>
											</p>
										</div>
									</c:forEach>
								</div>
								</br>
								<nav class="bg-danger text-center text-danger">
								<b> Player Responses </b> </nav>
								</br>
								<div class="row">
									<div class="col-md-1"></div>
									<c:forEach items="${questionLog.playersResponses}"
										var="playerResponse">
										<div class="col-md-5">
											<div class="thumbnail">
												<img
												src="https://s3.amazonaws.com/uifaces/faces/twitter/vladarbatov/73.jpg"
												style="border-radius: 50%; border: 3px solid #FFF; opacity: 1;"
												alt="...">
												<div class="caption text-center">
													<p>
														<c:out value="${playerResponse.user.name}"></c:out>
													</p>
													<p>
														<i class="glyphicon glyphicon-hourglass"></i>
														<c:out value="${playerResponse.timeTakenToAnswer/1000}"></c:out>
														(sec)
													</p>
													<div>
														<p>
															<c:if
																test="${questionLog.answerKey.optionId != playerResponse.response.id}">
																<span class="label label-danger">Response
																	Given: <c:out value="${playerResponse.response.text}"></c:out>
																</span>
																<div>
																	<p>
																		<span class="label label-danger">Points
																			Earned: <c:out value="${playerResponse.pointsEarned}"></c:out>
																		</span>
																	</p>
																</div>
															</c:if>
															<c:if
																test="${questionLog.answerKey.optionId == playerResponse.response.id}">
																<span class="label label-success">Response
																	Given: <c:out value="${playerResponse.response.text}"></c:out>
																</span>
																<div>
																	<p>
																		<span class="label label-success">Points
																			Earned: <c:out value="${playerResponse.pointsEarned}"></c:out>
																		</span>
																	</p>
																</div>
															</c:if>
														</p>

														<div>
															<c:if test="${playerResponse.questionWinner == true}">
																<span class="label label-success text-capitalize">Question
																	Winner:<i class="glyphicon glyphicon-ok"></i>
																</span>
															</c:if>
															<c:if test="${playerResponse.questionWinner == false}">
																<span class="label label-danger text-capitalize">Question
																	Winner:<i class="glyphicon glyphicon-remove"></i>
																</span>
															</c:if>
														</div>
													</div>
												</div>
											</div>
										</div>
									</c:forEach>
								</div>
							</div>
							<div class="text-center bg-danger text-capitalize text-danger mainHeader">
							Self Analysis Tool</div>
			
							<div class="text-left bg-info text-info text-capitalize mainHeader">
								<div class="row">
									<div class="col-md-12">
										Why do you think you answered the question wrong ?
									</div>
								</div>
							</div>
							<div class="text-left ">
								<div class="row">
									<div class="col-md-1"></div>
									<div class="col-md-11">
										<div class="text-danger" id='Reason${questionLog.question.id}A1' style="display: none")'>
										<i class="glyphicon glyphicon-ok"></i><c:out value= " OoPS!! Calculation mistake." /></div>
										<div class="" id='Reason${questionLog.question.id}A2' 
										onclick='addIncorrectReason(${questionLog.question.id}, "CALCULATION_MISTAKE", true, "A")'>
										<i class="glyphicon glyphicon-hand-right"></i><c:out value= " OoPS!! Calculation mistake." /></div>	
										<div class="text-danger" style="display: none" id='Reason${questionLog.question.id}B1' >
										<i class="glyphicon glyphicon-ok"></i><c:out value= " I have no clue know how to solve this question."/></div>
										<div class="" onclick='addIncorrectReason(${questionLog.question.id}, "CONCEPT_UNKNOWN",true, "B")' id='Reason${questionLog.question.id}B2'>
										<i class="glyphicon glyphicon-hand-right"></i><c:out value= " I have no clue know how to solve this question."/></div>
										<div class="text-danger" style="display: none" id='Reason${questionLog.question.id}C1' >
										<i class="glyphicon glyphicon-ok"></i><c:out value= " I know how to solve this question but time allocated was less."/></div>
										<div class="" onclick='addIncorrectReason(${questionLog.question.id}, "LESS_TIME", true, "C")' id='Reason${questionLog.question.id}C2'>
										<i class="glyphicon glyphicon-hand-right"></i><c:out value= " I know how to solve this question but time allocated was less."/></div>			
										<div class="text-danger" style="display: none" id='Reason${questionLog.question.id}D1' >
										<i class="glyphicon glyphicon-ok"></i><c:out value= " My understanding of question was different."/></div>
										<div class="" onclick='addIncorrectReason(${questionLog.question.id}, "QUESTION_INTEPRETATION_ISSUE", true, "D")' id='Reason${questionLog.question.id}D2'>
										<i class="glyphicon glyphicon-hand-right"></i><c:out value= " My understanding of question was different."/></div>
		
										<div class="text-danger" style="display: none" id='Reason${questionLog.question.id}E1' >
										<i class="glyphicon glyphicon-ok"></i><c:out value= " The question/ correct option seems wrong."/></div>
										<div class="" onclick='addIncorrectReason(${questionLog.question.id}, "INCORRECT_QUESTION", true, "E")' id='Reason${questionLog.question.id}E2'>
										<i class="glyphicon glyphicon-hand-right"></i><c:out value= " The question/ correct option seems wrong."/></div>
									</div>
								</div>
							</div>
							<div class="text-center bg-danger text-capitalize mainHeader">
							-</div>
						</div>
					
					
				</c:forEach>
			</div>
		</div>
	</nav>	
</body>
</html>