package com.example.demo.service;

import com.example.demo.entity.Ngram;
import com.example.demo.repository.NgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Katrina on 10/14/2018.
 */
@Service
public class NgramService {

    @Autowired
    NgramRepository ngramRepository;

    public void save(Ngram ngram) {

        ngramRepository.save(ngram);
    }

    public Ngram findByWords(String words) {

        return ngramRepository.findByWords(words);
    }

    public List<Ngram> getAll() {

        return ngramRepository.findAll();
    }

    public Ngram findByNgramId(int i) {
        return ngramRepository.findByNgramId(i);
    }


    public List<Ngram> findAll() {
        return ngramRepository.findAll();
    }
}
