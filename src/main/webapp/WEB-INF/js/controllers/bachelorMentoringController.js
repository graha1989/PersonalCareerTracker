app.controller("BachelorMentoringController", function($scope, $routeParams,
        $http, $location, $modal, PctService) {

  $scope.allBachelorThesis = [];
  $scope.thesis = {};
  $scope.resources = {};
  $scope.errorMessages = {};
  $scope.noResultsFound = true;
  
  $scope.isUser = false;
  $scope.isAdmin = false;
  $scope.mentorId = '';

  $scope.patterns = {
    onlyLetters: /^[a-zA-ZčČćĆšŠđĐžŽ ]*$/,
    onlyNumbers: /^[0-9 ]*$/
  };

  /* Load resources from .json properties file */
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

  $scope.loadThesis = function(mentorId, thesisTypeId) {
    PctService.loadThesis(mentorId, thesisTypeId, function(data) {
      if (angular.isObject(data) && data.length > 0) {
        $scope.allBachelorThesis = data;
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
    if ($routeParams.mentorId != null && $routeParams.mentorId != '') {
      $scope.mentorId = $routeParams.mentorId;
    } else {
      $scope.mentorId = document.getElementById('currentUserId').value;
    }
  };

  $scope.init = function() {
    $scope.initUserId();
    $scope.thesis = {};
    $scope.loadThesis($scope.mentorId, $routeParams.thesisTypeId);
    $scope.loadResources();
    $scope.getCurrentUserRole();
  };

  $scope.init();

  $scope.goBack = function() {
    window.history.back();
  };

  $scope.deleteThesis = function(id, index) {
    PctService.deleteThesis(id, function(data) {
      if (angular.isObject(data) && data.length > 0) {
        $scope.errorStatus = data.status;
      } else {
        $scope.successStatus = "Successfully deleted thesis.";
        $scope.allBachelorThesis.splice(index, 1);
        $scope.loadThesis($scope.mentorId, $routeParams.thesisTypeId);
      }
    });
  };

  $scope.editThesis = function(id) {
    $modal.open({
      templateUrl: 'editBachelorThesisPopup.html',
      controller: editBachelorThesisController,
      resolve: {
        thesisId: function() {
          return id;
        },
        mentorId: function() {
          return $scope.mentorId;
        }
      }
    });
  };

  $scope.createNewBachelorThesis = function() {
    $modal.open({
      templateUrl: 'createNewBachelorThesisPopup.html',
      controller: createNewBachelorThesisController,
      resolve: {
        mentorId: function() {
          return $scope.mentorId;
        }
      }
    });
  };
  
  $scope.goBack = function() {
    window.history.back();
  };

});

var editBachelorThesisController = function($scope, $modalInstance,
        $routeParams, $http, $route, thesisId, PctService, mentorId) {

  $scope.thesis = {};
  $scope.thesis.mentorId;
  $scope.thesis.thesisTypeId;

  /*
   * fields used for detecting if something changed during the edit on the popup
   */
  $scope.master = {};
  $scope.masterSelectedStudent = {};
  $scope.masterSelectedPresident = {};
  $scope.masterSelectedMember = {};

  $scope.selectedStudent = {};
  $scope.selectedCommissionPresident = {};
  $scope.selectedCommissionMember = {};

  /*
   * fields used for detecting if student, commission president and commission
   * member are selected
   */
  $scope.studentSelected = false;
  $scope.commissionPresidentSelected = false;
  $scope.commissionMemberSelected = false;

  $scope.opened = false;

  $scope.patterns = {
    onlyLetters: /^[a-zA-ZčČćĆšŠđĐžŽ ]*$/,
    onlyNumbers: /^[0-9 ]*$/
  };

  $scope.dateOptions = {
    "starting-day": "1"
  };

  /* Load resources from .json properties file */
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

  /* Date picker functions */
  $scope.open = function($event) {
    $event.preventDefault();
    $event.stopPropagation();

    $scope.opened = true;
  };

  $scope.loadSelectedThesis = function(id) {
    PctService.loadSelectedThesis(id, function(data) {
      if (angular.isObject(data)) {
        $scope.thesis = data;

        $scope.selectedStudent = {
          id: $scope.thesis.studentId,
          transcriptNumber: $scope.thesis.studentTranscriptNumber,
          name: $scope.thesis.studentName,
          surname: $scope.thesis.studentSurname
        };
        $scope.studentSelected = true;

        $scope.selectedCommissionPresident = {
          id: $scope.thesis.commissionPresidentId,
          name: $scope.thesis.commissionPresidentName,
          surname: $scope.thesis.commissionPresidentSurname
        };
        $scope.commissionPresidentSelected = true;

        $scope.selectedCommissionMember = {
          id: $scope.thesis.commissionMemberId,
          name: $scope.thesis.commissionMemberName,
          surname: $scope.thesis.commissionMemberSurname
        };
        $scope.commissionMemberSelected = true;

        $scope.master = angular.copy($scope.thesis);
        $scope.masterSelectedStudent = angular.copy($scope.selectedStudent);
        $scope.masterSelectedPresident = angular
                .copy($scope.selectedCommissionPresident);
        $scope.masterSelectedMember = angular
                .copy($scope.selectedCommissionMember);
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
    $scope.loadSelectedThesis(thesisId);
    $scope.status = $routeParams.status;
    $scope.loadResources();
  };

  $scope.init();

  $scope.getStudents = function(val) {
    $scope.studentSelected = false;
    var inputLabel = this.form.inputStudent;

    inputLabel.$setValidity("studentInvalid", true);
    return PctService.findStudentStartsWith(val).then(function(response) {
      var students = [];
      for (var i = 0; i < response.length; i++) {
        students.push(response[i]);
        inputLabel.$setValidity("studentInvalid", true);
      }
      if (val.length >= 3 && students.length == 0) {
        inputLabel.$setValidity("studentInvalid", false);
      }
      return students;
    });
  };

  $scope.getCommissionPresident = function(val) {
    $scope.commissionPresidentSelected = false;
    var inputLabel = this.form.inputCommissionPresident;

    inputLabel.$setValidity("commissionPresidentInvalid", true);
    return PctService.findProfessorsStartsWith(val,
            $scope.selectedCommissionMember.id, mentorId).then(
            function(response) {
              var professors = [];
              for (var i = 0; i < response.length; i++) {
                professors.push(response[i]);
                inputLabel.$setValidity("commissionPresidentInvalid", true);
              }
              if (val.length >= 3 && professors.length == 0) {
                inputLabel.$setValidity("commissionPresidentInvalid", false);
              }
              return professors;
            });
  };

  $scope.getCommissionMember = function(val) {
    $scope.commissionMemberSelected = false;
    var inputLabel = this.form.inputCommissionMember;

    inputLabel.$setValidity("commissionMemberInvalid", true);
    return PctService.findProfessorsStartsWith(val,
            $scope.selectedCommissionPresident.id, mentorId).then(
            function(response) {
              var professors = [];
              for (var i = 0; i < response.length; i++) {
                professors.push(response[i]);
                inputLabel.$setValidity("commissionMemberInvalid", true);
              }
              if (val.length >= 3 && professors.length == 0) {
                inputLabel.$setValidity("commissionMemberInvalid", false);
              }
              return professors;
            });
  };

  $scope.refreshStudentData = function() {
    $scope.thesis.studentId = $scope.selectedStudent.id;
    $scope.thesis.studentTranscriptNumber = $scope.selectedStudent.transcriptNumber;
    $scope.thesis.studentName = $scope.selectedStudent.name;
    $scope.thesis.studentSurname = $scope.selectedStudent.surname;
  };

  $scope.refreshThesisTypeData = function() {
    $scope.thesis.thesisTypeId = $routeParams.thesisTypeId;
  };

  $scope.refreshMentorData = function() {
    $scope.thesis.mentorId = mentorId;
  };

  $scope.refreshCommissionPresidentData = function() {
    $scope.thesis.commissionPresidentId = $scope.selectedCommissionPresident.id;
    $scope.thesis.commissionPresidentName = $scope.selectedCommissionPresident.name;
    $scope.thesis.commissionPresidentSurname = $scope.selectedCommissionPresident.surname;
  };

  $scope.refreshCommissionMemberData = function() {
    $scope.thesis.commissionMemberId = $scope.selectedCommissionMember.id;
    $scope.thesis.commissionMemberName = $scope.selectedCommissionMember.name;
    $scope.thesis.commissionMemberSurname = $scope.selectedCommissionMember.surname;
  };

  $scope.refreshThesisData = function() {
    $scope.refreshStudentData();
    $scope.refreshThesisTypeData();
    $scope.refreshMentorData();
    $scope.refreshCommissionPresidentData();
    $scope.refreshCommissionMemberData();
  };

  $scope.saveThesis = function() {
    $scope.refreshThesisData();

    $http({
      method: 'PUT',
      url: "api/thesis",
      data: $scope.thesis,
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

  $scope.onSelectStudent = function(selectedStudent) {
    $scope.selectedStudent = {
      id: selectedStudent.id,
      transcriptNumber: selectedStudent.transcriptNumber,
      name: selectedStudent.name,
      surname: selectedStudent.surname
    };
    $scope.studentSelected = true;
  }

  $scope.onSelectCommisionPresident = function(selectedCommissionPresident) {
    $scope.selectedCommissionPresident = {
      id: selectedCommissionPresident.id,
      name: selectedCommissionPresident.name,
      surname: selectedCommissionPresident.surname
    };
    $scope.commissionPresidentSelected = true;
  }

  $scope.onSelectCommisionMember = function(selectedCommissionMember) {
    $scope.selectedCommissionMember = {
      id: selectedCommissionMember.id,
      name: selectedCommissionMember.name,
      surname: selectedCommissionMember.surname
    };
    $scope.commissionMemberSelected = true;
  }

  $scope.isUnchanged = function(thesis) {
    if ($scope.studentSelected && $scope.commissionPresidentSelected
            && $scope.commissionMemberSelected) {
      thesis.dateOfGraduation = new Date(thesis.dateOfGraduation).getTime();
      return angular.equals(thesis, $scope.master)
              && angular.equals($scope.selectedStudent,
                      $scope.masterSelectedStudent)
              && angular.equals($scope.selectedCommissionPresident,
                      $scope.masterSelectedPresident)
              && angular.equals($scope.selectedCommissionMember,
                      $scope.masterSelectedMember);
    } else {
      return true;
    }
  };

  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };

};

var createNewBachelorThesisController = function($scope, $modalInstance,
        $routeParams, $http, $route, PctService, mentorId) {

  $scope.thesis = {};
  $scope.thesis.mentorId;
  $scope.thesis.thesisTypeId;
  $scope.master = {};

  $scope.selectedStudent = [];
  $scope.selectedCommissionPresident = [];
  $scope.selectedCommissionMember = [];

  /*
   * fields used for detecting if student, commission president and commission
   * member are selected
   */
  $scope.studentSelected = false;
  $scope.commissionPresidentSelected = false;
  $scope.commissionMemberSelected = false;

  $scope.opened = false;

  $scope.dateOptions = {
    "starting-day": "1"
  };

  $scope.patterns = {
    onlyLetters: /^[a-zA-ZčČćĆšŠđĐžŽ ]*$/,
    onlyNumbers: /^[0-9 ]*$/
  };

  /* Load resources from .json properties file */
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

  /* Date picker functions */
  $scope.open = function($event) {
    $event.preventDefault();
    $event.stopPropagation();

    $scope.opened = true;
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

  $scope.getStudents = function(val) {
    $scope.studentSelected = false;
    var inputLabel = this.form.inputStudent;

    inputLabel.$setValidity("studentInvalid", true);
    return PctService.findStudentStartsWith(val).then(function(response) {
      var students = [];
      for (var i = 0; i < response.length; i++) {
        students.push(response[i]);
        inputLabel.$setValidity("studentInvalid", true);
      }
      if (val.length >= 3 && students.length == 0) {
        inputLabel.$setValidity("studentInvalid", false);
      }
      return students;
    });
  };

  $scope.getCommissionPresident = function(val) {
    $scope.commissionPresidentSelected = false;
    var inputLabel = this.form.inputCommissionPresident;

    inputLabel.$setValidity("commissionPresidentInvalid", true);
    return PctService.findProfessorsStartsWith(val,
            $scope.selectedCommissionMember.id, mentorId).then(
            function(response) {
              var professors = [];
              for (var i = 0; i < response.length; i++) {
                professors.push(response[i]);
                inputLabel.$setValidity("commissionPresidentInvalid", true);
              }
              if (val.length >= 3 && professors.length == 0) {
                inputLabel.$setValidity("commissionPresidentInvalid", false);
              }
              return professors;
            });
  };

  $scope.getCommissionMember = function(val) {
    $scope.commissionMemberSelected = false;
    var inputLabel = this.form.inputCommissionMember;

    inputLabel.$setValidity("commissionMemberInvalid", true);
    return PctService.findProfessorsStartsWith(val,
            $scope.selectedCommissionPresident.id, mentorId).then(
            function(response) {
              var professors = [];
              for (var i = 0; i < response.length; i++) {
                professors.push(response[i]);
                inputLabel.$setValidity("commissionMemberInvalid", true);
              }
              if (val.length >= 3 && professors.length == 0) {
                inputLabel.$setValidity("commissionMemberInvalid", false);
              }
              return professors;
            });
  };

  $scope.saveNewThesis = function() {
    $scope.thesis.mentorId = mentorId;
    $scope.thesis.thesisTypeId = $routeParams.thesisTypeId;
    $scope.thesis.studentId = $scope.selectedStudent.id;
    $scope.thesis.commissionPresidentId = $scope.selectedCommissionPresident.id;
    $scope.thesis.commissionMemberId = $scope.selectedCommissionMember.id;

    $http({
      method: 'POST',
      url: "api/thesis",
      data: $scope.thesis,
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

  $scope.onSelectStudent = function() {
    $scope.studentSelected = true;
  }

  $scope.onSelectCommissionPresident = function() {
    $scope.commissionPresidentSelected = true;
  }

  $scope.onSelectCommissionMember = function() {
    $scope.commissionMemberSelected = true;
  }

  $scope.cancel = function() {
    $modalInstance.dismiss("cancel");
  };

};