package com.revature.controllers;

import com.revature.models.DTOs.IncomingReimbursementDTO;
import com.revature.models.Reimbursement;
import com.revature.services.ReimbursementService;
import com.revature.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reimbursements")
public class ReimbursementController {

    //create a ReimbursementService
    private ReimbursementService reimbursementService;

    //Create a UserService (just in case)
    private UserService userService;

    //Autowire with constructor injection
    @Autowired
    public ReimbursementController(ReimbursementService reimbursementService, UserService userService) {
        this.reimbursementService = reimbursementService;
        this.userService = userService;
    }

    //A method to create a reimbursement
    @PostMapping
    public ResponseEntity<Reimbursement> createTicket(@RequestBody IncomingReimbursementDTO rDTO){

        //errors have been handled in service so we can just do this
        Reimbursement reimbursement = reimbursementService.createReimbursementTicket(rDTO);

        //return the reimbursement with a 201
        return ResponseEntity.status(201).body(reimbursement);

    }

    //TODO:A method to update the description of a pending reimbursement
    //@PatchMapping

    //A method to see all reimbursements
    @GetMapping
    public ResponseEntity<List<Reimbursement>> getAllReimbursements(){

        //List of reimbursements
        List<Reimbursement> reimbursements = reimbursementService.getAllReimbursements();

        //return with a 200
        return ResponseEntity.ok().body(reimbursements);
    }

    //A method to see pending reimbursements
    @GetMapping("/pending")
    public ResponseEntity<List<Reimbursement>> getPendingReimbursements(){

        //List of reimbursements
        List<Reimbursement> reimbursements = reimbursementService.getAllPendingReimbursements();

        //return with a 200
        return ResponseEntity.ok().body(reimbursements);
    }

    //TODO:A method to resolve a pending reimbursement

    //An IllegalArgument exception handler
    @ExceptionHandler
    public ResponseEntity<String> handleIllegalArguments(IllegalArgumentException e){
        return ResponseEntity.status(400).body(e.getMessage());
    }
}
