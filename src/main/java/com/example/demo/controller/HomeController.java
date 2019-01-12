package com.example.demo.controller;

import com.example.demo.entity.*;

import com.example.demo.repository.TestRepository;
import com.example.demo.repository.TfidfRepository;
import com.example.demo.service.*;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.tomcat.jni.Error;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tartarus.snowball.ext.PorterStemmer;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Katrina on 9/27/2018.
 */
@Controller
public class HomeController {

    @Autowired
    UsersService usersService;

    @Autowired
    ArticleService articleService;

    @Autowired
    NgramService ngramService;

    @Autowired
    FrequencyService freService;

    @Autowired
    TfidfService tfidfService;

    @Autowired
    TestService testService;

    @Autowired
    ClassifierController classifierController;

    @Autowired
    ComplaintService complaintService;

    @Autowired
    TfIdfController tfIdfController;

    @RequestMapping("/registration")
    public String gotoRegistration(){
        return "register";
    }
    @RequestMapping("/goLogin")
    public String gotoLogin(){
        return "login";
    }
    @RequestMapping("/goTest")
    public String gotoTest(){

        return "test";
    }
//-----retraining happens in admin
    @RequestMapping("/tempretrain")
    public String tempretrain(Model model){
       List<Complaint> complaint = complaintService.findByTrainStatus(null);
        model.addAttribute("data",complaint);
        return "test/retrain";
    }

    @RequestMapping("/retrain")
    public String retrain(HttpServletRequest request,Model model) throws IOException {
        String data = request.getParameter("data");
        String cid = request.getParameter("complaintid");

        Article article = new Article();
        article.setContent(data);
        articleService.save(article);

        Complaint complaint1 = complaintService.findByComplaintId(Long.valueOf(cid));
        complaint1.setTrainStatus("1");
        complaintService.save(complaint1);

        cleanContent(data);
        tfIdfController.TermFrequency();
        tfIdfController.InverseTermFrequency();
        tfIdfController.TermFrequencyAndInverseTermFrequency();
        List<Complaint> complaint = complaintService.findByTrainStatus(null);

//        complaint.set()
        model.addAttribute("data",complaint);
        return "test/retrain";
//        return "retrain";
    }
//----------------------------
    @RequestMapping("/getResult")
    public String result(HttpServletRequest request, Model model){
        String test = request.getParameter("testcontent");
        Article article = articleService.findByContent(test);
        String rAgency = article.getAgency();
        System.out.println(rAgency);
        String[] words = test.replaceAll("[^a-zA-Z ]", "").split("\\s+");
        ArrayList<String> stemList = new ArrayList<>();

        for (String a : words) {
            PorterStemmer stemmer = new PorterStemmer();
            stemmer.setCurrent(a);
            stemmer.stem();
            String steem = stemmer.getCurrent();
            stemList.add(steem);
            System.out.println(steem);

        }
        System.out.println(stemList.size());
        List<Tfidf> tfidf6 = tfidfService.findAll();
        Double love = 0.0;
        Double lra = 0.0;
        Double lto = 0.0;
        Double sss = 0.0;
        HashMap<String, Double> result = new HashMap<>();
        HashMap<String, Double> entry = new HashMap<>();
        for (int i = 0; i < tfidf6.size(); i++) {
            Tfidf tfidf = tfidfService.findByTfidfId(tfidf6.get(i).getTfidfId());

            if (stemList.contains(tfidf.getWord())) {
                if (tfidf.getAgency().equals("LTO")) {
                    lto = lto + tfidf.getTfidfVal();
//                    System.out.println(tfidf.getWord() + "<------>" + tfidf.getAgency());
//                    System.out.println(tfidf.getWord() +" LTO value computation: " + lto + tfidf.getAgency());
                    System.out.println(tfidf.getAgency());
                    System.out.println(tfidf.getWord());
                    System.out.println(lto);
//                    System.out.println(tfidf.getAgency());          System.out.println(tfidf.getAgency());
//                    System.out.println("---------------");

                }
                if (tfidf.getAgency().equals("LRA")) {
                    System.out.println(tfidf.getAgency());
                    System.out.println(tfidf.getWord());
                    System.out.println(lra);
//                    System.out.println(tfidf.getAgency());
//                    System.out.println("---------------");
                }
                if (tfidf.getAgency().equals("PAG-IBIG")) {
                    System.out.println(tfidf.getAgency());
                    System.out.println(tfidf.getWord());
                    System.out.println(love);
//                    System.out.println(tfidf.getAgency());
//                    System.out.println("---------------");
                }
                if (tfidf.getAgency().equals("SSS")) {
                    System.out.println(tfidf.getAgency());
                    System.out.println(tfidf.getWord());
                    System.out.println(sss);
//                    System.out.println("---------------");
                }


            }
            entry.put("LTO", lto);
            entry.put("LRA", lra);
            entry.put("PAG-IBIG", love);
            entry.put("SSS", sss);
        }
        result = classifierController.maxVal(entry);

//        Article article = articleService.findAll();


        System.out.println("-----------RESULT-------------");
        for (Map.Entry<String, Double> e : result.entrySet()) {
            Test test1 = new Test();
            if(article.getAgency().equals(e.getKey())){
                test1.setArticleid(article.getArtId());
                test1.setActualAgency(article.getAgency());
                test1.setPredictedAgency(e.getKey());
                test1.setResultl("CORRECT");
                test1.setPhase("testtest");
                testService.save(test1);
                model.addAttribute("result","CORRECT");
            }
            else{
                test1.setArticleid(article.getArtId());
                test1.setActualAgency(article.getAgency());
                test1.setPredictedAgency(e.getKey());
                test1.setResultl("INCORRECT");
                test1.setPhase("testtest");
                testService.save(test1);
                model.addAttribute("result","INCORRECT");
            }

        }
        return "test";
    }



