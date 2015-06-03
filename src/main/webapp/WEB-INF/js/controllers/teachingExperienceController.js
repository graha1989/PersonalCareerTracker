app.controller("TeachingExperienceController", function($scope, $routeParams,
        $http, $location, $modal, PctService) {

  $scope.teachingExperiences = [];
  $scope.teachingExperience = {};

  $scope.noResultsFound = true;
  $scope.resources = {};
  $scope.errorMessages = {};
  
  $scope.isUser = false;
  $scope.isAdmin = false;
  $scope.professorId = '';

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
    $scope.loadTeachingExperiences($scope.professorId);
    $scope.loadResources();
    $scope.getCurrentUserRole();
  };

  $scope.init();
  
  $scope.goBack = function() {
    window.history.back();
  };

  $scope.createNewTeachingExperience = function() {
    $modal.open({
      templateUrl: 'createNewTeachingExperiencePopup.html',
      controller: createTeachingExperiencePopupController,
      resolve: {
        teachingExperiences: function() {
          return $scope.teachingExperiences;
        },
        professorId: function() {
          return $scope.professorId;
        }
      }
    });
  };

  $scope.deleteTeachingExperience = function(id, index) {
    PctService.deleteTeachingExperience(id, function(data) {
      if (angular.isObject(data)) {
        $scope.errorStatus = data.status;
      } else {
        $scope.successStatus = "Successfully deleted teaching experience.";
        $scope.teachingExperiences.splice(index, 1);
        $scope.loadTeachingExperiences($scope.professorId);
      }
    });
  };

  $scope.showSurveysForSubject = function(professorId, subjectId) {
    if (professorId != null && professorId != '') {
      $location.path('/surveys/professorId/' + professorId + '/subjectId/'
              + subjectId);
    } else {
      $location.path('/surveys/professorId/' + $scope.professorId + '/subjectId/'
              + subjectId);
    }
  };
  
  $scope.goBack = function() {
    window.history.back();
  };

});

var createTeachingExperiencePopupController = function($scope, $modalInstance,
        $routeParams, $http, $route, $templateCache, PctService,
        teachingExperiences, professorId) {

  $scope.teachingExperience = {};
  $scope.selectedSubject = [];
  $scope.masterSelectedSubject = [];
  $scope.isExistingSubject = false;

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

  $scope.getSubjectIds = function(selectedTeachingExperiences) {
    var subjectsIdsArray = [];
    for (var i = 0; i < selectedTeachingExperiences.length; i++) {
      subjectsIdsArray.push(selectedTeachingExperiences[i].subjectDto.id);
    }
    return subjectsIdsArray;
  };

  $scope.init = function() {
    $scope.status = $routeParams.status;
    $scope.loadResources();
  };

  $scope.init();

  $scope.getSubjects = function(val) {
    var subjectIdsList = $scope.getSubjectIds(teachingExperiences);
    for (var i = 0; i < subjectIdsList.length; i++) {
      if (angular
              .equals(subjectIdsList[i], $scope.teachingExperience.subjectId)) {
        subjectIdsList.splice(i, 1);
      }
    }
    return PctService.findSubjectsStartsWith(val, subjectIdsList).then(
            function(response) {
              var subjects = [];
              for (var i = 0; i < response.length; i++) {
                subjects.push(response[i]);
              }
              return subjects;
            });
  };

  $scope.onSelectSubject = function() {
    $scope.isExistingSubject = true;
    $scope.masterSelectedSubject = angular.copy($scope.selectedSubject);
    $scope.teachingExperience.subjectDto = {};
    $scope.teachingExperience.subjectDto.subjectName = $scope.selectedSubject.subjectName;
    $scope.teachingExperience.subjectDto.studyProgram = $scope.selectedSubject.studyProgram;
    $scope.teachingExperience.subjectDto.institutionName = $scope.selectedSubject.institutionName;
    $scope.teachingExperience.subjectDto.universityName = $scope.selectedSubject.universityName;
    $scope.teachingExperience.subjectDto.institutionCity = $scope.selectedSubject.institutionCity;
    $scope.teachingExperience.subjectDto.institutionCountry = $scope.selectedSubject.institutionCountry;
    $scope.teachingExperience.subjectDto.studiesThesisType = $scope.selectedSubject.studiesThesisType;
    $scope.teachingExperience.subjectDto.numberOfTeachingLessons = $scope.selectedSubject.numberOfTeachingLessons;
    $scope.teachingExperience.subjectDto.numberOfTheoreticalLessons = $scope.selectedSubject.numberOfTheoreticalLessons;
    $scope.teachingExperience.subjectDto.numberOfPracticalLessons = $scope.selectedSubject.numberOfPracticalLessons;
    $scope.teachingExperience.subjectDto.institutionId = $scope.selectedSubject.institutionId;
    $scope.teachingExperience.subjectDto.id = $scope.selectedSubject.id;
  };

  $scope.restartSubjectData = function() {
    $scope.selectedSubject = null;
    $scope.isExistingSubject = false;
    $scope.teachingExperience.subjectDto.subjectName = null;
    $scope.teachingExperience.subjectDto.studyProgram = null;
    $scope.teachingExperience.subjectDto.facultyName = null;
    $scope.teachingExperience.subjectDto.universityName = null;
    $scope.teachingExperience.subjectDto.facultyCity = null;
    $scope.teachingExperience.subjectDto.facultyCountry = null;
    $scope.teachingExperience.subjectDto.studiesThesisType = null;
    $scope.teachingExperience.subjectDto.numberOfTeachingLessons = null;
    $scope.teachingExperience.subjectDto.numberOfTheoreticalLessons = null;
    $scope.teachingExperience.subjectDto.numberOfPracticalLessons = null;
    $scope.teachingExperience.subjectDto.institutionId = null;
    $scope.teachingExperience.subjectDto.id = null;
    $scope.teachingExperience.subjectDto = null;
  };

  $scope.$watch('selectedSubject', function() {
    if ($scope.isExistingSubject
            && !angular.equals($scope.selectedSubject,
                    $scope.masterSelectedSubject)) {
      $scope.restartSubjectData();
    }
  });

  $scope.saveNewTeachingExperience = function() {
    if (!$scope.isExistingSubject) {
      $scope.teachingExperience.subjectDto.subjectName = $scope.selectedSubject;
    }
    $scope.teachingExperience.professorId = professorId;
    $http({
      method: 'POST',
      url: "api/teachingExperiences",
      data: $scope.teachingExperience,
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
    if ($scope.isExistingSubject) {
      return true;
    } else {
      return false;
    }
  };

};