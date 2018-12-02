package com.example.demo.repository;

import com.example.demo.entity.Tf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by Cloie Andrea on 14/11/2018.
 */
@Repository
public interface TfRepository extends JpaRepository<Tf,Long> {
    Tf findByNgramId(int id);
    Tf findByNgramIdAndFreqId(int id, int fid);
    List<Tf> findAll();
    Tf findByFreqId(int id);
    Tf findByTfid(int id);
    Tf findByStat(String s);
}
