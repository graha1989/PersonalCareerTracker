app.controller("ContestController", function($scope, $routeParams, $http, $location, $modal, PctService) {

  $scope.loadResources = function() {
    var locale = document.getElementById('localeCode');
    $http.get('messages/profesorDetails_' + locale.value + '.json').success(function(response) {
      $scope.resources = angular.fromJson(response);
    });
    $http.get('messages/errors_' + locale.value + '.json').success(function(response) {
      $scope.errorMessages = angular.fromJson(response);
    });
  };

  $scope.init = function() {
    $scope.loadResources();
  };

  $scope.init();

});