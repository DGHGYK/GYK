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

import com.bumptech.glide.Glide;
import com.inwhiter.inviteapp.project.ActivityG.InviteeActivity;
import com.inwhiter.inviteapp.project.BusineesH.CustomAdaptorMediaplayer;
import com.inwhiter.inviteapp.project.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class LayoutSS extends AppCompatActivity {
    ImageView ss;
    View l;
    MediaPlayer ring;
    Context context=this;

    public ArrayList<String> playlist=new ArrayList<>();
    public ListView lv_playlist;
    public   MediaPlayer mp = null;
    public double startTime = 0;
    public double finalTime = 0;
    public int oneTimeOnly = 0;
    public Handler myHandler = new Handler() ;
    public SeekBar seekBar;
    public ImageView play_pause;
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
        Button btn=(Button)findViewById(R.id.music);



  //medi player eklentisi
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);

                dialog.setContentView(R.layout.h_mediaplayer_custom_dialog);
                dialog.setTitle("Media player");






                 seekBar=(SeekBar)dialog.findViewById(R.id.seek_bar);
                 lv_playlist=(ListView)dialog.findViewById(R.id.lv_playlist);
                  current_duration=(TextView)dialog.findViewById(R.id.txt_current_duration);
                  current_music=(TextView)dialog.findViewById(R.id.txt_current_music);
                  total_duration=(TextView)dialog.findViewById(R.id.txt_total_duration);
                 play_pause=(ImageView)dialog.findViewById(R.id.btn_play_pause);

                 //play_pause.setVisibility(View.INVISIBLE);

                playlist.add("dm");
                playlist.add("happy1");
                playlist.add("happy2");

                CustomAdaptorMediaplayer adp=new CustomAdaptorMediaplayer((Activity) context,playlist);
                lv_playlist.setAdapter(adp);





        /*bilgi sayfasında resim ve video da alabilmek için her birinin formatını ayrı gönderdim. ör:resim dedğinde resim ekle butonu da gelsin*/
                lv_playlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                        String music_name = playlist.get(position).toString();
                        //play_pause.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_black));
                       // play_pause.setVisibility(View.VISIBLE);
                        if (music_name.equals("dm")) {
                            if (mp != null) {
                                mp.reset();

                            }
                            mp = MediaPlayer.create(LayoutSS.this, R.raw.dm);
                            current_music.setText("Düğün Marşı");
                            play();


                        }
                        else if(music_name.equals("happy1"))

                        {
                            if (mp != null) {
                                mp.reset();

                            }
                            mp = MediaPlayer.create(LayoutSS.this, R.raw.happy1);
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






