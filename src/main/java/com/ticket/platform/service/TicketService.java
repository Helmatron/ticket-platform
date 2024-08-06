package com.ticket.platform.service;

import java.util.List;
import com.ticket.platform.model.Ticket;

public interface TicketService {
	
	public List<Ticket> findAll();

	public List<Ticket> findByCategory(String category);

	public List<Ticket> findByWorkProgress(String workProgress);

}
