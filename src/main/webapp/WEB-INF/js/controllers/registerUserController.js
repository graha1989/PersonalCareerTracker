app.controller("RegisterUserController", function($scope, $routeParams, $http, $location, $modal, PctService) {

  $scope.professor;
  $scope.resources = {};
  $scope.errorMessages = {};
  $scope.fieldErrors = [];
  $scope.opened = false;
  $scope.isProfessor = false;

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

  $scope.scientificAreas = [{
    'name': "Informatika",
    'specialScientificAreas': [{
      'name': "Informacioni sistemi"
    }, {
      'name': "Računarske nauke"
    }]
  }, {
    'name': "Matematika",
    'specialScientificAreas': [{
      'name': "Primenena matematika"
    }]
  }];

  $scope.patterns = {
    onlyLetters: /^[a-zA-ZčČćĆšŠđĐžŽ ]*$/,
    onlyNumbers: /^[0-9 ]*$/
  };

  $scope.dateOptions = {
    "starting-day": "1"
  };

  $(function() {
    $("[data-hide]").on("click", function() {
      $("." + $(this).attr("data-hide")).hide();
    });
  });

  $scope.loadResources = function() {
    var locale = document.getElementById('localeCode');
    $http.get('messages/profesorDetails_' + locale.value + '.json').success(function(response) {
      $scope.resources = angular.fromJson(response);
    });
    $http.get('messages/errors_' + locale.value + '.json').success(function(response) {
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
    $scope.professor = {};
    $scope.loadResources();
  };

  $scope.init();

  $scope.openProfessorFieldSet = function() {
    $scope.isProfessor = true;
  };

  $scope.saveNewProfessor = function() {
    $http({
      method: 'POST',
      url: "api/professors/persistProfessor",
      data: $scope.professor,
      headers: {
        'Content-Type': 'application/json'
      }
    }).success(function(data, status) {
      $("html, body").animate({
        scrollTop: 0
      }, "slow");
      window.location.href = "login";
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

  $scope.isOtherDataValid = function() {
    if ($scope.professor.fathersName != null && $scope.professor.fathersName != '' && $scope.professor.dateOfBirth != null
            && $scope.professor.dateOfBirth != '' && $scope.professor.placeOfBirth != null && $scope.professor.placeOfBirth != ''
            && $scope.professor.countryOfBirth != null && $scope.professor.countryOfBirth != '' && $scope.professor.scientificArea != null
            && $scope.professor.scientificArea != '' && $scope.professor.specialScientificArea != null
            && $scope.professor.specialScientificArea != '') {
      return true;
    } else {
      return false;
    }
  };

  $scope.validateForm = function() {
    if ($scope.professor.userName != null && $scope.professor.userName != '' && $scope.professor.password != null && $scope.professor.password != ''
            && $scope.professor.name != null && $scope.professor.name != '' && $scope.professor.surname != null && $scope.professor.surname != ''
            && ($scope.isProfessor ? $scope.isOtherDataValid() : true)) {
      return true;
    } else {
      return false;
    }
  };

  $scope.goBack = function() {
    window.history.back();
  };

});