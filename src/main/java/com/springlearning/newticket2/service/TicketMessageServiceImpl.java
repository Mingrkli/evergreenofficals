package com.springlearning.newticket2.service;

import com.springlearning.newticket2.model.Ticket;
import com.springlearning.newticket2.model.TicketMessages;
import com.springlearning.newticket2.repository.TicketMessagesRepo;
import com.springlearning.newticket2.repository.TicketRepository;

import java.util.List;

public class TicketMessageServiceImpl implements TicketMessagesService{

    private TicketMessagesRepo ticketMessageRepo;


    @Override
    public TicketMessages addMessage(TicketMessages message) {
        return null;
    }

    @Override
    public List<TicketMessages> getMessages() {
        return null;
    }
}
