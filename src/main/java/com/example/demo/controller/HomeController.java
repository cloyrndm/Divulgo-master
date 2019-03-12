package com.example.demo.controller;

import com.example.demo.entity.*;

import com.example.demo.service.*;
//import edu.smu.tspell.wordnet.Synset;
//import edu.smu.tspell.wordnet.WordNetDatabase;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * Created by Katrina on 9/27/2018.
 */
@SuppressWarnings("Duplicates")
@Controller
public class HomeController {

    @Autowired
    UserService userService;

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
    StopwordsService stopwordsService;

//    @Autowired
//    SentimentService sentimentService;

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

    @RequestMapping("/goUnclassified")
    public String goUnclassified(Model model){
        List<Complaint> complaint = complaintService.findByAgencyAndTrainStatus("unclassified",null);
        model.addAttribute("complaint",complaint);
        return "unclassified";
    }

    @RequestMapping("/govUnclassified")
    public String govUnclasssified(HttpServletRequest request, Model model){
        Article article = new Article();
        String agency = request.getParameter("agency");
        String complaint_id = request.getParameter("id");
        String complaint = request.getParameter("complaint");

        Complaint complaint2 = complaintService.findByComplaintId(Long.valueOf(complaint_id));
        complaint2.setAgency(agency);
        complaintService.save(complaint2);

        return "unclassified";
    }


    @RequestMapping("/goTrain")
    public String goTrain(Model model){
        List<Complaint> complaint = complaintService.findByStatusAndTrainStatus("1",null);
        model.addAttribute("complaint",complaint);
        return "train";
    }

   @RequestMapping("govtrain")
   public String govTrain(HttpServletRequest request, Model model) throws IOException {
       Article article = new Article();
       String agency = request.getParameter("agency");
       String complaint_id = request.getParameter("id");
       String complaint = request.getParameter("complaint");

       Complaint complaint2 = complaintService.findByComplaintId(Long.valueOf(complaint_id));
       complaint2.setTrainStatus("1");
       complaintService.save(complaint2);

       int count = new StringTokenizer(complaint).countTokens();
       System.out.println(count);
       article.setContent(complaint2.getUser_complaint());
       article.setAgency(complaint2.getAgency());
       article.setTitle("Complaint From The Citizens");
       article.setArtSize(count);
       articleService.save(article);
       cleanContent(complaint);
       System.out.println("----------------------starting term frequency process------------------------");
       System.out.println("saving...");
       tfIdfController.TermFrequency();
       System.out.println("------------------------end of term frequency process------------------------");
       System.out.println("-------------------------start idf count-------------------------------------");
       System.out.println("saving...");
       tfIdfController.wordcount();
       System.out.println("-------------------------end count-------------------------------------------");
       System.out.println("----------------------starting idf frequency process------------------------");
       System.out.println("saving...");
       tfIdfController.InverseTermFrequency();
       System.out.println("----------------------end idf process---------------------------------------");
       System.out.println("-------------------start tfidf process--------------------------------------");
       System.out.println("saving...");
       tfIdfController.TermFrequencyAndInverseTermFrequency();
       System.out.println("---------------------------------------end----------------------------------");

       List<Complaint> complaint1 = complaintService.findByStatusAndTrainStatus("1",null);
       model.addAttribute("complaint",complaint1);
       return "train";
   }

    @RequestMapping("adtrain")
    public String adminTrain(HttpServletRequest request, Model model) throws IOException {

        String agency = request.getParameter("agency");
        String complaint = request.getParameter("complaint");

        int count = new StringTokenizer(complaint).countTokens();
        System.out.println(count);
        Article article = new Article();
        article.setContent(complaint);
        article.setAgency(agency);
        article.setTitle("Complaint From The Citizens");
        article.setArtSize(count);
        articleService.save(article);

        cleanContent(complaint);
        System.out.println("----------------------starting term frequency process------------------------");
        System.out.println("saving...");
        tfIdfController.TermFrequency();
        System.out.println("------------------------end of term frequency process------------------------");
        System.out.println("-------------------------start idf count-------------------------------------");
        System.out.println("saving...");
        tfIdfController.wordcount();
        System.out.println("-------------------------end count-------------------------------------------");
        System.out.println("----------------------starting idf frequency process------------------------");
        System.out.println("saving...");
        tfIdfController.InverseTermFrequency();
        System.out.println("----------------------end idf process---------------------------------------");
        System.out.println("-------------------start tfidf process--------------------------------------");
        System.out.println("saving...");
        tfIdfController.TermFrequencyAndInverseTermFrequency();
        System.out.println("---------------------------------------end----------------------------------");

        List<Complaint> complaint1 = complaintService.findByStatusAndTrainStatus("1",null);
        model.addAttribute("complaint",complaint1);
        return "index";
    }

