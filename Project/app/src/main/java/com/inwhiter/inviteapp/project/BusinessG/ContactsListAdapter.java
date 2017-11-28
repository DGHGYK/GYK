package com.inwhiter.inviteapp.project.BusinessG;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.inwhiter.inviteapp.project.ModelG.Contact;
import com.inwhiter.inviteapp.project.ModelG.ContactListSingleton;
import com.inwhiter.inviteapp.project.ModelG.ContactsList;
import com.inwhiter.inviteapp.project.R;

import java.util.ArrayList;

/**
 * Created by gncal on 7/30/2017.
 * Rehberden çektiğimiz kişileri kendi listemize atabilmek için adapter.
 * Kullanıcı, kişilerin adını, telefon numarasını, telefon numarası türünü
 * görüp seçeceği kişilerin checkboxını işaretleyebilir.
 *
 * liste için layout....activity_contacts_picker
 * liste elemanları için layout.....contact_item
 */

public class ContactsListAdapter extends BaseAdapter {

    Context context;
    public ContactsList contactsList,filteredContactsList,selectedContactsList,lastSelectedContactsList;
    String filterContactName;
    ContactListSingleton contactListSingleton;

    public ContactsListAdapter(Context context){
        super();
        this.context = context;
        this.filterContactName = "";
        filteredContactsList = new ContactsList();
        selectedContactsList = ContactListSingleton.getInst().getSelectedContactsList();

        lastSelectedContactsList= new ContactsList();
        contactsList= new ContactsList();
    }

    public void filter(String filterContactName){
        //filtrelemek için bir text girilmişse ona uygun sonuçlar çekilir
        filteredContactsList.contactArrayList.clear();

        //filtre sözcüğü boşsa bütün liste getirilir.
        if(filterContactName.isEmpty() || filterContactName.length()<1){
            filteredContactsList.contactArrayList.addAll(contactsList.contactArrayList);
            this.filterContactName = "";

        }
        else {
            //filtreleme sözcüğü doluysa kişi adında sözcük geçenleri getirir
            this.filterContactName = filterContactName.toLowerCase().trim();
            for (int i = 0; i < contactsList.contactArrayList.size(); i++) {

                if (contactsList.contactArrayList.get(i).getName().toLowerCase().contains(filterContactName))
                    filteredContactsList.addContact(contactsList.contactArrayList.get(i));
            }
        }

    }

    public void addContacts(ArrayList<Contact> contacts){
        this.contactsList.contactArrayList.addAll(contacts);
        this.filter(this.filterContactName);

    }

    @Override
    public int getCount() {
        return filteredContactsList.getCount();
    }

    @Override
    public Contact getItem(int position) {
        return filteredContactsList.contactArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(this.getItem(position).getId());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if(convertView==null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();

            convertView = inflater.inflate(R.layout.contact_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.chkContact = (CheckBox) convertView.findViewById(R.id.chk_contact);
            viewHolder.contactName = (TextView) convertView.findViewById(R.id.tv_contactName);
            viewHolder.contactNumber = (TextView) convertView.findViewById(R.id.tv_contactNumber);

            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.contactName.setText(this.filteredContactsList.contactArrayList.get(position).getName());
        viewHolder.contactNumber.setText(this.filteredContactsList.contactArrayList.get(position).getLabel()+" : "+this.filteredContactsList.contactArrayList.get(position).getPhoneNumber());
        viewHolder.chkContact.setId(Integer.parseInt(this.filteredContactsList.contactArrayList.get(position).getId()));
        if(alreadySelected(filteredContactsList.contactArrayList.get(position))) {
            viewHolder.chkContact.setChecked(true);
            viewHolder.chkContact.setEnabled(false);
        }

        viewHolder.chkContact.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Contact contact = filteredContactsList.getContactById(buttonView.getId());

                if(contact!=null && isChecked){
                    selectedContactsList.addContact(contact);
                    lastSelectedContactsList.addContact(contact);
                }
                else if(contact!=null && !isChecked){
                    selectedContactsList.removeContact(contact);
                    lastSelectedContactsList.removeContact(contact);
                }
            }
        });

        contactListSingleton = ContactListSingleton.getInst();
        contactListSingleton.setContactsList(this.contactsList);
        contactListSingleton.setFilteredContactsList(this.filteredContactsList);
        contactListSingleton.setSelectedContactsList(this.selectedContactsList);
        contactListSingleton.setLastSelectedContactsList(this.lastSelectedContactsList);
        return convertView;
    }

    public boolean alreadySelected(Contact contact)
    {
        ContactsList selected=   ContactListSingleton.getInst().getSelectedContactsList();
        if(selected!=null && selected.getContactById(Integer.parseInt(contact.getId()))!=null)
            return true;

        return false;
    }

    public static class ViewHolder{

        CheckBox chkContact;
        TextView contactName;
        TextView contactNumber;
    }
}
