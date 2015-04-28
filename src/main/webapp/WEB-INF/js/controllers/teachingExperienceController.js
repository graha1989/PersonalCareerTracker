app.controller("TeachingExperienceController", function($scope, $routeParams,
        $http, $location, $modal, PctService) {

  $scope.teachingExperiences = [];
  $scope.teachingExperience = {};

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

  $scope.loadTeachingExperiences = function(professorId) {
    return PctService.loadTeachingExperiences(professorId).then(function(response) {
      if (angular.isObject(response) && response.length > 0) {
        $scope.teachingExperiences = response;
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };

  $scope.init = function() {
    $scope.loadTeachingExperiences($routeParams.professorId);
    $scope.loadResources();
  };

  $scope.init();

  $scope.goBack = function() {
    window.history.back();
  };
/*
  $scope.deleteWorkExperience = function(id, index) {
    PctService.deleteWorkExperience(id, function(data) {
      if (angular.isObject(data)) {
        $scope.errorStatus = data.status;
      } else {
        $scope.successStatus = "Successfully deleted work experience.";
        $scope.workExperiences.splice(index, 1);
        $scope.loadWorkExperiences($routeParams.professorId);
      }
    });
  };

  $scope.editWorkExperience = function(id) {
    $modal.open({
      templateUrl: 'editWorkExperiencePopup.html',
      controller: editWorkExperiencePopupController,
      resolve: {
        workExperienceId: function() {
          return id;
        }
      }
    });
  };

  $scope.createNewWorkExperience = function() {
    $modal.open({
      templateUrl: 'createNewWorkExperiencePopup.html',
      controller: createNewWorkExperienceController,
      resolve: {
        workExperiences: function() {
          return $scope.workExperiences;
        }
      }
    });
  };
*/
});