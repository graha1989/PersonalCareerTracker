app.controller("ProfessorBachelorStudiesController", function($scope,
        $routeParams, $http, $location, $modal, PctService) {

  $scope.bachelorStudies = {};
  $scope.allBachelorStudies = [];
  $scope.allBachelorStudiesMaster = [];
  $scope.noResultsFound = true;
  $scope.resources = {};
  $scope.errorMessages = {};
  $scope.allStudyPrograms = [];
  $scope.editMode = [];
  $scope.inputStudyStartDateOpened = [];
  $scope.inputStudyEndDateOpened = [];
  
  $scope.patterns = {
    onlyLetters: /^[a-zA-ZčČćĆšŠđĐžŽ ]*$/,
    onlyNumbers: /^[0-9 ]*$/
  };

  $scope.dateOptions = {
    "starting-day": "1"
  };

  /* Date picker functions for start date */
  $scope.openStudyStartDate = function($event, index) {
    $event.preventDefault();
    $event.stopPropagation();

    $scope.inputStudyStartDateOpened[index] = $scope.editMode[index];
  };

  /* Date picker functions for end date */
  $scope.openStudyEndDate = function($event, index) {
    $event.preventDefault();
    $event.stopPropagation();

    $scope.inputStudyEndDateOpened[index] = $scope.editMode[index];
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

  $scope.loadProfessorsBachelorStudies = function(professorId, thesisTypeId) {
    return PctService.loadProfessorsBachelorStudies(professorId, thesisTypeId)
            .then(function(response) {
              if (angular.isObject(response) && response.length > 0) {
                $scope.allBachelorStudies = response;
                
                for (var i = 0; i < $scope.allBachelorStudies.length; i++) {
                  $scope.allBachelorStudies[i].studyStartDate = $scope.convertTimeToDate(response[i].studyStartDate);
                  $scope.allBachelorStudies[i].studyEndDate = $scope.convertTimeToDate(response[i].studyEndDate);
                }
                
                $scope.editMode = new Array($scope.allBachelorStudies.length);
                for (var i = 0; i < $scope.allBachelorStudies.length; i++) {
                  $scope.editMode.splice(i, 1, false);
                }
                
                $scope.inputStudyStartDateOpened = new Array($scope.allBachelorStudies.length);
                for (var i = 0; i < $scope.allBachelorStudies.length; i++) {
                  $scope.inputStudyStartDateOpened.splice(i, 1, false);
                }
                
                $scope.inputStudyEndDateOpened = new Array($scope.allBachelorStudies.length);
                for (var i = 0; i < $scope.allBachelorStudies.length; i++) {
                  $scope.inputStudyEndDateOpened.splice(i, 1, false);
                }
                $scope.allBachelorStudiesMaster = angular.copy($scope.allBachelorStudies);
                $scope.noResultsFound = false;
              } else {
                $scope.noResultsFound = true;
              }
            });
  };

  $scope.loadAllStudyPrograms = function() {
    PctService.loadAllStudyPrograms($routeParams, function(data) {
      if (angular.isObject(data) && data.length > 0) {
        $scope.allStudyPrograms = data;
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
    $scope.loadProfessorsBachelorStudies($routeParams.professorId,
            $routeParams.thesisTypeId);
    $scope.loadAllStudyPrograms();
    $scope.loadResources();
  };

  $scope.init();

  $scope.editProfesorBachelorStudies = function(index) {
    $scope.editMode.splice(index, 1, true);
    for (var i = 0; i < $scope.editMode.length; i++) {
      if (i != index) {
        $scope.allBachelorStudies[i] = angular.copy($scope.allBachelorStudiesMaster[i]);
        $scope.editMode.splice(i, 1, false);
      }
    }
  };
  
  $scope.close = function(index) {
    $scope.allBachelorStudies[index] = angular.copy($scope.allBachelorStudiesMaster[index]);
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
  
  $scope.updateProfessorBachelorStudies = function(studies, index) {
    $http({
      method: 'PUT',
      url: "api/studies",
      data: studies,
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
    return angular.equals($scope.allBachelorStudies[index], $scope.allBachelorStudiesMaster[index]);
  };

  $scope.goBack = function() {
    window.history.back();
  };
  
  $scope.deleteProfessorBachelorStudies = function(id, index) {
    PctService.deleteProfessorBachelorStudies(id, function(data) {
      if (angular.isObject(data)) {
        $scope.errorStatus = data.status;
      } else {
        $scope.successStatus = "Successfully deleted bachelor studies.";
        $scope.allBachelorStudies.splice(index, 1);
        $scope.loadProfessorsBachelorStudies($routeParams.professorId, $routeParams.thesisTypeId);
      }
    });
  };
  
  $scope.createNewBachelorStudies = function() {
    $modal.open({
      templateUrl: 'createNewBachelorStudiesPopup.html',
      controller: createNewBachelorStudiesController,
    });
  };
  
});

var createNewBachelorStudiesController = function($scope, $modalInstance,
        $routeParams, $http, $route, $templateCache, PctService) {

  $scope.bachelorStudies = {};
  $scope.noResultsFound = true;
  $scope.resources = {};
  $scope.errorMessages = {};
  $scope.allStudyPrograms = [];
  $scope.isExistingFaculty = false;
  $scope.selectedFaculty = [];
  
  $scope.patterns = {
    onlyLetters: /^[a-zA-ZčČćĆšŠđĐžŽ ]*$/,
    onlyNumbers: /^[0-9 ]*$/
  };

  $scope.dateOptions = {
    "starting-day": "1"
  };

  /* Date picker functions for start date */
  $scope.openStudyStartDate = function($event) {
    $event.preventDefault();
    $event.stopPropagation();

    $scope.inputStudyStartDateOpened = true;
  };

  /* Date picker functions for end date */
  $scope.openStudyEndDate = function($event) {
    $event.preventDefault();
    $event.stopPropagation();

    $scope.inputStudyEndDateOpened = true;
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

  $scope.loadAllStudyPrograms = function() {
    PctService.loadAllStudyPrograms($routeParams, function(data) {
      if (angular.isObject(data) && data.length > 0) {
        $scope.allStudyPrograms = data;
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
    $scope.loadAllStudyPrograms();
    $scope.status = $routeParams.status;
    $scope.loadResources();
  };

  $scope.init();

  $scope.getFaculties = function(val) {
    return PctService.findInstutionsStartsWith(val, 'FACULTY').then(
            function(response) {
              var faculties = [];
              for (var i = 0; i < response.length; i++) {
                faculties.push(response[i]);
              }
              return faculties;
            });
  };
  
  $scope.$watch('selectedFaculty', function() {
    if ($scope.selectedFaculty === null || $scope.selectedFaculty == '' || angular.isUndefined($scope.selectedFaculty)) {
      $scope.isExistingFaculty = false;
    }
  });

  $scope.onSelectFaculty = function() {
    $scope.isExistingFaculty = true;
    $scope.bachelorStudies.facultyName = $scope.selectedFaculty.name;
    $scope.bachelorStudies.universityName = $scope.selectedFaculty.university;
    $scope.bachelorStudies.institutionId = $scope.selectedFaculty.id;
  };

  $scope.saveNewProfessorBachelorStudies = function() {
    if (!$scope.isExistingFaculty) {
      $scope.bachelorStudies.facultyName = $scope.selectedFaculty;
    }
    $scope.bachelorStudies.professorId = $routeParams.professorId;
    $scope.bachelorStudies.thesisTypeId = $routeParams.thesisTypeId;
    $http({
      method: 'POST',
      url: "api/studies",
      data: $scope.bachelorStudies,
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
    if ((($scope.bachelorStudies.facultyName != null && $scope.bachelorStudies.facultyName != '') || ($scope.selectedFaculty != null && $scope.selectedFaculty != ''))
            && $scope.bachelorStudies.universityName != null
            && $scope.bachelorStudies.universityName != ''
            && (!$scope.isExistingFaculty ? ($scope.bachelorStudies.facultyCity != null && $scope.bachelorStudies.facultyCity != '') : true)
            && (!$scope.isExistingFaculty ? ($scope.bachelorStudies.facultyCountry != null && $scope.bachelorStudies.facultyCountry != '') : true)   
            && $scope.bachelorStudies.studyProgram != null
            && $scope.bachelorStudies.studyProgram != ''
            && $scope.bachelorStudies.studyArea != null
            && $scope.bachelorStudies.studyArea != ''
            && $scope.bachelorStudies.studyStartDate != null
            && $scope.bachelorStudies.studyStartDate != ''
            && $scope.bachelorStudies.studyEndDate != null
            && $scope.bachelorStudies.studyEndDate != ''
            && $scope.bachelorStudies.averageGrade != null
            && $scope.bachelorStudies.averageGrade != ''
            && $scope.bachelorStudies.thesisTitle != null
            && $scope.bachelorStudies.thesisTitle != ''
            && $scope.bachelorStudies.acquiredTitle != null
            && $scope.bachelorStudies.acquiredTitle != '') {
      return true;
    } else {
      return false;
    }
  };

};