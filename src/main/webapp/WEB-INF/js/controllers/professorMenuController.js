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
    $scope.professorId = document.getElementById('currentUserId').value;
  };

  $scope.init = function() {
    $scope.loadThesisTypes();
    $scope.loadResources();
    $scope.initUserId();
  };

  $scope.init();

  $scope.home = function() {
    $location.path('/professorDetails');
  };
  
  $scope.openBachelorMentoring = function() {
    $location.path('/bachelorMentoring/thesisTypeId/' + $scope.thesisTypes[0].id);
  };
    
  $scope.openMasterMentoring = function() {
    $location.path('/masterMentoring/thesisTypeId/' + $scope.thesisTypes[1].id);
  };
  
  $scope.openSpecialisticMentoring = function() {
    $location.path('/specialisticMentoring/thesisTypeId/' + $scope.thesisTypes[2].id);
  };

  $scope.openDoctorMentoring = function() {
    $location.path('/doctorMentoring/thesisTypeId/' + $scope.thesisTypes[3].id);
  };
  
  $scope.openProfessorBachelorStudies = function() {
    $location.path('/studies/professorBachelorStudies/thesisTypeId/' + $scope.thesisTypes[0].id);
  };
  
  $scope.openProfessorMasterStudies = function() {
    $location.path('/studies/professorMasterStudies/thesisTypeId/' + $scope.thesisTypes[1].id);
  };
  
  $scope.openProfessorSpecialisticStudies = function() {
    $location.path('/studies/professorSpecialisticStudies/thesisTypeId/' + $scope.thesisTypes[2].id);
  };
  
  $scope.openProfessorDoctorStudies = function() {
    $location.path('/studies/professorDoctorStudies/thesisTypeId/' + $scope.thesisTypes[3].id);
  };
  
  $scope.openProfessorSpecializationAbroad = function() {
    $location.path('/specializations/professorSpecializationsAbroad');
  };
  
  $scope.openProfessorAwards = function() {
    $location.path('/awards');
  };
  
  $scope.openProfessorOrganizationsMemberships = function() {
    $location.path('/scientificProfessionalOrgMem');
  };
  
  $scope.openProfessorLanguageExperiences = function() {
    $location.path('/languageExperience');
  };
  
  $scope.openProfessorWorkExperiences = function() {
    $location.path('/workExperiences');
  };
  
  $scope.openProfessorTeachingExperiences = function() {
    $location.path('/teachingExperience');
  };
  
  $scope.openProfessorSeminarsOrTeachings = function() {
    $location.path('/seminarOrTeachingAbroad');
  };
  
  $scope.openProfessorProjectExperiences = function() {
    $location.path('/projectExperiences');
  };
  
  $scope.openProfessorPublications = function() {
    $location.path('/publications/professorPublications');
  };
  
  $scope.openInternationalPublications = function() {
    $location.path('/publications/internationalPublications');
  };

});
