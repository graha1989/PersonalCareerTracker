package com.pct.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pct.domain.dto.AwardDto;
import com.pct.service.AwardService;

@RestController
@RequestMapping("/api/awards")
public class AwardsController {
	
	@Autowired
	AwardService awardService;
	
	@RequestMapping(value = "allAwards", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<AwardDto>> showAllAwards() {
		List<AwardDto> awards = awardService.findAll();

		return new ResponseEntity<List<AwardDto>>(awards, HttpStatus.OK);
	}
	
}
