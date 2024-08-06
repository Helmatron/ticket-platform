package com.ticket.platform.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;

	@NotBlank(message = "Il nome non può essere Null")
	@Size(max = 100, message = "il nome non può avere più di 100 caratteri")
	@Column(nullable = false)
	@JsonProperty("name")
	private String name;

	@NotBlank(message = "Il cognome non può essere Null")
	@Size(max = 100, message = "il cognome non può avere più di 100 caratteri")
	@Column(nullable = false)
	@JsonProperty("surname")
	private String surname;

	@NotNull(message = "la data di nascita non può essere Null")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "birth_date", nullable = false)
	@JsonIgnore
	private LocalDate birthDate;

	@NotBlank(message = "L'indirizzo di domicilio non può essere Null")
	@Column(nullable = false)
	@JsonIgnore
	private String address;

	@NotBlank(message = "Il numero di telefono non può essere Null")
	@Size(min = 8, max = 16, message = "I numeri di telefono devono avere un numero di caratteri compresi fra 8 e 16")
	@Column(name = "phone_number", nullable = false)
	@JsonIgnore
	private String phoneNumber;

	@NotBlank(message = "L'e-mail non può essere Null")
	@Column(nullable = false)
	@JsonIgnore
	private String mail;

	@NotBlank(message = "la password non può essere Null")
	@Column(nullable = false)
	@JsonIgnore
	private String password;

	@Column(nullable = false)
	@JsonIgnore
	private Boolean workStatus;

	@OneToMany(mappedBy = "user")
	@JsonBackReference
	private List<Ticket> tickets;

	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<Note> notes;

	@ManyToMany(fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<Role> roles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(Boolean workStatus) {
		this.workStatus = workStatus;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public boolean changeWorkStatus() {
		for (Ticket ticket : tickets) {
			if (!ticket.getWorkProgress().equals("CHIUSO")) {
				return false;
			}
		}
		return true;
	}

	public boolean hasOpenTickets() {
		for (Ticket ticket : tickets) {
			if (!ticket.getWorkProgress().equals("CHIUSO")) {
				return true;
			}
		}
		return false;
	}
}
