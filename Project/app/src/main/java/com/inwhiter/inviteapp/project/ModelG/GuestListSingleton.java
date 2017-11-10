package com.inwhiter.inviteapp.project.ModelG;

import android.telephony.PhoneNumberUtils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gamze on 25/08/2017.
 */

public class GuestListSingleton {
    private static GuestListSingleton inst = null;
    private List<Guest> guestList= new ArrayList<>();

    private GuestListSingleton() {
    }

    public static GuestListSingleton getInst()
    {
        if(inst == null)
        {
            inst = new GuestListSingleton();
        }

        return inst;
    }

    public static void setInst(GuestListSingleton inst) {
        GuestListSingleton.inst = inst;
    }


    public void setGuestList(List<Guest> guestList) {
        this.guestList = guestList;
    }

    public List<Guest> getGuestList() {
        if(guestList!=null)
            return guestList;
        return new ArrayList<Guest>();
    }


    public int getCount(){

        return guestList.size();
    }

    public void addguest(Guest guest){
        guestList.add(guest);
    }
    public void removeguest(Guest guest){

        guestList.remove(guest);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference guestRef = database.getReference("guest");
        guestRef.child(guest.getGuestId()).removeValue();
    }

    public void removeAllguests(){
        guestList.removeAll(guestList);
      /*  guestList.removeAll(guests);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference guestRef = database.getReference("guest");

        for (Guest in: guests) {
            guestRef.child(in.getGuestId()).removeValue();
        }*/

    }

    public boolean isSameNumber(String phoneNumber){
        for (Guest in: guestList) {
            if(PhoneNumberUtils.compare(in.getPhoneNumber(),phoneNumber)){
                return true;
            }
            /*if(in.getPhoneNumber().equals(phoneNumber)){
                return true;
            }*/
        }
        return false;
    }

    public int alreadyAdded(String guestId){
        for (Guest g: guestList) {
            if(g.getGuestId().equals(guestId)){
                return guestList.indexOf(g);
            }
        }
        return 0;
    }

}
