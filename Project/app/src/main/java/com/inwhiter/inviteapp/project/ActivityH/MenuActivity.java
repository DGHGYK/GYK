package com.inwhiter.inviteapp.project.ActivityH;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
import com.inwhiter.inviteapp.project.BusinessG.RecyclerItemClickListener;
import com.inwhiter.inviteapp.project.ModelG.Invitation;
import com.inwhiter.inviteapp.project.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


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
//        String titlem=getSupportActionBar().getTitle().toString();
        mAuth = FirebaseAuth.getInstance();

//        SpannableString s = new SpannableString(titlem);
//        s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimaryDark)), 0, titlem.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        getSupportActionBar().setTitle(s);

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


        //recycleview içindeki eski davetiye imajlarına basılınca devetlilerine gitmek için

        invite_recycler_view.addOnItemTouchListener(
                new RecyclerItemClickListener(getBaseContext(), invite_recycler_view ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        TextView tv_inviteId= (TextView) view.findViewById(R.id.tv_recycler_item_id);
                        String inviteId= (String) tv_inviteId.getText();
                        Intent intent=new Intent(MenuActivity.this, InviteeActivity.class);
                        intent.putExtra("inviteId", inviteId);
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );



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
                    String title="";
                    String fDate="";
                    if(snapshot.child("info/title").exists()) {
                        title = snapshot.child("info/title").getValue().toString();
                    }

                    if(snapshot.child("createdDate/time").exists()) {
                        Long time = snapshot.child("createdDate/time").getValue(Long.class);
                        Date date = new Date(time);
                        fDate = new SimpleDateFormat("dd/MM/yyyy hh:mm").format(date);
                        //DateFormat df = new DateFormat();
                        //df.format("dd--MM--yyyy hh:mm", date);

                    }

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
                        inv.setImageText(inviteId);
                        inv.setDetails(title + " - "+fDate);
                        horizontalList.add(inv);
                    }
                }
                if (horizontalList.size() == 0) {
                    Invitation inv = new Invitation("", Uri.parse("android.resource://com.inwhiter.inviteapp.project/" + R.drawable.giris));
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
