<%@ page
	language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib
	prefix="spring"
	uri="http://www.springframework.org/tags"%>
<%@ taglib
	prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<div
	class="menu"
	ng-controller="AdminMenuController">
	<div class="navbar navbar-default">
		<div class="container-fluid">
			<div class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li><a href="" ng-click="home()">{{resources.adminMenuLabels.home}}</a></li>
					<li><a href="#/showAllProfessors">{{resources.adminMenuLabels.allProfessors}}</a></li>
					<li><a href="#/showAllStudents">{{resources.adminMenuLabels.allStudents}}</a></li>
					<li><a href="#/showAllInstitutions">{{resources.adminMenuLabels.allInstitutions}}</a></li>
					<li><a href="#/showAllSubjects">{{resources.adminMenuLabels.allSubjects}}</a></li>
				</ul>
			</div>
		</div>
	</div>
</div>
