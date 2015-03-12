app.controller("LanguageController", function($scope, $routeParams, $http,
		$route, PctService) {

	$scope.languages = [];
	$scope.language = {};
	$scope.master = {};
	$scope.noResultsFound = true;
	$scope.resources = {};
	$scope.errorMessages = {};

	$scope.editMode = [];
	$scope.addNewRow = false;

	$scope.patterns = {
		onlyLetters : /^[a-zA-ZčČćĆšŠđĐžŽ ]*$/,
		onlyNumbers : /^[0-9 ]*$/
	};

	/* Variable used for hiding unimplemented fields */
	$scope.dontShowDataForFieldsThatAreNotImplemented = false;

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

	$scope.loadLanguages = function(mentorId) {
		return PctService.loadLanguages(mentorId).then(function(response) {
			if (angular.isObject(response) && response.length > 0) {
				$scope.languages = response;
				$scope.editMode = new Array($scope.languages.length);
				for (var i = 0; i < $scope.languages.length; i++) {
					$scope.editMode.splice(i, 1, false);
				};
				$scope.noResultsFound = false;
			} else {
				$scope.editMode = [0];
				$scope.noResultsFound = true;
			}
		});
	};

	$scope.init = function() {
		$scope.loadLanguages($routeParams.mentorId);
		$scope.loadResources();
	};

	$scope.init();

	$scope.goBack = function() {
		window.history.back();
	};

	$scope.makeEditable = function(index) {
		$scope.editMode.splice(index, 1, true);
	};

	$scope.editLanguageExperience = function(language, index) {
		$http({
			method : 'PUT',
			url : "api/languages",
			data : language,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data, status) {
			$("html, body").animate({
				scrollTop : 0
			}, "slow");
			$scope.editMode.splice(index, 1, false);
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
	
	$scope.addNewLanguage = function() {
		$scope.addNewRow = true;
	};

});