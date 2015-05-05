app.controller("ProfessorMasterStudiesController", function($scope,
        $routeParams, $http, $location, $modal, PctService) {

  $scope.masterStudies = {};
  $scope.allMasterStudies = [];
  $scope.allMasterStudiesMaster = [];
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

  $scope.loadProfessorsMasterStudies = function(professorId, thesisTypeId) {
    return PctService.loadProfessorStudies(professorId, thesisTypeId).then(
            function(response) {
              if (angular.isObject(response) && response.length > 0) {
                $scope.allMasterStudies = response;

                for (var i = 0; i < $scope.allMasterStudies.length; i++) {
                  $scope.allMasterStudies[i].studyStartDate = $scope
                          .convertTimeToDate(response[i].studyStartDate);
                  $scope.allMasterStudies[i].studyEndDate = $scope
                          .convertTimeToDate(response[i].studyEndDate);
                }

                $scope.editMode = new Array($scope.allMasterStudies.length);
                for (var i = 0; i < $scope.allMasterStudies.length; i++) {
                  $scope.editMode.splice(i, 1, false);
                }

                $scope.inputStudyStartDateOpened = new Array(
                        $scope.allMasterStudies.length);
                for (var i = 0; i < $scope.allMasterStudies.length; i++) {
                  $scope.inputStudyStartDateOpened.splice(i, 1, false);
                }

                $scope.inputStudyEndDateOpened = new Array(
                        $scope.allMasterStudies.length);
                for (var i = 0; i < $scope.allMasterStudies.length; i++) {
                  $scope.inputStudyEndDateOpened.splice(i, 1, false);
                }
                $scope.allMasterStudiesMaster = angular
                        .copy($scope.allMasterStudies);
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
    $scope.loadProfessorsMasterStudies($routeParams.professorId,
            $routeParams.thesisTypeId);
    $scope.loadAllStudyPrograms();
    $scope.loadResources();
  };

  $scope.init();

  $scope.editProfesorMasterStudies = function(index) {
    $scope.editMode.splice(index, 1, true);
    for (var i = 0; i < $scope.editMode.length; i++) {
      if (i != index) {
        $scope.allMasterStudies[i] = angular
                .copy($scope.allMasterStudiesMaster[i]);
        $scope.editMode.splice(i, 1, false);
      }
    }
  };

  $scope.close = function(index) {
    $scope.allMasterStudies[index] = angular
            .copy($scope.allMasterStudiesMaster[index]);
    $scope.editMode.splice(index, 1, false);
  };

  $scope.isInEditMode = function() {
    for (var i = 0; i < $scope.editMode.length; i++) {
      if ($scope.editMode[i] === true) { return true; }
    }
    return false;
  };

  $scope.updateProfessorMasterStudies = function(studies, index) {
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
    return angular.equals($scope.allMasterStudies[index],
            $scope.allMasterStudiesMaster[index]);
  };

  $scope.goBack = function() {
    window.history.back();
  };

  $scope.deleteProfessorMasterStudies = function(id, index) {
    PctService.deleteProfessorStudies(id, function(data) {
      if (angular.isObject(data)) {
        $scope.errorStatus = data.status;
      } else {
        $scope.successStatus = "Successfully deleted master studies.";
        $scope.allMasterStudies.splice(index, 1);
        $scope.loadProfessorsMasterStudies($routeParams.professorId,
                $routeParams.thesisTypeId);
      }
    });
  };

  $scope.createNewMasterStudies = function() {
    $modal.open({
      templateUrl: 'createNewMasterStudiesPopup.html',
      controller: createNewMasterStudiesController,
    });
  };

});

var createNewMasterStudiesController = function($scope, $modalInstance,
        $routeParams, $http, $route, $templateCache, PctService) {

  $scope.masterStudies = {};
  $scope.noResultsFound = true;
  $scope.resources = {};
  $scope.errorMessages = {};
  $scope.allStudyPrograms = [];
  $scope.isExistingFaculty = false;
  $scope.selectedFaculty = [];
  $scope.masterSelectedFaculty = [];

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
    if ($scope.isExistingFaculty
            && !angular.equals($scope.selectedFaculty,
                    $scope.masterSelectedFaculty)) {
      $scope.resetFacultyData();
    }
  });

  $scope.onSelectFaculty = function() {
    $scope.isExistingFaculty = true;
    $scope.masterSelectedFaculty = angular.copy($scope.selectedFaculty);
    $scope.masterStudies.facultyName = $scope.selectedFaculty.name;
    $scope.masterStudies.universityName = $scope.selectedFaculty.university;
    $scope.masterStudies.facultyCity = $scope.selectedFaculty.city;
    $scope.masterStudies.facultyCountry = $scope.selectedFaculty.country;
    $scope.masterStudies.institutionId = $scope.selectedFaculty.id;
  };

  $scope.resetFacultyData = function() {
    $scope.selectedFaculty = null;
    $scope.isExistingFaculty = false;
    $scope.masterStudies.facultyName = null;
    $scope.masterStudies.universityName = null;
    $scope.masterStudies.facultyCity = null;
    $scope.masterStudies.facultyCountry = null;
    $scope.masterStudies.institutionId = null;
  };

  $scope.saveNewProfessorMasterStudies = function() {
    if (!$scope.isExistingFaculty) {
      $scope.masterStudies.facultyName = $scope.selectedFaculty;
    }
    $scope.masterStudies.professorId = $routeParams.professorId;
    $scope.masterStudies.thesisTypeId = $routeParams.thesisTypeId;
    $http({
      method: 'POST',
      url: "api/studies",
      data: $scope.masterStudies,
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
    if ((($scope.masterStudies.facultyName != null && $scope.masterStudies.facultyName != '') || ($scope.selectedFaculty != null && $scope.selectedFaculty != ''))
            && $scope.masterStudies.universityName != null
            && $scope.masterStudies.universityName != ''
            && (!$scope.isExistingFaculty
                    ? ($scope.masterStudies.facultyCity != null && $scope.masterStudies.facultyCity != '')
                    : true)
            && (!$scope.isExistingFaculty
                    ? ($scope.masterStudies.facultyCountry != null && $scope.masterStudies.facultyCountry != '')
                    : true)
            && $scope.masterStudies.studyProgram != null
            && $scope.masterStudies.studyProgram != ''
            && $scope.masterStudies.studyArea != null
            && $scope.masterStudies.studyArea != ''
            && $scope.masterStudies.studyStartDate != null
            && $scope.masterStudies.studyStartDate != ''
            && $scope.masterStudies.studyEndDate != null
            && $scope.masterStudies.studyEndDate != ''
            && $scope.masterStudies.averageGrade != null
            && $scope.masterStudies.averageGrade != ''
            && $scope.masterStudies.thesisTitle != null
            && $scope.masterStudies.thesisTitle != ''
            && $scope.masterStudies.acquiredTitle != null
            && $scope.masterStudies.acquiredTitle != '') {
      return true;
    } else {
      return false;
    }
  };

};