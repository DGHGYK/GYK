package com.inwhiter.inviteapp.project.ActivityH;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.inwhiter.inviteapp.project.ActivityD.PrintActivity;
import com.inwhiter.inviteapp.project.ActivityG.InviteeActivity;
import com.inwhiter.inviteapp.project.ActivityG.LoginActivity;
import com.inwhiter.inviteapp.project.BusinessG.AllInvitesRecyclerAdapter;
import com.inwhiter.inviteapp.project.ModelG.Invitation;
import com.inwhiter.inviteapp.project.R;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    Button btn_menu_create,btn_menu_view,btn_menu_control,btn_menu_copy, btn_menu_cikis;
    private RecyclerView invite_recycler_view;
    private ArrayList<Invitation> horizontalList;
    private AllInvitesRecyclerAdapter recyclerAdapter;
    private FirebaseAuth mAuth;
    private FirebaseStorage storage;
    private FirebaseDatabase database;
    private DatabaseReference inviteRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.h_activity_menu);
        String titlem=getSupportActionBar().getTitle().toString();
        mAuth = FirebaseAuth.getInstance();

        SpannableString s = new SpannableString(titlem);
        s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimaryDark)), 0, titlem.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(s);

        btn_menu_create=(Button)findViewById(R.id.btn_menu_create);
        btn_menu_view=(Button)findViewById(R.id.btn_menu_view);
        btn_menu_copy=(Button)findViewById(R.id.btn_menu_copy);
        btn_menu_cikis=(Button)findViewById(R.id.btn_menu_cikis);

        btn_menu_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MenuActivity.this,TopicActivity.class);
                startActivity(intent);
            }
        });
        btn_menu_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//gamzenin davetlilerini ekleme buton kısmı
                Intent intent=new Intent(MenuActivity.this,InviteeActivity.class);
                startActivity(intent);
            }
        });

        btn_menu_cikis.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  mAuth.signOut();
                  Intent intent=new Intent(MenuActivity.this,LoginActivity.class);
                  startActivity(intent);
              }
          });


        invite_recycler_view = (RecyclerView) findViewById(R.id.rv_all_invites_view);
        setHorizontalListData();



 //DUYGUUUUU DAVETIYEMI BASKIYA GONDER
        btn_menu_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//davetiyemi baskiya gonder

                Intent intent=new Intent(MenuActivity.this,PrintActivity.class);
                startActivity(intent);

            }
        });

    }

    private void setHorizontalListData() {

        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        inviteRef = database.getReference("invite");
        horizontalList = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();


        inviteRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String inviteId = snapshot.getKey();
                    String userId = snapshot.child("userId").getValue().toString();
                    if (mAuth.getCurrentUser().getUid().equals(userId)) {
                        final Invitation inv = new Invitation();
                        storage.getReference().child("invites/" + inviteId + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                inv.setImageURI(uri);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                inv.setImageURI(Uri.parse("android.resource://com.inwhiter.inviteapp.project/" + R.drawable.giris));
                            }
                        });
                       // inv.setImageText(snapshot.child("info").child("title").getValue().toString() + " / " + snapshot.child("createdDate").getValue().toString());
                        horizontalList.add(inv);
                    }
                }
                if (horizontalList.size() == 0) {
                    Invitation inv = new Invitation("asfsdf", Uri.parse("android.resource://com.inwhiter.inviteapp.project/" + R.drawable.giris));
                    horizontalList.add(inv);
                }
                recyclerAdapter=new AllInvitesRecyclerAdapter(horizontalList, MenuActivity.this);


                LinearLayoutManager horizontalLayoutManagaer
                        = new LinearLayoutManager(MenuActivity.this, LinearLayoutManager.HORIZONTAL, false);
                invite_recycler_view.setLayoutManager(horizontalLayoutManagaer);

                invite_recycler_view.setAdapter(recyclerAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }



}
