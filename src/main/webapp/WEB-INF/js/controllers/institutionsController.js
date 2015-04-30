app.controller("InstitutionsController", function($scope, $routeParams, $http,
        $location, $modal, PctService) {

  $scope.institution = {};
  $scope.allInstitutions = [];

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

  $scope.loadAllInstitutions = function() {
    PctService.loadAllInstitutions($routeParams, function(data) {
      if (angular.isObject(data) && data.length > 0) {
        $scope.allInstitutions = data;
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };

  $scope.init = function() {
    $scope.loadAllInstitutions();
    $scope.loadResources();
  };

  $scope.init();

  $scope.editInstitution = function(id) {
    $modal.open({
      templateUrl: 'editInstitutionPopup.html',
      controller: editInstitutionPopupController,
      resolve: {
        institutionId: function() {
          return id;
        }
      }
    });
  };
});

var editInstitutionPopupController = function($scope, $modalInstance,
        $routeParams, $http, $route, $templateCache, institutionId, PctService) {

  $scope.institution = {};
  $scope.master = {};
  $scope.allInstitutionTypes = [];

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

  /*
   * TODO call method from Institution api controller
   */
  $scope.loadAllInstitutionTypes = function() {
    PctService.loadAllInstitutionTypes($routeParams, function(data) {
      if (angular.isObject(data) && data.length > 0) {
        $scope.allInstitutionTypes = data;
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };

  $scope.loadSelectedInstitution = function(id) {
    PctService.loadSelectedWorkExperience(id, function(data) {
      if (angular.isObject(data)) {
        $scope.institution = data;
        $scope.master = angular.copy($scope.institution);
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };

  $scope.init = function() {
    $scope.loadAllInstitutionTypes();
    $scope.loadSelectedInstitution(institutionId);
    $scope.loadResources();
    $scope.status = $routeParams.status;
  };

  $scope.init();

  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };

};

var createNewWorkExperienceController = function($scope, $modalInstance,
        $routeParams, $http, $route, $templateCache, workExperiences,
        PctService) {

  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };
};