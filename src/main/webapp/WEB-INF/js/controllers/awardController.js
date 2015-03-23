app.controller("AwardController", function($scope, $routeParams, $http, $route,
        $modal, PctService) {

  $scope.awards = [];
  $scope.award = {};
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

  $scope.loadAwards = function(mentorId) {
    PctService.loadAwards(mentorId, function(data) {
      if (angular.isObject(data) && data.length > 0) {
        $scope.awards = data;
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };

  $scope.init = function() {
    $scope.loadAwards($routeParams.mentorId);
    $scope.loadResources();
  };

  $scope.init();

  $scope.goBack = function() {
    window.history.back();
  };

  $scope.editAward = function(id) {
    $modal.open({
      templateUrl: 'editAwardPopup.html',
      controller: editAwardController,
      resolve: {
        awardId: function() {
          return id;
        }
      }
    });
  };

  $scope.createNewAward = function() {
    $modal.open({
      templateUrl: 'createNewAwardPopup.html',
      controller: createNewAwardController,
    });
  };

});

var editAwardController = function($scope, $modalInstance, $routeParams, $http,
        $route, awardId, PctService) {

  $scope.award = {};
  $scope.master = {};

  $scope.allAwardTypes = [];
  $scope.allAwardFields = [];

  /* Date picker functions */
  $scope.open = function($event) {
    $event.preventDefault();
    $event.stopPropagation();

    $scope.opened = true;
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

  $scope.loadAllAwardTypes = function() {
    PctService.loadAllAwardTypes($routeParams, function(data) {
      if (angular.isObject(data) && data.length > 0) {
        $scope.allAwardTypes = data;
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  }

  $scope.loadAllAwardFields = function() {
    PctService.loadAllAwardFields($routeParams, function(data) {
      if (angular.isObject(data) && data.length > 0) {
        $scope.allAwardFields = data;
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  }

  $scope.loadSelectedAward = function(id) {
    PctService.loadSelectedAward(id, function(data) {
      if (angular.isObject(data)) {
        $scope.award = data;
        $scope.master = angular.copy($scope.award);
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };

  $scope.init = function() {
    $scope.loadAllAwardTypes();
    $scope.loadAllAwardFields();
    $scope.loadSelectedAward(awardId);
    $scope.status = $routeParams.status;
    $scope.loadResources();
  };

  $scope.init();

  $scope.saveAward = function() {
    $http({
      method: 'PUT',
      url: "api/awards",
      data: $scope.award,
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

  $scope.isUnchanged = function(award) {
    award.dateOfAward = new Date(award.dateOfAward).getTime();
    return angular.equals(award, $scope.master);
  };

  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };

};

var createNewAwardController = function($scope, $modalInstance, $routeParams,
        $http, $route, PctService) {

  $scope.award = {};

  $scope.allAwardTypes = [];
  $scope.allAwardFields = [];

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

  $scope.loadAllAwardTypes = function() {
    PctService.loadAllAwardTypes($routeParams, function(data) {
      if (angular.isObject(data) && data.length > 0) {
        $scope.allAwardTypes = data;
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  }

  $scope.loadAllAwardFields = function() {
    PctService.loadAllAwardFields($routeParams, function(data) {
      if (angular.isObject(data) && data.length > 0) {
        $scope.allAwardFields = data;
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  }

  /* Date picker functions */
  $scope.open = function($event) {
    $event.preventDefault();
    $event.stopPropagation();

    $scope.opened = true;
  };

  $scope.init = function() {
    $scope.loadAllAwardTypes();
    $scope.loadAllAwardFields();
    $scope.award.mentorId = $routeParams.mentorId;
    $scope.status = $routeParams.status;
    $scope.loadResources();
  };

  $scope.init();

  $scope.saveNewAward = function() {
    $http({
      method: 'POST',
      url: "api/awards",
      data: $scope.award,
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