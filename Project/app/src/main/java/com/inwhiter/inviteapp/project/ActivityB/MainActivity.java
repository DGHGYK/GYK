package com.inwhiter.inviteapp.project.ActivityB;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.vending.billing.IInAppBillingService;
import com.google.firebase.auth.FirebaseAuth;
import com.inwhiter.inviteapp.project.CustomB.CustomBottomView.CustomBottomView;
import com.inwhiter.inviteapp.project.CustomB.CustomBottomView.CustomBottomViewListener;
import com.inwhiter.inviteapp.project.CustomB.CustomBottomView.CustomBottomViewOption;
import com.inwhiter.inviteapp.project.Fragment.FragmentController;
import com.inwhiter.inviteapp.project.Fragment.FragmentListener;
import com.inwhiter.inviteapp.project.R;
import com.inwhiter.inviteapp.project.Utils.IabBroadcastReceiver;
import com.inwhiter.inviteapp.project.Utils.IabBroadcastReceiver.IabBroadcastListener;
import com.inwhiter.inviteapp.project.Utils.IabHelper;
import com.inwhiter.inviteapp.project.Utils.IabResult;
import com.inwhiter.inviteapp.project.Utils.Inventory;
import com.inwhiter.inviteapp.project.Utils.Purchase;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FragmentListener, OnMenuItemClickListener,IabBroadcastListener  {

    private CustomBottomView cbv_main_bottombar;
    private FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;
    private Toolbar mToolbar;
    // Debug tag, for logging
    static final String TAG = "InwhiterPurchase";
    static final String ADET_50 = "davetiye50";
    static final String ADET_100 = "davetiye100";
    static final String ADET_500 = "davetiye500";
    static final String ADET_10 = "davetiye10";
    static final int RC_REQUEST = 9900;
    IInAppBillingService mService;
    ServiceConnection mServiceConn;
    IabHelper mHelper;
    // Provides purchase notification while this app is running
    IabBroadcastReceiver mBroadcastReceiver;
    int alinanDavetiyeSayisi = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cbv_main_bottombar = (CustomBottomView) findViewById(R.id.cbv_main_bottombar);
        cbv_main_bottombar.setCustomBottomViewListener(new CustomBottomViewListener() {
            @Override
            public void itemClickListener(CustomBottomViewOption customBottomViewOption) {

            if(customBottomViewOption == CustomBottomViewOption.USER)
            {
                changeFragment(FragmentController.PROFILE);
            }
            else if(customBottomViewOption == CustomBottomViewOption.ADD)
            {// TODO: 14.11.2017 buraya ekledin ben topic menü diye
                changeFragment(FragmentController.TOPIC);
            }
            else if(customBottomViewOption == CustomBottomViewOption.HOME)
            {
                changeFragment(FragmentController.MAIN);
            }
            }
        });

        changeFragment(FragmentController.MAIN);


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
        bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE);

        String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAg9C8RqdHJwKBNJCPG9T+aAaL/NIr4/5Hsf70V5Z3bTIL/O/JVBCh9EQqXHbZcweMnTptJFxkKLHE0qxG1qkL+8xIeSgux45h/qygfa36vI+ul14IEOP64QZf6ZPWIk+NXEqSrql4XTepGhsM+mllXDvooojURg88lWF7X/pnG5JwjcVph2+Nb6kFJqP3LiSTxpawE4P61tEC8ar/WgwRzaDTq0GuGmbR6Dqdat/b5uM1TEfU84Te37wgyS2+Vja2BFRocMi26rmF4vXGltCD0ahxcZssxPFZW0B+aTC/bV/pO5b+hwBkJbSZ19sCqmoIDpD8oXN4JHNhrOw5f83z7wIDAQAB";
        // compute your public key and store it in base64EncodedPublicKey
        mHelper = new IabHelper(this, base64EncodedPublicKey);

        // enable debug logging (for a production application, you should set this to false).
        mHelper.enableDebugLogging(true);
        // Start setup. This is asynchronous and the specified listener
        // will be called once setup completes.

        Log.d(TAG, "Starting setup.");
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                Log.d(TAG, "Uygulama içi satın alım başarıyla kuruldu.");

                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    complain("Uygulama içi satın alım kurulamadı. " + result);
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
                mBroadcastReceiver = new IabBroadcastReceiver(MainActivity.this);
                IntentFilter broadcastFilter = new IntentFilter(IabBroadcastReceiver.ACTION);
                registerReceiver(mBroadcastReceiver, broadcastFilter);

                // IAB is fully set up. Now, let's get an inventory of stuff we own.
                Log.d(TAG, "Setup successful. Querying inventory.");
                try {
                    mHelper.queryInventoryAsync(mGotInventoryListener);
                } catch (IabHelper.IabAsyncInProgressException e) {
                    complain("Error querying inventory. Another async operation in progress.");
                }
            }
        });

    }

    @Override
    public void changeFragment(String fragment) {
        changeFragment(fragment,null);
    }

    @Override
    public void changeFragment(String fragment, Bundle bundle) {
        if (fragment.equals(FragmentController.TEMPLATE))
        {
            contextMenu();
        }
        else
        {
            hideToolbar();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentController fragmentController = FragmentController.getInstance();
        Fragment tFragment = fragmentController.getFragment(fragment, bundle);
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.fl_main_container, tFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void selectTemplate(String fragment) {
        selectTemplate(fragment,null);
    }

    @Override
    public void selectTemplate(String fragment, Bundle bundle) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentController fragmentController = FragmentController.getInstance();
        Fragment tFragment = fragmentController.getFragment(fragment, bundle);
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.fl_template_container, tFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void hideToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setVisibility(View.GONE);
    }

    public void contextMenu(){
        /* context menu*/
        fragmentManager = getSupportFragmentManager();
        initToolbar();
        initMenuFragment();
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mToolBarTextView = (TextView) findViewById(R.id.text_view_toolbar_title);
        mToolBarTextView.setText("InWhiter");
        mToolbar.setVisibility(View.VISIBLE);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
      /*  mToolbar.setNavigationIcon(R.mipmap.ic_launcher_round);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });*/
/*        String titlem="InWhiter";
        SpannableString s = new SpannableString(titlem);
        s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimaryDark)), 0, titlem.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mToolBarTextView.setText(s);*/
    }

    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(false);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(this);
    }

    private List<MenuObject> getMenuObjects() {
        List<MenuObject> menuObjects = new ArrayList<>();

        MenuObject close = new MenuObject();
        close.setResource(R.drawable.cancelm);

        MenuObject template = new MenuObject("Şablon");
        template.setResource(R.drawable.boook);

        MenuObject color = new MenuObject("Renk");
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.paintbucket);
        color.setBitmap(b);

        MenuObject font = new MenuObject("Yazı Tipi");
        BitmapDrawable bd = new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(),R.drawable.text));
        font.setDrawable(bd);

        MenuObject music = new MenuObject("Müzik");
        music.setResource(R.drawable.musicplayer);

        MenuObject save= new MenuObject("Kaydet");
        save.setResource(R.drawable.save);

        menuObjects.add(close);
        menuObjects.add(template);
        menuObjects.add(color);
        menuObjects.add(font);
        menuObjects.add(music);
        menuObjects.add(save);

        return menuObjects;
    }

    @Override
    public void onMenuItemClick(View clickedView, int position) {
        if(position==1){
            themeSelected();
        }
        else if(position==2){
            colorSelected();
        }
        else if(position==3){
            fontSelected();
        }
        else if(position==4){
            MusicAdd();
        }
        else if(position==5){
            registerLayout();
        }
    }

    private void colorSelected() {
        Log.d(getLocalClassName(), "colorSelected: ");
    }

    private void fontSelected() {
        Log.d(getLocalClassName(),"fontSelected: ");
    }

    private void MusicAdd() {
        Log.d(getLocalClassName(), "MusicAdd: ");
    }

    private void registerLayout() {
        Log.d(getLocalClassName(), "registerLayout: ");
    }

    private void themeSelected() {
        Log.d(getLocalClassName(), "themeSelected: ");
    }



    //SATIN ALMA kodlarının activityde yazılması gerektiğinden buraya ekledim. Purchase Fragment tarafından çağırılıp kullanılıyorlar

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
                if (purchase.getSku().equals(ADET_10)) {
                    alinanDavetiyeSayisi += 10;
                } else if (purchase.getSku().equals(ADET_50)) {
                    alinanDavetiyeSayisi += 50;
                } else if (purchase.getSku().equals(ADET_100)) {
                    alinanDavetiyeSayisi += 100;
                } else if (purchase.getSku().equals(ADET_500)) {
                    alinanDavetiyeSayisi += 500;
                }
                saveData();
                alert("Toplam devetiye sayısı " + String.valueOf(alinanDavetiyeSayisi));
            } else {
                complain("Alım hatası: " + result);
            }
            updateUi();
            setWaitScreen(false);
            Log.d(TAG, "Alım sonu.");
        }
    };
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


            Purchase purchase10 = inventory.getPurchase(ADET_10);
            if (purchase10 != null && verifyDeveloperPayLoad(purchase10)) {
                Log.d(TAG, "10 davetiye bulundu");
                try {
                    mHelper.consumeAsync(inventory.getPurchase(ADET_10), mConsumeFinishedListener);
                } catch (IabHelper.IabAsyncInProgressException e) {
                    complain("10 davetiye paketi sorgulanırken hata oluştu. Asenkron başka bir işlem yapılıyor.");
                }
                return;
            }
            Purchase purchase50 = inventory.getPurchase(ADET_50);
            if (purchase50 != null && verifyDeveloperPayLoad(purchase50)) {
                Log.d(TAG, "50 davetiye bulundu");
                try {
                    mHelper.consumeAsync(inventory.getPurchase(ADET_50), mConsumeFinishedListener);
                } catch (IabHelper.IabAsyncInProgressException e) {
                    complain("50 davetiye paketi sorgulanırken hata oluştu. Asenkron başka bir işlem yapılıyor.");
                }
                return;
            }
            Purchase purchase100 = inventory.getPurchase(ADET_100);
            if (purchase100 != null && verifyDeveloperPayLoad(purchase100)) {
                Log.d(TAG, "100 davetiye bulundu");
                try {
                    mHelper.consumeAsync(inventory.getPurchase(ADET_100), mConsumeFinishedListener);
                } catch (IabHelper.IabAsyncInProgressException e) {
                    complain("100 davetiye paketi sorgulanırken hata oluştu. Asenkron başka bir işlem yapılıyor.");
                }
                return;
            }
            Purchase purchase500 = inventory.getPurchase(ADET_500);
            if (purchase500 != null && verifyDeveloperPayLoad(purchase500)) {
                Log.d(TAG, "500 davetiye bulundu");
                try {
                    mHelper.consumeAsync(inventory.getPurchase(ADET_500), mConsumeFinishedListener);
                } catch (IabHelper.IabAsyncInProgressException e) {
                    complain("500 davetiye paketi sorgulanırken hata oluştu. Asenkron başka bir işlem yapılıyor.");
                }
                return;
            }


            updateUi();
            setWaitScreen(false);
            Log.d(TAG, "Envanter sorgusu bitti, ekran düzenleniyor.");
        }
    };
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
            if (!verifyDeveloperPayLoad(purchase)) {
                complain("Satın ama hatası. Doğrulama başarısız.");
                setWaitScreen(false);
                return;
            }

            Log.d(TAG, "Satın alma başarılı");

            if (purchase.getSku().equals(ADET_10) || purchase.getSku().equals(ADET_50) || purchase.getSku().equals(ADET_100) || purchase.getSku().equals(ADET_500)) {

                Log.d(TAG, "Davetiye alındı");
                try {
                    mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                } catch (IabHelper.IabAsyncInProgressException e) {
                    complain("Satın alma sonrası elde edilemedi. Asenkron başka bir işlem var.");
                    setWaitScreen(false);
                    return;
                }
            }

        }
    };

    @Override
    public void receivedBroadcast() {
        // Received a broadcast notification that the inventory of items has changed
        Log.d(TAG, "Bildirim yayını alındı. Envanter sorgusu yapılıyor");
        try {
            mHelper.queryInventoryAsync(mGotInventoryListener);
        } catch (IabHelper.IabAsyncInProgressException e) {
            complain("Envanter sorgusunda hata oluştu. Asenkron başka bir işlem yapılıyor.");
        }
    }

    // User clicked the "Buy Gas" button
    public void davetiyeSatinAl(String paketTuru) {
        Log.d(TAG, "Davetiye satın alma isteği geldi");

        // launch the gas purchase UI flow.
        // We will be notified of completion via mPurchaseFinishedListener
        setWaitScreen(true);
        Log.d(TAG, paketTuru + " için satın alma işlemi yapılıyor.");


        String payload = computePayload();

        try {
            if (mHelper != null) mHelper.flagEndAsync();



            mHelper.launchPurchaseFlow(this, paketTuru, RC_REQUEST,
                    mPurchaseFinishedListener, payload);
        } catch (IabHelper.IabAsyncInProgressException e) {
            complain("Satın alma hatası. Başka bir asenkron işlem var");
            setWaitScreen(false);
        }
    }

    /**
     * Verifies the developer payload of a purchase.
     */
    private boolean verifyDeveloperPayLoad(Purchase purchase) {
        String responsePayload = purchase.getDeveloperPayload();
        String computedPayload = computePayload();

        return responsePayload != null && responsePayload.equals(computedPayload);
    }

    // We're being destroyed. It's important to dispose of the helper here!
    @Override
    public void onDestroy() {
        super.onDestroy();

        // very important:
        if (mBroadcastReceiver != null) {
            unregisterReceiver(mBroadcastReceiver);
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
        Log.e(TAG, "**** Inwhiter Error: " + message);
        alert("Error: " + message);
    }

    void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(this);
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        Log.d(TAG, "Showing alert dialog: " + message);
        bld.create().show();
    }

    void saveData() {


    }

    private String computePayload(){
        return "INW"+ FirebaseAuth.getInstance().getCurrentUser().getUid()+"APP";
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode,Intent data)
    {

        Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);
        if (mHelper == null) return;

        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            super.onActivityResult(requestCode, resultCode, data);
        }
        else {
            Log.d(TAG, "onActivityResult handled by IABUtil.");
        }
    }



}
