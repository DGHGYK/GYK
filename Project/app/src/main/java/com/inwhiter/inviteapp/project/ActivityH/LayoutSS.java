package com.inwhiter.inviteapp.project.ActivityH;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import com.inwhiter.inviteapp.project.R;

public class LayoutSS extends AppCompatActivity {
    ImageView ss;
    View l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.h_activity_layout_ss);

        ss= (ImageView) findViewById(R.id.imageView);
        String encoded = (String) getIntent().getExtras().get("ss");
        byte[] decodedString = Base64.decode(encoded, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        BitmapDrawable ob = new BitmapDrawable(getResources(), decodedByte);
        ss.setBackgroundDrawable(ob);
    }
}
