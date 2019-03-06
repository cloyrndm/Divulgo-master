package com.example.demo.controller;

import com.example.demo.entity.*;

import com.example.demo.service.*;
import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.WordNetDatabase;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import emoji4j.EmojiUtils;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
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

//import org.apache.commons.lang3.StringUtils;

/**
 * Created by Katrina on 9/27/2018.
 */
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

    @Autowired
    SentimentService sentimentService;


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
        return "test2";
    }

    @RequestMapping("/getResult")
    public String result(HttpServletRequest request, Model model){
        String test = request.getParameter("testcontent");
        Article article = articleService.findByContent(test);
//        String rAgency = article.getAgency();
//        System.out.println(rAgency);
        String[] words = test.replaceAll("[^a-zA-Z ]", "").split("\\s+");
        ArrayList<String> stemList = new ArrayList<>();
        ArrayList<String> wordsList = new ArrayList<>();
        ArrayList<String> ngramsss = new ArrayList<String>();
        ArrayList<String> ngramss = new ArrayList<String>();
        ArrayList<String> tempList= new ArrayList<String>();
        ArrayList<String> wordnetStem = new ArrayList<String>();
        File f=new File("C:\\Users\\Katrina\\Desktop\\Divulgo-master-master\\Divulgo-master-master\\WordNet-3.0\\dict");
        System.setProperty("wordnet.database.dir", f.toString());
        //setting path for the WordNet Directory

        WordNetDatabase database = WordNetDatabase.getFileInstance();

        String regex = "[A-Z]+";
        Pattern r = Pattern.compile(regex);

        System.out.println("WORDS LIST:");
        for (String word : words) {
            wordsList.add(word);
            System.out.println(word);
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
        System.out.println("TEMP LIST:");
        for (String wordssss:wordsList){
            tempList.add(wordssss);
            System.out.println(wordssss);
        }

//PLEASE INCLUDE THIS
//        int sentimentRate=0, sentId=0;
//        for (String senti: wordsList) {
//            Sentiment rate = sentimentService.findBySentiment(senti);
//            if (rate!=null){
//                sentId= rate.getSentimentId();
//                Sentiment getId = sentimentService.findBySentimentId(sentId);
//                sentimentRate+=getId.getSentimentScore();
//            }
//        }

        System.out.println("STEM LIST:");
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

        String tmp = String.join(" ", tempList);
        for (int n = 1; n <=3; n++) {
            for (String ngram : ngrams(n, tmp)){
                ngramss.add(ngram);
            }
        }



        Double love = 0.0;
        Double lra = 0.0;
        Double lto = 0.0;
        Double sss = 0.0;
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
                System.out.println("lto computation: "+lto);
            }
            else if(tfidf2!=null){
                lra = lra + tfidf2.getTfidfVal();
                System.out.println("word: "+tfidf2.getWord());
                System.out.println("lra computation: "+lra);
            }
            else if(tfidf3!=null){
                love = love + tfidf3.getTfidfVal();
                System.out.println("word: "+tfidf3.getWord());
                System.out.println("love computation: "+love);
            }
            else if(tfidf4!=null){
                sss = sss + tfidf4.getTfidfVal();
                System.out.println("word: "+tfidf4.getWord());
                System.out.println("sss computation: "+sss);
            }

            else {
                System.out.println("I WENT WORDNET");
                ArrayList<String> al = new ArrayList<String>();
                System.out.println("TO FIND:" + ngramsss.get(i));
                int retval = ngramsss.indexOf(ngramsss.get(i));
                System.out.println("RETVAL:" + retval);
                String thisWord= ngramss.get(retval).toString();
                System.out.println("THISWORD: "+ thisWord);
                Synset[] synsets = database.getSynsets(thisWord);

                if (synsets.length > 0) {
                    // add elements to al, including duplicates
                    HashSet hs = new HashSet();
                    for (int b = 0; b < synsets.length; b++) {
                        String[] wordForms = synsets[b].getWordForms();
                        for (int j = 0; j < wordForms.length; j++) {
                            al.add(wordForms[j]);
                        }

                        hs.addAll(al);
                        al.clear();
                        al.addAll(hs);
                    }
//                      showing all synsets
//                        for (int a = 0; a < al.size(); a++) {
//                            synonyms.add(al.get(a));
//                            System.out.println(al.get(a));
                }
                            for (String a : al) {
                                PorterStemmer stemmer = new PorterStemmer();
                                stemmer.setCurrent(a);
                                stemmer.stem();
                                String steem = stemmer.getCurrent();
                                wordnetStem.add(steem);
//                                System.out.println(steem);
                            }
                        for (int b = 0; b < wordnetStem.size(); b++) {
                            Tfidf tfidfa = tfidfService.findByWordAndAgency(ngramsss.get(b), "LTO");
                            Tfidf tfidfb = tfidfService.findByWordAndAgency(ngramsss.get(b), "LRA");
                            Tfidf tfidfc = tfidfService.findByWordAndAgency(ngramsss.get(b), "PAG-IBIG");
                            Tfidf tfidfd = tfidfService.findByWordAndAgency(ngramsss.get(b), "SSS");

                            if (tfidfa != null) {
                                lto = lto + tfidfa.getTfidfVal();
                                System.out.println("word: " + tfidfa.getWord());
                                System.out.println("lto computation: " + lto);

                            } if (tfidfb != null) {
                                lra = lra + tfidfb.getTfidfVal();
                                System.out.println("word: " + tfidfb.getWord());
                                System.out.println("lra computation: " + lra);

                            } if (tfidfc != null) {
                                love = love + tfidfc.getTfidfVal();
                                System.out.println("word: " + tfidfc.getWord());
                                System.out.println("love computation: " + love);

                            } if (tfidfd != null) {
                                sss = sss + tfidfd.getTfidfVal();
                                System.out.println("word: " + tfidfd.getWord());
                                System.out.println("sss computation: " + sss);

                            }
                            else {
                                System.out.println(thisWord + " === NO AVAILABLE DATASET");
                                break;
                            }
                        }

                System.out.println("DONE WORDNET");
                    }
                }


            entry.put("LTO", lto);
            entry.put("LRA", lra);
            entry.put("PAG-IBIG", love);
            entry.put("SSS", sss);




        result = maxVal(entry);

