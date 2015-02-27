package com.pct.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Korisnik(User) entity bean.
 * 
 * @author a.grahovac
 *
 */
@Entity
@Table(name = "profesor")
public class Korisnik extends AbstractEntity {
	
	private static final long serialVersionUID = 753163571345516344L;

	@Column(name = "userName", length = 50)
	private String userName;
	
	@Column(name = "password", length = 50)
	private String password;
	
	@Column(name = "email", length = 50)
	private String email;
	
	@ManyToOne
	@JoinColumn(name = "ulogaId")
	private Uloga uloga;
    
	public Korisnik() {
		super();
	}

	public Korisnik(String userName, String password, String email, Uloga uloga) {
		super();
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.uloga = uloga;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
