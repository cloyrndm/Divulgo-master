package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Katrina on 10/22/2018.
 */
@Entity
@Table(name="frequency")
public class Frequency {

    @Id
    @GeneratedValue
    private Integer freqId;
    private Integer ngramId;
    private Integer artId;
    private Integer frequency;
    private String word;

    private String stat;


    public Frequency (){

    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public Integer getFreqId() {
        return freqId;
    }

    public void setFreqId(Integer freqId) {
        this.freqId = freqId;
    }

    public Integer getNgramId() {
        return ngramId;
    }

    public void setNgramId(Integer ngramId) {
        this.ngramId = ngramId;
    }

    public Integer getArtId() {
        return artId;
    }

    public void setArtId(Integer artId) {
        this.artId = artId;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
