app.controller("SubjectsController", function($scope, $routeParams, $http,
        $location, $modal, PctService) {

  $scope.subject = {};
  $scope.allSubjects = [];

  $scope.completeSubjectDataArray = [];

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

  $scope.loadSelectedInstitution = function(id, index) {
    PctService.loadSelectedInstitution(id, function(data) {
      if (angular.isObject(data)) {
        $scope.faculty = data;
        $scope.completeSubjectDataArray.push({
          faculty: $scope.faculty,
          subject: $scope.allSubjects[index]
        });
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };

  $scope.loadAllSubjects = function() {
    PctService.loadAllSubjects($routeParams, function(data) {
      if (angular.isObject(data) && data.length > 0) {
        $scope.allSubjects = data;
        for (var i = 0; i < $scope.allSubjects.length; i++) {
          $scope
                  .loadSelectedInstitution($scope.allSubjects[i].institutionId,
                          i);
        }
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };

  $scope.init = function() {
    $scope.loadAllSubjects();
    $scope.loadResources();
  };

  $scope.init();

  $scope.editSubject = function(id) {
    $modal.open({
      templateUrl: 'editSubjectPopup.html',
      controller: editSubjectPopupController,
      resolve: {
        subjectId: function() {
          return id;
        }
      }
    });
  };

  $scope.createNewSubject = function() {
    $modal.open({
      templateUrl: 'createNewSubjectPopup.html',
      controller: createNewSubjectController,
    });
  };

});

var editSubjectPopupController = function($scope, $modalInstance, $routeParams,
        $http, $route, $templateCache, subjectId, PctService) {

  $scope.subject = {};
  $scope.faculty = {};
  $scope.allStudiesThesisTypes = [];
  $scope.master = {};
  $scope.allFacultyDataShown = false;

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

  $scope.loadSelectedFaculty = function(id) {
    PctService.loadSelectedInstitution(id, function(data) {
      if (angular.isObject(data)) {
        $scope.faculty = data;
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };

  $scope.loadSelectedSubject = function(id) {
    PctService.loadSelectedSubject(id, function(data) {
      if (angular.isObject(data)) {
        $scope.subject = data;
        $scope.master = angular.copy($scope.subject);
        $scope.loadSelectedFaculty($scope.subject.institutionId);
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
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

  $scope.init = function() {
    $scope.loadAllStudyTypes();
    $scope.loadSelectedSubject(subjectId);
    $scope.status = $routeParams.status;
    $scope.loadResources();
  };

  $scope.init();

  $scope.expandFacultyData = function() {
    $scope.allFacultyDataShown = true;
  };

  $scope.collapseFacultyData = function() {
    $scope.allFacultyDataShown = false;
  };

  $scope.saveSubject = function() {
    $http({
      method: 'PUT',
      url: "api/subjects",
      data: $scope.subject,
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

  $scope.isUnchanged = function(subject) {
    return angular.equals(subject, $scope.master);
  };

  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };

};

var createNewSubjectController = function($scope, $modalInstance, $routeParams,
        $http, $route, $templateCache, PctService) {

  $scope.subject = {};
  $scope.faculty = {};
  $scope.selectedFaculty = [];
  $scope.masterSelectedFaculty = [];
  $scope.isExistingFaculty = false;
  $scope.allStudiesThesisTypes = [];

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

  $scope.init = function() {
    $scope.loadAllStudyTypes();
    $scope.status = $routeParams.status;
    $scope.loadResources();
  };

  $scope.init();

  $scope.onSelectFaculty = function() {
    $scope.isExistingFaculty = true;
    $scope.masterSelectedFaculty = angular.copy($scope.selectedFaculty);
    $scope.subject.institutionName = $scope.selectedFaculty.name;
    $scope.subject.universityName = $scope.selectedFaculty.university;
    $scope.subject.institutionCity = $scope.selectedFaculty.city;
    $scope.subject.institutionCountry = $scope.selectedFaculty.country;
    $scope.subject.institutionId = $scope.selectedFaculty.id;
  };

  $scope.resetFacultyData = function() {
    $scope.selectedFaculty = null;
    $scope.isExistingFaculty = false;
    $scope.subject.institutionName = null;
    $scope.subject.universityName = null;
    $scope.subject.institutionCity = null;
    $scope.subject.institutionCountry = null;
    $scope.subject.institutionId = null;
  };

  $scope.$watch('selectedFaculty', function() {
    if ($scope.isExistingFaculty
            && !angular.equals($scope.selectedFaculty,
                    $scope.masterSelectedFaculty)) {
      $scope.resetFacultyData();
    }
  });

  $scope.saveNewSubject = function() {
    if (!$scope.isExistingFaculty) {
      $scope.subject.institutionName = $scope.selectedFaculty;
    }
    $http({
      method: 'POST',
      url: "api/subjects",
      data: $scope.subject,
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
    if ((($scope.subject.institutionName != null && $scope.subject.institutionName != '') || ($scope.selectedFaculty != null && $scope.selectedFaculty != ''))
            && $scope.subject.universityName != null
            && $scope.subject.universityName != ''
            && $scope.subject.institutionCity != null
            && $scope.subject.institutionCity != ''
            && $scope.subject.institutionCountry != null
            && $scope.subject.institutionCountry != ''
            && $scope.subject.subjectName != null
            && $scope.subject.subjectName != ''
            && $scope.subject.studyProgram != null
            && $scope.subject.studyProgram != ''
            && $scope.subject.studiesThesisType != null
            && $scope.subject.studiesThesisType != ''
            && $scope.subject.numberOfTeachingLessons != null
            && $scope.subject.numberOfTeachingLessons != ''
            && $scope.subject.numberOfTheoreticalLessons != null
            && $scope.subject.numberOfTheoreticalLessons != ''
            && $scope.subject.numberOfPracticalLessons != null
            && $scope.subject.numberOfPracticalLessons != '') {
      return true;
    } else {
      return false;
    }
  };

  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };

};