//        for (Map.Entry<String, Double> entryy : result.entrySet()) {
//            System.out.println("Result: "+entryy.getKey()+" : "+entryy.getValue());
//        }

        System.out.println("-----------RESULT-------------");

        System.out.println("LTO: " + lto);
        System.out.println("PAG-IBIG: " + love);
        System.out.println("SSS: "+ sss);
        System.out.println("LRA: "+ lra);
//        for (Map.Entry<String, Double> e : result.entrySet()) {
//            Test test1 = new Test();
//            if(article.getAgency().equals(e.getKey())){
//                test1.setArticleid(article.getArtId());
//                test1.setActualAgency(article.getAgency());
//                test1.setPredictedAgency(e.getKey());
//                test1.setResultl("CORRECT");
//                test1.setPhase("1");
//                testService.save(test1);
//                model.addAttribute("result","CORRECT");
//            }
//            else{
//                test1.setArticleid(article.getArtId());
//                test1.setActualAgency(article.getAgency());
//                test1.setPredictedAgency(e.getKey());
//                test1.setResultl("INCORRECT");
//                test1.setPhase("1");
//                testService.save(test1);
//                model.addAttribute("result","INCORRECT");
//            }
//
//        }
        return "test2";
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
        Sentiment sent = new Sentiment();
        String sentiment= request.getParameter("sentiment");
        String rating = request.getParameter("rating");
        String scoring = request.getParameter("scoring");
        Double score = Double.valueOf(scoring);
            Sentiment thisSentiment = sentimentService.findBySentiment(sentiment);

