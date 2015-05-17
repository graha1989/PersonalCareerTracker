app.controller("ProjectsController", function($scope, $routeParams, $http,
        $location, $modal, PctService) {

  $scope.project = {};
  $scope.project.projectLeaderDtos = [];
  $scope.allProjects = [];
  $scope.completeProjectData = [];

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

  $scope.constructLeadersString = function(array) {
    var leadersString = "";
    for (var i = 0; i < array.length; i++) {
      leadersString = leadersString + ((i > 0 && i < array.length) ? "; " : "")
              + array[i].name + " " + array[i].surname;
    }
    return leadersString;
  };

  $scope.makeCompleteProjectData = function(element) {
    var projectLeaders = $scope.constructLeadersString(element.projectLeaderDtos);
    $scope.completeProjectData.push({
      "projectType": element.projectType,
      "name": element.name,
      "financedBy": element.financedBy,
      "id": element.id,
      "projectLeaders": projectLeaders
    });
  };

  $scope.loadAllProjects = function() {
    PctService.loadAllProjects($routeParams, function(data) {
      if (angular.isObject(data) && data.length > 0) {
        $scope.allProjects = data;
        for (var i = 0; i < $scope.allProjects.length; i++) {
          $scope.makeCompleteProjectData($scope.allProjects[i]);
        }
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };

  $scope.init = function() {
    $scope.loadAllProjects();
    $scope.loadResources();
  };

  $scope.init();

  $scope.editProject = function(id) {
    $modal.open({
      templateUrl: 'editProjectPopup.html',
      controller: editProjectPopupController,
      resolve: {
        projectId: function() {
          return id;
        }
      }
    });
  };

  $scope.createNewProject = function() {
    $modal.open({
      templateUrl: 'createNewProjectPopup.html',
      controller: createNewProjectController,
    });
  };

  $scope.deleteProject = function(id, index) {
    PctService.deleteProject(id, function(data) {
      if (angular.isObject(data)) {
        $scope.errorStatus = data.status;
      } else {
        $scope.successStatus = "Successfully deleted project.";
        $scope.allProjects.splice(index, 1);
        $scope.loadAllProjects();
      }
    });
  };

});

var editProjectPopupController = function($scope, $modalInstance, $routeParams,
        $http, $route, $templateCache, projectId, PctService) {

  $scope.project = {};
  $scope.master = {};
  $scope.allProjectTypes = [];
  $scope.projectLeadersArray = [];
  $scope.newProjectLeader = [];
  $scope.isExistingPerson = false;

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
        $scope.master = angular.copy($scope.project);
        $scope.projectLeadersArray = $scope.createLeadersArray();
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };

  $scope.init = function() {
    $scope.loadAllProjectTypes();
    $scope.loadSelectedProject(projectId);
    $scope.loadResources();
    $scope.status = $routeParams.status;
  };

  $scope.init();

  $scope.onSelectPerson = function() {
    $scope.isExistingPerson = true;
  };

  $scope.getAllPotentalLeaders = function(val) {
    return PctService.findProfessorsOrLeadersStartsWith(val).then(
            function(response) {
              var persons = [];
              for (var i = 0; i < response.length; i++) {
                persons.push(response[i]);
              }
              return persons;
            });
  };

  $scope.addProjectLeader = function() {
    if ($scope.isExistingPerson) {
      $scope.project.projectLeaderDtos.push({
        "professorId": $scope.newProjectLeader.professorId,
        "name": $scope.newProjectLeader.name,
        "surname": $scope.newProjectLeader.surname,
        "projectId": $scope.project.id
      });
    };
    $scope.projectLeadersArray = [];
    $scope.projectLeadersArray = $scope.createLeadersArray();
    $scope.newProjectLeader = [];
  };

  $scope.removeProjectLeader = function(index) {
    $scope.projectLeadersArray.splice(index, 1);
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
    return angular.equals(project, $scope.master);
  };

  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };

};

var createNewProjectController = function($scope, $modalInstance, $routeParams,
        $http, $route, $templateCache, PctService) {

  $scope.project = {};
  $scope.allProjectTypes = [];
  $scope.projectLeadersArray = [];
  $scope.newProjectLeader = "";

  $scope.patterns = {
    onlyLetters: /^[a-zA-ZčČćĆšŠđĐžŽ ]*$/,
    onlyNumbers: /^[0-9 ]*$/
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

  $scope.init = function() {
    $scope.loadAllProjectTypes();
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
    $scope.constructLeadersString($scope.projectLeadersArray);
  };

  $scope.saveNewProject = function() {
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

  $scope.validateForm = function() {
    if ($scope.project.name != null && $scope.project.name != ''
            && $scope.project.financedBy != null
            && $scope.project.financedBy != ''
            && $scope.project.projectLeader != null
            && $scope.project.projectLeader != ''
            && $scope.project.projectType != null
            && $scope.project.projectType != '') {
      return true;
    } else {
      return false;
    }
  };

  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };

};