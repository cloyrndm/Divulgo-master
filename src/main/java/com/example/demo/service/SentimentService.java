package com.example.demo.service;

import com.example.demo.entity.Sentiment;
import com.example.demo.repository.SentimentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Katrina on 2/4/2019.
 */
@Service
public class SentimentService {

    @Autowired
    SentimentRepository sentimentRepository;


    public void save(Sentiment sentiment){

        sentimentRepository.save(sentiment);
    }


    public List<Sentiment> findAll(){

        return sentimentRepository.findAll();
    }

    public Sentiment findBySentiment(String sentiment) {
        return sentimentRepository.findBySentiment(sentiment);
    }

    public Sentiment findBySentimentId(int id) {
        return sentimentRepository.findBySentimentId(id);
    }
}
