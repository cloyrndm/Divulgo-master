package com.example.demo.controller;

import com.example.demo.entity.Complaint;
import com.example.demo.entity.Tfidf;
import com.example.demo.repository.ComplaintRepository;
import com.example.demo.repository.TfidfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tartarus.snowball.ext.PorterStemmer;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Cloie Andrea on 16/12/2018.
 */

@Controller
public class ClassifierController {

    @Autowired
    TfidfRepository tfidfRepository;

    @Autowired
    ComplaintRepository complaintRepository;

    @Autowired
    HomeController homeController;

//    @RequestMapping("classifier")
    private void classifier (String user_complaint) throws IOException {

        Complaint complaint = new Complaint();

        String[] words = user_complaint.replaceAll("[^a-zA-Z ]", "").split("\\s+");

        ArrayList<String> wList = homeController.removeStopWords(user_complaint);
        ArrayList<String> stemList = homeController.stemming(wList);

        System.out.println("-----STEM WORDS");
        for (String s : stemList) {
            System.out.print(s + "->");
        }

        List<Tfidf> tfidf6 = tfidfRepository.findAll();
        Double love = 0.0;
        Double lra = 0.0;
        Double lto = 0.0;
        Double sss = 0.0;
        HashMap<String, Double> result = new HashMap<>();
        HashMap<String, Double> entry = new HashMap<>();
        for (int i = 0; i < tfidf6.size(); i++) {
            Tfidf tfidf = tfidfRepository.findByTfidfId(tfidf6.get(i).getTfidfId());

            if (stemList.contains(tfidf.getWord())) {
                if (tfidf.getAgency().equals("LTO")) {
                    lto = lto + tfidf.getTfidfVal();
                    System.out.println(tfidf.getWord() + "<------>" + tfidf.getAgency());
                    System.out.println("LTO value computation" + lto);

                }
                if (tfidf.getAgency().equals("LRA")) {
                    lra = lra + tfidf.getTfidfVal();
                    System.out.println(tfidf.getWord() + "<------>" + tfidf.getAgency());
                    System.out.println("LRA value computation" + lra);
                }
                if (tfidf.getAgency().equals("PAG-IBIG")) {
                    love = love + tfidf.getTfidfVal();
                    System.out.println(tfidf.getWord() + "<------>" + tfidf.getAgency());
                    System.out.println("PAG-IBIG value computation" + love);
                }
                if (tfidf.getAgency().equals("SSS")) {
                    sss = sss + tfidf.getTfidfVal();
                    System.out.println(tfidf.getWord() + "<------>" + tfidf.getAgency());
                    System.out.println("SSS value computation" + sss);
                }

            }
            entry.put("LTO", lto);
            entry.put("LRA", lra);
            entry.put("PAG-IBIG", love);
            entry.put("SSS", sss);
        }
        result = maxVal(entry);

        System.out.println("-----------RESULT-------------");
        for (Map.Entry<String, Double> e : result.entrySet()) {
            complaint.setAgency(e.getKey());
        }
        complaintRepository.save(complaint);
//            redirectAttributes.addFlashAttribute("message",
//                    "You successfully uploaded '" + file.getOriginalFilename() + "'");
        System.out.println("File Path: "+complaint.getFile_path());
        System.out.println("Complaint Id: "+complaint.getComplaint_id());
        System.out.println("Date: "+complaint.getDate());
        System.out.println("Time: "+complaint.getTime());
        System.out.println("Complaint: "+complaint.getUser_complaint());
        System.out.println("User Latitude: "+complaint.getUser_lat());
        System.out.println("User Longitude: "+complaint.getUser_long());
        System.out.println("User Id: "+complaint.getUserId());
        System.out.println("Agency: "+complaint.getAgency());
        System.out.println("---------------------------------------------");
    }

    public HashMap<String, Double> maxVal(HashMap<String, Double> values){
        HashMap<String, Double> max = new HashMap<>();
        Double maxval = 0.0;
        for (Map.Entry<String, Double> entry : values.entrySet()) {
            if(entry.getValue()>maxval){
                maxval = entry.getValue();
            }
        }

        for (Map.Entry<String, Double> entry : values.entrySet())
            if(entry.getValue()==maxval)
                max.put(entry.getKey(),entry.getValue());
        return max;
    }
}
