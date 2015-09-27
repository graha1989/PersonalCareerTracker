app.controller("ScientificJournalReviewsController", function($scope, $routeParams, $http, $route, $modal,
        PctService) {

  $scope.reviews = [];
  $scope.review = {};
  $scope.noResultsFound = true;
  $scope.resources = {};
  $scope.errorMessages = {};

  $scope.isUser = false;
  $scope.isAdmin = false;
  $scope.professorId = '';

  $scope.type = "Recenzije u naucnim casopisima";

  $scope.loadResources = function() {
    var locale = document.getElementById('localeCode');
    $http.get('messages/profesorDetails_' + locale.value + '.json').success(function(response) {
      $scope.resources = angular.fromJson(response);
    });
    $http.get('messages/errors_' + locale.value + '.json').success(function(response) {
      $scope.errorMessages = angular.fromJson(response);
    });
  };

  $scope.loadReviews = function(professorId, type) {
    return PctService.loadAcademicCommunityContributions(professorId, type).then(function(response) {
      if (angular.isObject(response) && response.length > 0) {
        $scope.reviews = response;
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
    $scope.loadReviews($scope.professorId, $scope.type);
    $scope.loadResources();
    $scope.getCurrentUserRole();
  };

  $scope.init();

  $scope.goBack = function() {
    window.history.back();
  };

  $scope.editReview = function(id) {
    $modal.open({
      templateUrl: 'editScientificJournalReviewsPopup.html',
      controller: editScientificJournalReviewsController,
      resolve: {
        reviewId: function() {
          return id;
        },
        professorId: function() {
          return $scope.professorId;
        }
      }
    });
  };

  $scope.createNewReview = function() {
    $modal.open({
      templateUrl: 'createNewScientificJournalReviewsPopup.html',
      controller: createNewScientificJournalReviewsController,
      resolve: {
        professorId: function() {
          return $scope.professorId;
        }
      }
    });
  };

  $scope.deleteReview = function(id, index) {
    PctService.deleteAcademicCommunityContribution(id, function(data) {
      if (angular.isObject(data)) {
        $scope.errorStatus = data.status;
      } else {
        $scope.successStatus = "Successfully deleted scientific journal review.";
        $scope.reviews.splice(index, 1);
        $scope.loadReviews($scope.professorId, $scope.type);
      }
    });
  };

});

var editScientificJournalReviewsController = function($scope, $modalInstance, $routeParams, $http, $route, reviewId,
        PctService, professorId) {

  $scope.review = {};
  $scope.master = {};
  $scope.allCategories = [];

  $scope.loadResources = function() {
    var locale = document.getElementById('localeCode');
    $http.get('messages/profesorDetails_' + locale.value + '.json').success(function(response) {
      $scope.resources = angular.fromJson(response);
    });
    $http.get('messages/errors_' + locale.value + '.json').success(function(response) {
      $scope.errorMessages = angular.fromJson(response);
    });
  };
  
  $scope.loadAllCategories = function() {
    PctService.loadAllPublicationCategories($routeParams, function(data) {
      if (angular.isObject(data) && data.length > 0) {
        $scope.allCategories = data;
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };

  $scope.loadSelectedReview = function(id) {
    PctService.loadSelectedAcademicCommunityContribution(id, function(data) {
      if (angular.isObject(data)) {
        $scope.review = data;
        $scope.master = angular.copy($scope.review);
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };

  $scope.init = function() {
    $scope.loadSelectedReview(reviewId);
    $scope.loadAllCategories();
    $scope.status = $routeParams.status;
    $scope.loadResources();
  };

  $scope.init();

  $scope.saveReview = function() {
    $http({
      method: 'PUT',
      url: "api/academicCommunityContribution/saveAcademicCommunityContribution",
      data: $scope.review,
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

  $scope.isUnchanged = function(review) {
    return angular.equals(review, $scope.master);
  };

  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };

};

var createNewScientificJournalReviewsController = function($scope, $modalInstance, $routeParams, $http, $route,
        PctService, professorId) {

  $scope.review = {};
  $scope.allCategories = [];

  $scope.loadResources = function() {
    var locale = document.getElementById('localeCode');
    $http.get('messages/profesorDetails_' + locale.value + '.json').success(function(response) {
      $scope.resources = angular.fromJson(response);
    });
    $http.get('messages/errors_' + locale.value + '.json').success(function(response) {
      $scope.errorMessages = angular.fromJson(response);
    });
  };

  $scope.loadAllCategories = function() {
    PctService.loadAllPublicationCategories($routeParams, function(data) {
      if (angular.isObject(data) && data.length > 0) {
        $scope.allCategories = data;
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };

  $scope.init = function() {
    $scope.review.professorId = professorId;
    $scope.review.type = {
      name: "REVIEWS_IN_SCIENTIFIC_JOURNALS",
      title: "Recenzije u naucnim casopisima"
    };
    $scope.loadAllCategories();
    $scope.status = $routeParams.status;
    $scope.loadResources();
  };

  $scope.init();

  $scope.saveNewReview = function() {
    $http({
      method: 'POST',
      url: "api/academicCommunityContribution/saveAcademicCommunityContribution",
      data: $scope.review,
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
    if ($scope.review.authorityOrganizationOrJournal != null && $scope.review.authorityOrganizationOrJournal != ''
            && $scope.review.journalCategory != null && $scope.review.journalCategory != '') {
      return true;
    } else {
      return false;
    }
  };

  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };

};
