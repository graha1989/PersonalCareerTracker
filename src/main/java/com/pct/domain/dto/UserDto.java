package com.pct.domain.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import com.pct.domain.User;

public class UserDto implements Serializable {

	private static final long serialVersionUID = 3394849000813411999L;

	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String userName;

	@NotEmpty
	@Length(min = 8, max = 255)
	@SafeHtml
	private String password;

	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String email;

	@Length(max = 50)
	@SafeHtml
	private String name;

	@Length(max = 50)
	@SafeHtml
	private String surname;

	protected Long id;

	public UserDto() {
	}

	public UserDto(String userName, String password, String email, String name, String surname, Long id) {
		super();
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.name = name;
		this.surname = surname;
		this.id = id;
	}

	public UserDto(User user) {
		this.userName = user.getUserName();
		this.password = user.getPassword();
		this.email = user.getEmail();
		this.name = user.getName();
		this.surname = user.getSurname();
		this.id = user.getId();
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
