package com.inwhiter.inviteapp.project.Fragment.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inwhiter.inviteapp.project.BusinessG.GuestListAdapter;
import com.inwhiter.inviteapp.project.BusinessG.SendSMS;
import com.inwhiter.inviteapp.project.Fragment.BaseFragment;
import com.inwhiter.inviteapp.project.Fragment.FragmentController;
import com.inwhiter.inviteapp.project.ModelG.Guest;
import com.inwhiter.inviteapp.project.ModelG.GuestListSingleton;
import com.inwhiter.inviteapp.project.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gncal on 29.10.2017.
 */

public class GuestFragment extends BaseFragment {


    ExpandableListView guest_expandable;
    GuestListAdapter guest_adapter;

    Button pickContacts;
    Button sendSMS;
    CheckBox checkAll;
    Button deleteguest;
    Button addManually;
    static String inviteId;
    final int CONTACT_PICK_REQUEST = 1000;


    public GuestFragment(){

    }

    public static GuestFragment newInstance(Bundle args){
        GuestFragment fragment = new GuestFragment();
        fragment.setArguments(args);
        inviteId= args.getString("inviteId");
        return fragment;

    }

    @Override
    protected int getFID() {
        return R.layout.fragment_guest;
    }

    @Override
    protected void init() {

        guest_expandable = (ExpandableListView) getActivity().findViewById(R.id.lv_guest_expandable);
        pickContacts = (Button) getActivity().findViewById(R.id.iv_guest_pickContacts);
        sendSMS = (Button) getActivity().findViewById(R.id.bt_guest_send);
        checkAll = (CheckBox) getActivity().findViewById(R.id.cb_guest_checkAll);
        deleteguest = (Button) getActivity().findViewById(R.id.bt_guest_delete);
        addManually = (Button) getActivity().findViewById(R.id.bt_guest_addManually);

        guest_adapter = new GuestListAdapter(getActivity(), inviteId);
        guest_expandable.setAdapter(guest_adapter);


        if(guest_expandable.getChildCount()>0){
            checkAll.setVisibility(View.VISIBLE);
        }else{
            checkAll.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    protected void handlers() {
        GuestListSingleton.getInst().removeAllGuests();
        getOldGuests();


        guest_expandable.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getActivity(),
                        (guest_adapter.getGroup(groupPosition)).getName()+ " List Expanded.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        guest_expandable.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getActivity(),
                        ((Guest)guest_adapter.getGroup(groupPosition)).getName() + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();

            }
        });

        guest_expandable.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getActivity(),
                        (guest_adapter.getGroup(groupPosition)).getName()
                                + " -> "
                                + (guest_adapter.getChild(groupPosition,childPosition)).isAnswered(), Toast.LENGTH_SHORT
                ).show();
                return false;
            }
        });

        addManually.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle= new Bundle();
                bundle.putString("inviteId", inviteId);
                listener.changeFragment(FragmentController.ADD_MANUALLY, bundle);
            }
        });


        //rehberden kişi ekleme için rehberden çekilen kişi listesinin açılması
        pickContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle= new Bundle();
                bundle.putString("inviteId", inviteId);
                listener.changeFragment(FragmentController.CONTACTS_PICKER, bundle);
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

                    AlertDialog.Builder sendCheckedListDialog = new AlertDialog.Builder(getActivity());
                    sendCheckedListDialog.setMessage("Seçili davetlilere davetiye göndermek istediğinizden emin misiniz?");
                    sendCheckedListDialog.setCancelable(true);
                    sendCheckedListDialog.setPositiveButton(
                            "Evet",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    new SendSMS().execute(checkedguests);
                                    Toast.makeText(getActivity(), "Davetiyeleriniz seçilen davetlilere SMS ile iletilmiştir.", Toast.LENGTH_LONG).show();
                                    dialog.cancel();

                                }
                            });

                    sendCheckedListDialog.setNegativeButton(
                            "Hayır",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Log.d(getActivity().getLocalClassName(), "Davetiyeler gönderilmedi");
                                    dialog.cancel();
                                }
                            });

                    AlertDialog sendCheckedListAlert = sendCheckedListDialog.create();
                    sendCheckedListAlert.show();


                }else {
                    Toast.makeText(getActivity(), "Seçili davetli bulunamadı.", Toast.LENGTH_SHORT).show();
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
                    Map<String, Object> tobeRemoved = new HashMap<>();

                    if (cb.isChecked()) {
                        checkedguests.add(GuestListSingleton.getInst().getGuestList().get(i));
                        tobeRemoved.put(GuestListSingleton.getInst().getGuestList().get(i).getGuestId(), null);
                    }
                }

                if(checkedguests!=null && checkedguests.size()>0){

                    AlertDialog.Builder removeCheckedDialog = new AlertDialog.Builder(getActivity());
                    removeCheckedDialog.setMessage("Seçili davetlileri silmek istediğinizden emin misiniz?");
                    removeCheckedDialog.setCancelable(true);
                    removeCheckedDialog.setPositiveButton(
                            "Evet",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                 //   ContactListSingleton.getInst().getSelectedContactsList().removeContactsByPhoneNumbers(checkedguests);
                                    GuestListSingleton.getInst().deleteAllGuests(inviteId, checkedguests);
                                    guest_adapter.notifyDataSetChanged();

                                    // initList();
                                    dialog.cancel();
                                }
                            });

                    removeCheckedDialog.setNegativeButton(
                            "Hayır",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Log.d(getActivity().getLocalClassName(), "Davetli silinmedi");
                                    dialog.cancel();
                                }
                            });

                    AlertDialog removeCheckedAlert = removeCheckedDialog.create();
                    removeCheckedAlert.show();
                }else {
                    Toast.makeText(getActivity(), "Seçili davetli bulunamadı.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode, resultCode, data);

       /* if(requestCode == CONTACT_PICK_REQUEST && resultCode == RESULT_OK){
            convertContactsToguests();
        }*/

    }



    public void getOldGuests(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference inviteRef = database.getReference("invite");
        final DatabaseReference guestRef = database.getReference("guest");
        final List<String> oldGuestIds = new ArrayList<String>();
        inviteRef.child(inviteId).child("guestIds") .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    for (DataSnapshot data : dataSnapshot.getChildren()){
                        String gId = data.getKey();
                        oldGuestIds.add(gId);

                        guestRef.child(gId).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot guestSnapshot) {
                                if(guestSnapshot!=null){
                                    Guest g = guestSnapshot.getValue(Guest.class);
                                    int addedId=GuestListSingleton.getInst().alreadyAdded(g.getGuestId());
                                    if(addedId!=-1){
                                        GuestListSingleton.getInst().getGuestList().set(addedId,g);
                                    }else {
                                        GuestListSingleton.getInst().addGuest(g);
                                    }
                                    guest_adapter.notifyDataSetChanged();
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }




   /* @Override
    protected void onResume() {
        super.onResume();
        initList();
    }*/


}