    @RequestMapping("/getResult")
    public String result(HttpServletRequest request, Model model){
        String test = request.getParameter("testcontent");
        String agency = request.getParameter("agency");
//        Article article = articleService.findByContent(test);
//        String rAgency = article.getAgency();
//        System.out.println(rAgency);
        String[] words = test.replaceAll("[^a-zA-Z ]", "").split("\\s+");
        ArrayList<String> stemList = new ArrayList<>();
        ArrayList<String> wordsList = new ArrayList<>();
        ArrayList<String> ngramsss = new ArrayList<String>();
//        ArrayList<String> wordsList = new ArrayList<String>();
        String regex = "[A-Z]+";
        Pattern r = Pattern.compile(regex);

        for (String word : words) {
//            System.out.println(word);
            wordsList.add(word);
        }

        Iterator<String> itr =wordsList.iterator();

        while (itr.hasNext()) {

            String w = itr.next();
            Matcher m = r.matcher(w);
            Stopwords sampleStopword = stopwordsService.findByStopwords(w);
            if (m.find()) {
                itr.remove();
            }

            else if (sampleStopword != null) {
                itr.remove();
            }
        }

//        String regex = "[A-Z]+";
//        Pattern r = Pattern.compile(regex);
//        File f=new File("C:\\Users\\Katrina\\Desktop\\Divulgo-master-master\\Divulgo-master-master\\WordNet\\2.1\\dict");
//        System.setProperty("wordnet.database.dir", f.toString());
//        //setting path for the WordNet Directory
//
//        WordNetDatabase database = WordNetDatabase.getFileInstance();
        for (String word : wordsList) {
//            System.out.println(word);
            System.out.println(word);
//            wordsList.add(word);
        }

//        Iterator<String> itr =wordsList.iterator();
//
//        while (itr.hasNext()) {
//
//            String w = itr.next();
//            Matcher m = r.matcher(w);
//            Stopwords sampleStopword = stopwordsService.findByStopwords(w);
//            if (m.find()) {
//                itr.remove();
//            }
//
//            else if (sampleStopword != null) {
//                itr.remove();
//            }
//        }


//
//SENTIMENT
//        int sentimentRate=0, sentId=0;
//        List<Sentiment> sents = sentimentService.findAll();
//        for (String senti: wordsList) {
//
//            Sentiment rate = sentimentService.findBySentiment(senti);
//
//            System.out.println("I WAS HERE " + senti );
//
//            if (rate!=null){
//                System.out.println("ME HEREEEEE");
//                sentId= rate.getSentimentId();
//                Sentiment getId = sentimentService.findBySentimentId(sentId);
//                sentimentRate+=getId.getRating();
//                System.out.println("rate:" + sentimentRate);
//            }
//        }


        for (String a : wordsList) {
            PorterStemmer stemmer = new PorterStemmer();
            stemmer.setCurrent(a);
            stemmer.stem();
            String steem = stemmer.getCurrent();
            stemList.add(steem);
            System.out.println(steem);
        }

        String str = String.join(" ", stemList);
        for (int n = 1; n <=3; n++) {
            for (String ngram : ngrams(n, str)){
                ngramsss.add(ngram);
            }
        }

        System.out.println("WORDS: ");
        for(String x : ngramsss){
            System.out.println(x);
        }
        System.out.println("------------");

        List<Tfidf> tfidf6 = tfidfService.findAll();
        Double love = 0.0;
        Double lra = 0.0;
        Double lto = 0.0;
        Double sss = 0.0;
//        Double val = 0.0;
        HashMap<String, Double> result = new HashMap<>();
        HashMap<String, Double> entry = new HashMap<>();

        for (int i = 0; i < ngramsss.size(); i++) {
            Tfidf tfidf1 = tfidfService.findByWordAndAgency(ngramsss.get(i),"LTO");
            Tfidf tfidf2 = tfidfService.findByWordAndAgency(ngramsss.get(i),"LRA");
            Tfidf tfidf3 = tfidfService.findByWordAndAgency(ngramsss.get(i),"PAG-IBIG");
            Tfidf tfidf4 = tfidfService.findByWordAndAgency(ngramsss.get(i),"SSS");

            if(tfidf1!=null){
                lto = lto + tfidf1.getTfidfVal();
                System.out.println("word: "+tfidf1.getWord());
                System.out.println("tf = "+tfidf1.getTfVal());
                System.out.println("idf = "+tfidf1.getIdfVal());
                System.out.println("tf-idf = "+tfidf1.getTfidfVal());
                System.out.println("lto computation: "+lto);
                System.out.println("------------");
            }
            if(tfidf2!=null){
                lra = lra + tfidf2.getTfidfVal();
                System.out.println("word: "+tfidf2.getWord());
                System.out.println("tf = "+tfidf2.getTfVal());
                System.out.println("idf = "+tfidf2.getIdfVal());
                System.out.println("tf-idf = "+tfidf2.getTfidfVal());
                System.out.println("lra computation: "+lra);
                System.out.println("------------");
            }
            if(tfidf3!=null){
                love = love + tfidf3.getTfidfVal();
                System.out.println("word: "+tfidf3.getWord());
                System.out.println("tf = "+tfidf3.getTfVal());
                System.out.println("idf = "+tfidf3.getIdfVal());
                System.out.println("tf-idf = "+tfidf3.getTfidfVal());
                System.out.println("love computation: "+love);
                System.out.println("------------");
            }
            if(tfidf4!=null){
                sss = sss + tfidf4.getTfidfVal();
                System.out.println("word: "+tfidf4.getWord());
                System.out.println("tf = "+tfidf4.getTfVal());
                System.out.println("idf = "+tfidf4.getIdfVal());
                System.out.println("tf-idf = "+tfidf4.getTfidfVal());
                System.out.println("sss computation: "+sss);
                System.out.println("------------");
            }
//            else{
//                val = val + 1;
//            }

            entry.put("LTO", lto);
            entry.put("LRA", lra);
            entry.put("PAG-IBIG", love);
            entry.put("SSS", sss);
//            entry.put("UNCLASSIFIED",val);
    }

    result = maxVal(entry);

        for (Map.Entry<String, Double> entryy : result.entrySet()) {
            System.out.println("Result: "+entryy.getKey()+" : "+entryy.getValue());
        }

        System.out.println("-----------RESULT-------------");
        for (Map.Entry<String, Double> e : result.entrySet()) {
            Test test1 = new Test();
//            if(article.getAgency().equals(e.getKey())){
            if(agency.equals(e.getKey())){
//                test1.setArticleid(article.getArtId());
//                test1.setActualAgency(article.getAgency());
                test1.setActualAgency(agency);
                test1.setPredictedAgency(e.getKey());
                test1.setResultl("CORRECT");
                test1.setPhase("30");
                testService.save(test1);
                model.addAttribute("result","CORRECT");
            }
                else{
//                test1.setArticleid(article.getArtId());
                test1.setActualAgency(agency);
                test1.setPredictedAgency(e.getKey());
                test1.setResultl("INCORRECT");
                test1.setPhase("30");
                testService.save(test1);
                model.addAttribute("result","INCORRECT");
            }

        }
        return "test";
    }

//
//    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list)
//    {
//
//        ArrayList<T> newList = new ArrayList<T>();
//
//        for (T element : list) {
//
//            if (!newList.contains(element)) {
//
//                newList.add(element);
//            }
//        }
//
//        return newList;
//    }

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
            else if(maxval == 0.0){
                max.put("UNCLASSIFIED",0.0);
            }
        return max;
    }

    @RequestMapping("/goLogout")
    public String goLogout(){

        return "index";
    }
