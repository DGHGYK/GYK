package com.inwhiter.inviteapp.project.BusinessG;


import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.inwhiter.inviteapp.project.ModelG.Contact;

import java.util.ArrayList;


public class ContactsLoader extends AsyncTask<String,Void,Void> {

    ContactsListAdapter contactsListAdapter;
    Context context;
    private ArrayList<Contact> tempContactHolder;
    public TextView txtProgress;
    int totalContactsCount,loadedContactsCount;


    public ContactsLoader(Context context, ContactsListAdapter contactsListAdapter){
        this.context = context;
        this.contactsListAdapter = contactsListAdapter;
        this.tempContactHolder= new ArrayList<>();
        loadedContactsCount=0;
        totalContactsCount=0;
        txtProgress=null;
    }

    @Override
    protected Void doInBackground(String[] filters) {
        String filter = filters[0];

        ContentResolver contentResolver = context.getContentResolver();

        Uri uri = ContactsContract.Contacts.CONTENT_URI;

        String[] projection = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.HAS_PHONE_NUMBER
        };
        Cursor cursor;
        //filtre sözcüğü doluysa ona göre query ile isminde fitre sözcüğü geçenleri getirir
        if(filter.length()>0) {
            cursor = contentResolver.query(
                    uri,
                    projection,
                    ContactsContract.Contacts.DISPLAY_NAME + " LIKE ?",
                    new String[]{filter},
                    ContactsContract.Contacts.DISPLAY_NAME + " ASC"
            );
        }else {

            cursor = contentResolver.query(
                    uri,
                    projection,
                    null,
                    null,
                    ContactsContract.Contacts.DISPLAY_NAME + " ASC"
            );

        }
        totalContactsCount = cursor.getCount();

        //queryden gelen veya rehberin tümünden gelen kişi listesi varsa
        if(cursor!=null && cursor.getCount()>0){

            while(cursor.moveToNext()) {

                //telefon numarası alanı boş olmayanların idlerini alarak tekrar sorgu atıp diğer bilgilerini çeker
                if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {

                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));


                    Cursor phoneCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                            new String[]{id},
                            null
                    );

                    if (phoneCursor != null && phoneCursor.getCount() > 0) {

                        while (phoneCursor.moveToNext()) {
                            String phId = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));

                            String customLabel = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.LABEL));

                            String label = (String) ContactsContract.CommonDataKinds.Phone.getTypeLabel(context.getResources(),
                                    phoneCursor.getInt(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE)),
                                    customLabel
                            );

                            String phNo = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));


                            tempContactHolder.add(new Contact(phId, name, phNo, label));



                        }
                        phoneCursor.close();

                    }

                }
                loadedContactsCount++;

                publishProgress();


            }
            cursor.close();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Void[] v){

        //rehber yükleme sırasında temp listedeki değerlerin sayısı 100ü geçerse asıl listeye atıp temp listeyi boşaltır
        if(this.tempContactHolder.size()>=100) {
            contactsListAdapter.addContacts(tempContactHolder);

            this.tempContactHolder.clear();

            if(txtProgress!=null){
                txtProgress.setVisibility(View.VISIBLE);
                //rehberin yüzde kaçının yüklendiği bilgisini ekrana yazar
                String progressMessage = "Yükleniyor...("+loadedContactsCount+"/"+totalContactsCount+")";
                txtProgress.setText(progressMessage);
                Log.d("Visibility", String.valueOf(txtProgress.getVisibility()));
            }

        }

    }

    @Override
    protected void onPostExecute(Void v){

        //temp listedekileri gerçek listeye aktarır, temp listeyi siler
        contactsListAdapter.addContacts(tempContactHolder);
        tempContactHolder.clear();


        if(txtProgress!=null) {
            txtProgress.setText("");
            txtProgress.setVisibility(View.GONE);
        }
    }
}
