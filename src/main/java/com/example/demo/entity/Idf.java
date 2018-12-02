package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Cloie Andrea on 14/11/2018.
 */
@Entity
@Table(name="idf")
public class Idf {
    @Id
    @GeneratedValue
    private Integer idfid;
    private Integer ngramId;
    private Integer freqId;
    private Double idfVal;

    //addd
    private Integer artId;

    public Integer getArtId() {
        return artId;
    }

    public void setArtId(Integer artId) {
        this.artId = artId;
    }


    public Integer getIdfid() {
        return idfid;
    }

    public void setIdfid(Integer idfid) {
        this.idfid = idfid;
    }

    public Integer getNgramId() {
        return ngramId;
    }

    public void setNgramId(Integer ngramId) {
        this.ngramId = ngramId;
    }

    public Integer getFreqId() {
        return freqId;
    }

    public void setFreqId(Integer freqId) {
        this.freqId = freqId;
    }

//    public String getWord() {
//        return word;
//    }
//
//    public void setWord(String word) {
//        this.word = word;
//    }

    public Double getIdfVal() {
        return idfVal;
    }

    public void setIdfVal(Double idfVal) {
        this.idfVal = idfVal;
    }
}
