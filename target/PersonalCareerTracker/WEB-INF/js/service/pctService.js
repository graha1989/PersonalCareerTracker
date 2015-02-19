app.factory("PctService", function($http){
	var searchForm = {};
	return {
		loadStudents: function(params, callback) {
			$http.get('showAllStudents/students').success(callback);
		},
		
		
		
		search: function(params, callback) {
			 $http({
				  method: 'POST', 
				  url: "api/search/s",
				  data: searchForm,
				  headers: {'Content-Type': 'application/json'}
				  }).success(callback);
	    },
	    loadLivingExperienceCountries: function(callback) {
			$http.get('api/search/lecountries').success(callback);
	    },
	    loadLanguages: function(callback) {
			$http.get('api/search/languages').success(callback);
	    },
	    loadCountries: function(callback) {
	    	$http.get('api/search/countries').success(callback);
	    },
	    loadSpecializations: function(callback) {
	    	$http.get('api/search/specializations').success(callback);
	    },
	    saveSearchForm: function(form) {
	    	searchForm = form;
	    },
	    getSearchForm: function() {
	    	return searchForm;
	    }
    };       
});