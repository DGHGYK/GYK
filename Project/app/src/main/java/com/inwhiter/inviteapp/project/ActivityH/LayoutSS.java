package com.inwhiter.inviteapp.project.ActivityH;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.inwhiter.inviteapp.project.ActivityG.InviteeActivity;
import com.inwhiter.inviteapp.project.R;

public class LayoutSS extends AppCompatActivity {
    ImageView ss;
    View l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.h_activity_layout_ss);



        ss= (ImageView) findViewById(R.id.imageView);
        byte[] encoded = (byte[]) getIntent().getExtras().get("ss");
        Glide.with(LayoutSS.this)
                .load(encoded)
               // .asBitmap()
                //.centerCrop()
                .into(ss);

        /*custom action bar*/
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.h_custom_actionbar_ss);
        View customSS =getSupportActionBar().getCustomView();
        Button addInvitees=(Button)customSS.findViewById(R.id.btn_actionbarSS_addInvitees);
        addInvitees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LayoutSS.this, InviteeActivity.class);
                startActivity(intent);
            }
        });

    }
}
