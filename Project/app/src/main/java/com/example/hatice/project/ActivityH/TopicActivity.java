package com.example.hatice.project.ActivityH;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.hatice.project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TopicActivity extends AppCompatActivity {
    FirebaseDatabase db= FirebaseDatabase.getInstance();
    DatabaseReference dbref=db.getReference("Konu");
    ArrayList<String> topiclist=new ArrayList<>();
    ListView lv_konu_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.h_activity_topic);
        topiclist.add("Yükleniyor..");

        lv_konu_list=(ListView)findViewById(R.id.lv_konu_list);
        final ArrayAdapter<String> adapter=
                new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1,topiclist);
        lv_konu_list.setAdapter(adapter);

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                topiclist.clear();
                for (DataSnapshot ds:dataSnapshot.getChildren())
                {
                    topiclist.add(ds.getKey());
                }
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        /*bilgi sayfasında resim ve video da alabilmek için her birinin formatını ayrı gönderdim. ör:resim dedğinde resim ekle butonu da gelsin*/
        lv_konu_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if(topiclist.get(position).equals("Hazır Şablon İle Hazırlayın")){

                    Intent intent=new Intent(TopicActivity.this,InfoActivity.class);
                    intent.putExtra("mood","template");
                    startActivity(intent);
                    // finish();
                }
                else if(topiclist.get(position).equals("Fotoğraf Yükleyerek Hazırlayın")){

                    Intent intent=new Intent(TopicActivity.this,InfoActivity.class);
                    intent.putExtra("mood","image");
                    startActivity(intent);
                    // finish();
                }
                else if(topiclist.get(position).equals("Video Ekleyerek Hazırlayın")){

                    Intent intent=new Intent(TopicActivity.this,InfoActivity.class);
                    intent.putExtra("mood","video");
                    startActivity(intent);
                    // finish();
                }



            }
        });






    }
}
