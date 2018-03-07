package com.inwhiter.inviteapp.project.Fragment.Fragments;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;

import com.android.vending.billing.IInAppBillingService;
import com.inwhiter.inviteapp.project.Fragment.BaseFragment;
import com.inwhiter.inviteapp.project.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends BaseFragment {

    Button purchase;
    IInAppBillingService mService;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getFID() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void init() {//ONCREATE İŞLEMLERİN BURADA OLACAK
        ServiceConnection mServiceConn = new ServiceConnection() {
            @Override
            public void onServiceDisconnected(ComponentName name) {
                mService = null;
            }

            @Override
            public void onServiceConnected(ComponentName name,
                                           IBinder service) {
                mService = IInAppBillingService.Stub.asInterface(service);
            }
        };
        Intent serviceIntent =
                new Intent("com.android.vending.billing.InAppBillingService.BIND");
        serviceIntent.setPackage("com.android.vending");
        getActivity().bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE);
        purchase = (Button) getActivity().findViewById(R.id.bt_profile_purchase);



    }

    @Override
    protected void handlers() {
        purchase.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
    }
}
