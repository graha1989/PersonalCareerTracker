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

});