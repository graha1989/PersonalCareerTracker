app.controller("ScientificProfessionalOrgMemController", function($scope,
        $routeParams, $http, $route, $modal, PctService) {

  $scope.memberships = [];
  $scope.membership = {};
  $scope.noResultsFound = true;
  $scope.resources = {};
  $scope.errorMessages = {};

  $scope.isUser = false;
  $scope.isAdmin = false;
  $scope.professorId = '';

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
  
  $scope.loadMemberships = function(professorId) {
    return PctService.loadProfessorOrganizationMemberships(professorId).then(
            function(response) {
              if (angular.isObject(response)
                      && response.length > 0) {
                $scope.memberships = response;
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
    $scope.loadMemberships($scope.professorId);
    $scope.loadResources();
    $scope.getCurrentUserRole();
  };

  $scope.init();
  
  $scope.editMembership = function(id) {
    $modal.open({
      templateUrl: 'editMembershipPopup.html',
      controller: editMembershipController,
      resolve: {
        membershipId: function() {
          return id;
        }
      }
    });
  };

  $scope.createNewMembership = function() {
    $modal.open({
      templateUrl: 'createNewMembershipPopup.html',
      controller: createNewMembershipController,
      resolve: {
        professorId: function() {
          return $scope.professorId;
        }
      }
    });
  };
  
  $scope.deleteMembership = function(id, index) {
    PctService.deleteProfessorMembershipInOrganization(id, function(data) {
      if (angular.isObject(data)) {
        $scope.errorStatus = data.status;
      } else {
        $scope.successStatus = "Successfully deleted membership.";
        $scope.memberships.splice(index, 1);
        $scope.loadMemberships($scope.professorId);
      }
    });
  };

  $scope.goBack = function() {
    window.history.back();
  };

});

var editMembershipController = function($scope, $modalInstance, $routeParams, $http,
        $route, membershipId, PctService) {

  $scope.membership = {};
  $scope.master = {};
  
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
  
  $scope.loadSelectedMembership = function(id) {
    PctService.loadProfessorOrganizationMembership(id, function(data) {
      if (angular.isObject(data)) {
        $scope.membership = data;
        $scope.master = angular.copy($scope.membership);
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };
  
  $scope.init = function() {
    $scope.loadSelectedMembership(membershipId);
    $scope.status = $routeParams.status;
    $scope.loadResources();
  };

  $scope.init();
  
  $scope.saveMembership = function() {
    $http({
      method: 'PUT',
      url: "api/scientificProfessionalOrganisationMembership",
      data: $scope.membership,
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
  
  $scope.isUnchanged = function(membership) {
    return angular.equals(membership, $scope.master);
  };
  
  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };

};

var createNewMembershipController = function($scope, $modalInstance, $routeParams,
        $http, $route, PctService, professorId) {
  
  $scope.membership = {};

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
  
  $scope.init = function() {
    $scope.status = $routeParams.status;
    $scope.loadResources();
  };

  $scope.init();
  
  $scope.saveNewMembership = function() {
    $scope.membership.professorId = professorId;
    $http({
      method: 'POST',
      url: "api/scientificProfessionalOrganisationMembership",
      data: $scope.membership,
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