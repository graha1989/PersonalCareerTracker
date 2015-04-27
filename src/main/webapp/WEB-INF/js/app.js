var app = angular.module('PersonalCareerTracker',['ngRoute', 'ui.bootstrap'])
	.config(['$routeProvider', '$httpProvider', function($routeProvider, $httpProvider){
		$routeProvider
			.when('/adminHome', {
				templateUrl: 'pages/adminHome.html',
				controller: 'HomeController'
			})
			.when('/showAllStudents', {
				templateUrl: 'pages/showAllStudents.html',
				controller: 'StudentsController'
			})
			.when('/registerProfesor', {
				templateUrl: 'pages/registerProfesor.html',
				controller: 'RegisterProfesorController'
			})
			.when('/profesorDetails/id/:id', {
				templateUrl: 'pages/profesorDetails.html?id=:id',
				controller: 'ProfesorDetailsController'
			})
			.when('/bachelorMentoring/mentorId/:mentorId/thesisTypeId/:thesisTypeId', {
				templateUrl: 'pages/bachelorMentoring.html?mentorId=:mentorId&thesisTypeId=:thesisTypeId',
				controller: 'BachelorMentoringController'
			})
			.when('/masterMentoring/mentorId/:mentorId/thesisTypeId/:thesisTypeId', {
				templateUrl: 'pages/masterMentoring.html?mentorId=:mentorId&thesisTypeId=:thesisTypeId',
				controller: 'MasterMentoringController'
			})
			.when('/specialisticMentoring/mentorId/:mentorId/thesisTypeId/:thesisTypeId', {
				templateUrl: 'pages/specialisticMentoring.html?mentorId=:mentorId&thesisTypeId=:thesisTypeId',
				controller: 'SpecialisticMentoringController'
			})
			.when('/doctorMentoring/mentorId/:mentorId/thesisTypeId/:thesisTypeId', {
				templateUrl: 'pages/doctorMentoring.html?mentorId=:mentorId&thesisTypeId=:thesisTypeId',
				controller: 'DoctorMentoringController'
			})
			.when('/languageExperience/mentorId/:mentorId', {
				templateUrl: 'pages/languageExperience.html?mentorId=:mentorId',
				controller: 'LanguageController'
			})
			.when('/awards/mentorId/:mentorId', {
				templateUrl: 'pages/awards.html?mentorId=:mentorId',
				controller: 'AwardController'
			})
			.when('/projectExperiences/mentorId/:mentorId', {
        templateUrl: 'pages/projectExperiences.html?mentorId=:mentorId',
        controller: 'ProjectController'
      })
      .when('/publications/professorPublications/professorId/:professorId', {
        templateUrl: 'pages/professorPublications.html?professorId=:professorId',
        controller: 'ProfessorPublicationsController'
      })
      .when('/publications/internationalPublications/professorId/:professorId', {
        templateUrl: 'pages/internationalPublications.html?professorId=:professorId',
        controller: 'InternationalPublicationsController'
      })
      .when('/workExperiences/professorId/:professorId', {
        templateUrl: 'pages/workExperience.html?professorId=:professorId',
        controller: 'WorkExperienceController'
      })
      .when('/studies/professorBachelorStudies/professorId/:professorId/thesisTypeId/:thesisTypeId', {
        templateUrl: 'pages/professorBachelorStudies.html?professorId=:professorId&thesisTypeId=:thesisTypeId',
        controller: 'ProfessorBachelorStudiesController'
      })
      .when('/studies/professorMasterStudies/professorId/:professorId/thesisTypeId/:thesisTypeId', {
        templateUrl: 'pages/professorMasterStudies.html?professorId=:professorId&thesisTypeId=:thesisTypeId',
        controller: 'ProfessorMasterStudiesController'
      })
      .when('/studies/professorSpecialisticStudies/professorId/:professorId/thesisTypeId/:thesisTypeId', {
        templateUrl: 'pages/professorSpecialisticStudies.html?professorId=:professorId&thesisTypeId=:thesisTypeId',
        controller: 'ProfessorSpecialisticStudiesController'
      })
      .when('/studies/professorDoctorStudies/professorId/:professorId/thesisTypeId/:thesisTypeId', {
        templateUrl: 'pages/professorDoctorStudies.html?professorId=:professorId&thesisTypeId=:thesisTypeId',
        controller: 'ProfessorDoctorStudiesController'
      })
			.otherwise({
				redirectTo: '/showAllStudents'
			});
	}]);