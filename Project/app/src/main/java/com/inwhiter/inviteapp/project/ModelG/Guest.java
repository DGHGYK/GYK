package com.inwhiter.inviteapp.project.ModelG;

/**
 * Created by gncal on 8/3/2017.
 */

public class Guest {

    private String guestId;
    private String inviteId;

    private int lastProcess;
    private String name, phoneNumber;
    private GuestStatus status;

    public Guest() {
    }

    public Guest(String guestId, String inviteId, int lastProcess, String name, String phoneNumber, GuestStatus status) {
        this.guestId = guestId;
        this.inviteId = inviteId;
        this.lastProcess = lastProcess;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

    public String getGuestId() {
        return guestId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
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

    public GuestStatus getStatus() {
        if(status==null){
            status = new GuestStatus(false, false, false, null,null,"",0,false,true);
        }
        return status;
    }

    public void setStatus(GuestStatus status) {
        this.status = status;
    }
}
