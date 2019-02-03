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
    private Integer rating;

    public Sentiment() {
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

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
