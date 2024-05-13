package com.springlearning.newticket2.repository;

import com.springlearning.newticket2.model.TicketMessages;
import org.springframework.data.repository.CrudRepository;

public interface TicketMessagesRepo extends CrudRepository<TicketMessages, Long> {
}
