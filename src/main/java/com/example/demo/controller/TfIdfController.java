package com.example.demo.controller;


import com.example.demo.entity.*;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by Cloie Andrea on 13/11/2018.
 */
@SuppressWarnings("Duplicates")
@Controller
public class TfIdfController {

    @Autowired
    NgramService ngramService;

    @Autowired
    ArticleService articleService;

    @Autowired
    FrequencyService frequencyService;

    @Autowired
    TfidfService tfidfService;


    public void TermFrequency() {

        List<Frequency> freq = frequencyService.findAll();
        for (int i = 0; i < freq.size(); i++) {
                if (freq.get(i).getStat() == null) {
                Article article = articleService.findByArtId(freq.get(i).getArtId());
                Ngram ngram1 = ngramService.findByNgramId(freq.get(i).getNgramId());
                Double freqq = Double.valueOf(freq.get(i).getFrequency());
                System.out.println(freq.get(i).getFreqId()+" "+freq.get(i).getNgramId());
                if (tfidfService.findByNgramIdAndAgency(freq.get(i).getNgramId(), article.getAgency()) == null) {
                        Tfidf tfidf1 = new Tfidf();
                        tfidf1.setNgramId(freq.get(i).getNgramId());
                        tfidf1.setAgency(article.getAgency());
                        tfidf1.setWord(ngram1.getWords());
                        tfidf1.setFreqId(freq.get(i).getFreqId());
                        tfidf1.setArtId(freq.get(i).getArtId());
                        tfidf1.setStat(1);
                        Double w = freqq / article.getArtSize();
                        tfidf1.setTfVal(freqq / article.getArtSize());
                        tfidfService.save(tfidf1);
                }
                  else {
                        Tfidf tfidf2 = tfidfService.findByNgramIdAndAgency(freq.get(i).getNgramId(), article.getAgency());
                        Double tfNewVal = freqq / article.getArtSize();
                        Double tfUpdatedVal = tfidf2.getTfVal() + tfNewVal;
                        tfidf2.setTfVal(tfUpdatedVal);
                        tfidf2.setStat(1);
                        tfidfService.save(tfidf2);
                    }
                }

            }
        }


    public void wordcount(){
        List<Frequency> freq = frequencyService.findAll();
        for(int i = 0; i<freq.size();i++){
            if(freq.get(i).getStat()==null) {
                Ngram ngram = ngramService.findByNgramId(freq.get(i).getNgramId());
                if (ngram.getNgramId().equals(freq.get(i).getNgramId()) && ngram.getIdfWcount() == null) {
                    System.out.println("firstttt");
                    ngram.setIdfWcount(1);
                    ngramService.save(ngram);
                    freq.get(i).setStat("1");
                    frequencyService.save(freq.get(i));
                } else if (ngram.getNgramId().equals(freq.get(i).getNgramId()) && ngram.getIdfWcount() != null) {
                    System.out.println("secondddd");
                    ngram.setIdfWcount(ngram.getIdfWcount() + 1);
                    ngramService.save(ngram);

                    freq.get(i).setStat("1");
                    frequencyService.save(freq.get(i));
                }
            }
        }
    }

    public void InverseTermFrequency() {
//        wordcount();

        List<Article> a = articleService.findAll();
        int size = a.size();

        List<Tfidf> tfidf = tfidfService.findAll();
        for(int i = 0; i<tfidf.size(); i++){

            if(tfidf.get(i).getIdfVal()==null && tfidf.get(i).getStat()==null) {
                    Ngram ngram = ngramService.findByNgramId(tfidf.get(i).getNgramId());
                    Double d = (size / Double.valueOf(ngram.getIdfWcount()))+1;
                    Double idff = Math.log(d);
                    tfidf.get(i).setIdfVal(idff);
                    tfidfService.save(tfidf.get(i));
            }
            else if(tfidf.get(i).getStat()!=null && tfidf.get(i).getIdfVal()==null) {
                Ngram ngram = ngramService.findByNgramId(tfidf.get(i).getNgramId());
                Double d = (size / Double.valueOf(ngram.getIdfWcount()))+1;
                Double idff = Math.log(d);
                tfidf.get(i).setIdfVal(idff);
                tfidfService.save(tfidf.get(i));
            }
            else if(tfidf.get(i).getStat()!=null && tfidf.get(i).getIdfVal()!=null){
                Ngram ngram = ngramService.findByNgramId(tfidf.get(i).getNgramId());
                Double d = (size / Double.valueOf(ngram.getIdfWcount()))+1;
                Double newIdff = Math.log(d);
                Double updateIdf = tfidf.get(i).getIdfVal()+newIdff;
                tfidf.get(i).setIdfVal(updateIdf);
                tfidfService.save(tfidf.get(i));
            }
        }
    }


    public void TermFrequencyAndInverseTermFrequency(){

        List<Tfidf> tfidf1 = tfidfService.findAll();
        for(int i = 0; i<tfidf1.size(); i++) {
            if(tfidf1.get(i).getStat()!=null) {
                Tfidf tfidf2 = tfidfService.findByFreqId(tfidf1.get(i).getFreqId());
                Double tfidf = tfidf2.getTfVal() * tfidf2.getIdfVal();
                tfidf2.setTfidfVal(tfidf);
                tfidf2.setStat(null);
                tfidfService.save(tfidf2);
            }
        }
        }

    @RequestMapping("/tfidf")
    public String gotoTfidf(HttpSession session, Model model,ModelMap map, Tfidf tfidf) {
        System.out.println("----------------------starting term frequency process------------------------");
        System.out.println("saving...");
        TermFrequency();
        System.out.println("------------------------end of term frequency process------------------------");
        System.out.println("-------------------------start idf count-------------------------------------");
        System.out.println("saving...");
        wordcount();
        System.out.println("-------------------------end count-------------------------------------------");
        System.out.println("----------------------starting idf frequency process------------------------");
        System.out.println("saving...");
        InverseTermFrequency();
        System.out.println("----------------------end idf process---------------------------------------");
        System.out.println("-------------------start tfidf process--------------------------------------");
        System.out.println("saving...");
        TermFrequencyAndInverseTermFrequency();
        System.out.println("---------------------------------------end----------------------------------");

        List<Tfidf> tfidf3 = tfidfService.findAll();

        model.addAttribute("tfidf",tfidf3);

        return "index";
    }

}
