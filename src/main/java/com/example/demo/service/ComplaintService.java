package com.example.demo.service;


import com.example.demo.entity.Complaint;

import java.util.List;

/**
 * Created by Cloie Andrea on 02/10/2018.
 */
public interface ComplaintService {
//    void merge(Complaint complaint);
void save(Complaint complaint);
    Complaint findByComplaintId(Long id);
    List<Complaint> findByAgency(String a);

    List<Complaint> findByTrainStatus(String status);
}
