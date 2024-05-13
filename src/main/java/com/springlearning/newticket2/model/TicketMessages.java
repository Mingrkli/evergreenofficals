package com.springlearning.newticket2.model;

import jakarta.persistence.*;

@Entity
public class TicketMessages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int messageId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ticket_id", nullable = false)
    private Ticket ticket;

    public TicketMessages() {

    }
}
