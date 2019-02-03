package com.example.demo.service.Impl;


import com.example.demo.entity.Complaint;
import com.example.demo.entity.GovtUser;
import com.example.demo.repository.ComplaintRepository;
import com.example.demo.repository.GovtUserRepository;
import com.example.demo.service.GovtUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by Cloie Andrea on 01/10/2018.
 */
@Service("govtUserService")
public class GovtUserImpl implements GovtUserService {
    @Autowired
    GovtUserRepository govtUserRepository;
    @Autowired
    ComplaintRepository complaintRepository;
    @Autowired
    UserRepository userRepository;



    @Override
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
    @Override
    public List<Complaint> findAll(){
    return complaintRepository.findAll();
    }

    @Override
    public List<Complaint> findByAgency(String type) {
        return complaintRepository.findByAgency(type);
    }

    @Override
    public List<Complaint> findByAgencyAndStatus(String agency, String stat){
        return complaintRepository.findByAgencyAndStatus(agency,stat);
    }
    @Override
    public Complaint findByComplaintId(Long id){
        return complaintRepository.findByComplaintId(id);
    }

    @Override
    public List<Complaint> findByStatus(String status) {
        return complaintRepository.findByStatus(status);
    }

    @Override
    public User findByUserId(Long id) {
        return userRepository.findByUserId(id);
    }

//    @Override
//    public void update(Complaint complaint){
//        complaintRepository.update(complaint);
//    }

////
//    @Override
//    public List<Complaint> findByComplaintId(){
//        return complaintRepository.findByComplaintId();
//    }
//    @Override
//    public List<Complaint> findAll() {
////        return complaintRepository.findAll();
//        return complaintRepository.findAll();
//    }
}
