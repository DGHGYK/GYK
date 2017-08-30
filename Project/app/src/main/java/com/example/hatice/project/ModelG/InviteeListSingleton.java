package com.example.hatice.project.ModelG;

import android.telephony.PhoneNumberUtils;

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
