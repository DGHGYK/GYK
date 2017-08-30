package com.example.hatice.project.ModelG;

/**
 * Created by gncal on 8/3/2017.
 */

public class Invitee {

    private String lastProcess;
    private String id, name, phoneNumber;
    private InviteeStatus status;

    public Invitee(String id, String name, String phoneNumber, InviteeStatus status) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
