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
			.otherwise({
				redirectTo: '/showAllStudents'
			});
	}]);