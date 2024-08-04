package com.ticket.platform.model;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Note {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Il titolo non può essere Null")
	@Size(max = 150, message = "Il titolo può avere massimo 150 caratteri")
	@Column(name = "title_note",nullable = false)
	private String titleNote;
	
	@NotBlank(message = "La descrizione non può essere Null")
	@Lob
	@Column(name = "description_note", nullable = false)
	private String descriptionNote;
	

	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	@Column(name = "creation_date")
	private LocalDateTime creationDate;
	
	@ManyToOne
	@JoinColumn(name = "ticket_id")
	private Ticket ticket;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitleNote() {
		return titleNote;
	}

	public void setTitleNote(String titleNote) {
		this.titleNote = titleNote;
	}

	public String getDescriptionNote() {
		return descriptionNote;
	}

	public void setDescriptionNote(String descriptionNote) {
		this.descriptionNote = descriptionNote;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
