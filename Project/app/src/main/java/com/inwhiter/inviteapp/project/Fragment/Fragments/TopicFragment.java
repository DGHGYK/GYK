package com.inwhiter.inviteapp.project.Fragment.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.inwhiter.inviteapp.project.BusineesH.CustomAdaptor;
import com.inwhiter.inviteapp.project.Fragment.BaseFragment;
import com.inwhiter.inviteapp.project.Fragment.FragmentController;
import com.inwhiter.inviteapp.project.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopicFragment extends BaseFragment {
    ArrayList<String> topiclist=new ArrayList<>();
    ListView lv_konu_list;


    public TopicFragment() {
        // Required empty public constructor

    }


    @Override
    protected int getFID() {
        return R.layout.fragment_topic;
    }

    @Override
    protected void init() {
        lv_konu_list=(ListView)getActivity().findViewById(R.id.lv_konu_list);
        topiclist.add("Hazır şablon ile hazırlayın");
        topiclist.add("Fotoğraf yükleyerek hazırlayın");
        topiclist.add("Video yükleyerek hazırlayın");
//        String titlem=getActivity().getActionBar().getTitle().toString();

//        SpannableString s = new SpannableString(titlem);
//        s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimaryDark)), 0, titlem.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        getActivity().getActionBar().setTitle(s);


        CustomAdaptor adp=new CustomAdaptor(getActivity(),topiclist);
        lv_konu_list.setAdapter(adp);


        /*bilgi sayfasında resim ve video da alabilmek için her birinin formatını ayrı gönderdim. ör:resim dedğinde resim ekle butonu da gelsin*/
        lv_konu_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if(topiclist.get(position).toString().equals("Hazır şablon ile hazırlayın")){

                    Bundle bundle=new Bundle();
                    bundle.putString("mood","template");
                    listener.changeFragment(FragmentController.INFO,bundle);//infoys gidiyoruz bilgileri girmek için

                  /*  Intent intent=new Intent(TopicActivity.get,InfoActivity.class);
                    intent.putExtra("mood","template");
                  /*  Bundle bundle = new Bundle();
                    bundle.putString("mood", "template");
                    MainFragment mainfragment = new MainFragment();
                    mainfragment.setArguments(bundle);
                    //transaction.replace(R.id.fragment_single, fragInfo);
                   // transaction.commit();*/
                   // startActivity(intent);
                    // finish();
                }
                else if(topiclist.get(position).toString().equals("Fotoğraf yükleyerek hazırlayın")){

                    Bundle bundle=new Bundle();
                    bundle.putString("mood","image");
                    listener.changeFragment(FragmentController.INFO,bundle);

                   /* Intent intent=new Intent(TopicActivity.this,InfoActivity.class);
                    intent.putExtra("mood","image");
                    startActivity(intent);
                    // finish();*/
                }
                else if(topiclist.get(position).toString().equals("Video yükleyerek hazırlayın")){

                    Toast.makeText(getActivity() , "YAKINDA :)", Toast.LENGTH_SHORT).show();


                   /* Intent intent=new Intent(TopicActivity.this,InfoActivity.class);
                    intent.putExtra("mood","video");
                    startActivity(intent);
                    // finish();*/
                }



            }
        });





    }

    @Override
    protected void handlers() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_topic, container, false);
    }

}
