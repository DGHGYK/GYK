package com.inwhiter.inviteapp.project.ActivityG;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inwhiter.inviteapp.project.ActivityH.TemplateActivity;
import com.inwhiter.inviteapp.project.BusinessG.InviteeListAdapter;
import com.inwhiter.inviteapp.project.BusinessG.SendSMS;
import com.inwhiter.inviteapp.project.ModelG.Contact;
import com.inwhiter.inviteapp.project.ModelG.ContactListSingleton;
import com.inwhiter.inviteapp.project.ModelG.Invitee;
import com.inwhiter.inviteapp.project.ModelG.InviteeListSingleton;
import com.inwhiter.inviteapp.project.ModelG.InviteeStatus;
import com.inwhiter.inviteapp.project.R;

import java.util.ArrayList;
import java.util.List;

public class InviteeActivity extends AppCompatActivity {

    ExpandableListView invitee_expandable;
    InviteeListAdapter invitee_adapter;

    Button pickContacts;
    Button sendSMS;
    CheckBox checkAll;
    Button deleteInvitee;
    Button addManually;
    final int CONTACT_PICK_REQUEST = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitee);


        invitee_expandable = (ExpandableListView) findViewById(R.id.lv_invitee_expandable);
        pickContacts = (Button) findViewById(R.id.iv_invitee_pickContacts);
        sendSMS = (Button) findViewById(R.id.bt_invitee_send);
        checkAll = (CheckBox) findViewById(R.id.cb_invitee_checkAll);
        deleteInvitee = (Button) findViewById(R.id.bt_invitee_delete);
        addManually = (Button) findViewById(R.id.bt_invitee_addManually);

        if(invitee_expandable.getChildCount()>0){
            checkAll.setVisibility(View.VISIBLE);
        }else{
            checkAll.setVisibility(View.INVISIBLE);
        }



        invitee_expandable.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        ((Invitee)invitee_adapter.getGroup(groupPosition)).getName()+ " List Expanded.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        invitee_expandable.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        ((Invitee)invitee_adapter.getGroup(groupPosition)).getName() + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();

            }
        });

        invitee_expandable.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        ((Invitee)invitee_adapter.getGroup(groupPosition)).getName()
                                + " -> "
                                + ((InviteeStatus)invitee_adapter.getChild(groupPosition,childPosition)).isAnswered(), Toast.LENGTH_SHORT
                ).show();
                return false;
            }
        });

        addManually.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InviteeActivity.this, AddManuallyActivity.class);
                startActivity(intent);
            }
        });


        //rehberden kişi ekleme için rehberden çekilen kişi listesinin açılması
        pickContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentContactPick = new Intent(InviteeActivity.this,ContactsPickerActivity.class);
                InviteeActivity.this.startActivityForResult(intentContactPick,CONTACT_PICK_REQUEST);
            }
        });

        //üstteki checkboxa basılnıca tüm kişilerin işaretlenmesi, işaret silinince tüm kişilerden silinmesi
        checkAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for(int i=0; i < invitee_expandable.getChildCount(); i++){
                    LinearLayout itemLayout = (LinearLayout) invitee_expandable.getChildAt(i);
                    CheckBox cb = (CheckBox)itemLayout.findViewById(R.id.cb_item_check);
                    cb.setChecked(isChecked);
                }
            }
        });

        sendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final List<String> checkedIdList=new ArrayList<>();
                final List<Invitee> checkedInvitees = new ArrayList<>();
                for(int i=0; i < invitee_expandable.getChildCount(); i++) {
                    LinearLayout itemLayout = (LinearLayout) invitee_expandable.getChildAt(i);
                    CheckBox cb = (CheckBox) itemLayout.findViewById(R.id.cb_item_check);

                    if (cb.isChecked()) {
                        checkedInvitees.add(InviteeListSingleton.getInst().getInviteeList().get(i));
                    }
                }

                if(checkedInvitees!=null && checkedInvitees.size()>0){

                    AlertDialog.Builder sendCheckedListDialog = new AlertDialog.Builder(InviteeActivity.this);
                    sendCheckedListDialog.setMessage("Seçili davetlilere davetiye göndermek istediğinizden emin misiniz?");
                    sendCheckedListDialog.setCancelable(true);
                    sendCheckedListDialog.setPositiveButton(
                            "Evet",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    new SendSMS().execute(checkedInvitees);
                                    Toast.makeText(getBaseContext(), "Davetiyeleriniz seçilen davetlilere SMS ile iletilmiştir.", Toast.LENGTH_LONG).show();
                                    dialog.cancel();
                                }
                            });

                    sendCheckedListDialog.setNegativeButton(
                            "Hayır",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Log.d(InviteeActivity.this.getLocalClassName(), "Davetiyeler gönderilmedi");
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


        deleteInvitee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final List<Invitee> checkedInvitees = new ArrayList<>();
                for(int i=0; i < invitee_expandable.getChildCount(); i++) {
                    LinearLayout itemLayout = (LinearLayout) invitee_expandable.getChildAt(i);
                    CheckBox cb = (CheckBox) itemLayout.findViewById(R.id.cb_item_check);
                    TextView tv = (TextView) itemLayout.findViewById(R.id.tv_item_id);

                    if (cb.isChecked()) {
                        checkedInvitees.add(InviteeListSingleton.getInst().getInviteeList().get(i));

                    }
                }

                    if(checkedInvitees!=null && checkedInvitees.size()>0){

                            AlertDialog.Builder removeCheckedDialog = new AlertDialog.Builder(InviteeActivity.this);
                            removeCheckedDialog.setMessage("Seçili davetlileri silmek istediğinizden emin misiniz?");
                            removeCheckedDialog.setCancelable(true);
                            removeCheckedDialog.setPositiveButton(
                                    "Evet",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            ContactListSingleton.getInst().getSelectedContactsList().removeContactsByPhoneNumbers(checkedInvitees);
                                            InviteeListSingleton.getInst().removeAllInvitees(checkedInvitees);
                                            invitee_adapter.notifyDataSetChanged();

                                            // initList();
                                            dialog.cancel();
                                        }
                                    });

                            removeCheckedDialog.setNegativeButton(
                                    "Hayır",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            Log.d(InviteeActivity.this.getLocalClassName(), "Davetli silinmedi");
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
        DatabaseReference inviteeRef = database.getReference("invitee");
        Query q =inviteeRef.orderByChild("inviteId").equalTo(TemplateActivity.inviteId);
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot i : dataSnapshot.getChildren()) {
                        // do something with the individual "issues"
                        for (Invitee in: InviteeListSingleton.getInst().getInviteeList())
                            if(in.getName().equals(i.getValue(Invitee.class).getName()) )
                              {
                                in.setStatus(i.child("status").getValue(InviteeStatus.class));
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
            convertContactsToInvitees();
        }

    }

    private void convertContactsToInvitees() {
        //for (Contact c: contacts) {
        for (Contact c: ContactListSingleton.getInst().getLastSelectedContactsList().contactArrayList) {
            if(InviteeListSingleton.getInst().isSameNumber(c.getPhoneNumber())){
                Toast.makeText(getBaseContext(),c.getName()+" isimli davetli zaten davetli listenizde",Toast.LENGTH_LONG).show();
            }else {
                //davetli kişi verisi veritabanına kaydedilir
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference inviteeRef = database.getReference("invitee");

                String inviteeId = inviteeRef.push().getKey();
                Invitee in = new Invitee(inviteeId, TemplateActivity.inviteId, 0, c.getName(), c.getPhoneNumber(), new InviteeStatus());
                //Singleton listeye yeni kişi eklenir
                InviteeListSingleton.getInst().getInviteeList().add(in);

                inviteeRef.child(inviteeId).setValue(in);

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initList();
    }

    private void initList()
    {
       // InviteeListSingleton.getInst().getInviteeList().clear();
        //convertContactsToInvitees();
        invitee_adapter = new InviteeListAdapter(getApplicationContext(),this);
        invitee_expandable.setAdapter(invitee_adapter);





    }
}
