package com.inwhiter.inviteapp.project.Fragment.Fragments;


import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;

import com.inwhiter.inviteapp.project.Fragment.BaseFragment;
import com.inwhiter.inviteapp.project.Fragment.FragmentController;
import com.inwhiter.inviteapp.project.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends BaseFragment {

    Button purchase;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getFID() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void init() {//ONCREATE İŞLEMLERİN BURADA OLACAK
        purchase = (Button) getActivity().findViewById(R.id.bt_profile_purchase);
        purchase.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                listener.changeFragment(FragmentController.PURCHASE);
            }
        });

    }

    @Override
    protected void handlers() {

    }


}
