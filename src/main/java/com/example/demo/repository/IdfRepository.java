package com.example.demo.repository;

import com.example.demo.entity.Idf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by Cloie Andrea on 15/11/2018.
 */
@Repository
public interface IdfRepository extends JpaRepository<Idf,Long> {
    List<Idf> findAll();
    Idf findByFreqIdAndNgramId(int id, int nid);
    Idf findByFreqId(int id);
}
