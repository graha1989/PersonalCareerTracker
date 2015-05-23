app.controller("AdminDetailsController", function($scope, $routeParams, $http,
        $location, $modal, PctService) {

  $scope.admin = {};
  $scope.id;
  $scope.resources = {};
  $scope.errorMessages = {};
  $scope.master = {};
  $scope.editMode = false;

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

  $scope.loadAdminDetails = function(id) {
    $http({
      method: 'GET',
      url: "api/admins/loadAdminDetails?id=" + id,
      headers: {
        'Content-Type': 'application/json'
      }
    }).success(function(data, status) {
      if (angular.isObject(data)) {
        $scope.admin = data;
      }
    }).error(function(data, status) {
    });
  };

  $scope.init = function() {
    $scope.id = $routeParams.id;
    $scope.loadAdminDetails($scope.id);
    $scope.loadResources();
  };

  $scope.init();

  $scope.editAdmin = function(id) {
    $scope.editMode = true;
  };

  $scope.updateAdmin = function() {
    $scope.admin.id = $routeParams.id;
    $http({
      method: 'PUT',
      url: "api/professors/persistProfessor",
      data: $scope.admin,
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

  $scope.isUnchanged = function(admin) {
    return angular.equals(admin, $scope.master);
  };

});