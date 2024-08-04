package com.ticket.platform.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ticket.platform.model.Note;
import com.ticket.platform.model.Ticket;
import com.ticket.platform.model.User;
import com.ticket.platform.repository.NoteRepository;
import com.ticket.platform.repository.TicketRepository;
import com.ticket.platform.repository.UserRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/note")
public class NoteController {

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/nuova_nota")
	public String showCreateNoteForm(@RequestParam("ticketId") Long ticketId, Model model) {
		Optional<Ticket> ticketOpt = ticketRepository.findById(ticketId);
		if (ticketOpt.isPresent()) {
			Ticket ticket = ticketOpt.get();
			Note note = new Note();
			note.setTicket(ticket);
			model.addAttribute("note", note);
			return "note/nuova_nota";
		} else {
			model.addAttribute("error", "Ticket non trovato");
			return "error";
		}
	}

	@PostMapping("/nuova_nota")
	public String createNote(@Valid @ModelAttribute("note") Note note, BindingResult bindingResult, Principal principal,
			Model model, @RequestParam("ticketId") Long ticketId) {
		if (bindingResult.hasErrors()) {
			Optional<Ticket> ticketOpt = ticketRepository.findById(ticketId);
			if (ticketOpt.isPresent()) {
				note.setTicket(ticketOpt.get());
			}
			return "note/nuova_nota";
		}

		Optional<Ticket> ticketOpt = ticketRepository.findById(ticketId);
		if (!ticketOpt.isPresent()) {
			model.addAttribute("error", "Ticket non trovato");
			return "error";
		}

		String userEmail = principal.getName();
		Optional<User> userOpt = userRepository.findByMail(userEmail);
		if (userOpt.isPresent()) {
			User user = userOpt.get();
			note.setUser(user);
			note.setTicket(ticketOpt.get());
			note.setCreationDate(LocalDateTime.now());
			noteRepository.save(note);
			return "redirect:/admin/ticket/dettagli_ticket/" + note.getTicket().getId();
		} else {
			model.addAttribute("error", "Utente non trovato");
			return "error";
		}
	}

	// DELETE NOTA
	@GetMapping("/elimina_nota/{id}")
	public String deleteNote(@PathVariable Long id) {
		Optional<Note> noteOpt = noteRepository.findById(id);
		if (noteOpt.isPresent()) {
			Note note = noteOpt.get();
			Long ticketId = note.getTicket().getId();
			noteRepository.delete(note);
			return "redirect:/admin/ticket/dettagli_ticket/" + ticketId;
		} else {
			return "error";
		}
	}
}
