app.controller("AllProfessorsController", function($scope, $routeParams, $http,
        $location, $modal, PctService) {

  $scope.professor = {};
  $scope.allProfessors = [];
  $scope.thesisTypes = [];

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
  
  $scope.loadThesisTypes = function() {
    $http({
      method: 'GET',
      url: "api/thesis/allThesisTypes",
      headers: {
        'Content-Type': 'application/json'
      }
    }).success(function(data, status) {
      if (angular.isObject(data)) {
        $scope.thesisTypes = data;
      }
    }).error(function(data, status) {
    });
  };

  $scope.loadAllProfessors = function() {
    PctService.loadAllProfessors($routeParams, function(data) {
      if (angular.isObject(data) && data.length > 0) {
        $scope.allProfessors = data;
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };

  $scope.init = function() {
    $scope.loadThesisTypes();
    $scope.loadAllProfessors();
    $scope.loadResources();
  };

  $scope.init();

  $scope.showProfessorDetails = function(id) {
    $modal.open({
      templateUrl: 'professorDetailsPopup.html',
      controller: professorDetailsPopupController,
      resolve: {
        professorId: function() {
          return id;
        }
      }
    });
  };
  
  $scope.showProfessorTeahingExperience = function(id) {
    $location.path('/teachingExperience/professorId/' + id);
  };
  
  $scope.showProfessorProjectExperience = function(id) {
    $location.path('/projectExperiences/professorId/' + id);
  };
  
  $scope.openProfessorBachelorStudies = function(id) {
    $location.path('/studies/professorBachelorStudies/professorId/' + id
            + '/thesisTypeId/' + $scope.thesisTypes[0].id);
  };
  
  $scope.openProfessorMasterStudies = function(id) {
    $location.path('/studies/professorMasterStudies/professorId/' + id
            + '/thesisTypeId/' + $scope.thesisTypes[1].id);
  };
  
  $scope.openProfessorSpecialisticStudies = function(id) {
    $location.path('/studies/professorSpecialisticStudies/professorId/' + id
            + '/thesisTypeId/' + $scope.thesisTypes[2].id);
  };
  
  $scope.openProfessorDoctorStudies = function(id) {
    $location.path('/studies/professorDoctorStudies/professorId/' + id
            + '/thesisTypeId/' + $scope.thesisTypes[3].id);
  };
  
  $scope.openProfessorSpecializationAbroad = function(id) {
    $location.path('/specializations/professorSpecializationsAbroad/professorId/' + id);
  };
  
  $scope.openBachelorMentoring = function(id) {
    $location.path('/bachelorMentoring/mentorId/' + id
            + '/thesisTypeId/' + $scope.thesisTypes[0].id);
  };

  $scope.openMasterMentoring = function(id) {
    $location.path('/masterMentoring/mentorId/' + id
            + '/thesisTypeId/' + $scope.thesisTypes[1].id);
  };

  $scope.openSpecialisticMentoring = function(id) {
    $location.path('/specialisticMentoring/mentorId/' + id
            + '/thesisTypeId/' + $scope.thesisTypes[2].id);
  };

  $scope.openDoctorMentoring = function(id) {
    $location.path('/doctorMentoring/mentorId/' + id
            + '/thesisTypeId/' + $scope.thesisTypes[3].id);
  };
  
  $scope.openProfessorPublications = function(id) {
    $location.path('/publications/professorPublications/professorId/' + id);
  };
  
  $scope.openInternationalPublications = function(id) {
    $location.path('/publications/internationalPublications/professorId/' + id);
  };
  
  $scope.openProfessorAwards = function(id) {
    $location.path('/awards/professorId/' + id);
  };
  
  $scope.openProfessorLanguageExperiences = function(id) {
    $location.path('/languageExperience/professorId/' + id);
  };
  
  $scope.openProfessorWorkExperiences = function(id) {
    $location.path('/workExperiences/professorId/' + id);
  };
  
});

var professorDetailsPopupController = function($scope, $modalInstance,
        $routeParams, $http, $route, professorId, PctService) {

  $scope.professor = {};

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

  $scope.loadSelectedProfessor = function(id) {
    PctService.loadProfesor(id, function(data) {
      if (angular.isObject(data)) {
        $scope.professor = data;
        $scope.professor.dateOfBirth = new Date(data.dateOfBirth);
        $scope.noResultsFound = false;
      } else {
        $scope.noResultsFound = true;
      }
    });
  };

  $scope.init = function() {
    $scope.loadSelectedProfessor(professorId);
    $scope.status = $routeParams.status;
    $scope.loadResources();
  };

  $scope.init();

  $scope.cancel = function() {
    $modalInstance.dismiss('cancel');
  };

};