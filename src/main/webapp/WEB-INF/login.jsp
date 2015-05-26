<%@ page
	language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib
	prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib
	prefix="spring"
	uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html ng-app="Login">
<head>
<meta
	http-equiv="Content-Type"
	content="text/html; charset=UTF-8">
<title><spring:message code='project.name' /></title>
<!-- Styles -->
<link
	href="<spring:url value='resources/css/styles.css' />"
	rel="stylesheet">
<link
	href="<spring:url value='resources/css/bootstrap-theme.css' />"
	rel="stylesheet">
<link
	href="<spring:url value='resources/css/bootstrap.min.css' />"
	rel="stylesheet">
<link
	href="<spring:url value='resources/css/bootstrap-theme.min.css' />"
	rel="stylesheet">
</head>
<body>
	<div
		class="bg-success"
		ng-controller="LoginController"
		style="width: 600px; margin: 0 auto;">
		<form
			name="form"
			id="loginForm"
			method="post"
			action="j_spring_security_check"
			class="form-horizontal css-form ng-dirty ng-invalid ng-invalid-required mediators-search-form"
			novalidate>

			<c:if test="${param.error == '1' }">
				<div
					id="errors"
					class="alert-warning">
					<spring:message code="login.error.incorectUserNameOrPassword" />
				</div>
			</c:if>

			<div class="modal-header">
				<div class="row form-controll">
					<h3 class="modal-title col-sm-6">
						<spring:message code="login.login" />
					</h3>
					<div class="pull-right col-sm-2">
						<img
							class="img-responsive"
							src="resources/icons/pmfnovisad.png" />
					</div>
				</div>
			</div>
			<div class="modal-body">
				<!-- Username -->
				<div class="form-group">
					<label
						class="control-label col-sm-3"
						for="j_username"><spring:message code="login.username" /> <span class="required">*</span></label>
					<div class="col-sm-5">
						<input
							id="j_username"
							class="form-control"
							type="text"
							name="j_username"
							placeholder="<spring:message code="login.username" />"
							ng-model="user.userName"
							ng-maxlength="50"
							required />
						<div
							ng-show="form.j_username.$dirty && form.j_username.$invalid"
							class="registerUserFieldError">
							<span
								ng-show="form.j_username.$error.required"
								class="ng-binding"><spring:message code="error.required" /></span> <span
								ng-show="form.j_username.$error.maxlength"
								class="ng-binding"><spring:message code="error.max" /></span>
						</div>
					</div>
				</div>
				<!-- Password -->
				<div class="form-group">
					<label
						class="control-label col-sm-3"
						for="j_password"><spring:message code="login.password" /> <span class="required">*</span></label>
					<div class="col-sm-5">
						<input
							id="j_password"
							class="form-control"
							type="password"
							name="j_password"
							placeholder="<spring:message code="login.password" />"
							ng-model="user.password"
							ng-maxlength="50"
							required />
						<div
							ng-show="form.j_password.$dirty && form.j_password.$invalid"
							class="registerUserFieldError">
							<span
								ng-show="form.j_password.$error.required"
								class="ng-binding"><spring:message code="error.required" /></span> <span
								ng-show="form.j_password.$error.maxlength"
								class="ng-binding"><spring:message code="error.max" /></span>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button
					id="submit_button"
					class="btn btn-success pull-right btn-xs"
					type="submit"
					name="submit"
					ng-disabled="form.$invalid">
					<spring:message code="login.login" />
				</button>
				<a
					class="btn btn-link pull-left btn-xs"
					href="<spring:url value='/#/registerUser' />"><spring:message code="login.register" /></a>
			</div>
		</form>
	</div>
	<!-- Include -->
	<script
		type="text/javascript"
		src="<spring:url value='js/lib/angular.min.js' />"></script>
	<script
		type="text/javascript"
		src="<spring:url value='js/lib/angular-route.min.js' />"></script>
	<script
		type="text/javascript"
		src="<spring:url value='js/lib/jquery-1.11.1.min.js' />"></script>
	<script
		type="text/javascript"
		src="<spring:url value='js/lib/ui-bootstrap-tpls-0.11.0.min.js' />"></script>
	<script
		type="text/javascript"
		src="<spring:url value='js/lib/bootstrap.min.js' />"></script>
	<script
		type="text/javascript"
		src="<spring:url value='js/controllers/loginController.js' />"></script>
</body>
</html>