    @RequestMapping("/goLogout")
    public String goLogout(){

        return "index";
    }

    @PostMapping("/register")
    public String register(HttpServletRequest request){
        Users user = new Users();
        user.setUsername(request.getParameter("username"));
        user.setEmail(request.getParameter("email"));
        user.setPassword(request.getParameter("password"));
        usersService.saveUser(user);

        return "login";
    }
    @RequestMapping("/goIndex")
    public String goIndex(HttpServletRequest request, HttpSession session, Model model) {
        Users user= new Users();
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Users sampleUser = usersService.findUserByUsername(username, password);
        if (sampleUser != null) {
            session.setAttribute("user",sampleUser);
            model.addAttribute("username", username);
            String email=user.getEmail();
            model.addAttribute("email", email);

            int numArticles=articleService.getAll().size();
            model.addAttribute("numArticles", numArticles);
            model.addAttribute("msg","process web scraping...");
            return "index";
        }
        else {
            return "login";
        }
    }
    @PostMapping("/postText")
    public String text(HttpServletRequest request) throws IOException {
        Article article = new Article();
        String title=request.getParameter("title");
        String content=request.getParameter("content");
        String agency=request.getParameter("agency");
        String url=request.getParameter("url");
        article.setTitle(title);
        article.setTitle(url);
        article.setAgency(agency);
        article.setContent(content);
        articleService.save(article);
        cleanContent(content);
        return "index";
    }

//    @RequestMapping("/t")
//    public String p(){
//        return "govhtml/prac";
//    }
//
//    @RequestMapping("/testFunction")
//    public String prac(HttpServletRequest r) throws IOException {
//        String content = r.getParameter("content");
//        ArrayList<String> w = removeStopWords(content);
//        System.out.println("remove stop");
//        for(String ww:w){
//            System.out.println(ww);
//        }
//
//        ArrayList<String> s = stemming(w);
//
//        System.out.println("Stem");
//        for(String ss:s){
//            System.out.println(ss);
//        }
//
//        return "govhtml/prac";
//    }

