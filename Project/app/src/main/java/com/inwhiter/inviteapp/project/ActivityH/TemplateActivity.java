package com.inwhiter.inviteapp.project.ActivityH;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inwhiter.inviteapp.project.ModelG.Info;
import com.inwhiter.inviteapp.project.ModelG.Invite;
import com.inwhiter.inviteapp.project.R;
import com.rajasharan.layout.RearrangeableLayout;

import java.io.ByteArrayOutputStream;

import yuku.ambilwarna.AmbilWarnaDialog;

public class TemplateActivity extends AppCompatActivity {

    private static final String TAG = "DEMO-REARRANGEABLE-LOUT";
    public static String inviteId;
    private RearrangeableLayout root;
    private FirebaseAuth mAuth;


    TextView s1_title, s1_maintext, s1_family1, s1_family2, s1_adress, s1_tag, s1_time, s1_date;//sablon1 için
    TextView s2_title, s2_maintext, s2_family1, s2_family2, s2_adress, s2_tag, s2_time, s2_date;//şablon2 için
    TextView s3_title, s3_maintext, s3_family1, s3_family2, s3_adress, s3_tag, s3_time, s3_date;//şablon3 için

    TextView c1_title, c1_maintext, c1_family1, c1_family2, c1_adress, c1_tag, c1_time, c1_date;//cameradan foto layout için

    TextView v1_title, v1_maintext, v1_family1, v1_family2, v1_adress, v1_tag, v1_time, v1_date;//cameradan video layout için

    VideoView v1_video;

    Info info;

    Uri foto,video;
    int DefaultColor;
    RadioButton rb_painterfont, rb_solenafont, rb_glaresomefont, rb_geckofont;//fontlar
    Typeface face, face2, face3, face4;//fonttipi
    Drawable image = null;

    ImageView invalidation1, invalidation2, invalidation3,c1_foto;
    Context context = this;

