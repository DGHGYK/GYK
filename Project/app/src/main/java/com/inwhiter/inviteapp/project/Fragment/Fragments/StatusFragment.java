package com.inwhiter.inviteapp.project.Fragment.Fragments;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inwhiter.inviteapp.project.Fragment.BaseFragment;
import com.inwhiter.inviteapp.project.R;

import java.util.ArrayList;
import java.util.List;


public class StatusFragment extends BaseFragment {
    TextView sendedNumber;
    TextView answeredNumber;
    TextView attendanceNumber;
    String inviteId;
    int number;

    public StatusFragment() {
        // Required empty public constructor
    }


    public static StatusFragment newInstance(Bundle args){
        StatusFragment fragment = new StatusFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getFID() {
        return R.layout.fragment_status;
    }

    @Override
    protected void init() {
        sendedNumber = (TextView) getActivity().findViewById(R.id.tv_status_numberOfSended);
        answeredNumber = (TextView) getActivity().findViewById(R.id.tv_status_numberOfAnswers);
        attendanceNumber = (TextView) getActivity().findViewById(R.id.tv_status_numberOfAttendance);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            inviteId= bundle.getString("inviteId");
        }

        setStats();

    }


    @Override
    protected void handlers() {


    }

    private void setStats(){


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference inviteRef = database.getReference("invite");
        final List<String> comingIds = new ArrayList<>();
        final DatabaseReference guestRef = database.getReference("guest");
        //final List<String> oldGuestIds = new ArrayList<String>();

        inviteRef.child(inviteId).child("guestIds").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int nonAnswered=0;
                int coming=0;
                int maybe=0;
                int notComing=0;
                int numberOfAnswered=0;
                String numberOfInvitations=String.valueOf(dataSnapshot.getChildrenCount());

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    int stat = snapshot.getValue(Integer.class);
                    switch (stat) {
                        case 0:
                            nonAnswered++;
                            break;
                        case 1:
                            notComing++;
                            numberOfAnswered++;
                            break;
                        case 2:
                            maybe++;
                            numberOfAnswered++;
                            break;
                        case 3:
                            coming++;
                            numberOfAnswered++;
                            comingIds.add(snapshot.getKey());
                            break;

                    }
                    sendedNumber.setText( "Bu davetiniz için toplam " + numberOfInvitations+" davetiye gönderdiniz.");
                    answeredNumber.setText("Davetinize "+numberOfAnswered+ " kişi cevap verdi. \n\nGeleceklerin sayısı: "+coming+" \nBelki geleceklerin sayısı: "+maybe+" \nGelmeyeceklerin sayısı: "+notComing);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

         int totalNumber=0;

        for (String comingId: comingIds) {
            guestRef.child(comingId).child("status").child("numberOfPeople").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    number = Integer.valueOf(dataSnapshot.getValue().toString());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            totalNumber+=number;

        }
        attendanceNumber.setText("Davetinize toplam "+totalNumber+" davetli katılacaktır.");

    }
}
