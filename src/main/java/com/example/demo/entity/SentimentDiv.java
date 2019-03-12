package com.example.demo.entity;

import javax.persistence.*;

/**


 * Created by Katrina on 3/11/2019.

 */
@Entity
@Table(name="sentimentdiv")
public class SentimentDiv {
    @Id
    @GeneratedValue
    @Column(name="ID")
    private String id;
    @Column(name="POS")
    private String pos;

//    @Column(name="dum")
//    private String dum;
    @Column(name="PosScore")
    private Double pos_score;
    @Column(name="NegScore")
    private Double neg_score;
    @Column(name="SynsetTerms")
    private String synset_terms;
    @Column(name="Gloss")
    private String gloss;

    //    @Column(name="dum")
//    private String dum;
    @Column(name="PosScore")
    private Double posScore;
    @Column(name="NegScore")
    private Double negScore;
    @Column(name="SynsetTerms")
    private String synsetTerms;
    @Column(name="Gloss")
    private String gloss;


    public SentimentDiv() {
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getGloss() {
        return gloss;
    }

    public void setGloss(String gloss) {
        this.gloss = gloss;
    }

    public Double getNegScore() {
        return negScore;
    }

    public void setNegScore(Double negScore) {
        this.negScore = negScore;
    }

    public String getSynsetTerms() {
        return synsetTerms;
    }

    public void setSynsetTerms(String synsetTerms) {
        this.synsetTerms = synsetTerms;
    }

    public Double getPosScore() {
        return posScore;
    }

    public void setPosScore(Double posScore) {
        this.posScore = posScore;
    }

}
