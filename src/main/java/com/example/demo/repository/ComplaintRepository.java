package com.example.demo.repository;

import com.example.demo.entity.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Cloie Andrea on 02/10/2018.
 */
@Repository
public interface ComplaintRepository extends JpaRepository<Complaint,Long> {
    Complaint findByComplaintId(Long id);
    List<Complaint> findByAgency(String type);
    List<Complaint> findByAgencyAndStatus(String agency, String stat);
    List<Complaint> findByStatusAndTrainStatus(String status, String tsat);
    List<Complaint> findByAgencyAndTrainStatus(String a,String tsat);
    List<Complaint> findByStatus(String status);
//    List<Complaint> findByTrainStatus(String s);
//
//    void merge(Complaint complaint);
}