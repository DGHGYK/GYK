package com.inwhiter.inviteapp.project.BusineesH;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inwhiter.inviteapp.project.R;


public class MainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        //  int id= (int) getArguments().get("layoutid");
        View rootView = inflater.inflate(R.layout.h_contextmenu_fragment_main, container, false);
       /* if (R.layout.h_sablon1 == bundle) {
             rootView = inflater.inflate(R.layout.h_sablon1, container, false);

        }*/


        return rootView;
    }
}