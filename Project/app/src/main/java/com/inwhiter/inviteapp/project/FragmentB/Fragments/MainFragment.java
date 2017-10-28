package com.inwhiter.inviteapp.project.FragmentB.Fragments;


import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.inwhiter.inviteapp.project.CustomB.GridMarginDecoration;
import com.inwhiter.inviteapp.project.CustomB.MainInviteAdapter;
import com.inwhiter.inviteapp.project.FragmentB.BaseFragment;
import com.inwhiter.inviteapp.project.ModelG.Invitation;
import com.inwhiter.inviteapp.project.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private MainInviteAdapter mAdapter;
    private ArrayList<Invitation> invitationList;
    private FirebaseAuth mAuth;
    private FirebaseStorage storage;
    private FirebaseDatabase database;
    private DatabaseReference inviteRef;
    private int margin = 8;

    public MainFragment() {}


    @Override
    protected int getFID() {
        return R.layout.fragment_main;
    }

    @Override
    protected void init() {
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        inviteRef = database.getReference("invite");
        mAuth = FirebaseAuth.getInstance();
        invitationList = new ArrayList<>();

        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view);
        invitationList.add(new Invitation("Yükleniyor.", Uri.parse("")));
        mAdapter = new MainInviteAdapter(getActivity(), invitationList);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.addItemDecoration(new GridMarginDecoration(getActivity(), margin, margin, margin, margin));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void handlers() {
        mAdapter.SetOnItemClickListener(new MainInviteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Invitation model) {

            }
        });

        inviteRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null)
                {
                    invitationList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String inviteId = snapshot.getKey();
                        String userId = snapshot.child("userId").getValue().toString();
                        String title="";
                        String fDate="";
                        if(snapshot.child("info/title").exists()) {
                            title = snapshot.child("info/title").getValue().toString();
                        }

                        if(snapshot.child("createdDate/time").exists()) {
                            Long time = snapshot.child("createdDate/time").getValue(Long.class);
                            Date date = new Date(time);
                            fDate = new SimpleDateFormat("dd/MM/yyyy hh:mm").format(date);
                        }

                        if (mAuth.getCurrentUser().getUid().equals(userId)) {
                            final Invitation inv = new Invitation();
                            storage.getReference().child("invites/" + inviteId + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    inv.setImageURI(uri);
                                    mAdapter.notifyDataSetChanged();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    inv.setImageURI(Uri.parse("android.resource://com.inwhiter.inviteapp.project/" + R.drawable.giris));
                                }
                            });
                            inv.setImageText(inviteId);
                            inv.setDetails(title + " - "+fDate);
                            invitationList.add(inv);
                        }
                    }
                    if (invitationList.size() == 0) {
                        Invitation inv = new Invitation("Davetiyeniz bulunmamaktadır.", Uri.parse("android.resource://com.inwhiter.inviteapp.project/" + R.drawable.giris));
                        invitationList.add(inv);
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
