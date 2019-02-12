package com.example.demo.controller;

import com.example.demo.entity.Article;
import com.example.demo.entity.Complaint;
import com.example.demo.entity.ComplaintReply;
import com.example.demo.entity.GovtUser;
import com.example.demo.service.*;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by Cloie Andrea on 01/10/2018.
 */
@SuppressWarnings("Duplicates")
@Controller
public class GovtUserController {

     @Autowired
     GovtUserService govtUserService;

    @Autowired
    ComplaintReplyService complaintReplyService;

    @Autowired
    ComplaintService complaintService;

    @Autowired
    ArticleService articleService;

    @Autowired
    NgramService ngramService;

    @Autowired
    FrequencyService freService;

    @Autowired
    TfidfService tfidfService;

    @Autowired
    TfIdfController tfIdfController;

    @Autowired
    HomeController homeController;


    @RequestMapping("/glogin")
    private String goHome(HttpSession session){

        if(session.isNew()){
            return "govhtml/glogin";
        }
        if(session.getAttribute("type")==null){
            return "govhtml/glogin";
        }
        if(session.getAttribute("type")=="PAG"){
            System.out.println(session.getAttribute("type"));
            return "govhtml/homepage";
        }
        if(session.getAttribute("type")=="LRA"){
            System.out.println(session.getAttribute("type"));
            return "govhtml/homepage";
        }
        if(session.getAttribute("type")=="LTO"){
            System.out.println(session.getAttribute("type"));
            return "govhtml/homepage";
        }
        if(session.getAttribute("type")=="SSS")
            System.out.println(session.getAttribute("type"));
            return "govhtml/homepage";

    }

    @RequestMapping("/govlogout")
    private String logout(){
        return "govhtml/glogin";
    }

    @RequestMapping("/govhomepage")
    private String homepage(Model model,ModelMap map,HttpSession session){
        String type = (String)session.getAttribute("type");
        List<Complaint> complaint = govtUserService.findByAgencyAndStatus(type,null);
//        System.out.println(complaint);
        System.out.println(type);
//      --------------------------------------------------------
        if(type.equals("PAG")){
            model.addAttribute("complaint",complaint);
            map.addAttribute("agency","PAG");
            map.addAttribute("img","/images/love.png");
            return "govhtml/homepage";
        }

        if(type.equals("LRA")){
            model.addAttribute("complaint",complaint);
            map.addAttribute("agency","LRA");
            map.addAttribute("img","/images/lra.png");
            return "govhtml/homepage";
        }
        if(type.equals("LTO")){
            model.addAttribute("complaint",complaint);
            map.addAttribute("agency","LTO");
            map.addAttribute("img","/images/lto.png");
            return "govhtml/homepage";
        }
        if(type.equals("SSS"))
            model.addAttribute("complaint",complaint);
            map.addAttribute("agency","SSS");
            map.addAttribute("img","/images/sss.png");
        return "govhtml/homepage";
    }

    @RequestMapping("govviewcomplaints")
    private String viewcomplaints(HttpSession session,Model model,ModelMap map){
        String type = (String)session.getAttribute("type");
        Long userid = (Long)session.getAttribute("userid");
        List<ComplaintReply> complaintReply = complaintReplyService.findByAgencyAndUserid(type,userid);

        if(type.equals("PAG")){
            model.addAttribute("viewcomplaint",complaintReply);
            map.addAttribute("img","/images/love.png");
            return "govhtml/viewcomplaints";
        }

        if(type.equals("LRA")){
            model.addAttribute("viewcomplaint",complaintReply);
            map.addAttribute("img","/images/lra.png");
            return "govhtml/viewcomplaints";
        }
        if(type.equals("LTO")){
            model.addAttribute("viewcomplaint",complaintReply);
            map.addAttribute("img","/images/lto.png");
            return "govhtml/viewcomplaints";
        }
        if(type.equals("SSS")) {
            model.addAttribute("viewcomplaint", complaintReply);
            map.addAttribute("img", "/images/sss.png");
            return "govhtml/viewcomplaints";
        }
        return "homepage";
    }



