app.controller("ShowStudentsController", function($scope, $routeParams, $http,
		$location, $modal, PctService, $q) {

	$scope.students;
	$scope.student;
	$scope.master = {};
	$scope.noResultsFound = true;
	$scope.resources = {};
	$scope.errorMessages = {};

	/* Variable used for hiding unimplemented fields */
	$scope.dontShowDataForFieldsThatAreNotImplemented = false;

	$scope.loadResources = function() {
		var locale = document.getElementById('localeCode');
		$http.get('messages/studentDetails_' + locale.value + '.json').success(
				function(response) {
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
		$scope.students = [];
		$scope.loadStudents();
		$scope.loadResources();
	};

	$scope.init();

	$scope.goBack = function() {
		window.history.back();
	};

	$scope.deleteStudent = function(id) {
		PctService.deleteStudent(id, function(data) {
			if (angular.isObject(data)) {
				$scope.errorStatus = data.status;
			} else {
				$scope.successStatus = "Successfully deleted student.";
				$scope.loadStudents();
			}
		});
	};

	$scope.editStudent = function(id) {
		$modal.open({
			templateUrl : 'editStudentPopup.html',
			controller : editStudentController,
			resolve : {
				studentId : function() {
					return id;
				}
			}
		});
	};

	$scope.createNewStudent = function() {
		$modal.open({
			templateUrl : 'createNewStudentPopup.html',
			controller : createNewStudentController
		});
	};

});

var editStudentController = function($scope, $modalInstance, $routeParams,
		$http, $route, studentId, PctService) {

	$scope.loadSelectedStudent = function(id) {
		PctService.loadSelectedStudent(id, function(data) {
			if (angular.isObject(data)) {
				$scope.student = data;
				$scope.noResultsFound = false;
			} else {
				$scope.noResultsFound = true;
			}
		});
	};

	$scope.init = function() {
		$scope.loadSelectedStudent(studentId);
		$scope.master = angular.copy($scope.student);
		$scope.status = $routeParams.status;
	};

	$scope.init();

	$scope.isUnchanged = function(student) {
		return angular.equals(student, $scope.master);
	};

	$scope.saveStudent = function() {
		$http({
			method : 'PUT',
			url : "showAllStudents",
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

};

var createNewStudentController = function($scope, $modalInstance, $routeParams,
		$http, $route, PctService) {

	$scope.init = function() {
		$scope.status = $routeParams.status;
		$scope.student = {};
	};

	$scope.init();

	$scope.saveNewStudent = function() {
		$http({
			method : 'POST',
			url : "showAllStudents",
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

};