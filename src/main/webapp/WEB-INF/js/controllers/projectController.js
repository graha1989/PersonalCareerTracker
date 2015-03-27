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

  $scope.createNewProjectExperience = function() {
    $modal.open({
      templateUrl: 'createNewProjectExperiencePopup.html',
      controller: createNewProjectExperienceController,
      resolve: {
        projects: function() {
          return $scope.projects;
        }
      }
    });
  };

});

var editProjectExperienceController = function($scope, $modalInstance,
        $routeParams, $http, $route, projectId, PctService) {

  $scope.project = {};
  $scope.master = {};

  $scope.allProjectTypes = [];

  $scope.professor = {};
  $scope.professorNameAndSurname = "";

  $scope.projectLeadersArray = [];
  $scope.projectLeaderCustom = "";

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
  };

  $scope.loadProfessorNameAndSurname = function() {
    PctService.loadProfesor($routeParams.mentorId, function(data) {
      if (angular.isObject(data)) {
        $scope.professor = data;
        $scope.professorNameAndSurname = $scope.professor.name + " "
                + $scope.professor.surname;
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };

  $scope.updateLeadersList = function($event) {
    var checkbox = $event.target;
    var index = $scope.arrayContainsElement($scope.projectLeadersArray,
            $scope.professorNameAndSurname);
    if (checkbox.checked && index == -1) {
      $scope.projectLeadersArray.push($scope.professorNameAndSurname.trim())
    } else if (!checkbox.checked && index !== -1) {
      $scope.projectLeadersArray.splice(index, 1);
    }
    $scope.constructLeadersString($scope.projectLeadersArray);
  };

  $scope.constructLeadersString = function(array) {
    $scope.projectLeaderCustom = "";
    for (var i = 0; i < array.length; i++) {
      $scope.projectLeaderCustom = $scope.projectLeaderCustom
              + ((i > 0 && i < array.length) ? "; " : "") + array[i];
    }
  };

  $scope.arrayContainsElement = function(array, element) {
    var index = -1;
    for (var i = 0; i < array.length; i++) {
      if (array[i] === element) {
        index = i;
        break;
      }
    }
    return index;
  };

  $scope.loadSelectedProject = function(id) {
    PctService
            .loadSelectedProject(
                    id,
                    function(data) {
                      if (angular.isObject(data)) {
                        $scope.project = data;
                        $scope.project.projectType = data.projectType.name;
                        $scope.master = angular.copy($scope.project);
                        for (var i = 0; i < $scope.project.projectLeader
                                .split(";").length; i++) {
                          $scope.projectLeadersArray
                                  .push($scope.project.projectLeader.split(";")[i]
                                          .trim());
                        }
                        $scope
                                .constructLeadersString($scope.projectLeadersArray);
                        $scope.noResultsFound = false;
                      } else {
                        $scope.noResultsFound = true;
                      }
                    });
  };

  $scope.init = function() {
    $scope.loadAllProjectTypes();
    $scope.loadSelectedProject(projectId);
    $scope.loadProfessorNameAndSurname();
    $scope.status = $routeParams.status;
    $scope.loadResources();
  };

  $scope.init();

  $scope.saveProject = function() {
    $scope.project.projectLeader = $scope.projectLeaderCustom;
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

var createNewProjectExperienceController = function($scope, $modalInstance,
        $routeParams, $http, $route, projects, PctService) {

  $scope.project = {};
  $scope.selectedProject = [];
  $scope.allProjectTypes = [];

  $scope.professor = {};
  $scope.professorNameAndSurname = "";

  $scope.projectLeadersArray = [];
  $scope.projectLeaderCustom = "";

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

  $scope.loadProfessorNameAndSurname = function() {
    PctService.loadProfesor($routeParams.mentorId, function(data) {
      if (angular.isObject(data)) {
        $scope.professor = data;
        $scope.professorNameAndSurname = $scope.professor.name + " "
                + $scope.professor.surname;
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };

  $scope.updateLeadersList = function($event) {
    var checkbox = $event.target;
    var index = $scope.arrayContainsElement($scope.projectLeadersArray,
            $scope.professorNameAndSurname);
    if (checkbox.checked && index == -1) {
      $scope.projectLeadersArray.push($scope.professorNameAndSurname.trim())
    } else if (!checkbox.checked && index !== -1) {
      $scope.projectLeadersArray.splice(index, 1);
    }
    $scope.constructLeadersString($scope.projectLeadersArray);
  };

  $scope.constructLeadersString = function(array) {
    $scope.projectLeaderCustom = "";
    for (var i = 0; i < array.length; i++) {
      $scope.projectLeaderCustom = $scope.projectLeaderCustom
              + ((i > 0 && i < array.length) ? "; " : "") + array[i];
    }
  };

  $scope.arrayContainsElement = function(array, element) {
    var index = -1;
    for (var i = 0; i < array.length; i++) {
      if (array[i] === element) {
        index = i;
        break;
      }
    }
    return index;
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
  };

  $scope.getProjectsIds = function(selectedProjects) {
    var projectsIdsArray = [];
    for (var i = 0; i < selectedProjects.length; i++) {
      projectsIdsArray.push(selectedProjects[i].projectId);
    }
    return projectsIdsArray;
  };

  $scope.init = function() {
    $scope.loadAllProjectTypes();
    $scope.loadProfessorNameAndSurname();
    $scope.status = $routeParams.status;
    $scope.loadResources();
  };

  $scope.init();

  $scope.getProjects = function(val) {
    var projectExperienceIdsList = $scope.getProjectsIds(projects);

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
    $scope.project = angular.copy($scope.selectedProject);
    $scope.project.projectType = $scope.project.projectType.name;
    $scope.projectLeaderCustom = $scope.project.projectLeader;
    for (var i = 0; i < $scope.projectLeaderCustom.split(";").length; i++) {
      $scope.projectLeadersArray.push($scope.projectLeaderCustom.split(";")[i]
              .trim());
    }
  };

  $scope.saveProjectExperience = function() {
    $scope.project.professorId = $routeParams.mentorId;
    $scope.project.projectLeader = $scope.projectLeaderCustom;
    $http({
      method: 'POST',
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

  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };

  $scope.validateForm = function() {
    if ($scope.project.projectName != null
            && $scope.project.projectFinancedBy != null
            && $scope.project.projectStartDate != null
            && $scope.project.projectEndDate != null
            && $scope.projectLeaderCustom != null
            && $scope.project.projectType != null) {
      return true;
    } else {
      return false;
    }
  };

};