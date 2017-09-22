package com.inwhiter.inviteapp.project.ActivityG;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.inwhiter.inviteapp.project.BusinessG.ContactsListAdapter;
import com.inwhiter.inviteapp.project.BusinessG.ContactsLoader;
import com.inwhiter.inviteapp.project.ModelG.ContactListSingleton;
import com.inwhiter.inviteapp.project.R;

/**
 * Created by gncal on 7/30/2017.
 * Androidde rehberden çoklu seçim yapılabilen hazır bir yapı yok. Bunun için
 * rehberi kendi uygulamamıza aktarıp bir list view içinde gösterip kullanıcıya
 * kişileri seçtirmemiz gerekiyor.
 */

public class ContactsPickerActivity extends AppCompatActivity {

    ListView contactsChooser;
    Button btnDone;
    EditText txtFilter;
    TextView txtLoadInfo;
    ContactsListAdapter contactsListAdapter;
    ContactsLoader contactsLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_picker);
        String titlem=getSupportActionBar().getTitle().toString();

        SpannableString s = new SpannableString(titlem);
        s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimaryDark)), 0, titlem.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(s);

        //kişi seç butonu
        contactsChooser = (ListView) findViewById(R.id.lst_contacts_chooser);
        //seçilen kişileri davetli listesine ekle butonu
        btnDone = (Button) findViewById(R.id.btn_done);
        //rehberdeki kişileri filtrelemek için arama butonu
        txtFilter = (EditText) findViewById(R.id.txt_filter);
        //Rehberdeki kişiler yüklenene kadar Yükleniyor yazısının görünmesi için
        txtLoadInfo = (TextView) findViewById(R.id.txt_load_progress);

        //layoutta rehber kişilerinin gösterilmesi
        showContacts();


        txtFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                contactsListAdapter.filter(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(contactsListAdapter.selectedContactsList.contactArrayList.isEmpty()){
                    setResult(RESULT_CANCELED);
                }
                else{

                    //Intent resultIntent = new Intent();

                    //resultIntent.putParcelableArrayListExtra("SelectedContacts", contactsListAdapter.selectedContactsList.contactArrayList);
                    setResult(RESULT_OK);

                }
                finish();

            }
        });
    }



    private void loadContacts(String filter){

        if(contactsLoader!=null && contactsLoader.getStatus()!= AsyncTask.Status.FINISHED){
            try{
                contactsLoader.cancel(true);
            }catch (Exception e){

            }
        }
        if(filter==null) filter="";

        try{
            //Asenkron olarak rehber loaderı çalıştırır
            if (ContactListSingleton.getInst().getContactsList().getCount() == 0)
            {
                contactsLoader = new ContactsLoader(this,contactsListAdapter);
                contactsLoader.txtProgress = txtLoadInfo;
                contactsLoader.execute(filter);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;


    private void showContacts() {
        //Marshmallow sürümünde ve sonrasında kullanıcıdan izin alınması gerekiyor
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //bu satırdan sonra onRequestPermissionResult metodu çalışır
        } else {
            //izinler tamamsa contactListAdapter ile rehberdeki kişileri yüklüyoruz
            contactsListAdapter = new ContactsListAdapter(this);
            contactsChooser.setAdapter(contactsListAdapter);
            loadContacts("");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        //izin verildiyse showContacts else bloğu çalışır, verilmediyse ulaşılamayacağı toast mesajında gösterilir
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showContacts();
            } else {
                Toast.makeText(this, "Rehbere ulaşma izni verene kadar rehberdeki kişileri listeleyemezsiniz.", Toast.LENGTH_SHORT).show();
            }
        }
    }


}




