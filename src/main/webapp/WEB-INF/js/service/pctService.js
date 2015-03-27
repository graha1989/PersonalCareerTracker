app.factory("PctService", [
    "$http",
    "$q",
    function($http, $q) {

      return {
        loadStudents: function(params, callback) {
          $http.get('api/students/allStudents').success(callback);
        },
        loadSelectedStudent: function(id, callback) {
          $http.get('api/students/selectedStudent?id=' + id).success(callback)
                  .error(callback);
        },
        deleteStudent: function(id, callback) {
          $http({
            method: 'DELETE',
            url: 'api/students?id=' + id
          }).success(callback).error(callback);
        },
        loadProfesor: function(id, callback) {
          $http.get('api/professor/loadProfesorDetails?id=' + id).success(
                  callback).error(callback);
        },
        loadThesis: function(mentorId, thesisTypeId, callback) {
          $http.get(
                  'api/thesis/allThesis?mentorId=' + mentorId
                          + '&thesisTypeId=' + thesisTypeId).success(callback);
        },
        loadThesisTypes: function(params, callback) {
          $http.get('api/thesis/allThesisTypes').success(callback);
        },
        loadThesisType: function(id, callback) {
          $http.get('api/thesis/loadThesisTypeDetails?id=' + id).success(
                  callback).error(callback);
        },
        loadSelectedThesis: function(id, callback) {
          $http.get('api/thesis/selectedThesis?id=' + id).success(callback)
                  .error(callback);
        },
        deleteThesis: function(id, callback) {
          $http({
            method: 'DELETE',
            url: 'api/thesis?id=' + id
          }).success(callback).error(callback);
        },
        findStudentStartsWith: function(value) {
          var deferred = $q.defer();
          $http.get("api/students/findStudentStartsWith", {
            params: {
              value: value
            }
          }).success(function(response) {
            deferred.resolve(response);
          }).error(function(response) {
            deferred.reject(response);
          });
          return deferred.promise;
        },
        findProfessorsStartsWith: function(value, id1, id2) {
          var deferred = $q.defer();
          $http.get("api/professor/findProfessorStartsWith", {
            params: {
              value: value,
              idProf: id1,
              idMentor: id2
            }
          }).success(function(response) {
            deferred.resolve(response);
          }).error(function(response) {
            deferred.reject(response);
          });
          return deferred.promise;
        },
        loadLanguages: function(mentorId) {
          var deferred = $q.defer();
          $http.get("api/languages/allProfessorLanguages", {
            params: {
              mentorId: mentorId
            }
          }).success(function(response) {
            deferred.resolve(response);
          }).error(function(response) {
            deferred.reject(response);
          });
          return deferred.promise;
        },
        loadAllLanguages: function(languageExperienceIdsList) {
          var deferred = $q.defer();
          $http.get("api/languages/allLanguages", {
            params: {
              languageExperienceIdsList: languageExperienceIdsList
            }
          }).success(function(response) {
            deferred.resolve(response);
          }).error(function(response) {
            deferred.reject(response);
          });
          return deferred.promise;
        },
        deleteLanguageExperience: function(id, callback) {
          $http({
            method: 'DELETE',
            url: 'api/languages?id=' + id
          }).success(callback).error(callback);
        },
        loadAwards: function(params, callback) {
          $http.get('api/awards/allAwards').success(callback);
        },
        loadProfessorAwards: function(professorId) {
          var deferred = $q.defer();
          $http.get("api/awards/allProfessorAwards", {
            params: {
              professorId: professorId
            }
          }).success(function(response) {
            deferred.resolve(response);
          }).error(function(response) {
            deferred.reject(response);
          });
          return deferred.promise;
        },
        loadAllAwardTypes: function(params, callback) {
          $http.get('api/awards/allAwardTypes').success(callback);
        },
        loadAllAwardFields: function(params, callback) {
          $http.get('api/awards/allAwardFields').success(callback);
        },
        loadSelectedAward: function(id, callback) {
          $http.get('api/awards/selectedAward?id=' + id).success(callback)
                  .error(callback);
        },
        deleteAward: function(id, callback) {
          $http({
            method: 'DELETE',
            url: 'api/awards?id=' + id
          }).success(callback).error(callback);
        },
        loadProfessorProjectExperiences: function(professorId) {
          var deferred = $q.defer();
          $http.get("api/projects/allProfessorProjecExperiences", {
            params: {
              professorId: professorId
            }
          }).success(function(response) {
            deferred.resolve(response);
          }).error(function(response) {
            deferred.reject(response);
          });
          return deferred.promise;
        },
        loadAllProjectTypes: function(params, callback) {
          $http.get('api/projects/allProjectTypes').success(callback);
        },
        loadSelectedProject: function(id, callback) {
          $http.get('api/projects/selectedProject?id=' + id).success(callback)
                  .error(callback);
        },
        findProjectsStartsWith: function(value, projectIds) {
          var deferred = $q.defer();
          $http.get("api/projects/findProjectStartsWith", {
            params: {
              value: value,
              projectIds: projectIds
            }
          }).success(function(response) {
            deferred.resolve(response);
          }).error(function(response) {
            deferred.reject(response);
          });
          return deferred.promise;
        },
      };
    }]);