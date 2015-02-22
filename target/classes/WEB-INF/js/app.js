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
				controller: 'ProfesorController'
			})
			.when('/profesorDetails', {
				templateUrl: 'pages/profesorDetails.html',
				controller: 'ProfesorController'
			})
			.otherwise({
				redirectTo: '/showAllStudents'
			});
	}]);