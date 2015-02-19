package com.pct.service;

import com.pct.domain.Korisnik;
import com.pct.domain.Profesor;
import com.pct.domain.dto.KorisnikFormaDTO;

/**
 * User(Korisnik) utility class.
 * 
 * @author a.grahovac
 * 
 */
public class KorisnikUtil {
	// EDIT Korisnik
	public static Korisnik createKorisnikInstanceFromKorisnikFormaDTO(KorisnikFormaDTO korisnikFormaDTO) {

		Korisnik korisnik = createNewKorisnikInstanceFromKorisnikFormaDTO(korisnikFormaDTO);
		korisnik.setId(korisnikFormaDTO.getId());

		return korisnik;
	}

	// CREATE new Korisnik
	public static Korisnik createNewKorisnikInstanceFromKorisnikFormaDTO(KorisnikFormaDTO korisnikFormaDTO) {

		Korisnik korisnik = new Korisnik();

		korisnik.setUserName(korisnikFormaDTO.getUserName());
		korisnik.setPassword(korisnikFormaDTO.getPassword());
		korisnik.setEmail(korisnikFormaDTO.getEmail());

		return korisnik;
	}

	// EDIT Profesor
	public static Profesor createProfesorInstanceFromKorisnikFormaDTO(KorisnikFormaDTO korisnikFormaDTO) {

		Profesor profesor = createNewProfesorInstanceFromKorisnikFormaDTO(korisnikFormaDTO);
		profesor.setId(korisnikFormaDTO.getId());

		return profesor;
	}

	// CREATE new Profesor
	public static Profesor createNewProfesorInstanceFromKorisnikFormaDTO(KorisnikFormaDTO korisnikFormaDTO) {

		Korisnik korisnik = createNewKorisnikInstanceFromKorisnikFormaDTO(korisnikFormaDTO);
		Profesor profesor = new Profesor(korisnik);
		
		profesor.setName(korisnikFormaDTO.getName());
		profesor.setSurname(korisnikFormaDTO.getSurname());
		profesor.setFathersName(korisnikFormaDTO.getFathersName());
		profesor.setDateOfBirth(korisnikFormaDTO.getDateOfBirth());
		profesor.setPlaceOfBirth(korisnikFormaDTO.getPlaceOfBirth());
		profesor.setCountryOfBirth(korisnikFormaDTO.getCountryOfBirth());
		profesor.setScientificArea(korisnikFormaDTO.getScientificArea());
		profesor.setSpecialScientificArea(korisnikFormaDTO.getSpecialScientificArea());

		return profesor;
	}

}
