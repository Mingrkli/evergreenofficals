package com.springlearning.newticket2.controller;

import com.springlearning.newticket2.model.Ticket;
import com.springlearning.newticket2.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClientController {
    // Repository
    @Autowired
    private TicketService ticketService;

    @RequestMapping("/")
    public String hello_world() {
        return "Hello World";
    }

    /**
     * Adds the ticket
     * @param ticket
     * @return
     */
    @CrossOrigin(origins = "http://localhost:5174", allowedHeaders = "*")
    @PostMapping("/add")
    public String addTicket(@RequestBody Ticket ticket) {
        ticketService.addTicket(ticket);
        return "Ticket added";
    }

    @CrossOrigin(origins = "http://localhost:5174", allowedHeaders = "*")
    @RequestMapping("/tickets")
    public List<Ticket> getTickets() {
        return ticketService.getTickets();
    }
}
