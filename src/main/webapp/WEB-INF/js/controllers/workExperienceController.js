app.controller("WorkExperienceController", function($scope, $routeParams,
        $http, $location, $modal, PctService) {

  $scope.workExperiences = [];
  $scope.workExperience = {};

  $scope.noResultsFound = true;
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

  $scope.loadWorkExperiences = function(professorId) {
    return PctService.loadWorkExperiences(professorId).then(function(response) {
      if (angular.isObject(response) && response.length > 0) {
        $scope.workExperiences = response;
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };

  $scope.init = function() {
    $scope.loadWorkExperiences($routeParams.professorId);
    $scope.loadResources();
  };

  $scope.init();

  $scope.goBack = function() {
    window.history.back();
  };

  $scope.editWorkExperience = function(id) {
    $modal.open({
      templateUrl: 'editWorkExperiencePopup.html',
      controller: editWorkExperiencePopupController,
      resolve: {
        workExperienceId: function() {
          return id;
        }
      }
    });
  };

  /*
   * $scope.deleteProfessorPublication = function(id, index) {
   * PctService.deleteProfessorPublication(id, function(data) { if
   * (angular.isObject(data) && data.length > 0) { $scope.errorStatus =
   * data.status; } else { $scope.successStatus = "Successfully deleted
   * professor publication."; $scope.publications.splice(index, 1);
   * $scope.loadProfessorsPublications($routeParams.professorId); } }); };
   * 
   * $scope.createNewProfessorPublication = function() { $modal.open({
   * templateUrl: 'createNewProfessorPublicationPopup.html', controller:
   * createNewProfessorPublicationController, }); };
   */
});

var editWorkExperiencePopupController = function($scope, $modalInstance,
        $routeParams, $http, $route, $templateCache, workExperienceId,
        PctService) {

  $scope.workExperience = {};
  $scope.master = {};
  $scope.allInstitutionTypes = [];

  $scope.patterns = {
    onlyLetters: /^[a-zA-ZčČćĆšŠđĐžŽ ]*$/,
    onlyNumbers: /^[0-9 ]*$/
  };

  /* Date picker functions for start date */
  $scope.openWorkStartDate = function($event) {
    $event.preventDefault();
    $event.stopPropagation();

    $scope.inputWorkStartDateOpened = true;
  };

  /* Date picker functions for end date */
  $scope.openWorkEndDate = function($event) {
    $event.preventDefault();
    $event.stopPropagation();

    $scope.inputWorkEndDateOpened = true;
  };

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

  $scope.loadAllInstitutionTypes = function() {
    PctService.loadAllInstitutionTypes($routeParams, function(data) {
      if (angular.isObject(data) && data.length > 0) {
        $scope.allInstitutionTypes = data;
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };

  $scope.loadSelectedWorkExperience = function(id) {
    PctService.loadSelectedWorkExperience(id, function(data) {
      if (angular.isObject(data)) {
        $scope.workExperience = data;
        $scope.master = angular.copy($scope.workExperience);
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };

  $scope.init = function() {
    $scope.loadAllInstitutionTypes();
    $scope.loadSelectedWorkExperience(workExperienceId);
    $scope.status = $routeParams.status;
    $scope.loadResources();
  };

  $scope.init();

  $scope.saveWorkExperience = function() {
    $http({
      method: 'PUT',
      url: "api/workExperiences",
      data: $scope.workExperience,
      headers: {
        'Content-Type': 'application/json'
      }
    }).success(function(data, status) {
      $("html, body").animate({
        scrollTop: 0
      }, "slow");
      $modalInstance.close();
      $route.reload();
    }).error(function(data, status) {
      if (angular.isObject(data.fieldErrors)) {
        $scope.fieldErrors = angular.fromJson(data.fieldErrors);
      }
      $scope.status = status;
      $("html, body").animate({
        scrollTop: 0
      }, "slow");
    });
  };

  $scope.isUnchanged = function(workExperience) {
    workExperience.workStartDate = new Date(workExperience.workStartDate)
            .getTime();
    workExperience.workEndDate = new Date(workExperience.workEndDate).getTime();
    return angular.equals(workExperience, $scope.master);
  };

  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };

};