package com.inwhiter.inviteapp.project.ActivityG;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inwhiter.inviteapp.project.ActivityH.MenuActivity;
import com.inwhiter.inviteapp.project.BusinessG.GuestListAdapter;
import com.inwhiter.inviteapp.project.BusinessG.SendSMS;
import com.inwhiter.inviteapp.project.ModelG.Contact;
import com.inwhiter.inviteapp.project.ModelG.ContactListSingleton;
import com.inwhiter.inviteapp.project.ModelG.Guest;
import com.inwhiter.inviteapp.project.ModelG.GuestListSingleton;
import com.inwhiter.inviteapp.project.ModelG.GuestStatus;
import com.inwhiter.inviteapp.project.R;

import java.util.ArrayList;
import java.util.List;

public class GuestActivity extends AppCompatActivity {

    ExpandableListView guest_expandable;
    GuestListAdapter guest_adapter;

    Button pickContacts;
    Button sendSMS;
    CheckBox checkAll;
    Button deleteguest;
    Button addManually;
    String inviteId;
    final int CONTACT_PICK_REQUEST = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);
        String titlem=getSupportActionBar().getTitle().toString();

        Bundle bundle= getIntent().getExtras();
        if(bundle!=null){
            inviteId = bundle.getString("inviteId");
        }


        SpannableString s = new SpannableString(titlem);
        s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimaryDark)), 0, titlem.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(s);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar_invite);
        View customSS =getSupportActionBar().getCustomView();
        ImageButton home=(ImageButton)customSS.findViewById(R.id.ib_actionbarInvite_home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuestActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });


        guest_expandable = (ExpandableListView) findViewById(R.id.lv_guest_expandable);
        pickContacts = (Button) findViewById(R.id.iv_guest_pickContacts);
        sendSMS = (Button) findViewById(R.id.bt_guest_send);
        checkAll = (CheckBox) findViewById(R.id.cb_guest_checkAll);
        deleteguest = (Button) findViewById(R.id.bt_guest_delete);
        addManually = (Button) findViewById(R.id.bt_guest_addManually);

        if(guest_expandable.getChildCount()>0){
            checkAll.setVisibility(View.VISIBLE);
        }else{
            checkAll.setVisibility(View.INVISIBLE);
        }



        guest_expandable.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        ((Guest)guest_adapter.getGroup(groupPosition)).getName()+ " List Expanded.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        guest_expandable.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        ((Guest)guest_adapter.getGroup(groupPosition)).getName() + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();

            }
        });

        guest_expandable.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        ((Guest)guest_adapter.getGroup(groupPosition)).getName()
                                + " -> "
                                + ((GuestStatus)guest_adapter.getChild(groupPosition,childPosition)).isAnswered(), Toast.LENGTH_SHORT
                ).show();
                return false;
            }
        });

        addManually.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuestActivity.this, AddManuallyActivity.class);
                intent.putExtra("inviteId", inviteId);
                startActivity(intent);
            }
        });


        //rehberden kişi ekleme için rehberden çekilen kişi listesinin açılması
        pickContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentContactPick = new Intent(GuestActivity.this,ContactsPickerActivity.class);
                GuestActivity.this.startActivityForResult(intentContactPick,CONTACT_PICK_REQUEST);
            }
        });

        //üstteki checkboxa basılnıca tüm kişilerin işaretlenmesi, işaret silinince tüm kişilerden silinmesi
        checkAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for(int i=0; i < guest_expandable.getChildCount(); i++){
                    ConstraintLayout itemLayout = (ConstraintLayout) guest_expandable.getChildAt(i);
                    CheckBox cb = (CheckBox)itemLayout.findViewById(R.id.cb_item_check);
                    cb.setChecked(isChecked);
                }
            }
        });

        sendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final List<String> checkedIdList=new ArrayList<>();
                final List<Guest> checkedguests = new ArrayList<>();
                for(int i=0; i < guest_expandable.getChildCount(); i++) {
                    ConstraintLayout itemLayout = (ConstraintLayout) guest_expandable.getChildAt(i);
                    CheckBox cb = (CheckBox) itemLayout.findViewById(R.id.cb_item_check);

                    if (cb.isChecked()) {
                        checkedguests.add(GuestListSingleton.getInst().getGuestList().get(i));
                    }
                }

                if(checkedguests!=null && checkedguests.size()>0){

                    AlertDialog.Builder sendCheckedListDialog = new AlertDialog.Builder(GuestActivity.this);
                    sendCheckedListDialog.setMessage("Seçili davetlilere davetiye göndermek istediğinizden emin misiniz?");
                    sendCheckedListDialog.setCancelable(true);
                    sendCheckedListDialog.setPositiveButton(
                            "Evet",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    new SendSMS().execute(checkedguests);
                                    Toast.makeText(getBaseContext(), "Davetiyeleriniz seçilen davetlilere SMS ile iletilmiştir.", Toast.LENGTH_LONG).show();
                                    dialog.cancel();

                                }
                            });

                    sendCheckedListDialog.setNegativeButton(
                            "Hayır",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Log.d(GuestActivity.this.getLocalClassName(), "Davetiyeler gönderilmedi");
                                    dialog.cancel();
                                }
                            });

                    AlertDialog sendCheckedListAlert = sendCheckedListDialog.create();
                    sendCheckedListAlert.show();


                }else {
                    Toast.makeText(getBaseContext(), "Seçili davetli bulunamadı.", Toast.LENGTH_SHORT).show();
                }

            }
        });


        deleteguest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final List<Guest> checkedguests = new ArrayList<>();
                for(int i=0; i < guest_expandable.getChildCount(); i++) {
                    ConstraintLayout itemLayout = (ConstraintLayout) guest_expandable.getChildAt(i);
                    CheckBox cb = (CheckBox) itemLayout.findViewById(R.id.cb_item_check);
                    TextView tv = (TextView) itemLayout.findViewById(R.id.tv_item_id);

                    if (cb.isChecked()) {
                        checkedguests.add(GuestListSingleton.getInst().getGuestList().get(i));

                    }
                }

                    if(checkedguests!=null && checkedguests.size()>0){

                            AlertDialog.Builder removeCheckedDialog = new AlertDialog.Builder(GuestActivity.this);
                            removeCheckedDialog.setMessage("Seçili davetlileri silmek istediğinizden emin misiniz?");
                            removeCheckedDialog.setCancelable(true);
                            removeCheckedDialog.setPositiveButton(
                                    "Evet",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            ContactListSingleton.getInst().getSelectedContactsList().removeContactsByPhoneNumbers(checkedguests);
                                            GuestListSingleton.getInst().removeAllguests();
                                            guest_adapter.notifyDataSetChanged();

                                            // initList();
                                            dialog.cancel();
                                        }
                                    });

                            removeCheckedDialog.setNegativeButton(
                                    "Hayır",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            Log.d(GuestActivity.this.getLocalClassName(), "Davetli silinmedi");
                                            dialog.cancel();
                                        }
                                    });

                            AlertDialog removeCheckedAlert = removeCheckedDialog.create();
                            removeCheckedAlert.show();
                        }else {
                            Toast.makeText(getBaseContext(), "Seçili davetli bulunamadı.", Toast.LENGTH_SHORT).show();
                        }

            }
        });

        /*FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference guestRef = database.getReference("guest");
        Query q =guestRef.orderByChild("inviteId").equalTo(TemplateActivity.inviteId);
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot i : dataSnapshot.getChildren()) {
                        // do something with the individual "issues"
                        for (Guest in: GuestListSingleton.getInst().getGuestList())
                            if(in.getName().equals(i.getValue(Guest.class).getName()) )
                              {
                                in.setStatus(i.child("status").getValue(GuestStatus.class));
                                initList();
                              }

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/





    }


    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CONTACT_PICK_REQUEST && resultCode == RESULT_OK){
            convertContactsToguests();
        }

    }

    private void convertContactsToguests() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference guestRef = database.getReference("guest");
        List<String> guestIds = new ArrayList<>();

        //for (Contact c: contacts) {
        for (Contact c: ContactListSingleton.getInst().getLastSelectedContactsList().contactArrayList) {
            if(GuestListSingleton.getInst().isSameNumber(c.getPhoneNumber())){
                Toast.makeText(getBaseContext(),c.getName()+" isimli davetli zaten davetli listenizde",Toast.LENGTH_LONG).show();
            }else {
                //davetli kişi verisi veritabanına kaydedilir


                String guestId = guestRef.push().getKey();
                Guest in = new Guest(guestId, inviteId, 0, c.getName(), c.getPhoneNumber(), new GuestStatus());
                //Singleton listeye yeni kişi eklenir
                GuestListSingleton.getInst().getGuestList().add(in);
                guestIds.add(guestId);
                guestRef.child(guestId).setValue(in);

            }
            DatabaseReference inviteRef = database.getReference("invite");
            inviteRef.child(inviteId).child("guestIds").setValue(guestIds);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initList();
    }

    private void initList()
    {
       // GuestListSingleton.getInst().getGuestList().clear();
        //convertContactsToguests();
        guest_adapter = new GuestListAdapter(this);
        guest_expandable.setAdapter(guest_adapter);





    }
}
