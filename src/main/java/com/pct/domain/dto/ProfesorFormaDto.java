package com.pct.domain.dto;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import com.pct.domain.Profesor;
import com.pct.domain.Uloga;

public class ProfesorFormaDto implements Serializable {

	private static final long serialVersionUID = -1976948443456869445L;

	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String userName;

	@NotEmpty
	@Length(min = 8, max = 50)
	@SafeHtml
	private String password;

	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String email;

	private Uloga uloga;
	
	@Length(max = 50)
	@SafeHtml
	private String name;

	@Length(max = 50)
	@SafeHtml
	private String surname;

	@Length(max = 50)
	@SafeHtml
	private String fathersName;

	private Date dateOfBirth;

	@Length(max = 50)
	@SafeHtml
	private String placeOfBirth;

	@Length(max = 50)
	@SafeHtml
	private String countryOfBirth;

	@Length(max = 50)
	@SafeHtml
	private String scientificArea;

	@Length(max = 50)
	@SafeHtml
	private String specialScientificArea;
	
	protected Long id;
	
	public ProfesorFormaDto() {
		super();
	}

	public ProfesorFormaDto(String userName, String password, String email, Uloga uloga,
			String name, String surname, String fathersName, Date dateOfBirth, String placeOfBirth,
			String countryOfBirth, String scientificArea, String specialScientificArea, Long id) {
		super();
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.uloga = uloga;
		this.name = name;
		this.surname = surname;
		this.fathersName = fathersName;
		this.dateOfBirth = dateOfBirth;
		this.placeOfBirth = placeOfBirth;
		this.countryOfBirth = countryOfBirth;
		this.scientificArea = scientificArea;
		this.specialScientificArea = specialScientificArea;
		this.id = id;
	}
	
	public ProfesorFormaDto(Profesor p) {
		super();
		this.userName = p.getUserName();
		this.password = p.getPassword();
		this.email = p.getEmail();
		this.uloga = p.getUloga();
		this.name = p.getName();
		this.surname = p.getSurname();
		this.fathersName = p.getFathersName();
		this.dateOfBirth = p.getDateOfBirth();
		this.placeOfBirth = p.getPlaceOfBirth();
		this.countryOfBirth = p.getCountryOfBirth();
		this.scientificArea = p.getScientificArea();
		this.specialScientificArea = p.getSpecialScientificArea();
		this.id = p.getId();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Uloga getUloga() {
		return uloga;
	}

	public void setUloga(Uloga uloga) {
		this.uloga = uloga;
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
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


}
