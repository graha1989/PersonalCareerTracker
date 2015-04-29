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
    return PctService.loadTeachingExperiences(professorId).then(
            function(response) {
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
   * $scope.deleteWorkExperience = function(id, index) {
   * PctService.deleteWorkExperience(id, function(data) { if
   * (angular.isObject(data)) { $scope.errorStatus = data.status; } else {
   * $scope.successStatus = "Successfully deleted work experience.";
   * $scope.workExperiences.splice(index, 1);
   * $scope.loadWorkExperiences($routeParams.professorId); } }); };
   */
  $scope.editTeachingExperience = function(id) {
    $modal.open({
      templateUrl: 'editTeachingExperiencePopup.html',
      controller: editTeachingExperiencePopupController,
      resolve: {
        teachingExperienceId: function() {
          return id;
        },
        teachingExperiences: function() {
          return $scope.teachingExperiences;
        }
      }
    });
  };
  /*
   * $scope.createNewWorkExperience = function() { $modal.open({ templateUrl:
   * 'createNewWorkExperiencePopup.html', controller:
   * createNewWorkExperienceController, resolve: { workExperiences: function() {
   * return $scope.workExperiences; } } }); };
   */
});

var editTeachingExperiencePopupController = function($scope, $modalInstance,
        $routeParams, $http, $route, $templateCache, teachingExperienceId, teachingExperiences,
        PctService) {

  $scope.teachingExperience = {};
  $scope.master = {};
  $scope.allStudiesThesisTypes = [];
  $scope.selectedFaculty = [];
  $scope.selectedSubject = [];
  $scope.isExistingFaculty = false;

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

  $scope.loadAllStudyTypes = function() {
    PctService.loadThesisTypes($routeParams, function(data) {
      if (angular.isObject(data) && data.length > 0) {
        $scope.allStudiesThesisTypes = data;
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };

  $scope.loadSelectedTeachingExperience = function(id) {
    PctService.loadSelectedTeachingExperience(id, function(data) {
      if (angular.isObject(data)) {
        $scope.teachingExperience = data;
        $scope.selectedFaculty = $scope.teachingExperience.facultyName;
        $scope.selectedSubject = $scope.teachingExperience.subjectName;
        $scope.master = angular.copy($scope.workExperience);
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };

  $scope.getSubjectIds = function(selectedTeachingExperiences) {
    var subjectsIdsArray = [];
    for (var i = 0; i < selectedTeachingExperiences.length; i++) {
      subjectsIdsArray.push(selectedTeachingExperiences[i].subjectId);
    }
    return subjectsIdsArray;
  };

  $scope.init = function() {
    $scope.loadAllStudyTypes();
    $scope.loadSelectedTeachingExperience(teachingExperienceId);
    $scope.status = $routeParams.status;
    $scope.loadResources();
  };

  $scope.init();

  $scope.getSubjects = function(val) {
    var subjectIdsList = $scope.getSubjectIds(teachingExperiences);
    for (var i = 0; i < subjectIdsList.length; i++) {
      if (angular.equals(subjectIdsList[i], $scope.teachingExperience.subjectId)) {
        subjectIdsList.splice(i, 1);
      }
    }
    return PctService.findSubjectsStartsWith(val, subjectIdsList).then(function(response) {
      var subjects = [];
      for (var i = 0; i < response.length; i++) {
        subjects.push(response[i]);
      }
      return subjects;
    });
  };

  $scope.getFaculties = function(val) {
    return PctService.findInstutionsStartsWith(val, 'FACULTY').then(
            function(response) {
              var faculties = [];
              for (var i = 0; i < response.length; i++) {
                faculties.push(response[i]);
              }
              return faculties;
            });
  };

};