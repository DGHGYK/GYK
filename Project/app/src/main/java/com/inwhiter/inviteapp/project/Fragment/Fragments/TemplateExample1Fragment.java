package com.inwhiter.inviteapp.project.Fragment.Fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.inwhiter.inviteapp.project.Fragment.BaseFragment;
import com.inwhiter.inviteapp.project.Fragment.FragmentController;
import com.inwhiter.inviteapp.project.ModelG.Info;
import com.inwhiter.inviteapp.project.R;

public class TemplateExample1Fragment extends BaseFragment {
    Bundle bundle;
    int color=-44444626;
    String font;
    Info info;
    Typeface face,face1,face2,face3,face4=null;


    public TemplateExample1Fragment() {}

    public static TemplateExample1Fragment newInstance(Bundle args) {
        TemplateExample1Fragment fragment = new TemplateExample1Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getFID() {
        return R.layout.fragment_template_example1;
    }

    @Override
    protected void init() {
//         /*fontlar*/
       face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/BonbonRegular.otf");
       face2 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/solena.otf");
       face3 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Glaresome.otf");
       face4 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Gecko.ttf");

        bundle=this.getArguments();
        info= bundle.getParcelable("info");
        color=bundle.getInt("Color");
        font=bundle.getString("font");

        if(font=="face1"){
            face=face1;

        }
        else if(font=="face2"){
            face=face2;
        }
        else if(font=="face3"){
            face=face3;
        }
        else if(font=="face4"){
            face=face4;
        }


        TextView title = (TextView) getActivity().findViewById(R.id.tv_sablon1_title);
        title.setText(info.getTitle());



        TextView maintext = (TextView) getActivity().findViewById(R.id.tv_sablon1_maintext);
        maintext.setText(info.getText());

        TextView family1 = (TextView) getActivity().findViewById(R.id.tv_sablon1_family1);
        family1.setText(info.getFamily1());

        TextView family2 = (TextView) getActivity().findViewById(R.id.tv_sablon1_family2);
        family2.setText(info.getFamily2());

        TextView adress = (TextView) getActivity().findViewById(R.id.tv_sablon1_adress);
        adress.setText(info.getAddress());


        TextView tag = (TextView) getActivity().findViewById(R.id.tv_sablon1_tag);
        tag.setText(info.getHashtag());


        TextView time = (TextView) getActivity().findViewById(R.id.tv_sablon1_time);
        time.setText(info.getTime());


        TextView date= (TextView) getActivity().findViewById(R.id.tv_sablon1_date);
        date.setText(info.getDate());

//-44444626

//
            title.setTextColor(color);
            maintext.setTextColor(color);
            family1.setTextColor(color);
            family2.setTextColor(color);
            adress.setTextColor(color);
            tag.setTextColor(color);
            time.setTextColor(color);
            date.setTextColor(color);

             title.setTypeface(face);
             maintext.setTypeface(face);
             family1.setTypeface(face);
             family2.setTypeface(face);
             adress.setTypeface(face);
             tag.setTypeface(face);
             time.setTypeface(face);
             date.setTypeface(face);





    }







    @Override
    protected void handlers() {

    }
}
