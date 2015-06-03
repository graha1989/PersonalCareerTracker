app
        .controller(
                "InternationalPublicationsController",
                function($scope, $routeParams, $http, $location, $modal,
                        PctService) {

                  $scope.internationalPublications = [];
                  $scope.internationalPublication = {};

                  $scope.noResultsFound = true;
                  $scope.resources = {};
                  $scope.errorMessages = {};

                  $scope.isUser = false;
                  $scope.isAdmin = false;
                  $scope.professorId = '';

                  $scope.loadResources = function() {
                    var locale = document.getElementById('localeCode');
                    $http.get(
                            'messages/profesorDetails_' + locale.value
                                    + '.json').success(function(response) {
                      $scope.resources = angular.fromJson(response);
                    });
                    $http.get('messages/errors_' + locale.value + '.json')
                            .success(
                                    function(response) {
                                      $scope.errorMessages = angular
                                              .fromJson(response);
                                    });
                  };

                  $scope.loadInternationalPublications = function(professorId) {
                    return PctService
                            .loadInternationalPublications(professorId)
                            .then(
                                    function(response) {
                                      if (angular.isObject(response)
                                              && response.length > 0) {
                                        $scope.internationalPublications = response;
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

                  $scope.init = function() {
                    $scope.initUserId();
                    $scope.loadInternationalPublications($scope.professorId);
                    $scope.loadResources();
                    $scope.getCurrentUserRole();
                  };

                  $scope.init();

                  $scope.goBack = function() {
                    window.history.back();
                  };

                  $scope.deleteInternationalPublication = function(id, index) {
                    PctService
                            .deleteInternationalPublication(
                                    id,
                                    function(data) {
                                      if (angular.isObject(data)
                                              && data.length > 0) {
                                        $scope.errorStatus = data.status;
                                      } else {
                                        $scope.successStatus = "Successfully deleted international publication.";
                                        $scope.internationalPublications
                                                .splice(index, 1);
                                        $scope
                                                .loadInternationalPublications($scope.professorId);
                                      }
                                    });
                  };

                  $scope.editInternationalPublication = function(id) {
                    $modal.open({
                      templateUrl: 'editInternationalPublicationPopup.html',
                      controller: editInternationalPublicationPopupController,
                      resolve: {
                        publicationId: function() {
                          return id;
                        },
                        professorId: function() {
                          return $scope.professorId;
                        }
                      }
                    });
                  };

                  $scope.createNewInternationalPublication = function() {
                    $modal
                            .open({
                              templateUrl: 'createNewInternationalPublicationPopup.html',
                              controller: createNewInternationalPublicationController,
                              resolve: {
                                professorId: function() {
                                  return $scope.professorId;
                                }
                              }
                            });
                  };

                  $scope.goBack = function() {
                    window.history.back();
                  };

                });

var editInternationalPublicationPopupController = function($scope,
        $modalInstance, $routeParams, $http, $route, publicationId, PctService,
        professorId) {

  $scope.internationalPublication = {};
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
    PctService.loadProfesor(professorId, function(data) {
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
    for (var i = 0; i < $scope.internationalPublication.authors.split(";").length; i++) {
      array.push($scope.internationalPublication.authors.split(";")[i].trim());
    }
    return array;
  }

  $scope.createPageRangesArray = function() {
    var array = [];
    for (var i = 0; i < $scope.internationalPublication.pagesWithQuotes
            .split(";").length; i++) {
      var oneRangeString = $scope.internationalPublication.pagesWithQuotes
              .split(";")[i].trim();
      var oneRangeObject = {
        "startPage": parseInt(oneRangeString.split("-")[0].trim(), 10),
        "endPage": parseInt(oneRangeString.split("-")[1].trim(), 10)
      };
      array.push(oneRangeObject);
    }
    return array;
  }

  $scope.constructPublicationAuthorsString = function(array) {
    $scope.internationalPublication.authors = "";
    for (var i = 0; i < array.length; i++) {
      $scope.internationalPublication.authors = $scope.internationalPublication.authors
              + ((i > 0 && i < array.length) ? "; " : "") + array[i];
    }
  };

  $scope.constructPublicationPageRangesString = function(array) {
    $scope.internationalPublication.pagesWithQuotes = "";
    for (var i = 0; i < array.length; i++) {
      $scope.internationalPublication.pagesWithQuotes = $scope.internationalPublication.pagesWithQuotes
              + ((i > 0 && i < array.length) ? "; " : "")
              + array[i].startPage
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

  $scope.loadSelectedInternationalPublication = function(id) {
    PctService
            .loadSelectedInternationalPublication(
                    id,
                    function(data) {
                      if (angular.isObject(data)) {
                        $scope.internationalPublication = data;
                        $scope.master = angular
                                .copy($scope.internationalPublication);
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
    $scope.loadSelectedInternationalPublication(publicationId);
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

  $scope.saveInternationalPublication = function() {
    $http({
      method: 'PUT',
      url: "api/publications/internationalPublication",
      data: $scope.internationalPublication
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

  $scope.isUnchanged = function(internationalPublication) {
    if (angular.equals(internationalPublication.isbn, $scope.master.isbn)
            && angular.equals(internationalPublication.title,
                    $scope.master.title)
            && angular.equals(internationalPublication.journalTitle,
                    $scope.master.journalTitle)
            && angular.equals(internationalPublication.publisher,
                    $scope.master.publisher)
            && angular.equals(internationalPublication.publicationType,
                    $scope.master.publicationType)
            && angular
                    .equals(internationalPublication.year, $scope.master.year)
            && angular.equals(internationalPublication.publicationCategoryDto,
                    $scope.master.publicationCategoryDto)
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
            || $scope.endPage <= $scope.startPage;
  };

  $scope.scientificPublicationNotSelected = function() {
    if (angular.isUndefined($scope.internationalPublication.publicationType)
            || $scope.internationalPublication.publicationType === null
            || $scope.internationalPublication.publicationType === '') {
      $scope.internationalPublication.publicationCategoryDto = null;
      return true;
    } else if ($scope.internationalPublication.publicationType.name == "SCIENTIFIC") {
      return false;
    } else {
      $scope.internationalPublication.publicationCategoryDto = null;
      return true;
    }
  };

  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };

};

var createNewInternationalPublicationController = function($scope,
        $modalInstance, $routeParams, $http, $route, PctService, professorId) {

  $scope.internationalPublication = {};
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
    PctService.loadProfesor(professorId, function(data) {
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
    for (var i = 0; i < $scope.internationalPublication.authors.split(";").length; i++) {
      array.push($scope.internationalPublication.authors.split(";")[i].trim());
    }
    return array;
  }

  $scope.createPageRangesArray = function() {
    var array = [];
    for (var i = 0; i < $scope.internationalPublication.pagesWithQuotes
            .split(";").length; i++) {
      var oneRangeString = $scope.internationalPublication.pagesWithQuotes
              .split(";")[i].trim();
      var oneRangeObject = {
        "startPage": parseInt(oneRangeString.split("-")[0].trim(), 10),
        "endPage": parseInt(oneRangeString.split("-")[1].trim(), 10)
      };
      array.push(oneRangeObject);
    }
    return array;
  }

  $scope.constructPublicationAuthorsString = function(array) {
    $scope.internationalPublication.authors = "";
    for (var i = 0; i < array.length; i++) {
      $scope.internationalPublication.authors = $scope.internationalPublication.authors
              + ((i > 0 && i < array.length) ? "; " : "") + array[i];
    }
  };

  $scope.constructPublicationPageRangesString = function(array) {
    $scope.internationalPublication.pagesWithQuotes = "";
    for (var i = 0; i < array.length; i++) {
      $scope.internationalPublication.pagesWithQuotes = $scope.internationalPublication.pagesWithQuotes
              + ((i > 0 && i < array.length) ? "; " : "")
              + array[i].startPage
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

  $scope.saveInternationalPublication = function() {
    $scope.internationalPublication.professorId = professorId;
    $http({
      method: 'POST',
      url: "api/publications/internationalPublication",
      data: $scope.internationalPublication
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

  $scope.validateNewInternationalPublicationForm = function() {
    if ($scope.internationalPublication.isbn != null
            && $scope.internationalPublication.isbn != ''
            && $scope.internationalPublication.title != null
            && $scope.internationalPublication.title != ''
            && $scope.internationalPublication.authors != null
            && $scope.internationalPublication.authors != ''
            && $scope.internationalPublication.publisher != null
            && $scope.internationalPublication.publisher != ''
            && $scope.internationalPublication.pagesWithQuotes != null
            && $scope.internationalPublication.pagesWithQuotes != ''
            && $scope.internationalPublication.year != null
            && $scope.internationalPublication.year != ''
            && $scope.internationalPublication.publicationType != null
            && $scope.internationalPublication.publicationType != '') {
      if ($scope.internationalPublication.publicationType.name === "SCIENTIFIC"
              && $scope.internationalPublication.publicationCategoryDto != null
              && $scope.internationalPublication.publicationCategoryDto != '') {
        return true;
      } else if ($scope.internationalPublication.publicationType.name === "SCIENTIFIC"
              && ($scope.internationalPublication.publicationCategoryDto == null || $scope.internationalPublication.publicationCategoryDto == '')) {
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
            || $scope.endPage <= $scope.startPage;
  };

  $scope.scientificPublicationNotSelected = function() {
    if (angular.isUndefined($scope.internationalPublication.publicationType)
            || $scope.internationalPublication.publicationType === null
            || $scope.internationalPublication.publicationType === '') {
      $scope.internationalPublication.publicationCategoryDto = null;
      return true;
    } else if ($scope.internationalPublication.publicationType.name === "SCIENTIFIC") {
      return false;
    } else {
      $scope.internationalPublication.publicationCategoryDto = null;
      return true;
    }
  };

  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };

};