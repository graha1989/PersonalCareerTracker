app.controller("SpecialisticMentoringController", function($scope, $routeParams,
		$http, $location, $modal, PctService) {

	$scope.thesis = {};
	$scope.allSpecialisticThesis = [];
	$scope.resources = {};
	$scope.errorMessages = {};
	$scope.noResultsFound = true;

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

	$scope.loadThesis = function(mentorId, thesisTypeId) {
		PctService.loadThesis(mentorId, thesisTypeId, function(data) {
			if (angular.isObject(data) && data.length > 0) {
				$scope.allSpecialisticThesis = data;
				$scope.noResultsFound = false;
			} else {
				$scope.noResultsFound = true;
			}
		});
	};

	$scope.init = function() {
		$scope.thesis = {};
		$scope.loadThesis($routeParams.mentorId, $routeParams.thesisTypeId);
		$scope.loadResources();
	};

	$scope.init();

	$scope.goBack = function() {
		window.history.back();
	};

	$scope.deleteThesis = function(id) {
		PctService.deleteThesis(id, function(data) {
			if (angular.isObject(data)) {
				$scope.errorStatus = data.status;
			} else {
				$scope.successStatus = "Successfully deleted thesis.";
				$scope.loadThesis($routeParams.mentorId,
						$routeParams.thesisTypeId);
			}
		});
	};

	$scope.editThesis = function(id) {
		$modal.open({
			templateUrl : 'editSpecialisticThesisPopup.html',
			controller : editSpecialisticThesisController,
			resolve : {
				thesisId : function() {
					return id;
				}
			}
		});
	};

	$scope.createNewSpecialisticThesis = function() {
		$modal.open({
			templateUrl : 'createNewSpecialisticThesisPopup.html',
			controller : createNewSpecialisticThesisController
		});
	};

});

var editSpecialisticThesisController = function($scope, $modalInstance, $routeParams,
		$http, $route, thesisId, PctService) {

	$scope.thesis = {};
	$scope.thesis.mentorId;
	$scope.thesis.thesisTypeId;
	$scope.master = {};

	$scope.student = {};
	$scope.commissionPresident = {};
	$scope.commissionMember = {};

	$scope.opened = false;

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

		$scope.opened = true;
	};

	$scope.loadSelectedThesis = function(id) {
		PctService.loadSelectedThesis(id, function(data) {
			if (angular.isObject(data)) {
				$scope.thesis = data;
				$scope.master = angular.copy($scope.thesis);
				$scope.noResultsFound = false;
			} else {
				$scope.noResultsFound = true;
			}
		});
	};

	$scope.init = function() {
		$scope.loadSelectedThesis(thesisId);
		$scope.status = $routeParams.status;
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
		var inputLabel = this.form.inputCommissionPresident;

		inputLabel.$setValidity("commissionPresidentInvalid", true);
		return PctService.findProfessorsStartsWith(val,
				$scope.thesis.commissionMember.id, $routeParams.mentorId).then(
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
				$scope.thesis.commissionPresident.id, $routeParams.mentorId)
				.then(
						function(response) {
							var professors = [];
							for (var i = 0; i < response.length; i++) {
								professors.push(response[i]);
								inputLabel.$setValidity(
										"commissionMemberInvalid", true);
							}
							if (val.length >= 3 && professors.length == 0) {
								inputLabel.$setValidity(
										"commissionMemberInvalid", false);
							}
							return professors;
						});
	};

	$scope.saveThesis = function() {
		$scope.thesis.mentorId = $routeParams.mentorId;
		$scope.thesis.thesisTypeId = $routeParams.thesisTypeId;
		$scope.thesis.studentId = $scope.student.id;
		$scope.thesis.commissionPresidentId = $scope.commissionPresident.id;
		$scope.thesis.commissionMemberId = $scope.commissionMember.id;

		$http({
			method : 'PUT',
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

	$scope.isUnchanged = function(thesis) {
		thesis.dateOfGraduation = new Date(thesis.dateOfGraduation).getTime();
		return angular.equals(thesis, $scope.master);
	};

	$scope.cancel = function() {
		$modalInstance.dismiss('cancel');
	};

};

var createNewSpecialisticThesisController = function($scope, $modalInstance, $routeParams,
		$http, $route, PctService) {

	$scope.thesis = {};
	$scope.thesis.mentorId;
	$scope.thesis.thesisTypeId;
	$scope.master = {};

	$scope.student = [];
	$scope.commissionPresident = [];
	$scope.commissionMember = [];

	$scope.opened = false;

	$scope.patterns = {
		onlyLetters : /^[a-zA-ZčČćĆšŠđĐžŽ ]*$/,
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

	/*
	 * $scope.loadProfessor = function(id) { PctService.loadProfesor(id,
	 * function(data) { if (angular.isObject(data)) { $scope.thesis.mentor =
	 * data; $scope.noResultsFound = false; } else { $scope.noResultsFound =
	 * true; } }); };
	 */

	/*
	 * $scope.loadThesisType = function(id) { PctService.loadThesisType(id,
	 * function(data) { if (angular.isObject(data)) { $scope.thesis.thesisType =
	 * data; $scope.noResultsFound = false; } else { $scope.noResultsFound =
	 * true; } }); };
	 */

	$scope.init = function() {
		$scope.status = $routeParams.status;
		$scope.status = $routeParams.status;
		/*
		 * $scope.loadProfessor($routeParams.mentorId);
		 * $scope.loadThesisType($routeParams.thesisTypeId);
		 */
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
		var inputLabel = this.form.inputCommissionPresident;

		inputLabel.$setValidity("commissionPresidentInvalid", true);
		return PctService.findProfessorsStartsWith(val,
				$scope.thesis.commissionMember.id, $routeParams.mentorId).then(
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
				$scope.thesis.commissionPresident.id, $routeParams.mentorId)
				.then(
						function(response) {
							var professors = [];
							for (var i = 0; i < response.length; i++) {
								professors.push(response[i]);
								inputLabel.$setValidity(
										"commissionMemberInvalid", true);
							}
							if (val.length >= 3 && professors.length == 0) {
								inputLabel.$setValidity(
										"commissionMemberInvalid", false);
							}
							return professors;
						});
	};

	$scope.saveNewThesis = function() {
		$scope.thesis.mentorId = $routeParams.mentorId;
		$scope.thesis.thesisTypeId = $routeParams.thesisTypeId;
		$scope.thesis.studentId = $scope.student.id;
		$scope.thesis.commissionPresidentId = $scope.commissionPresident.id;
		$scope.thesis.commissionMemberId = $scope.commissionMember.id;

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

	$scope.cancel = function() {
		$modalInstance.dismiss("cancel");
	};

};