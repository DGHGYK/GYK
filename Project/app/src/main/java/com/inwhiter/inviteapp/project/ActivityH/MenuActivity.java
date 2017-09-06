package com.inwhiter.inviteapp.project.ActivityH;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.inwhiter.inviteapp.project.ActivityG.InviteeActivity;
import com.inwhiter.inviteapp.project.R;

public class MenuActivity extends AppCompatActivity {

        Button btn_menu_create,btn_menu_view,btn_menu_control,btn_menu_copy;

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
    }


}
