<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<security:authorize var="loggedIn" access="isAuthenticated()" />
<!DOCTYPE html>
<html xmlns:ng="http://angularjs.org" id="ng-app" ng-app="PersonalCareerTracker" class="personalCareerTrackerApp">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='project.name' /></title>

<link href="<spring:url value='resources/css/styles.css' />" rel="stylesheet">
<link href="<spring:url value='resources/css/bootstrap-theme.css' />" rel="stylesheet">
<link href="<spring:url value='resources/css/bootstrap.min.css' />" rel="stylesheet">
<link href="<spring:url value='resources/css/bootstrap-theme.min.css' />" rel="stylesheet">
<link href="<spring:url value='resources/css/datepicker.css' />" rel="stylesheet">
<link href="<spring:url value='resources/css/ngDialog.css' />" rel="stylesheet">

</head>
<body>
<c:set var="role" value='<%= session.getAttribute("currentUserRole") %>' />
	<div class="container bg-success" id="appContainer">
		<div class="col-sm-12 pull-right">
			<c:choose>
				<c:when test="${loggedIn}">
					<a class="btn btn-link pull-right btn-xs" id="logout" href="j_spring_security_logout"><spring:message code='logout' /></a>
				</c:when>
				<c:otherwise>
					<a id="login" class="btn btn-link pull-right btn-xs" href="login"><spring:message code='login.login' /></a>
				</c:otherwise>
			</c:choose>
		</div>
		<c:if test="${loggedIn}">
			<c:choose>
				<c:when test="${role == 'ROLE_USER'}">
					<hr/>
					<div>
						<jsp:include page="professorMenu.jsp" />
					</div>
				</c:when>
				<c:when test="${role == 'ROLE_ADMIN'}">
					<hr/>
					<div>
						<jsp:include page="admin/adminMenu.jsp" />
					</div>
				</c:when>
			</c:choose>
		</c:if>
		<div ng-view>
			<!-- Pages will be inserted here -->
		</div>
		<input type="hidden" value='<c:out value="${pageContext.response.locale}"></c:out>' id="localeCode">
		<input type="hidden" value='<%= session.getAttribute("currentUserId") %>' id="currentUserId">
		<input type="hidden" value='<c:out value="${role}"></c:out>' id="currentUserRole">
	</div>

	<script type="text/javascript" src="<spring:url value='js/lib/angular.min.js' />"></script>
	<script type="text/javascript" src="<spring:url value='js/lib/angular-route.min.js' />"></script>
	<script type="text/javascript" src="<spring:url value='js/lib/jquery-1.11.1.min.js' />"></script>
	<script type="text/javascript" src="<spring:url value='js/lib/ui-bootstrap-tpls-0.11.0.min.js' />"></script>
	<script type="text/javascript" src="<spring:url value='js/lib/bootstrap.min.js' />"></script>
	<script type="text/javascript" src="<spring:url value='js/lib/bootstrap-datepicker.js' />"></script>
	<script type="text/javascript" src="<spring:url value='js/lib/ngDialog.min.js' />"></script>

	<script type="text/javascript" src="<spring:url value='js/app.js' />"></script>
	<script type="text/javascript" src="<spring:url value='js/controllers/registerProfesorController.js' />"></script>
	<script type="text/javascript" src="<spring:url value='js/controllers/registerUserController.js' />"></script>
	<script type="text/javascript" src="<spring:url value='js/controllers/profesorDetailsController.js' />"></script>
	<script type="text/javascript" src="<spring:url value='js/controllers/bachelorMentoringController.js' />"></script>
	<script type="text/javascript" src="<spring:url value='js/controllers/masterMentoringController.js' />"></script>
	<script type="text/javascript" src="<spring:url value='js/controllers/specialisticMentoringController.js' />"></script>
	<script type="text/javascript" src="<spring:url value='js/controllers/doctorMentoringController.js' />"></script>
	<script type="text/javascript" src="<spring:url value='js/controllers/languageController.js' />"></script>
	<script type="text/javascript" src="<spring:url value='js/controllers/awardController.js' />"></script>
	<script type="text/javascript" src="<spring:url value='js/controllers/scientificProfessionalOrgMemController.js' />"></script>
	<script type="text/javascript" src="<spring:url value='js/controllers/projectExperienceController.js' />"></script>
	<script type="text/javascript" src="<spring:url value='js/controllers/professorPublicationsController.js' />"></script>
	<script type="text/javascript" src="<spring:url value='js/controllers/internationalPublicationsController.js' />"></script>
	<script type="text/javascript" src="<spring:url value='js/controllers/workExperienceController.js' />"></script>
	<script type="text/javascript" src="<spring:url value='js/controllers/professorBachelorStudiesController.js' />"></script>
	<script type="text/javascript" src="<spring:url value='js/controllers/professorMasterStudiesController.js' />"></script>
	<script type="text/javascript" src="<spring:url value='js/controllers/professorSpecialisticStudiesController.js' />"></script>
	<script type="text/javascript" src="<spring:url value='js/controllers/professorDoctorStudiesController.js' />"></script>
	<script type="text/javascript" src="<spring:url value='js/controllers/professorSpecializationAbroadController.js' />"></script>
	<script type="text/javascript" src="<spring:url value='js/controllers/teachingExperienceController.js' />"></script>
	<script type="text/javascript" src="<spring:url value='js/controllers/seminarOrTeachingAbroadController.js' />"></script>
	<script type="text/javascript" src="<spring:url value='js/controllers/professorSubjectsSurveysController.js' />"></script>
	<script type="text/javascript" src="<spring:url value='js/controllers/facultyOrUniversityAuthoritiesWorkController.js' />"></script>
	<script type="text/javascript" src="<spring:url value='js/controllers/professionalOrganizationConductingController.js' />"></script>
	<script type="text/javascript" src="<spring:url value='js/controllers/committeesAndLegislativeBodiesWorkController.js' />"></script>
	<script type="text/javascript" src="<spring:url value='js/controllers/meetingsConferencesAndEventsConductingController.js' />"></script>
	<script type="text/javascript" src="<spring:url value='js/controllers/scientificJournalReviewController.js' />"></script>
	<script type="text/javascript" src="<spring:url value='js/controllers/professorMenuController.js' />"></script>
	<script type="text/javascript" src="<spring:url value='js/service/pctService.js' />"></script>
	<c:if test="${role == 'ROLE_ADMIN'}">
		<script type="text/javascript" src="<spring:url value='admin/controllers/studentsController.js' />"></script>
		<script type="text/javascript" src="<spring:url value='admin/controllers/allProfessorsController.js' />"></script>
		<script type="text/javascript" src="<spring:url value='admin/controllers/institutionsController.js' />"></script>
		<script type="text/javascript" src="<spring:url value='admin/controllers/subjectsController.js' />"></script>
		<script type="text/javascript" src="<spring:url value='admin/controllers/adminMenuController.js' />"></script>
		<script type="text/javascript" src="<spring:url value='admin/controllers/projectsController.js' />"></script>
		<script type="text/javascript" src="<spring:url value='admin/controllers/adminDetailsController.js' />"></script>
		<script type="text/javascript" src="<spring:url value='admin/controllers/seminarsController.js' />"></script>
		<script type="text/javascript" src="<spring:url value='admin/controllers/contestController.js' />"></script>
	</c:if>
	
</body>
</html>