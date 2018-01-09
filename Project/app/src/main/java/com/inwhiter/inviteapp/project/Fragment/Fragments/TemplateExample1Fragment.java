package com.inwhiter.inviteapp.project.Fragment.Fragments;


import android.os.Bundle;

import com.inwhiter.inviteapp.project.Fragment.BaseFragment;
import com.inwhiter.inviteapp.project.Fragment.FragmentController;
import com.inwhiter.inviteapp.project.R;

public class TemplateExample1Fragment extends BaseFragment {


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
    }


    @Override
    protected void handlers() {

    }
}
