package com.example.demo.service;

import com.example.demo.entity.Tf;
import com.example.demo.repository.IdfRepository;
import com.example.demo.repository.TfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by Cloie Andrea on 14/11/2018.
 */
@Service
public class TfService {
    @Autowired
    TfRepository tfRepository;

    @Autowired
    IdfRepository idfRepository;

    public void save(Tf tf){
        tfRepository.save(tf);
    }

    public Tf findByNgramIdAndFreqId(int id,int fid){
        return  tfRepository.findByNgramId(id);
    }

//    public Tf findByFreqId(int id){
//        return tfRepository.findByFreqId(id);
//    }

    public Tf findByFreqId(int id){
        return tfRepository.findByFreqId(id);
    }

    public List<Tf> findAll(){
        return tfRepository.findAll();
    }

    public Tf findByTfid(int id){
        return tfRepository.findByTfid(id);
    }

    public Tf findByStat(String s){
        return tfRepository.findByStat(s);
    }

}
