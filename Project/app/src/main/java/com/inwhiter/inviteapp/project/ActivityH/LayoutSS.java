package com.inwhiter.inviteapp.project.ActivityH;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inwhiter.inviteapp.project.ActivityG.InviteeActivity;
import com.inwhiter.inviteapp.project.BusineesH.CustomAdaptorMediaplayer;
import com.inwhiter.inviteapp.project.ModelH.Media;
import com.inwhiter.inviteapp.project.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LayoutSS extends AppCompatActivity {
    ImageView ss;
    Context context=this;

    public String inviteId;
    private FirebaseDatabase database;


    public List<Media> playlist=new ArrayList<Media>();
    public ListView lv_playlist;
    public ImageView icon_play;
    public   MediaPlayer mp = null;
    public double startTime = 0;
    public double finalTime = 0;
    public int oneTimeOnly = 0;
    public Handler myHandler = new Handler() ;
    public SeekBar seekBar;
    public ImageView play_pause,on_off;
    Button btn_ok;
    //public Layout layout_media_player;
    public TextView current_duration,total_duration,current_music;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.h_activity_layout_ss);

        Bundle bundle= getIntent().getExtras();
        if(bundle!=null){
            inviteId = bundle.getString("inviteId");
        }


        /*custom action bar*//////////////////////////////////////////////////////////
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







        ss= (ImageView) findViewById(R.id.imageView);
        byte[] encoded = (byte[]) getIntent().getExtras().get("ss");
        Glide.with(LayoutSS.this)
                .load(encoded)
               // .asBitmap()
                //.centerCrop()
                .into(ss);
        //Button btn=(Button)findViewById(R.id.music);



  //medi player eklentisi



            }
}






