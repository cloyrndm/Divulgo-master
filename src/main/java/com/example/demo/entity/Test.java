package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Cloie Andrea on 16/11/2018.
 */
@Entity
@Table(name="test")
public class Test {

    @Id
    @GeneratedValue
    private Integer testId;
//    private String content;
    private String actualAgency;
    private Integer articleid;
    private String predictedAgency;
    private String resultl;
    private String phase;

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    //    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }


    public Integer getArticleid() {
        return articleid;
    }

    public void setArticleid(Integer articleid) {
        this.articleid = articleid;
    }

    public String getActualAgency() {
        return actualAgency;
    }

    public void setActualAgency(String actualAgency) {
        this.actualAgency = actualAgency;
    }

    public String getPredictedAgency() {
        return predictedAgency;
    }

    public void setPredictedAgency(String predictedAgency) {
        this.predictedAgency = predictedAgency;
    }

    public String getResultl() {
        return resultl;
    }

    public void setResultl(String resultl) {
        this.resultl = resultl;
    }
    //    public String getAgency() {
//        return agency;
//    }
//
//    public void setAgency(String agency) {
//        this.agency = agency;
//    }

    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }

    //    private String content;
//    private String agency;
//    private Integer artSize;
}
