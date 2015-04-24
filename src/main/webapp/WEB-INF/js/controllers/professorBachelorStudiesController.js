app.controller("ProfessorBachelorStudiesController", function($scope,
        $routeParams, $http, $location, $modal, PctService) {

  $scope.bachelorStudies = {};
  $scope.allBachelorStudies = [];

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

    $scope.inputStudyStartDateOpened[index] = true;
  };

  /* Date picker functions for end date */
  $scope.openStudyEndDate = function($event, index) {
    $event.preventDefault();
    $event.stopPropagation();

    $scope.inputStudyEndDateOpened[index] = true;
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

  $scope.loadProfessorsBachelorStudies = function(professorId, thesisTypeId) {
    return PctService.loadProfessorsBachelorStudies(professorId, thesisTypeId)
            .then(function(response) {
              if (angular.isObject(response) && response.length > 0) {
                $scope.allBachelorStudies = response;
                
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
  };

  $scope.goBack = function() {
    window.history.back();
  };

});