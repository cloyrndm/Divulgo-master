package com.example.demo.repository;


import com.example.demo.entity.ComplaintReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by Cloie Andrea on 02/10/2018.
 */
@Repository
public interface ComplaintReplyRepository extends JpaRepository<ComplaintReply,Long> {
//    void save(ComplaintReply complaintReply);
    List<ComplaintReply> findAll();
    List<ComplaintReply> findByAgency(String agency);
    List<ComplaintReply> findByAgencyAndUserid(String agency, Long id);
//    List<ComplaintReply> findByStatus(String c);
//    List<ComplaifindByAgencyAndTrainStatus
}
