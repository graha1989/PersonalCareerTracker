package com.pct.domain.dto;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.SafeHtml;

import com.pct.domain.Professor;

public class ProfessorDto extends UserDto implements Serializable {

	private static final long serialVersionUID = -1976948443456869445L;

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

	public ProfessorDto() {
	}

	public ProfessorDto(String userName, String password, String email, String name, String surname,
			String fathersName, Date dateOfBirth, String placeOfBirth, String countryOfBirth, String scientificArea,
			String specialScientificArea, Long id) {
		super(userName, password, email, name, surname, id);
		this.fathersName = fathersName;
		this.dateOfBirth = dateOfBirth;
		this.placeOfBirth = placeOfBirth;
		this.countryOfBirth = countryOfBirth;
		this.scientificArea = scientificArea;
		this.specialScientificArea = specialScientificArea;
	}

	public ProfessorDto(Professor p) {
		super(p.getUserName(), p.getPassword(), p.getEmail(), p.getName(), p.getSurname(), p.getId());
		this.fathersName = p.getFathersName();
		this.dateOfBirth = p.getDateOfBirth();
		this.placeOfBirth = p.getPlaceOfBirth();
		this.countryOfBirth = p.getCountryOfBirth();
		this.scientificArea = p.getScientificArea();
		this.specialScientificArea = p.getSpecialScientificArea();
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
