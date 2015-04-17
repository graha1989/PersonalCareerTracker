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

  $scope.deleteWorkExperience = function(id, index) {
    PctService.deleteWorkExperience(id, function(data) {
      if (angular.isObject(data)) {
        $scope.errorStatus = data.status;
      } else {
        $scope.successStatus = "Successfully deleted work experience.";
        $scope.workExperiences.splice(index, 1);
        $scope.loadWorkExperiences($routeParams.professorId);
      }
    });
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

  $scope.createNewWorkExperience = function() {
    $modal.open({
      templateUrl: 'createNewWorkExperiencePopup.html',
      controller: createNewWorkExperienceController,
      resolve: {
        workExperiences: function() {
          return $scope.workExperiences;
        }
      }
    });
  };

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

var createNewWorkExperienceController = function($scope, $modalInstance,
        $routeParams, $http, $route, $templateCache, workExperiences,
        PctService) {

  $scope.workExperience = {};
  $scope.allInstitutionTypes = [];
  $scope.selectedInstitution = [];
  $scope.isExistingInstitution = false;

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

  $scope.getInstitutionsIds = function(selectedWorkExperiences) {
    var institutionsIdsArray = [];
    for (var i = 0; i < selectedWorkExperiences.length; i++) {
      institutionsIdsArray.push(selectedWorkExperiences[i].institutionId);
    }
    return institutionsIdsArray;
  };

  $scope.init = function() {
    $scope.loadAllInstitutionTypes();
    $scope.status = $routeParams.status;
    $scope.loadResources();
  };

  $scope.init();

  $scope.getInstitutions = function(val) {
    var workExperienceIdsList = $scope.getInstitutionsIds(workExperiences);

    return PctService.findInstutionsStartsWith(val, workExperienceIdsList)
            .then(function(response) {
              var institutions = [];
              for (var i = 0; i < response.length; i++) {
                institutions.push(response[i]);
              }
              return institutions;
            });
  };

  $scope.onSelectInstitution = function() {
    $scope.isExistingInstitution = true;
    $scope.workExperience.institutionType = $scope.selectedInstitution.institutionType;
    $scope.workExperience.institutionName = $scope.selectedInstitution.name;
    $scope.workExperience.institutionCity = $scope.selectedInstitution.city;
    $scope.workExperience.institutionCountry = $scope.selectedInstitution.country;
    $scope.workExperience.institutionId = $scope.selectedInstitution.id;
  };

  $scope.saveWorkExperience = function() {
    if (!$scope.isExistingInstitution) {
      $scope.workExperience.institutionName = $scope.selectedInstitution;
    }
    $scope.workExperience.professorId = $routeParams.professorId;
    $http({
      method: 'POST',
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

  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };

  $scope.validateForm = function() {
    if ((($scope.workExperience.institutionName != null && $scope.workExperience.institutionName != '') || ($scope.selectedInstitution != null && $scope.selectedInstitution != ''))
            && $scope.workExperience.institutionType != null
            && $scope.workExperience.institutionType != ''
            && $scope.workExperience.institutionCity != null
            && $scope.workExperience.institutionCity != ''
            && $scope.workExperience.institutionCountry != null
            && $scope.workExperience.institutionCountry != ''
            && $scope.workExperience.workStartDate != null
            && $scope.workExperience.workStartDate != ''
            && $scope.workExperience.workEndDate != null
            && $scope.workExperience.workEndDate != ''
            && $scope.workExperience.title != null
            && $scope.workExperience.title != '') {
      return true;
    } else {
      return false;
    }
  };

};