package com.ticket.platform.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ticket.platform.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

	public List<Ticket> findByTitleTicketContainingIgnoreCase(String titleTicket);

	public List<Ticket> findByUserId(Long user);

	public List<Ticket> findByCategoryName(String category);

	public List<Ticket> findByWorkProgress(String workProgress);
}
