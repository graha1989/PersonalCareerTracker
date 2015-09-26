package com.pct.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pct.domain.Institution;
import com.pct.domain.Professor;
import com.pct.domain.Studies;
import com.pct.domain.StudiesThesisType;
import com.pct.domain.StudyProgram;
import com.pct.domain.dto.StudiesDto;
import com.pct.repository.InstitutionRepository;
import com.pct.repository.InstitutionTypeRepository;
import com.pct.repository.ProfesorRepository;
import com.pct.repository.ProfessorStudiesRepository;
import com.pct.repository.StudiesThesisTypeRepository;
import com.pct.repository.StudyProgramRepository;
import com.pct.service.ProfessorStudiesService;
import com.pct.validation.InstitutionNotFoundException;
import com.pct.validation.ProfessorNotFoundException;
import com.pct.validation.ProfessorStudiesNotFoundException;
import com.pct.validation.StudiesThesisTypeNotFoundException;

@Service
public class ProfessorStudiesServiceImpl implements ProfessorStudiesService {

	private ProfessorStudiesRepository professorStudiesRepository;
	private ProfesorRepository professorRepository;
	private InstitutionRepository institutionRepository;
	private StudiesThesisTypeRepository studiesRepository;
	private InstitutionTypeRepository institutionTypeRepository;
	private StudyProgramRepository studyProgramRepository;

	@Autowired
	public ProfessorStudiesServiceImpl(ProfessorStudiesRepository professorStudiesRepository,
			ProfesorRepository professorRepository, InstitutionRepository institutionRepository,
			StudiesThesisTypeRepository studiesRepository, InstitutionTypeRepository institutionTypeRepository,
			StudyProgramRepository studyProgramRepository) {
		this.professorStudiesRepository = professorStudiesRepository;
		this.professorRepository = professorRepository;
		this.institutionRepository = institutionRepository;
		this.studiesRepository = studiesRepository;
		this.institutionTypeRepository = institutionTypeRepository;
		this.studyProgramRepository = studyProgramRepository;
	}

	@Override
	@Transactional
	public List<StudiesDto> findAllStudies(Long professorId, Long thesisTypeId)
			throws ProfessorStudiesNotFoundException {

		List<StudiesDto> studiesDtos = new ArrayList<StudiesDto>();
		try {
			List<Studies> studiesList = professorStudiesRepository.findAllStudies(professorId, thesisTypeId);
			for (Studies s : studiesList) {
				StudiesDto studiesDto = new StudiesDto(s);
				studiesDtos.add(studiesDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return studiesDtos;
	}

	@Override
	@Transactional
	public List<StudyProgram> findAllStudyPrograms() {

		List<StudyProgram> studyPrograms = null;
		try {
			studyPrograms = studyProgramRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return studyPrograms;
	}

	@Override
	@Transactional
	public void saveProfessorStudies(StudiesDto studiesDto) throws ProfessorStudiesNotFoundException,
			ProfessorNotFoundException, InstitutionNotFoundException, StudiesThesisTypeNotFoundException {

		Professor professor = professorRepository.findOne(studiesDto.getProfessorId());
		if (professor == null) {
			throw new ProfessorNotFoundException();
		}
		StudiesThesisType studiesType = studiesRepository.findOne(studiesDto.getThesisTypeId());
		if (studiesType == null) {
			throw new StudiesThesisTypeNotFoundException();
		}

		Institution institution = initializeInstitution(studiesDto);
		institutionRepository.save(institution);
		professorStudiesRepository.saveAndFlush(createOrUpdateProfessorStudiesInstanceFromStudiesDto(studiesDto,
				professor, institution, studiesType));
	}

	/**
	 * Creates new Institution entity object or retrieves existing from the database and sets field values from
	 * StudiesDto.
	 * 
	 * @param studiesDto
	 * @return
	 */
	public Institution initializeInstitution(@Nonnull StudiesDto studiesDto) {
		Institution institution = null;
		if (studiesDto.getInstitutionId() != null) {
			institution = institutionRepository.findOne(studiesDto.getInstitutionId());
		} else {
			institution = new Institution();
		}
		institution.setCity(studiesDto.getFacultyCity());
		institution.setCountry(studiesDto.getFacultyCountry());
		institution.setName(studiesDto.getFacultyName());
		institution.setUniversity(studiesDto.getUniversityName());
		institution.setInstitutionType(institutionTypeRepository.findByTypeName(studiesDto.getInstitutionType()));

		return institution;
	}

	public Studies createOrUpdateProfessorStudiesInstanceFromStudiesDto(@Nonnull StudiesDto studiesDto,
			@Nonnull Professor professor, @Nonnull Institution institution, @Nonnull StudiesThesisType studiesType) {

		Studies studies = null;
		if (studiesDto.getId() == null) {
			studies = new Studies();
			studies.setProfessor(professor);
			studies.setStudiesThesisType(studiesType);
		} else {
			studies = professorStudiesRepository.findOne(studiesDto.getId());
		}
		studies.setInstitution(institution);
		studies.setStudyProgram(studyProgramRepository.findByName(studiesDto.getStudyProgram()));
		studies.setStudyArea(studiesDto.getStudyArea());
		studies.setStudyStartDate(studiesDto.getStudyStartDate());
		studies.setStudyEndDate(studiesDto.getStudyEndDate());
		studies.setAverageGrade(studiesDto.getAverageGrade());
		studies.setThesisTitle(studiesDto.getThesisTitle());
		studies.setAcquiredTitle(studiesDto.getAcquiredTitle());

		return studies;
	}

	@Override
	@Transactional
	public void deleteStudies(Long id) throws ProfessorStudiesNotFoundException {

		Studies studies = professorStudiesRepository.findOne(id);
		if (studies == null) {
			throw new ProfessorStudiesNotFoundException();
		}
		professorStudiesRepository.delete(studies);
	}

}
