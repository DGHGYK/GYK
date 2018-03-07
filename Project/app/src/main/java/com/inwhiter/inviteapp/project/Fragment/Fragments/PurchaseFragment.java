package com.inwhiter.inviteapp.project.Fragment.Fragments;

import android.view.View;
import android.widget.Button;

import com.inwhiter.inviteapp.project.ActivityB.MainActivity;
import com.inwhiter.inviteapp.project.Fragment.BaseFragment;
import com.inwhiter.inviteapp.project.R;


public class PurchaseFragment extends BaseFragment {


    Button purchase10;
    Button purchase50;
    Button purchase100;
    Button purchase500;
    static final String ADET_50 = "davetiye50";
    static final String ADET_100 = "davetiye100";
    static final String ADET_500 = "davetiye500";
    static final String ADET_10 = "davetiye10";
    public PurchaseFragment() {
    }

    @Override
    protected int getFID() {
        return R.layout.fragment_purchase;
    }



    @Override
    protected void init() {
        purchase10 = (Button) getActivity().findViewById(R.id.bt_purchase_10);
        purchase50 = (Button) getActivity().findViewById(R.id.bt_purchase_50);
        purchase100 = (Button) getActivity().findViewById(R.id.bt_purchase_100);
        purchase500 = (Button) getActivity().findViewById(R.id.bt_purchase_500);

        purchase10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).davetiyeSatinAl(ADET_10);
            }
        });
        purchase50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).davetiyeSatinAl(ADET_50);
            }
        });
        purchase100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).davetiyeSatinAl(ADET_100);
            }
        });
        purchase500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).davetiyeSatinAl(ADET_500);
            }
        });

    }

    @Override
    protected void handlers() {

    }




}
