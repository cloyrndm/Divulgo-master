
//package com.example.demo.repository;
//
//import com.example.demo.entity.Sentiment;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
///**
// * Created by Katrina on 2/4/2019.
// */
//@Repository
//public interface SentimentRepository extends JpaRepository<Sentiment, Integer> {
////    Sentiment findBySentiment(String sentiment);
////    Sentiment findBySentimentId(int id);
//    List<Sentiment> findAll();
//}

package com.example.demo.repository;

import com.example.demo.entity.Sentiment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Katrina on 2/4/2019.
 */
@Repository
public interface SentimentRepository extends JpaRepository<Sentiment, Integer> {
    Sentiment findBySentiment(String sentiment);
    Sentiment findBySentimentId(int id);
    Sentiment findBySentimentAndPosTagger(String sentiment, String postagger);
//    List<Sentiment> findAll();
}
