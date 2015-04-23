app.controller("ProfessorBachelorStudiesController", function($scope,
        $routeParams, $http, $location, $modal, PctService) {

  $scope.bachelorStudies = {};
  $scope.allBachelorStudies = [];

  $scope.noResultsFound = true;
  $scope.resources = {};
  $scope.errorMessages = {};

  $scope.editMode = false;

  $scope.patterns = {
    onlyLetters: /^[a-zA-ZčČćĆšŠđĐžŽ ]*$/,
    onlyNumbers: /^[0-9 ]*$/
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
                $scope.noResultsFound = false;
              } else {
                $scope.noResultsFound = true;
              }
            });
  };

  $scope.init = function() {
    $scope.loadProfessorsBachelorStudies($routeParams.professorId,
            $routeParams.thesisTypeId);
    $scope.loadResources();
  };

  $scope.init();

  $scope.goBack = function() {
    window.history.back();
  };

});