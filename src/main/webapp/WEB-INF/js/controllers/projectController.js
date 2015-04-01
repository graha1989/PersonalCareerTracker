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

  $scope.deleteProjectExperience = function(id, index) {
    PctService.deleteProjectExperience(id, function(data) {
      if (angular.isObject(data)) {
        $scope.errorStatus = data.status;
      } else {
        $scope.successStatus = "Successfully deleted project experience.";
        $scope.projects.splice(index, 1);
        $scope.loadProjects($routeParams.mentorId);
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
        $routeParams, $http, $route, $templateCache, projectId, PctService) {

  $scope.project = {};
  $scope.master = {};
  $scope.allProjectTypes = [];
  $scope.professor = {};
  $scope.professorNameAndSurname = "";
  $scope.projectLeadersArray = [];
  $scope.newProjectLeader = "";
  $scope.checkbox = angular
          .element($templateCache.get('editProjectPopup.html'))[0].inputIsProfessorLeader;

  $scope.patterns = {
    onlyLetters: /^[a-zA-ZčČćĆšŠđĐžŽ ]*$/,
    onlyNumbers: /^[0-9 ]*$/
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

  $scope.createLeadersArray = function() {
    var array = [];
    for (var i = 0; i < $scope.project.projectLeader.split(";").length; i++) {
      array.push($scope.project.projectLeader.split(";")[i].trim());
    }
    return array;
  }

  $scope.updateLeadersList = function($event) {
    $scope.checkbox = $event.target;
    var index = $scope.arrayContainsElement($scope.projectLeadersArray,
            $scope.professorNameAndSurname);
    if ($scope.checkbox.checked && index == -1) {
      $scope.projectLeadersArray.push($scope.professorNameAndSurname.trim())
    } else if (!$scope.checkbox.checked && index !== -1) {
      $scope.projectLeadersArray.splice(index, 1);
    }
    $scope.constructLeadersString($scope.projectLeadersArray);
  };

  $scope.constructLeadersString = function(array) {
    $scope.project.projectLeader = "";
    for (var i = 0; i < array.length; i++) {
      $scope.project.projectLeader = $scope.project.projectLeader
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
    PctService.loadSelectedProject(id, function(data) {
      if (angular.isObject(data)) {
        $scope.project = data;
        $scope.project.projectType = data.projectType.name;
        $scope.master = angular.copy($scope.project);
        $scope.projectLeadersArray = $scope.createLeadersArray();
        $scope.constructLeadersString($scope.projectLeadersArray);
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

  $scope.addProjectLeader = function() {
    $scope.projectLeadersArray.push($scope.newProjectLeader);
    $scope.constructLeadersString($scope.projectLeadersArray);
    $scope.newProjectLeader = "";
  };

  $scope.removeProjectLeader = function(index) {
    $scope.projectLeadersArray.splice(index, 1);
    var index = $scope.arrayContainsElement($scope.projectLeadersArray,
            $scope.professorNameAndSurname);
    if (index == -1 && $scope.checkbox.checked) {
      $scope.checkbox.checked = false;
    }
    $scope.constructLeadersString($scope.projectLeadersArray);
  };

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

var createNewProjectExperienceController = function($scope, $modalInstance,
        $routeParams, $http, $route, $templateCache, projects, PctService) {

  $scope.project = {};
  $scope.master = {};
  $scope.selectedProject = [];
  $scope.allProjectTypes = [];
  $scope.professor = {};
  $scope.professorNameAndSurname = "";
  $scope.projectLeadersArray = [];
  $scope.newProjectLeader = "";
  $scope.isExistingProject = false;
  $scope.checkbox = angular.element($templateCache
          .get('createNewProjectExperiencePopup.html'))[0].inputIsProfessorLeader;

  $scope.patterns = {
    onlyLetters: /^[a-zA-ZčČćĆšŠđĐžŽ ]*$/,
    onlyNumbers: /^[0-9 ]*$/
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

  $scope.createLeadersArray = function() {
    var array = [];
    for (var i = 0; i < $scope.project.projectLeader.split(";").length; i++) {
      array.push($scope.project.projectLeader.split(";")[i].trim());
    }
    return array;
  }

  $scope.updateLeadersList = function($event) {
    $scope.checkbox = $event.target;
    var index = $scope.arrayContainsElement($scope.projectLeadersArray,
            $scope.professorNameAndSurname);
    $scope.project.professorLeader = $scope.checkbox.checked;
    if ($scope.checkbox.checked && index == -1) {
      $scope.projectLeadersArray.push($scope.professorNameAndSurname.trim())
    } else if (!$scope.checkbox.checked && index !== -1) {
      $scope.projectLeadersArray.splice(index, 1);
    }
    $scope.constructLeadersString($scope.projectLeadersArray);
  };

  $scope.constructLeadersString = function(array) {
    $scope.project.projectLeader = "";
    for (var i = 0; i < array.length; i++) {
      $scope.project.projectLeader = $scope.project.projectLeader
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

  $scope.addProjectLeader = function() {
    $scope.projectLeadersArray.push($scope.newProjectLeader);
    $scope.constructLeadersString($scope.projectLeadersArray);
    $scope.newProjectLeader = "";
  };

  $scope.removeProjectLeader = function(index) {
    $scope.projectLeadersArray.splice(index, 1);
    var index = $scope.arrayContainsElement($scope.projectLeadersArray,
            $scope.professorNameAndSurname);
    if (index == -1 && $scope.checkbox.checked) {
      $scope.checkbox.checked = false;
    }
    $scope.constructLeadersString($scope.projectLeadersArray);
  };

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
    $scope.isExistingProject = true;
    $scope.project = angular.copy($scope.selectedProject);
    $scope.master = angular.copy($scope.selectedProject);
    $scope.project.projectType = $scope.project.projectType.name;
    $scope.projectLeadersArray = $scope.createLeadersArray();
  };

  $scope.saveProjectExperience = function() {
    if (!$scope.isExistingProject) {
      $scope.project.projectName = $scope.selectedProject;
    }
    $scope.project.professorId = $routeParams.mentorId;
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
    if ((($scope.project.projectName != null && $scope.project.projectName != '') || ($scope.selectedProject != null && $scope.selectedProject != ''))
            && $scope.project.projectFinancedBy != null && $scope.project.projectFinancedBy != ''
            && $scope.project.projectStartDate != null && $scope.project.projectStartDate != ''
            && $scope.project.projectEndDate != null && $scope.project.projectEndDate != ''
            && $scope.project.projectLeader != null && $scope.project.projectLeader != ''
            && $scope.project.projectType != null && $scope.project.projectType != '') {
      return true;
    } else {
      return false;
    }
  };

};