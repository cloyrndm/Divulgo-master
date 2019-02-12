package com.example.demo.service;

import com.example.demo.entity.Tfidf;
import com.example.demo.repository.TfidfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Cloie Andrea on 12/11/2018.
 */
@Service
public class TfidfService {

    @Autowired
    TfidfRepository tfidfRepository;

    public void save(Tfidf tfidf){
        tfidfRepository.save(tfidf);
    }

    public List<Tfidf> findAll(){
        return tfidfRepository.findAll();
    }

    public Tfidf findByNgramId(int id){
        return tfidfRepository.findByNgramId(id);
    }

    public Tfidf findByFreqId(int id){
        return tfidfRepository.findByFreqId(id);
    }

    public Tfidf findByFreqIdAndWord(int id, String a){
        return tfidfRepository.findByFreqIdAndWord(id,a);
    }

    public Tfidf findByAgency(String a){
        return tfidfRepository.findByAgency(a);
    }


    //Kat added this function:
    public Tfidf findByWord(String word){
        return tfidfRepository.findByWord(word);
    }


//    public Tfidf findByFreqId(int id){
//        return tfidfRepository.findByFreqId(id);
//    }

    public Tfidf findByFreqIdAndNgramId(int id1, int id2){
        return tfidfRepository.findByFreqIdAndNgramId(id1,id2);
    }

    public Tfidf findByTfidfId(int id){
       return tfidfRepository.findByTfidfId(id);
    }

    public Tfidf findByWordAndAgency(String w, String a){
        return tfidfRepository.findByWordAndAgency(w,a);
    }
    public Tfidf findByNgramIdAndAgency(int id, String a){
        return tfidfRepository.findByNgramIdAndAgency(id,a);
    }

//    public Tfidf findByTfidfIdA
}
