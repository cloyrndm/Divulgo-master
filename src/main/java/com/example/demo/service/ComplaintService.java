package com.example.demo.service;


import com.example.demo.entity.Complaint;

/**
 * Created by Cloie Andrea on 02/10/2018.
 */
public interface ComplaintService {
//    void merge(Complaint complaint);
void save(Complaint complaint);
    Complaint findByComplaintId(Long id);
}
