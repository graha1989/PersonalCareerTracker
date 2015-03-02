app.factory("PctService", function($http){
	
	return {
		loadStudents: function(params, callback) {
			$http.get('api/students/allStudents').success(callback);
		},
		loadSelectedStudent: function(id, callback) {
			$http.get('api/students/selectedStudent?id='+id).success(callback).error(callback);
		},
		deleteStudent: function(id, callback) {
			$http({
		        method: 'DELETE', 
		        url: 'api/students?id='+id
		    }).success(callback).error(callback);
	    },
	    loadProfesor: function(id, callback) {
			$http.get('api/professor/loadProfesorDetails?id='+id).success(callback).error(callback);
		},
		loadBachelorThesis: function(id, callback) {
			$http.get('api/thesis/allBachelorThesis?id='+id).success(callback);
		},
    };       
});