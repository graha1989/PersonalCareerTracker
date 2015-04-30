package com.pct.service;

import java.util.List;

import com.pct.domain.dto.SubjectDto;

public interface SubjectService {
	
	List<SubjectDto> findSubjectsStartsWith(String value, List<Long> subjectIds);

}
