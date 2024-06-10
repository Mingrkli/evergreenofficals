package com.springlearning.newticket2.repository;

import com.springlearning.newticket2.model.Ticket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ming, Toby, Lillian, Leah
 * @version 1.0
 * Extends basic CRUD for Tickets
 */
@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {

}