//--------error bc deleted users entity
//    @PostMapping("/register")
//    public String register(HttpServletRequest request){
//        Users user = new Users();
//        user.setUsername(request.getParameter("username"));
//        user.setEmail(request.getParameter("email"));
//        user.setPassword(request.getParameter("password"));
//        usersService.saveUser(user);
//
//        return "login";
//    }
    //--------error bc deleted users entity

    //--------error bc deleted users entity
//    @RequestMapping("/goIndex")
//    public String goIndex(HttpServletRequest request, HttpSession session, Model model) {
//        Users user= new Users();
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//
//        Users sampleUser = usersService.findUserByUsername(username, password);
//        if (sampleUser != null) {
//            session.setAttribute("user",sampleUser);
//            model.addAttribute("username", username);
//            String email=user.getEmail();
//            model.addAttribute("email", email);
//
//            int numArticles=articleService.getAll().size();
//            model.addAttribute("numArticles", numArticles);
//            model.addAttribute("msg","process web scraping...");
//            return "index";
//        }
//        else {
//            return "login";
//        }
//    }
    //--------error bc deleted users entity


    @PostMapping("/register")
    public String register(HttpServletRequest request){
        User users = new User();
        String uType="admin";
        users.setUsername(request.getParameter("username"));
        users.setEmail(request.getParameter("email"));
        users.setPassword(request.getParameter("password"));
        users.setFirst_name(request.getParameter("firstname"));
        users.setLast_name(request.getParameter("lastname"));
        users.setUserType(uType);
        userService.save(users);

        return "login";
    }
    @RequestMapping("/goIndex")
    public String goIndex(HttpServletRequest request, HttpSession session, Model model) {
        User user= new User();
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User sampleUser = userService.findByUsername(username);
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
//        Article article = new Article();
//        String title=request.getParameter("title");
//        String content=request.getParameter("content");
//        String agency=request.getParameter("agency");
//        String url=request.getParameter("url");
//        System.out.println(content);
//        article.setTitle(title);
//        article.setUrl(url);
//        article.setAgency(agency);
//        article.setContent(content);
//        articleService.save(article);
//        cleanContent(content);
//        Sentiment sent = new Sentiment();
//        String sentiment= request.getParameter("sentiment");
//        String rating = request.getParameter("rating");
//        Integer rate = Integer.valueOf(rating);

//        PorterStemmer stemmer = new PorterStemmer();
//        stemmer.setCurrent(sentiment);
//        stemmer.stem();
//        String steem=stemmer.getCurrent();

//        sent.setSentiment(sentiment);
//        sent.setRating(rate);
//        sentimentService.save(sent);

        return "index";
    }

    @PostMapping("/postScrape")
    public String scrape(HttpServletRequest request, Model model) throws IOException {
        Article article = new Article();
        String url=request.getParameter("url");
        String agency=request.getParameter("agency");
        Pattern mb = Pattern.compile("mb");
        Pattern abs = Pattern.compile("abs-cbn");
        Pattern manila= Pattern.compile("manilatimes");
        Pattern inq= Pattern.compile("inquirer");
        Matcher m=mb.matcher( url );
        Matcher a=abs.matcher( url );
        Matcher t=manila.matcher( url );
        Matcher i=inq.matcher( url );
        String title, text;
        Article sampleUrl = articleService.findByUrl(url);
        System.out.println("SADSADASDAS");

        if (sampleUrl != null) {
            return "error";
        } else {

            if (a.find()) {
                System.out.println("THIS IS ABS CBN");
                Document document = Jsoup.connect(url).get();
                title = document.title();
                text = document.select("div.article-content").text();
                System.out.println("title:" + title);
                System.out.println("article" + text);
//                article.setTitle(title);
//                article.setAgency(agency);
//                article.setUrl(url);
//                article.setContent(text);
//                articleService.save(article);
//                cleanContent(text);
                int count = new StringTokenizer(text).countTokens();
                System.out.println(count);
                article.setArtSize(count);
                article.setTitle(title);
                article.setContent(text);
                article.setAgency(agency);
                article.setUrl(url);
                articleService.save(article);
                cleanContent(text);
            } else if (t.find()) {
                System.out.println("THIS IS MANILATIMES");
                Document document = Jsoup.connect(url).get();
                title = document.title();
                System.out.println("title:" + title);
                text = document.select("div.article-wrap").text();

                System.out.println("article" + text);
                int count = new StringTokenizer(text).countTokens();
                System.out.println(count);
                article.setArtSize(count);
                article.setTitle(title);
                article.setContent(text);
                article.setAgency(agency);
                article.setUrl(url);
                articleService.save(article);
                cleanContent(text);
            } else if (m.find()) {
                System.out.println("THIS IS MANILA BULLETIN");
                Document document = Jsoup.connect(url).get();
                title = document.title();
                System.out.println("title:" + title);
                text = document.select("article.uk-article").text();

                System.out.println("article" + text);
                int count = new StringTokenizer(text).countTokens();
                System.out.println(count);
                article.setArtSize(count);
                article.setTitle(title);
                article.setContent(text);
                article.setAgency(agency);
                article.setUrl(url);
                articleService.save(article);
                cleanContent(text);

            } else if (i.find()) {
                System.out.println("THIS IS INQUIRER");
                ArrayList<String> content = new ArrayList<String>();
                Document document = Jsoup.connect(url).get();
                title = document.title();
                System.out.println("title:" + title);
                Elements elements = document.select("p");
                for (Element e : elements) {
                    content.add(e.text());
                }
                text = String.join(" ", content);
                int count = new StringTokenizer(text).countTokens();
                System.out.println(count);
                article.setArtSize(count);
                article.setTitle(title);
                article.setContent(text);
                article.setAgency(agency);
                article.setUrl(url);
                articleService.save(article);
                cleanContent(text);
            }
        }

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

//    @PostMapping("/stopwords")
//    public String stop () throws IOException {
//
//        File file = new File("C:\\Users\\Cloie Andrea\\IdeaProjects\\Divulgo-master\\stopwords.txt");
//        Set<String> stopWords = new LinkedHashSet<String>();
//        BufferedReader br = new BufferedReader(new FileReader(file));
//
//        for(String line;(line = br.readLine()) != null;)
//            stopWords.add(line.trim());
//        br.close();
//
//        for (String a: stopWords){
//            Stopwords stop = new Stopwords();
//            stop.setStopwords(a);
//            stopwordsService.save(stop);
//        }
//        return "index";
//    }

    public String cleanContent(String content) throws IOException {

        String regex = "[A-Z]+";
        Pattern r = Pattern.compile(regex);
        int wc=0;

        String[] words =content.replaceAll("[^a-zA-Z ]", "").split("\\s+");

        ArrayList<String> wordsList = new ArrayList<String>();
        ArrayList<String> stemList = new ArrayList<String>();
        ArrayList<String> ngramsss = new ArrayList<String>();
        for (String word : words) {
            System.out.println(word);
            wordsList.add(word);
        }

        Iterator<String> itr =wordsList.iterator();

        while (itr.hasNext()) {

            String w = itr.next();
            Matcher m = r.matcher(w);
            Stopwords sampleStopword = stopwordsService.findByStopwords(w);
            if (m.find()) {
                itr.remove();
            }

            else if (sampleStopword != null) {
                itr.remove();
            }
        }


        for (String a:wordsList){
            PorterStemmer stemmer = new PorterStemmer();
            stemmer.setCurrent(a);
            stemmer.stem();
            String steem=stemmer.getCurrent();
            stemList.add(steem);
        }


        System.out.println("DONE STEMMING");

        Article sampleContent = articleService.findByContent(content);
        int articleid = sampleContent.getArtId();

        String str = String.join(" ", stemList);
        for (int n = 1; n <=3; n++) {
            for (String ngram : ngrams(n, str)){
                ngramsss.add(ngram);
                System.out.println(ngram);
            }
        }

        for (String bag:ngramsss){

            Ngram sampleWord = ngramService.findByWords(bag);
            if (sampleWord != null) {
//                int id = sampleWord.getArticleId();
//                wc=sampleWord.getWordCount();
            }
            else {
                Ngram ngram = new Ngram();
                ngram.setWords(bag);
                ngram.setWordCount(wc);
                ngramService.save(ngram);

            }

        }



        System.out.println("DONE SAVING STEM WORDS");
        Set<String> unique = new HashSet<String>(ngramsss);

        for (String key : unique) {
            Ngram sampleWords = ngramService.findByWords(key);

//            int wordsid = sampleWords.getNgramId();
//            System.out.println("wordsid:"+ wordsid);
            if (sampleWords != null) {
                wc = sampleWords.getWordCount() + Collections.frequency(ngramsss, key);
                sampleWords.setWordCount(wc);
                ngramService.save(sampleWords);
                Frequency fre = new Frequency();
                fre.setFrequency(Collections.frequency(ngramsss, key));
                fre.setNgramId(sampleWords.getNgramId());
                fre.setArtId(articleid);
                freService.save(fre);


            } else {
                Frequency fre1 = new Frequency();
                fre1.setFrequency(Collections.frequency(ngramsss, key));
                fre1.setNgramId(sampleWords.getNgramId());
                fre1.setArtId(articleid);
                freService.save(fre1);
            }
        }
        System.out.println("DONE NGRAM");
        return "index";
    }

    public static List<String> ngrams(int n, String str) {
        List<String> ngrams = new ArrayList<String>();
        String[] words = str.split(" ");
        for (int i = 0; i < words.length - n + 1; i++)
            ngrams.add(concat(words, i, i+n));

        return ngrams;
    }
    public static String concat(String[] words, int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++)
            sb.append((i > start ? " " : "") + words[i]);
        return sb.toString();
    }


}
