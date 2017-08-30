package com.example.hatice.project.ModelG;

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

    public void removeContactsByIds(List<String> ids){
        for (Iterator<Contact> it = contactArrayList.iterator(); it.hasNext();) {
            Contact con = it.next();
            for (int i = 0; i < ids.size(); i++) {
                if (con.getId().equals(ids.get(i))) {
                    it.remove();
                }
            }
            System.out.println(con.getName()+" SİLİNDİ-------------");
        }

    }

    public void removeContactById(String id){
        for (Iterator<Contact> it = contactArrayList.iterator(); it.hasNext();) {
            Contact con = it.next();
                if (con.getId().equals(id)) {
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
