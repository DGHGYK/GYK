package com.inwhiter.inviteapp.project.Fragment.Fragments;


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

//    public TemplateExample1Fragment() {}
//
//    public static TemplateExample1Fragment newInstance(Bundle args) {
//        TemplateExample1Fragment fragment = new TemplateExample1Fragment();
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    protected int getFID() {
//        return R.layout.fragment_template_example1;
//    }
//
//    @Override
//    protected void init() {
//        /*deneme*/
//        Bundle bundle=this.getArguments();
//        Info info= bundle.getParcelable("info");
////        String temp=bundle.getString("temp");
////        Toast.makeText(getActivity(), "seçilen temp :"+temp, Toast.LENGTH_SHORT).show();
//
//        TextView s1_title = (TextView) getActivity().findViewById(R.id.tv_sablon1_title);
//        s1_title.setText(info.getTitle());
//
//
//
//    }
//
//
//    @Override
//    protected void handlers() {
//
//    }


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
        Bundle bundle=this.getArguments();
        Info info= bundle.getParcelable("info");
        int color=bundle.getInt("Color");

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
