app.controller("UserController", function($scope, $routeParams, $http,
		$location, $modal, PctService) {

	$scope.isProfessor = false;
	$scope.korisnik;

	/* Date picker functions */
	$scope.open = function($event) {
		$event.preventDefault();
		$event.stopPropagation();

		$scope.opened = true;
	};

	$scope.init = function() {
		$scope.status = $routeParams.status;
		$scope.korisnik = {};
	};

	$scope.init();

	$scope.updateUser = function() {
		$http({
			method : 'PUT',
			url : "registerUser",
			data : $scope.korisnik,
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

	$scope.saveNewUser = function() {
		$http({
			method : 'POST',
			url : "registerUser",
			data : $scope.korisnik,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data, status) {
			$("html, body").animate({
				scrollTop : 0
			}, "slow");
			$location.path("userDetails");
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