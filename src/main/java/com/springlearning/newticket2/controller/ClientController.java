package com.springlearning.newticket2.controller;

import com.springlearning.newticket2.model.Ticket;
import com.springlearning.newticket2.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class ClientController {
    // Repository
    @Autowired
    private TicketService ticketService;

    /**
     * Adds the ticket
     * @param ticket
     * @return
     */
    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*")
    @PostMapping("/add")
    public String addTicket(@Valid @RequestBody Ticket ticket,
                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/create";
        }

//        if (bindingResult.hasErrors()) {
//            return ResponseEntity.badRequest().body(
//                    bindingResult.getAllErrors().stream().map(
//                            ObjectError::getDefaultMessage).collect(
//                                    Collectors.joining())
//            );
//        }
        ticketService.addTicket(ticket);
        return "Ticket added";
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*")
    @RequestMapping("/tickets")
    public List<Ticket> getTickets() {
        return ticketService.getTickets();
    }

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
