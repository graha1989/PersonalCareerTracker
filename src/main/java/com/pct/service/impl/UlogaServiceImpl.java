package com.pct.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pct.domain.Uloga;
import com.pct.repository.UlogaRepository;
import com.pct.service.UlogaService;

/**
 * Service for Uloga repository
 * 
 * @author a.grahovac
 *
 */
@Service
public class UlogaServiceImpl implements UlogaService {
	
	@Autowired
	private UlogaRepository ulogaRepository;
	
	public List<Uloga> findAll() {
		return (List<Uloga>) ulogaRepository.findAll();
	}
	
}
