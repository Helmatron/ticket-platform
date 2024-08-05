package com.ticket.platform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ticket.platform.model.Ticket;
import com.ticket.platform.model.User;
import com.ticket.platform.repository.CategoryRepository;
import com.ticket.platform.repository.TicketRepository;
import com.ticket.platform.repository.UserRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	// VISIBILITA ADMIN/INDEX
	@GetMapping("/admin/index")
	public String adminIndex(Model model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()
				|| authentication.getPrincipal().equals("anonymousUser")) {
			return "redirect:/login";
		}
		model.addAttribute("categories", categoryRepository.findAll());
		List<Ticket> tickets = ticketRepository.findAll();
		model.addAttribute("list", tickets);
		return "admin/index";
	}

	// VISIBILITÀ OPERATOR/INDEX
	@GetMapping("/operator/index")
	public String operatorIndex(Model model, Principal principal) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()
				|| authentication.getPrincipal().equals("anonymousUser")) {
			return "redirect:/login";
		}

		Optional<User> userOpt = userRepository.findByMail(principal.getName());
		if (userOpt.isPresent()) {
			Long id = userOpt.get().getId();
			model.addAttribute("operatorId", id);
			List<Ticket> tickets = ticketRepository.findAll();
			model.addAttribute("list", tickets);
			return "redirect:/operator/dettagli_operatore/" + id;
		} else {
			return "redirect:/error";
		}
	}
}
