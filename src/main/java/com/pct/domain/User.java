package com.pct.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends AbstractEntity {

	private static final long serialVersionUID = 7998705611484219998L;

	@Column(name = "userName", unique = true, length = 50)
	private String userName;

	@Column(name = "password", unique = true,  length = 255)
	private String password;
	
	@Column(name = "salt", length = 50)
	private String salt;

	@Column(name = "email", unique = true, length = 50)
	private String email;

	@Column(name = "name", length = 50)
	private String name;

	@Column(name = "surname", length = 50)
	private String surname;

	@ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER, cascade = {})
	@JoinTable(name = "user_role", uniqueConstraints = { @UniqueConstraint(columnNames = { "userId", "roleId" }) }, joinColumns = @JoinColumn(name = "userId", referencedColumnName = "id"), inverseJoinColumns = { @JoinColumn(name = "roleId", referencedColumnName = "id") })
	private Set<Role> roles;

	public User() {
		this.roles = new HashSet<Role>();
	}

	public User(String userName, String password, String email, String name, String surname, Professor professor) {
		super();
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.name = name;
		this.surname = surname;
		this.roles = new HashSet<Role>();
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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles.clear();

		if (roles != null) {
			this.roles.addAll(roles);
		}
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

}
