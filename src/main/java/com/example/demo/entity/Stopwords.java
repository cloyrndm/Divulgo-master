package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Katrina on 1/11/2019.
 */
@Entity
@Table(name="stopwords")
public class Stopwords {
    @Id
    @GeneratedValue
    private Integer stopwordsId;
    private String stopwords;

    public Stopwords() {
    }

    public Integer getStopwordsId() {
        return stopwordsId;
    }

    public void setStopwordsId(Integer stopwordsId) {
        this.stopwordsId = stopwordsId;
    }

    public String getStopwords() {
        return stopwords;
    }

    public void setStopwords(String stopwords) {
        this.stopwords = stopwords;
    }
}

