<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<div class="menu" ng-controller="AdminMenuController">
	<div class="navbar navbar-default">
		<div class="container-fluid">
			<ul class="nav nav-pills nav-justified">
				<li><a href="" ng-click="home()"><span
						class="glyphicon glyphicon-home"></span></a></li>
				<li><a href="#/showAllProfessors">{{resources.adminMenuLabels.allProfessors}}</a></li>
				<li><a href="#/showAllStudents">{{resources.adminMenuLabels.allStudents}}</a></li>
				<li><a href="#/showAllInstitutions">{{resources.adminMenuLabels.allInstitutions}}</a></li>
				<li><a href="#/showAllSubjects">{{resources.adminMenuLabels.allSubjects}}</a></li>
				<li><a href="#/showAllProjects">{{resources.adminMenuLabels.allProjects}}</a></li>
			</ul>
		</div>
	</div>
</div>
