app.controller("ProfesorDetailsController", function($scope, $routeParams,
		$http, $location, $modal, PctService) {

	$scope.profesor = {};
	$scope.id;
	$scope.resources = {};
	$scope.errorMessages = {};
	$scope.thesisTypes = [];
	$scope.master = {};
	$scope.editMode = false;

	$scope.patterns = {
		onlyLetters : /^[a-zA-ZčČćĆšŠđĐžŽ ]*$/,
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
	
	$scope.loadThesisTypes = function() {
		$http({
			method : 'GET',
			url : "api/thesis/allThesisTypes",
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data, status) {
			if (angular.isObject(data)) {
				$scope.thesisTypes = data;
			}
		}).error(function(data, status) {
		});
	};
	
	$scope.init = function() {
		$scope.id = $routeParams.id;
		$scope.loadProfesorDetails($scope.id);
		$scope.loadThesisTypes();
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
	
	$scope.isUnchanged = function(profesor) {
		profesor.dateOfBirth = new Date(profesor.dateOfBirth).getTime();
		return angular.equals(profesor, $scope.master);
	};

	$scope.openMentoring = function(mentorId, type) {
		if (type.finalPaperTypeName == 'Bachelor teza') {
			$location.path('/bachelorMentoring/mentorId/' + mentorId + '/thesisTypeId/' + type.id);
		} else if (type.finalPaperTypeName == 'Master teza') {
			$location.path('/masterMentoring/mentorId/' + mentorId + '/thesisTypeId/' + type.id);
		} else if (type.finalPaperTypeName == 'Specijalistička teza') {
			$location.path('/specialisticMentoring/mentorId/' + mentorId + '/thesisTypeId/' + type.id);
		} else {
			$location.path('/doctorMentoring/mentorId/' + mentorId + '/thesisTypeId/' + type.id);
		}
	};

});