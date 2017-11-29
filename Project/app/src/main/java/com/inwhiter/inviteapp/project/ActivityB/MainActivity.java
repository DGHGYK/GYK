package com.inwhiter.inviteapp.project.ActivityB;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.inwhiter.inviteapp.project.CustomB.CustomBottomView.CustomBottomView;
import com.inwhiter.inviteapp.project.CustomB.CustomBottomView.CustomBottomViewListener;
import com.inwhiter.inviteapp.project.CustomB.CustomBottomView.CustomBottomViewOption;
import com.inwhiter.inviteapp.project.Fragment.FragmentController;
import com.inwhiter.inviteapp.project.Fragment.FragmentListener;
import com.inwhiter.inviteapp.project.R;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FragmentListener, OnMenuItemClickListener {

    private CustomBottomView cbv_main_bottombar;
    private FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;
    private Toolbar mToolbar;


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
                FTransaction(FragmentController.PROFILE);
            }
            else if(customBottomViewOption == CustomBottomViewOption.ADD)
            {// TODO: 14.11.2017 buraya ekledin ben topic menü diye
                FTransaction(FragmentController.TOPIC);
            }
            else if(customBottomViewOption == CustomBottomViewOption.HOME)
            {
                FTransaction(FragmentController.MAIN);
            }
            }
        });

        FTransaction(FragmentController.MAIN);
    }

    @Override
    public void changeFragment(String tag) {
        FTransaction(tag);
    }

    @Override
    public void changeFragment(String tag, Bundle bundle) {
        FTransaction(tag,bundle);
    }

    protected void FTransaction(String fragment) {
        this.FTransaction(fragment, null);
    }

    protected void FTransaction(String fragment, Bundle bundle) {
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
}
