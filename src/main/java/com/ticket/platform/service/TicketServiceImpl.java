package com.ticket.platform.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ticket.platform.model.Ticket;
import com.ticket.platform.repository.TicketRepository;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	private TicketRepository ticketRepository;

	@Override
	public List<Ticket> findAll() {
		return ticketRepository.findAll();
	}

	@Override
    public List<Ticket> findByCategory(String category) {
        return ticketRepository.findByCategoryName(category);
    }

	@Override
	public List<Ticket> findByWorkProgress(String workProgress) {
		return ticketRepository.findByWorkProgress(workProgress);
	}

}
