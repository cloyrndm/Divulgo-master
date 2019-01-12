package com.example.demo.service;

import com.example.demo.entity.Stopwords;
import com.example.demo.repository.StopwordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Katrina on 1/11/2019.
 */
@Service
public class StopwordsService {


    @Autowired
    StopwordsRepository stopwordsRepository;


    public void save(Stopwords stopwords){

        stopwordsRepository.save(stopwords);
    }


    public List<Stopwords> getAll(){

        return stopwordsRepository.findAll();
    }


    public Stopwords findByStopwords (String stopwords) {

        return stopwordsRepository.findByStopwords(stopwords);
    }

}
