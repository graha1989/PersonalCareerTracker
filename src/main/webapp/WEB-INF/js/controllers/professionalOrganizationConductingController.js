app.controller("ProfessionalOrganizationConductingController", function($scope, $routeParams, $http, $route, $modal, PctService) {

  $scope.conductings = [];
  $scope.conduction = {};
  $scope.noResultsFound = true;
  $scope.resources = {};
  $scope.errorMessages = {};

  $scope.isUser = false;
  $scope.isAdmin = false;
  $scope.professorId = '';

  $scope.type = "Vodjenje profesionalnih i strukovnih organizacija";

  $scope.loadResources = function() {
    var locale = document.getElementById('localeCode');
    $http.get('messages/profesorDetails_' + locale.value + '.json').success(function(response) {
      $scope.resources = angular.fromJson(response);
    });
    $http.get('messages/errors_' + locale.value + '.json').success(function(response) {
      $scope.errorMessages = angular.fromJson(response);
    });
  };

  $scope.loadConductions = function(professorId, type) {
    return PctService.loadAcademicCommunityContributions(professorId, type).then(function(response) {
      if (angular.isObject(response) && response.length > 0) {
        $scope.conductings = response;
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
    if ($routeParams.professorId != null && $routeParams.professorId != '') {
      $scope.professorId = $routeParams.professorId;
    } else {
      $scope.professorId = document.getElementById('currentUserId').value;
    }
  };

  $scope.init = function() {
    $scope.initUserId();
    $scope.loadConductions($scope.professorId, $scope.type);
    $scope.loadResources();
    $scope.getCurrentUserRole();
  };

  $scope.init();

  $scope.goBack = function() {
    window.history.back();
  };

  $scope.editConduction = function(id) {
    $modal.open({
      templateUrl: 'editProfessionalOrganizationConductionPopup.html',
      controller: editProfessionalOrganizationConductionController,
      resolve: {
        conductionId: function() {
          return id;
        },
        professorId: function() {
          return $scope.professorId;
        }
      }
    });
  };

  $scope.createNewConduction = function() {
    $modal.open({
      templateUrl: 'createNewProfessionalOrganizationConductionPopup.html',
      controller: createNewProfessionalOrganizationConductionController,
      resolve: {
        professorId: function() {
          return $scope.professorId;
        }
      }
    });
  };

  $scope.deleteConduction = function(id, index) {
    PctService.deleteAcademicCommunityContribution(id, function(data) {
      if (angular.isObject(data)) {
        $scope.errorStatus = data.status;
      } else {
        $scope.successStatus = "Successfully deleted faculty or university authorities work.";
        $scope.conductings.splice(index, 1);
        $scope.loadConductions($scope.professorId, $scope.type);
      }
    });
  };

});

var editProfessionalOrganizationConductionController = function($scope, $modalInstance, $routeParams, $http, $route, conductionId, PctService,
        professorId) {

  $scope.conduction = {};
  $scope.master = {};

  $scope.dateOptions = {
    "starting-day": "1"
  };

  $scope.functions = [{
    "name": "Predsednik"
  }, {
    "name": "Organizator"
  }, {
    "name": "Član"
  }, {
    "name": "Drugo"
  }];

  /* Date picker functions for start date */
  $scope.openStartDate = function($event) {
    $event.preventDefault();
    $event.stopPropagation();

    $scope.inputStartDateOpened = true;
  };

  /* Date picker functions for end date */
  $scope.openWorkEndDate = function($event) {
    $event.preventDefault();
    $event.stopPropagation();

    $scope.inputEndDateOpened = true;
  };

  $scope.loadResources = function() {
    var locale = document.getElementById('localeCode');
    $http.get('messages/profesorDetails_' + locale.value + '.json').success(function(response) {
      $scope.resources = angular.fromJson(response);
    });
    $http.get('messages/errors_' + locale.value + '.json').success(function(response) {
      $scope.errorMessages = angular.fromJson(response);
    });
  };

  $scope.loadSelectedConduction = function(id) {
    PctService.loadSelectedAcademicCommunityContribution(id, function(data) {
      if (angular.isObject(data)) {
        $scope.conduction = data;
        $scope.conduction.authorityOrOrganizationWorkStartDate = new Date(data.authorityOrOrganizationWorkStartDate);
        $scope.conduction.authorityOrOrganizationWorkEndDate = new Date(data.authorityOrOrganizationWorkEndDate);
        $scope.master = angular.copy($scope.conduction);
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };

  $scope.setMaxDate = function() {
    $scope.maxDate = new Date();
  };

  $scope.setMaxDate();

  $scope.init = function() {
    $scope.loadSelectedConduction(conductionId);
    $scope.status = $routeParams.status;
    $scope.loadResources();
  };

  $scope.init();

  $scope.saveConduction = function() {
    $http({
      method: 'PUT',
      url: "api/academicCommunityContribution/saveAcademicCommunityContribution",
      data: $scope.conduction,
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

  $scope.isUnchanged = function(conduction) {
    return angular.equals(conduction, $scope.master);
  };

  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };

};

var createNewProfessionalOrganizationConductionController = function($scope, $modalInstance, $routeParams, $http, $route, PctService, professorId) {

  $scope.conduction = {};

  $scope.dateOptions = {
    "starting-day": "1"
  };

  $scope.functions = [{
    "name": "Predsednik"
  }, {
    "name": "Organizator"
  }, {
    "name": "Član"
  }, {
    "name": "Drugo"
  }];

  /* Date picker functions for start date */
  $scope.openStartDate = function($event) {
    $event.preventDefault();
    $event.stopPropagation();

    $scope.inputStartDateOpened = true;
  };

  /* Date picker functions for end date */
  $scope.openWorkEndDate = function($event) {
    $event.preventDefault();
    $event.stopPropagation();

    $scope.inputEndDateOpened = true;
  };

  $scope.loadResources = function() {
    var locale = document.getElementById('localeCode');
    $http.get('messages/profesorDetails_' + locale.value + '.json').success(function(response) {
      $scope.resources = angular.fromJson(response);
    });
    $http.get('messages/errors_' + locale.value + '.json').success(function(response) {
      $scope.errorMessages = angular.fromJson(response);
    });
  };

  $scope.setMaxDate = function() {
    $scope.maxDate = new Date();
  };

  $scope.setMaxDate();

  $scope.init = function() {
    $scope.conduction.professorId = professorId;
    $scope.conduction.type = {
      name: "PROFESSIONAL_ORGANIZATIONS_CONDUCTION",
      title: "Vodjenje profesionalnih i strukovnih organizacija"
    };
    $scope.status = $routeParams.status;
    $scope.loadResources();
  };

  $scope.init();

  $scope.saveNewConduction = function() {
    $http({
      method: 'POST',
      url: "api/academicCommunityContribution/saveAcademicCommunityContribution",
      data: $scope.conduction,
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
    if ($scope.conduction.authorityOrganizationOrJournal != null && $scope.conduction.authorityOrganizationOrJournal != ''
            && $scope.conduction.functionInOrganizationConferenceOrCommittee != null
            && $scope.conduction.functionInOrganizationConferenceOrCommittee != '' && $scope.conduction.authorityOrOrganizationWorkStartDate != null
            && $scope.conduction.authorityOrOrganizationWorkStartDate != '' && $scope.conduction.authorityOrOrganizationWorkEndDate != null
            && $scope.conduction.authorityOrOrganizationWorkEndDate != '') {
      return true;
    } else {
      return false;
    }
  };

  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };

};
