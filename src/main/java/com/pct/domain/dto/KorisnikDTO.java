package com.pct.domain.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import com.pct.domain.Korisnik;
import com.pct.domain.Uloga;

public class KorisnikDTO implements Serializable {

	private static final long serialVersionUID = -1289494378550306156L;

	@NotEmpty
	@Length(max = 30)
	@SafeHtml
	private String userName;

	@NotEmpty
	@Length(max = 30)
	@SafeHtml
	private String password;

	@NotEmpty
	@Length(max = 30)
	@SafeHtml
	private String email;

	private Uloga uloga;

	private boolean isProfessor;

	protected Long id;

	public KorisnikDTO() {
		super();
	}

	public KorisnikDTO(String userName, String password, String email, Uloga uloga, boolean isProfessor, Long id) {
		super();
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.uloga = uloga;
		this.isProfessor = isProfessor;
		this.id = id;
	}

	public KorisnikDTO(Korisnik korisnik) {
		super();
		this.userName = korisnik.getUserName();
		this.password = korisnik.getPassword();
		this.email = korisnik.getEmail();
		this.uloga = korisnik.getUloga();
		this.id = korisnik.getId();
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

	public Uloga getUloga() {
		return uloga;
	}

	public void setUloga(Uloga uloga) {
		this.uloga = uloga;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isProfessor() {
		return isProfessor;
	}

	public void setProfessor(boolean isProfessor) {
		this.isProfessor = isProfessor;
	}

}
