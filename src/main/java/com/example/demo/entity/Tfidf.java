package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Cloie Andrea on 12/11/2018.
 */
@Entity
@Table(name="tfidf")
public class Tfidf {
    @Id
    @GeneratedValue
    private Integer tfidfId;
    private String agency;
    private Integer ngramId;
    private String word;
    private Double tfidfVal;
    private Integer freqId;


    //addd
    private Integer artId;

    public Integer getArtId() {
        return artId;
    }

    public void setArtId(Integer artId) {
        this.artId = artId;
    }


    public Integer getFreqId() {
        return freqId;
    }

    public void setFreqId(Integer freqId) {
        this.freqId = freqId;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public Integer getNgramId() {
        return ngramId;
    }

    public void setNgramId(Integer ngramId) {
        this.ngramId = ngramId;
    }

    public Integer getTfidfId() {
        return tfidfId;
    }

    public void setTfidfId(Integer tfidfId) {
        this.tfidfId = tfidfId;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Double getTfidfVal() {
        return tfidfVal;
    }

    public void setTfidfVal(Double tfidfVal) {
        this.tfidfVal = tfidfVal;
    }
}
