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

public class CustomAdaptor  extends BaseAdapter {

    private LayoutInflater Inflater;
    private List<String> liste = new ArrayList<String>();

    public CustomAdaptor(Activity activity, List<String> liste) {

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
        View satirView;

        satirView = Inflater.inflate(R.layout.h_satir_layout, null);
        TextView textView = (TextView) satirView.findViewById(R.id.MenuName);
        ImageView imageView = (ImageView) satirView.findViewById(R.id.simge);

        String topic = liste.get(position);
        textView.setText(topic);

        if(topic.equals("Hazır şablon ile hazırlayın")){
            imageView.setImageResource(R.drawable.book);
        }
        else if(topic.equals("Fotoğraf yükleyerek hazırlayın")){
            imageView.setImageResource(R.drawable.foto);
        }
        else if(topic.equals("Video yükleyerek hazırlayın")){
            imageView.setImageResource(R.drawable.video);
        }

        return satirView;

    }
}
