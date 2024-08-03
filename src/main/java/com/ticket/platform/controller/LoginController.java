package com.ticket.platform.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	// REINDIRIZZAMENTO VERSO LOGIN
	@GetMapping("/")
	public String redirectToLogin() {
		return "redirect:/login";
	}

	// REINDIRIZZAMENTO INDEX --> RUOLI
	@GetMapping("/default")
	public String defaultAfterLogin() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		boolean isAdmin = false;
		boolean isOperator = false;

		for (GrantedAuthority authority : auth.getAuthorities()) {
			if (authority.getAuthority().equals("ADMIN")) {
				isAdmin = true;
				break;
			}
		}

		if (!isAdmin) {
			for (GrantedAuthority authority : auth.getAuthorities()) {
				if (authority.getAuthority().equals("OPERATOR")) {
					isOperator = true;
					break;
				}
			}
		}

		if (isAdmin) {
			return "redirect:/admin/index";
		} else if (isOperator) {
			return "redirect:/operator/index";
		} else {
			return "redirect:/login?error";
		}
	}

}
