package com.pct.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pct.domain.Subject;
import com.pct.repository.SubjectRepository;
import com.pct.service.SubjectService;

@Service
public class SubjectServiceImpl implements SubjectService {
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	@Override
	@Transactional
	public List<Subject> findSubjectsStartsWith(String value, List<Long> subjectIds) {

		List<Subject> subjects = null;
		if (subjectIds != null && subjectIds.size() > 0) {
			subjects = subjectRepository.findByNameLikeAndNotInIds(value, subjectIds);
		} else {
			subjects = subjectRepository.findByNameLike(value);
		}
		
		return subjects;
	}

}
