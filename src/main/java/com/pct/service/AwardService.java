package com.pct.service;

import java.util.List;

import com.pct.domain.AwardField;
import com.pct.domain.AwardType;
import com.pct.domain.dto.AwardDto;
import com.pct.validation.ProfessorNotFoundException;

public interface AwardService {
	
	List<AwardDto> findAll();

	List<AwardType> findAllAwardTypes();

	List<AwardField> findAllAwardFields();

	AwardDto saveAward(AwardDto awardDto) throws ProfessorNotFoundException;
	
}
