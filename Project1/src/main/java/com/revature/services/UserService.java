package com.revature.services;

import com.revature.DAOs.UserDAO;
import com.revature.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    //DAO to call CRUD methods
    private UserDAO uDAO;

    //Wire the DAO with constructor injection
    @Autowired
    public UserService(UserDAO uDAO){ this.uDAO = uDAO; }


    //General access methods--------------------------------

    //A method to create a new User with default role "employee"
    public User register(User newUser){

        //Check if username and password are supplied
        if(newUser.getUsername().isBlank() || newUser.getUsername() == null){
            throw new IllegalArgumentException("Username cannot be blank!");
        }
        //Check if username is taken
        else if(uDAO.findByUsername(newUser.getUsername()) != null){
            throw new IllegalArgumentException("Username: " + newUser.getUsername() +
                    " is taken.");
        }
        //return and save the newUser in the DB
        else{
            return uDAO.save(newUser);
        }

    }

    //Some other method of your choice



    //Manager only methods-----------------------------------

    //A method for Managers to see all users
    public List<User> getAllUsers(){

        //TODO:check that current user is admin
        if(true){
            return uDAO.findAll();
        }else {
            throw new IllegalArgumentException("You must be logged in as an administrator " +
                    "to perform this function");
        }
    }

    //A method for Managers to delete a user (should also delete any related reimbursements)
    public User removeUser(int userId){

        //User object for error handling and return
        User deletedUser = uDAO.findByUserId(userId);

        //Check that the userId passed in is valid and a corresponding user exists
        if (userId <= 0){
            throw new IllegalArgumentException("User Id must be greater than 0!");
        }
        else if (uDAO.findByUserId(userId) == null){
            throw new IllegalArgumentException("User with UserId: " + userId + " was not found!");
        }

        //TODO: check that the current user is admin
        else if(false){
            throw new IllegalArgumentException("You must be logged in as an administrator " +
                    "to perform this function!");
        }

        //Delete the user from the DB and return the deleted user
        else {
            uDAO.delete(deletedUser);
            return deletedUser;
        }
    }

    //A method to update an employee's role to manager
    public User elevateRole(int userId){

        //Create a User object for error handling and return
        User updatedUser = uDAO.findByUserId(userId);

        //TODO:Check that current user is admin
        if (false){
            throw new IllegalArgumentException("You must be logged in as an administrator " +
                    "to perform this function!");
        }

        //Check that a user exists in the DB with specified userId
        else if (updatedUser == null) {
            throw new IllegalArgumentException("User with UserId: " + userId +
                    " was not found!");
        }
        //Check that updatedUser is not already admin
        else if (updatedUser.getRole().equalsIgnoreCase("manager")) {
            throw new IllegalArgumentException("That user is already a manager!");
        }

        //update the User object, save it to the DB and return the user
        else {
            updatedUser.setRole("manager");

            return uDAO.save(updatedUser);
        }
    }
}
