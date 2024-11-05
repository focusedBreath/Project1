package com.revature.controllers;

import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.services.ReimbursementService;
import com.revature.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    //create a UserService object to access its methods
    private UserService userService;

    //create a ReimbursementService object also
    private ReimbursementService reimbursementService;

    //Autowire the UserService with constructor injection
    @Autowired
    public UserController(UserService userService, ReimbursementService rmbService) {
        this.userService = userService;
        this.reimbursementService = rmbService;
    }

    //A method to create a new User
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User newUser){

        //create a User to return and register
        User u = userService.register(newUser);

        //Send back a responseEntity with the user and a 201 status code
        return ResponseEntity.status(201).body(u);
    }

    //A method to get all of a User's reimbursements
    @GetMapping("/{userId}/reimbursements")
    public ResponseEntity<List<Reimbursement>> getAllReimbursements(@PathVariable int userId){

        //Create a list to return
        List<Reimbursement> reimbursements = reimbursementService.getReimbursementsByUserId(userId);

        //Return the reimbursements with a 200
        return ResponseEntity.ok().body(reimbursements);
    }

    //A method to get all of a Users pending Reimbursements
    @GetMapping("/{userId}/reimbursements/pending")
    public ResponseEntity<List<Reimbursement>> getPendingReimbursements(@PathVariable int userId){

        //List of reimbursements
        List<Reimbursement> pendingReimbursements =
                reimbursementService.getPendingReimbursementsByUserId(userId);

        //return the reimbursements with a 200
        return ResponseEntity.ok().body(pendingReimbursements);
    }

    //A method to get all Users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){

        //List of users
        List<User> users = userService.getAllUsers();

        //return users with a 200
        return ResponseEntity.ok().body(users);
    }

    //A method to delete a User
    @DeleteMapping("/{userId}")
    public ResponseEntity<User> deleteUser(@PathVariable int userId){

        //Create user object we'll be deleting from the DB
        User deletedUser = userService.removeUser(userId);

        //Return 202 and deleted user
        return ResponseEntity.status(202).body(deletedUser);
    }

    //A method to update a Users role to manager
    @PatchMapping("/{userId}/promotion")
    public ResponseEntity<User> updateUserRole(@PathVariable int userId){

        //create a User
        User u = userService.elevateRole(userId);

        //return 202 and promoted user
        return ResponseEntity.status(202).body(u);
    }

    //An exception handler
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException e){

        return ResponseEntity.status(400).body(e.getMessage());
    }

}
