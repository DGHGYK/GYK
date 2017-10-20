package com.inwhiter.inviteapp.project.ActivityH;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.inwhiter.inviteapp.project.BusineesH.CustomAdaptor;
import com.inwhiter.inviteapp.project.BusineesH.MainFragment;
import com.inwhiter.inviteapp.project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TopicActivity extends AppCompatActivity {

    ArrayList<String> topiclist=new ArrayList<>();
    ListView lv_konu_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.h_activity_topic);

        lv_konu_list=(ListView)findViewById(R.id.lv_konu_list);
        topiclist.add("Hazır şablon ile hazırlayın");
        topiclist.add("Fotoğraf yükleyerek hazırlayın");
        topiclist.add("Video yükleyerek hazırlayın");
        String titlem=getSupportActionBar().getTitle().toString();

        SpannableString s = new SpannableString(titlem);
        s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimaryDark)), 0, titlem.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(s);


        CustomAdaptor adp=new CustomAdaptor(this,topiclist);
        lv_konu_list.setAdapter(adp);


        /*bilgi sayfasında resim ve video da alabilmek için her birinin formatını ayrı gönderdim. ör:resim dedğinde resim ekle butonu da gelsin*/
       lv_konu_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if(topiclist.get(position).toString().equals("Hazır şablon ile hazırlayın")){

                    Intent intent=new Intent(TopicActivity.this,InfoActivity.class);
                    intent.putExtra("mood","template");
                  /*  Bundle bundle = new Bundle();
                    bundle.putString("mood", "template");
                    MainFragment mainfragment = new MainFragment();
                    mainfragment.setArguments(bundle);
                    //transaction.replace(R.id.fragment_single, fragInfo);
                   // transaction.commit();*/
                    startActivity(intent);
                    // finish();
                }
                else if(topiclist.get(position).toString().equals("Fotoğraf yükleyerek hazırlayın")){

                    Intent intent=new Intent(TopicActivity.this,InfoActivity.class);
                    intent.putExtra("mood","image");
                    startActivity(intent);
                    // finish();
                }
                else if(topiclist.get(position).toString().equals("Video yükleyerek hazırlayın")){

                    Toast.makeText(TopicActivity.this, "YAKINDA :)", Toast.LENGTH_SHORT).show();


                   /* Intent intent=new Intent(TopicActivity.this,InfoActivity.class);
                    intent.putExtra("mood","video");
                    startActivity(intent);
                    // finish();*/
                }



            }
        });






    }
}
