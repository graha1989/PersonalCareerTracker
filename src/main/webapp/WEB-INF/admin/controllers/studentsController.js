app.controller("StudentsController", function($scope, $routeParams, $http, $location, $modal, PctService) {

  $scope.students = [];
  $scope.student = {};
  $scope.master = {};
  $scope.noResultsFound = true;
  $scope.resources = {};
  $scope.errorMessages = {};

  $scope.sortType = '';

  $scope.allSortTypes = [{
    description: "imenu-rastuće",
    sortBy: "+name"
  }, {
    description: "imenu-opadajuće",
    sortBy: "-name"
  }, {
    description: "prezimenu-rastuće",
    sortBy: "+surname"
  }, {
    description: "prezimenu-opadajuće",
    sortBy: "-surname"
  }];

  $scope.patterns = {
    onlyLetters: /^[a-zA-ZčČćĆšŠđĐžŽ ]*$/,
    onlyNumbers: /^[0-9 ]*$/
  };

  /* Variable used for hiding unimplemented fields */
  $scope.dontShowDataForFieldsThatAreNotImplemented = false;

  $scope.loadResources = function() {
    var locale = document.getElementById('localeCode');
    $http.get('messages/profesorDetails_' + locale.value + '.json').success(function(response) {
      $scope.resources = angular.fromJson(response);
    });
    $http.get('messages/errors_' + locale.value + '.json').success(function(response) {
      $scope.errorMessages = angular.fromJson(response);
    });
  };

  $scope.loadStudents = function() {
    PctService.loadStudents($routeParams, function(data) {
      if (angular.isObject(data) && data.length > 0) {
        $scope.students = data;
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };

  $scope.init = function() {
    $scope.student = {};
    $scope.students = [];
    $scope.master = {};
    $scope.loadStudents();
    $scope.loadResources();
  };

  $scope.init();

  $scope.goBack = function() {
    window.history.back();
  };

  $scope.deleteStudent = function(id) {
    PctService.deleteStudent(id, function(data) {
      if (angular.isObject(data)) {
        $scope.fieldErrors = data.fieldErrors;
        $scope.errorStatus = "Error!";
        $("#warning").fadeTo(5000, 500).slideUp(500, function() {
          $("#warning").alert('close');
        });
      } else {
        $scope.successStatus = "Successfully deleted student.";
        $scope.students.splice(index, 1);
        $scope.loadStudents();
      }
    });
  };

  $scope.editStudent = function(id) {
    $modal.open({
      templateUrl: 'editStudentPopup.html',
      controller: editStudentController,
      resolve: {
        studentId: function() {
          return id;
        }
      }
    });
  };

  $scope.createNewStudent = function() {
    $modal.open({
      templateUrl: 'createNewStudentPopup.html',
      controller: createNewStudentController
    });
  };

});

var editStudentController = function($scope, $modalInstance, $routeParams, $http, $route, studentId, PctService) {

  $scope.patterns = {
    onlyLetters: /^[a-zA-ZčČćĆšŠđĐžŽ ]*$/,
    onlyNumbers: /^[0-9 ]*$/
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

  $scope.loadSelectedStudent = function(id) {
    PctService.loadSelectedStudent(id, function(data) {
      if (angular.isObject(data)) {
        $scope.student = data;
        $scope.master = angular.copy($scope.student);
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };

  $scope.init = function() {
    $scope.loadSelectedStudent(studentId);
    $scope.status = $routeParams.status;
    $scope.loadResources();
  };

  $scope.init();

  $scope.saveStudent = function() {
    $http({
      method: 'PUT',
      url: "api/students",
      data: $scope.student,
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
      $("#warningCreate").fadeTo(5000, 500).slideUp(500, function() {
        $("#warning").alert('close');
      });
    });
  };

  $scope.isUnchanged = function(student) {
    return angular.equals(student, $scope.master);
  };

  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };

};

var createNewStudentController = function($scope, $modalInstance, $routeParams, $http, $route, PctService) {

  $scope.patterns = {
    onlyLetters: /^[a-zA-ZčČćĆšŠđĐžŽ ]*$/,
    onlyNumbers: /^[0-9 ]*$/
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

  $scope.init = function() {
    $scope.status = $routeParams.status;
    $scope.student = {};
    $scope.loadResources();
  };

  $scope.init();

  $scope.saveNewStudent = function() {
    $http({
      method: 'POST',
      url: "api/students",
      data: $scope.student,
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
      $("#warningCreate").fadeTo(5000, 500).slideUp(500, function() {
        $("#warning").alert('close');
      });
    });
  };

  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };

};