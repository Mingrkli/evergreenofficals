package com.springlearning.newticket2.controller;

import com.springlearning.newticket2.model.TicketMessage;
import com.springlearning.newticket2.service.TicketMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ming, Toby, Lillian, Leah
 * @version 1.0
 * The Controller with focus for ticketMessageService fetches
 */
@CrossOrigin(origins = "http://localhost:5173/", allowedHeaders = "*")
@RestController
public class TicketMessageController {
    // Repository
    @Autowired
    private TicketMessageService ticketMessageService;

    /**
     * Adds the ticket message to the ticketID which so it connects to the ticketID
     * @param ticketMessage message data
     * @param ticketId the number for the ticket
     * @return a message that the ticket has been added
     */
    @PostMapping("/add/message/{ticketId}")
    public String addTicketMessage(@RequestBody TicketMessage ticketMessage, @PathVariable("ticketId") int ticketId) {
        ticketMessageService.addTicketMessage(ticketMessage, ticketId);
        return "Message added";
    }

    /**
     * Gets all the messages by the ticket id
     * @param ticketId long
     * @return all the ticket messages by that id
     */
    @GetMapping("/ticket/{ticketId}/messages")
    public List<TicketMessage> getTicketMessagesByTicketId(@PathVariable(value = "ticketId") Long ticketId) {
        return ticketMessageService.getTicketMessagesByTicketId(ticketId);
    }
}