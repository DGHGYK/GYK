package com.inwhiter.inviteapp.project.Fragment.Fragments;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.widget.Button;

import com.android.vending.billing.IInAppBillingService;
import com.inwhiter.inviteapp.project.Fragment.BaseFragment;
import com.inwhiter.inviteapp.project.R;

import java.util.ArrayList;
import java.util.List;

public class PurchaseFragment extends BaseFragment {

    Button purchase10;
    Button purchase50;
    Button purchase100;
    Button purchase500;
    IInAppBillingService mService;
    ServiceConnection mServiceConn;



    public PurchaseFragment() {
    }

    @Override
    protected int getFID() {
        return R.layout.fragment_purchase;
    }

    @Override
    protected void init() {
        purchase10 = (Button)getActivity().findViewById(R.id.bt_purchase_10);
        purchase50 = (Button)getActivity().findViewById(R.id.bt_purchase_50);
        purchase100 = (Button)getActivity().findViewById(R.id.bt_purchase_100);
        purchase500 = (Button)getActivity().findViewById(R.id.bt_purchase_500);
        mServiceConn = new ServiceConnection() {
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

        String base64EncodedPublicKey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAg9C8RqdHJwKBNJCPG9T+aAaL/NIr4/5Hsf70V5Z3bTIL/O/JVBCh9EQqXHbZcweMnTptJFxkKLHE0qxG1qkL+8xIeSgux45h/qygfa36vI+ul14IEOP64QZf6ZPWIk+NXEqSrql4XTepGhsM+mllXDvooojURg88lWF7X/pnG5JwjcVph2+Nb6kFJqP3LiSTxpawE4P61tEC8ar/WgwRzaDTq0GuGmbR6Dqdat/b5uM1TEfU84Te37wgyS2+Vja2BFRocMi26rmF4vXGltCD0ahxcZssxPFZW0B+aTC/bV/pO5b+hwBkJbSZ19sCqmoIDpD8oXN4JHNhrOw5f83z7wIDAQAB";

        // compute your public key and store it in base64EncodedPublicKey
        IabHelper mHelper = new IabHelper(this, base64EncodedPublicKey);

        List additionalSkuList = new ArrayList();
        additionalSkuList.add(SKU_APPLE);
        additionalSkuList.add(SKU_BANANA);
        mHelper.queryInventoryAsync(true, additionalSkuList,
                mQueryFinishedListener);

        IabHelper.QueryInventoryFinishedListener
                mQueryFinishedListener = new IabHelper.QueryInventoryFinishedListener() {
            public void onQueryInventoryFinished(IabResult result, Inventory inventory)
            {
                if (result.isFailure()) {
                    // handle error
                    return;
                }

                String applePrice =
                        inventory.getSkuDetails(SKU_APPLE).getPrice();
                String bananaPrice =
                        inventory.getSkuDetails(SKU_BANANA).getPrice();

                // update the UI
            }
        };


    }

    @Override
    protected void handlers() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mService != null) {
            getActivity().getApplicationContext().unbindService(mServiceConn);
        }
    }
}
