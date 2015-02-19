app.controller("ShowStudentsController", function($scope, $routeParams, $http, $location, PctService, $q){
	
	$scope.students = [];
	$scope.noResultsFound = true;
	
	/* Variable used for hiding unimplemented fields */
	$scope.dontShowDataForFieldsThatAreNotImplemented = false;
	
	/* Load data for details page */
	 $scope.loadStudents = function() {
		 PctService.loadStudents($routeParams, function(data){
			 if(angular.isObject(data)) {
				 $scope.students = data;
				 $scope.noResultsFound = false;
			 } else{
				 $scope.noResultsFound = true;
			 }
		 });
	 };
	 
	/**
	 * Initializes user.
	 */
	$scope.init = function(){
		$scope.students = [];
		$scope.loadStudents();
	};
	
	$scope.init();
	
	$scope.goBack = function() {
	    window.history.back();
	};
	
});