    String title, maintext, family1, family2, date, time, adress, tag, fotoS ,videoS ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.h_activity_template);


        root = (RearrangeableLayout) findViewById(R.id.rearrangeable_layout);



        info = getIntent().getExtras().getParcelable("info");
        title = info.getTitle();
        maintext = info.getText();
        family1 = info.getFamily1();
        family2 = info.getFamily2();
        adress = info.getAddress();
        time = info.getTime();
        date = info.getDate();
        tag = info.getHashtag();


        //girilen değerler veri tabanına kaydedilir
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference inviteRef = database.getReference("invite");

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Invite invite = new Invite(currentUser.getUid(),info, 1, "", getIntent().getExtras().getString("menu") );
        if(inviteId==null || inviteId.equals("")) {
            inviteId = inviteRef.push().getKey();
        }
        inviteRef.child(inviteId).setValue(invite);


       // foto = getIntent().getExtras().toString("foto");

        /*h_sablon1*/




        /*REARREGENABLE LAYOUT FUNCTION*/
        root.setChildPositionListener(new RearrangeableLayout.ChildPositionListener() {
            @Override
            public void onChildMoved(View childView, Rect oldPosition, Rect newPosition) {
                Log.d(TAG, title.toString());
                Log.d(TAG, oldPosition.toString() + " -> " + newPosition.toString());
            }
        });

        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.d(TAG, "onGlobalLayout");
                Log.d(TAG, root.toString());
            }
        });

        root.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                //Log.d(TAG, "onPreDraw");
                //Log.d(TAG, root.toString());
                return true;
            }
        });
    }

        /* ***************************************************************/






    /* **************************************************************************/


        /*fontSelected function*/
    public void fontSelected() {
        final Dialog dialog = new Dialog(context);

        dialog.setContentView(R.layout.h_custom_dialog_font);
        dialog.setTitle("Font");


        rb_painterfont = (RadioButton) dialog.findViewById(R.id.rb_bilgi_painterfont);
        rb_solenafont = (RadioButton) dialog.findViewById(R.id.rb_bilgi_solenafont);
        rb_glaresomefont = (RadioButton) dialog.findViewById(R.id.rb_bilgi_glarosomefont);
        rb_geckofont = (RadioButton) dialog.findViewById(R.id.rb_bilgi_geckofont);


        /*fontlar*/
        face = Typeface.createFromAsset(getAssets(), "fonts/Painter.ttf");
        rb_painterfont.setTypeface(face);
        face2 = Typeface.createFromAsset(getAssets(), "fonts/solena.otf");
        rb_solenafont.setTypeface(face2);
        face3 = Typeface.createFromAsset(getAssets(), "fonts/Glaresome.otf");
        rb_glaresomefont.setTypeface(face3);
        face4 = Typeface.createFromAsset(getAssets(), "fonts/Gecko.ttf");
        rb_geckofont.setTypeface(face4);

        /*    final Button ok = (Button) dialog.findViewById(R.id.btn_cdfont_ok);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rb_painterfont.isChecked()) {
                        title.setTypeface(face);

                    } else if (rb_solenafont.isChecked()) {
                        title.setTypeface(face2);
                    } else if (rb_glaresomefont.isChecked()) {
                        title.setTypeface(face3);
                    } else if (rb_geckofont.isChecked()) {
                        title.setTypeface(face4);
                    }
                    dialog.dismiss();

                }
            });*/


        dialog.show();


    }
    /* ******************************************************************/

    /*colorSelected function*/
    public void colorSelected(TextView v) {
        OpenColorPickerDialog(false, v);

    }

    private void OpenColorPickerDialog(boolean AlphaSupport, final TextView v) {

        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(TemplateActivity.this, DefaultColor, AlphaSupport, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog ambilWarnaDialog, int color) {

                DefaultColor = color;

                v.setTextColor(color);
            }

            @Override
            public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {

                Toast.makeText(TemplateActivity.this, "Color Picker Closed", Toast.LENGTH_SHORT).show();
            }
        });
        ambilWarnaDialog.show();

    }

    /*layout screenshot*/
    public void registerLayout() {


        Intent intent = new Intent(TemplateActivity.this, LayoutSS.class);
        String encoded = takeScreenshot();


        Toast.makeText(TemplateActivity.this, "sayfanın ss i alınıyor", Toast.LENGTH_SHORT).show();

        intent.putExtra("ss", encoded);
        startActivity(intent);


    }

    public String takeScreenshot() {

        View rootView = findViewById(R.id.sablon1).getRootView();
        rootView.setDrawingCacheEnabled(true);
        rootView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED) );
        rootView.layout(0,0,rootView.getMeasuredWidth(),rootView.getMeasuredHeight());
        rootView.buildDrawingCache(true);
        Bitmap b = Bitmap.createBitmap(rootView.getDrawingCache());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        rootView.setDrawingCacheEnabled(false);
        return encoded;

    }
    /*fotoLayoutSelected function*/
    public void fotoLayoutSelected(){

        setContentView(R.layout.h_camera1);

        fotoS=getIntent().getExtras().getString("foto");
        foto=Uri.parse(fotoS);//GAMZE FIRABASE E ATACAĞIN RESİM

        c1_title = (TextView) findViewById(R.id.tv_camera_title);
        c1_title.setText(title);

        c1_maintext = (TextView) findViewById(R.id.tv_camera_maintext);
        c1_maintext.setText(maintext);

        c1_family1 = (TextView) findViewById(R.id.tv_camera_family1);
        c1_family1.setText(family1);

        c1_family2 = (TextView) findViewById(R.id.tv_camera_family2);
        c1_family2.setText(family2);

        c1_adress = (TextView) findViewById(R.id.tv_camera_adress);
        c1_adress.setText(adress);

        c1_time = (TextView) findViewById(R.id.tv_camera_time);
        c1_time.setText(time);

        c1_date = (TextView) findViewById(R.id.tv_camera_date);
        c1_date.setText(date);

        c1_tag = (TextView) findViewById(R.id.tv_camera_tag);
        c1_tag.setText(tag);

        c1_foto = (ImageView) findViewById(R.id.iv_camera_foto);
        c1_foto.setImageURI(foto);





        Toast.makeText(TemplateActivity.this, "foto yerleşim sayfası oluşturuldu", Toast.LENGTH_SHORT).show();


    }
