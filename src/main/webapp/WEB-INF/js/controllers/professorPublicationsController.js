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

  $scope.deleteProfessorPublication = function(id, index) {
    PctService.deleteProfessorPublication(id, function(data) {
      if (angular.isObject(data) && data.length > 0) {
        $scope.errorStatus = data.status;
      } else {
        $scope.successStatus = "Successfully deleted professor publication.";
        $scope.publications.splice(index, 1);
        $scope.loadProfessorsPublications($routeParams.professorId);
      }
    });
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

  $scope.createNewProfessorPublication = function() {
    $modal.open({
      templateUrl: 'createNewPublicationPopup.html',
      controller: createNewPublicationController,
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
  $scope.startPage = "";
  $scope.endPage = "";
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
              + ((i > 0 && i < array.length) ? "; " : "") + array[i].startPage
              + "-" + array[i].endPage;
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
                                .constructPublicationPageRangesString($scope.publicationPageRanges);
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
    $scope.startPage = "";
    $scope.endPage = "";
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

  $scope.testArrays = function(array1, array2) {
    if ((!array1[0]) || (!array2[0])) { return false; }
    if (array1.length != array2.length) { return false; }
    var pom = false;
    outer: for (var i = 0; i < array1.length; i++) {
      var object1 = array1[i];
      if ($.isEmptyObject(object1)) {
        return false;
      } else {
        inner: for (var j = 0; j < array2.length; j++) {
          var object2 = array2[j];
          if ($.isEmptyObject(object2)) {
            return false;
          } else {
            if (angular.equals(object1, object2)) {
              pom = true;
              break inner;
            } else {
              pom = false;
              continue inner;
            }
          }
        }
      }
    }
    return pom;
  };

  $scope.isUnchanged = function(publication) {
    if (angular.equals(publication.isbn, $scope.master.isbn)
            && angular.equals(publication.title, $scope.master.title)
            && angular.equals(publication.publisher, $scope.master.publisher)
            && angular.equals(publication.publicationType,
                    $scope.master.publicationType)
            && angular.equals(publication.quoted, $scope.master.quoted)
            && angular.equals(publication.publicationCategory,
                    $scope.master.publicationCategory)
            && $scope.testArrays($scope.publicationAuthorsArray,
                    $scope.masterPublicationAuthorsArray)
            && $scope.testArrays($scope.publicationPageRanges,
                    $scope.masterPublicationPageRanges)) {
      return true;
    } else {
      return false;
    }

  };

  $scope.noAuthors = function() {
    return $scope.publicationAuthorsArray.length == 0;
  };

  $scope.noPageRanges = function() {
    return $scope.publicationPageRanges.length == 0;
  };

  $scope.validatePageRangeInput = function() {
    return $scope.startPage == 0 || $scope.endPage == 0
            || $scope.endPage < $scope.startPage;
  };

  $scope.scientificPublicationNotSelected = function() {
    if (angular.isUndefined($scope.publication.publicationType)
            || $scope.publication.publicationType === null
            || $scope.publication.publicationType === '') {
      $scope.publication.publicationCategory = null;
      return true;
    } else if ($scope.publication.publicationType.name == "SCIENTIFIC") {
      return false;
    } else {
      $scope.publication.publicationCategory = null;
      return true;
    }
  };

  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };

};

var createNewPublicationController = function($scope, $modalInstance,
        $routeParams, $http, $route, PctService) {

  $scope.publication = {};
  $scope.allPublicationTypes = [];
  $scope.allPublicationCategories = [];
  $scope.professor = {};
  $scope.professorNameAndSurname = "";
  $scope.publicationAuthorsArray = [];
  $scope.startPage = "";
  $scope.endPage = "";
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
    PctService
            .loadProfesor(
                    $routeParams.professorId,
                    function(data) {
                      if (angular.isObject(data)) {
                        $scope.professor = data;
                        $scope.professorNameAndSurname = $scope.professor.name
                                + " " + $scope.professor.surname;
                        $scope.publicationAuthorsArray
                                .push($scope.professorNameAndSurname);
                        $scope
                                .constructPublicationAuthorsString($scope.publicationAuthorsArray);
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
              + ((i > 0 && i < array.length) ? "; " : "") + array[i].startPage
              + "-" + array[i].endPage;
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

  $scope.init = function() {
    $scope.loadAllPublicationTypes();
    $scope.loadAllPublicationCategories();
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
    $scope.startPage = "";
    $scope.endPage = "";
  };

  $scope.removePageRange = function(index) {
    $scope.publicationPageRanges.splice(index, 1);
    $scope.constructPublicationPageRangesString($scope.publicationPageRanges);
  };

  $scope.saveProfessorPublication = function() {
    $scope.publication.professorId = $routeParams.professorId;
    $http({
      method: 'POST',
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

  $scope.testArrays = function(array1, array2) {
    if ((!array1[0]) || (!array2[0])) { return false; }
    if (array1.length != array2.length) { return false; }
    var pom = false;
    outer: for (var i = 0; i < array1.length; i++) {
      var object1 = array1[i];
      if ($.isEmptyObject(object1)) {
        return false;
      } else {
        inner: for (var j = 0; j < array2.length; j++) {
          var object2 = array2[j];
          if ($.isEmptyObject(object2)) {
            return false;
          } else {
            if (angular.equals(object1, object2)) {
              pom = true;
              break inner;
            } else {
              pom = false;
              continue inner;
            }
          }
        }
      }
    }
    return pom;
  };

  $scope.validateForm = function() {
    if ($scope.publication.isbn != null && $scope.publication.isbn != ''
            && $scope.publication.title != null
            && $scope.publication.title != ''
            && $scope.publication.authors != null
            && $scope.publication.authors != ''
            && $scope.publication.publisher != null
            && $scope.publication.publisher != ''
            && $scope.publication.pageRange != null
            && $scope.publication.pageRange != ''
            && $scope.publication.quoted != null
            && $scope.publication.quoted != ''
            && $scope.publication.publicationType != null
            && $scope.publication.publicationType != '') {
      if ($scope.publication.publicationType.name === "SCIENTIFIC"
              && $scope.publication.publicationCategory != null
              && $scope.publication.publicationCategory != '') {
        return true;
      } else if ($scope.publication.publicationType.name === "SCIENTIFIC"
              && ($scope.publication.publicationCategory == null || $scope.publication.publicationCategory == '')) {
        return false;
      } else {
        return true;
      }
    } else {
      return false;
    }
  };

  $scope.validatePageRangeInput = function() {
    return $scope.startPage == 0 || $scope.endPage == 0
            || $scope.endPage < $scope.startPage;
  };

  $scope.scientificPublicationNotSelected = function() {
    if (angular.isUndefined($scope.publication.publicationType)
            || $scope.publication.publicationType === null
            || $scope.publication.publicationType === '') {
      $scope.publication.publicationCategory = null;
      return true;
    } else if ($scope.publication.publicationType.name === "SCIENTIFIC") {
      return false;
    } else {
      $scope.publication.publicationCategory = null;
      return true;
    }
  };

  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };

};