package com.inwhiter.inviteapp.project.ModelG;

import android.telephony.PhoneNumberUtils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gamze on 25/08/2017.
 */

public class InviteeListSingleton {
    private static InviteeListSingleton inst = null;
    private List<Invitee> inviteeList= new ArrayList<>();

    private InviteeListSingleton() {
    }

    public static InviteeListSingleton getInst()
    {
        if(inst == null)
        {
            inst = new InviteeListSingleton();
        }

        return inst;
    }

    public static void setInst(InviteeListSingleton inst) {
        InviteeListSingleton.inst = inst;
    }


    public void setInviteeList(List<Invitee> inviteeList) {
        this.inviteeList = inviteeList;
    }

    public List<Invitee> getInviteeList() {
        if(inviteeList!=null)
            return inviteeList;
        return new ArrayList<Invitee>();
    }


    public int getCount(){

        return inviteeList.size();
    }

    public void addInvitee(Invitee invitee){
        inviteeList.add(invitee);
    }
    public void removeInvitee(Invitee invitee){

        inviteeList.remove(invitee);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference inviteeRef = database.getReference("invitee");
        inviteeRef.child(invitee.getInviteeId()).removeValue();
    }

    public void removeAllInvitees(List<Invitee> invitees){
        inviteeList.removeAll(invitees);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference inviteeRef = database.getReference("invitee");

        for (Invitee in: invitees) {
            inviteeRef.child(in.getInviteeId()).removeValue();
        }

    }

    public boolean isSameNumber(String phoneNumber){
        for (Invitee in: inviteeList) {
            if(PhoneNumberUtils.compare(in.getPhoneNumber(),phoneNumber)){
                return true;
            }
            /*if(in.getPhoneNumber().equals(phoneNumber)){
                return true;
            }*/
        }
        return false;
    }

}