    @PostMapping("/postScrape")
    public String scrape(HttpServletRequest request, Model model) throws IOException {
        Article article = new Article();
        String url=request.getParameter("url");
        String agency=request.getParameter("agency");
        Pattern mb = Pattern.compile("mb");
        Pattern abs = Pattern.compile("abs-cbn");
        Pattern manila= Pattern.compile("manilatimes");
        Pattern inquirer = Pattern.compile("inquirer");
        Pattern gma = Pattern.compile("gmanetwork");
        Pattern sun = Pattern.compile("sunstar");
        Matcher m=mb.matcher( url );
        Matcher a=abs.matcher( url );
        Matcher t=manila.matcher( url );
        //------------------------------------------------------------
        Matcher i=inquirer.matcher( url );
        Matcher g=gma.matcher( url );
        Matcher s=sun.matcher( url );

        Article sampleUrl = articleService.findByUrl(url);

            if (sampleUrl != null) {
                return "error";
            } else {

                if (a.find()) {
                    System.out.println("THIS IS ABS CBN");
                    Document document = Jsoup.connect(url).get();
                    String title = document.title();
                    String text = document.select("div.article-content").text();
                    System.out.println("title:" + title);
                    System.out.println("article" + text);
                    article.setTitle(title);
                    article.setAgency(agency);
                    article.setUrl(url);
                    article.setContent(text);
                    articleService.save(article);
//                    try {
                        cleanContent(text);
//                        model.addAttribute("msg","successful web scraping");
//                        return "index";
//                    }
//                    catch (Exception e){
//                        model.addAttribute("msg","web scraping error");
//                        return "index";
//                    }
                } else if (t.find()) {
                    System.out.println("THIS IS MANILATIMES");
                    Document document = Jsoup.connect(url).get();
                    String title = document.title();
                    System.out.println("title:" + title);

                    String text = document.select("div.article-wrap").text();

                    System.out.println("article" + text);
                    article.setTitle(title);
                    article.setAgency(agency);
                    article.setUrl(url);
                    article.setContent(text);
                    articleService.save(article);
//                    try {
                        cleanContent(text);
//                        model.addAttribute("msg","successful web scraping");
//                        return "index";
//                    }
//                    catch (Exception e){
//                        model.addAttribute("msg","web scraping error");
//                        return "index";
//                    }
                } else if (m.find()) {
                    System.out.println("THIS IS MANILA BULLETIN");
                    Document document = Jsoup.connect(url).get();
                    String title = document.title();
                    System.out.println("title:" + title);

                    String text = document.select("article.uk-article").text();

                    System.out.println("article" + text);
                    article.setTitle(title);
                    article.setAgency(agency);
                    article.setUrl(url);
                    article.setContent(text);
                    articleService.save(article);
//                    try {
                        cleanContent(text);
//                        model.addAttribute("msg","successful web scraping");
//                        return "index";
//                    }
//                    catch (Exception e){
//                        model.addAttribute("msg","web scraping error");
//                        return "index";
//                    }
                }

            }


        return "index";
    }
    @PostMapping("/postFile")
     public String postFile(){
        return "index";
    }

//    @GetMapping(value="/getArticles")
//    public String getArticles(Model map){
//        List<Article> articlelist = articleService.getAll();
//        map.addAttribute("articlelist",articlelist);
//        return "articles";
//    }
//
//    @GetMapping(value="/getFreq")
//    public String getFreq(HttpServletRequest request) {
//        int number= freService.getAll().size();
//        int artId=Integer.parseInt(request.getParameter("artId"));
//        List<String> allWords = new ArrayList<String>();
//        List<Integer> allFreq = new ArrayList<Integer>();
////        Frequency sampleFreq = freService.findByArtId(artId);
//
//        for (int c=0; c< number; c++){
//            Frequency sampleFreq = freService.findByArtId(artId);
//            String thisWord= sampleFreq.getWord();
//            int thisFreq=sampleFreq.getFrequency();
//            allWords.add(thisWord);
//            allFreq.add(thisFreq);
//        }
//
//        return "docu";
//    }
//    @GetMapping(value="/getExam")
//    public String getExam(Model map){
////        Ngram ngram =findbyAll();
//        List <Article> arts = articleService.getAll();
//        List <Ngram> ngram = ngramService.getAll();
//        List <Frequency> freq = freService.getAll();
//        List <Frequency> temp = new ArrayList<>();
////        for (int j=0; j<freq.size(); j++) {
////            for (int i = 0; i < freq.size(); i++) {
////                temp = freService.findByFreqId(freq.get(i).getNgramId());
////                System.out.println(temp);
////
////            }
////        }
//////                Frequency sampleFreq= freService.findByNgramId(temp);
////                System.out.println(freq.get(j).getFrequency());
////            }
//
////        List<Ngram> ngramlist = ngramService.getAll();
////        Ngram ngram = ngramService.getAll();
////        Frequency freq = freService.getAll();
////        List <Frequency> fre1 = freService.findByNgramIdandFreqId(ngram.getNgramId(), freq.getFreqId());
//////        Collections.sort(ngramlist);
//        map.addAttribute("freq",freq);
//
//        map.addAttribute("arts",arts);
////        map.addAttribute("freq",freq);
////        int col = wordlist.size();
//
//
//        return "docu";
//    }
//
////    public String error(){
////        return "index";
////    }

