package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Katrina on 10/14/2018.
 */

@Entity
@Table(name="ngram")
public class Ngram implements Comparable<Ngram> {
    @Id
    @GeneratedValue
    private Integer ngramId;
    private String words;
    private Integer wordCount;
    private Integer idfWcount;

    public Integer getIdfWcount() {
        return idfWcount;
    }

    public void setIdfWcount(Integer idfWcount) {
        this.idfWcount = idfWcount;
    }

    //    private Integer articleId;

//    public Integer getArticleId() {
//        return articleId;
//    }

//    public void setArticleId(Integer articleId) {
//        this.articleId = articleId;
//    }

    public Integer getNgramId() {
        return ngramId;
    }

    public void setNgramId(Integer ngramId) {
        this.ngramId = ngramId;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public Integer getWordCount() {
        return wordCount;
    }

    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }

    public Ngram (){

    }

    @Override
    public int compareTo(Ngram ngram) {
        return ngram.getWords().compareTo(ngram.getWords());
    }
}

