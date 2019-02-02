package com.example.demo.repository;

import com.example.demo.entity.Ngram;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Katrina on 10/14/2018.
 */
public interface NgramRepository extends JpaRepository<Ngram, Integer> {
    //
//    @Query("SELECT word FROM Ngram")
//    List<Ngram> getAllWord();

    //    @Query(value = "SELECT words FROM Ngram")
//    List<Ngram> findAllWords(String words);
//
    Ngram findByWords(String words);

    Ngram findByNgramId(int i);

    List<Ngram> findAll();
}
