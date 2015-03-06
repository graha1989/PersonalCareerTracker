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
			if (angular.isObject(data) && data.length > 0) {
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

	$scope.status = {};
	$scope.thesis;
	$scope.resources = {};

	$scope.commissionPresident = {};
	$scope.commissionMember = {};
	$scope.student = {};

	$scope.patterns = {
		onlyLetters : /^[a-zA-Z ]*$/,
		onlyNumbers : /^[0-9 ]*$/
	};

	/* Load resources from .json properties file */
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

	$scope.init = function() {
		$scope.status = $routeParams.status;
		$scope.thesis = {};
		$scope.loadResources();
	};

	$scope.init();

	$scope.getStudents = function(val) {
		var inputLabel = this.form.inputStudent;

		inputLabel.$setValidity("studentInvalid", true);
		return PctService.findStudentStartsWith(val).then(function(response) {
			var students = [];
			for (var i = 0; i < response.length; i++) {
				students.push(response[i]);
				inputLabel.$setValidity("studentInvalid", true);
			}
			if (val.length >= 3 && students.length == 0) {
				inputLabel.$setValidity("studentInvalid", false);
			}
			return students;
		});
	};

	$scope.getCommissionPresident = function(val) {
		var inputLabel = this.form.inputCommissionPesident;

		inputLabel.$setValidity("commissionPresidentInvalid", true);
		return PctService.findProfessorsStartsWith(val,
				$scope.commissionMember.id, $routeParams.id).then(
				function(response) {
					var professors = [];
					for (var i = 0; i < response.length; i++) {
						professors.push(response[i]);
						inputLabel.$setValidity("commissionPresidentInvalid",
								true);
					}
					if (val.length >= 3 && professors.length == 0) {
						inputLabel.$setValidity("commissionPresidentInvalid",
								false);
					}
					return professors;
				});
	};

	$scope.getCommissionMember = function(val) {
		var inputLabel = this.form.inputCommissionMember;

		inputLabel.$setValidity("commissionMemberInvalid", true);
		return PctService.findProfessorsStartsWith(val,
				$scope.commissionPresident.id, $routeParams.id).then(
				function(response) {
					var professors = [];
					for (var i = 0; i < response.length; i++) {
						professors.push(response[i]);
						inputLabel
								.$setValidity("commissionMemberInvalid", true);
					}
					if (val.length >= 3 && professors.length == 0) {
						inputLabel.$setValidity("commissionMemberInvalid",
								false);
					}
					return professors;
				});
	};

	$scope.saveNewThesis = function() {
		$http({
			method : 'POST',
			url : "api/thesis",
			data : $scope.thesis,
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

	$scope.onSelectStudent = function($item) {
		$scope.$item = $item;
		$scope.student = $scope.$item;
	};

	$scope.onSelectCommissionPresident = function($item) {
		$scope.$item = $item;
		$scope.commissionPresident = $scope.$item;
	};

	$scope.onSelectCommissionMember = function($item) {
		$scope.$item = $item;
		$scope.commissionMember = $scope.$item;
	};

	$scope.cancel = function() {
		$modalInstance.dismiss("cancel");
	};
};