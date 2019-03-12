
//package com.example.demo.entity;
//
//import javax.persistence.*;
//
///**
// * Created by Katrina on 2/4/2019.
// */
//@Entity
//@Table(name="sentiment")
//public class Sentiment {
//
////    @GeneratedValue
//    @Column(name="# POS")
//    private String pos;
//    @Id
//    @Column(name="ID")
//    private String id;
//    @Column(name="PosScore")
//    private Double pos_score;
//    @Column(name="NegScore")
//    private Double neg_score;
//    @Column(name="SynsetTerms")
//    private String synset_terms;
//    @Column(name="Gloss")
//    private String gloss;
//    public Sentiment() {
//    }
//
//    public String getPos() {
//        return pos;
//    }
//
//    public void setPos(String pos) {
//        this.pos = pos;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public Double getPos_score() {
//        return pos_score;
//    }
//
//    public void setPos_score(Double pos_score) {
//        this.pos_score = pos_score;
//    }
//
//    public Double getNeg_score() {
//        return neg_score;
//    }
//
//    public void setNeg_score(Double neg_score) {
//        this.neg_score = neg_score;
//    }
//
//    public String getSynset_terms() {
//        return synset_terms;
//    }
//
//    public void setSynset_terms(String synset_terms) {
//        this.synset_terms = synset_terms;
//    }
//
//    public String getGloss() {
//        return gloss;
//    }
//
//    public void setGloss(String gloss) {
//        this.gloss = gloss;
//    }
//
//    //    public Integer getSentimentId() {
////        return sentimentId;
////    }
////
////    public void setSentimentId(Integer sentimentId) {
////        this.sentimentId = sentimentId;
////    }
////
////    public String getSentiment() {
////        return sentiment;
////    }
////
////    public void setSentiment(String sentiment) {
////        this.sentiment = sentiment;
////    }
////
////    public Integer getRating() {
////        return rating;
////    }
////
////    public void setRating(Integer rating) {
////        this.rating = rating;
////    }
//}

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

