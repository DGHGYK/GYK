package com.inwhiter.inviteapp.project.ActivityB;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.inwhiter.inviteapp.project.CustomB.CustomBottomView.CustomBottomView;
import com.inwhiter.inviteapp.project.CustomB.CustomBottomView.CustomBottomViewListener;
import com.inwhiter.inviteapp.project.CustomB.CustomBottomView.CustomBottomViewOption;
import com.inwhiter.inviteapp.project.Fragment.FragmentController;
import com.inwhiter.inviteapp.project.Fragment.FragmentListener;
import com.inwhiter.inviteapp.project.R;

public class MainActivity extends AppCompatActivity implements FragmentListener {

    private CustomBottomView cbv_main_bottombar;
    private FrameLayout fl_main_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cbv_main_bottombar = (CustomBottomView) findViewById(R.id.cbv_main_bottombar);
        cbv_main_bottombar.setCustomBottomViewListener(new CustomBottomViewListener() {
            @Override
            public void itemClickListener(CustomBottomViewOption customBottomViewOption) {
                if (customBottomViewOption == CustomBottomViewOption.USER)
                {
                    
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
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentController fragmentController = FragmentController.getInstance();
        Fragment tFragment = fragmentController.getFragment(fragment, bundle);
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.fl_main_container, tFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
