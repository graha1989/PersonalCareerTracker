app.controller("SubjectsController", function($scope, $routeParams, $http,
        $location, $modal, PctService) {

  $scope.subject = {};
  $scope.allSubjects = [];
  $scope.professor = {};

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

  $scope.loadSelectedProfessor = function(index) {
    PctService.loadProfesor($scope.allSubjects[index].professorId, function(
            data) {
      if (angular.isObject(data)) {
        $scope.professor = data;
        $scope.completeSubjectDataArray.push({
          faculty: $scope.faculty,
          subject: $scope.allSubjects[index],
          professor: $scope.professor
        });
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };

  $scope.loadSelectedInstitution = function(id, index) {
    PctService.loadSelectedInstitution(id, function(data) {
      if (angular.isObject(data)) {
        $scope.faculty = data;
        $scope.loadSelectedProfessor(index);
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
  $scope.selectedProfessor = [];
  $scope.masterSelectedProfessor = [];
  $scope.professorSelected = false;

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

  $scope.loadSelectedProfessor = function(id) {
    PctService.loadProfesor(id,
            function(data) {
              if (angular.isObject(data)) {
                $scope.selectedProfessor = data;
                $scope.professorSelected = true;
                $scope.masterSelectedProfessor = angular
                        .copy($scope.selectedProfessor);
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
        $scope.loadSelectedProfessor($scope.subject.professorId)
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

  $scope.getProfessors = function(val) {
    var inputLabel = this.form.inputProfessor;

    inputLabel.$setValidity("professorInvalid", true);
    return PctService.findProfessorsStartsWith(val).then(function(response) {
      var professors = [];
      for (var i = 0; i < response.length; i++) {
        professors.push(response[i]);
        inputLabel.$setValidity("professorInvalid", true);
      }
      if (val.length >= 3 && professors.length == 0) {
        inputLabel.$setValidity("professorInvalid", false);
      }
      return professors;
    });
  };

  $scope.onSelectProfessor = function() {
    $scope.subject.professorId = $scope.selectedProfessor.id;
    $scope.masterSelectedProfessor = angular.copy($scope.selectedProfessor);
    $scope.professorSelected = true;
  };

  $scope.$watch('selectedProfessor', function() {
    if ($scope.professorSelected
            && !angular.equals($scope.selectedProfessor,
                    $scope.masterSelectedProfessor)) {
      $scope.selectedProfessor = null;
      $scope.professorSelected = false;
    }
  });

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
  $scope.selectedProfessor = [];
  $scope.masterSelectedProfessor = [];
  $scope.professorSelected = false;

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
    return PctService.findInstutionsStartsWith(val, 'Fakultet').then(
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

  $scope.getProfessors = function(val) {
    var inputLabel = this.form.inputProfessor;

    inputLabel.$setValidity("professorInvalid", true);
    return PctService.findProfessorsStartsWith(val).then(function(response) {
      var professors = [];
      for (var i = 0; i < response.length; i++) {
        professors.push(response[i]);
        inputLabel.$setValidity("professorInvalid", true);
      }
      if (val.length >= 3 && professors.length == 0) {
        inputLabel.$setValidity("professorInvalid", false);
      }
      return professors;
    });
  };

  $scope.onSelectProfessor = function() {
    $scope.subject.professorId = $scope.selectedProfessor.id;
    $scope.masterSelectedProfessor = angular.copy($scope.selectedProfessor);
    $scope.professorSelected = true;
  };

  $scope.$watch('selectedProfessor', function() {
    if ($scope.professorSelected
            && !angular.equals($scope.selectedProfessor,
                    $scope.masterSelectedProfessor)) {
      $scope.selectedProfessor = null;
      $scope.professorSelected = false;
    }
  });

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
            && $scope.subject.numberOfPracticalLessons != ''
            && $scope.selectedProfessor != null && $scope.selectedProfessor != '') {
      return true;
    } else {
      return false;
    }
  };

  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };

};