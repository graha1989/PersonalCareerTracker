app.controller("ProjectController", function($scope, $routeParams, $http,
        $route, $modal, PctService) {

  $scope.projects = [];
  $scope.project = {};
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

  $scope.loadProjects = function(professorId) {
    return PctService.loadProfessorProjectExperiences(professorId).then(
            function(response) {
              if (angular.isObject(response) && response.length > 0) {
                $scope.projects = response;
                $scope.noResultsFound = false;
              } else {
                $scope.noResultsFound = true;
              }
            });
  };

  $scope.init = function() {
    $scope.loadProjects($routeParams.mentorId);
    $scope.loadResources();
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
        $scope.loadAwards();
      }
    });
  };

  $scope.editProjectExperience = function(id) {
    $modal.open({
      templateUrl: 'editProjectPopup.html',
      controller: editProjectExperienceController,
      resolve: {
        projectId: function() {
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

var editProjectExperienceController = function($scope, $modalInstance,
        $routeParams, $http, $route, projectId, PctService) {

  $scope.project = {};
  $scope.master = {};

  $scope.allProjectTypes = [];

  $scope.patterns = {
    projectLeadersList: /^[a-zA-ZčČćĆšŠđĐžŽ][a-zA-ZčČćĆšŠđĐžŽ ;]*$/
  };

  /* Date picker functions */
  $scope.openProjectStartDate = function($event) {
    $event.preventDefault();
    $event.stopPropagation();

    $scope.inputProjectStartDateOpened = true;
  };

  /* Date picker functions */
  $scope.openProjectEndDate = function($event) {
    $event.preventDefault();
    $event.stopPropagation();

    $scope.inputProjectEndDateOpened = true;
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

  $scope.loadAllProjectTypes = function() {
    PctService.loadAllProjectTypes($routeParams, function(data) {
      if (angular.isObject(data) && data.length > 0) {
        $scope.allProjectTypes = data;
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  }

  $scope.loadSelectedProject = function(id) {
    PctService.loadSelectedProject(id, function(data) {
      if (angular.isObject(data)) {
        $scope.project = data;
        $scope.project.projectType = data.projectType.name;
        $scope.master = angular.copy($scope.project);
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };

  $scope.init = function() {
    $scope.loadAllProjectTypes();
    $scope.loadSelectedProject(projectId);
    $scope.status = $routeParams.status;
    $scope.loadResources();
  };

  $scope.init();

  $scope.saveProject = function() {
    $http({
      method: 'PUT',
      url: "api/projects",
      data: $scope.project,
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

  $scope.isUnchanged = function(project) {
    project.projectStartDate = new Date(project.projectStartDate).getTime();
    project.projectEndDate = new Date(project.projectEndDate).getTime();
    return angular.equals(project, $scope.master);
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