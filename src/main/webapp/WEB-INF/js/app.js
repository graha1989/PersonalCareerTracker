var app = angular.module('PersonalCareerTracker',['ngRoute', 'ui.bootstrap'])
	.config(['$routeProvider', '$httpProvider', function($routeProvider, $httpProvider){
		$routeProvider
			.when('/adminDetails/id/:id', {
				templateUrl: 'admin/pages/adminDetails.html?id=:id',
				controller: 'AdminDetailsController'
			})
			.when('/adminDetails', {
        templateUrl: 'admin/pages/adminDetails.html',
        controller: 'AdminDetailsController'
      })
			.when('/showAllStudents', {
				templateUrl: 'admin/pages/showAllStudents.html',
				controller: 'StudentsController'
			})
			.when('/registerProfesor', {
				templateUrl: 'pages/registerProfesor.html',
				controller: 'RegisterProfesorController'
			})
			.when('/registerUser', {
        templateUrl: 'pages/registerUser.html',
        controller: 'RegisterUserController'
      })
			.when('/professorDetails/id/:id', {
				templateUrl: 'pages/professorDetails.html?id=:id',
				controller: 'ProfesorDetailsController'
			})
			.when('/professorDetails', {
        templateUrl: 'pages/professorDetails.html',
        controller: 'ProfesorDetailsController'
      })
			.when('/bachelorMentoring/mentorId/:mentorId/thesisTypeId/:thesisTypeId', {
        templateUrl: 'pages/bachelorMentoring.html?mentorId=:mentorId&thesisTypeId=:thesisTypeId',
        controller: 'BachelorMentoringController'
      })
			.when('/bachelorMentoring/thesisTypeId/:thesisTypeId', {
        templateUrl: 'pages/bachelorMentoring.html?thesisTypeId=:thesisTypeId',
        controller: 'BachelorMentoringController'
      })
      .when('/masterMentoring/mentorId/:mentorId/thesisTypeId/:thesisTypeId', {
        templateUrl: 'pages/masterMentoring.html?mentorId=:mentorId&thesisTypeId=:thesisTypeId',
        controller: 'MasterMentoringController'
      })
      .when('/masterMentoring/thesisTypeId/:thesisTypeId', {
        templateUrl: 'pages/masterMentoring.html?thesisTypeId=:thesisTypeId',
        controller: 'MasterMentoringController'
      })
      .when('/specialisticMentoring/mentorId/:mentorId/thesisTypeId/:thesisTypeId', {
        templateUrl: 'pages/specialisticMentoring.html?mentorId=:mentorId&thesisTypeId=:thesisTypeId',
        controller: 'SpecialisticMentoringController'
      })
      .when('/specialisticMentoring/thesisTypeId/:thesisTypeId', {
        templateUrl: 'pages/specialisticMentoring.html?thesisTypeId=:thesisTypeId',
        controller: 'SpecialisticMentoringController'
      })
      .when('/doctorMentoring/mentorId/:mentorId/thesisTypeId/:thesisTypeId', {
        templateUrl: 'pages/doctorMentoring.html?mentorId=:mentorId&thesisTypeId=:thesisTypeId',
        controller: 'DoctorMentoringController'
      })
			.when('/doctorMentoring/thesisTypeId/:thesisTypeId', {
        templateUrl: 'pages/doctorMentoring.html?thesisTypeId=:thesisTypeId',
        controller: 'DoctorMentoringController'
      })
      .when('/languageExperience/professorId/:professorId', {
        templateUrl: 'pages/languageExperience.html?professorId=:professorId',
        controller: 'LanguageController'
      })
      .when('/languageExperience', {
        templateUrl: 'pages/languageExperience.html',
        controller: 'LanguageController'
      })
      .when('/awards/professorId/:professorId', {
        templateUrl: 'pages/awards.html?professorId=:professorId',
        controller: 'AwardController'
      })
			.when('/awards', {
        templateUrl: 'pages/awards.html',
        controller: 'AwardController'
      })
      .when('/projectExperiences/professorId/:professorId', {
        templateUrl: 'pages/projectExperiences.html?professorId=:professorId',
        controller: 'ProjectExperienceController'
      })
      .when('/projectExperiences', {
        templateUrl: 'pages/projectExperiences.html',
        controller: 'ProjectExperienceController'
      })
      .when('/publications/professorPublications/professorId/:professorId', {
        templateUrl: 'pages/professorPublications.html?professorId=:professorId',
        controller: 'ProfessorPublicationsController'
      })
      .when('/publications/professorPublications', {
        templateUrl: 'pages/professorPublications.html',
        controller: 'ProfessorPublicationsController'
      })
      .when('/publications/internationalPublications/professorId/:professorId', {
        templateUrl: 'pages/internationalPublications.html?professorId=:professorId',
        controller: 'InternationalPublicationsController'
      })
      .when('/publications/internationalPublications', {
        templateUrl: 'pages/internationalPublications.html',
        controller: 'InternationalPublicationsController'
      })
      .when('/workExperiences/professorId/:professorId', {
        templateUrl: 'pages/workExperience.html?professorId=:professorId',
        controller: 'WorkExperienceController'
      })
      .when('/workExperiences', {
        templateUrl: 'pages/workExperience.html',
        controller: 'WorkExperienceController'
      })
      .when('/studies/professorBachelorStudies/professorId/:professorId/thesisTypeId/:thesisTypeId', {
        templateUrl: 'pages/professorBachelorStudies.html?professorId=:professorId&thesisTypeId=:thesisTypeId',
        controller: 'ProfessorBachelorStudiesController'
      })
      .when('/studies/professorBachelorStudies/thesisTypeId/:thesisTypeId', {
        templateUrl: 'pages/professorBachelorStudies.html?thesisTypeId=:thesisTypeId',
        controller: 'ProfessorBachelorStudiesController'
      })
      .when('/studies/professorMasterStudies/professorId/:professorId/thesisTypeId/:thesisTypeId', {
        templateUrl: 'pages/professorMasterStudies.html?professorId=:professorId&thesisTypeId=:thesisTypeId',
        controller: 'ProfessorMasterStudiesController'
      })
      .when('/studies/professorMasterStudies/thesisTypeId/:thesisTypeId', {
        templateUrl: 'pages/professorMasterStudies.html?thesisTypeId=:thesisTypeId',
        controller: 'ProfessorMasterStudiesController'
      })
      .when('/studies/professorSpecialisticStudies/professorId/:professorId/thesisTypeId/:thesisTypeId', {
        templateUrl: 'pages/professorSpecialisticStudies.html?professorId=:professorId&thesisTypeId=:thesisTypeId',
        controller: 'ProfessorSpecialisticStudiesController'
      })
      .when('/studies/professorSpecialisticStudies/thesisTypeId/:thesisTypeId', {
        templateUrl: 'pages/professorSpecialisticStudies.html?thesisTypeId=:thesisTypeId',
        controller: 'ProfessorSpecialisticStudiesController'
      })
      .when('/studies/professorDoctorStudies/professorId/:professorId/thesisTypeId/:thesisTypeId', {
        templateUrl: 'pages/professorDoctorStudies.html?professorId=:professorId&thesisTypeId=:thesisTypeId',
        controller: 'ProfessorDoctorStudiesController'
      })
      .when('/studies/professorDoctorStudies/thesisTypeId/:thesisTypeId', {
        templateUrl: 'pages/professorDoctorStudies.html?thesisTypeId=:thesisTypeId',
        controller: 'ProfessorDoctorStudiesController'
      })
      .when('/specializations/professorSpecializationsAbroad/professorId/:professorId', {
        templateUrl: 'pages/professorSpecializationAbroad.html?professorId=:professorId',
        controller: 'ProfessorSpecializationAbroadController'
      })
      .when('/specializations/professorSpecializationsAbroad', {
        templateUrl: 'pages/professorSpecializationAbroad.html',
        controller: 'ProfessorSpecializationAbroadController'
      })
      .when('/teachingExperience/professorId/:professorId', {
        templateUrl: 'pages/teachingExperience.html?professorId=:professorId',
        controller: 'TeachingExperienceController'
      })
      .when('/teachingExperience', {
        templateUrl: 'pages/teachingExperience.html',
        controller: 'TeachingExperienceController'
      })
      .when('/seminarOrTeachingAbroad/professorId/:professorId', {
        templateUrl: 'pages/seminarOrTeachingAbroad.html?professorId=:professorId',
        controller: 'SeminarOrTeachingAbroadController'
      })
      .when('/seminarOrTeachingAbroad', {
        templateUrl: 'pages/seminarOrTeachingAbroad.html',
        controller: 'SeminarOrTeachingAbroadController'
      })
      .when('/showAllInstitutions', {
        templateUrl: 'admin/pages/showAllInstitutions.html',
        controller: 'InstitutionsController'
      })
      .when('/showAllProjects', {
        templateUrl: 'admin/pages/showAllProjects.html',
        controller: 'ProjectsController'
      })
      .when('/showAllSubjects', {
        templateUrl: 'admin/pages/showAllSubjects.html',
        controller: 'SubjectsController'
      })
      .when('/surveys/professorId/:professorId/subjectId/:subjectId', {
        templateUrl: 'pages/professorSubjectsSurveys.html?professorId=:professorId&subjectId=:subjectId',
        controller: 'ProfessorSubjectsSurveysController'
      })
      .when('/surveys/subjectId/:subjectId', {
        templateUrl: 'pages/professorSubjectsSurveys.html?subjectId=:subjectId',
        controller: 'ProfessorSubjectsSurveysController'
      })
      .when('/showAllProfessors', {
        templateUrl: 'admin/pages/showAllProfessors.html',
        controller: 'AllProfessorsController'
      })
			.otherwise({
				redirectTo: '/showAllStudents'
			});
	}]);