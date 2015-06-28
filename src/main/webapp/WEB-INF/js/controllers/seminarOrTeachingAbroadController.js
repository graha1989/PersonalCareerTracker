app.controller("SeminarOrTeachingAbroadController", function($scope, $routeParams, $http, $location, $modal, PctService) {

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
    $http.get('messages/profesorDetails_' + locale.value + '.json').success(function(response) {
      $scope.resources = angular.fromJson(response);
    });
    $http.get('messages/errors_' + locale.value + '.json').success(function(response) {
      $scope.errorMessages = angular.fromJson(response);
    });
  };

  $scope.loadTeachingExperiences = function(professorId) {
    return PctService.loadTeachingExperiences(professorId, true).then(function(response) {
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

  $scope.editSeminarOrTeachingExperience = function(id) {
    $modal.open({
      templateUrl: 'editSeminarOrTeachingExperiencePopup.html',
      controller: editSeminarOrTeachingExperiencePopupController,
      resolve: {
        teachingExperienceId: function() {
          return id;
        },
        professorId: function() {
          return $scope.professorId;
        }
      }
    });
  };

  $scope.createNewTeachingExperience = function() {
    $modal.open({
      templateUrl: 'createNewSeminarOrTeachingExperiencePopup.html',
      controller: createNewSeminarOrTeachingExperiencePopupController,
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

  $scope.goBack = function() {
    window.history.back();
  };

});

var editSeminarOrTeachingExperiencePopupController = function($scope, $modalInstance, $routeParams, $http, $route, $templateCache, PctService,
        professorId, teachingExperienceId) {

  $scope.teachingExperience = {};
  $scope.master = {};
  $scope.allSubjectDataShown = false;

  $scope.patterns = {
    onlyLetters: /^[a-zA-ZčČćĆšŠđĐžŽ ]*$/,
    onlyNumbers: /^[0-9 ]*$/
  };

  $scope.dateOptions = {
    "starting-day": "1"
  };

  /* Date picker functions for start date */
  $scope.openTeachingStartDate = function($event) {
    $event.preventDefault();
    $event.stopPropagation();

    $scope.inputTeachingStartDateOpened = true;
  };

  /* Date picker functions for end date */
  $scope.openTeachingEndDate = function($event) {
    $event.preventDefault();
    $event.stopPropagation();

    $scope.inputTeachingEndDateOpened = true;
  };

  $scope.loadSelectedTeachingExperience = function(id) {
    PctService.loadSelectedTeachingExperience(id, function(data) {
      if (angular.isObject(data)) {
        $scope.teachingExperience = data;
        $scope.teachingExperience.teachingStartDate = new Date(data.teachingStartDate);
        $scope.teachingExperience.teachingEndDate = (data.teachingEndDate != null ? new Date(data.teachingEndDate) : null);
        $scope.master = angular.copy($scope.teachingExperience);
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
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
    $scope.loadSelectedTeachingExperience(teachingExperienceId);
    $scope.status = $routeParams.status;
    $scope.loadResources();
  };

  $scope.init();

  $scope.expandSubjectData = function() {
    $scope.allSubjectDataShown = true;
  };

  $scope.collapseSubjectData = function() {
    $scope.allSubjectDataShown = false;
  };

  $scope.saveTeachingExperience = function() {
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

  $scope.isUnchanged = function(teachingExperience) {
    return angular.equals(teachingExperience, $scope.master);
  };

  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };

};

var createNewSeminarOrTeachingExperiencePopupController = function($scope, $modalInstance, $routeParams, $http, $route, $templateCache, PctService,
        teachingExperiences, professorId) {

  $scope.teachingExperience = {};
  $scope.selectedSubject = [];
  $scope.masterSelectedSubject = [];
  $scope.isExistingSubject = false;

  $scope.patterns = {
    onlyLetters: /^[a-zA-ZčČćĆšŠđĐžŽ ]*$/,
    onlyNumbers: /^[0-9 ]*$/
  };

  $scope.dateOptions = {
    "starting-day": "1"
  };

  /* Date picker functions for start date */
  $scope.openTeachingStartDate = function($event) {
    $event.preventDefault();
    $event.stopPropagation();

    $scope.inputTeachingStartDateOpened = true;
  };

  /* Date picker functions for end date */
  $scope.openTeachingEndDate = function($event) {
    $event.preventDefault();
    $event.stopPropagation();

    $scope.inputTeachingEndDateOpened = true;
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
    $scope.status = $routeParams.status;
    $scope.loadResources();
  };

  $scope.init();

  $scope.getSeminarsOrForeignSubjects = function(val) {
    return PctService.findSubjectsStartsWith(val, true).then(function(response) {
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
    $scope.teachingExperience.subjectDto.institutionName = $scope.selectedSubject.institutionName;
    $scope.teachingExperience.subjectDto.universityName = $scope.selectedSubject.universityName;
    $scope.teachingExperience.subjectDto.institutionCity = $scope.selectedSubject.institutionCity;
    $scope.teachingExperience.subjectDto.institutionCountry = $scope.selectedSubject.institutionCountry;
    $scope.teachingExperience.subjectDto.institutionId = $scope.selectedSubject.institutionId;
    $scope.teachingExperience.subjectDto.id = $scope.selectedSubject.id;
  };

  $scope.restartSubjectData = function() {
    $scope.selectedSubject = null;
    $scope.isExistingSubject = false;
    $scope.teachingExperience.subjectDto.subjectName = null;
    $scope.teachingExperience.subjectDto.institutionName = null;
    $scope.teachingExperience.subjectDto.universityName = null;
    $scope.teachingExperience.subjectDto.institutionCity = null;
    $scope.teachingExperience.subjectDto.institutionCountry = null;
    $scope.teachingExperience.subjectDto.institutionId = null;
    $scope.teachingExperience.subjectDto.id = null;
    $scope.teachingExperience.subjectDto = null;
  };

  $scope.$watch('selectedSubject', function() {
    if ($scope.isExistingSubject && !angular.equals($scope.selectedSubject, $scope.masterSelectedSubject)) {
      $scope.restartSubjectData();
    }
  });

  $scope.saveNewSeminarOrTeachingExperience = function() {
    if (!$scope.isExistingSubject) {
      $scope.teachingExperience.subjectDto.subjectName = $scope.selectedSubject;
      $scope.teachingExperience.subjectDto.seminarOrTeachingAbroad = true;
    }
    $scope.teachingExperience.professorId = professorId;
    $scope.teachingExperience.seminarOrTeachingAbroad = true;
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
    if ($scope.teachingExperience.subjectDto != null
            && (($scope.teachingExperience.subjectDto.subjectName != null && $scope.teachingExperience.subjectDto.subjectName != '') || ($scope.selectedSubject != null && $scope.selectedSubject != ''))
            && $scope.teachingExperience.subjectDto.institutionName != null && $scope.teachingExperience.subjectDto.institutionName != ''
            && $scope.teachingExperience.subjectDto.universityName != null && $scope.teachingExperience.subjectDto.universityName != ''
            && $scope.teachingExperience.subjectDto.institutionCity != null && $scope.teachingExperience.subjectDto.institutionCity != ''
            && $scope.teachingExperience.subjectDto.institutionCountry != null && $scope.teachingExperience.subjectDto.institutionCountry != ''
            && $scope.teachingExperience.teachingStartDate != null && $scope.teachingExperience.teachingStartDate != '') {
      return true;
    } else {
      return false;
    }
  };

};