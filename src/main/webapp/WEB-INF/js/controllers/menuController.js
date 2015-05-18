app.controller("MenuController", function($scope, $http, $location,
        $routeParams) {

  $scope.thesisTypes = [];
  $scope.resources = {};
  $scope.professorId = '';

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

  $scope.loadThesisTypes = function() {
    $http({
      method: 'GET',
      url: "api/thesis/allThesisTypes",
      headers: {
        'Content-Type': 'application/json'
      }
    }).success(function(data, status) {
      if (angular.isObject(data)) {
        $scope.thesisTypes = data;
      }
    }).error(function(data, status) {
    });
  };
  
  $scope.initUserId = function() {
    console.log('Setting professor id: ');
    $scope.professorId = document.getElementById('currentUserId').value;
    console.log($scope.professorId);
  };

  $scope.init = function() {
    $scope.loadThesisTypes();
    $scope.loadResources();
    $scope.initUserId();
  };

  $scope.init();

  $scope.openBachelorMentoring = function() {
    $location.path('/bachelorMentoring/mentorId/' + $scope.professorId
            + '/thesisTypeId/' + $scope.thesisTypes[0].id);
  };

});