    @RequestMapping("govcorrection")
    private String correction(HttpServletRequest request,HttpSession session, Model model,ModelMap map,ComplaintReply complaintReply) throws IOException {

        String complaint = request.getParameter("complaint");
        String agency = request.getParameter("agency");
        String id = request.getParameter("id");
        long idd = Long.valueOf(id);



        Complaint complaint1 = complaintService.findByComplaintId(idd);
        complaint1.setAgency(agency);
//        complaint1.setTrainStatus("1");
//        complaint1.setTrain_status();
        complaintService.save(complaint1);


            Article article = new Article();
            article.setContent(complaint);
            article.setAgency(agency);
            article.setTitle("retrain");
            articleService.save(article);

        homeController.cleanContent(complaint);
        tfIdfController.TermFrequency();
        tfIdfController.wordcount();
        tfIdfController.InverseTermFrequency();
//        tfIdfController.clean();
        tfIdfController.TermFrequencyAndInverseTermFrequency();

            String type = (String) session.getAttribute("type");
            List<Complaint> complaint2 = govtUserService.findByAgencyAndStatus(type, null);
//      --------------------------------------------------------
            if (type.equals("PAG")) {
                model.addAttribute("complaint", complaint2);
                map.addAttribute("agency", "PAG");
                map.addAttribute("img", "/images/love.png");
                return "govhtml/homepage";
            }

            if (type.equals("LRA")) {
                model.addAttribute("complaint", complaint2);
                map.addAttribute("agency", "LRA");
                map.addAttribute("img", "/images/lra.png");
                return "govhtml/homepage";
            }
            if (type.equals("LTO")) {
                model.addAttribute("complaint", complaint2);
                map.addAttribute("agency", "LTO");
                map.addAttribute("img", "/images/lto.png");
                return "govhtml/homepage";
            }
            if (type.equals("SSS"))
                model.addAttribute("complaint", complaint2);
            map.addAttribute("agency", "SSS");
            map.addAttribute("img", "/images/sss.png");
            return "govhtml/homepage";
    }


    @RequestMapping("govreply")
    private String reply(HttpServletRequest request,HttpSession session, Model model,ModelMap map,ComplaintReply complaintReply) throws IOException {
//      --------------------get from html-----------------------
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm:ss");

        String complaintId = request.getParameter("complaintid");
        String agency = request.getParameter("complaintagency");
        String uid = request.getParameter("uid");
        Long uidd = Long.valueOf(uid);
        Long userid = (Long)session.getAttribute("userid");
        String reply = request.getParameter("replyy");
        String type = (String)session.getAttribute("type");
        String dateee = formatter.format(date);
        String time = formatter2.format(date);
        String status = null;

        String complaintt = request.getParameter("complaint");
        System.out.println(complaintt);

        Article article = new Article();
        article.setContent(complaintt);
        article.setAgency(agency);
        article.setTitle("train");
        articleService.save(article);

        homeController.cleanContent(complaintt);
        tfIdfController.TermFrequency();
        tfIdfController.wordcount();
        tfIdfController.InverseTermFrequency();
//        tfIdfController.clean();
        tfIdfController.TermFrequencyAndInverseTermFrequency();

//      --------------------save to database---------------------
        complaintReply.setComplaintId(Long.parseLong(complaintId));
        complaintReply.setComplaintReply(reply);
        complaintReply.setDate(dateee);
        complaintReply.setTime(time);
        complaintReply.setAgency(agency);
        complaintReply.setUserid(uidd);
        complaintReplyService.save(complaintReply);
//      --------------------------check data----------------------
        System.out.println("-----------COMPLAINT REPLY DETAILS-----------");
        System.out.println("Complaint id: "+complaintId);
        System.out.println("Reply: "+reply);
        System.out.println("Date: "+dateee);
        System.out.println("Time "+time);
        System.out.println("Type: "+type);
        Complaint complaint1 = govtUserService.findByComplaintId(Long.parseLong(complaintId));
        complaint1.setStatus("1");
        complaintService.save(complaint1);
        //      --------------------------getemail----------------------
//        User user = govtUserService.findByUserId(complaint1.getUserId());
        List<Complaint> complaint = govtUserService.findByAgencyAndStatus(type,status);
//        System.out.println(complaint);
//        List<Complaint> complaint2 = new ArrayList<>();

//        for(int i=0; i<complaint.size(); i++){
//            complaint2 = govtUserService.findByGovtAgency(complaint.get(i).getFile_path());
//        }
//        complaint.getClass()


//        String agency = request.getParameter("agency");
//        String id = request.getParameter("id");
//        long idd = Long.valueOf(id);

//        Complaint complaint2 = complaintService.findByComplaintId(Long.valueOf(complaintId));
//        complaint1.setAgency(agency);
//        complaintService.save(complaint1);
//        String complaint = request.getParameter("complaint");
//        String agencyy = request.getParameter("agency");
//        String id = request.getParameter("complaintid");

        if(type.equals("PAG")){
            model.addAttribute("complaint",complaint);
            map.addAttribute("agency","PAG");
            map.addAttribute("img","/images/love.png");
//            sendEmail(user.getEmail(),complaintReply.getComplaintReply(),"Divulgo: PAG-IBIG's feedback reply");
            return "govhtml/homepage";
        }
        if(type.equals("LRA")){
            model.addAttribute("complaint",complaint);
            map.addAttribute("agency","LRA");
            map.addAttribute("img","/images/lra.png");
//            sendEmail(user.getEmail(),complaintReply.getComplaintReply(),"Divulgo: LRA's feedback reply");
            return "govhtml/homepage";
        }
        if(type.equals("LTO")){
            model.addAttribute("complaint",complaint);
            map.addAttribute("agency","LTO");
            map.addAttribute("img","/images/lto.png");
//            sendEmail(user.getEmail(),complaintReply.getComplaintReply(),"Divulgo: LTO's feedback reply");
            return "govhtml/homepage";
        }
        if(type.equals("SSS"))
            model.addAttribute("complaint",complaint);
            map.addAttribute("agency","SSS");
            map.addAttribute("img","/images/sss.png");
//            sendEmail(user.getEmail(),complaintReply.getComplaintReply(),"Divulgo: SSS's feedback reply");
            return "govhtml/homepage";
    }

