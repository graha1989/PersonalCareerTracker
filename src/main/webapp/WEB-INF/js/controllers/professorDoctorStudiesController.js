app.controller("ProfessorDoctorStudiesController", function($scope,
        $routeParams, $http, $route, $location, $modal, PctService) {

  $scope.doctorStudies = {};
  $scope.allDoctorStudies = [];
  $scope.allDoctorStudiesMaster = [];
  $scope.noResultsFound = true;
  $scope.resources = {};
  $scope.errorMessages = {};
  $scope.allStudyPrograms = [];
  $scope.editMode = [];
  $scope.inputStudyStartDateOpened = [];
  $scope.inputStudyEndDateOpened = [];
  
  $scope.isUser = false;
  $scope.isAdmin = false;
  $scope.professorId = '';

  $scope.patterns = {
    onlyLetters: /^[a-zA-ZčČćĆšŠđĐžŽ ]*$/,
    onlyNumbers: /^[0-9 ]*$/
  };

  $scope.dateOptions = {
    "starting-day": "1"
  };
  
  $scope.studyPrograms = [{
    'name': "Matematika i informatika",
    'studyAreas': [{
      'name': "Računarske nauke",
      'title': "Doktor računarskih nauka"
    }, {
      'name': "Informacioni sistemi",
      'title': "Doktor informacionih sistema"
    }, {
      'name': "Primenjena matematika",
      'title': "Doktor primenjene matematike"
    }, {
      'name': "Informatika",
      'title': "Doktor informatike"
    }, {
      'name': "Matematika",
      'title': "Doktor matematike"
    }]
  }, {
    'name': "Biologija i ekologija",
    'studyAreas': [{
      'name': "Biologija",
      'title': "Doktor biologije"
    }, {
      'name': "Ekologija",
      'title': "Doktor ekologije"
    }]
  }, {
    'name': "Hemija, biohemija i zaštita životne sredine",
    'studyAreas': [{
      'name': "Hemija",
      'title': "Doktor hemije"
    }, {
      'name': "Biohemija",
      'title': "Doktor biohemije"
    }, {
      'name': "Zaštita životne sredine",
      'title': "Doktor zaštite životne sredine"
    }]
  }, {
    'name': "Geografija, turizam i hotelijerstvo",
    'studyAreas': [{
      'name': "Geografija",
      'title': "Doktor geografije"
    }, {
      'name': "Turizam",
      'title': "Doktor turizmologije"
    }, {
      'name': "Hotelijerstvo",
      'title': "Doktor hotelijerstva"
    }]
  }];

  /* Date picker functions for start date */
  $scope.openStudyStartDate = function($event, index) {
    $event.preventDefault();
    $event.stopPropagation();

    $scope.inputStudyStartDateOpened[index] = $scope.editMode[index];
  };

  /* Date picker functions for end date */
  $scope.openStudyEndDate = function($event, index) {
    $event.preventDefault();
    $event.stopPropagation();

    $scope.inputStudyEndDateOpened[index] = $scope.editMode[index];
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

  $scope.convertTimeToDate = function(time) {
    return new Date(time);
  };

  $scope.loadProfessorsDoctorStudies = function(professorId, thesisTypeId) {
    return PctService.loadProfessorStudies(professorId, thesisTypeId).then(
            function(response) {
              if (angular.isObject(response) && response.length > 0) {
                $scope.allDoctorStudies = response;

                for (var i = 0; i < $scope.allDoctorStudies.length; i++) {
                  $scope.allDoctorStudies[i].studyStartDate = $scope
                          .convertTimeToDate(response[i].studyStartDate);
                  $scope.allDoctorStudies[i].studyEndDate = $scope
                          .convertTimeToDate(response[i].studyEndDate);
                }

                $scope.editMode = new Array($scope.allDoctorStudies.length);
                for (var i = 0; i < $scope.allDoctorStudies.length; i++) {
                  $scope.editMode.splice(i, 1, false);
                }

                $scope.inputStudyStartDateOpened = new Array(
                        $scope.allDoctorStudies.length);
                for (var i = 0; i < $scope.allDoctorStudies.length; i++) {
                  $scope.inputStudyStartDateOpened.splice(i, 1, false);
                }

                $scope.inputStudyEndDateOpened = new Array(
                        $scope.allDoctorStudies.length);
                for (var i = 0; i < $scope.allDoctorStudies.length; i++) {
                  $scope.inputStudyEndDateOpened.splice(i, 1, false);
                }
                $scope.allDoctorStudiesMaster = angular
                        .copy($scope.allDoctorStudies);
                $scope.noResultsFound = false;
              } else {
                $scope.noResultsFound = true;
              }
            });
  };

  $scope.loadAllStudyPrograms = function() {
    PctService.loadAllStudyPrograms($routeParams, function(data) {
      if (angular.isObject(data) && data.length > 0) {
        $scope.allStudyPrograms = data;
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
    $scope.loadProfessorsDoctorStudies($scope.professorId,
            $routeParams.thesisTypeId);
    $scope.loadAllStudyPrograms();
    $scope.loadResources();
    $scope.getCurrentUserRole();
  };

  $scope.init();

  $scope.editProfesorDoctorStudies = function(index) {
    $scope.editMode.splice(index, 1, true);
    for (var i = 0; i < $scope.editMode.length; i++) {
      if (i != index) {
        $scope.allDoctorStudies[i] = angular
                .copy($scope.allDoctorStudiesMaster[i]);
        $scope.editMode.splice(i, 1, false);
      }
    }
  };

  $scope.close = function(index) {
    $scope.allDoctorStudies[index] = angular
            .copy($scope.allDoctorStudiesMaster[index]);
    $scope.editMode.splice(index, 1, false);
  };

  $scope.isInEditMode = function() {
    for (var i = 0; i < $scope.editMode.length; i++) {
      if ($scope.editMode[i] === true) { return true; }
    }
    return false;
  };

  $scope.updateProfessorDoctorStudies = function(studies, index) {
    $http({
      method: 'PUT',
      url: "api/studies",
      data: studies,
      headers: {
        'Content-Type': 'application/json'
      }
    }).success(function(data, status) {
      $("html, body").animate({
        scrollTop: 0
      }, "slow");
      $scope.editMode[index] = false;
      $route.reload();
    }).error(function(data, status) {
      $scope.error = "Greška!";
      if (angular.isObject(data.fieldErrors)) {
        $scope.fieldErrors = angular.fromJson(data.fieldErrors);
      }
      $scope.status = status;
      $("html, body").animate({
        scrollTop: 0
      }, "slow");
    });
  };

  $scope.isUnchanged = function(index) {
    return angular.equals($scope.allDoctorStudies[index],
            $scope.allDoctorStudiesMaster[index]);
  };

  $scope.goBack = function() {
    window.history.back();
  };

  $scope.deleteProfessorDoctorStudies = function(id, index) {
    PctService.deleteProfessorStudies(id, function(data) {
      if (angular.isObject(data)) {
        $scope.errorStatus = data.status;
      } else {
        $scope.successStatus = "Successfully deleted doctor studies.";
        $scope.allDoctorStudies.splice(index, 1);
        $scope.loadProfessorsDoctorStudies($scope.professorId,
                $routeParams.thesisTypeId);
      }
    });
  };

  $scope.createNewDoctorStudies = function() {
    $modal.open({
      templateUrl: 'createNewDoctorStudiesPopup.html',
      controller: createNewDoctorStudiesController,
      resolve: {
        professorId: function() {
          return $scope.professorId;
        }
      }
    });
  };
  
  $scope.goBack = function() {
    window.history.back();
  };

});

var createNewDoctorStudiesController = function($scope, $modalInstance,
        $routeParams, $http, $route, $templateCache, PctService, professorId) {

  $scope.doctorStudies = {};
  $scope.noResultsFound = true;
  $scope.resources = {};
  $scope.errorMessages = {};
  $scope.allStudyPrograms = [];
  $scope.isExistingFaculty = false;
  $scope.selectedFaculty = [];
  $scope.masterSelectedFaculty = [];

  $scope.patterns = {
    onlyLetters: /^[a-zA-ZčČćĆšŠđĐžŽ ]*$/,
    onlyNumbers: /^[0-9 ]*$/
  };

  $scope.dateOptions = {
    "starting-day": "1"
  };
  
  $scope.countries = [{
    'name': "Srbija",
    'cities': [{
      'name': "Novi Sad"
    }, {
      'name': "Beograd"
    }]
  }, {
    'name': "Bosna i Hercegovina",
    'cities': [{
      'name': "Banja Luka"
    }, {
      'name': "Sarajevo"
    }]
  }];

  $scope.studyPrograms = [{
    'name': "Matematika i informatika",
    'studyAreas': [{
      'name': "Računarske nauke",
      'title': "Doktor računarskih nauka"
    }, {
      'name': "Informacioni sistemi",
      'title': "Doktor informacionih sistema"
    }, {
      'name': "Primenjena matematika",
      'title': "Doktor primenjene matematike"
    }, {
      'name': "Informatika",
      'title': "Doktor informatike"
    }, {
      'name': "Matematika",
      'title': "Doktor matematike"
    }]
  }, {
    'name': "Biologija i ekologija",
    'studyAreas': [{
      'name': "Biologija",
      'title': "Doktor biologije"
    }, {
      'name': "Ekologija",
      'title': "Doktor ekologije"
    }]
  }, {
    'name': "Hemija, biohemija i zaštita životne sredine",
    'studyAreas': [{
      'name': "Hemija",
      'title': "Doktor hemije"
    }, {
      'name': "Biohemija",
      'title': "Doktor biohemije"
    }, {
      'name': "Zaštita životne sredine",
      'title': "Doktor zaštite životne sredine"
    }]
  }, {
    'name': "Geografija, turizam i hotelijerstvo",
    'studyAreas': [{
      'name': "Geografija",
      'title': "Doktor geografije"
    }, {
      'name': "Turizam",
      'title': "Doktor turizmologije"
    }, {
      'name': "Hotelijerstvo",
      'title': "Doktor hotelijerstva"
    }]
  }];

  /* Date picker functions for start date */
  $scope.openStudyStartDate = function($event) {
    $event.preventDefault();
    $event.stopPropagation();

    $scope.inputStudyStartDateOpened = true;
  };

  /* Date picker functions for end date */
  $scope.openStudyEndDate = function($event) {
    $event.preventDefault();
    $event.stopPropagation();

    $scope.inputStudyEndDateOpened = true;
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

  $scope.convertTimeToDate = function(time) {
    return new Date(time);
  };

  $scope.loadAllStudyPrograms = function() {
    PctService.loadAllStudyPrograms($routeParams, function(data) {
      if (angular.isObject(data) && data.length > 0) {
        $scope.allStudyPrograms = data;
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
    $scope.loadAllStudyPrograms();
    $scope.status = $routeParams.status;
    $scope.loadResources();
  };

  $scope.init();

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

  $scope.$watch('selectedFaculty', function() {
    if ($scope.isExistingFaculty
            && !angular.equals($scope.selectedFaculty,
                    $scope.masterSelectedFaculty)) {
      $scope.resetFacultyData();
    }
  });

  $scope.onSelectFaculty = function() {
    $scope.isExistingFaculty = true;
    $scope.masterSelectedFaculty = angular.copy($scope.selectedFaculty);
    $scope.doctorStudies.facultyName = $scope.selectedFaculty.name;
    $scope.doctorStudies.universityName = $scope.selectedFaculty.university;
    $scope.doctorStudies.facultyCity = $scope.selectedFaculty.city;
    $scope.doctorStudies.facultyCountry = $scope.selectedFaculty.country;
    $scope.doctorStudies.institutionId = $scope.selectedFaculty.id;
  };

  $scope.resetFacultyData = function() {
    $scope.selectedFaculty = null;
    $scope.isExistingFaculty = false;
    $scope.doctorStudies.facultyName = null;
    $scope.doctorStudies.universityName = null;
    $scope.doctorStudies.facultyCity = null;
    $scope.doctorStudies.facultyCountry = null;
    $scope.doctorStudies.institutionId = null;
  };

  $scope.saveNewProfessorDoctorStudies = function() {
    if (!$scope.isExistingFaculty) {
      $scope.doctorStudies.facultyName = $scope.selectedFaculty;
    }
    $scope.doctorStudies.professorId = professorId;
    $scope.doctorStudies.thesisTypeId = $routeParams.thesisTypeId;
    $http({
      method: 'POST',
      url: "api/studies",
      data: $scope.doctorStudies,
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
    if ((($scope.doctorStudies.facultyName != null && $scope.doctorStudies.facultyName != '') || ($scope.selectedFaculty != null && $scope.selectedFaculty != ''))
            && $scope.doctorStudies.universityName != null
            && $scope.doctorStudies.universityName != ''
            && (!$scope.isExistingFaculty
                    ? ($scope.doctorStudies.facultyCity != null && $scope.doctorStudies.facultyCity != '')
                    : true)
            && (!$scope.isExistingFaculty
                    ? ($scope.doctorStudies.facultyCountry != null && $scope.doctorStudies.facultyCountry != '')
                    : true)
            && $scope.doctorStudies.studyProgram != null
            && $scope.doctorStudies.studyProgram != ''
            && $scope.doctorStudies.studyArea != null
            && $scope.doctorStudies.studyArea != ''
            && $scope.doctorStudies.studyStartDate != null
            && $scope.doctorStudies.studyStartDate != ''
            && $scope.doctorStudies.studyEndDate != null
            && $scope.doctorStudies.studyEndDate != ''
            && $scope.doctorStudies.averageGrade != null
            && $scope.doctorStudies.averageGrade != ''
            && $scope.doctorStudies.thesisTitle != null
            && $scope.doctorStudies.thesisTitle != ''
            && $scope.doctorStudies.acquiredTitle != null
            && $scope.doctorStudies.acquiredTitle != '') {
      return true;
    } else {
      return false;
    }
  };

};