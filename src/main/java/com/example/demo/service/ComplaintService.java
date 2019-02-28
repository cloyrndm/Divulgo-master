package com.example.demo.service;

import com.example.demo.entity.Complaint;
import com.example.demo.repository.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Katrina on 2/3/2019.
 */

@Service
public class ComplaintService {

    @Autowired
    ComplaintRepository complaintRepository;

    public void save(Complaint complaint) {
        complaintRepository.save(complaint);
    }

    public Complaint findByComplaintId(Long id) {
        return complaintRepository.findByComplaintId(id);
    }

    public List<Complaint> findByStatusAndTrainStatus(String status, String tsat){
        return complaintRepository.findByStatusAndTrainStatus("1",null);
    }
}
