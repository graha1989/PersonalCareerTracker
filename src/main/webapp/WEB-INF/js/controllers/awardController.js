app
        .controller(
                "AwardController",
                function($scope, $routeParams, $http, $route, $modal,
                        PctService) {

                  $scope.awards = [];
                  $scope.award = {};
                  $scope.noResultsFound = true;
                  $scope.resources = {};
                  $scope.errorMessages = {};

                  $scope.isUser = false;
                  $scope.isAdmin = false;
                  $scope.professorId = '';

                  $scope.loadResources = function() {
                    var locale = document.getElementById('localeCode');
                    $http.get(
                            'messages/profesorDetails_' + locale.value
                                    + '.json').success(function(response) {
                      $scope.resources = angular.fromJson(response);
                    });
                    $http.get('messages/errors_' + locale.value + '.json')
                            .success(
                                    function(response) {
                                      $scope.errorMessages = angular
                                              .fromJson(response);
                                    });
                  };

                  $scope.loadAwards = function(professorId) {
                    return PctService.loadProfessorAwards(professorId).then(
                            function(response) {
                              if (angular.isObject(response)
                                      && response.length > 0) {
                                $scope.awards = response;
                                $scope.noResultsFound = false;
                              } else {
                                $scope.noResultsFound = true;
                              }
                            });
                  };

                  $scope.getCurrentUserRole = function() {
                    if (document.getElementById('currentUserRole').value === 'ROLE_USER') {
                      $scope.isUser = true;
                    } else if (document.getElementById('currentUserRole').value === 'ROLE_ADMIN') {
                      $scope.isAdmin = true;
                    }
                  };

                  $scope.initUserId = function() {
                    if ($routeParams.professorId != null
                            && $routeParams.professorId != '') {
                      $scope.professorId = $routeParams.professorId;
                    } else {
                      $scope.professorId = document
                              .getElementById('currentUserId').value;
                    }
                  };

                  $scope.init = function() {
                    $scope.initUserId();
                    $scope.loadAwards($scope.professorId);
                    $scope.loadResources();
                    $scope.getCurrentUserRole();
                  };

                  $scope.init();

                  $scope.goBack = function() {
                    window.history.back();
                  };

                  $scope.deleteAward = function(id, index) {
                    PctService.deleteAward(id, function(data) {
                      if (angular.isObject(data)) {
                        $scope.errorStatus = data.status;
                      } else {
                        $scope.successStatus = "Successfully deleted award.";
                        $scope.awards.splice(index, 1);
                        $scope.loadAwards($scope.professorId);
                      }
                    });
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
                      resolve: {
                        professorId: function() {
                          return $scope.professorId;
                        }
                      }
                    });
                  };

                  $scope.goBack = function() {
                    window.history.back();
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
        $scope.award.awardType = data.awardType.name;
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
    return angular.isUndefined(award.awardField)
            || angular.equals(award, $scope.master);
  };

  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };

};

var createNewAwardController = function($scope, $modalInstance, $routeParams,
        $http, $route, PctService, professorId) {

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
    $scope.award.professorId = professorId;
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