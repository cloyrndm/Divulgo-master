package com.example.demo.entity;

import javax.persistence.*;

/**
 * Created by Cloie Andrea on 11/03/2019.
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
}
