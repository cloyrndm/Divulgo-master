package com.example.demo.repository;

import com.example.demo.entity.Sentiment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Katrina on 2/4/2019.
 */
@Repository
public interface SentimentRepository extends JpaRepository<Sentiment, Integer> {

}
