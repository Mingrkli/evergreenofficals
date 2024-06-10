package com.springlearning.newticket2.service;

import com.springlearning.newticket2.model.Ticket;
import com.springlearning.newticket2.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ming, Toby, Lillian, Leah
 * @version 1.0
 * Service for Tickets in Spring Boot,
 * includes the business logic
 */
@Service
public class TicketService {
    // Repository
    @Autowired
    private TicketRepository ticketRepository;

    // explain more in javadoc @return :D

    /**
     * Adds a Ticket
     *
     * @param ticket information data being added
     * @return the added ticket
     */
    public Ticket addTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    /**
     * Gets the list of Tickets
     *
     * @return all tickets in the database
     */
    // change to findAllTickets
    public List<Ticket> getTickets() {
        return (List<Ticket>) ticketRepository.findAll();
    }

    /**
     * Get the ticket by id
     *
     * @param id ticket id
     * @return the id of the ticket
     */
    public Ticket getTicketById(long id) {
        return ticketRepository.findById(id).get();
    }

    /**
     * Deletes the ticket by id
     * @param id ticket id
     */
    public void deleteTicket(long id) { ticketRepository.deleteById(id); }
}
