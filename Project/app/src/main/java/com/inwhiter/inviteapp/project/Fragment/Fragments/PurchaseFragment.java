package com.inwhiter.inviteapp.project.Fragment.Fragments;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.vending.billing.IInAppBillingService;
import com.inwhiter.inviteapp.project.Fragment.BaseFragment;
import com.inwhiter.inviteapp.project.R;
import com.inwhiter.inviteapp.project.Utils.IabBroadcastReceiver;
import com.inwhiter.inviteapp.project.Utils.IabBroadcastReceiver.IabBroadcastListener;
import com.inwhiter.inviteapp.project.Utils.IabHelper;
import com.inwhiter.inviteapp.project.Utils.IabHelper.IabAsyncInProgressException;
import com.inwhiter.inviteapp.project.Utils.IabResult;
import com.inwhiter.inviteapp.project.Utils.Inventory;
import com.inwhiter.inviteapp.project.Utils.Purchase;



public class PurchaseFragment extends BaseFragment implements IabBroadcastListener {

    Button purchase10;
    Button purchase50;
    Button purchase100;
    Button purchase500;
    IInAppBillingService mService;
    ServiceConnection mServiceConn;
    // Debug tag, for logging
    static final String TAG = "InwhiterPurchase";
    IabHelper mHelper;

    // Provides purchase notification while this app is running
    IabBroadcastReceiver mBroadcastReceiver;
    static final String ADET_50="davetiye50";
    static final String ADET_100="davetiye100";
    static final String ADET_500="davetiye500";
    static final String ADET_10="davetiye10";
    static final int RC_REQUEST=9900;

    int alinanDavetiyeSayisi=0;


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
        mHelper = new IabHelper(getContext(), base64EncodedPublicKey);

        // enable debug logging (for a production application, you should set this to false).
        mHelper.enableDebugLogging(true);
        // Start setup. This is asynchronous and the specified listener
        // will be called once setup completes.

