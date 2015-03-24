package com.pct.service;

import java.util.List;

import com.pct.domain.dto.AwardDto;
import com.pct.domain.enums.AwardField;
import com.pct.domain.enums.AwardType;
import com.pct.validation.AwardNotFoundException;
import com.pct.validation.ProfessorNotFoundException;

public interface AwardService {
	
	List<AwardDto> findAll();

	List<AwardType> findAllAwardTypes();

	List<AwardField> findAllAwardFields();

	AwardDto saveAward(AwardDto awardDto) throws ProfessorNotFoundException;

	AwardDto findAwardById(Long id) throws AwardNotFoundException;

	void deleteAward(Long id) throws AwardNotFoundException;

	List<AwardDto> findAllAwards(Long professorId) throws AwardNotFoundException;
	
}
