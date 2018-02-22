package com.inwhiter.inviteapp.project.Fragment.Fragments;


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
    Info info;


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
        /*deneme*/
        bundle=this.getArguments();
        info= bundle.getParcelable("info");
        color=bundle.getInt("Color");


        TextView title = (TextView) getActivity().findViewById(R.id.tv_sablon1_title);
        title.setText(info.getTitle());
        title.setTextColor(color);


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
//            title.setTextColor(color);
            maintext.setTextColor(color);
            family1.setTextColor(color);
            family2.setTextColor(color);
            adress.setTextColor(color);
            tag.setTextColor(color);
            time.setTextColor(color);
            date.setTextColor(color);




        }







    @Override
    protected void handlers() {

    }
}
