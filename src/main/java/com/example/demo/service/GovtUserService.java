package com.example.demo.service;


import com.example.demo.entity.Complaint;
import com.example.demo.entity.GovtUser;
import com.example.demo.repository.ComplaintRepository;
import com.example.demo.repository.GovtUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Cloie Andrea on 01/10/2018.
 */
@Service
public class GovtUserService {
    @Autowired
    GovtUserRepository govtUserRepository;
    @Autowired
    ComplaintRepository complaintRepository;



    public GovtUser findByUsernameAndPassword(String username, String password) {
        GovtUser user = new GovtUser();
        if(govtUserRepository.findByUsernameAndPassword(username,password) == null) {
            System.out.println("INCORRECT USERNAME AND PASSWORD");
            return null;
        }
        else {
//            System.out.println(user.getFirstName());
            return govtUserRepository.findByUsernameAndPassword(username,password);
        }
    }

    public List<Complaint> findAll(){
        return complaintRepository.findAll();
    }


    public List<Complaint> findByAgency(String type) {
        return complaintRepository.findByAgency(type);
    }


    public List<Complaint> findByAgencyAndStatus(String agency, String stat){
        return complaintRepository.findByAgencyAndStatus(agency,stat);
    }

    public Complaint findByComplaintId(Long id){
        return complaintRepository.findByComplaintId(id);
    }

    public List<Complaint> findByStatus(String status) {
        return complaintRepository.findByStatus(status);
    }
}


