package com.example.demo.repository;

import com.example.demo.entity.Frequency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Katrina on 10/22/2018.
 */
@Repository
public interface FrequencyRepository extends JpaRepository<Frequency, Integer> {

//    @Query("SELECT Frequency.frequency from Ngram, Frequency where Ngram.words= Frequency.word")
    Frequency findByArtId(int artId);
    List<Frequency> findAll();
    Frequency findByNgramId(int ngramId);
    Frequency findByArtIdAndFreqId(int ai, int fi);
    Frequency findByFreqId(int freqId);

//    Frequency findByNgramIdandFreqId(int ngramId,int freqId);
}
