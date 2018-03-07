package com.inwhiter.inviteapp.project.Fragment.Fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inwhiter.inviteapp.project.Fragment.BaseFragment;
import com.inwhiter.inviteapp.project.ModelG.Info;
import com.inwhiter.inviteapp.project.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TemplateExample2Fragment extends BaseFragment {
    int color=-44444626;
    Info info;
    Bundle bundle;
    Typeface face,face2,face1,face3,face4=null;
    String font;



    public TemplateExample2Fragment() {
        // Required empty public constructor
    }
    public static TemplateExample2Fragment newInstance(Bundle args) {
        TemplateExample2Fragment fragment = new TemplateExample2Fragment();
        fragment.setArguments(args);
        return fragment;}


    @Override
    protected int getFID() {
        return R.layout.fragment_template_example2;
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


        TextView title = (TextView) getActivity().findViewById(R.id.tv_sablon2_title);
        title.setText(info.getTitle());
        title.setTextColor(color);

        TextView maintext = (TextView) getActivity().findViewById(R.id.tv_sablon2_maintext);
        maintext.setText(info.getText());
        maintext.setTextColor(color);

        TextView family1 = (TextView) getActivity().findViewById(R.id.tv_sablon2_family1);
        family1.setText(info.getFamily1());
        family1.setTextColor(color);

        TextView family2 = (TextView) getActivity().findViewById(R.id.tv_sablon2_family2);
        family2.setText(info.getFamily2());
        family2.setTextColor(color);

        TextView adress = (TextView) getActivity().findViewById(R.id.tv_sablon2_adress);
        adress.setText(info.getAddress());
        adress.setTextColor(color);
        TextView tag = (TextView) getActivity().findViewById(R.id.tv_sablon2_tag);

        tag.setText(info.getHashtag());
        tag.setTextColor(color);

        TextView time = (TextView) getActivity().findViewById(R.id.tv_sablon2_time);
        time.setText(info.getTime());
        time.setTextColor(color);

        TextView date= (TextView) getActivity().findViewById(R.id.tv_sablon2_date);
        date.setText(info.getDate());
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

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_template_example2, container, false);
//    }

}
