app.controller("BachelorMentoringController", function($scope, $routeParams,
		$http, $location, $modal, PctService) {

	$scope.thesis;
	$scope.allBachelorThesis;
	$scope.resources = {};
	$scope.errorMessages = {};
	$scope.noResultsFound = true;

	$scope.patterns = {
		onlyLetters : /^[a-zA-Z ]*$/,
		onlyNumbers : /^[0-9 ]*$/
	};

	$scope.loadResources = function() {
		var locale = document.getElementById('localeCode');
		$http.get('messages/profesorDetails_' + locale.value + '.json')
				.success(function(response) {
					$scope.resources = angular.fromJson(response);
				});
		$http.get('messages/errors_' + locale.value + '.json').success(
				function(response) {
					$scope.errorMessages = angular.fromJson(response);
				});
	};

	$scope.loadBachelorThesis = function(id) {
		PctService.loadBachelorThesis(id, function(data) {
			if (angular.isObject(data)) {
				$scope.allBachelorThesis = data;
				$scope.noResultsFound = false;
			} else {
				$scope.noResultsFound = true;
			}
		});
	};

	$scope.init = function() {
		$scope.thesis = {};
		$scope.allBachelorThesis = [];
		$scope.loadBachelorThesis($routeParams.id);
		$scope.loadResources();
	};

	$scope.init();

	$scope.goBack = function() {
		window.history.back();
	};

	$scope.createNewBachelorThesis = function() {
		$modal.open({
			templateUrl : 'createNewBachelorThesisPopup.html',
			controller : createNewThesisController
		});
	};

});

var createNewThesisController = function($scope, $modalInstance, $routeParams,
		$http, $route, PctService) {

	$scope.status;
	$scope.thesis;
	$scope.student;
	$scope.students;

	$scope.patterns = {
		onlyLetters : /^[a-zA-Z ]*$/,
		onlyNumbers : /^[0-9 ]*$/
	};

	$scope.loadResources = function() {
		var locale = document.getElementById('localeCode');
		$http.get('messages/profesorDetails_' + locale.value + '.json')
				.success(function(response) {
					$scope.resources = angular.fromJson(response);
				});
		$http.get('messages/errors_' + locale.value + '.json').success(
				function(response) {
					$scope.errorMessages = angular.fromJson(response);
				});
	};

	$scope.loadStudents = function() {
		PctService.loadStudents($routeParams, function(data) {
			if (angular.isObject(data)) {
				$scope.students = data;
				$scope.noResultsFound = false;
			} else {
				$scope.noResultsFound = true;
			}
		});
	};

	$scope.init = function() {
		$scope.status = $routeParams.status;
		$scope.thesis = {};
		$scope.student = {};
		$scope.students = [];
		$scope.loadStudents();
		$scope.loadResources();
	};

	$scope.init();

	$scope.saveNewThesis = function() {
		$http({
			method : 'POST',
			url : "api/students",
			data : $scope.student,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data, status) {
			$("html, body").animate({
				scrollTop : 0
			}, "slow");
			$modalInstance.close();
			$route.reload();
		}).error(function(data, status) {
			if (angular.isObject(data.fieldErrors)) {
				$scope.fieldErrors = angular.fromJson(data.fieldErrors);
			}
			$scope.status = status;
			$("html, body").animate({
				scrollTop : 0
			}, "slow");
		});
	};

	$scope.cancel = function() {
		$modalInstance.dismiss('cancel');
	};

}