package com.inwhiter.inviteapp.project.ModelG;

import java.util.Date;

/**
 * Created by gncal on 8/2/2017.
 */

public class GuestStatus {

    private boolean sended, viewed, answered;
    private Date sendDate, answerDate;
    private String answer;
    private int numberOfPeople;
    private boolean hardcopy, softcopy;


    public GuestStatus(boolean sended, boolean viewed, boolean answered, Date sendDate, Date answerDate, String answer, int numberOfPeople, boolean hardcopy, boolean softcopy) {
        this.sended = sended;
        this.viewed = viewed;
        this.answered = answered;
        this.sendDate = sendDate;
        this.answerDate = answerDate;
        this.answer = answer;
        this.numberOfPeople = numberOfPeople;
        this.hardcopy = hardcopy;
        this.softcopy = softcopy;
    }

    public GuestStatus() {
        this.sended = false;
        this.viewed = false;
        this.answered = false;
        this.sendDate = null;
        this.answerDate = null;
        this.answer = null;
        this.numberOfPeople = 0;
        this.hardcopy = false;
        this.softcopy = true;
    }

    public boolean isSended() {
        return sended;
    }

    public void setSended(boolean sended) {
        this.sended = sended;
    }

    public boolean isViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Date getAnswerDate() {
        return answerDate;
    }

    public void setAnswerDate(Date answerDate) {
        this.answerDate = answerDate;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public boolean isHardcopy() {
        return hardcopy;
    }

    public void setHardcopy(boolean hardcopy) {
        this.hardcopy = hardcopy;
    }

    public boolean isSoftcopy() {
        return softcopy;
    }

    public void setSoftcopy(boolean softcopy) {
        this.softcopy = softcopy;
    }
}
