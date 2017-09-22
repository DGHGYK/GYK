package com.inwhiter.inviteapp.project.ModelG;

/**
 * Created by gncal on 8/3/2017.
 */

public class Invitee {

    private String inviteeId;
    private String inviteId;

    private int lastProcess;
    private String name, phoneNumber;
    private InviteeStatus status;

    public Invitee() {
    }

    public Invitee(String inviteeId, String inviteId, int lastProcess, String name, String phoneNumber, InviteeStatus status) {
        this.inviteeId = inviteeId;
        this.inviteId = inviteId;
        this.lastProcess = lastProcess;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

    public String getInviteeId() {
        return inviteeId;
    }

    public void setInviteeId(String inviteeId) {
        this.inviteeId = inviteeId;
    }

    public String getInviteId() {
        return inviteId;
    }

    public void setInviteId(String inviteId) {
        this.inviteId = inviteId;
    }

    public int getLastProcess() {
        return lastProcess;
    }

    public void setLastProcess(int lastProcess) {
        this.lastProcess = lastProcess;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public InviteeStatus getStatus() {
        if(status==null){
            status = new InviteeStatus(false, false, false, null,null,"",0,false,true);
        }
        return status;
    }

    public void setStatus(InviteeStatus status) {
        this.status = status;
    }
}
