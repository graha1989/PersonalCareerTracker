app.controller("ProfesorDetailsController", function($scope, $routeParams,
		$http, $location, $modal, PctService) {

	$scope.profesor;
	$scope.id;
	$scope.resources = {};
	$scope.errorMessages = {};

	$scope.editMode = false;

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

		$scope.opened = $scope.editMode;
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
		$scope.editMode = true;
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
			$scope.editMode = false;
		}).error(function(data, status) {
			$scope.error = "Greska!";
			if (angular.isObject(data.fieldErrors)) {
				$scope.fieldErrors = angular.fromJson(data.fieldErrors);
			}
			$scope.status = status;
			$("html, body").animate({
				scrollTop : 0
			}, "slow");
		});
	};

	$scope.openBachelorMentoring = function(id) {
		$location.path('/bachelorMentoring/id/' + id);
	};

	$scope.openMasterMentoring = function(id) {
		$location.path('/masterMentoring/id/' + id);
	};

	$scope.openSpecialisticMentoring = function(id) {
		$location.path('/specialisticMentoring/id/' + id);
	};

	$scope.openDoctorMentoring = function(id) {
		$location.path('/doctorMentoring/id/' + id);
	};

});