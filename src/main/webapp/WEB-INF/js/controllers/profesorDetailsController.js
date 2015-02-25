app.controller("ProfesorDetailsController", function($scope, $routeParams,
		$http, $location, $modal, PctService) {

	$scope.profesor;
	$scope.id;
	$scope.resources = {};
	$scope.errorMessages = {};

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

	/* Date picker functions */
	$scope.open = function($event) {
		$event.preventDefault();
		$event.stopPropagation();

		$scope.opened = true;
	};

	$scope.loadProfesorDetails = function(id) {
		$http({
			method : 'GET',
			url : "api/professor/loadProfesorDetails?id=" + id,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data, status) {
			if (angular.isObject(data)) {
				$scope.profesor = data;
			}
		}).error(function(data, status) {
		});
	};

	$scope.init = function() {
		$scope.id = $routeParams.id;
		$scope.profesor = $scope.loadProfesorDetails($scope.id);
		$scope.loadResources();
	};

	$scope.init();
	
	$scope.editProfesor = function(id) {
		$modal.open({
			templateUrl : 'editProfesorPopup.html',
			controller : editProfesorController,
			resolve : {
				profesorId : function() {
					return id;
				}
			}
		});
	};

});

var editProfesorController = function($scope, $modalInstance, $routeParams,
		$http, $route, profesorId, PctService) {

	$scope.loadProfesor = function(id) {
		PctService.loadProfesor(id, function(data) {
			if (angular.isObject(data)) {
				$scope.profesor = data;
				$scope.noResultsFound = false;
			} else {
				$scope.noResultsFound = true;
			}
		});
	};

	$scope.init = function() {
		$scope.loadProfesor(profesorId);
		$scope.master = angular.copy($scope.profesor);
		$scope.status = $routeParams.status;
	};

	$scope.init();

	$scope.isUnchanged = function(profesor) {
		return angular.equals(profesor, $scope.master);
	};

	$scope.updateProfesor = function() {
		$http({
			method : 'PUT',
			url : "api/professor/persistProfessor",
			data : $scope.profesor,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data, status) {
			$("html, body").animate({
				scrollTop : 0
			}, "slow");
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

};