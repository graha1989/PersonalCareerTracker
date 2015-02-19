<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<security:authorize var="loggedIn" access="isAuthenticated()" />
<!DOCTYPE html>
<html xmlns:ng="http://angularjs.org" id="ng-app" ng-app="PersonalCareerTracker" class="personalCareerTrackerApp">
<head >
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><spring:message code='project.name'/></title>
	
	<link href="<spring:url value='resources/css/styles.css' />" rel="stylesheet">
	<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
	      <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	      <!--[if lt IE 9]>
	         <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
	         <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
	<![endif]-->
</head>
<body>
	
	<div class="container" id="appContainer">
	
		<div>
			<c:choose>
				<c:when test="${loggedIn}">
					<a id="logout" href="j_spring_security_logout"><spring:message code='logout'/></a>
				</c:when>
				<c:otherwise>
					<a id="login" href="login"><spring:message code='login.login'/></a>
				</c:otherwise>
			</c:choose>
		</div>
		
		<div>Hello world</div>
		
		<div ng-view>
			<!-- Pages will be inserted here -->
		</div>
		
	</div>
	
	<script type="text/javascript" src="<spring:url value='js/lib/angular.min.js' />"></script>
	<script type="text/javascript" src="<spring:url value='js/lib/angular-route.min.js' />"></script>
	<script type="text/javascript" src="<spring:url value='js/lib/jquery-1.11.1.min.js' />"></script>
	<script type="text/javascript" src="<spring:url value='js/lib/ui-bootstrap-tpls-0.11.0.min.js' />"></script>
	<script type="text/javascript" src="<spring:url value='js/app.js' />"></script>
	<script type="text/javascript" src="<spring:url value='js/controllers/AdminHomeController.js' />"></script>
	<script type="text/javascript" src="<spring:url value='js/controllers/ShowStudentsController.js' />"></script>
	<script type="text/javascript" src="<spring:url value='js/service/PctService.js' />"></script>
</body>
</html>