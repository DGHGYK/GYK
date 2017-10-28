package com.inwhiter.inviteapp.project.FragmentB;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.inwhiter.inviteapp.project.FragmentB.Fragments.MainFragment;

/**
 * Created by bthnorhan on 27.10.2017.
 */

public class FragmentController {
    private static FragmentController instance = null;
    public static final String MAIN= "fragment_main";

    private FragmentController() { }

    public synchronized static FragmentController getInstance() {
        if (instance == null) {
            instance = new FragmentController();
        }
        return instance;
    }
    public Fragment getFragment(String fragment){
        return this.getFragment(fragment,null);
    }
    public Fragment getFragment(String fragment, Bundle bundle) {
        if (fragment == null) { return null; }

        if (fragment == MAIN)
        {
            return new MainFragment();
        }
        else
        {
            return null;
        }
    }
}
