package com.inwhiter.inviteapp.project.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

<<<<<<< HEAD:Project/app/src/main/java/com/inwhiter/inviteapp/project/FragmentB/FragmentController.java
import com.inwhiter.inviteapp.project.FragmentB.Fragments.MainFragment;
import com.inwhiter.inviteapp.project.FragmentH.ProfileFragment;
=======
import com.inwhiter.inviteapp.project.Fragment.Fragments.AddManuallyFragment;
import com.inwhiter.inviteapp.project.Fragment.Fragments.GuestFragment;
import com.inwhiter.inviteapp.project.Fragment.Fragments.MainFragment;
>>>>>>> e8dc00f267664042c81e8ae0968d10ec76c500ff:Project/app/src/main/java/com/inwhiter/inviteapp/project/Fragment/FragmentController.java

/**
 * Created by bthnorhan on 27.10.2017.
 */

public class FragmentController {
    private static FragmentController instance = null;
    public static final String MAIN= "fragment_main";
<<<<<<< HEAD:Project/app/src/main/java/com/inwhiter/inviteapp/project/FragmentB/FragmentController.java
    public static final String PROFILE="fragment_profile";
=======
    public static final String GUEST= "fragment_guest";
    public static final String ADDMANUALLY= "fragment_add_manually";
>>>>>>> e8dc00f267664042c81e8ae0968d10ec76c500ff:Project/app/src/main/java/com/inwhiter/inviteapp/project/Fragment/FragmentController.java

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
<<<<<<< HEAD:Project/app/src/main/java/com/inwhiter/inviteapp/project/FragmentB/FragmentController.java
        else if(fragment == PROFILE){
            return new ProfileFragment();

        }

=======
        else if (fragment == GUEST)
        {
            return new GuestFragment().newInstance(bundle);
        }
        else if (fragment == ADDMANUALLY)
        {
            return new AddManuallyFragment().newInstance(bundle);
        }
>>>>>>> e8dc00f267664042c81e8ae0968d10ec76c500ff:Project/app/src/main/java/com/inwhiter/inviteapp/project/Fragment/FragmentController.java
        else
        {
            return null;
        }
    }
}
