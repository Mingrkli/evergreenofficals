package com.springlearning.newticket2.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ming, Toby, Lillian, Leah
 * @version 1.0
 * Entity class for creating user tickets
 * This Class uses Lombok to reduce code
 * for constructors, accessors and mutators
 *
 */
@Entity
// We are putting the details in the database table named ticket_list
@Table(name = "ticket_list")
@AllArgsConstructor // Creates Constructor with arguments
@NoArgsConstructor // Creates Default Constructors
@Data // Creates Setters, Getters, toString, Equals, and HashCode
@Builder
public class Ticket {
    @Valid

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Ticket Name Required")
    @NotBlank(message = "Ticket Name Required")
    @Size(min=4, max=50, message = "Please provide a descriptive" +
            " name for this ticket between 4 and 50 characters")
    private String ticketName;

    private String created;
    private String lastMessage; // change to date
    private String status;
    
    private String groupID;

    @NotBlank(message = "Please Select a Priority Level")
    private String priority;

    @NotBlank(message = "Please Select a Type For This Ticket")
    private String type;

    // "orphanRemoval = true" means that when the parent is gone the childs are too :(
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    // We will have a list here since we would have many messages
    private List<TicketMessage> ticketMessages = new ArrayList<TicketMessage>();
}