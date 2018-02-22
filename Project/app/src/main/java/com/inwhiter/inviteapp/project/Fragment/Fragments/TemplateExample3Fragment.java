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
public class TemplateExample3Fragment extends BaseFragment {


    public TemplateExample3Fragment() {
        // Required empty public constructor
    }

    public static TemplateExample3Fragment newInstance(Bundle args) {
        TemplateExample3Fragment fragment = new TemplateExample3Fragment();
        fragment.setArguments(args);
        return fragment;}



    @Override
    protected int getFID() {
        return R.layout.fragment_template_example3;
    }

    @Override
    protected void init() {
        Bundle bundle=this.getArguments();
        Info info= bundle.getParcelable("info");
        int color=bundle.getInt("Color");

        TextView title = (TextView) getActivity().findViewById(R.id.tv_sablon3_title);
        title.setText(info.getTitle());
        title.setTextColor(color);

        TextView maintext = (TextView) getActivity().findViewById(R.id.tv_sablon3_maintext);
        maintext.setText(info.getText());
        maintext.setTextColor(color);

        TextView family1 = (TextView) getActivity().findViewById(R.id.tv_sablon3_family1);
        family1.setText(info.getFamily1());
        family1.setTextColor(color);

        TextView family2 = (TextView) getActivity().findViewById(R.id.tv_sablon3_family2);
        family2.setText(info.getFamily2());
        family2.setTextColor(color);

        TextView adress = (TextView) getActivity().findViewById(R.id.tv_sablon3_adress);
        adress.setText(info.getAddress());
        adress.setTextColor(color);
        TextView tag = (TextView) getActivity().findViewById(R.id.tv_sablon3_tag);

        tag.setText(info.getHashtag());
        tag.setTextColor(color);

        TextView time = (TextView) getActivity().findViewById(R.id.tv_sablon3_time);
        time.setText(info.getTime());
        time.setTextColor(color);

        TextView date= (TextView) getActivity().findViewById(R.id.tv_sablon3_date);
        date.setText(info.getDate());
        date.setTextColor(color);



    }

    @Override
    protected void handlers() {

    }



}
