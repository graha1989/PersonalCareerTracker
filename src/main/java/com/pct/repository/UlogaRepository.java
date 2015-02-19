package com.pct.repository;

import org.springframework.data.repository.CrudRepository;

import com.pct.domain.Uloga;

/**
 * Spring Data repository for Uloga(Role) entity
 * 
 * @author a.grahovac
 *
 */
public interface UlogaRepository extends CrudRepository<Uloga, Long> {
	
}