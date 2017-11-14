package com.inwhiter.inviteapp.project.Fragment.Fragments;

import android.os.Bundle;

import com.inwhiter.inviteapp.project.Fragment.BaseFragment;

public class ContactsPickerFragment extends BaseFragment {

    @Override
    protected int getFID() {
        return 0;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void handlers() {

    }

    public ContactsPickerFragment newInstance(Bundle args) {
        ContactsPickerFragment fragment = new ContactsPickerFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
