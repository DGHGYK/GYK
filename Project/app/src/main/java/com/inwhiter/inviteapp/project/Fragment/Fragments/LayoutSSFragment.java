package com.inwhiter.inviteapp.project.Fragment.Fragments;


import android.app.ActionBar;
import android.content.Context;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.FirebaseDatabase;

import com.inwhiter.inviteapp.project.Fragment.BaseFragment;
import com.inwhiter.inviteapp.project.Fragment.FragmentController;
import com.inwhiter.inviteapp.project.ModelH.Media;
import com.inwhiter.inviteapp.project.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LayoutSSFragment extends BaseFragment {
    ImageView ss;
    Context context=getActivity();

    public String inviteId;
    private FirebaseDatabase database;


    public List<Media> playlist=new ArrayList<Media>();
    public ListView lv_playlist;
    public ImageView icon_play;
    public MediaPlayer mp = null;
    public double startTime = 0;
    public double finalTime = 0;
    public int oneTimeOnly = 0;
    public Handler myHandler = new Handler() ;
    public SeekBar seekBar;
    public ImageView play_pause,on_off;
    Button btn_ok;
    //public Layout layout_media_player;
    public TextView current_duration,total_duration,current_music;

    public LayoutSSFragment()
    {

    }

    public static LayoutSSFragment newInstance (Bundle args) {
        LayoutSSFragment fragment = new LayoutSSFragment();
        fragment.setArguments(args);
        return fragment;

    }


    @Override
    protected int getFID() {
        return R.layout.fragment_layout_ss;
    }

    @Override
    protected void init() {//burası böyle olmalı sanırım @gamze senin burası ama
        Bundle bundle= this.getArguments();
//        if(bundle!=null){
//            inviteId = bundle.getString("inviteId");
//        }
//
//
//        /*custom action bar*//////////////////////////////////////////////////////////
//       getActivity().getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getActivity().getActionBar().setDisplayShowCustomEnabled(true);
//       getActivity().getActionBar().setCustomView(R.layout.h_custom_actionbar_ss);
//        View customSS =getActivity().getActionBar().getCustomView();
//        Button addguests=(Button)customSS.findViewById(R.id.btn_actionbarSS_addguests);
//
//        addguests.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle bundle=new Bundle();
//                bundle.putString("inviteId", inviteId);
//                listener.changeFragment(FragmentController.GUEST,bundle);
//              /*  Intent intent = new Intent(LayoutSS.this, GuestActivity.class);
//                intent.putExtra("inviteId", inviteId);
//                startActivity(intent);*/
//            }
//        });
//






        ss= (ImageView) getActivity().findViewById(R.id.imageView);
        byte[] encoded = (byte[]) getArguments().get("ss");
        Glide.with(getActivity())//burasını da tam yaptım denemez test edemediğim için üzülüyorum :(
                .load(encoded)
                // .asBitmap()
                //.centerCrop()
                .into(ss);
        //Button btn=(Button)findViewById(R.id.music);



        //medi player eklentisi





    }

    @Override
    protected void handlers() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_layout_ss, container, false);
    }

}
