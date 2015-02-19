package com.pct.domain.dto;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.SafeHtml;

import com.pct.domain.Korisnik;
import com.pct.domain.Uloga;

public class ProfesorDTO extends KorisnikDTO {

	private static final long serialVersionUID = -5641097839017292986L;
	
	@Length(max = 30)
	@SafeHtml
	private String name;
	
	@Length(max = 30)
	@SafeHtml
	private String surname;
	
	@Length(max = 30)
	@SafeHtml
	private String fathersName;
	
	@Length(max = 20)
	@SafeHtml
	private Date dateOfBirth;
	
	@Length(max = 30)
	@SafeHtml
	private String placeOfBirth;
	
	@Length(max = 30)
	@SafeHtml
	private String countryOfBirth;
	
	@Length(max = 30)
	@SafeHtml
	private String scientificArea;
	
	@Length(max = 30)
	@SafeHtml
	private String specialScientificArea;

	public ProfesorDTO(String name, String surname, String fathersName,
			Date dateOfBirth, String placeOfBirth, String countryOfBirth,
			String scientificArea, String specialScientificArea) {
		super();
		this.name = name;
		this.surname = surname;
		this.fathersName = fathersName;
		this.dateOfBirth = dateOfBirth;
		this.placeOfBirth = placeOfBirth;
		this.countryOfBirth = countryOfBirth;
		this.scientificArea = scientificArea;
		this.specialScientificArea = specialScientificArea;
	}

	public ProfesorDTO() {
		super();
	}

	public ProfesorDTO(Korisnik korisnik) {
		super(korisnik);
	}

	public ProfesorDTO(String userName, String password, Uloga uloga, boolean isProfessor, Long id) {
		super(userName, password, password, uloga, isProfessor, id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getFathersName() {
		return fathersName;
	}

	public void setFathersName(String fathersName) {
		this.fathersName = fathersName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	public String getCountryOfBirth() {
		return countryOfBirth;
	}

	public void setCountryOfBirth(String countryOfBirth) {
		this.countryOfBirth = countryOfBirth;
	}

	public String getScientificArea() {
		return scientificArea;
	}

	public void setScientificArea(String scientificArea) {
		this.scientificArea = scientificArea;
	}

	public String getSpecialScientificArea() {
		return specialScientificArea;
	}

	public void setSpecialScientificArea(String specialScientificArea) {
		this.specialScientificArea = specialScientificArea;
	}
	
}
