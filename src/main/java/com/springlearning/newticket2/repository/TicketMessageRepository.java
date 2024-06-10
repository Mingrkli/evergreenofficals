package com.springlearning.newticket2.repository;

import com.springlearning.newticket2.model.TicketMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketMessageRepository extends JpaRepository<TicketMessage, Long> {
    /**
     * Find all the tickets by the id
     * @param ticketId the long number
     * @return a list of TicketMessage by the ticketId
     */
    // Basically the select everything by if in the ticket database
    List<TicketMessage> findAllByTicketId(Long ticketId);
}
