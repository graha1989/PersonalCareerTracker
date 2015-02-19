var app = angular.module('PersonalCareerTracker',['ngRoute', 'ui.bootstrap'])
	.config(['$routeProvider', '$httpProvider', function($routeProvider, $httpProvider){
		$routeProvider
			.when('/adminHome', {
				templateUrl: 'pages/adminHome.html',
				controller: 'AdminHomeController'
			})
			.when('/showAllStudents', {
				templateUrl: 'pages/showAllStudents.html',
				controller: 'ShowStudentsController'
			})
			.otherwise({
				redirectTo: '/showAllStudents'
			});
	}]);