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
    private Double tfidfVal;
    private Integer freqId;
    private Double tfVal;
    private Double idfVal;
    private Integer artId;
    private Integer stat;
    private String word;
//    private Integer artId;


    //addd


    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Integer getStat() {
        return stat;
    }

    public void setStat(Integer stat) {
        this.stat = stat;
    }

    public Double getTfVal() {
        return tfVal;
    }

    public void setTfVal(Double tfVal) {
        this.tfVal = tfVal;
    }

    public Double getIdfVal() {
        return idfVal;
    }

    public void setIdfVal(Double idfVal) {
        this.idfVal = idfVal;
    }



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

    public Double getTfidfVal() {
        return tfidfVal;
    }

    public void setTfidfVal(Double tfidfVal) {
        this.tfidfVal = tfidfVal;
    }
}
