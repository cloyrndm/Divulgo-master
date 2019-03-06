package com.example.demo.service;


import com.example.demo.entity.ComplaintReply;
import com.example.demo.repository.ComplaintReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Cloie Andrea on 02/10/2018.
 */
@Service
public class ComplaintReplyService {
    @Autowired
    ComplaintReplyRepository complaintReplyRepository;

    public void save(ComplaintReply complaintReply) {
        complaintReplyRepository.save(complaintReply);
    }

    public List<ComplaintReply> findAll() {
        return complaintReplyRepository.findAll();
    }

    public List<ComplaintReply> findByAgency(String agency) {
        return complaintReplyRepository.findByAgency(agency);
    }

    public List<ComplaintReply> findByAgencyAndUserid(String agency, Long id) {
        return complaintReplyRepository.findByAgencyAndUserid(agency,id);
    }

//    public List<ComplaintReply> findByStatus(String c){
//        return complaintReplyRepository.findByStatus(c);
//    }
}
