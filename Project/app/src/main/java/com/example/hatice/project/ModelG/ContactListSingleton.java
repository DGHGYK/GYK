package com.example.hatice.project.ModelG;

/**
 * Created by gamze on 20/08/2017.
 */

public class ContactListSingleton {

    private static ContactListSingleton inst = null;
    private ContactsList contactsList,filteredContactsList,selectedContactsList, lastSelectedContactsList= new ContactsList();

    private ContactListSingleton() {
    }

    public static ContactListSingleton getInst()
    {
        if(inst == null)
        {
            inst = new ContactListSingleton();
        }

        return inst;
    }

    public static void setInst(ContactListSingleton inst) {
        ContactListSingleton.inst = inst;
    }

    public ContactsList getContactsList() {
        if(contactsList!=null)
            return contactsList;
        return new ContactsList();
    }

    public void setContactsList(ContactsList contactsList) {
        this.contactsList = contactsList;
    }

    public ContactsList getFilteredContactsList() {
        if(filteredContactsList!=null)
            return filteredContactsList;
        return new ContactsList();
    }

    public void setFilteredContactsList(ContactsList filteredContactsList) {
        this.filteredContactsList = filteredContactsList;
    }

    public ContactsList getSelectedContactsList() {
        if(selectedContactsList!=null)
            return selectedContactsList;
        return new ContactsList();
    }

    public void setSelectedContactsList(ContactsList selectedContactsList) {
        this.selectedContactsList = selectedContactsList;
    }

    public ContactsList getLastSelectedContactsList() {
        if(lastSelectedContactsList!=null)
            return lastSelectedContactsList;
        return new ContactsList();
    }

    public void setLastSelectedContactsList(ContactsList lastSelectedContactsList) {
        this.lastSelectedContactsList = lastSelectedContactsList;
    }
}
