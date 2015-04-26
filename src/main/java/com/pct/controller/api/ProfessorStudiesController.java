package com.pct.controller.api;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pct.constants.MimeTypes;
import com.pct.constants.RequestMappings;
import com.pct.domain.dto.StudiesDto;
import com.pct.domain.enums.StudyProgram;
import com.pct.service.ProfessorStudiesService;
import com.pct.validation.InstitutionNotFoundException;
import com.pct.validation.ProfessorNotFoundException;
import com.pct.validation.ProfessorStudiesNotFoundException;
import com.pct.validation.StudiesThesisTypeNotFoundException;

@RestController
@RequestMapping("/api/studies")
public class ProfessorStudiesController {

	private static final Logger logger = LoggerFactory.getLogger(ProfessorStudiesController.class);

	@Autowired
	ProfessorStudiesService professorStudiesService;

	@RequestMapping(value = "professorBachelorStudies/allProfessorBachelorStudies", method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<StudiesDto>> showAllProfessorStudies(
			@RequestParam(value = "professorId", required = true) Long professorId,
			@RequestParam(value = "thesisTypeId", required = true) Long thesisTypeId) {

		List<StudiesDto> studiesDtos = null;
		try {
			studiesDtos = professorStudiesService.findAllStudies(professorId, thesisTypeId);
		} catch (ProfessorStudiesNotFoundException e) {
			e.printStackTrace();
		}

		logger.debug("Professor got: " + studiesDtos.size() + " studies.");

		return new ResponseEntity<List<StudiesDto>>(studiesDtos, HttpStatus.OK);
	}

	@RequestMapping(value = "allStudyPrograms", method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<StudyProgram>> showAllStudyPrograms() {
		List<StudyProgram> studyPrograms = professorStudiesService.findAllStudyPrograms();

		return new ResponseEntity<List<StudyProgram>>(studyPrograms, HttpStatus.OK);
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, consumes = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<String> persistProfessorStudies(@Valid @RequestBody StudiesDto studiesDto) {

		try {
			professorStudiesService.saveProfessorStudies(studiesDto);
		} catch (ProfessorStudiesNotFoundException e) {
			e.printStackTrace();
		} catch (ProfessorNotFoundException e) {
			e.printStackTrace();
		} catch (InstitutionNotFoundException e) {
			e.printStackTrace();
		} catch (StudiesThesisTypeNotFoundException e) {
			e.printStackTrace();
		}

		logger.debug("Studies fo professor " + studiesDto.getProfessorId() + " on " + studiesDto.getFacultyName()
				+ ", " + studiesDto.getUniversityName() + " successfully saved.");

		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<StudiesDto> deleteProfessorStudies(
			@RequestParam(value = RequestMappings.ID, required = true) Long id) throws ProfessorStudiesNotFoundException {
		professorStudiesService.deleteStudies(id);

		return new ResponseEntity<StudiesDto>(HttpStatus.OK);
	}

}
