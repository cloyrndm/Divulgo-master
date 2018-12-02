package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Katrina on 7/20/2018.
 */
@Entity
@Table(name="article")
public class Article {
    @Id
    @GeneratedValue
    private Integer artId;
    private String url;
    private String title;
    private String content;
    private String agency;
    private Integer artSize;

    public Article() {
    }

    public Integer getArtSize() {
        return artSize;
    }

    public void setArtSize(Integer artSize) {
        this.artSize = artSize;
    }

//    public Integer getArticleId() {
//        return articleId;
//    }
//
//    public void setArticleId(Integer articleId) {
//        this.articleId = articleId;
//    }


    public Integer getArtId() {
        return artId;
    }

    public void setArtId(Integer artId) {
        this.artId = artId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }
}
