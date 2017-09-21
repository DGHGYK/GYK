package com.inwhiter.inviteapp.project.ActivityH;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.inwhiter.inviteapp.project.ActivityD.PrintActivity;
import com.inwhiter.inviteapp.project.ActivityG.InviteeActivity;
import com.inwhiter.inviteapp.project.BusinessG.AllInvitesRecyclerAdapter;
import com.inwhiter.inviteapp.project.R;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

        Button btn_menu_create,btn_menu_view,btn_menu_control,btn_menu_copy;
    private RecyclerView invite_recycler_view;
    private ArrayList<String> horizontalList;
    private AllInvitesRecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.h_activity_menu);

        btn_menu_create=(Button)findViewById(R.id.btn_menu_create);
        btn_menu_view=(Button)findViewById(R.id.btn_menu_view);
        btn_menu_control=(Button)findViewById(R.id.btn_menu_control);
        btn_menu_copy=(Button)findViewById(R.id.btn_menu_copy);

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

        invite_recycler_view= (RecyclerView) findViewById(R.id.rv_all_invites_view);

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
