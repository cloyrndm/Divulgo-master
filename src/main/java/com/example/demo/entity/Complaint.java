package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * Created by Cloie Andrea on 01/10/2018.
 */
@Entity
@Table(name="complaint")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value={"createdAt","updatedAt"},allowGetters=true)
public class Complaint {

    @Id
    @GeneratedValue
    private Long complaintId;

    @Column(name = "user_id")
    private Long userId;

    private String date;

    private String time;

    private String user_complaint;

    private Double user_lat;

    private Double user_long;

    private String file_path;

    private String agency;

    private String status;

    private String trainStatus;

    private String address;

    private Integer sentimentRate;

//    private String


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTrainStatus() {
        return trainStatus;
    }

    public void setTrainStatus(String trainStatus) {
        this.trainStatus = trainStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(Long complaintId) {
        this.complaintId = complaintId;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    //    public String getGovtAgency() {
//        return govtAgency;
//    }
//
//    public void setGovtAgency(String govtAgency) {
//        this.govtAgency = govtAgency;
//    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getComplaint_id() {
        return complaintId;
    }

    public void setComplaint_id(Long complaint_id) {
        this.complaintId = complaint_id;
    }

    public String getUser_complaint() {
        return user_complaint;
    }

    public void setUser_complaint(String user_complaint) {
        this.user_complaint = user_complaint;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public Double getUser_lat() {
        return user_lat;
    }

    public void setUser_lat(Double user_lat) {
        this.user_lat = user_lat;
    }

    public Double getUser_long() {
        return user_long;
    }

    public void setUser_long(Double user_long) {
        this.user_long = user_long;
    }

    public Integer getSentimentRate() {
        return sentimentRate;
    }

    public void setSentimentRate(Integer sentimentRate) {
        this.sentimentRate = sentimentRate;
    }
}
