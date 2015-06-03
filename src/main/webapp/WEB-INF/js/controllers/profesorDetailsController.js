app.controller("ProfesorDetailsController", function($scope, $routeParams,
        $http, $location, $modal, PctService) {

  $scope.professor = {};
  $scope.id;
  $scope.resources = {};
  $scope.errorMessages = {};
  $scope.master = {};
  $scope.editMode = false;
  $scope.id = '';

  $scope.patterns = {
    onlyLetters: /^[a-zA-ZčČćĆšŠđĐžŽ ]*$/,
    onlyNumbers: /^[0-9 ]*$/
  };

  $scope.dateOptions = {
    "starting-day": "1"
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

  /* Date picker functions */
  $scope.open = function($event) {
    $event.preventDefault();
    $event.stopPropagation();

    $scope.opened = $scope.editMode;
  };

  $scope.loadProfessorDetails = function(id) {
    $http({
      method: 'GET',
      url: "api/professors/loadProfessorDetails?id=" + id,
      headers: {
        'Content-Type': 'application/json'
      }
    }).success(function(data, status) {
      if (angular.isObject(data)) {
        $scope.professor = data;
        $scope.professor.dateOfBirth = new Date(data.dateOfBirth);
      }
    }).error(function(data, status) {
    });
  };

  $scope.setMaxDate = function() {
    $scope.maxDate = new Date();
  };

  $scope.setMaxDate();
  
  $scope.initUserId = function() {
    if ($routeParams.id != null && $routeParams.id != '') {
      $scope.id = $routeParams.id;
    } else {
      $scope.id = document.getElementById('currentUserId').value;
    }
  };

  $scope.init = function() {
    $scope.initUserId();
    $scope.loadProfessorDetails($scope.id);
    $scope.loadResources();
  };

  $scope.init();

  $scope.editProfessor = function(id) {
    $scope.editMode = true;
  };

  $scope.updateProfessor = function() {
    $scope.professor.id = $routeParams.id;
    $http({
      method: 'PUT',
      url: "api/professors/persistProfessor",
      data: $scope.professor,
      headers: {
        'Content-Type': 'application/json'
      }
    }).success(function(data, status) {
      $("html, body").animate({
        scrollTop: 0
      }, "slow");
      $scope.editMode = false;
    }).error(function(data, status) {
      $scope.error = "Greska!";
      if (angular.isObject(data.fieldErrors)) {
        $scope.fieldErrors = angular.fromJson(data.fieldErrors);
      }
      $scope.status = status;
      $("html, body").animate({
        scrollTop: 0
      }, "slow");
    });
  };

  $scope.isUnchanged = function(profesor) {
    return angular.equals(profesor, $scope.master);
  };
  
});