package com.revature.DAOs;

import com.revature.models.Reimbursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReimbursementDAO extends JpaRepository<Reimbursement, Integer> {

    //custom defined method to find all reimbursements by userId
    List<Reimbursement> findAllByUserUserId(int userId);

    List<Reimbursement> findAllByStatus(String status);

    List<Reimbursement> findAllByUserUserIdAndStatus(int userId, String status);
}