//        if (thisSentiment!= null) {
//            if (thisSentiment.getSentimentScore() > score) {
//            } else {
//                thisSentiment.setSentimentScore(score);
//                sentimentService.save(thisSentiment);
//            }
//        }
//        else {


            sent.setSentiment(sentiment);
            sent.setRating(rating);
            sent.setScoring(score);
//        sent.setPosTagger("RB");
            sentimentService.save(sent);
//        }

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
                Document document = Jsoup.connect(url).get();
                title = document.title();
                text = document.select("div.article-content").text();
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
                Document document = Jsoup.connect(url).get();
                title = document.title();
                text = document.select("div.article-wrap").text();
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
                Document document = Jsoup.connect(url).get();
                title = document.title();
                text = document.select("article.uk-article").text();
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

    @RequestMapping("/getSentiment")
    public String getSentiment (){

        int upperCase=0, exMark=0, temp=0, repeatedSequence=0;
        String complaint="I'm SO ANNOYED :( :( :'( right now because of the sooooo heavy traffic and I have plenty of tasks to accomplish!!!!";


        ArrayList<String> trimmedWords= new ArrayList<String>();

        String[] complaintWords = complaint.trim().split("\\s+");
        double wordCount = complaintWords.length;
//
//        for (int k = 0; k < complaint.length(); k++) {
//            if (wordArray(k).equals(str.toUpperCase()))
//                upperCase++;
//        }
////

        for (String abc:complaintWords){
            if (!abc.contains("I")&&StringUtils.isAllUpperCase(abc)){
               upperCase++;

            }
        }
        Double capsRate= (double) upperCase/ (double) wordCount;
        System.out.println("WC: "+wordCount);
        System.out.println("UC: "+upperCase);
        System.out.println("CR: "+capsRate);

        //counts number of exclamation marks
        for (int k = 0; k < complaint.length(); k++) {
            if (complaint.charAt(k)=='!')
                exMark++;
        }

        System.out.println("EM: "+ exMark);

        //counts repeated sequence
        Matcher m = Pattern.compile("(\\p{Alpha})\\1{2,}").matcher(complaint);
        while (m.find()) {
            repeatedSequence=m.group().length()-1;
            System.out.println(repeatedSequence);
            temp=temp + repeatedSequence;
        }
        System.out.println("RS: "+ temp);

        //removes repeated characters
//        String ourString="";
//        for (int i=0; i<complaint.length()-1 ; i++){
//            if(i==0){
//                ourString = ""+complaint.charAt(i);
//            }else{
//                if(complaint.charAt(i-1) != complaint.charAt(i)){
//                    ourString = ourString +complaint.charAt(i);
//                }
//            }
//        }
//
//        System.out.println(ourString);

//WORKINGGGG!!!!!
        System.out.println(EmojiUtils.shortCodify(complaint));

        String[] resultEmoji = EmojiUtils.shortCodify(complaint).trim().split(" ");
        ArrayList <String> wordEmoticon = new ArrayList<String>();
        Double emoticonScoring=0.0;
        Set<String> emojis = new HashSet<String>();
        for (int x=0; x<resultEmoji.length; x++){

            Pattern pattern = Pattern.compile(":(.*?):");
            Matcher matcher = pattern.matcher(resultEmoji[x]);
            if (matcher.find()){
//                System.out.println(matcher.group(1));
                wordEmoticon.add(matcher.group(1));
                emojis.add(matcher.group(1));
            }
        }
//        Set<String> emojis = new HashSet<String>(wordEmoticon);
        for (int aaa=0; aaa<wordEmoticon.size();aaa++){
//            System.out.println(kat);
            Sentiment emotion=sentimentService.findBySentiment(wordEmoticon.get(aaa));

            if (emotion!=null) {
//                HashSet hs = new HashSet();
//                hs.addAll(emojis);
//                emojis.clear();
//                emojis.addAll(hs);

                Double theScore = emotion.getScoring();
                System.out.println("THE SCORE:" + theScore);
                Double ScoreTimesFrequency = theScore * Collections.frequency(wordEmoticon, wordEmoticon.get(aaa));
                System.out.println("SCORE TIMES FREQUENCY:" + ScoreTimesFrequency);
                emoticonScoring += ScoreTimesFrequency;
                System.out.println("EMOTION SCORING: " + emoticonScoring);
                //System.out.println(Collections.frequency(wordEmoticon, kat));
            }
            else
            {

            }

           // Collections.frequency(ngramsss, key);
        }



//////
        String theComplaint= complaint.toLowerCase();
        MaxentTagger tagger = new MaxentTagger("C:\\Users\\Katrina\\Desktop\\Divulgo-master-master\\Divulgo-master-master\\models\\english-left3words-distsim.tagger");
        String tagged = tagger.tagString(theComplaint);

        System.out.println(tagged);
        ArrayList <String> wordArray = new ArrayList<String>();
        String [] theWords= tagged.trim().split("\\s+");
        for (String c:theWords){
            wordArray.add(c);
        }
        for (String strings:wordArray) {
            String s1 = strings.substring(strings.indexOf("_") );
            String test = strings.replace(s1, "");
            trimmedWords.add(test);
        }
//        System.out.println("TRIMMED:");
//        for (String wordss: trimmedWords){
//            System.out.println(wordss);
//        }


        Double adv=0.0, adj=0.0, adjGrp=0.0, totalAdjGrp=0.0;
        String pos="";
        for (String aaa: trimmedWords) {

            Sentiment senti = sentimentService.findBySentiment(aaa);
//                pos=senti.getPosTagger();

            if (senti != null) {
                for (int i = 0; i < wordArray.size(); i++) {

                    if (wordArray.get(i).contains("RB") && wordArray.get(i + 1).contains("JJ")) {
//                    String thisWord=wordArray.get(i);
//                        String s1 = wordArray.get(i).substring(wordArray.get(i).indexOf("_"));
//                        String finalWord = wordArray.get(i).replace(s1, "");

                        System.out.println("I WENT HEREEEEEEE");
                        System.out.println(wordArray.get(i));
                        System.out.println(wordArray.get(i + 1));
                        String final1 = trimmedWords.get(i);
                        String final2 = trimmedWords.get(i + 1);
                        Sentiment sentiRB = sentimentService.findBySentiment(final1);
                        Sentiment sentiJJ = sentimentService.findBySentiment(final2);
                        adv = sentiRB.getScoring();
                        adj = sentiJJ.getScoring();
                        adjGrp = adv * adj;
                        totalAdjGrp *= adjGrp;
                        System.out.println("TEMPORARY ADJ GRP: "+ adjGrp);
                    } else if (wordArray.get(i).contains("JJ")) {
                        System.out.println("JJJJJ");
                        Sentiment sentiJJ = sentimentService.findBySentiment(trimmedWords.get(i));
                        if (sentiJJ != null) {
                            adj = sentiJJ.getScoring();
                            Double tempo = adj * 0.5;
                            adjGrp *= tempo;
                        } else {
                            adj = 0.0;
                        }
                    }
//                          else {
//                        System.out.println("WALA NA FINISH NA");
//                    }
                }
            }
        }

//        for (String key :emojis) {
//            System.out.println(Collections.frequency(emojis, key));
//        }

//        int count = 0;
//        String[] resultEmojiArray = EmojiUtils.shortCodify(complaint).split("\\s");;
//        for (int i = 0; i < resultEmojiArray.length; i++)
//        {
//            // if match found increase count
//            if (word.equals(a[i]))
//                count++;
//        }
//        System.out.println("Total adj grp: "+totalAdjGrp);
//        System.out.println("Adj grp: "+ adjGrp);




        return "index";
    }

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
