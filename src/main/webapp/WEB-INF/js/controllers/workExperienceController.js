app.controller("WorkExperienceController", function($scope, $routeParams, $http, $location, $modal, PctService) {

  $scope.workExperiences = [];
  $scope.workExperience = {};

  $scope.noResultsFound = true;
  $scope.resources = {};
  $scope.errorMessages = {};

  $scope.isUser = false;
  $scope.isAdmin = false;
  $scope.professorId = '';

  $scope.loadResources = function() {
    var locale = document.getElementById('localeCode');
    $http.get('messages/profesorDetails_' + locale.value + '.json').success(function(response) {
      $scope.resources = angular.fromJson(response);
    });
    $http.get('messages/errors_' + locale.value + '.json').success(function(response) {
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

  $scope.getCurrentUserRole = function() {
    if (document.getElementById('currentUserRole').value === 'ROLE_USER') {
      $scope.isUser = true;
    } else if (document.getElementById('currentUserRole').value === 'ROLE_ADMIN') {
      $scope.isAdmin = true;
    }
  };

  $scope.initUserId = function() {
    if ($routeParams.professorId != null && $routeParams.professorId != '') {
      $scope.professorId = $routeParams.professorId;
    } else {
      $scope.professorId = document.getElementById('currentUserId').value;
    }
  };

  $scope.init = function() {
    $scope.initUserId();
    $scope.loadWorkExperiences($scope.professorId);
    $scope.loadResources();
    $scope.getCurrentUserRole();
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
        $scope.loadWorkExperiences($scope.professorId);
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
        },
        professorId: function() {
          return $scope.professorId;
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
        },
        professorId: function() {
          return $scope.professorId;
        }
      }
    });
  };

  $scope.goBack = function() {
    window.history.back();
  };

});

var editWorkExperiencePopupController = function($scope, $modalInstance, $routeParams, $http, $route, $templateCache, workExperienceId, PctService,
        professorId) {

  $scope.workExperience = {};
  $scope.master = {};
  $scope.allInstitutionDataShown = false;

  $scope.patterns = {
    onlyLetters: /^[a-zA-ZčČćĆšŠđĐžŽ ]*$/,
    onlyNumbers: /^[0-9 ]*$/
  };

  $scope.dateOptions = {
    "starting-day": "1"
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
    $http.get('messages/profesorDetails_' + locale.value + '.json').success(function(response) {
      $scope.resources = angular.fromJson(response);
    });
    $http.get('messages/errors_' + locale.value + '.json').success(function(response) {
      $scope.errorMessages = angular.fromJson(response);
    });
  };

  $scope.loadSelectedWorkExperience = function(id) {
    PctService.loadSelectedWorkExperience(id, function(data) {
      if (angular.isObject(data)) {
        $scope.workExperience = data;
        $scope.workExperience.workStartDate = new Date(data.workStartDate);
        $scope.workExperience.workEndDate = (data.workEndDate != null ? new Date(data.workEndDate) : null);
        $scope.master = angular.copy($scope.workExperience);
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };

  $scope.setMaxDate = function() {
    $scope.maxDate = new Date();
  };

  $scope.setMaxDate();

  $scope.init = function() {
    $scope.loadSelectedWorkExperience(workExperienceId);
    $scope.loadResources();
    $scope.status = $routeParams.status;
  };

  $scope.init();

  $scope.expandInstitutionData = function() {
    $scope.allInstitutionDataShown = true;
  };

  $scope.collapseInstitutionData = function() {
    $scope.allInstitutionDataShown = false;
  };

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
    return angular.equals(workExperience, $scope.master);
  };

  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };

};

var createNewWorkExperienceController = function($scope, $modalInstance, $routeParams, $http, $route, $templateCache, workExperiences, PctService,
        professorId) {

  $scope.workExperience = {};
  $scope.allInstitutionTypes = [];
  $scope.selectedInstitution = [];
  $scope.masterSelectedInstitution = [];
  $scope.isExistingInstitution = false;

  $scope.patterns = {
    onlyLetters: /^[a-zA-ZčČćĆšŠđĐžŽ ]*$/,
    onlyNumbers: /^[0-9 ]*$/
  };

  $scope.dateOptions = {
    "starting-day": "1"
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
    $http.get('messages/profesorDetails_' + locale.value + '.json').success(function(response) {
      $scope.resources = angular.fromJson(response);
    });
    $http.get('messages/errors_' + locale.value + '.json').success(function(response) {
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

  $scope.setMaxDate = function() {
    $scope.maxDate = new Date();
  };

  $scope.setMaxDate();

  $scope.init = function() {
    $scope.loadAllInstitutionTypes();
    $scope.status = $routeParams.status;
    $scope.loadResources();
  };

  $scope.init();

  $scope.getInstitutions = function(val, type) {
    return PctService.findInstutionsStartsWith(val, type).then(function(response) {
      var institutions = [];
      for (var i = 0; i < response.length; i++) {
        institutions.push(response[i]);
      }
      return institutions;
    });
  };

  $scope.onSelectInstitution = function() {
    $scope.isExistingInstitution = true;
    $scope.masterSelectedInstitution = angular.copy($scope.selectedInstitution);
    $scope.workExperience.institutionType = $scope.selectedInstitution.institutionType;
    $scope.workExperience.institutionName = $scope.selectedInstitution.name;
    $scope.workExperience.universityName = $scope.selectedInstitution.university;
    $scope.workExperience.institutionCity = $scope.selectedInstitution.city;
    $scope.workExperience.institutionCountry = $scope.selectedInstitution.country;
    $scope.workExperience.institutionId = $scope.selectedInstitution.id;
  };

  $scope.restartInsitutionData = function() {
    $scope.selectedInstitution = null;
    $scope.isExistingInstitution = false;
    $scope.workExperience.institutionType = null;
    $scope.workExperience.institutionName = null;
    $scope.workExperience.universityName = null;
    $scope.workExperience.institutionCity = null;
    $scope.workExperience.institutionCountry = null;
    $scope.workExperience.institutionId = null;
  };

  $scope.$watch('selectedInstitution', function() {
    if ($scope.isExistingInstitution && !angular.equals($scope.selectedInstitution, $scope.masterSelectedInstitution)) {
      $scope.restartInsitutionData();
    }
  });

  $scope.saveWorkExperience = function() {
    if (!$scope.isExistingInstitution) {
      $scope.workExperience.institutionName = $scope.selectedInstitution;
    }
    $scope.workExperience.professorId = professorId;
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
      $("#warning").fadeTo(5000, 500).slideUp(500, function() {
        $("#warning").alert('close');
      });
    });
  };

  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };

  $scope.validateForm = function() {
    if ((($scope.workExperience.institutionName != null && $scope.workExperience.institutionName != '') || ($scope.selectedInstitution != null && $scope.selectedInstitution != ''))
            && $scope.workExperience.institutionType != null
            && $scope.workExperience.institutionType != ''
            && ($scope.workExperience.institutionType == 'Fakultet'
                    ? ($scope.workExperience.universityName != null && $scope.workExperience.universityName != '') : true)
            && $scope.workExperience.institutionCity != null
            && $scope.workExperience.institutionCity != ''
            && $scope.workExperience.institutionCountry != null
            && $scope.workExperience.institutionCountry != ''
            && $scope.workExperience.workStartDate != null
            && $scope.workExperience.workStartDate != ''
            && $scope.workExperience.title != null
            && $scope.workExperience.title != '') {
      return true;
    } else {
      return false;
    }
  };

};