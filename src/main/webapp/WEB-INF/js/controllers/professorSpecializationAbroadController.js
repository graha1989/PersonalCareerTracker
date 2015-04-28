app.controller("ProfessorSpecializationAbroadController", function($scope,
        $routeParams, $http, $location, $modal, PctService) {

  $scope.specialization = {};
  $scope.allSpecializations = [];
  $scope.allSpecializationMaster = [];
  $scope.noResultsFound = true;
  $scope.resources = {};
  $scope.errorMessages = {};
  $scope.editMode = [];
  $scope.inputStartDateOpened = [];
  $scope.inputEndDateOpened = [];
  
  $scope.patterns = {
    onlyLetters: /^[a-zA-ZčČćĆšŠđĐžŽ ]*$/,
    onlyNumbers: /^[0-9 ]*$/
  };

  $scope.dateOptions = {
    "starting-day": "1"
  };

  /* Date picker functions for start date */
  $scope.openStartDate = function($event, index) {
    $event.preventDefault();
    $event.stopPropagation();

    $scope.inputStartDateOpened[index] = $scope.editMode[index];
  };

  /* Date picker functions for end date */
  $scope.openEndDate = function($event, index) {
    $event.preventDefault();
    $event.stopPropagation();

    $scope.inputEndDateOpened[index] = $scope.editMode[index];
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
  
  $scope.convertTimeToDate = function(time) {
    return new Date(time);
  };

  $scope.loadProfessorsSpecializationsAbroad = function(professorId) {
    return PctService.loadProfessorsSpecializationsAbroad(professorId)
            .then(function(response) {
              if (angular.isObject(response) && response.length > 0) {
                $scope.allSpecializations = response;
                
                for (var i = 0; i < $scope.allSpecializations.length; i++) {
                  $scope.allSpecializations[i].startDate = $scope.convertTimeToDate(response[i].startDate);
                  $scope.allSpecializations[i].endDate = $scope.convertTimeToDate(response[i].endDate);
                }
                
                $scope.editMode = new Array($scope.allSpecializations.length);
                for (var i = 0; i < $scope.allSpecializations.length; i++) {
                  $scope.editMode.splice(i, 1, false);
                }
                
                $scope.inputStartDateOpened = new Array($scope.allSpecializations.length);
                for (var i = 0; i < $scope.allSpecializations.length; i++) {
                  $scope.inputStartDateOpened.splice(i, 1, false);
                }
                
                $scope.inputEndDateOpened = new Array($scope.allSpecializations.length);
                for (var i = 0; i < $scope.allSpecializations.length; i++) {
                  $scope.inputEndDateOpened.splice(i, 1, false);
                }
                $scope.allSpecializationsMaster = angular.copy($scope.allSpecializations);
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
    $scope.loadProfessorsSpecializationsAbroad($routeParams.professorId);
    $scope.loadResources();
  };

  $scope.init();

  $scope.editProfesorSpecialization = function(index) {
    $scope.editMode.splice(index, 1, true);
    for (var i = 0; i < $scope.editMode.length; i++) {
      if (i != index) {
        $scope.allSpecializations[i] = angular.copy($scope.allSpecializationsMaster[i]);
        $scope.editMode.splice(i, 1, false);
      }
    }
  };
  
  $scope.close = function(index) {
    $scope.allSpecializations[index] = angular.copy($scope.allSpecializationsMaster[index]);
    $scope.editMode.splice(index, 1, false);
  };
  
  $scope.isInEditMode = function() {
    for (var i = 0; i < $scope.editMode.length; i++) {
      if ($scope.editMode[i] === true) {
        return true;
      }
    }
    return false;
  };
  
  $scope.updateProfessorSpecialization = function(specialization, index) {
    $http({
      method: 'PUT',
      url: "api/specialization",
      data: specialization,
      headers: {
        'Content-Type': 'application/json'
      }
    }).success(function(data, status) {
      $("html, body").animate({
        scrollTop: 0
      }, "slow");
      $scope.editMode[index] = false;
    }).error(function(data, status) {
      $scope.error = "Greška!";
      if (angular.isObject(data.fieldErrors)) {
        $scope.fieldErrors = angular.fromJson(data.fieldErrors);
      }
      $scope.status = status;
      $("html, body").animate({
        scrollTop: 0
      }, "slow");
    });
  };
  
  $scope.isUnchanged = function(index) {
    return angular.equals($scope.allSpecializations[index], $scope.allSpecializationsMaster[index]);
  };

  $scope.goBack = function() {
    window.history.back();
  };
  
  $scope.deleteProfessorSpecializationAbroad = function(id, index) {
    PctService.deleteProfessorSpecializationAbroad(id, function(data) {
      if (angular.isObject(data)) {
        $scope.errorStatus = data.status;
      } else {
        $scope.successStatus = "Successfully deleted specialization.";
        $scope.allSpecializations.splice(index, 1);
        $scope.loadProfessorsSpecializationsAbroad($routeParams.professorId);
      }
    });
  };
  
  $scope.createNewSpecialization = function() {
    $modal.open({
      templateUrl: 'createNewSpecializationPopup.html',
      controller: createNewSpecializationController,
    });
  };
  
});

var createNewSpecializationController = function($scope, $modalInstance,
        $routeParams, $http, $route, $templateCache, PctService) {

  $scope.specialization = {};
  $scope.noResultsFound = true;
  $scope.resources = {};
  $scope.errorMessages = {};
  $scope.isExistingInstitution = false;
  $scope.selectedInstitution = [];
  
  $scope.patterns = {
    onlyLetters: /^[a-zA-ZčČćĆšŠđĐžŽ ]*$/,
    onlyNumbers: /^[0-9 ]*$/
  };

  $scope.dateOptions = {
    "starting-day": "1"
  };

  /* Date picker functions for start date */
  $scope.openStartDate = function($event) {
    $event.preventDefault();
    $event.stopPropagation();

    $scope.inputStartDateOpened = true;
  };

  /* Date picker functions for end date */
  $scope.openEndDate = function($event) {
    $event.preventDefault();
    $event.stopPropagation();

    $scope.inputEndDateOpened = true;
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
  
  $scope.convertTimeToDate = function(time) {
    return new Date(time);
  };

  $scope.setMaxDate = function() {
    $scope.maxDate = new Date();
  };

  $scope.setMaxDate();

  $scope.init = function() {
    $scope.status = $routeParams.status;
    $scope.loadResources();
  };

  $scope.init();

  $scope.getInstitutions = function(val) {
    return PctService.findInstutionsStartsWith(val, 'OTHER').then(
            function(response) {
              var institutions = [];
              for (var i = 0; i < response.length; i++) {
                institutions.push(response[i]);
              }
              return institutions;
            });
  };
  
  $scope.$watch('selectedInstitution', function() {
    if ($scope.selectedInstitution === null || $scope.selectedInstitution == '' || angular.isUndefined($scope.selectedInstitution)) {
      $scope.isExistingInstitution = false;
    }
  });

  $scope.onSelectInstitution = function() {
    $scope.isExistingInstitution = true;
    $scope.specialization.institutionName = $scope.selectedInstitution.name;
    $scope.specialization.city = $scope.selectedInstitution.city;
    $scope.specialization.country = $scope.selectedInstitution.country;
    $scope.specialization.institutionId = $scope.selectedInstitution.id;
  };

  $scope.saveNewProfessorSpecializationAbroad = function() {
    if (!$scope.isExistingInstitution) {
      $scope.specialization.institutionName = $scope.selectedInstitution;
    }
    $scope.specialization.professorId = $routeParams.professorId;
    $http({
      method: 'POST',
      url: "api/specialization",
      data: $scope.specialization,
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
    if ((($scope.specialization.institutionName != null && $scope.specialization.institutionName != '') || ($scope.selectedInstitution != null && $scope.selectedInstitution != ''))
            && (!$scope.isExistingInstitution ? ($scope.specialization.city != null && $scope.specialization.city != '') : true)
            && (!$scope.isExistingInstitution ? ($scope.specialization.country != null && $scope.specialization.country != '') : true)   
            && $scope.specialization.startDate != null
            && $scope.specialization.startDate != ''
            && $scope.specialization.endDate != null
            && $scope.specialization.endDate != ''
            && $scope.specialization.purpose != null
            && $scope.specialization.purpose != '') {
      return true;
    } else {
      return false;
    }
  };

};