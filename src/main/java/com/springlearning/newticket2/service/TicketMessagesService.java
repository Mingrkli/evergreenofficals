package com.springlearning.newticket2.service;

import com.springlearning.newticket2.model.TicketMessages;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface TicketMessagesService {

    public TicketMessages addMessage(TicketMessages message);

    /**
     * Gets the list of Tickets
     * @return
     */
    public List<TicketMessages> getMessages();

}
