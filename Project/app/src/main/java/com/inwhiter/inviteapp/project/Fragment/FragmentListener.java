package com.inwhiter.inviteapp.project.Fragment;

import android.os.Bundle;

/**
 * Created by bthnorhan on 27.10.2017.
 */

public interface FragmentListener {
    void changeFragment(String fragment);
    void changeFragment(String fragment, Bundle bundle);
    void selectTemplate(String fragment);
    void selectTemplate(String fragment, Bundle bundle);
}
