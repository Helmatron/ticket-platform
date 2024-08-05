package com.ticket.platform.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ticket.platform.model.Ticket;
import com.ticket.platform.model.User;
import com.ticket.platform.repository.TicketRepository;
import com.ticket.platform.repository.UserRepository;

import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/ticket")
public class TicketController {

	@Autowired
	private TicketRepository ticketRepository;
	@Autowired
	private UserRepository userRepository;

	// DETTAGLI TICKET
	@GetMapping("/dettagli_ticket/{id}")
	public String findPizzaById(@PathVariable("id") Long id, Model model) {
		Ticket ticket = ticketRepository.getReferenceById(id);
		if (ticket != null) {

			List<Ticket> tickets = ticketRepository.findAll();
			model.addAttribute("list", tickets);

			model.addAttribute("ticket", ticket);
			model.addAttribute("findTicketById", true);
			return "ticket/dettagli_ticket";

		} else {

			return "redirect:/admin/index";
		}
	}

	// RICERCA PER NOME DEI TICKET
	@GetMapping("/search")
	public String findTicketByTitle(@RequestParam(name = "titleTicket", required = false) Optional<String> title,
			Model model) {
		List<Ticket> tickets;

		if (title.isPresent() && StringUtils.isNotBlank(title.get())) {
			tickets = ticketRepository.findByTitleTicketContainingIgnoreCase(title.get());
		} else {
			tickets = ticketRepository.findAll();
		}

		model.addAttribute("list", tickets);

		if (tickets.isEmpty()) {
			model.addAttribute("message", "Nessun ticket trovato con il titolo fornito.");
		}

		return "/admin/index";
	}

	// CREA NUOVO TICKET
	@GetMapping("/nuovo_ticket")
	public String creaTicket(Model model) {

		model.addAttribute("ticket", new Ticket());
		model.addAttribute("users", userRepository.findByRolesName("OPERATOR"));

		return "/ticket/nuovo_ticket";
	}

	@PostMapping("/nuovo_ticket")
	public String store(@Valid @ModelAttribute("ticket") Ticket formTicket,
			@RequestParam(name = "operatorId", required = false) Long operatorId, BindingResult bindingResult,
			Model model) {

		if (bindingResult.hasErrors() || operatorId == null) {
			if (operatorId == null) {
				model.addAttribute("operatorError", "Devi selezionare un operatore.");
			}
			model.addAttribute("users", userRepository.findByRolesName("OPERATOR"));
			return "/ticket/nuovo_ticket";
		}

		Optional<User> selectedOperatorId = userRepository.findById(operatorId);
		if (!selectedOperatorId.isPresent()) {
			bindingResult.rejectValue("user", "error.ticket", "Operatore non trovato.");
			model.addAttribute("users", userRepository.findByRolesName("OPERATOR"));
			return "/ticket/nuovo_ticket";
		}
		User selectedOperator = selectedOperatorId.get();
		formTicket.setUser(selectedOperator);

		ticketRepository.save(formTicket);
		return "redirect:/admin/index";
	}

	// MODIFICA TICKET
	@GetMapping("/edit_ticket/{id}")
	public String editTicket(@PathVariable("id") Long id, Model model) {

		model.addAttribute("ticket", ticketRepository.findById(id).get());
		model.addAttribute("users", userRepository.findByRolesName("OPERATOR"));

		return "/ticket/edit_ticket";
	}

	@PostMapping("/edit_ticket")
	public String updateTicket(@Valid @ModelAttribute("ticket") Ticket formTicket,
			@RequestParam(name = "operatorId", required = false) Long operatorId, BindingResult bindingResult,
			Model model) {

		if (bindingResult.hasErrors() || operatorId == null) {
			if (operatorId == null) {
				model.addAttribute("operatorError", "Devi selezionare un operatore.");
			}
			model.addAttribute("users", userRepository.findByRolesName("OPERATOR"));
			return "ticket/edit_ticket";
		}

		Optional<User> selectedOperator = userRepository.findById(operatorId);
		if (!selectedOperator.isPresent()) {
			bindingResult.rejectValue("user", "error.ticket", "Operatore non trovato.");
			model.addAttribute("users", userRepository.findByRolesName("OPERATOR"));
			return "ticket/edit_ticket";
		}

		formTicket.setUser(selectedOperator.get());
		ticketRepository.save(formTicket);
		return "redirect:/admin/index";
	}

	// DELETE TICKET

	@DeleteMapping("/edit_ticket/{id}")
	@Transactional
	public String deleteTicket(@PathVariable("id") Long id, Model model) {
		Optional<Ticket> optionalTicket = ticketRepository.findById(id);
		if (optionalTicket.isPresent()) {
			Ticket ticket = optionalTicket.get();
			ticketRepository.delete(ticket); // La configurazione CascadeType.ALL si occuperà di eliminare le note
		} else {
			model.addAttribute("error", "Il ticket non esiste.");
			return "redirect:/admin/index";
		}
		return "redirect:/admin/index";
	}

}
