package com.springlearning.newticket2.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class TicketMessages {
    @Valid
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int messageId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ticket_id", nullable = false)
    private Ticket ticket;

    @NotNull(message = "Please Describe How We Can Help You")
    @NotBlank(message = "Please Describe How We Can Help You")
    @Size(min=50, max=700, message =
            "Please provide a detailed description of the issue " +
            "we can assist you with. Minimum description must be " +
            "at least 50 characters and should not exceed 1000 " +
            "characters...")
    public TicketMessages() {

    }
}
