package com.revature.models.DTOs;

//This class will be used to make it easier to take in reimbursement objects on the front end
    //without having to send a user object in JSON
public class IncomingReimbursementDTO {

    //fields
    private String description;

    private int amount;

    private String status;

    private int userId;

    //Boilerplate code----------------

    //constructors

    public IncomingReimbursementDTO() {
    }

    public IncomingReimbursementDTO(String description, int amount, String status, int userId) {
        this.description = description;
        this.amount = amount;
        this.status = status;
        this.userId = userId;
    }

    //getters/setters
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    //toString
    @Override
    public String toString() {
        return "IncomingReimbursementDTO{" +
                "description='" + description + '\'' +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", userId=" + userId +
                '}';
    }
}
