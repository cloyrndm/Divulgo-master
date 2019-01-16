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

    @Autowired
    TfService tfService;

    @Autowired
    IdfService idfService;


    public void TermFrequency(){

        List<Frequency> freq = frequencyService.findAll();
        List<Integer> artid = new ArrayList<Integer>();
        List<Integer> frid = new ArrayList<Integer>();
        List<Integer> nid = new ArrayList<>();

        for (int i = 0; i < freq.size(); i++) {
            artid.add(frequencyService.findAll().get(i).getArtId());
            frid.add(frequencyService.findAll().get(i).getFreqId());
            nid.add(frequencyService.findAll().get(i).getNgramId());
        }

        System.out.println("asd"+artid.size());

        Map<Integer, String> artIdContent = new HashMap<>();
        List<Article> article = articleService.findAll();
        for (int i = 0; i < article.size(); i++) {
            artIdContent.put(articleService.findAll().get(i).getArtId(),articleService.findAll().get(i).getContent());
        }
//      for checking purposes only
        System.out.println("-----------------------------");
        for (Map.Entry<Integer, String> artic : artIdContent.entrySet()) {
            System.out.println(artic.getKey()+artic.getValue());
        }

        Map<Integer, Integer> artIdAndArtSize = new HashMap<>();
        for (Map.Entry<Integer, String> artic : artIdContent.entrySet()) {
            StringTokenizer st = new StringTokenizer(String.valueOf(artic.getValue()));
            Integer count = st.countTokens();
            artIdAndArtSize.put(artic.getKey(),count);

        }
        //checking purposes only

        System.out.println("---------Article id and Count---------");
        for (Map.Entry<Integer, Integer> artic : artIdAndArtSize.entrySet()) {
            System.out.println(artic.getKey() + "\t\t" + artic.getValue());
            Article article1 = articleService.findByArtId(artic.getKey());
            article1.setArtSize(artic.getValue());
            articleService.save(article1);
        }

        System.out.println("---------------------------------------");
        //checking purposes only
        Map<Integer, Double> tfval =  new HashMap<>();

        System.out.println("freq"+freq.size());

        List<Tf> tf1 = tfService.findAll();

        for (int i = 0; i<freq.size(); i++) {
            Article article1 = articleService.findByArtId(artid.get(i));
            Frequency fre1 = frequencyService.findByArtIdAndFreqId(artid.get(i),frid.get(i));
            Ngram ngram = ngramService.findByNgramId(fre1.getNgramId());

            Tf tf5 = tfService.findByFreqId(frid.get(i));

            Double freqq = Double.valueOf(fre1.getFrequency());
            Double arts = Double.valueOf(article1.getArtSize());

            if(tf1.size()==0){
                Tf tf = new Tf();
                tf.setNgramId(fre1.getNgramId());
                tf.setAgency(article1.getAgency());
//                tf.setWord(fre1.getWord());
                tf.setWord(ngram.getWords());
                tf.setFreqId(fre1.getFreqId());
                tf.setArtId(fre1.getArtId());
                tf.setTfVal(freqq / arts);
                tfService.save(tf);
//               System.out.println("done tf");
            }
            else if(tfService.findByFreqId(frid.get(i))!=null){
                tf5.setNgramId(fre1.getNgramId());
                tf5.setAgency(article1.getAgency());
//                tf5.setWord(fre1.getWord());
                tf5.setWord(ngram.getWords());
                tf5.setFreqId(fre1.getFreqId());
                tf5.setArtId(fre1.getArtId());
                tf5.setTfVal(freqq / arts);
                tfService.save(tf1.get(i));
//               System.out.println("done tf");
            }
            else if(tfService.findByFreqId(frid.get(i))==null){
                Tf tf = new Tf();
                tf.setNgramId(fre1.getNgramId());
                tf.setAgency(article1.getAgency());
                //tf.setWord(fre1.getWord());
                tf.setWord(ngram.getWords());
                tf.setFreqId(fre1.getFreqId());
                tf.setArtId(fre1.getArtId());
                tf.setTfVal(freqq / arts);
                tfService.save(tf);
//               System.out.println("done tf");
            }
        }
        System.out.println("DONE TF");
    }

    public void clean(){
        List<Ngram> ngram = ngramService.findAll();
        for (int i = 0; i < ngram.size(); i++) {
            ngram.get(i).setIdfWcount(null);
            ngramService.save(ngram.get(i));
        }
    }
    @RequestMapping(value="/wat")
    public void InverseTermFrequency() {
        List<Frequency> freq = frequencyService.findAll();

//        ArrayList<Integer> ngramid = new ArrayList<Integer>();
//        ArrayList<Integer> aid = new ArrayList<Integer>();
//        ArrayList<Integer> freqqq = new ArrayList<>();

//        for (int i = 0; i < freq.size(); i++) {
//            ngramid.add(frequencyService.findAll().get(i).getNgramId());
//            aid.add(frequencyService.findAll().get(i).getArtId());
//            freqqq.add(frequencyService.findAll().get(i).getFreqId());
//        }
        for(int i = 0; i<freq.size();i++){
            Ngram ngram = ngramService.findByNgramId(freq.get(i).getNgramId());
//            List<Frequency> frequency1 = frequencyService.findAll();
            if(ngram.getNgramId().equals(freq.get(i).getNgramId())&&ngram.getIdfWcount()==null) {
                ngram.setIdfWcount(1);
                ngramService.save(ngram);
            }
            else if(ngram.getNgramId().equals(freq.get(i).getNgramId())&&ngram.getIdfWcount()!=null){
                ngram.setIdfWcount(ngram.getIdfWcount()+1);
                ngramService.save(ngram);
            }

        }

        //TF-IDF
        List<Article> a = articleService.findAll();
        int size = a.size();
        System.out.println("size: "+size);

        List<Idf> idf5 = idfService.findAll();
        for(int i = 0; i<freq.size(); i++){

            Idf idf2 = idfService.findByFreqId(freq.get(i).getFreqId());
            Ngram ngram = ngramService.findByNgramId(freq.get(i).getNgramId());

            if(idf5.size()==0){
                System.out.println(1);
                Idf idf1 = new Idf();
                Double d = (size / Double.valueOf(ngram.getIdfWcount()))+1;
                System.out.println(d);
                Double idff = Math.log(d);
                System.out.println(idff);
                idf1.setIdfVal(idff);
                idf1.setNgramId(ngram.getNgramId());
                idf1.setFreqId(freq.get(i).getFreqId());
                idf1.setArtId(freq.get(i).getArtId());
                idfService.save(idf1);
            }
            else if(idfService.findByFreqId(freq.get(i).getFreqId())!=(null)) {
                System.out.println(2);
                Double d = size / Double.valueOf(ngram.getIdfWcount());
                Double idff = Math.log(d);
                idf2.setIdfVal(idff);
                idf2.setNgramId(ngram.getNgramId());
                idf2.setFreqId(freq.get(i).getFreqId());
                idf2.setArtId(freq.get(i).getArtId());
                idfService.save(idf2);
//                System.out.println("done idf");
            }
            else if(idfService.findByFreqId(freq.get(i).getFreqId())==(null)){
                System.out.println(3);
                Idf idf1 = new Idf();
                Double d = size / Double.valueOf(ngram.getIdfWcount())+1;
                Double idff = Math.log(d);
                idf1.setIdfVal(idff);
                idf1.setNgramId(ngram.getNgramId());
                idf1.setFreqId(freq.get(i).getFreqId());
                idf1.setArtId(freq.get(i).getArtId());
                idfService.save(idf1);
//                System.out.println("done idf");
            }
        }
        System.out.println("done idf");
    }

    @RequestMapping("/watt")
    public void TermFrequencyAndInverseTermFrequency(){

        List<Tf> tf = tfService.findAll();
        System.out.println("size tf"+tf.size());
        List<Idf> idf = idfService.findAll();
        System.out.println("size idf"+idf.size());
        List<Tfidf> tfidf1 = tfidfService.findAll();
        for(int i = 0; i<tf.size(); i++) {

            if(tf.get(i).getStat()==null) {
                Tfidf tfidf2 = tfidfService.findByWordAndAgency(tf.get(i).getWord(), tf.get(i).getAgency());
//            if(tfidf1.size()==0) {
//                Tfidf tfidf = new Tfidf();
//
//                Idf idf1 = idfService.findByFreqIdAndNgramId(tf.get(i).getFreqId(), tf.get(i).getNgramId());
//                Double tfidff = idf1.getIdfVal() * tf.get(i).getTfVal();
//                System.out.println("wow"+tfidff);
//                tfidf.setNgramId(tf.get(i).getNgramId());
//                tfidf.setWord(tf.get(i).getWord());
//                tfidf.setAgency(tf.get(i).getAgency());
//                tfidf.setTfidfVal(tfidff);
//                tfidf.setFreqId(tf.get(i).getFreqId());
//                tfidf.setArtId(tf.get(i).getArtId());
//                tfidfService.save(tfidf);
//
//                tf.get(i).setStat("1");
//                tfService.save(tf.get(i));
//            }
                if (tfidfService.findByWordAndAgency(tf.get(i).getWord(), tf.get(i).getAgency()) == null || tfidf1.size()==0) {
                    System.out.println("1st");
                    Tfidf tfidf = new Tfidf();

                    Idf idf1 = idfService.findByFreqIdAndNgramId(tf.get(i).getFreqId(), tf.get(i).getNgramId());
                    Double tfidff = idf1.getIdfVal() * tf.get(i).getTfVal();
                    System.out.println("wow"+tfidff);
                    tfidf.setNgramId(tf.get(i).getNgramId());
                    tfidf.setWord(tf.get(i).getWord());
                    tfidf.setAgency(tf.get(i).getAgency());
                    tfidf.setTfidfVal(tfidff);
                    tfidf.setFreqId(tf.get(i).getFreqId());
                    tfidf.setArtId(tf.get(i).getArtId());
                    tfidfService.save(tfidf);

                    tf.get(i).setStat("1");
                    tfService.save(tf.get(i));
                } else if (tfidfService.findByWordAndAgency(tf.get(i).getWord(), tf.get(i).getAgency()) != null) {
                    System.out.println("2nd");
                    Idf idf1 = idfService.findByFreqIdAndNgramId(tf.get(i).getFreqId(), tf.get(i).getNgramId());
                    Double tfidff = idf1.getIdfVal() * tf.get(i).getTfVal();
                    System.out.println("wow"+tfidff);
                    tfidf2.setTfidfVal(tfidff + tfidf2.getTfidfVal());
                    tfidfService.save(tfidf2);
                    tf.get(i).setStat("1");
                    tfService.save(tf.get(i));
                }
            }

        }

        System.out.println("welp done");
    }
    //    @RequestMapping("/tfidf")
    @RequestMapping("/tfidf")
    public String gotoTfidf(HttpSession session, Model model,        ModelMap map, Tfidf tfidf) {

        TermFrequency();
        InverseTermFrequency();
        clean();
        TermFrequencyAndInverseTermFrequency();

        List<Tfidf> tfidf3 = tfidfService.findAll();

        model.addAttribute("tfidf",tfidf3);

        return "index";
    }
}
