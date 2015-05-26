app.controller("ProfessorMenuController", function($scope, $http, $location,
        $routeParams) {

  $scope.thesisTypes = [];
  $scope.resources = {};
  $scope.professorId = '';

  $scope.isActive = function(menu) {
    return (menu === $location.path());
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

  $scope.initUserId = function() {
    console.log('Setting professor id: ');
    $scope.professorId = document.getElementById('currentUserId').value;
    console.log($scope.professorId);
  };

  $scope.init = function() {
    $scope.loadThesisTypes();
    $scope.loadResources();
    $scope.initUserId();
  };

  $scope.init();

  $scope.home = function() {
    $location.path('/professorDetails/id/' + $scope.professorId);
  };

  $scope.openBachelorMentoring = function() {
    $location.path('/bachelorMentoring/mentorId/' + $scope.professorId
            + '/thesisTypeId/' + $scope.thesisTypes[0].id);
  };

  $scope.openMasterMentoring = function() {
    $location.path('/masterMentoring/mentorId/' + $scope.professorId
            + '/thesisTypeId/' + $scope.thesisTypes[1].id);
  };

  $scope.openSpecialisticMentoring = function() {
    $location.path('/specialisticMentoring/mentorId/' + $scope.professorId
            + '/thesisTypeId/' + $scope.thesisTypes[2].id);
  };

  $scope.openDoctorMentoring = function() {
    $location.path('/doctorMentoring/mentorId/' + $scope.professorId
            + '/thesisTypeId/' + $scope.thesisTypes[3].id);
  };
  
  $scope.openProfessorBachelorStudies = function() {
    $location.path('/studies/professorBachelorStudies/professorId/' + $scope.professorId
            + '/thesisTypeId/' + $scope.thesisTypes[0].id);
  };
  
  $scope.openProfessorMasterStudies = function() {
    $location.path('/studies/professorMasterStudies/professorId/' + $scope.professorId
            + '/thesisTypeId/' + $scope.thesisTypes[1].id);
  };
  
  $scope.openProfessorSpecialisticStudies = function() {
    $location.path('/studies/professorSpecialisticStudies/professorId/' + $scope.professorId
            + '/thesisTypeId/' + $scope.thesisTypes[2].id);
  };
  
  $scope.openProfessorDoctorStudies = function() {
    $location.path('/studies/professorDoctorStudies/professorId/' + $scope.professorId
            + '/thesisTypeId/' + $scope.thesisTypes[3].id);
  };
  
  $scope.openProfessorSpecializationAbroad = function() {
    $location.path('/specializations/professorSpecializationsAbroad/professorId/' + $scope.professorId);
  };
  
  $scope.openProfessorAwards = function() {
    $location.path('/awards/professorId/' + $scope.professorId);
  };
  
  $scope.openProfessorLanguageExperiences = function() {
    $location.path('/languageExperience/professorId/' + $scope.professorId);
  };
  
  $scope.openProfessorWorkExperiences = function() {
    $location.path('/workExperiences/professorId/' + $scope.professorId);
  };
  
  $scope.openProfessorTeachingExperiences = function() {
    $location.path('/teachingExperience/professorId/' + $scope.professorId);
  };
  
  $scope.openProfessorProjectExperiences = function() {
    $location.path('/projectExperiences/professorId/' + $scope.professorId);
  };
  
  $scope.openProfessorPublications = function() {
    $location.path('/publications/professorPublications/professorId/' + $scope.professorId);
  };
  
  $scope.openInternationalPublications = function() {
    $location.path('/publications/internationalPublications/professorId/' + $scope.professorId);
  };

});
