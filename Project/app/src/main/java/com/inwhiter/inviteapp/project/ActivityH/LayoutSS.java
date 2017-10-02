package com.inwhiter.inviteapp.project.ActivityH;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.inwhiter.inviteapp.project.ActivityG.InviteeActivity;
import com.inwhiter.inviteapp.project.BusineesH.CustomAdaptorMediaplayer;
import com.inwhiter.inviteapp.project.ModelG.Invite;
import com.inwhiter.inviteapp.project.R;
import com.rajasharan.layout.RearrangeableLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import static com.inwhiter.inviteapp.project.R.drawable.icon_music_play;

public class LayoutSS extends AppCompatActivity {
    ImageView ss;
    Context context=this;

    public static String inviteId;
    private RearrangeableLayout root;
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;


    public ArrayList<String> playlist=new ArrayList<>();
    public ListView lv_playlist;
    public ImageView icon_play;
    public   MediaPlayer mp = null;
    public double startTime = 0;
    public double finalTime = 0;
    public int oneTimeOnly = 0;
    public Handler myHandler = new Handler() ;
    public SeekBar seekBar;
    public ImageView play_pause;
    Button btn_ok;
    //public Layout layout_media_player;
    public TextView current_duration,total_duration,current_music;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.h_activity_layout_ss);


        /*custom action bar*//////////////////////////////////////////////////////////
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.h_custom_actionbar_ss);
        View customSS =getSupportActionBar().getCustomView();
        Button addInvitees=(Button)customSS.findViewById(R.id.btn_actionbarSS_addInvitees);
        Button addmusic=(Button)customSS.findViewById(R.id.btn_actionbarSS_addMusic);
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
        addmusic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);

                dialog.setContentView(R.layout.h_mediaplayer_custom_dialog);
                dialog.setTitle("Media player");






                 //layout_media_player=(Layout) findViewById(R.layout.h_satir_mediaplayer);
                 seekBar=(SeekBar)dialog.findViewById(R.id.seek_bar);
                 lv_playlist=(ListView)dialog.findViewById(R.id.lv_playlist);
                  current_duration=(TextView)dialog.findViewById(R.id.txt_current_duration);
                  current_music=(TextView)dialog.findViewById(R.id.txt_current_music);
                  total_duration=(TextView)dialog.findViewById(R.id.txt_total_duration);
                 play_pause=(ImageView)dialog.findViewById(R.id.btn_play_pause);
                btn_ok=(Button)dialog.findViewById(R.id.btn_ok);
                // icon_play= (ImageView)findViewById(R.id.media_simge);

                 play_pause.setVisibility(View.INVISIBLE);

                playlist.add("dm");
                playlist.add("happy1");
                playlist.add("happy2");
                playlist.add("happy3");
                playlist.add("slow1");
                playlist.add("slow2");

                CustomAdaptorMediaplayer adp=new CustomAdaptorMediaplayer((Activity) context,playlist);
                lv_playlist.setAdapter(adp);









                lv_playlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                        final String music_name = playlist.get(position).toString();

                        btn_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                String firebase_music_name=music_name;//bu isim gönderilecek firebase e
                             //firebase e gönderme/sanırım burayı yapamam gamze :)su invitee içine neleri koyacağım bilemedim
                             /*   FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference inviteRef = database.getReference("invite");

                                mAuth = FirebaseAuth.getInstance();
                                FirebaseUser currentUser = mAuth.getCurrentUser();
                                Invite invite = new Invite(currentUser.getUid(),info, 1, "", getIntent().getExtras().getString("menu"),  Calendar.getInstance().getTime() );
                                if(inviteId==null || inviteId.equals("")) {
                                    inviteId = inviteRef.push().getKey();
                                }
                                inviteRef.child(inviteId).setValue(invite);*/
                                Toast.makeText(LayoutSS.this,music_name+ " seçildi", Toast.LENGTH_SHORT).show();
                             mp.stop();

                             dialog.dismiss();

                            }
                        });

                        //play_pause.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_black));
                        play_pause.setVisibility(View.VISIBLE);
                        if (music_name.equals("dm")) {
                            if (mp != null) {
                                mp.reset();

                            }
                            mp = MediaPlayer.create(LayoutSS.this, R.raw.dm);
                           // icon_play.setImageDrawable(getResources().getDrawable(R.drawable.icon_music_play));

                            current_music.setText("Düğün Marşı");
                            play();



                        }
                        else if(music_name.equals("happy1"))

                        {
                            if (mp != null) {
                                mp.reset();

                            }
                            mp = MediaPlayer.create(LayoutSS.this, R.raw.happy1);
                           // icon_play.setImageDrawable(getResources().getDrawable(R.drawable.icon_music_play));
                            current_music.setText("Happy 1");
                            play();

                        }
                        else if(music_name.equals("happy2"))

                        {
                            if (mp != null) {
                                mp.reset();


                            }
                            mp = MediaPlayer.create(LayoutSS.this, R.raw.happy2);
                            current_music.setText("Happy2");
                            play();

                        }
                        else if(music_name.equals("happy3"))

                        {
                            if (mp != null) {
                                mp.reset();


                            }
                            mp = MediaPlayer.create(LayoutSS.this, R.raw.happy3);
                            current_music.setText("Happy3");
                            play();

                        }
                        else if(music_name.equals("slow1"))

                        {
                            if (mp != null) {
                                mp.reset();


                            }
                            mp = MediaPlayer.create(LayoutSS.this, R.raw.slow1);
                            current_music.setText("slow1");
                            play();

                        }
                        else if(music_name.equals("slow2"))

                        {
                            if (mp != null) {
                                mp.reset();


                            }
                            mp = MediaPlayer.create(LayoutSS.this, R.raw.slow2);
                            current_music.setText("slow2");
                            play();

                        }

                        play_pause.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(mp.isPlaying()){
                                    play_pause.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_black));
                                    pause();
                                }

                                else{

                                    play_pause.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_black));

                                    play();


                                }

                            }

                        });



                    }



                    private void play() {
                        seekBar.setClickable(false);
                        mp.start();


                        finalTime = mp.getDuration();
                        startTime = mp.getCurrentPosition();
                        if (oneTimeOnly == 0) {
                            seekBar.setMax((int) finalTime);
                            oneTimeOnly = 1;
                        }
                        //Muziğin toplamda ne kadar süre oldugunu  endTimeField controller ına yazdırıyoruz...
                        total_duration.setText(String.format("%d min, %d sec",
                                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                                TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                                toMinutes((long) finalTime)))
                        );
                        //Muziğin başladıgı andan itibaren gecen süreyi ,startTimeField controller ına yazdırıyoruz...
                        current_duration.setText(String.format("%d min, %d sec",
                                TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                                TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                                toMinutes((long) startTime)))
                        );
                        //Muziğin hangi sürede oldugunu gostermek icin, seekbar kullarak gosteriyoruz...
                        seekBar.setProgress((int) startTime);
                        myHandler.postDelayed(UpdateSongTime, 100);
                        //pause.setImageDrawable(getResources().getDrawable(R.mipmap.ic_pause));
                        //pause.setEnabled(true);
                        //play.setEnabled(false);


                    }

                    Runnable UpdateSongTime = new Runnable() {
                        public void run() {
                            startTime = mp.getCurrentPosition();
                            total_duration.setText(String.format("%d min, %d sec",
                                    TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                                    TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                                    toMinutes((long) finalTime)))
                            );
                            current_duration.setText(String.format("%d min, %d sec",
                                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                                    toMinutes((long) startTime)))
                            );
                            //Muziğin hangi sürede oldugunu gostermek icin, seekbar kullarak gosteriyoruz...
                            seekBar.setProgress((int)startTime);
                            myHandler.postDelayed(this, 100);
                        }
                    };

                    public  void pause(){

                        mp.pause();
                    }








                });dialog.show();

            }



                });



            }
}






