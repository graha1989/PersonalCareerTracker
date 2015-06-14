<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div class="menu" ng-controller="ProfessorMenuController">
	<div class="navbar navbar-default">
		<div class="container-fluid">
			<div class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li><a href="" ng-click="home()"><span class="glyphicon glyphicon-home"></span></a></li>
					<li class="dropdown"><a href="" class="dropdown-toggle" role="button" aria-expanded="false">{{ resources.professorMenuLabels.studies }}<span class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="" ng-click="openProfessorBachelorStudies()">{{ resources.professorMenuLabels.studyTypes.bachelorStudies }}</a></li>
							<li><a href="" ng-click="openProfessorMasterStudies()">{{ resources.professorMenuLabels.studyTypes.masterStudies }}</a></li>
							<li><a href="" ng-click="openProfessorSpecialisticStudies()">{{ resources.professorMenuLabels.studyTypes.specialisticStudies }}</a></li>
							<li><a href="" ng-click="openProfessorDoctorStudies()">{{ resources.professorMenuLabels.studyTypes.doctorStudies }}</a></li>
							<li><a href="" ng-click="openProfessorSpecializationAbroad()">{{ resources.professorMenuLabels.studyTypes.specializationAbroad }}</a></li>
						</ul></li>
					<li class="dropdown"><a href="" class="dropdown-toggle" role="button" aria-expanded="false">{{ resources.professorMenuLabels.mentoring }}<span class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="" ng-click="openBachelorMentoring()">{{ resources.professorMenuLabels.mentoringTypes.bachelorMentoring }}</a></li>
							<li><a href="" ng-click="openMasterMentoring()">{{ resources.professorMenuLabels.mentoringTypes.masterMentoring }}</a></li>
							<li><a href="" ng-click="openSpecialisticMentoring()">{{ resources.professorMenuLabels.mentoringTypes.specialisticMentoring }}</a></li>
							<li><a href="" ng-click="openDoctorMentoring()">{{ resources.professorMenuLabels.mentoringTypes.doctorMentoring }}</a></li>
						</ul></li>
					<li><a href="" ng-click="openProfessorAwards()">{{ resources.professorMenuLabels.awards }}</a></li>
					<li><a href="" ng-click="openProfessorLanguageExperiences()">{{ resources.professorMenuLabels.languageExperiences }}</a></li>
					<li class="dropdown"><a href="" class="dropdown-toggle" role="button" aria-expanded="false">{{ resources.professorMenuLabels.publications }}<span class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="" tooltip-placement="bottom" tooltip="{{ resources.professorMenuLabels.publicationTypes.personalPublicationsTooltip }}" ng-click="openProfessorPublications()">{{
									resources.professorMenuLabels.publicationTypes.personalPublications }}</a></li>
							<li><a href="" tooltip-placement="bottom" tooltip="{{ resources.professorMenuLabels.publicationTypes.internationalPublicationsTooltip }}" ng-click="openInternationalPublications()">{{
									resources.professorMenuLabels.publicationTypes.internationalPublications }}</a></li>
						</ul></li>
					<li><a href="" ng-click="openProfessorWorkExperiences()">{{ resources.professorMenuLabels.workExperiences }}</a></li>
					<li><a href="" ng-click="openProfessorTeachingExperiences()">{{ resources.professorMenuLabels.teachingExperiences }}</a></li>
					<li><a href="" ng-click="openProfessorProjectExperiences()">{{ resources.professorMenuLabels.projectExperiences }}</a></li>
				</ul>
			</div>
		</div>
	</div>
</div>
