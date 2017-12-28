package com.inwhiter.inviteapp.project.Fragment.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inwhiter.inviteapp.project.BusinessG.ContactsListAdapter;
import com.inwhiter.inviteapp.project.BusinessG.ContactsLoader;
import com.inwhiter.inviteapp.project.Fragment.BaseFragment;
import com.inwhiter.inviteapp.project.Fragment.FragmentController;
import com.inwhiter.inviteapp.project.ModelG.Contact;
import com.inwhiter.inviteapp.project.ModelG.ContactsList;
import com.inwhiter.inviteapp.project.ModelG.Guest;
import com.inwhiter.inviteapp.project.ModelG.GuestListSingleton;
import com.inwhiter.inviteapp.project.ModelG.GuestStatus;
import com.inwhiter.inviteapp.project.R;

public class ContactsPickerFragment extends BaseFragment implements ContactsListAdapter.AdapterCallback {


    ListView contactsChooser;
    Button btnDone;
    EditText txtFilter;
    TextView txtLoadInfo;
    ContactsListAdapter contactsListAdapter;
    ContactsLoader contactsLoader;
    static String inviteId;
    public ContactsList selectedContactsList,lastSelectedContactsList;

   /*  if(contact!=null && isChecked){
        selectedContactsList.addContact(contact);
        lastSelectedContactsList.addContact(contact);
    }
                else if(contact!=null && !isChecked){
        selectedContactsList.removeContact(contact);
        lastSelectedContactsList.removeContact(contact);
    }
*/


    @Override
    protected int getFID() {
        return R.layout.activity_contacts_picker;
    }


    //hangi davetiyede işlem yapılacağını bilmek için inviteId taşınır
    public static ContactsPickerFragment newInstance(Bundle args) {
        ContactsPickerFragment fragment = new ContactsPickerFragment();
        inviteId= args.getString("inviteId");
        return fragment;
    }

    @Override
    protected void init() {
        //Eskiden seçilmiş davetliler rehberde seçili geleceğinden yüklenir
        selectedContactsList = new ContactsList();
        getOldContacts();
        //En sn seçilenler ayrıca kaydedileceğinden ayrı bir listede tutulur
        lastSelectedContactsList = new ContactsList();

        //kişi seç butonu
        contactsChooser = (ListView) getActivity().findViewById(R.id.lst_contacts_chooser);
        //seçilen kişileri davetli listesine ekle butonu
        btnDone = (Button) getActivity().findViewById(R.id.btn_done);
        //rehberdeki kişileri filtrelemek için arama butonu
        txtFilter = (EditText) getActivity().findViewById(R.id.txt_filter);
        //Rehberdeki kişiler yüklenene kadar Yükleniyor yazısının görünmesi için
        txtLoadInfo = (TextView) getActivity().findViewById(R.id.txt_load_progress);
    }

    @Override
    protected void handlers() {
        //layoutta rehber kişilerinin gösterilmesi
        showContacts();

        //aramada girilen texte göre rehberin filtrelenmesi
        txtFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                contactsListAdapter.filter(s.toString());
                contactsListAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        //davetliler listesine ekle butonuna basılınca olacaklar
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //davetli kişi verisi veritabanına kaydedilir
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference guestRef = database.getReference("guest");
                String guestId = guestRef.push().getKey();
              for(Contact c :lastSelectedContactsList.contactArrayList) {
                    Guest in = new Guest(guestId, inviteId, 0, c.getName(), c.getPhoneNumber(), new GuestStatus());
                    guestRef.child(guestId).setValue(in);
                    DatabaseReference inviteRef = database.getReference("invite").child(inviteId).child("guestIds");
                    inviteRef.child(guestId).setValue(true);
                }
                Bundle bundle= new Bundle();
                bundle.putString("inviteId", inviteId);
                listener.changeFragment(FragmentController.GUEST, bundle);

            }
        });

    }

    private void loadContacts(String filter){
        try{
        if(contactsLoader!=null && contactsLoader.getStatus()!= AsyncTask.Status.FINISHED){
                contactsLoader.cancel(true);
        }
        if(filter==null) filter="";
            //Asenkron olarak rehber loaderı çalıştırır
                contactsLoader = new ContactsLoader(getActivity(),contactsListAdapter);
                contactsLoader.txtProgress = txtLoadInfo;
                contactsLoader.execute(filter);
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;


    private void showContacts() {
        //Marshmallow sürümünde ve sonrasında kullanıcıdan izin alınması gerekiyor
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getActivity().checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //bu satırdan sonra onRequestPermissionResult metodu çalışır
        } else {
            //izinler tamamsa contactListAdapter ile rehberdeki kişileri yüklüyoruz
            contactsListAdapter = new ContactsListAdapter(getActivity(),selectedContactsList, ContactsPickerFragment.this);
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
                Toast.makeText(getActivity(), "Rehbere ulaşma izni verene kadar rehberdeki kişileri listeleyemezsiniz.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void getOldContacts(){
        for (Guest g: GuestListSingleton.getInst().getGuestList()) {
            Contact c = new Contact(null,g.getName(),g.getPhoneNumber(), null);
            selectedContactsList.addContact(c);
        }
    }

    @Override
    public void onItemChecked(Contact c) {
        selectedContactsList.addContact(c);
        lastSelectedContactsList.addContact(c);

        Log.d("position...............", c.getName());

    }
}
