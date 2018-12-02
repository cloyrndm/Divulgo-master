package com.example.demo.service;

import com.example.demo.entity.Article;
import com.example.demo.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Katrina on 10/7/2018.
 */
@Service
public class ArticleService {

    @Autowired
    ArticleRepository articleRepository;


    public void save(Article article){

        articleRepository.save(article);
    }


    public List<Article> getAll(){

        return articleRepository.findAll();
    }


    public Article findByUrl (String url) {

        return articleRepository.findByUrl(url);
    }



    public Article findByContent (String content) {

        return articleRepository.findByContent(content);
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public Article findByArtId(int id){
        return articleRepository.findByArtId(id);
    }

}
