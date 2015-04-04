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
  $scope.startPage = 0;
  $scope.endPage = 0;
  $scope.publicationPageRanges = [];

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
  
  $scope.createPageRangesArray = function() {
    var array = [];
    for (var i = 0; i < $scope.publication.pageRange.split(";").length; i++) {
      var oneRangeString = $scope.publication.pageRange.split(";")[i].trim();
      var oneRangeObject = {"startPage": parseInt(oneRangeString.split("-")[0].trim(), 10), "endPage": parseInt(oneRangeString.split("-")[1].trim(), 10)};
      array.push(oneRangeObject);
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
        $scope.publicationPageRanges = $scope.createPageRangesArray();
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

  $scope.addPublicationAuthor = function() {
    $scope.publicationAuthorsArray.push($scope.newPublicationAuthor);
    $scope.constructPublicationAuthorsString($scope.publicationAuthorsArray);
    $scope.newPublicationAuthor = "";
  };

  $scope.removePublicationAuthor = function(index) {
    $scope.publicationAuthorsArray.splice(index, 1);
    $scope.constructPublicationAuthorsString($scope.publicationAuthorsArray);
  };
  
  $scope.addNewPageRange = function() {
    $scope.publicationPageRanges.push({"startPage": $scope.startPage, "endPage": $scope.endPage});
    $scope.startPage = 0;
    $scope.endPage = 0;
  };
  
  $scope.removePageRange = function(index) {
    $scope.publicationPageRanges.splice(index, 1);
  };

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
  
  $scope.validatePageRangeInput = function() {
    return $scope.startPage == 0 || $scope.endPage == 0 || $scope.endPage < $scope.startPage;
  };

  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };

};