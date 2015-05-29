app
        .controller(
                "ProfessorSpecialisticStudiesController",
                function($scope, $routeParams, $http, $location, $modal,
                        PctService) {

                  $scope.specialisticStudies = {};
                  $scope.allSpecialisticStudies = [];
                  $scope.allSpecialisticStudiesMaster = [];
                  $scope.noResultsFound = true;
                  $scope.resources = {};
                  $scope.errorMessages = {};
                  $scope.allStudyPrograms = [];
                  $scope.editMode = [];
                  $scope.inputStudyStartDateOpened = [];
                  $scope.inputStudyEndDateOpened = [];

                  $scope.isUser = false;
                  $scope.isAdmin = false;

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

                  $scope.convertTimeToDate = function(time) {
                    return new Date(time);
                  };

                  $scope.loadProfessorsSpecialisticStudies = function(
                          professorId, thesisTypeId) {
                    return PctService
                            .loadProfessorStudies(professorId, thesisTypeId)
                            .then(
                                    function(response) {
                                      if (angular.isObject(response)
                                              && response.length > 0) {
                                        $scope.allSpecialisticStudies = response;

                                        for (var i = 0; i < $scope.allSpecialisticStudies.length; i++) {
                                          $scope.allSpecialisticStudies[i].studyStartDate = $scope
                                                  .convertTimeToDate(response[i].studyStartDate);
                                          $scope.allSpecialisticStudies[i].studyEndDate = $scope
                                                  .convertTimeToDate(response[i].studyEndDate);
                                        }

                                        $scope.editMode = new Array(
                                                $scope.allSpecialisticStudies.length);
                                        for (var i = 0; i < $scope.allSpecialisticStudies.length; i++) {
                                          $scope.editMode.splice(i, 1, false);
                                        }

                                        $scope.inputStudyStartDateOpened = new Array(
                                                $scope.allSpecialisticStudies.length);
                                        for (var i = 0; i < $scope.allSpecialisticStudies.length; i++) {
                                          $scope.inputStudyStartDateOpened
                                                  .splice(i, 1, false);
                                        }

                                        $scope.inputStudyEndDateOpened = new Array(
                                                $scope.allSpecialisticStudies.length);
                                        for (var i = 0; i < $scope.allSpecialisticStudies.length; i++) {
                                          $scope.inputStudyEndDateOpened
                                                  .splice(i, 1, false);
                                        }
                                        $scope.allSpecialisticStudiesMaster = angular
                                                .copy($scope.allSpecialisticStudies);
                                        $scope.noResultsFound = false;
                                      } else {
                                        $scope.noResultsFound = true;
                                      }
                                    });
                  };

                  $scope.loadAllStudyPrograms = function() {
                    PctService.loadAllStudyPrograms($routeParams,
                            function(data) {
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

                  $scope.getCurrentUserRole = function() {
                    if (document.getElementById('currentUserRole').value === 'ROLE_USER') {
                      $scope.isUser = true;
                    } else if (document.getElementById('currentUserRole').value === 'ROLE_ADMIN') {
                      $scope.isAdmin = true;
                    }
                  };

                  $scope.init = function() {
                    $scope
                            .loadProfessorsSpecialisticStudies(
                                    $routeParams.professorId,
                                    $routeParams.thesisTypeId);
                    $scope.loadAllStudyPrograms();
                    $scope.loadResources();
                    $scope.getCurrentUserRole();
                  };

                  $scope.init();

                  $scope.editProfesorSpecialisticStudies = function(index) {
                    $scope.editMode.splice(index, 1, true);
                    for (var i = 0; i < $scope.editMode.length; i++) {
                      if (i != index) {
                        $scope.allSpecialisticStudies[i] = angular
                                .copy($scope.allSpecialisticStudiesMaster[i]);
                        $scope.editMode.splice(i, 1, false);
                      }
                    }
                  };

                  $scope.close = function(index) {
                    $scope.allSpecialisticStudies[index] = angular
                            .copy($scope.allSpecialisticStudiesMaster[index]);
                    $scope.editMode.splice(index, 1, false);
                  };

                  $scope.isInEditMode = function() {
                    for (var i = 0; i < $scope.editMode.length; i++) {
                      if ($scope.editMode[i] === true) { return true; }
                    }
                    return false;
                  };

                  $scope.updateProfessorSpecialisticStudies = function(studies,
                          index) {
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
                    }).error(
                            function(data, status) {
                              $scope.error = "Greška!";
                              if (angular.isObject(data.fieldErrors)) {
                                $scope.fieldErrors = angular
                                        .fromJson(data.fieldErrors);
                              }
                              $scope.status = status;
                              $("html, body").animate({
                                scrollTop: 0
                              }, "slow");
                            });
                  };

                  $scope.isUnchanged = function(index) {
                    return angular.equals($scope.allSpecialisticStudies[index],
                            $scope.allSpecialisticStudiesMaster[index]);
                  };

                  $scope.goBack = function() {
                    window.history.back();
                  };

                  $scope.deleteProfessorSpecialisticStudies = function(id,
                          index) {
                    PctService
                            .deleteProfessorStudies(
                                    id,
                                    function(data) {
                                      if (angular.isObject(data)) {
                                        $scope.errorStatus = data.status;
                                      } else {
                                        $scope.successStatus = "Successfully deleted specialistic studies.";
                                        $scope.allSpecialisticStudies.splice(
                                                index, 1);
                                        $scope
                                                .loadProfessorsSpecialisticStudies(
                                                        $routeParams.professorId,
                                                        $routeParams.thesisTypeId);
                                      }
                                    });
                  };

                  $scope.createNewSpecialisticStudies = function() {
                    $modal.open({
                      templateUrl: 'createNewSpecialisticStudiesPopup.html',
                      controller: createNewSpecialisticStudiesController,
                    });
                  };
                  
                  $scope.goBack = function() {
                    window.history.back();
                  };

                });

var createNewSpecialisticStudiesController = function($scope, $modalInstance,
        $routeParams, $http, $route, $templateCache, PctService) {

  $scope.specialisticStudies = {};
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
    $scope.specialisticStudies.facultyName = $scope.selectedFaculty.name;
    $scope.specialisticStudies.universityName = $scope.selectedFaculty.university;
    $scope.specialisticStudies.facultyCity = $scope.selectedFaculty.city;
    $scope.specialisticStudies.facultyCountry = $scope.selectedFaculty.country;
    $scope.specialisticStudies.institutionId = $scope.selectedFaculty.id;
  };

  $scope.resetFacultyData = function() {
    $scope.selectedFaculty = null;
    $scope.isExistingFaculty = false;
    $scope.specialisticStudies.facultyName = null;
    $scope.specialisticStudies.universityName = null;
    $scope.specialisticStudies.facultyCity = null;
    $scope.specialisticStudies.facultyCountry = null;
    $scope.specialisticStudies.institutionId = null;
  };

  $scope.saveNewProfessorSpecialisticStudies = function() {
    if (!$scope.isExistingFaculty) {
      $scope.specialisticStudies.facultyName = $scope.selectedFaculty;
    }
    $scope.specialisticStudies.professorId = $routeParams.professorId;
    $scope.specialisticStudies.thesisTypeId = $routeParams.thesisTypeId;
    $http({
      method: 'POST',
      url: "api/studies",
      data: $scope.specialisticStudies,
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
    if ((($scope.specialisticStudies.facultyName != null && $scope.specialisticStudies.facultyName != '') || ($scope.selectedFaculty != null && $scope.selectedFaculty != ''))
            && $scope.specialisticStudies.universityName != null
            && $scope.specialisticStudies.universityName != ''
            && (!$scope.isExistingFaculty
                    ? ($scope.specialisticStudies.facultyCity != null && $scope.specialisticStudies.facultyCity != '')
                    : true)
            && (!$scope.isExistingFaculty
                    ? ($scope.specialisticStudies.facultyCountry != null && $scope.specialisticStudies.facultyCountry != '')
                    : true)
            && $scope.specialisticStudies.studyProgram != null
            && $scope.specialisticStudies.studyProgram != ''
            && $scope.specialisticStudies.studyArea != null
            && $scope.specialisticStudies.studyArea != ''
            && $scope.specialisticStudies.studyStartDate != null
            && $scope.specialisticStudies.studyStartDate != ''
            && $scope.specialisticStudies.studyEndDate != null
            && $scope.specialisticStudies.studyEndDate != ''
            && $scope.specialisticStudies.averageGrade != null
            && $scope.specialisticStudies.averageGrade != ''
            && $scope.specialisticStudies.thesisTitle != null
            && $scope.specialisticStudies.thesisTitle != ''
            && $scope.specialisticStudies.acquiredTitle != null
            && $scope.specialisticStudies.acquiredTitle != '') {
      return true;
    } else {
      return false;
    }
  };

};