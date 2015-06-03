app.controller("AdminMenuController", function($scope, $http, $location,
        $routeParams) {

  $scope.resources = {};
  $scope.adminId = '';

  $scope.isActive = function(menu) {
    return (menu === $location.path());
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

  $scope.initUserId = function() {
    $scope.adminId = document.getElementById('currentUserId').value;
  };

  $scope.init = function() {
    $scope.loadResources();
    $scope.initUserId();
  };

  $scope.init();
  
  $scope.home = function() {
    $location.path('/adminDetails');
  };

});
