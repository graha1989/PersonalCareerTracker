package com.pct.service;

import java.util.List;

import com.pct.domain.dto.KorisnikDTO;
import com.pct.domain.dto.KorisnikFormaDTO;
import com.pct.validation.KorisnikNotFoundException;

public interface KorisnikService {

	List<KorisnikDTO> findAll();

	KorisnikDTO findKorisnikById(Long id) throws KorisnikNotFoundException;

	void saveKorisnik(KorisnikFormaDTO korisnikFormaDTO);
}
