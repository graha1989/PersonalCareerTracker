app.controller("SeminarsController", function($scope, $routeParams, $route, $http, $location, $modal, PctService) {

  $scope.seminar = {};
  $scope.allSeminars = [];
  $scope.allSeminarsData = [];

  $scope.noResultsFound = true;
  $scope.resources = {};
  $scope.errorMessages = {};

  $scope.loadResources = function() {
    var locale = document.getElementById('localeCode');
    $http.get('messages/profesorDetails_' + locale.value + '.json').success(function(response) {
      $scope.resources = angular.fromJson(response);
    });
    $http.get('messages/errors_' + locale.value + '.json').success(function(response) {
      $scope.errorMessages = angular.fromJson(response);
    });
  };

  $scope.loadSelectedInstitution = function(id, index) {
    PctService.loadSelectedInstitution(id, function(data) {
      if (angular.isObject(data)) {
        $scope.allSeminarsData = {
          faculty: data,
          seminar: $scope.allSeminars[index]
        };
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };

  $scope.loadAllSeminars = function() {
    PctService.loadAllSubjectsOrSeminars($routeParams, true, function(data) {
      if (angular.isObject(data) && data.length > 0) {
        $scope.allSeminars = data;
        for (var i = 0; i < $scope.allSeminars.length; i++) {
          $scope.loadSelectedInstitution($scope.allSeminars[i].institutionId, i);
        }
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };

  $scope.init = function() {
    $scope.loadAllSeminars();
    $scope.loadResources();
  };

  $scope.init();

  $scope.deleteSeminar = function(id, index) {
    PctService.deleteSubject(id, function(data) {
      if (angular.isObject(data)) {
        $scope.fieldErrors = data.fieldErrors;
        $scope.errorStatus = "Error!";
        $("#warning").fadeTo(5000, 500).slideUp(500, function() {
          $("#warning").alert('close');
        });
      } else {
        $scope.successStatus = "Successfully deleted seminar.";
        $route.reload();
      }
    });
  };

  $scope.editSeminar = function(id) {
    $modal.open({
      templateUrl: 'editSeminarPopup.html',
      controller: editSeminarPopupController,
      resolve: {
        seminarId: function() {
          return id;
        }
      }
    });
  };

  $scope.createNewSeminar = function() {
    $modal.open({
      templateUrl: 'createNewSeminarPopup.html',
      controller: createNewSeminarController,
    });
  };

});

var editSeminarPopupController = function($scope, $modalInstance, $routeParams, $http, $route, $templateCache, seminarId, PctService) {

  $scope.seminar = {};
  $scope.master = {};
  $scope.allFacultyDataShown = false;

  $scope.loadResources = function() {
    var locale = document.getElementById('localeCode');
    $http.get('messages/profesorDetails_' + locale.value + '.json').success(function(response) {
      $scope.resources = angular.fromJson(response);
    });
    $http.get('messages/errors_' + locale.value + '.json').success(function(response) {
      $scope.errorMessages = angular.fromJson(response);
    });
  };

  $scope.loadSelectedSeminar = function(id) {
    PctService.loadSelectedSubject(id, function(data) {
      if (angular.isObject(data)) {
        $scope.seminar = data;
        $scope.master = angular.copy($scope.seminar);
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };

  $scope.init = function() {
    $scope.loadSelectedSeminar(seminarId);
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

  $scope.saveSeminar = function() {
    $http({
      method: 'PUT',
      url: "api/subjects",
      data: $scope.seminar,
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

  $scope.isUnchanged = function(seminar) {
    return angular.equals(seminar, $scope.master);
  };

  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };

};

var createNewSeminarController = function($scope, $modalInstance, $routeParams, $http, $route, $templateCache, PctService) {

  $scope.seminar = {};
  $scope.selectedFaculty = [];
  $scope.masterSelectedFaculty = [];
  $scope.isExistingFaculty = false;

  $scope.loadResources = function() {
    var locale = document.getElementById('localeCode');
    $http.get('messages/profesorDetails_' + locale.value + '.json').success(function(response) {
      $scope.resources = angular.fromJson(response);
    });
    $http.get('messages/errors_' + locale.value + '.json').success(function(response) {
      $scope.errorMessages = angular.fromJson(response);
    });
  };

  $scope.getFaculties = function(val) {
    return PctService.findInstutionsStartsWith(val, 'Fakultet').then(function(response) {
      var faculties = [];
      for (var i = 0; i < response.length; i++) {
        faculties.push(response[i]);
      }
      return faculties;
    });
  };

  $scope.init = function() {
    $scope.status = $routeParams.status;
    $scope.loadResources();
  };

  $scope.init();

  $scope.onSelectFaculty = function() {
    $scope.isExistingFaculty = true;
    $scope.masterSelectedFaculty = angular.copy($scope.selectedFaculty);
    $scope.seminar.institutionName = $scope.selectedFaculty.name;
    $scope.seminar.universityName = $scope.selectedFaculty.university;
    $scope.seminar.institutionCity = $scope.selectedFaculty.city;
    $scope.seminar.institutionCountry = $scope.selectedFaculty.country;
    $scope.seminar.institutionId = $scope.selectedFaculty.id;
  };

  $scope.resetFacultyData = function() {
    $scope.selectedFaculty = null;
    $scope.isExistingFaculty = false;
    $scope.seminar.institutionName = null;
    $scope.seminar.universityName = null;
    $scope.seminar.institutionCity = null;
    $scope.seminar.institutionCountry = null;
    $scope.seminar.institutionId = null;
  };

  $scope.$watch('selectedFaculty', function() {
    if ($scope.isExistingFaculty && !angular.equals($scope.selectedFaculty, $scope.masterSelectedFaculty)) {
      $scope.resetFacultyData();
    }
  });

  $scope.saveNewSeminar = function() {
    if (!$scope.isExistingFaculty) {
      $scope.seminar.institutionName = $scope.selectedFaculty;
    }
    $scope.seminar.seminarOrTeachingAbroad = true;
    $http({
      method: 'POST',
      url: "api/subjects",
      data: $scope.seminar,
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
    if ((($scope.seminar.institutionName != null && $scope.seminar.institutionName != '') || ($scope.selectedFaculty != null && $scope.selectedFaculty != ''))
            && $scope.seminar.universityName != null
            && $scope.seminar.universityName != ''
            && $scope.seminar.institutionCity != null
            && $scope.seminar.institutionCity != ''
            && $scope.seminar.institutionCountry != null
            && $scope.seminar.institutionCountry != ''
            && $scope.seminar.subjectName != null && $scope.seminar.subjectName != '') {
      return true;
    } else {
      return false;
    }
  };

  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };

};