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
  $scope.masterPublicationAuthorsArray = [];
  $scope.startPage = 0;
  $scope.endPage = 0;
  $scope.publicationPageRanges = [];
  $scope.masterPublicationPageRanges = [];

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
      var oneRangeObject = {
        "startPage": parseInt(oneRangeString.split("-")[0].trim(), 10),
        "endPage": parseInt(oneRangeString.split("-")[1].trim(), 10)
      };
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

  $scope.constructPublicationPageRangesString = function(array) {
    $scope.publication.pageRange = "";
    for (var i = 0; i < array.length; i++) {
      $scope.publication.pageRange = $scope.publication.pageRange
              + ((i > 0 && i < array.length) ? "; " : "") + array[i].startPage + "-" + array[i].endPage;
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
    PctService
            .loadSelectedPublication(
                    id,
                    function(data) {
                      if (angular.isObject(data)) {
                        $scope.publication = data;
                        $scope.publication.publicationType = data.publicationType.name;
                        $scope.master = angular.copy($scope.publication);
                        $scope.publicationAuthorsArray = $scope
                                .createAuthorsArray();
                        $scope.masterPublicationAuthorsArray = angular
                                .copy($scope.publicationAuthorsArray);
                        $scope
                                .constructPublicationAuthorsString($scope.publicationAuthorsArray);
                        $scope.publicationPageRanges = $scope
                                .createPageRangesArray();
                        $scope.masterPublicationPageRanges = angular
                                .copy($scope.publicationPageRanges);
                        $scope
                                .constructPublicationPageRangesString($scope.publicationPageRanges)
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
    $scope.publicationPageRanges.push({
      "startPage": $scope.startPage,
      "endPage": $scope.endPage
    });
    $scope.constructPublicationPageRangesString($scope.publicationPageRanges);
    $scope.startPage = 0;
    $scope.endPage = 0;
  };

  $scope.removePageRange = function(index) {
    $scope.publicationPageRanges.splice(index, 1);
    $scope.constructPublicationPageRangesString($scope.publicationPageRanges);
  };

  $scope.saveProfessorPublication = function() {
    $http({
      method: 'PUT',
      url: "api/publications",
      data: $scope.publication
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

  $scope.areArraysEqual = function(array1, array2) {
    var temp = new Array();
    if ((!array1[0]) || (!array2[0])) { // If either is not an array
      return false;
    }
    if (array1.length != array2.length) { return false; }
    // Put all the elements from array1 into a "tagged" array
    for (var i = 0; i < array1.length; i++) {
      key = (typeof array1[i]) + "~" + array1[i];
      // Use "typeof" so a number 1 isn't equal to a string "1".
      if (temp[key]) {
        temp[key]++;
      } else {
        temp[key] = 1;
      }
      // temp[key] = # of occurrences of the value (so an element could appear
      // multiple times)
    }
    // Go through array2 - if same tag missing in "tagged" array, not equal
    for (var i = 0; i < array2.length; i++) {
      key = (typeof array2[i]) + "~" + array2[i];
      if (temp[key]) {
        if (temp[key] == 0) {
          return false;
        } else {
          temp[key]--;
        }
        // Subtract to keep track of # of appearances in array2
      } else { // Key didn't appear in array1, arrays are not equal.
        return false;
      }
    }
    // If we get to this point, then every generated key in array1 showed up the
    // exact same
    // number of times in array2, so the arrays are equal.
    return true;
  }

  $scope.isUnchanged = function(publication) {
    if (angular.equals(publication.isbn, $scope.master.isbn)
            && angular.equals(publication.title, $scope.master.title)
            && angular.equals(publication.publisher, $scope.master.publisher)
            && angular.equals(publication.publicationType,
                    $scope.master.publicationType)
            && angular.equals(publication.quoted, $scope.master.quoted)
            && angular.equals(publication.publicationCategory, $scope.master.publicationCategory)
            && $scope.areArraysEqual($scope.publicationAuthorsArray,
                    $scope.masterPublicationAuthorsArray)
            && $scope.areArraysEqual($scope.publicationPageRanges,
                    $scope.masterPublicationPageRanges)) {
      return true;
    } else {
      return false;
    }

  };

  $scope.validatePageRangeInput = function() {
    return $scope.startPage == 0 || $scope.endPage == 0
            || $scope.endPage < $scope.startPage;
  };

  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };

};