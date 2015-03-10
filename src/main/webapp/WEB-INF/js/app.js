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
			.otherwise({
				redirectTo: '/showAllStudents'
			});
	}]);