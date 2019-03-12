package com.example.demo.repository;

import com.example.demo.entity.SentimentDiv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Katrina on 3/12/2019.
 */
@Repository
public interface SentimentDivRepository extends JpaRepository<SentimentDiv, String> {

//    final String queryCheck = "SELECT * from messages WHERE msgid = ?";
//    final PreparedStatement ps = conn.prepareStatement(queryCheck);
//    ps.setString(1, msgid);
//    final ResultSet resultSet = ps.executeQuery();
//    @Query("SELECT s FROM Sentimentdiv s WHERE s.synset_terms LIKE %:synset_terms%")
//
//    List <SentimentDiv> findBySynsetTerms (String synset_terms);


    List <SentimentDiv> findBySynsetTermsIgnoreCaseContaining (String synset_terms);
}
