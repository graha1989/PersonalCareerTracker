package com.pct.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pct.domain.Studies;
import com.pct.domain.dto.StudiesDto;
import com.pct.domain.enums.StudyProgram;
import com.pct.repository.ProfessorStudiesRepository;
import com.pct.service.ProfessorStudiesService;
import com.pct.validation.ProfessorStudiesNotFoundException;

@Service
public class ProfessorStudiesServiceImpl implements ProfessorStudiesService {

	@Autowired
	private ProfessorStudiesRepository professorStudiesRepository;

	@Override
	@Transactional
	public List<StudiesDto> findAllStudies(Long professorId, Long thesisTypeId)
			throws ProfessorStudiesNotFoundException {

		List<StudiesDto> studiesDtos = new ArrayList<StudiesDto>();
		try {
			List<Studies> studiesList = professorStudiesRepository.findAllPublications(professorId, thesisTypeId);
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
		return new ArrayList<StudyProgram>(Arrays.asList(StudyProgram.values()));
	}

}
