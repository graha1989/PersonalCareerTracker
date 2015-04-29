package com.pct.service;

import java.util.List;

import com.pct.domain.Subject;

public interface SubjectService {
	
	List<Subject> findSubjectsStartsWith(String value, List<Long> subjectIds);

}
