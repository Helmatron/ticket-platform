package com.ticket.platform.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Il titolo non può essere Null")
	@Size(max = 150, message = "Il titolo può avere massimo 150 caratteri")
	@Column(name = "title_ticket", nullable = false)
	private String titleTicket;

	@NotBlank(message = "La descrizione non può essere Null")
	@Lob
	@Column(name = "description_ticket", nullable = false)
	private String descriptionTicket;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "La data di inizio non può essere Null")
	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "La data di fine non può essere Null")
	@Column(name = "end_date", nullable = false)
	private LocalDate endDate;

	@NotBlank(message = "Lo stato non può essere Null")
	@Pattern(regexp = "^(APERTO|IN LAVORAZIONE|CHIUSO)$", message = "Lo stato deve essere uno tra 'Aperto', 'In lavorazione' o 'Chiuso'")
	@Size(max = 20, message = "Il work progess può avere massimo 20 caratteri")
	@Column(name = "work_progress", nullable = false)
	private String workProgress = "APERTO";

	@ManyToOne
	@JoinColumn(name = "category_id")
	@JsonProperty("category")
	private Category category;

	@OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Note> notes = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonProperty("Operator")
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitleTicket() {
		return titleTicket;
	}

	public void setTitleTicket(String titleTicket) {
		this.titleTicket = titleTicket;
	}

	public String getDescriptionTicket() {
		return descriptionTicket;
	}

	public void setDescriptionTicket(String descriptionTicket) {
		this.descriptionTicket = descriptionTicket;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getWorkProgress() {
		return workProgress;
	}

	public void setWorkProgress(String workProgress) {
		this.workProgress = workProgress;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}
