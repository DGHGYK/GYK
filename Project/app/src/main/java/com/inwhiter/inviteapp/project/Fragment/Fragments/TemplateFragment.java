package com.inwhiter.inviteapp.project.Fragment.Fragments;


import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.inwhiter.inviteapp.project.Fragment.BaseFragment;
import com.inwhiter.inviteapp.project.Fragment.FragmentController;
import com.inwhiter.inviteapp.project.R;

public class TemplateFragment extends BaseFragment {


    public TemplateFragment() { }

    @Override
    protected int getFID() {
        return R.layout.fragment_template;
    }

    @Override
    protected void init() {
        setHasOptionsMenu(true);
        listener.selectTemplate(FragmentController.TEMPLATE1);
    }

    @Override
    protected void handlers() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
