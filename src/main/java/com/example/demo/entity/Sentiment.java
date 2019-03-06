package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Katrina on 2/4/2019.
 */
@Entity
@Table(name="sentiment")
public class Sentiment {
    @Id
    @GeneratedValue
    private Integer sentimentId;
    private String sentiment;
    private String rating;
    private Double sentimentScore;
    private String posTagger;
    private Double scoring;

    public Sentiment() {
    }

    public Double getScoring() {
        return scoring;
    }

    public void setScoring(Double scoring) {
        this.scoring = scoring;
    }

    public Integer getSentimentId() {
        return sentimentId;
    }

    public void setSentimentId(Integer sentimentId) {
        this.sentimentId = sentimentId;
    }

    public String getSentiment() {
        return sentiment;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Double getSentimentScore() {
        return sentimentScore;
    }

    public void setSentimentScore(Double sentimentScore) {
        this.sentimentScore = sentimentScore;
    }

    public String getPosTagger() {
        return posTagger;
    }

    public void setPosTagger(String posTagger) {
        this.posTagger = posTagger;
    }
}
