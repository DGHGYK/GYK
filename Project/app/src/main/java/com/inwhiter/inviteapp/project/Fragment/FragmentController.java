package com.inwhiter.inviteapp.project.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.inwhiter.inviteapp.project.Fragment.Fragments.MainFragment;
import com.inwhiter.inviteapp.project.Fragment.Fragments.ProfileFragment;
import com.inwhiter.inviteapp.project.Fragment.Fragments.AddManuallyFragment;
import com.inwhiter.inviteapp.project.Fragment.Fragments.ContactsPickerFragment;
import com.inwhiter.inviteapp.project.Fragment.Fragments.GuestFragment;
import com.inwhiter.inviteapp.project.Fragment.Fragments.TemplateFragment;


/**
 * Created by bthnorhan on 27.10.2017.
 */

public class FragmentController {
    private static FragmentController instance = null;

    public static final String MAIN = "fragment_main";
    public static final String GUEST = "fragment_guest";
    public static final String ADD_MANUALLY = "fragment_add_manually";
    public static final String TEMPLATE = "fragment_template";
    public static final String PROFILE="fragment_profile";
    public static final String CONTACTS_PICKER="fragment_contacts_picker";


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
        else if(fragment == PROFILE){
            return new ProfileFragment();
        }
        else if (fragment == GUEST)
        {
            return new GuestFragment().newInstance(bundle);
        }
        else if (fragment == ADD_MANUALLY)
        {
            return new AddManuallyFragment().newInstance(bundle);
        }else if (fragment == CONTACTS_PICKER)
        {
            return new ContactsPickerFragment().newInstance(bundle);
        }
        else if (fragment == TEMPLATE)
        {
            return new TemplateFragment().newInstance(bundle);
        }
        else
        {
            return null;
        }
    }
}
