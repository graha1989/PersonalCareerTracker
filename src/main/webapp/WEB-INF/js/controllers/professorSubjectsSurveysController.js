app.controller("ProfessorSubjectsSurveysController", function($scope,
        $routeParams, $http, $location, $modal, PctService) {

  $scope.survey = {};
  $scope.allSurveys = [];

  $scope.completeSurveyDataArray = [];

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

  $scope.loadSelectedSubject = function(id, index) {
    PctService.loadSelectedSubject(id, function(data) {
      if (angular.isObject(data)) {
        $scope.subject = data;
        $scope.completeSurveyDataArray.push({
          subject: $scope.subject,
          survey: $scope.allSurveys[index]
        });
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };

  $scope.loadAllSurveys = function(professorId, subjectId) {
    PctService.loadAllSurveys(professorId, subjectId, function(data) {
      if (angular.isObject(data) && data.length > 0) {
        $scope.allSurveys = data;
        for (var i = 0; i < $scope.allSurveys.length; i++) {
          $scope.loadSelectedSubject($scope.allSurveys[i].subjectId, i);
        }
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };

  $scope.init = function() {
    $scope.loadAllSurveys($routeParams.professorId, $routeParams.subjectId);
    $scope.loadResources();
  };

  $scope.init();

  $scope.editSurvey = function(id) {
    $modal.open({
      templateUrl: 'editSurveyPopup.html',
      controller: editSurveyPopupController,
      resolve: {
        surveyId: function() {
          return id;
        }
      }
    });
  };

  $scope.createNewSurvey = function() {
    $modal.open({
      templateUrl: 'createNewSurveyPopup.html',
      controller: createNewSurveyController,
    });
  };

});

var editSurveyPopupController = function($scope, $modalInstance, $routeParams,
        $http, $route, $templateCache, surveyId, PctService) {

  $scope.survey = {};
  $scope.master = {};

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

  $scope.loadSelectedSurvey = function(id) {
    PctService.loadSelectedSurvey(id, function(data) {
      if (angular.isObject(data)) {
        $scope.survey = data;
        $scope.master = angular.copy($scope.survey);
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };

  $scope.init = function() {
    $scope.loadSelectedSurvey(surveyId);
    $scope.status = $routeParams.status;
    $scope.loadResources();
  };

  $scope.init();

  $scope.saveSurvey = function() {
    $http({
      method: 'PUT',
      url: "api/surveys",
      data: $scope.survey,
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

  $scope.isUnchanged = function(survey) {
    return angular.equals(survey, $scope.master);
  };

  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };

};

var createNewSurveyController = function($scope, $modalInstance, $routeParams,
        $http, $route, $templateCache, PctService) {

  $scope.survey = {};

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

  $scope.init = function() {
    $scope.status = $routeParams.status;
    $scope.loadResources();
  };

  $scope.init();

  $scope.saveNewSurvey = function() {
    $scope.survey.professorId = $routeParams.professorId;
    $scope.survey.subjectId = $routeParams.subjectId;
    $http({
      method: 'POST',
      url: "api/surveys",
      data: $scope.survey,
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

  $scope.validateForm = function() {
    if ($scope.survey.academicYear != null && $scope.survey.academicYear != ''
            && $scope.survey.averageGrade != null
            && $scope.survey.averageGrade != ''
            && $scope.survey.numberOfStudents != null
            && $scope.survey.numberOfStudents != '') {
      return true;
    } else {
      return false;
    }
  };

};