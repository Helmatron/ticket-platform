package com.ticket.platform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.ticket.platform.model.Ticket;
import com.ticket.platform.repository.TicketRepository;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private TicketRepository ticketRepository;
    
    // VISIBILITA ADMIN/INDEX
    @GetMapping("/admin/index")
    public String adminIndex(Model model) {
        List<Ticket> tickets = ticketRepository.findAll();
        model.addAttribute("list", tickets);
        return "admin/index";
    }

    // VISIBILITÀ OPERATOR/INDEX
    @GetMapping("/operator/index")
    public String operatorIndex(Model model) {
        List<Ticket> tickets = ticketRepository.findAll();
        model.addAttribute("list", tickets);
        return "operator/index";
    }
}
