app.controller("CommitteesAndLegislativeBodiesWorkController", function($scope, $routeParams, $http, $route, $modal,
        PctService) {

  $scope.works = [];
  $scope.work = {};
  $scope.noResultsFound = true;
  $scope.resources = {};
  $scope.errorMessages = {};

  $scope.isUser = false;
  $scope.isAdmin = false;
  $scope.professorId = '';

  $scope.type = "Učešće u radu odbora i zakonodavnih tela";

  $scope.loadResources = function() {
    var locale = document.getElementById('localeCode');
    $http.get('messages/profesorDetails_' + locale.value + '.json').success(function(response) {
      $scope.resources = angular.fromJson(response);
    });
    $http.get('messages/errors_' + locale.value + '.json').success(function(response) {
      $scope.errorMessages = angular.fromJson(response);
    });
  };

  $scope.loadWorks = function(professorId, type) {
    return PctService.loadAcademicCommunityContributions(professorId, type).then(function(response) {
      if (angular.isObject(response) && response.length > 0) {
        $scope.works = response;
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
    $scope.loadWorks($scope.professorId, $scope.type);
    $scope.loadResources();
    $scope.getCurrentUserRole();
  };

  $scope.init();

  $scope.goBack = function() {
    window.history.back();
  };

  $scope.editWork = function(id) {
    $modal.open({
      templateUrl: 'editCommitteeAndLegislativeBodiesWorkPopup.html',
      controller: editCommitteeAndLegislativeBodiesWorkController,
      resolve: {
        workId: function() {
          return id;
        },
        professorId: function() {
          return $scope.professorId;
        }
      }
    });
  };

  $scope.createNewWork = function() {
    $modal.open({
      templateUrl: 'createNewCommitteeAndLegislativeBodiesWorkPopup.html',
      controller: createNewCommitteeAndLegislativeBodiesWorkController,
      resolve: {
        professorId: function() {
          return $scope.professorId;
        }
      }
    });
  };

  $scope.deleteWork = function(id, index) {
    PctService.deleteAcademicCommunityContribution(id, function(data) {
      if (angular.isObject(data)) {
        $scope.errorStatus = data.status;
      } else {
        $scope.successStatus = "Successfully deleted committee and legislative bodies work.";
        $scope.works.splice(index, 1);
        $scope.loadWorks($scope.professorId, $scope.type);
      }
    });
  };

});

var editCommitteeAndLegislativeBodiesWorkController = function($scope, $modalInstance, $routeParams, $http, $route,
        workId, PctService, professorId) {

  $scope.work = {};
  $scope.master = {};

  $scope.dateOptions = {
    "starting-day": "1"
  };

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

  $scope.loadSelectedWork = function(id) {
    PctService.loadSelectedAcademicCommunityContribution(id, function(data) {
      if (angular.isObject(data)) {
        $scope.work = data;
        $scope.work.authorityOrOrganizationWorkStartDate = new Date(data.authorityOrOrganizationWorkStartDate);
        $scope.work.authorityOrOrganizationWorkEndDate = new Date(data.authorityOrOrganizationWorkEndDate);
        $scope.master = angular.copy($scope.work);
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
    $scope.loadSelectedWork(workId);
    $scope.status = $routeParams.status;
    $scope.loadResources();
  };

  $scope.init();

  $scope.saveWork = function() {
    $http({
      method: 'PUT',
      url: "api/academicCommunityContribution/saveAcademicCommunityContribution",
      data: $scope.work,
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

  $scope.isUnchanged = function(work) {
    return angular.equals(work, $scope.master);
  };

  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };

};

var createNewCommitteeAndLegislativeBodiesWorkController = function($scope, $modalInstance, $routeParams, $http,
        $route, PctService, professorId) {

  $scope.work = {};

  $scope.dateOptions = {
    "starting-day": "1"
  };

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
    $scope.work.professorId = professorId;
    $scope.work.type = {
      name: "WORK_IN_COMMITTEES_AND_LEGISLATIVE_BODIES",
      title: "Učešće u radu odbora i zakonodavnih tela"
    };
    $scope.status = $routeParams.status;
    $scope.loadResources();
  };

  $scope.init();

  $scope.saveNewWork = function() {
    $http({
      method: 'POST',
      url: "api/academicCommunityContribution/saveAcademicCommunityContribution",
      data: $scope.work,
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
    if ($scope.work.authorityOrganizationOrJournal != null && $scope.work.authorityOrganizationOrJournal != ''
            && $scope.work.functionInOrganizationConferenceOrCommittee != null
            && $scope.work.functionInOrganizationConferenceOrCommittee != ''
            && $scope.work.authorityOrOrganizationWorkStartDate != null
            && $scope.work.authorityOrOrganizationWorkStartDate != ''
            && $scope.work.authorityOrOrganizationWorkEndDate != null
            && $scope.work.authorityOrOrganizationWorkEndDate != '') {
      return true;
    } else {
      return false;
    }
  };

  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };

};
