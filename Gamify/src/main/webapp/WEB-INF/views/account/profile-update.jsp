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
.logo-section h1 .half {
  color: #1976d2;
}
.logo-section h1 .quarter {
  color: red;
}
.light-bg {
	position: absolute;
	top: 0;
	left: 0;
	box-shadow: 0px 8px 6px -6px #000;
	background-color: #fff;
    width: 100%;
	opacity: 0.9;
}

#login header .light-bg {
  top: 35px;
  height: 80px;
  z-index: -1;
  background:rgb(245, 245, 245);
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Profile Update</title>
</head>
<!-- vendor styles -->
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
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
  <link rel="stylesheet" type="text/css" href="resources/css/common.css">
  <link rel="stylesheet" type="text/css" href="resources/css/login_page.css">
<!-- custom styles -->
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/page_four.css"/>">
<script type="text/javascript"
	src="<c:url value="/resources/js/bootstrap.min.js" />"></script>


<script type="text/javascript"
	src="<c:url value="/resources/js/jquery.cookie.js" /> "></script>

<script src="//cdnjs.cloudflare.com/ajax/libs/validate.js/0.9.0/validate.min.js"></script>
<script type="text/javascript">
	var myContextPath = "${pageContext.request.contextPath}";
	$(document).ready(function(){
		$("#displayNameUpdateButton").click(function(){
			updateDisplayName();
		});
		$("#emailButton").click(function(){
			updateEmailAddress();
		});
		$("#parentEmailButton").click(function(){
			updateParentsEmailAddress();
		});
		$("#phoneNumberUpdateButton").click(function(){
			updatePhoneNumberAddress();
		});
		$("#genderUpdateButton").click(function(){
			updateGender();
		});
		$("#updatePasswordButton").click(function(){
			changePassword();
		});
	});
</script>


<body id="profileUpdate">
	<jsp:include page="topmenu.jsp" flush="true"/>
		<div class="container">
			</br></br></br></br></br>
			<div class="row">
				<div class="col-md-2" ></div>
				<div class="light-bg col-md-8">
					<jsp:include page="profile-update-inner.jsp" flush="true"/>
				</div>	
				<div class="col-md-2" ></div>
			</div>
		</div>
	</div>
	<script src="<c:url value="/resources/js/updateUserProfile.js"/>"></script>	
</body>
</html>