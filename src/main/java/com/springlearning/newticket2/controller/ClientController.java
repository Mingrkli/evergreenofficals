package com.springlearning.newticket2.controller;

import com.springlearning.newticket2.model.Ticket;
import com.springlearning.newticket2.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*")
@RestController
public class ClientController {
    // Repository
    @Autowired
    private TicketService ticketService;

    /**
     * Adds the ticket
     *
     * @param ticket
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addTicket(
            @Valid @RequestBody Ticket ticket,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();

            bindingResult.getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
//            model.getAttribute("errors", errors);
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        ticketService.addTicket(ticket);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Gets all the tickets form the database
     * @return
     */
    @RequestMapping("/tickets")
    public List<Ticket> getTickets() {
        return ticketService.getTickets();
    }

//    @GetMapping("/ticket/{id}")
//    public Ticket getTicketById(@PathVariable long id) {
//        return ticketService;
//    }

    /**
     * Handles exception for invalid user input and displays
     * a Map of errors recieved while trying to process
     * the request
     * @param ex Exception class to be resolved
     * @return Map of errors regarding invalid input
     */
    @ResponseStatus (HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> validationErrors(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
