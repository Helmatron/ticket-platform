package com.ticket.platform.controller;

import java.security.Principal;
import java.util.List;
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

import com.ticket.platform.model.Ticket;
import com.ticket.platform.model.User;
import com.ticket.platform.repository.TicketRepository;
import com.ticket.platform.repository.UserRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/operator")
public class OperatorController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TicketRepository ticketRepository;

	// VISTA ELENCO OPERATORI
	@GetMapping("/lista_operatori")
	public String listaOperatori(Model model) {
		List<User> operators = userRepository.findByRolesName("OPERATOR");
		model.addAttribute("users", operators);
		return "operator/lista_operatori";
	}

	// VISTA DETTAGLIO OPERATORE
	@GetMapping("/dettagli_operatore/{id}")
	public String showOperatorDetails(@PathVariable("id") Long id, Model model, Principal principal) {
		Optional<User> userOpt = userRepository.findById(id);
		if (userOpt.isPresent()) {
			User user = userOpt.get();
			List<Ticket> tickets = ticketRepository.findByUserId(user.getId());

			// Imposta lo stato dell'operatore su attivo se ha ticket aperti
			boolean hasOpenTickets = tickets.stream().anyMatch(ticket -> !ticket.getWorkProgress().equals("CHIUSO"));
			if (hasOpenTickets && !user.getWorkStatus()) {
				user.setWorkStatus(true);
				userRepository.save(user);
			}

			boolean canChangeWorkStatus = tickets.stream()
					.allMatch(ticket -> ticket.getWorkProgress().equals("CHIUSO"));
			model.addAttribute("user", user);
			model.addAttribute("canChangeWorkStatus", canChangeWorkStatus);
			model.addAttribute("tickets", tickets);
			model.addAttribute("operatorId", id); // per poter gestire il pulsante home dell'operator
			return "operator/dettagli_operatore";
		} else {
			return "redirect:/error";
		}
	}

	// CAMBIO STATUS OPERATORE
	@PostMapping("/cambia_stato/{id}")
	public String changeWorkStatus(@PathVariable("id") Long id, Model model) {
		Optional<User> userOpt = userRepository.findById(id);
		if (userOpt.isPresent()) {
			User user = userOpt.get();
			List<Ticket> tickets = ticketRepository.findByUserId(user.getId());
			boolean canBeInactive = tickets.stream().allMatch(ticket -> ticket.getWorkProgress().equals("CHIUSO"));
			if (canBeInactive) {
				user.setWorkStatus(false); // Assicura che lo stato venga impostato su inattivo
				userRepository.save(user);
				model.addAttribute("canChangeWorkStatus", false); // Disabilita il pulsante
			} else {
				model.addAttribute("errorMessage",
						"Non puoi cambiare lo stato a inattivo finché hai ticket non chiusi.");
				model.addAttribute("canChangeWorkStatus", true); // Mantiene il pulsante abilitato
			}
			model.addAttribute("user", user);
			model.addAttribute("tickets", tickets);
			return "redirect:/operator/dettagli_operatore/" + id;
		} else {
			return "redirect:/error";
		}
	}

	// MODIFICA DATI OPERATORE
	@GetMapping("/edit_operatore/{id}")
	public String editOperator(@PathVariable("id") Long id, Model model, Principal principal) {
		Optional<User> userOpt = userRepository.findById(id);
		if (userOpt.isPresent()) {
			// Verifica che l'utente loggato stia modificando solo i propri dati
			if (!userOpt.get().getMail().equals(principal.getName())) {
				return "redirect:/error";
			}

			model.addAttribute("user", userOpt.get());

			// Aggiungi l'operatorId al modello per far funzionare tasto home
			model.addAttribute("operatorId", userOpt.get().getId());

			return "operator/edit_operatore";
		} else {
			return "redirect:/error";
		}
	}

	@PostMapping("/edit_operatore")
	public String updateOperator(@Valid @ModelAttribute("user") User formUser, BindingResult bindingResult, Model model,
			Principal principal) {
		if (bindingResult.hasErrors()) {
			return "user/edit_user";
		}

		// Assicura che l'user possa modificare solo i suoi dati
		Optional<User> userOpt = userRepository.findByMail(principal.getName());
		if (!userOpt.isPresent() || !userOpt.get().getId().equals(formUser.getId())) {
			return "redirect:/error";
		}

		User user = userOpt.get();
		user.setName(formUser.getName());
		user.setSurname(formUser.getSurname());
		user.setBirthDate(formUser.getBirthDate());
		user.setAddress(formUser.getAddress());
		user.setPhoneNumber(formUser.getPhoneNumber());
		user.setMail(formUser.getMail());
		user.setPassword(formUser.getPassword());

		userRepository.save(user);

		return "redirect:/operator/dettagli_operatore/" + user.getId();
	}
}
