package com.pct.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pct.domain.Korisnik;
import com.pct.domain.Profesor;
import com.pct.domain.dto.KorisnikDTO;
import com.pct.domain.dto.KorisnikFormaDTO;
import com.pct.repository.KorisnikRepository;
import com.pct.repository.ProfesorRepository;
import com.pct.service.KorisnikService;
import com.pct.service.KorisnikUtil;
import com.pct.validation.KorisnikNotFoundException;

/**
 * User(Korisnik) service implementation class.
 * 
 * @author a.grahovac
 * 
 */
@Service
public class KorisnikServiceImpl implements KorisnikService {

	@Autowired
	private KorisnikRepository korisnikRepository;
	
	@Autowired
	private ProfesorRepository profesorRepository;

	@Override
	@Transactional
	public List<KorisnikDTO> findAll() {
		List<KorisnikDTO> korisnikDTOList = new ArrayList<KorisnikDTO>();

		List<Korisnik> korisnikList = korisnikRepository.findAll();
		for (Korisnik k : korisnikList) {
			KorisnikDTO korisnikDTO = new KorisnikDTO(k.getUserName(), k.getPassword(), k.getEmail(), k.getUloga(),
					false, k.getId());
			korisnikDTOList.add(korisnikDTO);
		}
		return korisnikDTOList;
	}

	@Override
	@Transactional
	public KorisnikDTO findKorisnikById(Long id) throws KorisnikNotFoundException {

		KorisnikDTO korisnikDTO;

		if (id == null || korisnikRepository.findOne(id) == null) {
			throw new KorisnikNotFoundException();
		} else {
			Korisnik korisnik = korisnikRepository.findOne(id);
			korisnikDTO = new KorisnikDTO(korisnik);
		}

		return korisnikDTO;
	}

	@Override
	@Transactional
	public void saveKorisnik(KorisnikFormaDTO korisnikFormaDTO) {
		
		Korisnik korisnik = null;
		Profesor profesor = null;
		
		if (korisnikFormaDTO.getId() != null) {
			korisnik = KorisnikUtil.createKorisnikInstanceFromKorisnikFormaDTO(korisnikFormaDTO);
			
			if (korisnikFormaDTO.getIsProfessor()) {
				profesor = new Profesor();
				profesor = KorisnikUtil.createProfesorInstanceFromKorisnikFormaDTO(korisnikFormaDTO);
			}
			
		} else {
			if (korisnikFormaDTO.getIsProfessor()) {
				profesor = new Profesor();
				profesor = KorisnikUtil.createNewProfesorInstanceFromKorisnikFormaDTO(korisnikFormaDTO);
			} else {
				korisnik = KorisnikUtil.createNewKorisnikInstanceFromKorisnikFormaDTO(korisnikFormaDTO);
			}
		}
		
		if (profesor != null) {
			profesorRepository.saveAndFlush(profesor);
		} else {
			korisnikRepository.saveAndFlush(korisnik);
		}
	}

}
