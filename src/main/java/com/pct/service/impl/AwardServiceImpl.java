package com.pct.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pct.domain.Award;
import com.pct.domain.Professor;
import com.pct.domain.dto.AwardDto;
import com.pct.domain.enums.AwardField;
import com.pct.domain.enums.AwardType;
import com.pct.repository.AwardRepository;
import com.pct.repository.ProfesorRepository;
import com.pct.service.AwardService;
import com.pct.service.util.AwardUtil;
import com.pct.validation.AwardNotFoundException;
import com.pct.validation.ProfessorNotFoundException;

@Service
public class AwardServiceImpl implements AwardService {

	@Autowired
	private AwardRepository awardRepository;

	@Autowired
	private ProfesorRepository professorRepository;

	@Override
	@Transactional
	public List<AwardDto> findAll() {

		List<AwardDto> awardsDtoList = new ArrayList<AwardDto>();

		List<Award> awardsList = awardRepository.findAll();
		for (Award a : awardsList) {
			AwardDto awardDto = new AwardDto(a);
			awardDto.setId(a.getId());
			awardsDtoList.add(awardDto);
		}
		return awardsDtoList;
	}

	@Override
	@Transactional
	public List<AwardType> findAllAwardTypes() {
		return new ArrayList<AwardType>(Arrays.asList(AwardType.values()));
	}

	@Override
	@Transactional
	public List<AwardField> findAllAwardFields() {
		return new ArrayList<AwardField>(Arrays.asList(AwardField.values()));
	}

	@Override
	@Transactional
	public AwardDto saveAward(AwardDto awardDto) throws ProfessorNotFoundException {

		Award award = new Award();
		Professor mentor = new Professor();

		if (awardDto.getMentorId() == null || professorRepository.findOne(awardDto.getMentorId()) == null) {
			throw new ProfessorNotFoundException();
		} else {
			mentor = professorRepository.findOne(awardDto.getMentorId());
		}

		if (awardDto.getId() != null) {
			award = AwardUtil.createAwardInstanceFromAwardDto(awardDto, mentor);
		} else {
			award = AwardUtil.createNewAwardInstanceFromAwardDto(awardDto, mentor);
		}

		return new AwardDto(awardRepository.save(award));
	}

	@Override
	@Transactional
	public AwardDto findAwardById(Long id) throws AwardNotFoundException {

		AwardDto awardDto;

		if (id == null || awardRepository.findOne(id) == null) {
			throw new AwardNotFoundException();
		} else {
			Award award = awardRepository.findOne(id);
			awardDto = new AwardDto(award);
		}

		return awardDto;
	}

	@Override
	@Transactional
	public void deleteAward(Long id) throws AwardNotFoundException {

		if (id == null || awardRepository.findOne(id) == null) {
			throw new AwardNotFoundException();
		}

		awardRepository.delete(id);

	}

}
