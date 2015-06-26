app.controller("FacultyOrUniversityAuthoritiesWorkController", function($scope, $routeParams, $http, $route, $modal,
        PctService) {

  $scope.works = [];
  $scope.work = {};
  $scope.noResultsFound = true;
  $scope.resources = {};
  $scope.errorMessages = {};

  $scope.isUser = false;
  $scope.isAdmin = false;
  $scope.professorId = '';

  $scope.type = "Učešće u radu organa i tela fakulteta i Univerziteta";

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
    return PctService.loadFacultyOrUniversityWork(professorId, type).then(function(response) {
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
      templateUrl: 'editFacultyOrUniversityAuthoritiesWorkPopup.html',
      controller: editFacultyOrUniversityAuthoritiesWorkController,
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
      templateUrl: 'createNewFacultyOrUniversityAuthoritiesWorkPopup.html',
      controller: createNewFacultyOrUniversityAuthoritiesWorkController,
      resolve: {
        professorId: function() {
          return $scope.professorId;
        }
      }
    });
  };

  $scope.deleteWork = function(id, index) {
    PctService.deleteFacultyOrUniversityAuthoritiesWork(id, function(data) {
      if (angular.isObject(data)) {
        $scope.errorStatus = data.status;
      } else {
        $scope.successStatus = "Successfully deleted faculty or university authorities work.";
        $scope.works.splice(index, 1);
        $scope.loadWorks($scope.professorId, $scope.type);
      }
    });
  };

});

var editFacultyOrUniversityAuthoritiesWorkController = function($scope, $modalInstance, $routeParams, $http, $route,
        workId, PctService, professorId) {

  $scope.work = {};
  $scope.master = {};
  $scope.allInstitutionTypes = ['Fakultet', 'Univerzitet'];

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
    PctService.loadSelectedFacultyOruniversityAuthorityWork(id, function(data) {
      if (angular.isObject(data)) {
        $scope.work = data;
        $scope.work.startDate = new Date(data.startDate);
        $scope.work.endDate = new Date(data.endDate);
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
      url: "api/academicCommunityContribution/saveFacultyOrUniversityAuthorityWork",
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

var createNewFacultyOrUniversityAuthoritiesWorkController = function($scope, $modalInstance, $routeParams, $http,
        $route, PctService, professorId) {

  $scope.work = {};
  $scope.allInstitutionTypes = ['Fakultet', 'Univerzitet'];

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
    $scope.status = $routeParams.status;
    $scope.loadResources();
  };

  $scope.init();

  $scope.saveNewWork = function() {
    $http({
      method: 'POST',
      url: "api/academicCommunityContribution/saveFacultyOrUniversityAuthorityWork",
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
    if ($scope.work.authority != null && $scope.work.authority != '' && $scope.work.institutionType != null
            && $scope.work.institutionType != '' && $scope.work.startDate != null && $scope.work.startDate != ''
            && $scope.work.endDate != null && $scope.work.endDate != '') {
      return true;
    } else {
      return false;
    }
  };

  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };

};
