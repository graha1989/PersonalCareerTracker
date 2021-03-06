package com.pct.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pct.domain.Award;
import com.pct.domain.AwardField;
import com.pct.domain.AwardType;
import com.pct.domain.Professor;
import com.pct.domain.dto.AwardDto;
import com.pct.repository.AwardFieldRepository;
import com.pct.repository.AwardRepository;
import com.pct.repository.AwardTypeRepository;
import com.pct.repository.ProfesorRepository;
import com.pct.service.AwardService;
import com.pct.validation.AwardNotFoundException;
import com.pct.validation.ProfessorNotFoundException;

@Service
public class AwardServiceImpl implements AwardService {

	private AwardRepository awardRepository;
	private ProfesorRepository professorRepository;
	private AwardFieldRepository awardFieldRepository;
	private AwardTypeRepository awardTypeRepository;

	@Autowired
	public AwardServiceImpl(AwardRepository awardRepository, ProfesorRepository professorRepository,
			AwardFieldRepository awardFieldRepository, AwardTypeRepository awardTypeRepository) {
		this.awardRepository = awardRepository;
		this.professorRepository = professorRepository;
		this.awardFieldRepository = awardFieldRepository;
		this.awardTypeRepository = awardTypeRepository;
	}

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

		List<AwardType> awardTypes = null;
		try {
			awardTypes = awardTypeRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return awardTypes;
	}

	@Override
	@Transactional
	public List<AwardField> findAllAwardFields() {

		List<AwardField> awardFields = null;
		try {
			awardFields = awardFieldRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return awardFields;
	}

	@Override
	@Transactional
	public void saveAward(AwardDto awardDto) throws ProfessorNotFoundException {

		Professor professor = professorRepository.findOne(awardDto.getProfessorId());
		if (professor == null) {
			throw new ProfessorNotFoundException();
		}

		awardRepository.saveAndFlush(createOrUpdateAwardInstanceFromAwardDto(awardDto, professor));
	}

	public Award createOrUpdateAwardInstanceFromAwardDto(@Nonnull AwardDto awardDto, @Nonnull Professor professor) {

		Award award = null;
		if (awardDto.getId() == null) {
			award = new Award();
			award.setProfessor(professor);
		} else {
			award = awardRepository.findOne(awardDto.getId());
		}
		award.setAwardName(awardDto.getAwardName());
		award.setAwardedBy(awardDto.getAwardedBy());
		award.setAwardType(awardTypeRepository.findByTypeName(awardDto.getAwardType()));
		award.setAwardField(awardFieldRepository.findByFieldName(awardDto.getAwardField()));
		award.setDateOfAward(awardDto.getDateOfAward());

		return award;
	}

	@Override
	@Transactional
	public AwardDto findAwardById(Long id) throws AwardNotFoundException {

		AwardDto awardDto;
		if (id != null) {
			Award a = awardRepository.findOne(id);
			if (a != null) {
				awardDto = new AwardDto(a);
				return awardDto;
			}
		}
		throw new AwardNotFoundException();
	}

	@Override
	@Transactional
	public void deleteAward(@Nonnull Long id) throws AwardNotFoundException {

		Award award = awardRepository.findOne(id);
		if (award == null) {
			throw new AwardNotFoundException();
		}

		awardRepository.delete(award);
	}

	@Override
	@Transactional
	public List<AwardDto> findAllAwards(Long professorId) throws AwardNotFoundException {

		List<AwardDto> awardDtoList = new ArrayList<AwardDto>();
		try {
			List<Award> awardList = awardRepository.findAllAwards(professorId);
			for (Award a : awardList) {
				AwardDto awardDto = new AwardDto(a);
				awardDtoList.add(awardDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return awardDtoList;
	}

}
