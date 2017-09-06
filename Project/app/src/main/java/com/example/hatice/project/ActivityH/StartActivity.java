package com.example.hatice.project.ActivityH;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hatice.project.R;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.h_activity_start);
       // getActionBar().hide();


        Intent intent=new Intent(StartActivity.this,MenuActivity.class);

        startActivity(intent);
    }
}
