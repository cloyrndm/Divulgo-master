package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Cloie Andrea on 14/11/2018.
 */
@Entity
@Table(name="tf")
public class Tf {
    @Id
    @GeneratedValue
    private Integer tfid;
    private Integer ngramId;
    private Integer freqId;
    private String agency;
    private String word;
    private Double tfVal;
    private String stat;

    //addd


    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    private Integer artId;

    public Integer getArtId() {
        return artId;
    }

    public void setArtId(Integer artId) {
        this.artId = artId;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public Integer getTfid() {
        return tfid;
    }

    public void setTfid(Integer tfid) {
        this.tfid = tfid;
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

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Double getTfVal() {
        return tfVal;
    }

    public void setTfVal(Double tfVal) {
        this.tfVal = tfVal;
    }
}
