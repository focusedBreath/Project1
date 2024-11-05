package com.revature.services;

import com.revature.DAOs.ReimbursementDAO;
import com.revature.DAOs.UserDAO;
import com.revature.models.DTOs.IncomingReimbursementDTO;
import com.revature.models.Reimbursement;
import com.revature.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReimbursementService {

    //DAOs to call CRUD methods
    private ReimbursementDAO rDAO;

    private UserDAO uDAO;

    //Wire the DAOs with constructor injection
    @Autowired
    public ReimbursementService(ReimbursementDAO rDAO, UserDAO uDAO){
        this.rDAO = rDAO;
        this.uDAO = uDAO;
    }


    //General access methods-------------------------------------

    //A method to create a new Reimbursement
    public Reimbursement createReimbursementTicket(IncomingReimbursementDTO rDTO){

        //create a new reimbursement to fill in with data from rDTO
        Reimbursement newReimbursement = new Reimbursement(
                0,
                rDTO.getDescription(),
                rDTO.getAmount(),
                rDTO.getStatus(),
                null);

        //check to make sure userId from rDTO is valid and a user with that Id exists
        if(rDTO.getUserId() <= 0){
            throw new IllegalArgumentException("UserId must be greater than 0!");
        }
        //Check to make sure user exists
        else if(uDAO.findByUserId(rDTO.getUserId()) == null){
            throw new IllegalArgumentException("User with UserId: " + rDTO.getUserId() +
                    " was not found!");
        } //if it makes it past this the userId from rDTO is valid.
        // Add the corresponding user to newReimbursement!
        else {
            newReimbursement.setUser(uDAO.findByUserId(rDTO.getUserId()));
        }

        //finally return and save the newReimbursement to the DB
        return rDAO.save(newReimbursement);

    }

    //A method to find reimbursement tickets by userId
    public List<Reimbursement> getReimbursementsByUserId(int userId){

        //Check to make sure userId is greater than 0
        if(userId <= 0){
            throw new IllegalArgumentException("UserId must be greater than 0!");
        }
        //Check to make sure user exists
        else if(uDAO.findByUserId(userId) == null){
            throw new IllegalArgumentException("User with UserId: " + userId + " was not found!");
        }
        //return a list of reimbursements that belong to the userId
        else {
            return rDAO.findAllByUserUserId(userId);
        }
    }

    //A method to find only pending reimbursements by userId
    public List<Reimbursement> getPendingReimbursementsByUserId(int userId) {

        //Check to make sure userId is greater than 0
        if(userId <= 0){
            throw new IllegalArgumentException("UserId must be greater than 0!");
        }
        //Check to make sure user exists
        else if(uDAO.findByUserId(userId) == null){
            throw new IllegalArgumentException("User with UserId: " + userId + " was not found!");
        }
        //return a list of reimbursements that belong to the userId
        else {
            return rDAO.findAllByUserUserIdAndStatus(userId, "Pending");
        }
    }

    //TODO: A method to update the description of a pending reimbursement
    /*public Reimbursement updateReimbursementDesc(int rmbId){

        //Create a reimbursement object for error handling and return
        Optional<Reimbursement> updatedRmb = rDAO.findById(rmbId);

        //Check that current user is admin or updatedRmb belongs to them

        //Check that the reimbursement exists

        //Check that status is pending

        //TODO: finish this method
    }*/


    //Manager only methods--------------------------------------

    //A method to see all reimbursements
    public List<Reimbursement> getAllReimbursements(){

        //TODO:check that current user is admin
        if(false){
            throw new IllegalArgumentException("You must be logged in as an administrator " +
                    "to perform this function");
        }else {
            return rDAO.findAll();
        }
    }

    //A method to see all pending reimbursements
    public List<Reimbursement> getAllPendingReimbursements(){

        //TODO: check that the current user is admin
        if (false){
            throw new IllegalArgumentException("You must be logged in as an administrator " +
                    "to perform this function");
        }
        else {
            return rDAO.findAllByStatus("Pending");
        }
    }

    //A method to resolve a reimbursement
    public Reimbursement resolveTicket(int rmbId){

        //Create Reimbursement object for error handling and return
        Optional<Reimbursement> updatedRmb = rDAO.findById(rmbId);


        //TODO:Check that the current user is admin
        if (false){
            throw new IllegalArgumentException("You must be logged in as an administrator " +
                    "to perform this function");
        }
        //Check that reimbursement exists
        else if (updatedRmb.isEmpty()){
            throw new IllegalArgumentException("No reimbursement found with Id " + rmbId);
        }
        //Check that reimbursement is not already resolved
        else if(updatedRmb.get().getStatus().equalsIgnoreCase("pending")){
            throw new IllegalArgumentException("Reimbursement with Id " + rmbId +
                    " is already resolved!");
        }
        //Update the Reimbursement object, save to DB and return to the user
        else {
            updatedRmb.get().setStatus("Pending");
            return rDAO.save(updatedRmb.get());
        }
    }
}
