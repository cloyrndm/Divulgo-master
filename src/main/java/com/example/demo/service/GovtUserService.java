package com.example.demo.service;


import com.example.demo.entity.Complaint;
import com.example.demo.entity.GovtUser;

import java.util.List;

/**
 * Created by Cloie Andrea on 01/10/2018.
 */
public interface GovtUserService {
    GovtUser findByUsernameAndPassword(String username, String password);
    List<Complaint> findAll();
    List<Complaint> findByAgency(String type);
    List<Complaint> findByAgencyAndStatus(String agency, String stat);
    Complaint findByComplaintId(Long id);
    List<Complaint> findByStatus(String status);
    User findByUserId(Long id);
//    void update(Complaint complaint);
//    List<Complaint> findByComplaintId();
}


