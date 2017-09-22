package com.inwhiter.inviteapp.project.ActivityH;

import android.app.ActionBar;
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
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
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
    private StorageReference mStorageRef;

    int id;//hangi layouttayız ?







    TextView s1_title, s1_maintext, s1_family1, s1_family2, s1_adress, s1_tag, s1_time, s1_date;//sablon1 için
    TextView s2_title, s2_maintext, s2_family1, s2_family2, s2_adress, s2_tag, s2_time, s2_date;//şablon2 için
    TextView s3_title, s3_maintext, s3_family1, s3_family2, s3_adress, s3_tag, s3_time, s3_date;//şablon3 için

    TextView c1_title, c1_maintext, c1_family1, c1_family2, c1_adress, c1_tag, c1_time, c1_date;//cameradan foto layout için

    TextView v1_title, v1_maintext, v1_family1, v1_family2, v1_adress, v1_tag, v1_time, v1_date;//cameradan video layout için

    VideoView v1_video;



    Info info;

    Uri foto,video;
    int DefaultColor;
    RadioButton rb_bonbonRegularfont, rb_solenafont, rb_glaresomefont, rb_geckofont;//fontlar
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

       /*custom action bar*/
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.h_custom_actionbar_sablon);
        View mCustomView =getSupportActionBar().getCustomView();

        Button template_select=(Button)mCustomView.findViewById(R.id.btn_actionbar_sablon);
        final Button color_select=(Button)mCustomView.findViewById(R.id.btn_actionbar_color);
        Button font_select=(Button)mCustomView.findViewById(R.id.btn_actionbar_font);
        Button save=(Button)mCustomView.findViewById(R.id.btn_actionbar_save);

        if (getIntent().getExtras().get("menu").equals("video") || getIntent().getExtras().get("menu").equals("camera") ) {
            View video = findViewById(R.id.btn_actionbar_sablon);
            video.setVisibility(View.INVISIBLE);

        }

        template_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themeSelected();
            }
        });

        color_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    colorSelected();

            }
        });

        font_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fontSelected();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerLayout();

            }
        });

         /* cunstom action bar finish */

        /*info sayfasından gelen bilgiler*/
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




        /*seçeneğe göre layoutların çağırımı*/
        if (getIntent().getExtras().get("menu").equals("template")) {
            id=R.layout.h_sablon1;

            setContentView(R.layout.h_sablon1);

            s1_title = (TextView) findViewById(R.id.tv_sablon1_title);
            s1_title.setText(title);
               if(color_select.isClickable()){

               }


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

        else if (getIntent().getExtras().get("menu").equals("camera")) {

            fotoLayoutSelected();
            id=R.layout.h_camera1;

        } else if (getIntent().getExtras().get("menu").equals("video")) {

            videoLayoutSelected();}
            //id=R.layout.h_video1;



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








        /*fontSelected function*/
      public void fontSelected() {
        final Dialog dialog = new Dialog(context);

        dialog.setContentView(R.layout.h_custom_dialog_font);
        dialog.setTitle("Font");


        rb_bonbonRegularfont = (RadioButton) dialog.findViewById(R.id.rb_bilgi_bonbon);
        rb_solenafont = (RadioButton) dialog.findViewById(R.id.rb_bilgi_solenafont);
        rb_glaresomefont = (RadioButton) dialog.findViewById(R.id.rb_bilgi_glarosomefont);
        rb_geckofont = (RadioButton) dialog.findViewById(R.id.rb_bilgi_geckofont);


        /*fontlar*/
        face = Typeface.createFromAsset(getAssets(), "fonts/BonbonRegular.otf");
        rb_bonbonRegularfont.setTypeface(face);
        face2 = Typeface.createFromAsset(getAssets(), "fonts/solena.otf");
        rb_solenafont.setTypeface(face2);
        face3 = Typeface.createFromAsset(getAssets(), "fonts/Glaresome.otf");
        rb_glaresomefont.setTypeface(face3);
        face4 = Typeface.createFromAsset(getAssets(), "fonts/Gecko.ttf");
        rb_geckofont.setTypeface(face4);

          final Button ok = (Button) dialog.findViewById(R.id.btn_cdfont_ok);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rb_bonbonRegularfont.isChecked()) {
                        font_edit(face);
                    } else if (rb_solenafont.isChecked()) {
                        font_edit(face2);

                    } else if (rb_glaresomefont.isChecked()) {
                        font_edit(face3);

                    } else if (rb_geckofont.isChecked()) {
                        font_edit(face4);

                    }

                    dialog.dismiss();

                }
            });


        dialog.show();


    }
    /* ******************************************************************/
    public void font_edit(Typeface tf) {
        if (getIntent().getExtras().get("menu").equals("template")) {
            if(id==R.layout.h_sablon1){



                           /*sadece şablon1 in elemanlarını yapabildim */
                s1_title.setTypeface(tf);
                s1_maintext.setTypeface(tf);
                s1_family1.setTypeface(tf);
                s1_family2.setTypeface(tf);
                s1_adress.setTypeface(tf);
                s1_tag.setTypeface(tf);
                s1_time.setTypeface(tf);
                s1_date.setTypeface(tf);
                    /*SpannableString ss=  new SpannableString(title);
                ss.setSpan(new ForegroundColorSpan(color), 0, 5, 0);*/}
            else if(id==R.layout.h_sablon2){
                s2_title.setTypeface(tf);
                s2_maintext.setTypeface(tf);
                s2_family1.setTypeface(tf);
                s2_family2.setTypeface(tf);
                s2_adress.setTypeface(tf);
                s2_tag.setTypeface(tf);
                s2_time.setTypeface(tf);
                s2_date.setTypeface(tf);

            }

            else if(id==R.layout.h_sablon3){
                s3_title.setTypeface(tf);
                s3_maintext.setTypeface(tf);
                s3_family1.setTypeface(tf);
                s3_family2.setTypeface(tf);
                s3_adress.setTypeface(tf);
                s3_tag.setTypeface(tf);
                s3_time.setTypeface(tf);
                s3_date.setTypeface(tf);

            }

        }
        else if (getIntent().getExtras().get("menu").equals("camera")) {

            c1_title.setTypeface(tf);
            c1_maintext.setTypeface(tf);
            c1_family1.setTypeface(tf);
            c1_family2.setTypeface(tf);
            c1_adress.setTypeface(tf);
            c1_tag.setTypeface(tf);
            c1_time.setTypeface(tf);
            c1_date.setTypeface(tf);


        }
        else if (getIntent().getExtras().get("menu").equals("video")) {

            v1_title.setTypeface(tf);
            v1_maintext.setTypeface(tf);
            v1_family1.setTypeface(tf);
            v1_family2.setTypeface(tf);
            v1_adress.setTypeface(tf);
            v1_tag.setTypeface(tf);
            v1_time.setTypeface(tf);
            v1_date.setTypeface(tf);


        }



    }

    /*colorSelected function*/
    public void colorSelected() {
        OpenColorPickerDialog(false);

    }

    public void OpenColorPickerDialog(boolean AlphaSupport) {

        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(TemplateActivity.this, DefaultColor, AlphaSupport, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog ambilWarnaDialog, int color) {

                DefaultColor = color;
                /*burada rengni değiştirecekleri yaz*//*çözüm bulamadım amele gibi hepsini tek tek yazacağim*/
                if (getIntent().getExtras().get("menu").equals("template")) {
                    if(id==R.layout.h_sablon1){



                           /*sadece şablon1 in elemanlarını yapabildim */
                            s1_title.setTextColor(color);
                            s1_maintext.setTextColor(color);
                            s1_family1.setTextColor(color);
                            s1_family2.setTextColor(color);
                            s1_adress.setTextColor(color);
                            s1_tag.setTextColor(color);
                            s1_time.setTextColor(color);
                            s1_date.setTextColor(color);
                    /*SpannableString ss=  new SpannableString(title);
                ss.setSpan(new ForegroundColorSpan(color), 0, 5, 0);*/}
                    else if(id==R.layout.h_sablon2){
                        s2_title.setTextColor(color);
                        s2_maintext.setTextColor(color);
                        s2_family1.setTextColor(color);
                        s2_family2.setTextColor(color);
                        s2_adress.setTextColor(color);
                        s2_tag.setTextColor(color);
                        s2_time.setTextColor(color);
                        s2_date.setTextColor(color);

                    }

                    else if(id==R.layout.h_sablon3){
                        s3_title.setTextColor(color);
                        s3_maintext.setTextColor(color);
                        s3_family1.setTextColor(color);
                        s3_family2.setTextColor(color);
                        s3_adress.setTextColor(color);
                        s3_tag.setTextColor(color);
                        s3_time.setTextColor(color);
                        s3_date.setTextColor(color);

                    }

                }
                else if (getIntent().getExtras().get("menu").equals("camera")) {

                            c1_title.setTextColor(color);
                            c1_maintext.setTextColor(color);
                            c1_family1.setTextColor(color);
                            c1_family2.setTextColor(color);
                            c1_adress.setTextColor(color);
                            c1_tag.setTextColor(color);
                            c1_time.setTextColor(color);
                            c1_date.setTextColor(color);


                }
                else if (getIntent().getExtras().get("menu").equals("video")) {

                            v1_title.setTextColor(color);
                            v1_maintext.setTextColor(color);
                            v1_family1.setTextColor(color);
                            v1_family2.setTextColor(color);
                            v1_adress.setTextColor(color);
                            v1_tag.setTextColor(color);
                            v1_time.setTextColor(color);
                            v1_date.setTextColor(color);


                }
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
        Intent intent=new Intent(TemplateActivity.this,LayoutSS.class);
        byte[] encoded=takeScreenshot();
        Toast.makeText(TemplateActivity.this, "Tebrikler Davetiyeniz Oluşturuldu.", Toast.LENGTH_SHORT).show();
       intent.putExtra("ss",encoded);
        startActivity(intent);



        mStorageRef = FirebaseStorage.getInstance().getReference();
        StorageReference isRef = mStorageRef.child("invites/"+TemplateActivity.inviteId+".jpg");

        UploadTask uploadTask = isRef.putBytes(encoded);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();

            }
        });







    }

    public byte[] takeScreenshot() {
        View rootView = null;
       /* switch(id){
            case R.layout.h_sablon1: rootView = findViewById(R.id.sablon1);
                break;
            case R.layout.h_sablon2: rootView = findViewById(R.id.sablon2);
                break;
            case R.layout.h_sablon3: rootView = findViewById(R.id.sablon3);
                break;
            case R.layout.h_camera1: rootView = findViewById(R.id.camera1);
                break;
            case R.layout.h_video1: rootView = findViewById(R.id.video1);

                break;
        }*/
       if(id==R.layout.h_sablon1) rootView=findViewById(R.id.sablon1);
       else if(id==R.layout.h_sablon2) rootView=findViewById(R.id.sablon2);
       else if(id==R.layout.h_sablon3) rootView=findViewById(R.id.sablon3);
       else if(id==R.layout.h_camera1) rootView=findViewById(R.id.camera1);
       //else if(id==R.layout.h_video1) rootView=findViewById(R.id.video1);

        View v1=rootView;
        v1.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
        v1.setDrawingCacheEnabled(false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        rootView.setDrawingCacheEnabled(false);

        return byteArray;

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
                id=R.layout.h_sablon1;
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
                id=R.layout.h_sablon2;

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
                id=R.layout.h_sablon3;

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


}




