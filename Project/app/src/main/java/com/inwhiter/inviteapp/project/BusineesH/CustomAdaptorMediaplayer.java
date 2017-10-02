package com.inwhiter.inviteapp.project.BusineesH;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.inwhiter.inviteapp.project.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hatice on 22.09.2017.
 */

public class CustomAdaptorMediaplayer extends BaseAdapter {

    private LayoutInflater Inflater;
    private List<String> liste = new ArrayList<String>();

    public CustomAdaptorMediaplayer(Activity activity, List<String> liste) {

        Inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.liste = liste;
    }

    @Override
    public int getCount() {
        return liste.size();
    }

    @Override
    public Object getItem(int position) {
        return liste.get(position);
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




        String music = liste.get(position).toString();


        if(music.equals("dm")){
           // imageView.setImageResource(R.drawable.icon_music_play);
            textView.setText("Düğün Marşı");


        }
        else if(music.equals("happy1")){
           // imageView.setImageResource(R.drawable.icon_music_play);
            textView.setText("happy1");

        }
        else if(music.equals("happy2")){
           // imageView.setImageResource(R.drawable.icon_music_play);
            textView.setText("happy2");



       }
        else if(music.equals("happy3")){
            // imageView.setImageResource(R.drawable.icon_music_play);
            textView.setText("happy3");



        }
        else if(music.equals("slow1")){
            // imageView.setImageResource(R.drawable.icon_music_play);
            textView.setText("slow1");



        }
        else if(music.equals("slow2")){
            // imageView.setImageResource(R.drawable.icon_music_play);
            textView.setText("slow2");



        }

        return satirView;

    }
}
