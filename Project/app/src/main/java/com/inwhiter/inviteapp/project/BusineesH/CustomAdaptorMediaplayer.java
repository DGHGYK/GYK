package com.inwhiter.inviteapp.project.BusineesH;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.inwhiter.inviteapp.project.ModelH.Media;
import com.inwhiter.inviteapp.project.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hatice on 22.09.2017.
 */

public class CustomAdaptorMediaplayer extends BaseAdapter {

    private LayoutInflater Inflater;
    private List<Media> medialistem;

    public CustomAdaptorMediaplayer(Activity activity, List<Media> medialistem) {

        Inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.medialistem = medialistem;
    }

    @Override
    public int getCount() {
        return medialistem.size();
    }

    @Override
    public Object getItem(int position) {
        return medialistem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View satirView = Inflater.inflate(R.layout.h_satir_mediaplayer, null);

        TextView textView = (TextView) satirView.findViewById(R.id.textView);
        ImageView imageView = (ImageView) satirView.findViewById(R.id.media_simge);




        Media media= medialistem.get(position);

        if(media.getIs_play()){
            imageView.setImageResource(R.drawable.icon_music_play);
            media.setIs_play(false);
        }



        if(media.getMusic_name().equals("dm")){


           //
            textView.setText("Düğün Marşı");


        }
        else if(media.getMusic_name().equals("happy1")){
           // imageView.setImageResource(R.drawable.icon_music_play);
            textView.setText("happy1");

        }
        else if(media.getMusic_name().equals("happy2")){
           // imageView.setImageResource(R.drawable.icon_music_play);
            textView.setText("happy2");



       }
        else if(media.getMusic_name().equals("happy3")){
            // imageView.setImageResource(R.drawable.icon_music_play);
            textView.setText("happy3");



        }
        else if(media.getMusic_name().equals("slow1")){
            // imageView.setImageResource(R.drawable.icon_music_play);
            textView.setText("slow1");



        }
        else if(media.getMusic_name().equals("slow2")){
            // imageView.setImageResource(R.drawable.icon_music_play);
            textView.setText("slow2");



        }

        return satirView;

    }


}
