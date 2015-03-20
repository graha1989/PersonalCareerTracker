app
		.controller(
				"AwardController",
				function($scope, $routeParams, $http, $route, PctService) {

					$scope.awards = [];
					$scope.award = {};
					$scope.noResultsFound = true;
					$scope.resources = {};
					$scope.errorMessages = {};

					$scope.loadResources = function() {
						var locale = document.getElementById('localeCode');
						$http.get(
								'messages/profesorDetails_' + locale.value
										+ '.json').success(function(response) {
							$scope.resources = angular.fromJson(response);
						});
						$http.get('messages/errors_' + locale.value + '.json')
								.success(
										function(response) {
											$scope.errorMessages = angular
													.fromJson(response);
										});
					};

					$scope.loadLanguages = function(mentorId) {
						return PctService
								.loadLanguages(mentorId)
								.then(
										function(response) {
											if (angular.isObject(response)
													&& response.length > 0) {
												$scope.professorLanguages = response;
												$scope.editMode = new Array(
														$scope.professorLanguages.length);
												for (var i = 0; i < $scope.professorLanguages.length; i++) {
													$scope.editMode.splice(i,
															1, false);
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
						}).error(
								function(data, status) {
									if (angular.isObject(data.fieldErrors)) {
										$scope.fieldErrors = angular
												.fromJson(data.fieldErrors);
									}
									$scope.status = status;
									$("html, body").animate({
										scrollTop : 0
									}, "slow");
								});
					};

					$scope.saveNewLanguageExperience = function() {
						$scope.language.languageId = $scope.selectedLanguage.id;
						$scope.language.languageName = $scope.selectedLanguage.languageName;
						$http({
							method : 'POST',
							url : "api/languages",
							data : $scope.language,
							headers : {
								'Content-Type' : 'application/json'
							}
						}).success(function(data, status) {
							$("html, body").animate({
								scrollTop : 0
							}, "slow");
							$route.reload();
						}).error(
								function(data, status) {
									$scope.error = "Greska!";
									if (angular.isObject(data.fieldErrors)) {
										$scope.fieldErrors = angular
												.fromJson(data.fieldErrors);
									}
									$scope.status = status;
									$("html, body").animate({
										scrollTop : 0
									}, "slow");
								});
					};

					$scope.deleteLanguageExperience = function(id) {
						PctService
								.deleteLanguageExperience(
										id,
										function(data) {
											if (angular.isObject(data)) {
												$scope.errorStatus = data.status;
											} else {
												$scope.successStatus = "Successfully deleted language experience.";
												$scope
														.loadLanguages($routeParams.mentorId);
											}
										});
					};

					$scope.getLanguagesIds = function(selectedLanguages) {
						var languagesIdsArray = [];
						for (var i = 0; i < selectedLanguages.length; i++) {
							languagesIdsArray
									.push(selectedLanguages[i].languageId);
						}
						return languagesIdsArray;
					};

					$scope.addNewLanguage = function() {
						$scope.addNewRow = true;
						$scope.getLanguages();
					};

					$scope.cancelAddingNew = function() {
						$scope.addNewRow = false;
					};

					$scope.getLanguages = function() {
						if ($scope.addNewRow) {
							$scope.languageExperienceIdsList = $scope
									.getLanguagesIds($scope.professorLanguages);
							return PctService.loadAllLanguages(
									$scope.languageExperienceIdsList).then(
									function(data) {
										if (angular.isObject(data)
												&& data.length > 0) {
											$scope.allLanguages = data;
											$scope.noResultsFound = false;
										} else {
											$scope.noResultsFound = true;
										}
									});
						}
					};

					$scope.isNewLanguageSelected = function() {
						return jQuery.isEmptyObject($scope.selectedLanguage);
					}

				});