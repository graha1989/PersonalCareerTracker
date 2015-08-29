app.controller("ContestController", function($scope, $routeParams, $http, $location, $modal, PctService) {

  $scope.contestData = {};
  $scope.contestData.commissionMemberDtos = [];
  $scope.professor = {};
  $scope.professorsWhoAreCommissionMembersOnThisContest = [];
  $scope.commissionMembersOnThisContestWhoAreNotProfessors = [];
  $scope.isExistingPerson = false;
  $scope.newCommissionMember = {};
  $scope.selectedCommissionMember = [];
  $scope.commissionMembersTemp = [];
  $scope.formSaved = false;

  $scope.loadResources = function() {
    var locale = document.getElementById('localeCode');
    $http.get('messages/profesorDetails_' + locale.value + '.json').success(function(response) {
      $scope.resources = angular.fromJson(response);
    });
    $http.get('messages/errors_' + locale.value + '.json').success(function(response) {
      $scope.errorMessages = angular.fromJson(response);
    });
  };

  $scope.dateOptions = {
    "starting-day": "1"
  };

  $scope.titles = [{
    "name": "Docent"
  }, {
    "name": "Redovni profesor"
  }, {
    "name": "Vanredni profesor"
  }];

  $scope.studyAreas = [{
    "name": "Računarske nauke"
  }, {
    "name": "Informacioni sistemi"
  }, {
    "name": "Primenjena matematika"
  }];

  $scope.functions = [{
    "name": "Predsednik"
  }, {
    "name": "Član"
  }];

  /* Date picker functions for decision date */
  $scope.openDecisionDate = function($event) {
    $event.preventDefault();
    $event.stopPropagation();

    $scope.inputDecisionDateOpened = true;
  };

  /* Date picker functions for announcing date */
  $scope.openAnnouncingDate = function($event) {
    $event.preventDefault();
    $event.stopPropagation();

    $scope.inputAnnouncingDateOpened = true;
  };

  $scope.loadSelectedProfessor = function(id) {
    PctService.loadProfesor(id, function(data) {
      if (angular.isObject(data)) {
        $scope.professor = data;
        $scope.professor.dateOfBirth = new Date(data.dateOfBirth);
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

  $scope.getCommissionMemberIds = function() {
    $scope.professorsWhoAreCommissionMembersOnThisContest = [$scope.professorId];
    $scope.commissionMembersOnThisContestWhoAreNotProfessors = [$scope.professorId];
    for (var i = 0; i < $scope.contestData.commissionMemberDtos.length; i++) {
      if ($scope.contestData.commissionMemberDtos[i].professorId != null && $scope.contestData.commissionMemberDtos[i].professorId != '') {
        $scope.professorsWhoAreCommissionMembersOnThisContest.push($scope.contestData.commissionMemberDtos[i].professorId);
      } else if ($scope.contestData.commissionMemberDtos[i].id != null && $scope.contestData.commissionMemberDtos[i].id != '') {
        $scope.commissionMembersOnThisContestWhoAreNotProfessors.push($scope.contestData.commissionMemberDtos[i].id);
      }
    }
  };

  $scope.setMaxDate = function() {
    $scope.maxDate = new Date();
  };

  $scope.setMaxDate();

  $scope.init = function() {
    $scope.initUserId();
    $scope.loadSelectedProfessor($scope.professorId);
    $scope.loadResources();
    $scope.professorsWhoAreCommissionMembersOnThisContest.push($scope.professorId);
    $scope.commissionMembersOnThisContestWhoAreNotProfessors.push($scope.professorId);
  };

  $scope.init();

  $scope.onSelectPerson = function() {
    $scope.isExistingPerson = true;
    $scope.newCommissionMember.title = $scope.selectedCommissionMember.title;
    $scope.newCommissionMember.specificScientificArea = $scope.selectedCommissionMember.specificScientificArea;
    $scope.newCommissionMember.institution = $scope.selectedCommissionMember.institution;
    $scope.newCommissionMember.commissionFunction = null;
  };

  $scope.addCommissionMember = function() {
    if ($scope.isExistingPerson) {
      $scope.commissionMembersTemp.push({
        "professorId": $scope.selectedCommissionMember.professorId,
        "nameAndSurname": $scope.selectedCommissionMember.name + ' ' + $scope.selectedCommissionMember.surname,
        "title": $scope.newCommissionMember.title,
        "specificScientificArea": $scope.newCommissionMember.specificScientificArea,
        "institution": $scope.newCommissionMember.institution,
        "commissionFunction": $scope.newCommissionMember.commissionFunction,
        "id": $scope.newCommissionMember.id
      });
    } else {
      $scope.commissionMembersTemp.push({
        "professorId": null,
        "nameAndSurname": $scope.selectedCommissionMember,
        "title": $scope.newCommissionMember.title,
        "specificScientificArea": $scope.newCommissionMember.specificScientificArea,
        "institution": $scope.newCommissionMember.institution,
        "commissionFunction": $scope.newCommissionMember.commissionFunction,
        "id": null
      });
    }
    $scope.getCommissionMemberIds();
    $scope.newCommissionMember = {};
    $scope.selectedCommissionMember = [];
    $scope.isExistingPerson = false;
  };

  $scope.removeCommissionMember = function(index) {
    $scope.commissionMembersTemp.splice(index, 1);
  };

  $scope.getAllPotentalMembers = function(val) {
    return PctService.findAllProfessorsOrCommissionMembersStartsWith(val, $scope.professorsWhoAreCommissionMembersOnThisContest,
            $scope.commissionMembersOnThisContestWhoAreNotProfessors).then(function(response) {
      var persons = [];
      for (var i = 0; i < response.length; i++) {
        persons.push(response[i]);
      }
      return persons;
    });
  };

  $scope.populateCommissionMembersDtoList = function() {
    for (var i = 0; i < $scope.commissionMembersTemp.length; i++) {
      var member = $scope.commissionMembersTemp[i];
      $scope.contestData.commissionMemberDtos.push({
        "professorId": member.professorId,
        "name": member.nameAndSurname.substring(0, member.nameAndSurname.indexOf(' ')),
        "surname": member.nameAndSurname.substring(member.nameAndSurname.indexOf(' ') + 1, member.nameAndSurname.length),
        "title": member.title,
        "specificScientificArea": member.specificScientificArea,
        "institution": member.institution,
        "commissionFunction": member.commissionFunction,
        "id": member.id
      });
    }
  };

  $scope.validetCommissionMember = function() {
    if ($scope.isExistingPerson) {
      if ($scope.selectedCommissionMember != null && $scope.selectedCommissionMember != '' && $scope.newCommissionMember.commissionFunction != null
              && $scope.newCommissionMember.commissionFunction != '') {
        return false;
      } else {
        return true;
      }
    } else {
      if ($scope.selectedCommissionMember != null && $scope.selectedCommissionMember != '' && $scope.newCommissionMember.title != null
              && $scope.newCommissionMember.title != '' && $scope.newCommissionMember.specificScientificArea != null
              && $scope.newCommissionMember.specificScientificArea != '' && $scope.newCommissionMember.institution != null
              && $scope.newCommissionMember.institution != '' && $scope.newCommissionMember.commissionFunction != null
              && $scope.newCommissionMember.commissionFunction != '' && $scope.newCommissionMember.commissionFunction != null
              && $scope.newCommissionMember.commissionFunction != '') {
        return false;
      } else {
        return true;
      }
    }
  };

  $scope.validateForm = function() {
    if ($scope.contestData.authority != null && $scope.contestData.authority != '' && $scope.contestData.decisionDate != null
            && $scope.contestData.decisionDate != '' && $scope.contestData.placeOfAnnouncement != null
            && $scope.contestData.placeOfAnnouncement != '' && $scope.contestData.announcingDate != null && $scope.contestData.announcingDate != ''
            && $scope.contestData.titleToChoose != null && $scope.contestData.titleToChoose != ''
            && $scope.contestData.specificScientificArea != null && $scope.contestData.specificScientificArea != ''
            && $scope.commissionMembersTemp.length > 0) {
      return true;
    } else {
      return false;
    }
  };

  $scope.saveNewContestAndGenerateReport = function() {
    $scope.contestData.candidateId = $scope.professorId;
    $scope.populateCommissionMembersDtoList();
    $http({
      method: 'POST',
      url: "api/contest",
      data: $scope.contestData,
      headers: {
        'Content-Type': 'application/json'
      }
    }).success(function(data, status) {
      $("html, body").animate({
        scrollTop: 0
      }, "slow");
      $scope.formSaved = true;
      window.open('http://localhost:8180/jasperserver/rest_v2/reports/pct_report/PCT_Report.pdf?konkursId=' + data, '_blank');
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

});