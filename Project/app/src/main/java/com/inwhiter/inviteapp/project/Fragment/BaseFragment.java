package com.inwhiter.inviteapp.project.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by bthnorhan on 27.10.2017.
 */

public abstract class BaseFragment extends Fragment {

    protected FragmentListener listener;

    protected abstract int getFID();
    protected abstract void init();
    protected abstract void handlers();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        handlers();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getFID(), container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (FragmentListener) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listener = null;
    }
}
