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

  $scope.createNewInstitution = function() {
    $modal.open({
      templateUrl: 'createNewInstitutionPopup.html',
      controller: createNewInstitutionController,
    });
  };

  $scope.deleteInstitution = function(id, index) {
    PctService.deleteInstitution(id, function(data) {
      if (angular.isObject(data)) {
        $scope.errorStatus = data.status;
      } else {
        $scope.successStatus = "Successfully deleted institution.";
        $scope.allInstitutions.splice(index, 1);
        $scope.loadAllInstitutions();
      }
    });
  };

});

var editInstitutionPopupController = function($scope, $modalInstance,
        $routeParams, $http, $route, $templateCache, institutionId, PctService) {

  $scope.institution = {};
  $scope.master = {};
  $scope.allInstitutionTypes = [];

  $scope.noResultsFound = true;
  $scope.resources = {};
  $scope.errorMessages = {};

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
    PctService.loadSelectedInstitution(id, function(data) {
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

  $scope.resetInsitutionData = function() {
    $scope.institution.name = null;
    $scope.institution.university = null;
    $scope.institution.city = null;
    $scope.institution.country = null;
  };

  $scope.isUnchanged = function(institution) {
    return angular.equals(institution, $scope.master);
  };

  $scope.saveInstitution = function() {
    $http({
      method: 'PUT',
      url: "api/institutions",
      data: $scope.institution,
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

  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };

};

var createNewInstitutionController = function($scope, $modalInstance,
        $routeParams, $http, $route, $templateCache, PctService) {

  $scope.institution = {};
  $scope.allInstitutionTypes = [];

  $scope.noResultsFound = true;
  $scope.resources = {};
  $scope.errorMessages = {};

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

  $scope.init = function() {
    $scope.loadAllInstitutionTypes();
    $scope.loadResources();
    $scope.status = $routeParams.status;
  };

  $scope.init();

  $scope.saveNewInstitution = function() {
    $http({
      method: 'POST',
      url: "api/institutions",
      data: $scope.institution,
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
    if ($scope.institution.institutionType != null
            && $scope.institution.institutionType != ''
            && ($scope.institution.institutionType == 'FACULTY'
                    ? ($scope.institution.university != null && $scope.institution.university != '')
                    : true) && $scope.institution.city != null
            && $scope.institution.city != ''
            && $scope.institution.country != null
            && $scope.institution.country != '') {
      return true;
    } else {
      return false;
    }
  };

  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };
};