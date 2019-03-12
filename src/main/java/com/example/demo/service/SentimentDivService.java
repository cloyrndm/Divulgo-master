package com.example.demo.service;

import com.example.demo.entity.SentimentDiv;
import com.example.demo.repository.SentimentDivRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Katrina on 3/12/2019.
 */
@Service
public class SentimentDivService {

    @Autowired
    SentimentDivRepository sentimentdivRepository;

    public List<SentimentDiv> findBySynsetTerms (String synset_terms) {

        return sentimentdivRepository.findBySynsetTermsIgnoreCaseContaining(synset_terms);
    }

//    public void save(SentimentDiv synset_terms){
//
//        sentimentdivRepository.save(synset_terms);
//    }
}