        Log.d(TAG, "Starting setup.");
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                Log.d(TAG, "Setup finished.");

                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    complain("Problem setting up in-app billing: " + result);
                    return;
                }

                // Have we been disposed of in the meantime? If so, quit.
                if (mHelper == null) return;

                // Important: Dynamically register for broadcast messages about updated purchases.
                // We register the receiver here instead of as a <receiver> in the Manifest
                // because we always call getPurchases() at startup, so therefore we can ignore
                // any broadcasts sent while the app isn't running.
                // Note: registering this listener in an Activity is a bad idea, but is done here
                // because this is a SAMPLE. Regardless, the receiver must be registered after
                // IabHelper is setup, but before first call to getPurchases().
                mBroadcastReceiver = new IabBroadcastReceiver(PurchaseFragment.this);
                IntentFilter broadcastFilter = new IntentFilter(IabBroadcastReceiver.ACTION);
                getActivity().registerReceiver(mBroadcastReceiver, broadcastFilter);

                // IAB is fully set up. Now, let's get an inventory of stuff we own.
                Log.d(TAG, "Setup successful. Querying inventory.");
                try {
                    mHelper.queryInventoryAsync(mGotInventoryListener);
                } catch (IabAsyncInProgressException e) {
                    complain("Error querying inventory. Another async operation in progress.");
                }
            }
        });

        purchase100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                davetiyeSatinAl(ADET_100);
            }
        });

    }

    @Override
    protected void handlers() {

    }

    // Listener that's called when we finish querying the items and subscriptions we own
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            Log.d(TAG, "Envanter sorgusu bitti.");

            // Have we been disposed of in the meantime? If so, quit.
            if (mHelper == null) return;

            // Is it a failure?
            if (result.isFailure()) {
                complain("Envanter sorgusunda hata: " + result);
                return;
            }

            Log.d(TAG, "Envanter sorgusu başarılı.");

            // Check for gas delivery -- if we own gas, we should fill up the tank immediately
            Purchase purchase100 = inventory.getPurchase(ADET_100);
            if (purchase100 != null && verifyDeveloperPayload(purchase100)) {
                Log.d(TAG, "100 davetiye bulundu");
                try {
                    mHelper.consumeAsync(inventory.getPurchase(ADET_100), mConsumeFinishedListener);
                } catch (IabAsyncInProgressException e) {
                    complain("100 davetiye alınırken hata oluştu. Asenkron başka bir işlem yapılıyor.");
                }
                return;
            }

            updateUi();
            setWaitScreen(false);
            Log.d(TAG, "Envanter sorgusu bitti, ekran düzenleniyor.");
        }
    };

    @Override
    public void receivedBroadcast() {
        // Received a broadcast notification that the inventory of items has changed
        Log.d(TAG, "Bildirim yayını alındı. Envanter sorgusu yapılıyor");
        try {
            mHelper.queryInventoryAsync(mGotInventoryListener);
        } catch (IabAsyncInProgressException e) {
            complain("Envanter sorgusunda hata oluştu. Asenkron başka bir işlem yapılıyor.");
        }
    }



    // User clicked the "Buy Gas" button
    public void davetiyeSatinAl(String paketTuru) {
        Log.d(TAG, "Davetiye satın alma isteği geldi");

        // launch the gas purchase UI flow.
        // We will be notified of completion via mPurchaseFinishedListener
        setWaitScreen(true);
        Log.d(TAG, paketTuru+" için satın alma işlemi yapılıyor.");

        /* TODO: for security, generate your payload here for verification. See the comments on
         *        verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use
         *        an empty string, but on a production app you should carefully generate this. */
        String payload = "";

        try {
            mHelper.launchPurchaseFlow(getActivity(), paketTuru, RC_REQUEST,
                    mPurchaseFinishedListener, payload);
        } catch (IabAsyncInProgressException e) {
            complain("Satın alma hatası. Başka bir asenkron işlem var");
            setWaitScreen(false);
        }
    }


    /** Verifies the developer payload of a purchase. */
    boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();

        /*
         * TODO: verify that the developer payload of the purchase is correct. It will be
         * the same one that you sent when initiating the purchase.
         *
         * WARNING: Locally generating a random string when starting a purchase and
         * verifying it here might seem like a good approach, but this will fail in the
         * case where the user purchases an item on one device and then uses your app on
         * a different device, because on the other device you will not have access to the
         * random string you originally generated.
         *
         * So a good developer payload has these characteristics:
         *
         * 1. If two different users purchase an item, the payload is different between them,
         *    so that one user's purchase can't be replayed to another user.
         *
         * 2. The payload must be such that you can verify it even when the app wasn't the
         *    one who initiated the purchase flow (so that items purchased by the user on
         *    one device work on other devices owned by the user).
         *
         * Using your own server to store and verify developer payloads across app
         * installations is recommended.
         */

        return true;
    }

    // Callback for when a purchase is finished
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            Log.d(TAG, "Satın alma tamamlandı: " + result + ", purchase: " + purchase);

            // if we were disposed of in the meantime, quit.
            if (mHelper == null) return;

            if (result.isFailure()) {
            complain("Satın alma hatası: " + result);
                setWaitScreen(false);
                return;
            }
            if (!verifyDeveloperPayload(purchase)) {
                complain("Satın ama hatası. Doğrulama başarısız.");
                setWaitScreen(false);
                return;
            }

            Log.d(TAG, "Satın alma başarılı");

            if (purchase.getSku().equals(ADET_100)) {

                Log.d(TAG, "100 davetiye alındı");
                try {
                    mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                } catch (IabAsyncInProgressException e) {
                    complain("Satın alma sonrası elde edilemedi. Asenkron başka bir işlem var.");
                    setWaitScreen(false);
                    return;
                }
            }

        }
    };

    // Called when consumption is complete
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        public void onConsumeFinished(Purchase purchase, IabResult result) {
            Log.d(TAG, "Alım bitti. Atın alma: " + purchase + ", sonuç: " + result);

            // if we were disposed of in the meantime, quit.
            if (mHelper == null) return;

            // We know this is the "gas" sku because it's the only one we consume,
            // so we don't check which sku was consumed. If you have more than one
            // sku, you probably should check...
            if (result.isSuccess()) {
                // successfully consumed, so we apply the effects of the item in our
                // game world's logic, which in our case means filling the gas tank a bit
                Log.d(TAG, "Alım bitti");
                if (purchase.getSku().equals(ADET_100)) {
                    alinanDavetiyeSayisi += 100;
                }
                saveData();
                alert("Toplam devetiye sayısı " + String.valueOf(alinanDavetiyeSayisi));
            }
            else {
                complain("Alım hatası: " + result);
            }
            updateUi();
            setWaitScreen(false);
            Log.d(TAG, "Alım sonu.");
        }
    };


    // We're being destroyed. It's important to dispose of the helper here!
    @Override
    public void onDestroy() {
        super.onDestroy();

        // very important:
        if (mBroadcastReceiver != null) {
            getActivity().unregisterReceiver(mBroadcastReceiver);
        }

        // very important:
        Log.d(TAG, "Destroying helper.");
        if (mHelper != null) {
            mHelper.disposeWhenFinished();
            mHelper = null;
        }
    }

    // updates UI to reflect model
    public void updateUi() {

    }

    // Enables or disables the "please wait" screen.
    void setWaitScreen(boolean set) {

    }

    void complain(String message) {
        Log.e(TAG, "**** TrivialDrive Error: " + message);
        alert("Error: " + message);
    }

    void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(getContext());
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        Log.d(TAG, "Showing alert dialog: " + message);
        bld.create().show();
    }

    void saveData() {


    }


}
