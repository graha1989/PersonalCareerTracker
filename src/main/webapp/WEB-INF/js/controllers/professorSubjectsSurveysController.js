app.controller("ProfessorSubjectsSurveysController", function($scope,
        $routeParams, $http, $location, $modal, PctService) {

  $scope.survey = {};
  $scope.allSurveys = [];
  $scope.result = {};
  $scope.completeSurveyDataArray = [];

  $scope.noResultsFound = true;
  $scope.resources = {};
  $scope.errorMessages = {};
  
  $scope.isUser = false;
  $scope.isAdmin = false;
  $scope.professorId = '';

  $scope.sortType = '';

  $scope.allSortTypes = [{
    description: "godini-rastuće",
    sortBy: "+academicYear"
  }, {
    description: "godini-opadajuće",
    sortBy: "-academicYear"
  }, {
    description: "prosečnoj oceni-rastuće",
    sortBy: "+averageGrade"
  }, {
    description: "prosečnoj oceni-opadajuće",
    sortBy: "-averageGrade"
  }, {
    description: "broju studenata-rastuće",
    sortBy: "+numberOfStudents"
  }, {
    description: "broju studenata-opadajuće",
    sortBy: "-numberOfStudents"
  }];
  
  $scope.noSurveys = function() {
    return $scope.allSurveys.length === 0;
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
        $scope.calculateResult();
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };

  $scope.calculateResult = function() {
    var gradeSum = 0;
    var studentsSum = 0;
    for (var i = 0; i < $scope.allSurveys.length; i++) {
      gradeSum = gradeSum + $scope.allSurveys[i].averageGrade;
      studentsSum = studentsSum + $scope.allSurveys[i].numberOfStudents;
    }
    $scope.result = {
      averageGrade: gradeSum / $scope.allSurveys.length,
      averageSudentsNumber: studentsSum / $scope.allSurveys.length
    }
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
    $scope.loadAllSurveys($scope.professorId, $routeParams.subjectId);
    $scope.loadResources();
    $scope.getCurrentUserRole();
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
      resolve: {
        professorId: function() {
          return $scope.professorId;
        }
      }
    });
  };

  $scope.deleteSurvey = function(id, index) {
    PctService.deleteSurvey(id,
            function(data) {
              if (angular.isObject(data)) {
                $scope.errorStatus = data.status;
              } else {
                $scope.successStatus = "Successfully deleted survey.";
                $scope.allSurveys.splice(index, 1);
                $scope.loadAllSurveys($scope.professorId,
                        $routeParams.subjectId);
              }
            });
  };
  
  $scope.goBack = function() {
    window.history.back();
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
        $http, $route, $templateCache, PctService, professorId) {

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
    $scope.survey.professorId = professorId;
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
  
  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };

};