    @RequestMapping("govlogin")
    public String glogin(HttpServletRequest request, Model model, HttpSession session,ModelMap map){

        String type;
        System.out.println("------I GOT INSIDE THE LOGIN CONTROLLER-------");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        session.setAttribute("username",username);
        session.setAttribute("password",password);

        GovtUser user = govtUserService.findByUsernameAndPassword(username,password);
        type=user.getType();

        String status = null;

        List<Complaint> complaint = govtUserService.findByAgencyAndStatus(type,status);

        if(user==null){
            System.out.println("--------USER NOT FOUND-------");
            return "govhtml/glogin";
        }
        if (user.getType().equals("PAG")) {
            model.addAttribute("complaint",complaint);
            map.addAttribute("agency","PAG");
            map.addAttribute("img","/images/love.png");
            session.setAttribute("type",user.getType());
            session.setAttribute("stat",status);
            session.setAttribute("userid",user.getId());
//            session.setAttribute("complaint",complaint);
            return "govhtml/homepage";
        }
        if (user.getType().equals("LRA")) {
            model.addAttribute("complaint",complaint);
            map.addAttribute("agency","LRA");
            map.addAttribute("img","/images/lra.png");
            session.setAttribute("type",user.getType());
            session.setAttribute("stat",status);
            session.setAttribute("userid",user.getId());
            return "govhtml/homepage";
        }
        if (user.getType().equals("LTO")) {
          model.addAttribute("complaint",complaint);
            map.addAttribute("img","/images/lto.png");
            map.addAttribute("agency","LTO");
            session.setAttribute("type",user.getType());
            session.setAttribute("stat",status);
            session.setAttribute("userid",user.getId());
            return "govhtml/homepage";
        }
        if (user.getType().equals("SSS")) {
            model.addAttribute("complaint",complaint);
            map.addAttribute("img","/images/sss.png");
            map.addAttribute("agency","SSS");
            session.setAttribute("type",user.getType());
            session.setAttribute("stat",status);
            session.setAttribute("userid",user.getId());
            return "govhtml/homepage";
        }
        return null;
    }


}
