package com.inwhiter.inviteapp.project.ModelG;

import java.util.Date;

/**
 * Created by gamze on 05/09/2017.
 */

public class Invite {
    private String inviteId;
    private String userId;
    private Info info;
    private int status; //aktif, aktif değil
    private String view;
    private String mood;
    private int numberOfAnswers;
    private Date createdDate;



    public Invite(String userId, Info info, int status, String view, String mood, Date createdDate) {
        this.userId = userId;
        this.info = info;
        this.status = status;
        this.view = view;
        this.mood = mood;
        this.numberOfAnswers =0;
        this.createdDate =createdDate;
    }

    public String getInviteId() {
        return inviteId;
    }

    public void setInviteId(String inviteId) {
        this.inviteId = inviteId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public int getNumberOfAnswers() {
        return numberOfAnswers;
    }

    public void setNumberOfAnswers(int numberOfAnswers) {
        this.numberOfAnswers = numberOfAnswers;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
