app.controller("AllProfessorsController", function($scope, $routeParams, $http,
        $location, $modal, PctService) {

  $scope.professor = {};
  $scope.allProfessors = [];

  $scope.noResultsFound = true;
  $scope.resources = {};
  $scope.errorMessages = {};
  
  $scope.sortType = '';

  $scope.allSortTypes = [{
    description: "imenu-rastuće",
    sortBy: "+name"
  }, {
    description: "imenu-opadajuće",
    sortBy: "-name"
  }, {
    description: "prezimenu-rastuće",
    sortBy: "+surname"
  }, {
    description: "prezimenu-opadajuće",
    sortBy: "-surname"
  }];
  
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

  $scope.loadAllProfessors = function() {
    PctService.loadAllProfessors($routeParams, function(data) {
      if (angular.isObject(data) && data.length > 0) {
        $scope.allProfessors = data;
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };

  $scope.init = function() {
    $scope.loadAllProfessors();
    $scope.loadResources();
  };

  $scope.init();

  $scope.showProfessorDetails = function(id) {
    $modal.open({
      templateUrl: 'professorDetailsPopup.html',
      controller: professorDetailsPopupController,
      resolve: {
        professorId: function() {
          return id;
        }
      }
    });
  };
  
  $scope.showProfessorTeahingExperience = function(id) {
    $location.path('/teachingExperience/professorId/' + id);
  };
  
});

var professorDetailsPopupController = function($scope, $modalInstance,
        $routeParams, $http, $route, professorId, PctService) {

  $scope.professor = {};

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

  $scope.loadSelectedProfessor = function(id) {
    PctService.loadProfesor(id, function(data) {
      if (angular.isObject(data)) {
        $scope.professor = data;
        $scope.professor.dateOfBirth = new Date(data.dateOfBirth);
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };

  $scope.init = function() {
    $scope.loadSelectedProfessor(professorId);
    $scope.status = $routeParams.status;
    $scope.loadResources();
  };

  $scope.init();

  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };

};