package com.pct.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pct.domain.Institution;
import com.pct.domain.Professor;
import com.pct.domain.Subject;
import com.pct.domain.TeachingExperience;
import com.pct.domain.dto.TeachingExperienceDto;
import com.pct.repository.InstitutionRepository;
import com.pct.repository.InstitutionTypeRepository;
import com.pct.repository.ProfesorRepository;
import com.pct.repository.SubjectRepository;
import com.pct.repository.TeachingExperienceRepository;
import com.pct.service.TeachingExperienceService;
import com.pct.validation.ProfessorNotFoundException;
import com.pct.validation.SimilarDataAlreadyExistsException;
import com.pct.validation.SubjectNotFoundException;
import com.pct.validation.TeachingExperienceNotFoundException;

@Service
public class TeachingExperienceServiceImpl implements TeachingExperienceService {

	private TeachingExperienceRepository teachingExperienceRepository;
	private ProfesorRepository professorRepository;
	private SubjectRepository subjectRepository;
	private InstitutionTypeRepository institutionTypeRepository;
	private InstitutionRepository institutionRepository;

	@Autowired
	public TeachingExperienceServiceImpl(TeachingExperienceRepository teachingExperienceRepository, ProfesorRepository professorRepository,
			SubjectRepository subjectRepository, InstitutionTypeRepository institutionTypeRepository, InstitutionRepository institutionRepository) {
		this.teachingExperienceRepository = teachingExperienceRepository;
		this.professorRepository = professorRepository;
		this.subjectRepository = subjectRepository;
		this.institutionTypeRepository = institutionTypeRepository;
		this.institutionRepository = institutionRepository;
	}

	@Override
	@Transactional
	public List<TeachingExperienceDto> findAllTeachingExperiences(Long professorId, Boolean seminarOrTeachingAbroad)
			throws TeachingExperienceNotFoundException {

		List<TeachingExperienceDto> teachingExperienceDtos = new ArrayList<TeachingExperienceDto>();
		try {
			List<TeachingExperience> teachingExperiences = teachingExperienceRepository.findAllTeachingExperiences(professorId,
					seminarOrTeachingAbroad);
			for (TeachingExperience e : teachingExperiences) {
				TeachingExperienceDto teachingExperienceDto = new TeachingExperienceDto(e);
				teachingExperienceDtos.add(teachingExperienceDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return teachingExperienceDtos;
	}

	@Override
	@Transactional
	public TeachingExperienceDto findTeachingExperienceById(Long id) throws TeachingExperienceNotFoundException {

		TeachingExperienceDto teachingExperienceDto;
		if (id != null) {
			TeachingExperience teachingExperience = teachingExperienceRepository.findOne(id);
			if (teachingExperience != null) {
				teachingExperienceDto = new TeachingExperienceDto(teachingExperience);
				return teachingExperienceDto;
			}
		}

		throw new TeachingExperienceNotFoundException();
	}

	@Override
	@Transactional
	public void saveTeachingExperience(TeachingExperienceDto teachingExperienceDto) throws TeachingExperienceNotFoundException,
			ProfessorNotFoundException, SubjectNotFoundException, SimilarDataAlreadyExistsException {

		if (teachingExperienceRepository.isThereTeachingExperienceWithSimilarPeriod(
				(teachingExperienceDto.getId() != null ? teachingExperienceDto.getId() : new Integer("0")), teachingExperienceDto.getSubjectDto()
						.getId(), teachingExperienceDto.getTeachingStartDate(),
				(teachingExperienceDto.getTeachingEndDate() != null ? teachingExperienceDto.getTeachingEndDate() : new Date())) == 0) {

			Professor professor = professorRepository.findOne(teachingExperienceDto.getProfessorId());
			if (professor == null) {
				throw new ProfessorNotFoundException();
			}

			Subject subject = null;
			try {
				subject = subjectRepository.findOne(teachingExperienceDto.getSubjectDto().getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (subject == null && teachingExperienceDto.getSeminarOrTeachingAbroad()) {
				Institution institution = new Institution();
				institution.setInstitutionType(institutionTypeRepository.findByTypeName(teachingExperienceDto.getSubjectDto().getInstitutionType()));
				institution.setName(teachingExperienceDto.getSubjectDto().getInstitutionName());
				institution.setCity(teachingExperienceDto.getSubjectDto().getInstitutionCity());
				institution.setCountry(teachingExperienceDto.getSubjectDto().getInstitutionCountry());
				institution.setUniversity(teachingExperienceDto.getSubjectDto().getUniversityName());
				institutionRepository.save(institution);

				subject = new Subject();
				subject.setInstitution(institution);
				subject.setName(teachingExperienceDto.getSubjectDto().getSubjectName());
				subject.setSeminarOrTeachingAbroad(teachingExperienceDto.getSeminarOrTeachingAbroad()
						&& teachingExperienceDto.getSubjectDto().getSeminarOrTeachingAbroad());
				subjectRepository.save(subject);
			}

			teachingExperienceRepository.saveAndFlush(createOrUpdateTeachingExperienceInstanceFromTeachingExperienceDto(teachingExperienceDto,
					professor, subject));

		} else {
			throw new SimilarDataAlreadyExistsException("Teaching experience start date/Teaching experience end date",
					"teachingStartDate/teachingEndDate");
		}

	}

	private TeachingExperience createOrUpdateTeachingExperienceInstanceFromTeachingExperienceDto(
			@Nonnull TeachingExperienceDto teachingExperienceDto, @Nonnull Professor professor, @Nonnull Subject subject) {

		TeachingExperience teachingExperience = null;
		if (teachingExperienceDto.getId() == null) {
			teachingExperience = new TeachingExperience();
			teachingExperience.setProfessor(professor);
		} else {
			teachingExperience = teachingExperienceRepository.findOne(teachingExperienceDto.getId());
		}
		teachingExperience.setSubject(subject);
		teachingExperience.setTeachingStartDate(teachingExperienceDto.getTeachingStartDate());
		teachingExperience.setTeachingEndDate(teachingExperienceDto.getTeachingEndDate());
		teachingExperience.setSeminarOrTeachingAbroad(teachingExperienceDto.getSeminarOrTeachingAbroad());

		return teachingExperience;
	}

	@Override
	@Transactional
	public void deleteTeachingExperience(Long id) throws TeachingExperienceNotFoundException {

		TeachingExperience teachingExperience = teachingExperienceRepository.findOne(id);
		if (teachingExperience == null) {
			throw new TeachingExperienceNotFoundException();
		}

		teachingExperienceRepository.delete(teachingExperience);
	}

}
