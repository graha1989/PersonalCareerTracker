app.controller("ProfesorController", function($scope, $routeParams, $http,
		$location, $modal, PctService) {

	$scope.profesor;
	$scope.resources = {};
	$scope.errorMessages = {};
	
	$scope.loadResources = function() {
		var locale = document.getElementById('localeCode');
		$http.get('messages/profesorDetails_' + locale.value + '.json').success(
				function(response) {
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

	$scope.init = function() {
		$scope.status = $routeParams.status;
		$scope.profesor = {};
		$scope.loadResources();
	};

	$scope.init();

	$scope.updateProfesor = function() {
		$http({
			method : 'PUT',
			url : "registerProfesor",
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

	$scope.saveNewProfesor = function() {
		$http({
			method : 'POST',
			url : "registerProfesor",
			data : $scope.profesor,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data, status) {
			$("html, body").animate({
				scrollTop : 0
			}, "slow");
			$scope.profesor = data;
			$location.path('/profesorDetails');
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

});