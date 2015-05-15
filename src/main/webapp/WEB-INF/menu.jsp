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
	ng-controller="MenuController">
	<div class="navbar navbar-default">
		<div class="container-fluid">
			<div class="collapse navbar-collapse">

				<ul class="nav navbar-nav">

					<li class="dropdown"><a
						href="#"
						class="dropdown-toggle"
						data-toggle="dropdown"
						role="button"
						aria-expanded="false">{{resources.professorMenuLabels.mentoring}}<span class="caret"></span></a>
						<ul
							class="dropdown-menu"
							role="menu">
							<li><a
								href="#"
								ng-click="openBachelorMentoring()">{{resources.professorMenuLabels.bachelorMentoring}}</a></li>
							<li><a
								href="#"
								onclick="openMasterMentoring()">{{resources.professorMenuLabels.masterMentoring}}</a></li>
							<li><a
								href="#"
								onclick="openSpecialisticMentoring()">{{resources.professorMenuLabels.specialisticMentoring}}</a></li>
							<li><a
								href="#"
								onclick="openDoctorMentoring()">{{resources.professorMenuLabels.doctorMentoring}}</a></li>
						</ul></li>

				</ul>
			</div>
		</div>
	</div>
</div>