    @GetMapping(value="/getAllNgrams")
    public String getNgrams(HttpServletRequest request, Model map){
        List<Ngram> ngramlist = ngramService.getAll();
        System.out.println("Before: " + Arrays.toString(ngramlist.toArray(new Ngram[0])));

        Collections.sort(ngramlist, new Comparator<Ngram>() {
            public int compare(final Ngram keyValue1, final Ngram keyValue2) {
                return keyValue1.getWords().compareTo(keyValue2.getWords());
            }
        });

        System.out.println(ngramlist);
        map.addAttribute("ngramlist",ngramlist);
        return "ngrams";
    }
    @GetMapping(value="/getArticles")
    public String getArticles(Model map){
        List<Article> articlelist = articleService.getAll();
        map.addAttribute("articlelist",articlelist);
        return "articles";
    }
    @RequestMapping("/getArticleIds")
    public String getArticlesIds(HttpServletRequest request, ModelMap m, Model model) {
        String id = request.getParameter("id");
        Integer id1 = Integer.valueOf(id);
        List<Frequency> frequency = freService.getAll();
        List<Integer> val = new ArrayList<Integer>();


        for (int i = 0; i < frequency.size(); i++) {
            if (id1.equals(frequency.get(i).getNgramId())) {
                Frequency freq = freService.findByNgramId(frequency.get(i).getNgramId());
                val.add(freq.getArtId());
            }
        }
        System.out.println(val);
        m.addAttribute("val", val);

        return "sample";
    }

    @RequestMapping("/getSingleNgrams")
    public String getSingleNgrams(HttpServletRequest request, ModelMap m, Model model) {
        String id = request.getParameter("id");
        Integer id1 = Integer.valueOf(id);

        HashMap<String, Integer> nbysingle = new HashMap<>();
        System.out.println(id1);
        List<Frequency> frequency = freService.getAll();
//        ArrayList<String> k = new

        for (int i = 0; i < frequency.size(); i++) {
            System.out.println(frequency.get(i).getArtId());
            if (id1.equals(frequency.get(i).getArtId())) {
                Ngram ngram = ngramService.findByNgramId(frequency.get(i).getNgramId());
                nbysingle.put(ngram.getWords(), ngram.getWordCount());
            }
        }
        m.addAttribute("singleN",nbysingle);
//        for()
        return "singleNgram";
    }

    public ArrayList<String> removeStopWords(String content) throws IOException {
        System.out.println("done scrape");
        File file = new File("C:\\Users\\Cloie Andrea\\IdeaProjects\\Divulgo-master\\stopwords.txt");
        Set<String> stopWords = new LinkedHashSet<String>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String regex = "[A-Z]+";
        Pattern r = Pattern.compile(regex);

        String[] words =content.replaceAll("[^a-zA-Z ]", "").split("\\s+");

        for(String line;(line = br.readLine()) != null;)
            stopWords.add(line.trim());
        br.close();

        ArrayList<String> wordsList = new ArrayList<String>();
        for (String word : words) {
            System.out.println(word);
            wordsList.add(word);
        }

        Iterator<String> itr =wordsList.iterator();

        while (itr.hasNext()) {

            String w = itr.next();
            Matcher m = r.matcher(w);
            if (m.find()) {
                itr.remove();
                System.out.println("removed capital");
                System.out.println("--------------------");

            }

            else if (stopWords.contains(w)) {
                itr.remove();
                System.out.println("remove stop");
                System.out.println("--------------------");

            }
        }
        return wordsList;
    }

    public ArrayList<String> stemming(ArrayList<String> words){
        ArrayList<String> stemList = new ArrayList<String>();
        for (String a:words){
            PorterStemmer stemmer = new PorterStemmer();
            stemmer.setCurrent(a);
            stemmer.stem();
            String steem=stemmer.getCurrent();
            stemList.add(steem);
            System.out.println("stemmer: "+ steem);
        }


        System.out.println("DONE STEMMING");

        return stemList;
    }

    
// unclassified------------------------------------
    @RequestMapping("/unclass")
    public String unclass(Model model){
        List<Complaint> complaint = complaintService.findByAgency("Unclassified");
        model.addAttribute("unclassified_data",complaint);
        return "test/unclass";
    }

    @RequestMapping("/unclassified")
    public String unclassified(Model model,HttpServletRequest request){
        String cid = request.getParameter("id");
        String agency = request.getParameter("agency");
        Complaint complaint1 = complaintService.findByComplaintId(Long.valueOf(cid));
        complaint1.setAgency(agency);
        complaintService.save(complaint1);

        List<Complaint> complaint = complaintService.findByAgency("Unclassified");
        model.addAttribute("unclassified_data",complaint);
        return "test/unclass";
    }
//----------------------------------------


