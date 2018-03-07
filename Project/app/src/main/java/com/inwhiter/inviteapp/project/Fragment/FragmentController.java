package com.inwhiter.inviteapp.project.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.inwhiter.inviteapp.project.Fragment.Fragments.AddManuallyFragment;
import com.inwhiter.inviteapp.project.Fragment.Fragments.ContactsPickerFragment;
import com.inwhiter.inviteapp.project.Fragment.Fragments.GuestEditFragment;
import com.inwhiter.inviteapp.project.Fragment.Fragments.GuestFragment;
import com.inwhiter.inviteapp.project.Fragment.Fragments.InfoFragment;
import com.inwhiter.inviteapp.project.Fragment.Fragments.LayoutSSFragment;
import com.inwhiter.inviteapp.project.Fragment.Fragments.MainFragment;
import com.inwhiter.inviteapp.project.Fragment.Fragments.ProfileFragment;
import com.inwhiter.inviteapp.project.Fragment.Fragments.StatusFragment;
import com.inwhiter.inviteapp.project.Fragment.Fragments.TemplateExample1Fragment;
import com.inwhiter.inviteapp.project.Fragment.Fragments.TemplateExample2Fragment;
import com.inwhiter.inviteapp.project.Fragment.Fragments.TemplateExample3Fragment;
import com.inwhiter.inviteapp.project.Fragment.Fragments.TemplateFragment;
import com.inwhiter.inviteapp.project.Fragment.Fragments.TopicFragment;


/**
 * Created by bthnorhan on 27.10.2017.
 */

public class FragmentController {
    private static FragmentController instance = null;

    public static final String MAIN = "fragment_main";
    public static final String GUEST = "fragment_guest";
    public static final String GUEST_EDIT = "fragment_guest_edit";
    public static final String ADD_MANUALLY = "fragment_add_manually";
    public static final String STATUS = "fragment_status";
    public static final String TEMPLATE = "fragment_template";
    public static final String PROFILE="fragment_profile";

    public static final String TOPIC="fragment_topic";
    public static final String INFO="fragment_info";
    public static final String CONTACTS_PICKER="fragment_contacts_picker";
    public static final String LAYOUTSS="fragment_layout_ss";

    public String currentFragment = null;

    //Şablonlar
    public static final String TEMPLATE1 ="fragment_template_example1";
    public static final String TEMPLATE2 ="fragment_template_example2";
    public static final String TEMPLATE3 ="fragment_template_example3";


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
           currentFragment=fragment;
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
        else if (fragment == GUEST_EDIT)
        {
            return new GuestEditFragment().newInstance(bundle);
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
        else if (fragment == TOPIC)
        {
            return new TopicFragment();
        }
        else if (fragment == INFO)
        {
            return new InfoFragment().newInstance(bundle);
        }
        else if (fragment == LAYOUTSS)
        {
            return new LayoutSSFragment().newInstance(bundle);
        }
        else if (fragment == TEMPLATE1)
        {
            return new TemplateExample1Fragment().newInstance(bundle);
        }

        else if (fragment == TEMPLATE2)
        {
            return new TemplateExample2Fragment().newInstance(bundle);
        }
        else if (fragment == TEMPLATE3) {
            return new TemplateExample3Fragment().newInstance(bundle);
        }
        else if (fragment == STATUS)
        {
            return new StatusFragment().newInstance(bundle);

        }

        else
        {
            return null;
        }
    }
    public String getCurrentFragment(){
        return currentFragment;
    }
}
