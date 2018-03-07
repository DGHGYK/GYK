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
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference inviteRef = database.getReference("invite");
    final DatabaseReference guestRef = database.getReference("guest");

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


    public List<Guest> getGuestList() {
        if(guestList!=null)
            return guestList;
        return new ArrayList<Guest>();
    }

    public void addGuest(Guest guest){
        guestList.add(guest);
    }


    public void deleteGuest(Guest guest, String inviteId){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference guestRef = database.getReference("guest");
        guestRef.child(guest.getGuestId()).removeValue();

        //guestIds listesinde davetli listesinden silinenlerin değeri -1 olarak görünür
        DatabaseReference inviteRef = database.getReference("invite");
        inviteRef.child(inviteId).child("guestIds").child(guest.getGuestId()).setValue(-1);
        guestList.remove(guest);
    }

    public void deleteAllGuests(String inviteId, List<Guest> guests){
        for (Guest g: guests) {
            deleteGuest(g, inviteId);
        }
    }

    public void removeAllGuests(){
        guestList.removeAll(guestList);
    }

    public boolean isSameNumber(String phoneNumber){
        for (Guest in: guestList) {
            if(PhoneNumberUtils.compare(in.getPhoneNumber(),phoneNumber)){
                return true;
            }
        }
        return false;
    }

    public int alreadyAdded(String guestId){
        for (Guest g: guestList) {
            if(g.getGuestId().equals(guestId)){
                return guestList.indexOf(g);
            }
        }
        return -1;
    }

}
