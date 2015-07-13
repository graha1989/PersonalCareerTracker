package com.pct.controller.api;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
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
import com.pct.domain.dto.CommissionMemberDto;
import com.pct.domain.dto.ContestDto;
import com.pct.service.CommissionMemberService;
import com.pct.service.ContestService;

@RestController
@RequestMapping(RequestMappings.CONTEST_API)
public class ContestController {

	@Autowired
	private ContestService contestService;

	@Autowired
	private CommissionMemberService commissionMemberService;

	private static final Logger logger = LoggerFactory.getLogger(ContestController.class);

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, consumes = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<String> persistContest(@Valid @RequestBody ContestDto contestDto) {
		contestService.saveContest(contestDto);
		logger.debug("Contest for candidate with ID {} successfully saved.", contestDto.getCandidateId());

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(value = RequestMappings.LOAD_ALL_PROFESSORS_OR_COMMISSION_MEMBERS_STARTS_WITH, method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<CommissionMemberDto>> findProfessorsOrCommissionMembersStartsWith(
			@RequestParam(value = "value", required = true) String value,
			@RequestParam(value = "contestId", required = false) Long contestId,
			@RequestParam(value = "professorsWhoAreCommissionMembersOnThisContest", required = false) @Nullable List<Long> professorsWhoAreCommissionMembersOnThisContest,
			@RequestParam(value = "commissionMembersOnThisContestWhoAreNotProfessors", required = false) @Nullable List<Long> commissionMembersOnThisContestWhoAreNotProfessors) {

		List<CommissionMemberDto> commissionMemberDtos = new ArrayList<CommissionMemberDto>();
		if (value.length() >= 3) {
			commissionMemberDtos = commissionMemberService.findCommissionMembersStartsWith(value, contestId,
					professorsWhoAreCommissionMembersOnThisContest, commissionMembersOnThisContestWhoAreNotProfessors);
		}

		return new ResponseEntity<List<CommissionMemberDto>>(commissionMemberDtos, HttpStatus.OK);
	}

}
