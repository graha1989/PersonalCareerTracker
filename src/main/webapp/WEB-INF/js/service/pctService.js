app.factory("PctService", function($http){
	
	return {
		loadStudents: function(params, callback) {
			$http.get('showAllStudents/students').success(callback);
		},
		loadSelectedStudent: function(id, callback) {
			$http.get('showAllStudents/selectedStudent?id='+id).success(callback).error(callback);
		},
		deleteStudent: function(id, callback) {
			$http({
		        method: 'DELETE', 
		        url: 'showAllStudents?id='+id
		    }).success(callback).error(callback);
	    },
	    loadProfesor: function(id, callback) {
			$http.get('api/professor/loadProfesorDetails?id='+id).success(callback).error(callback);
		}
    };       
});