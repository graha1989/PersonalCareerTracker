app.factory("PctService", ["$http", "$q", function($http, $q){
	
	return {
		loadStudents : function(params, callback) {
			$http.get('api/students/allStudents').success(callback);
		},
		loadSelectedStudent : function(id, callback) {
			$http.get('api/students/selectedStudent?id='+id).success(callback).error(callback);
		},
		deleteStudent : function(id, callback) {
			$http({
		        method: 'DELETE', 
		        url: 'api/students?id='+id
		    }).success(callback).error(callback);
	    },
	    loadProfesor : function(id, callback) {
			$http.get('api/professor/loadProfesorDetails?id='+id).success(callback).error(callback);
		},
		loadBachelorThesis : function(id, callback) {
			$http.get('api/thesis/allBachelorThesis?id='+id).success(callback);
		},
		loadThesisTypes: function(params, callback) {
			$http.get('api/thesis/allThesisTypes').success(callback);
		},
		findStudentStartsWith : function(value) {
			var deferred = $q.defer();
			$http.get("api/students/findStudentStartsWith", {
				params : {
					value : value
				}
			}).success(function(response) {
				deferred.resolve(response);
			}).error(function(response) {
				deferred.reject(response);
			});
			return deferred.promise;
		},
		findProfessorsStartsWith : function(value, id1, id2) {
			var deferred = $q.defer();
			$http.get("api/professor/findProfessorStartsWith", {
				params : {
					value : value,
					idProf: id1,
					idMentor: id2
				}
			}).success(function(response) {
				deferred.resolve(response);
			}).error(function(response) {
				deferred.reject(response);
			});
			return deferred.promise;
		}
    };       
}]);