package com.project.pojo;
// default package
// Generated Jan 14, 2017 12:09:05 AM by Hibernate Tools 5.2.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * Patient generated by hbm2java
 */
@Entity
@Table(name = "patient", catalog = "minirisdb", uniqueConstraints = @UniqueConstraint(columnNames = "iduser"))
public class Patient implements java.io.Serializable {

	private String idpatient;
	private Users users;
	private Date dateOfBirth;
	private String sex;
	private Set<Study> studies = new HashSet<Study>(0);

	public Patient() {
	}

	public Patient(String idpatient, Users users) {
		this.idpatient = idpatient;
		this.users = users;
	}

	public Patient(String idpatient, Users users, Date dateOfBirth, String sex, Set studies) {
		this.idpatient = idpatient;
		this.users = users;
		this.dateOfBirth = dateOfBirth;
		this.sex = sex;
		this.studies = studies;
	}

	@Id

	@Column(name = "idpatient", unique = true, nullable = false, length = 50)
	public String getIdpatient() {
		return this.idpatient;
	}

	public void setIdpatient(String idpatient) {
		this.idpatient = idpatient;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "iduser", unique = true, nullable = false)
	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "date_of_birth", length = 10)
	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Column(name = "sex", length = 10)
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "patient")
	public Set<Study> getStudies() {
		return this.studies;
	}

	public void setStudies(Set studies) {
		this.studies = studies;
	}

}