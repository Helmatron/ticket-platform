package com.ticket.platform.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.ticket.platform.model.Ticket;
import com.ticket.platform.repository.TicketRepository;

import io.micrometer.common.util.StringUtils;

@Controller
public class TicketController {

	@Autowired
	private TicketRepository ticketRepository;

	// DETTAGLI TICKET
	@GetMapping("/admin/ticket/dettagli_ticket/{id}")
	public String findPizzaById(@PathVariable("id") Long id, Model model) {
		Ticket ticket = ticketRepository.getReferenceById(id);
		if (ticket != null) {
			model.addAttribute("ticket", ticket);
			model.addAttribute("findTicketById", true);
			return "/admin/ticket/dettagli_ticket";
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
	
	//
}
