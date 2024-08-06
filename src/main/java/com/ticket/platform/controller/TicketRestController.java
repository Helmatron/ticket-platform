package com.ticket.platform.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticket.platform.model.Ticket;
import com.ticket.platform.service.TicketService;

@RestController
@CrossOrigin
@RequestMapping("/api/ticket")
public class TicketRestController {

	@Autowired
	private TicketService ticketService;

	// ELENCO TICKET
	@GetMapping
	public ResponseEntity<List<Ticket>> getAllTickets() {
		List<Ticket> tickets = ticketService.findAll();
		return ResponseEntity.ok(tickets);
	}

	// FILTRA TICKET PER CATEGORIA
	@GetMapping("/category/{category}")
	public ResponseEntity<List<Ticket>> getTicketByCategory(@PathVariable String category) {
		List<Ticket> tickets = ticketService.findByCategory(category);
		if (tickets.isEmpty()) {
			return ResponseEntity.noContent().build(); // Errore per lista vuota
		}
		return ResponseEntity.ok(tickets);
	}

	// FILTRA PER STATO DEL TICKET
	@GetMapping("/work_progress/{workProgress}")
	public ResponseEntity<List<Ticket>> getTicketByWorkProgress(@PathVariable String workProgress) {
		List<Ticket> tickets = ticketService.findByWorkProgress(workProgress);
		if (tickets.isEmpty()) {
			return ResponseEntity.noContent().build(); // Errore per lista vuota
		}
		return ResponseEntity.ok(tickets);
	}
}
