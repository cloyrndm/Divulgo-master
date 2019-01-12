package com.example.demo.service.Impl;

import com.example.demo.entity.Complaint;
import com.example.demo.repository.ComplaintRepository;
import com.example.demo.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Cloie Andrea on 02/10/2018.
 */
@Service("complaintService")
 class ComplaintImpl implements ComplaintService {

    @Autowired
    ComplaintRepository complaintRepository;

    @Override
    public void save(Complaint complaint) {
        complaintRepository.save(complaint);
    }

    @Override
    public Complaint findByComplaintId(Long id) {
       return complaintRepository.findByComplaintId(id);
    }

   @Override
   public List<Complaint> findByAgency(String a) {
      return complaintRepository.findByAgency(a);
   }

   @Override
   public List<Complaint> findByTrainStatus(String status) {
      return complaintRepository.findByTrainStatus(status);
   }

/*    @Override
    public void merge(Complaint complaint) {
        complaintRepository.merge(complaint);
    }*/

//    @Autowired
//    public void merge(Complaint complaint){
//       complaintRepository.merge(complaint);
//    }


}