    @PostMapping("/cleanContent")
    public String cleanContent(String content) throws IOException {

//        System.out.println("done scrape");
//        File file = new File("C:\\Users\\Cloie Andrea\\IdeaProjects\\Divulgo-master\\stopwords.txt");
//        Set<String> stopWords = new LinkedHashSet<String>();
//        List<String> ngrams = new ArrayList<String>();
//        BufferedReader br = new BufferedReader(new FileReader(file));
//        String regex = "[A-Z]+";
//        Pattern r = Pattern.compile(regex);
        int wc=0, tempWC=0;
//        String changeWord;
//
//        String[] words =content.replaceAll("[^a-zA-Z ]", "").split("\\s+");
//
//        for(String line;(line = br.readLine()) != null;)
//            stopWords.add(line.trim());
//        br.close();
//
//        ArrayList<String> wordsList = new ArrayList<String>();
//        ArrayList<String> stemList = new ArrayList<String>();
//        for (String word : words) {
//            System.out.println(word);
//            wordsList.add(word);
//        }
//
//        Iterator<String> itr =wordsList.iterator();
//
//        while (itr.hasNext()) {
//
//            String w = itr.next();
//            Matcher m = r.matcher(w);
//            if (m.find()) {
//                itr.remove();
//                System.out.println("removed capital");
//                System.out.println("--------------------");
//
//            }
//
//            else if (stopWords.contains(w)) {
//                itr.remove();
//                System.out.println("remove stop");
//                System.out.println("--------------------");
//
//            }
//        }
//
//
//        for (String a:wordsList){
//            PorterStemmer stemmer = new PorterStemmer();
//            stemmer.setCurrent(a);
//            stemmer.stem();
//            String steem=stemmer.getCurrent();
//            stemList.add(steem);
//            System.out.println("stemmer: "+ steem);
//        }
//
//
//        System.out.println("DONE STEMMING");

        ArrayList<String> wordsList = removeStopWords(content);
        ArrayList<String> stemList = stemming(wordsList);

        Article sampleContent = articleService.findByContent(content);
        int articleid = sampleContent.getArtId();
        for (String bag:stemList){

            Ngram sampleWord = ngramService.findByWords(bag);
            if (sampleWord != null) {
//                int id = sampleWord.getArticleId();
//                wc=sampleWord.getWordCount();
            }
            else {
                Ngram ngram = new Ngram();
//                ngram.setArticleId(articleid);
                ngram.setWords(bag);
                ngram.setWordCount(wc);
                ngramService.save(ngram);

            }

        }

        System.out.println("DONE SAVING STEM WORDS");
        Set<String> unique = new HashSet<String>(stemList);

        for (String key : unique) {
            Ngram sampleWords = ngramService.findByWords(key);

            int wordsid = sampleWords.getNgramId();
//            int artid = sampleWords.getArticleId();

            if (sampleWords != null) {
                tempWC = sampleWords.getWordCount();
                System.out.println("temp count:" + tempWC);
                wc = tempWC + Collections.frequency(stemList, key);
                sampleWords.setWordCount(wc);
                ngramService.save(sampleWords);
                Frequency fre = new Frequency();
                fre.setFrequency(Collections.frequency(stemList, key));
                fre.setNgramId(wordsid);
                fre.setArtId(articleid);
                fre.setWord(key);
                freService.save(fre);

            } else {
                Frequency fre1 = new Frequency();
                fre1.setFrequency(Collections.frequency(stemList, key));
                fre1.setNgramId(wordsid);
                fre1.setArtId(articleid);
                fre1.setWord(key);
                freService.save(fre1);
            }
        }
        System.out.println("DONE NGRAM");
        return "index";
    }

//    public static List<String> ngrams(int n, String[] str) {
//        List<String> ngrams = new ArrayList<String>();
////        String[] words = str.split(" ");
//        for (int i = 0; i < words.length - n + 1; i++)
//            ngrams.add(concat(words, i, i+n));
//
//        return ngrams;
//    }
//    public static String concat(String[] words, int start, int end) {
//        StringBuilder sb = new StringBuilder();
//        for (int i = start; i < end; i++)
//            sb.append((i > start ? " " : "") + words[i]);
//        return sb.toString();
//    }


}
