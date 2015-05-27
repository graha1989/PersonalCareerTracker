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
          $http.get('api/professors/loadProfesorDetails?id=' + id).success(
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
          $http.get("api/professors/findProfessorStartsWith", {
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
        loadLanguages: function(professorId) {
          var deferred = $q.defer();
          $http.get("api/languages/allProfessorLanguages", {
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
          $http.get("api/projectExperiences/allProfessorProjecExperiences", {
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
          $http.get('api/projectExperiences/allProjectTypes').success(callback);
        },
        loadSelectedProjectExperience: function(id, callback) {
          $http.get('api/projectExperiences/selectedProjectExperience?id=' + id).success(callback)
                  .error(callback);
        },
        findProjectsStartsWith: function(value, projectIds) {
          var deferred = $q.defer();
          $http.get("api/projectExperiences/findProjectStartsWith", {
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
        deleteProjectExperience: function(id, callback) {
          $http({
            method: 'DELETE',
            url: 'api/projectExperiences?id=' + id
          }).success(callback).error(callback);
        },
        loadProfessorsPublications: function(professorId) {
          var deferred = $q.defer();
          $http.get("api/publications/allProfessorPublications", {
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
        loadInternationalPublications: function(professorId) {
          var deferred = $q.defer();
          $http.get("api/publications/allInternationalPublications", {
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
        loadAllPublicationTypes: function(params, callback) {
          $http.get('api/publications/allPublicationTypes').success(callback);
        },
        loadAllPublicationCategories: function(params, callback) {
          $http.get('api/publications/allPublicationCategories').success(
                  callback);
        },
        loadSelectedProfessorPublication: function(id, callback) {
          $http.get('api/publications/selectedProfessorPublication?id=' + id)
                  .success(callback).error(callback);
        },
        loadSelectedInternationalPublication: function(id, callback) {
          $http.get(
                  'api/publications/selectedInternationalPublication?id=' + id)
                  .success(callback).error(callback);
        },
        deleteProfessorPublication: function(id, callback) {
          $http({
            method: 'DELETE',
            url: 'api/publications/professorPublication?id=' + id
          }).success(callback).error(callback);
        },
        deleteInternationalPublication: function(id, callback) {
          $http({
            method: 'DELETE',
            url: 'api/publications/internationalPublication?id=' + id
          }).success(callback).error(callback);
        },
        loadWorkExperiences: function(professorId) {
          var deferred = $q.defer();
          $http.get("api/workExperiences/allWorkExperiences", {
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
        loadSelectedWorkExperience: function(id, callback) {
          $http.get('api/workExperiences/selectedWorkExperience?id=' + id)
                  .success(callback).error(callback);
        },
        loadAllInstitutionTypes: function(params, callback) {
          $http.get('api/institutions/allInstitutionTypes')
                  .success(callback);
        },
        findInstutionsStartsWith: function(value, type) {
          var deferred = $q.defer();
          $http.get("api/workExperiences/findInstitutionStartsWith", {
            params: {
              value: value,
              institutionType: type
            }
          }).success(function(response) {
            deferred.resolve(response);
          }).error(function(response) {
            deferred.reject(response);
          });
          return deferred.promise;
        },
        deleteWorkExperience: function(id, callback) {
          $http({
            method: 'DELETE',
            url: 'api/workExperiences?id=' + id
          }).success(callback).error(callback);
        },
        loadProfessorStudies: function(professorId, thesisTypeId) {
          var deferred = $q.defer();
          $http.get("api/studies/allProfessorStudies", {
            params: {
              professorId: professorId,
              thesisTypeId: thesisTypeId
            }
          }).success(function(response) {
            deferred.resolve(response);
          }).error(function(response) {
            deferred.reject(response);
          });
          return deferred.promise;
        },
        loadAllStudyPrograms: function(params, callback) {
          $http.get('api/studies/allStudyPrograms')
                  .success(callback);
        },
        deleteProfessorStudies: function(id, callback) {
          $http({
            method: 'DELETE',
            url: 'api/studies?id=' + id
          }).success(callback).error(callback);
        },
        loadProfessorsSpecializationsAbroad: function(professorId) {
          var deferred = $q.defer();
          $http.get("api/specialization/allProfessorSpecializations", {
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
        deleteProfessorSpecializationAbroad: function(id, callback) {
          $http({
            method: 'DELETE',
            url: 'api/specialization?id=' + id
          }).success(callback).error(callback);
        },
        loadTeachingExperiences: function(professorId) {
          var deferred = $q.defer();
          $http.get("api/teachingExperiences/allTeachingExperiences", {
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
        loadSelectedTeachingExperience: function(id, callback) {
          $http.get('api/teachingExperiences/selectedTeachingExperience?id=' + id)
                  .success(callback).error(callback);
        },
        deleteTeachingExperience: function(id, callback) {
          $http({
            method: 'DELETE',
            url: 'api/teachingExperiences?id=' + id
          }).success(callback).error(callback);
        },
        findSubjectsStartsWith: function(value, subjectIds) {
          var deferred = $q.defer();
          $http.get("api/teachingExperiences/findSubjectsStartsWith", {
            params: {
              value: value,
              subjectIds: subjectIds
            }
          }).success(function(response) {
            deferred.resolve(response);
          }).error(function(response) {
            deferred.reject(response);
          });
          return deferred.promise;
        },
        loadAllInstitutions: function(params, callback) {
          $http.get('api/institutions/allInstitutions').success(callback);
        },
        loadSelectedInstitution: function(id, callback) {
          $http.get('api/institutions/selectedInstitution?id=' + id)
                  .success(callback).error(callback);
        },
        deleteInstitution: function(id, callback) {
          $http({
            method: 'DELETE',
            url: 'api/institutions?id=' + id
          }).success(callback).error(callback);
        },
        loadAllProjects: function(params, callback) {
          $http.get('api/projects/allProjects').success(callback);
        },
        loadSelectedProject: function(id, callback) {
          $http.get('api/projects/selectedProject?id=' + id)
                  .success(callback).error(callback);
        },
        deleteProject: function(id, callback) {
          $http({
            method: 'DELETE',
            url: 'api/projects?id=' + id
          }).success(callback).error(callback);
        },
        loadAllSubjects: function(params, callback) {
          $http.get('api/subjects/allSubjects').success(callback);
        },
        loadSelectedSubject: function(id, callback) {
          $http.get('api/subjects/selectedSubject?id=' + id)
                  .success(callback).error(callback);
        },
        loadAllProfessors: function(params, callback) {
          $http.get('api/professors/allProfessors').success(callback);
        },
        loadAllSurveys: function(professorId, subjectId, callback) {
          $http.get(
                  'api/surveys/allSurveys?professorId=' + professorId
                          + '&subjectId=' + subjectId).success(callback);
        },
        loadSelectedSurvey: function(id, callback) {
          $http.get('api/surveys/selectedSurvey?id=' + id)
                  .success(callback).error(callback);
        },
        deleteSurvey: function(id, callback) {
          $http({
            method: 'DELETE',
            url: 'api/surveys?id=' + id
          }).success(callback).error(callback);
        },
        findProfessorsOrLeadersStartsWith: function(value, projectId, professorsWhoAreLeadersOnThisProject, leadersOnThisProjectWhoAreNotProfessors) {
          var deferred = $q.defer();
          $http.get("api/projects/findProfessorsOrLeadersStartsWith", {
            params: {
              value: value,
              projectId: projectId,
              professorsWhoAreLeadersOnThisProject: professorsWhoAreLeadersOnThisProject,
              leadersOnThisProjectWhoAreNotProfessors: leadersOnThisProjectWhoAreNotProfessors
            }
          }).success(function(response) {
            deferred.resolve(response);
          }).error(function(response) {
            deferred.reject(response);
          });
          return deferred.promise;
        },
        findAllProfessorsOrLeadersStartsWith: function(value, professorsWhoAreLeadersOnThisProject, leadersOnThisProjectWhoAreNotProfessors) {
          var deferred = $q.defer();
          $http.get("api/projects/findProfessorsOrLeadersStartsWith", {
            params: {
              value: value,
              professorsWhoAreLeadersOnThisProject: professorsWhoAreLeadersOnThisProject,
              leadersOnThisProjectWhoAreNotProfessors: leadersOnThisProjectWhoAreNotProfessors
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