/*videoLayoutSelected*/
    public void videoLayoutSelected(){

        setContentView(R.layout.h_video1);

        videoS=getIntent().getExtras().getString("video");
        video=Uri.parse(videoS);//GAMZE FIREBASE ATACAĞIN VİDEO

        v1_title = (TextView) findViewById(R.id.tv_video_title);
        v1_title.setText(title);

        v1_maintext = (TextView) findViewById(R.id.tv_video_maintext);
        v1_maintext.setText(maintext);

        v1_family1 = (TextView) findViewById(R.id.tv_video_family1);
        v1_family1.setText(family1);

        v1_family2 = (TextView) findViewById(R.id.tv_video_family2);
        v1_family2.setText(family2);

        v1_adress = (TextView) findViewById(R.id.tv_video_adress);
        v1_adress.setText(adress);

        v1_time = (TextView) findViewById(R.id.tv_video_time);
        v1_time.setText(time);

        v1_date = (TextView) findViewById(R.id.tv_video_date);
        v1_date.setText(date);

        v1_tag = (TextView) findViewById(R.id.tv_video_tag);
        v1_tag.setText(tag);

        v1_video=(VideoView)findViewById(R.id.vv_video);
        v1_video.setVideoURI(video);
        v1_video.start();



        Toast.makeText(TemplateActivity.this, "video yerleşim sayfası oluşturuldu", Toast.LENGTH_SHORT).show();


    }



    /*themeSelected function*/
    public void themeSelected() {
        final Dialog dialog_theme = new Dialog(context);

        dialog_theme.setContentView(R.layout.h_custom_dilaog_sablon_choose);
        dialog_theme.setTitle("şablon");


        invalidation1 = (ImageView) dialog_theme.findViewById(R.id.iv_cd_sablon_choose_inva1);
        invalidation2 = (ImageView) dialog_theme.findViewById(R.id.iv_cd_sablon_choose_inva2);
        invalidation3 = (ImageView) dialog_theme.findViewById(R.id.iv_cd_sablon_choose_inva3);

        invalidation1.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                setContentView(R.layout.h_sablon1);
                s1_title = (TextView) findViewById(R.id.tv_sablon1_title);
                s1_title.setText(title);


                s1_maintext = (TextView) findViewById(R.id.tv_sablon1_maintext);
                s1_maintext.setText(maintext);

                s1_family1 = (TextView) findViewById(R.id.tv_sablon1_family1);
                s1_family1.setText(family1);

                s1_family2 = (TextView) findViewById(R.id.tv_sablon1_family2);
                s1_family2.setText(family2);

                s1_adress = (TextView) findViewById(R.id.tv_sablon1_adress);
                s1_adress.setText(adress);

                s1_time = (TextView) findViewById(R.id.tv_sablon1_time);
                s1_time.setText(time);

                s1_date = (TextView) findViewById(R.id.tv_sablon1_date);
                s1_date.setText(date);

                s1_tag = (TextView) findViewById(R.id.tv_sablon1_tag);
                s1_tag.setText(tag);

                Toast.makeText(TemplateActivity.this, "şablon1 seçildi", Toast.LENGTH_SHORT).show();

            }
        });


        invalidation2.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                setContentView(R.layout.h_sablon2);
                s2_title = (TextView) findViewById(R.id.tv_sablon2_title);
                s2_title.setText(title);


                s2_maintext = (TextView) findViewById(R.id.tv_sablon2_maintext);
                s2_maintext.setText(maintext);

                s2_family1 = (TextView) findViewById(R.id.tv_sablon2_family1);
                s2_family1.setText(family1);

                s2_family2 = (TextView) findViewById(R.id.tv_sablon2_family2);
                s2_family2.setText(family2);

                s2_adress = (TextView) findViewById(R.id.tv_sablon2_adress);
                s2_adress.setText(adress);

                s2_time = (TextView) findViewById(R.id.tv_sablon2_time);
                s2_time.setText(time);

                s2_date = (TextView) findViewById(R.id.tv_sablon2_date);
                s2_date.setText(date);

                s2_tag = (TextView) findViewById(R.id.tv_sablon2_tag);
                s2_tag.setText(tag);

                Toast.makeText(TemplateActivity.this, "şablon2 seçildi", Toast.LENGTH_SHORT).show();

            }
        });

        invalidation3.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                setContentView(R.layout.h_sablon3);
                s3_title = (TextView) findViewById(R.id.tv_sablon3_title);
                s3_title.setText(title);


                s3_maintext = (TextView) findViewById(R.id.tv_sablon3_maintext);
                s3_maintext.setText(maintext);

                s3_family1 = (TextView) findViewById(R.id.tv_sablon3_family1);
                s3_family1.setText(family1);

                s3_family2 = (TextView) findViewById(R.id.tv_sablon3_family2);
                s3_family2.setText(family2);

                s3_adress = (TextView) findViewById(R.id.tv_sablon3_adress);
                s3_adress.setText(adress);

                s3_time = (TextView) findViewById(R.id.tv_sablon3_time);
                s3_time.setText(time);

                s3_date = (TextView) findViewById(R.id.tv_sablon3_date);
                s3_date.setText(date);

                s3_tag = (TextView) findViewById(R.id.tv_sablon3_tag);
                s3_tag.setText(tag);
                Toast.makeText(TemplateActivity.this, "şablon3 seçildi", Toast.LENGTH_SHORT).show();

            }
        });
        final Button tamam = (Button) dialog_theme.findViewById(R.id.btn_cd_sablon_choose_ok);

        tamam.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                dialog_theme.dismiss();
            }
        });

        dialog_theme.show();


    }


