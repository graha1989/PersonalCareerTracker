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

  $scope.loadSelectedProject = function(id, index) {
    PctService.loadSelectedProject(id, function(data) {
      if (angular.isObject(data)) {
        $scope.project = data;
        $scope.completeProjecExperienceDataArray.push({
          project: $scope.project,
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

});

var editProjectExperiencePopupController = function($scope, $modalInstance,
        $routeParams, $http, $route, $templateCache, projectExperienceId,
        PctService) {

  $scope.projectExperience = {};
  $scope.master = {};
  $scope.projectLeadersArray = [];
  $scope.project = {};
  $scope.professor = {};
  $scope.professorNameAndSurname = "";

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
    for (var i = 0; i < $scope.project.projectLeader.split(";").length; i++) {
      array.push($scope.project.projectLeader.split(";")[i].trim());
    }
    return array;
  }

  $scope.constructLeadersString = function(array) {
    $scope.project.projectLeader = "";
    for (var i = 0; i < array.length; i++) {
      $scope.project.projectLeader = $scope.project.projectLeader
              + ((i > 0 && i < array.length) ? "; " : "") + array[i];
    }
  };

  $scope.loadProfessorNameAndSurname = function() {
    PctService.loadProfesor($routeParams.professorId, function(data) {
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
        $scope.projectLeadersArray = $scope.createLeadersArray();
        $scope.constructLeadersString($scope.projectLeadersArray);
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
    $scope.loadProfessorNameAndSurname();
    $scope.loadResources();
    $scope.status = $routeParams.status;
  };

  $scope.init();

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
  
  $scope.isProfessorLeader = function() {
    var index = $scope.arrayContainsElement($scope.projectLeadersArray, $scope.professorNameAndSurname);
    if (index == -1) {
      return false;
    }
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
        $routeParams, $http, $route, $templateCache, PctService) {

  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };

};