package com.springlearning.newticket2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ticket_messages_list")
@AllArgsConstructor // Creates Constructor with arguments
@NoArgsConstructor // Creates Default Constructors
@Data // Creates Setters, Getters, toString, Equals, and HashCode
@Builder
/**
 *
 */
public class TicketMessages {

    @Valid
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int messageId;
    //This shold be Date Object
    private String created;

    private String userName;

    @NotNull(message = "Please Describe How We Can Help You")
    @NotBlank(message = "Please Describe How We Can Help You")
    @Size(min=50, max=700, message =
            "Please provide a detailed description of the issue " +
                    "we can assist you with. Minimum description must be " +
                    "at least 50 characters and should not exceed 1000 " +
                    "characters...")
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ticket_id", nullable = false)
    @JsonIgnore
    private Ticket ticket;
}