/*MENU CREATE*/

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        if (getIntent().getExtras().get("menu").equals("template")) {
            setContentView(R.layout.h_sablon1);
            s1_title = (TextView) findViewById(R.id.tv_sablon1_title);
            s1_title.setText(title);


            s1_maintext = (TextView) findViewById(R.id.tv_sablon1_maintext);
            s1_maintext.setText(maintext);

            s1_family1 = (TextView) findViewById(R.id.tv_sablon1_family1);
            s1_family1.setText(family1);

            s1_family2 = (TextView) findViewById(R.id.tv_sablon1_family2);
            s1_family2.setText(family2);

            s1_adress = (TextView) findViewById(R.id.tv_sablon1_adress);
            s1_adress.setText(adress);

            s1_time = (TextView) findViewById(R.id.tv_sablon1_time);
            s1_time.setText(time);

            s1_date = (TextView) findViewById(R.id.tv_sablon1_date);
            s1_date.setText(date);

            s1_tag = (TextView) findViewById(R.id.tv_sablon1_tag);
            s1_tag.setText(tag);

            Toast.makeText(TemplateActivity.this, "şablon1 seçildi", Toast.LENGTH_SHORT).show();


            getMenuInflater().inflate(R.menu.menusablon, menu);
        } else if (getIntent().getExtras().get("menu").equals("camera")) {
            fotoLayoutSelected();
            getMenuInflater().inflate(R.menu.menucamera, menu);

        } else if (getIntent().getExtras().get("menu").equals("video")) {
            videoLayoutSelected();
            getMenuInflater().inflate(R.menu.menuvideo, menu);}

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_template:
                themeSelected();
                return true;
            case R.id.menu_color:
                colorSelected(s1_title);
                return true;
            case R.id.menu_font:
                fontSelected();
                return true;
            case R.id.menu_register:
                registerLayout();
                return true;
            case R.id.menu_camera:
                fotoLayoutSelected();
                return true;
            case R.id.menu_video:
                videoLayoutSelected();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}




