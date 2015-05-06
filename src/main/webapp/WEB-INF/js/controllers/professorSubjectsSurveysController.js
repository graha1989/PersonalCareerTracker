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

  $scope.loadAllSurveys = function(subjectId) {
    PctService.loadAllSurveys(subjectId, function(data) {
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
    $scope.loadAllSurveys($routeParams.prosubjectId);
    $scope.loadResources();
  };

  $scope.init();
/*
  $scope.editSubject = function(id) {
    $modal.open({
      templateUrl: 'editSubjectPopup.html',
      controller: editSubjectPopupController,
      resolve: {
        subjectId: function() {
          return id;
        }
      }
    });
  };

  $scope.createNewSubject = function() {
    $modal.open({
      templateUrl: 'createNewSubjectPopup.html',
      controller: createNewSubjectController,
    });
  };
*/
});

var editSubjectPopupController = function($scope, $modalInstance, $routeParams,
        $http, $route, $templateCache, subjectId, PctService) {

  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };

};

var createNewSubjectController = function($scope, $modalInstance, $routeParams,
        $http, $route, $templateCache, PctService) {

  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };

};