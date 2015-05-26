app.controller("ProjectExperienceController", function($scope, $routeParams,
        $http, $route, $modal, PctService) {

  $scope.allProjectExperiences = [];
  $scope.projectExperience = {};
  $scope.noResultsFound = true;
  $scope.resources = {};
  $scope.errorMessages = {};

  $scope.project = {};
  $scope.completeProjecExperienceDataArray = [];

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

  $scope.constructLeadersString = function(array) {
    var leadersString = "";
    for (var i = 0; i < array.length; i++) {
      leadersString = leadersString + ((i > 0 && i < array.length) ? "; " : "")
              + array[i].name + " " + array[i].surname;
    }
    return leadersString;
  };

  $scope.loadSelectedProject = function(id, index) {
    PctService.loadSelectedProject(id, function(data) {
      if (angular.isObject(data)) {
        $scope.project = data;
        var projectLeaders = $scope
                .constructLeadersString($scope.project.projectLeaderDtos);
        $scope.completeProjecExperienceDataArray.push({
          project: $scope.project,
          projectLeaders: projectLeaders,
          projectExperience: $scope.allProjectExperiences[index]
        });
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };

  $scope.loadProjectExperiences = function(professorId) {
    return PctService.loadProfessorProjectExperiences(professorId).then(
            function(response) {
              if (angular.isObject(response) && response.length > 0) {
                $scope.allProjectExperiences = response;
                $scope.completeProjecExperienceDataArray = [];
                for (var i = 0; i < $scope.allProjectExperiences.length; i++) {
                  $scope.loadSelectedProject(
                          $scope.allProjectExperiences[i].projectId, i);
                }
                $scope.noResultsFound = false;
              } else {
                $scope.noResultsFound = true;
              }
            });
  };

  $scope.init = function() {
    $scope.loadProjectExperiences($routeParams.professorId);
    $scope.loadResources();
  };

  $scope.init();

  $scope.goBack = function() {
    window.history.back();
  };

  $scope.editProjectExperience = function(id) {
    $modal.open({
      templateUrl: 'editProjectExperiencePopup.html',
      controller: editProjectExperiencePopupController,
      resolve: {
        projectExperienceId: function() {
          return id;
        }
      }
    });
  };

  $scope.createNewProjectExperience = function() {
    $modal.open({
      templateUrl: 'createNewProjectExperiencePopup.html',
      controller: createNewProjectExperienceController,
      resolve: {
        projectExperiences: function() {
          return $scope.allProjectExperiences;
        }
      }
    });
  };

  $scope.deleteProjectExperience = function(id, index) {
    PctService.deleteProjectExperience(id, function(data) {
      if (angular.isObject(data)) {
        $scope.errorStatus = data.status;
      } else {
        $scope.successStatus = "Successfully deleted teaching experience.";
        $scope.completeProjecExperienceDataArray.splice(index, 1);
        $scope.allProjectExperiences.splice(index, 1);
        $scope.loadProjectExperiences($routeParams.professorId);
      }
    });
  };

});

var editProjectExperiencePopupController = function($scope, $modalInstance,
        $routeParams, $http, $route, $templateCache, projectExperienceId,
        PctService) {

  $scope.projectExperience = {};
  $scope.master = {};
  $scope.projectLeadersArray = [];
  $scope.project = {};
  $scope.allProjectDataShown = false;
  $scope.isProfessorLeader = false;

  $scope.patterns = {
    onlyLetters: /^[a-zA-ZčČćĆšŠđĐžŽ ]*$/,
    onlyNumbers: /^[0-9 ]*$/
  };

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
  $scope.openEndDate = function($event) {
    $event.preventDefault();
    $event.stopPropagation();

    $scope.inputEndDateOpened = true;
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

  $scope.createLeadersArray = function() {
    var array = [];
    for (var i = 0; i < $scope.project.projectLeaderDtos.length; i++) {
      array.push($scope.project.projectLeaderDtos[i].name + " "
              + $scope.project.projectLeaderDtos[i].surname);
    }
    return array;
  }

  $scope.loadSelectedProject = function(id) {
    PctService.loadSelectedProject(id, function(data) {
      if (angular.isObject(data)) {
        $scope.project = data;
        $scope.projectLeadersArray = $scope.createLeadersArray();
        $scope.isProfessorLeader = $scope.isProfessorLeader();
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };

  $scope.loadSelectedProjectExperience = function(id) {
    PctService.loadSelectedProjectExperience(id, function(data) {
      if (angular.isObject(data)) {
        $scope.projectExperience = data;
        $scope.projectExperience.startDate = new Date(data.startDate);
        $scope.projectExperience.endDate = new Date(data.endDate);
        $scope.loadSelectedProject($scope.projectExperience.projectId);
        $scope.master = angular.copy($scope.projectExperience);
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
    $scope.loadSelectedProjectExperience(projectExperienceId);
    $scope.loadResources();
    $scope.status = $routeParams.status;
  };

  $scope.init();

  $scope.expandProjectData = function() {
    $scope.allProjectDataShown = true;
  };

  $scope.collapseProjectData = function() {
    $scope.allProjectDataShown = false;
  };

  $scope.saveProjectExperience = function() {
    $http({
      method: 'PUT',
      url: "api/projectExperiences",
      data: $scope.projectExperience,
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

  $scope.arrayContainsElement = function(array, element) {
    var index = -1;
    for (var i = 0; i < array.length; i++) {
      var temp = array[i];
      if (temp.professorId === parseInt(element)) {
        index = i;
        break;
      }
    }
    return index;
  };

  $scope.isProfessorLeader = function() {
    var index = $scope.arrayContainsElement($scope.project.projectLeaderDtos,
            $routeParams.professorId);
    if (index == -1) { return false; }
    return true;
  };

  $scope.isUnchanged = function(projectExperience) {
    return angular.equals(projectExperience, $scope.master);
  };

  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };

};

var createNewProjectExperienceController = function($scope, $modalInstance,
        $routeParams, $http, $route, $templateCache, PctService,
        projectExperiences) {

  $scope.projectExperience = {};
  $scope.master = {};
  $scope.selectedProject = [];
  $scope.allProjectTypes = [];
  $scope.projectLeadersArray = [];
  $scope.isProjectSelected = false;
  $scope.isProfessorLeader = false;

  $scope.patterns = {
    onlyLetters: /^[a-zA-ZčČćĆšŠđĐžŽ ]*$/,
    onlyNumbers: /^[0-9 ]*$/
  };

  $scope.dateOptions = {
    "starting-day": "1"
  };

  /* Date picker functions for start date */
  $scope.openProjectStartDate = function($event) {
    $event.preventDefault();
    $event.stopPropagation();

    $scope.inputProjectStartDateOpened = true;
  };

  /* Date picker functions for end date */
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

  $scope.createLeadersArray = function() {
    var array = [];
    for (var i = 0; i < $scope.selectedProject.projectLeaderDtos.length; i++) {
      array.push($scope.selectedProject.projectLeaderDtos[i].name + " "
              + $scope.selectedProject.projectLeaderDtos[i].surname);
    }
    return array;
  }

  $scope.loadAllProjectTypes = function() {
    PctService.loadAllProjectTypes($routeParams, function(data) {
      if (angular.isObject(data) && data.length > 0) {
        $scope.allProjectTypes = data;
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };

  $scope.getProjectsIds = function(selectedProjects) {
    var projectsIdsArray = [];
    for (var i = 0; i < selectedProjects.length; i++) {
      projectsIdsArray.push(selectedProjects[i].projectId);
    }
    return projectsIdsArray;
  };

  $scope.setMaxDate = function() {
    $scope.maxDate = new Date();
  };

  $scope.setMaxDate();

  $scope.init = function() {
    $scope.loadAllProjectTypes();
    $scope.loadResources();
    $scope.status = $routeParams.status;
  };

  $scope.init();

  $scope.getProjects = function(val) {
    var projectExperienceIdsList = $scope.getProjectsIds(projectExperiences);

    return PctService.findProjectsStartsWith(val, projectExperienceIdsList)
            .then(function(response) {
              var projects = [];
              for (var i = 0; i < response.length; i++) {
                projects.push(response[i]);
              }
              return projects;
            });
  };

  $scope.onSelectProject = function() {
    $scope.isProjectSelected = true;
    $scope.master = angular.copy($scope.selectedProject);
    $scope.projectLeadersArray = $scope.createLeadersArray();
    $scope.isProfessorLeader = $scope.isProfessorLeader();
  };

  $scope.restartProjectData = function() {
    $scope.selectedProject = null;
    $scope.isProjectSelected = false;
    $scope.projectLeadersArray = [];
  };

  $scope.$watch('selectedProject', function() {
    if ($scope.isProjectSelected
            && !angular.equals($scope.selectedProject, $scope.master)) {
      $scope.restartProjectData();
    }
  });

  $scope.saveProjectExperience = function() {
    $scope.projectExperience.projectId = $scope.selectedProject.id;
    $scope.projectExperience.professorId = $routeParams.professorId;
    $http({
      method: 'POST',
      url: "api/projectExperiences",
      data: $scope.projectExperience,
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

  $scope.arrayContainsElement = function(array, element) {
    var index = -1;
    for (var i = 0; i < array.length; i++) {
      var temp = array[i];
      if (temp.professorId === parseInt(element)) {
        index = i;
        break;
      }
    }
    return index;
  };

  $scope.isProfessorLeader = function() {
    var index = $scope.arrayContainsElement(
            $scope.selectedProject.projectLeaderDtos, $routeParams.professorId);
    if (index == -1) { return false; }
    return true;
  };

  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };

  $scope.validateForm = function() {
    if ($scope.selectedProject != null && $scope.selectedProject != ''
            && $scope.projectExperience.startDate != null
            && $scope.projectExperience.startDate != ''
            && $scope.projectExperience.endDate != null
            && $scope.projectExperience.endDate != '') {
      return true;
    } else {
      return false;
    }
  };

};