package com.inwhiter.inviteapp.project.ActivityH;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.inwhiter.inviteapp.project.ActivityD.PrintActivity;
import com.inwhiter.inviteapp.project.ActivityG.InviteeActivity;
import com.inwhiter.inviteapp.project.ActivityG.LoginActivity;
import com.inwhiter.inviteapp.project.BusinessG.AllInvitesRecyclerAdapter;
import com.inwhiter.inviteapp.project.R;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

        Button btn_menu_create,btn_menu_view,btn_menu_control,btn_menu_copy, btn_menu_cikis;
    private RecyclerView invite_recycler_view;
    private ArrayList<String> horizontalList;
    private AllInvitesRecyclerAdapter recyclerAdapter;
    private FirebaseAuth mAuth;


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

        horizontalList=new ArrayList<>();
        horizontalList.add("horizontal 1");
        horizontalList.add("horizontal 2");
        horizontalList.add("horizontal 3");
        horizontalList.add("horizontal 4");
        horizontalList.add("horizontal 5");
        horizontalList.add("horizontal 6");
        horizontalList.add("horizontal 7");
        horizontalList.add("horizontal 8");
        horizontalList.add("horizontal 9");
        horizontalList.add("horizontal 10");


        recyclerAdapter=new AllInvitesRecyclerAdapter(horizontalList);


        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(MenuActivity.this, LinearLayoutManager.HORIZONTAL, false);
        invite_recycler_view.setLayoutManager(horizontalLayoutManagaer);

        invite_recycler_view.setAdapter(recyclerAdapter);


 //DUYGUUUUU DAVETIYEMI BASKIYA GONDER
        btn_menu_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//davetiyemi baskiya gonder

                Intent intent=new Intent(MenuActivity.this,PrintActivity.class);
                startActivity(intent);

            }
        });

    }


}
