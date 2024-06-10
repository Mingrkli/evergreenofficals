package com.springlearning.newticket2.controller;

import com.springlearning.newticket2.dto.AddTicketRequest;
import com.springlearning.newticket2.model.Ticket;
import com.springlearning.newticket2.model.TicketMessage;
import com.springlearning.newticket2.service.TicketMessageService;
import com.springlearning.newticket2.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ming, Toby, Lillian, Leah
 * @version 1.0
 * The Controller with focus for ticketService fetches
 */
@CrossOrigin(origins = "http://localhost:5173/", allowedHeaders = "*")
@RestController
public class ClientController {
    // Repository
    @Autowired
    private TicketService ticketService;
    @Autowired
    private TicketMessageService ticketMessageService;

    /**
     * Adds the ticket
     *
     * @param AddTicketRequest DTO which separates the ticket and the message for easier use
     * @param bindingResult errors
     * @return ResponseEntity of error or success HttpStatus with message
     */
    @PostMapping("/add")
    public ResponseEntity addTicket(
            @Valid @RequestBody AddTicketRequest AddTicketRequest,
            BindingResult bindingResult) {

        // Ticket and Message
        Ticket ticket = AddTicketRequest.getTicket();
        TicketMessage message = AddTicketRequest.getMessage();

//        if (AddTicketRequest.getTicket().getName() == null) {
//            return new ResponseEntity<>("You have no name... ", HttpStatus.BAD_REQUEST);
//        }

        if (bindingResult.hasFieldErrors()) {
            String[] errorsArray = new String[bindingResult.getFieldErrors().size()];
            final int[] counter = {0};
            // Map<String, String> errors = new HashMap<>();

            bindingResult.getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();

                errorsArray[counter[0]] = fieldName + " " + errorMessage;

                counter[0]++;

                // errors.put(fieldName, errorMessage);
            });

            return new ResponseEntity<>(errorsArray, HttpStatus.BAD_REQUEST);
        }

        // Checks if the ticket fields has errors
//        if (bindingResult.hasFieldErrors()) {
//            Map<String, String> errors = new HashMap<>();
//
//            bindingResult.getAllErrors().forEach((error) -> {
//                String fieldName = ((FieldError) error).getField();
//                String errorMessage = error.getDefaultMessage();
//                errors.put(fieldName, errorMessage);
//            });
//
//            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//        }

        // Add the ticket and also save the results which we'll use it to add the message to the new ticket is created
        Ticket ticketAddedInfo = ticketService.addTicket(ticket);
        ticketMessageService.addTicketMessage(message, ticketAddedInfo.getId());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

//    public static ResponseEntity testing() {
//        return new ResponseEntity<>("You have no name... ", HttpStatus.BAD_REQUEST);
//    }

    /**
     * Gets all the tickets from the database
     * @return List<Tickets> from ticketService
     */
    @RequestMapping("/tickets")
    public List<Ticket> getTickets() {
        return ticketService.getTickets();
    }

    /**
     * Gets the ticket by id
     * @param id long
     * @return the ticket by the id
     */
    @GetMapping("/ticket/{id}")
    public Ticket getTicketById(@PathVariable long id) {
        return ticketService.getTicketById(id);
    }

//    /**
//     * Handles exception for invalid user input and displays
//     * a Map of errors recieved while trying to process
//     * the request
//     * @param ex Exception class to be resolved
//     * @return Map of errors regarding invalid input
//     */
//    @ResponseStatus (HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public Map<String,String> validationErrors(MethodArgumentNotValidException ex) {
//
//        Map<String, String> errors = new HashMap<>();
//
//        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
//        return errors;
//    }

    /**
     * Deletes the ticket by the id
     * @param id long
     * @return a message saying the ticket was deleted
     */
    @DeleteMapping("/ticket/delete/{id}")
    public String deleteTicket(@PathVariable long id) {
        ticketService.deleteTicket(id);
        return "Ticket deleted";
    }


}

