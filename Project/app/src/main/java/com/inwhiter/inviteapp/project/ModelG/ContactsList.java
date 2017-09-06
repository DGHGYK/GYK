package com.inwhiter.inviteapp.project.ModelG;

import android.telephony.PhoneNumberUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by gncal on 7/31/2017.
 */

public class ContactsList {

    public ArrayList<Contact> contactArrayList;

    public ContactsList(){

        contactArrayList = new ArrayList<>();
    }

    public ContactsList(ArrayList<Contact> contactArrayList){
        this.contactArrayList=contactArrayList;
    }



    public int getCount(){

        return contactArrayList.size();
    }

    public void addContact(Contact contact){
        contactArrayList.add(contact);
    }

    public  void removeContact(Contact contact){
        contactArrayList.remove(contact);
    }

    public void removeContactsByPhoneNumbers(List<Invitee> invitees){
        for (Iterator<Contact> it = contactArrayList.iterator(); it.hasNext();) {
            Contact con = it.next();
            for (int i = 0; i < invitees.size(); i++) {
                if (PhoneNumberUtils.compare(con.getPhoneNumber(),invitees.get(i).getPhoneNumber())){
                    it.remove();

                }
            }
            System.out.println(con.getName()+" SİLİNDİ-------------");
        }

    }

    public void removeContactByPhoneNumber(String number){
        for (Iterator<Contact> it = contactArrayList.iterator(); it.hasNext();) {
            Contact con = it.next();
                if (PhoneNumberUtils.compare(con.getPhoneNumber(),number)){
                    it.remove();
            }
            System.out.println(con.getName()+" SİLİNDİ-------------");
        }

    }

    public Contact getContact(int i){
        return contactArrayList.get(i);
    }

    public Contact getContactById(int id){

        for(int i=0;i<this.getCount();i++){
            if(Integer.parseInt(contactArrayList.get(i).getId())==id)
                return contactArrayList.get(i);
        }

        return null;
    }


}
