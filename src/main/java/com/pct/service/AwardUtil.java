package com.pct.service;

import com.pct.domain.Award;
import com.pct.domain.Professor;
import com.pct.domain.dto.AwardDto;

public class AwardUtil {

	public static Award createAwardInstanceFromAwardDto(AwardDto awardDto, Professor mentor) {
		
		Award award = createNewAwardInstanceFromAwardDto(awardDto, mentor);
		award.setId(awardDto.getId());

		return award;
	}

	public static Award createNewAwardInstanceFromAwardDto(AwardDto awardDto, Professor mentor) {
		
		Award award = new Award();
		
		award.setAwardName(awardDto.getAwardName());
		award.setAwardedBy(awardDto.getAwardedBy());
		award.setDateOfAward(awardDto.getDateOfAward());
		award.setAwardType(awardDto.getAwardType());
		award.setAwardField(awardDto.getAwardField());
		award.setProfessor(mentor);
		
		return award;
	}

}
