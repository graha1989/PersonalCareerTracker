package com.pct.constants;

/**
 * Constants for Controller mappings.
 * 
 * @author a.grahovac
 * 
 */
public final class RequestMappings {

	public static final String ROOT = "/";
	public static final String LOGIN = "/login";

	/** URLs for Rest Controllers */
	public static final String ADMINS_API = "/api/admins";
	public static final String LOAD_ADMIN_DETAILS = "loadAdminDetails";

	public static final String AWARDS_API = "/api/awards";
	public static final String LOAD_ALL_AWARDS = "allAwards";
	public static final String LOAD_ALL_PROFESSOR_AWARDS = "allProfessorAwards";
	public static final String LOAD_AWARD_TYPES = "allAwardTypes";
	public static final String LOAD_AWARD_FIELDS = "allAwardFields";
	public static final String LOAD_SELECTED_AWARD = "selectedAward";

	public static final String INSTITUTIONS_API = "/api/institutions";
	public static final String LOAD_ALL_INSTITUTIONS = "allInstitutions";
	public static final String LOAD_INSTITUTION_TYPES = "allInstitutionTypes";
	public static final String LOAD_SELECTED_INSTITUTION = "selectedInstitution";

	public static final String LANGUAGES_API = "/api/languages";
	public static final String LOAD_ALL_LANGUAGES = "allLanguages";
	public static final String LOAD_ALL_PROFESSOR_LANGUAGES = "allProfessorLanguages";
	public static final String LOAD_SELECTED_LANGUAGE = "selectedLanguage";

	public static final String PROFESSORS_API = "/api/professors";
	public static final String PERSIST_PROFESSOR = "persistProfessor";
	public static final String LOAD_SELECTED_PROFESSOR = "selectedProfesor";
	public static final String LOAD_PROFESSOR_DETAILS = "loadProfessorDetails";
	public static final String LOAD_PROFESSOR_STARTS_WITH_AND_DIFFERENT_ID = "findProfessorStartsWith";
	public static final String LOAD_PROFESSOR_STARTS_WITH = "findAllProfessorStartsWith";
	public static final String LOAD_ALL_PROFESSORS = "allProfessors";

	public static final String SPECIALIZATION_API = "/api/specialization";
	public static final String LOAD_ALL_PROFESSOR_SPECIALIZATIONS = "allProfessorSpecializations";

	public static final String PROFESSOR_STUDIES_API = "/api/studies";
	public static final String LOAD_ALL_PROFESSOR_STUDIES = "allProfessorStudies";
	public static final String LOAD_ALL_STUDY_PROGRAMS = "allStudyPrograms";

	public static final String PROJECTS_API = "/api/projects";
	public static final String LOAD_ALL_PROJECTS = "allProjects";
	public static final String LOAD_ALL_PROJECT_TYPES = "allProjectTypes";
	public static final String LOAD_ALL_PROJECT_PROFESSORS_OR_LEADERS_STARTS_WITH = "findProfessorsOrLeadersStartsWith";

	public static final String PROJECT_EXPERIENCES_API = "/api/projectExperiences";
	public static final String LOAD_ALL_PROFESSOR_PROJECT_EXPERIENCES = "allProfessorProjecExperiences";
	public static final String LOAD_SELECTED_PROJECT_EXPERIENCE = "selectedProjectExperience";
	public static final String LOAD_ALL_PROJECTS_STARTS_WITH = "findProjectStartsWith";

	public static final String PUBLICATIONS_API = "/api/publications";
	public static final String LOAD_ALL_PROFESSOR_PUBLICATIONS = "allProfessorPublications";
	public static final String LOAD_ALL_INTERNATIONAL_PUBLICATIONS = "allInternationalPublications";
	public static final String LOAD_ALL_PUBLICATION_TYPES = "allPublicationTypes";
	public static final String LOAD_ALL_PUBLICATION_CATEGORIES = "allPublicationCategories";
	public static final String LOAD_SELECTED_PROFESSOR_PUBLICATION = "selectedProfessorPublication";
	public static final String LOAD_SELECTED_INTERNATIONAL_PUBLICATION = "selectedInternationalPublication";
	public static final String PROFESSOR_PUBLICATION = "professorPublication";
	public static final String INTERNATIONAL_PUBLICATION = "internationalPublication";

	public static final String STUDENTS_API = "/api/students";
	public static final String LOAD_ALL_STUDENTS = "allStudents";
	public static final String LOAD_SELECTED_STUDENT = "selectedStudent";
	public static final String LOAD_ALL_STUDENTS_STARTS_WITH = "findStudentStartsWith";

	public static final String SUBJECTS_API = "/api/subjects";
	public static final String LOAD_ALL_SUBJECTS = "allSubjects";
	public static final String LOAD_SELECTED_SUBJECT = "selectedSubject";

	public static final String SURVEYS_API = "/api/surveys";
	public static final String LOAD_ALL_SURVEYS = "allSurveys";
	public static final String LOAD_SELECTED_SURVEY = "selectedSurvey";

	public static final String TEACHING_EXPERIENCES_API = "/api/teachingExperiences";
	public static final String LOAD_ALL_TEACHING_EXPERIENCES = "allTeachingExperiences";
	public static final String LOAD_SELECTED_TEACHING_EXPERIENCE = "selectedTeachingExperience";
	public static final String LOAD_SUBJECTS_STARTS_WITH = "findSubjectsStartsWith";

	public static final String THESIS_API = "/api/thesis";
	public static final String LOAD_ALL_THESIS = "allThesis";
	public static final String LOAD_ALL_THESIS_TYPES = "allThesisTypes";
	public static final String LOAD_ALL_THESIS_TYPE_DETAILS = "loadThesisTypeDetails";
	public static final String LOAD_SELECTED_THESIS = "selectedThesis";

	public static final String WORK_EXPERIENCES_API = "/api/workExperiences";
	public static final String LOAD_ALL_WORK_EXPERIENCES = "allWorkExperiences";
	public static final String LOAD_SELECTED_WORK_EXPERIENCE = "selectedWorkExperience";
	public static final String LOAD_INSTITUTIONS_STARTS_WITH = "findInstitutionStartsWith";

	public static final String ID = "id";

	/**
	 * Constructor. Prevents initialization.
	 */
	private RequestMappings() {
		// EMPTY CONSTRUCTOR.
	}

}
