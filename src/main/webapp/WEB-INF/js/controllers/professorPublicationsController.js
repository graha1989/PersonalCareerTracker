app.controller("ProfessorPublicationsController", function($scope,
        $routeParams, $http, $location, $modal, PctService) {

  $scope.publications = [];
  $scope.publication = {};

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

  $scope.loadProfessorsPublications = function(professorId) {
    return PctService.loadProfessorsPublications(professorId).then(
            function(response) {
              if (angular.isObject(response) && response.length > 0) {
                $scope.publications = response;
                $scope.noResultsFound = false;
              } else {
                $scope.noResultsFound = true;
              }
            });
  };

  $scope.init = function() {
    $scope.loadProfessorsPublications($routeParams.professorId);
    $scope.loadResources();
  };

  $scope.init();

  $scope.goBack = function() {
    window.history.back();
  };

  $scope.editProfessorPublication = function(id) {
    $modal.open({
      templateUrl: 'editPublicationPopup.html',
      controller: editPublicationPopupController,
      resolve: {
        publicationId: function() {
          return id;
        }
      }
    });
  };

});

var editPublicationPopupController = function($scope, $modalInstance,
        $routeParams, $http, $route, publicationId, PctService) {

  $scope.publication = {};
  $scope.master = {};
  $scope.allPublicationTypes = [];
  $scope.allPublicationCategories = [];
  $scope.professor = {};
  $scope.professorNameAndSurname = "";
  $scope.publicationAuthorsArray = [];

  $scope.patterns = {
    onlyLetters: /^[a-zA-ZčČćĆšŠđĐžŽ ]*$/,
    onlyNumbers: /^[0-9 ]*$/
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

  $scope.loadAllPublicationTypes = function() {
    PctService.loadAllPublicationTypes($routeParams, function(data) {
      if (angular.isObject(data) && data.length > 0) {
        $scope.allPublicationTypes = data;
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };

  $scope.loadAllPublicationCategories = function() {
    PctService.loadAllPublicationCategories($routeParams, function(data) {
      if (angular.isObject(data) && data.length > 0) {
        $scope.allPublicationCategories = data;
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };

  $scope.loadProfessorNameAndSurname = function() {
    PctService.loadProfesor($routeParams.professorId, function(data) {
      if (angular.isObject(data)) {
        $scope.professor = data;
        $scope.professorNameAndSurname = $scope.professor.name + " "
                + $scope.professor.surname;
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };

  $scope.createAuthorsArray = function() {
    var array = [];
    for (var i = 0; i < $scope.publication.authors.split(";").length; i++) {
      array.push($scope.publication.authors.split(";")[i].trim());
    }
    return array;
  }

  $scope.constructPublicationAuthorsString = function(array) {
    $scope.publication.authors = "";
    for (var i = 0; i < array.length; i++) {
      $scope.publication.authors = $scope.publication.authors
              + ((i > 0 && i < array.length) ? "; " : "") + array[i];
    }
  };

  $scope.arrayContainsElement = function(array, element) {
    var index = -1;
    for (var i = 0; i < array.length; i++) {
      if (array[i] === element) {
        index = i;
        break;
      }
    }
    return index;
  };

  $scope.loadSelectedPublication = function(id) {
    PctService.loadSelectedPublication(id, function(data) {
      if (angular.isObject(data)) {
        $scope.publication = data;
        $scope.publication.publicationType = data.publicationType.name;
        $scope.master = angular.copy($scope.publication);
        
        $scope.publicationAuthorsArray = $scope.createAuthorsArray();
        $scope.constructPublicationAuthorsString($scope.publicationAuthorsArray);
        
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };

  $scope.init = function() {
    $scope.loadAllPublicationTypes();
    $scope.loadAllPublicationCategories();
    $scope.loadSelectedPublication(publicationId);
    $scope.loadProfessorNameAndSurname();
    $scope.status = $routeParams.status;
    $scope.loadResources();
  };

  $scope.init();
/*
  $scope.addProjectLeader = function() {
    $scope.projectLeadersArray.push($scope.newProjectLeader);
    $scope.constructLeadersString($scope.projectLeadersArray);
    $scope.newProjectLeader = "";
  };

  $scope.removeProjectLeader = function(index) {
    $scope.projectLeadersArray.splice(index, 1);
    var index = $scope.arrayContainsElement($scope.projectLeadersArray,
            $scope.professorNameAndSurname);
    if (index == -1 && $scope.checkbox.checked) {
      $scope.checkbox.checked = false;
    }
    $scope.constructLeadersString($scope.projectLeadersArray);
  };
*/
  $scope.saveProject = function() {
    $http({
      method: 'PUT',
      url: "api/publications",
      data: $scope.publication,
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

  $scope.isUnchanged = function(publication) {
    return angular.equals(publication, $scope.master);
  };

  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };

};