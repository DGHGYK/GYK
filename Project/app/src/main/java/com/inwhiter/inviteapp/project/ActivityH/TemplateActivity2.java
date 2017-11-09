package com.inwhiter.inviteapp.project.ActivityH;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SeekBar;
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
import com.inwhiter.inviteapp.project.BusineesH.CustomAdaptorMediaplayer;
import com.inwhiter.inviteapp.project.BusineesH.MainFragment;
//import com.inwhiter.inviteapp.project.R;
//import com.yalantis.contextmenu.R;
import com.inwhiter.inviteapp.project.ModelG.Info;
import com.inwhiter.inviteapp.project.ModelG.Invite;
import com.inwhiter.inviteapp.project.ModelH.Media;
import com.inwhiter.inviteapp.project.R;
import com.rajasharan.layout.RearrangeableLayout;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import yuku.ambilwarna.AmbilWarnaDialog;

public class TemplateActivity2 extends AppCompatActivity implements OnMenuItemClickListener, OnMenuItemLongClickListener {
    private static final String TAG = "DEMO-REARRANGEABLE-LOUT";
    public static String inviteId;
    private RearrangeableLayout root;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;

    private StorageReference mStorageRef;

    int id;//hangi layouttayız ?
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
    public TextView current_duration,total_duration,current_music;
    Button btn_ok;







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



    private FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template2);
        contextMenu();





        root = (RearrangeableLayout) findViewById(R.id.rearrangeable_layout);
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
        Invite invite = new Invite(currentUser.getUid(),info, 1, "", getIntent().getExtras().getString("menu"),  Calendar.getInstance().getTime() );
        if(inviteId==null || inviteId.equals("")) {
            inviteId = inviteRef.push().getKey();
        }
        inviteRef.child(inviteId).setValue(invite);

         /*seçeneğe göre layoutların çağırımı*/

       if (getIntent().getExtras().get("menu").equals("template")) {
           //Toast.makeText(this, "template seçildiii "+title , Toast.LENGTH_SHORT).show();
           id = R.layout.h_sablonbir;
           setContentView(R.layout.h_sablonbir);
           contextMenu();

           View v=findViewById(R.id.sablon1);

           s1_title = (TextView)v.findViewById(R.id.tv_sablon1_title);
           s1_title.setText(title);

           s1_date = (TextView)v. findViewById(R.id.tv_sablon1_date);
           s1_date.setText(date);

           s1_maintext = (TextView) v.findViewById(R.id.tv_sablon1_maintext);
            s1_maintext.setText(maintext);

            s1_family1 = (TextView)v. findViewById(R.id.tv_sablon1_family1);
            s1_family1.setText(family1);

            s1_family2 = (TextView)v. findViewById(R.id.tv_sablon1_family2);
            s1_family2.setText(family2);

            s1_adress = (TextView)v. findViewById(R.id.tv_sablon1_adress);
            s1_adress.setText(adress);

            s1_time = (TextView)v. findViewById(R.id.tv_sablon1_time);
            s1_time.setText(time);

            s1_tag = (TextView)v. findViewById(R.id.tv_sablon1_tag);
            s1_tag.setText(tag);



        }

        else if (getIntent().getExtras().get("menu").equals("camera")) {

            fotoLayoutSelected();
            id=R.layout.h_camerabir;

      /*  } else if (getIntent().getExtras().get("menu").equals("video")) {

            videoLayoutSelected();}
        //id=R.layout.h_video1;*/





       /* /*REARREGENABLE LAYOUT FUNCTION*/
       /*
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
        });*/




    }}
    public void contextMenu(){
        /* context menu*/
        fragmentManager = getSupportFragmentManager();
        initToolbar();
        initMenuFragment();


    }

    /*fontSelected function*/
    public void fontSelected() {
        final Dialog dialog = new Dialog(context,R.style.dialogTheme);

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
            if(id==R.layout.h_sablonbir){



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
            else if(id==R.layout.h_sabloniki){
                s2_title.setTypeface(tf);
                s2_maintext.setTypeface(tf);
                s2_family1.setTypeface(tf);
                s2_family2.setTypeface(tf);
                s2_adress.setTypeface(tf);
                s2_tag.setTypeface(tf);
                s2_time.setTypeface(tf);
                s2_date.setTypeface(tf);

            }

            else if(id==R.layout.h_sablonuc){
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

        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(TemplateActivity2.this, DefaultColor, AlphaSupport, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog ambilWarnaDialog, int color) {

                DefaultColor = color;
                /*burada rengni değiştirecekleri yaz*//*çözüm bulamadım amele gibi hepsini tek tek yazacağim*/
                if (getIntent().getExtras().get("menu").equals("template")) {
                    if(id==R.layout.h_sablonbir){




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
                    else if(id==R.layout.h_sabloniki){
                        s2_title.setTextColor(color);
                        s2_maintext.setTextColor(color);
                        s2_family1.setTextColor(color);
                        s2_family2.setTextColor(color);
                        s2_adress.setTextColor(color);
                        s2_tag.setTextColor(color);
                        s2_time.setTextColor(color);
                        s2_date.setTextColor(color);

                    }

                    else if(id==R.layout.h_sablonuc){
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


                }
            }

            @Override
            public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {

                Toast.makeText(TemplateActivity2.this, "Color Picker Closed", Toast.LENGTH_SHORT).show();
            }
        });
        ambilWarnaDialog.show();

    }

    /*layout screenshot*/
    public void registerLayout() {
        Intent intent=new Intent(TemplateActivity2.this,LayoutSS.class);
        intent.putExtra("inviteId", inviteId);
        byte[] encoded=takeScreenshot();

        intent.putExtra("ss",encoded);
        startActivity(intent);



        mStorageRef = FirebaseStorage.getInstance().getReference();

        StorageReference isRef = mStorageRef.child("invites/"+inviteId+".jpg");


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

        if(id==R.layout.h_sablonbir) rootView=findViewById(R.id.sablon1);
        else if(id==R.layout.h_sabloniki) rootView=findViewById(R.id.sablon2);
        else if(id==R.layout.h_sablonuc) rootView=findViewById(R.id.sablon3);
        else if(id==R.layout.h_camerabir) rootView=findViewById(R.id.camera1);
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

        setContentView(R.layout.h_camerabir);
        contextMenu();

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




    }



    /*themeSelected function*/
    public void themeSelected() {
        final Dialog dialog_theme = new Dialog(context,R.style.dialogTheme);

        dialog_theme.setContentView(R.layout.h_custom_dilaog_sablon_choose);
        dialog_theme.setTitle("şablon");


        invalidation1 = (ImageView) dialog_theme.findViewById(R.id.iv_cd_sablon_choose_inva1);
        invalidation2 = (ImageView) dialog_theme.findViewById(R.id.iv_cd_sablon_choose_inva2);
        invalidation3 = (ImageView) dialog_theme.findViewById(R.id.iv_cd_sablon_choose_inva3);

        invalidation1.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                id=R.layout.h_sablonbir;
                setContentView(R.layout.h_sablonbir);
                contextMenu();
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



            }
        });


        invalidation2.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                id=R.layout.h_sabloniki;

                setContentView(R.layout.h_sabloniki);
                contextMenu();
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



            }
        });

        invalidation3.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                id=R.layout.h_sablonuc;

                setContentView(R.layout.h_sablonuc);
                contextMenu();
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
    public void MusicAdd(){
        final Dialog dialog = new Dialog(context,R.style.dialogTheme);

        dialog.setContentView(R.layout.h_mediaplayer_custom_dialog);
        dialog.setTitle("Media player");







        //layout_media_player=(Layout) findViewById(R.layout.h_satir_mediaplayer);
        seekBar=(SeekBar)dialog.findViewById(R.id.seek_bar);
        lv_playlist=(ListView)dialog.findViewById(R.id.lv_playlist);
        current_duration=(TextView)dialog.findViewById(R.id.txt_current_duration);
        current_music=(TextView)dialog.findViewById(R.id.txt_current_music);
        total_duration=(TextView)dialog.findViewById(R.id.txt_total_duration);
        play_pause=(ImageView)dialog.findViewById(R.id.btn_play_pause);
        btn_ok=(Button)dialog.findViewById(R.id.btn_ok);

        // icon_play= (ImageView)findViewById(R.id.media_simge);


        play_pause.setVisibility(View.INVISIBLE);



        playlist.add(new Media("dm",false));
        playlist.add(new Media("happy1",false));
        playlist.add(new Media("happy2",false));
        playlist.add(new Media("happy3",false));
        playlist.add(new Media("slow1",false));
        playlist.add(new Media("slow2",false));

        final CustomAdaptorMediaplayer adp=new CustomAdaptorMediaplayer((Activity) context, playlist);
        lv_playlist.setAdapter(adp);

        lv_playlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                final Media media = playlist.get(position);
                media.setIs_play(true);
                adp.notifyDataSetChanged();

                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String firebase_music_name=media.getMusic_name();
                        database = FirebaseDatabase.getInstance();
                        DatabaseReference musicRef = database.getReference("invite").child(inviteId).child("info").child("music");
                        musicRef.setValue(firebase_music_name);
                        Toast.makeText(TemplateActivity2.this,media.getMusic_name()+ " seçildi", Toast.LENGTH_SHORT).show();

                        mp.stop();

                        dialog.dismiss();

                    }
                });

                //play_pause.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_black));
                play_pause.setVisibility(View.VISIBLE);
                if (media.getMusic_name().equals("dm")) {


                    if (mp != null) {

                        play_pause.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_black));
                        mp.reset();

                    }

                    mp = MediaPlayer.create(TemplateActivity2.this, R.raw.dm);
                    // icon_play.setImageDrawable(getResources().getDrawable(R.drawable.icon_music_play));

                    current_music.setText("Düğün Marşı");
                    play();




                }
                else if(media.getMusic_name().equals("happy1"))

                {
                    if (mp != null) {
                        play_pause.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_black));

                        mp.reset();

                    }
                    mp = MediaPlayer.create(TemplateActivity2.this, R.raw.happy1);
                    // icon_play.setImageDrawable(getResources().getDrawable(R.drawable.icon_music_play));
                    current_music.setText("Happy 1");
                    play();

                }
                else if(media.getMusic_name().equals("happy2"))

                {
                    if (mp != null) {

                        play_pause.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_black));
                        mp.reset();


                    }
                    mp = MediaPlayer.create(TemplateActivity2.this, R.raw.happy2);
                    current_music.setText("Happy2");
                    play();

                }
                else if(media.getMusic_name().equals("happy3"))

                {
                    if (mp != null) {
                        play_pause.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_black));

                        mp.reset();


                    }
                    mp = MediaPlayer.create(TemplateActivity2.this, R.raw.happy3);
                    current_music.setText("Happy3");
                    play();

                }
                else if(media.getMusic_name().equals("slow1"))

                {
                    if (mp != null) {
                        play_pause.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_black));

                        mp.reset();


                    }
                    mp = MediaPlayer.create(TemplateActivity2.this, R.raw.slow1);
                    current_music.setText("slow1");
                    play();

                }
                else if(media.getMusic_name().equals("slow2"))

                {
                    if (mp != null) {
                        play_pause.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_black));

                        mp.reset();


                    }
                    mp = MediaPlayer.create(TemplateActivity2.this, R.raw.slow2);
                    current_music.setText("slow2");
                    play();

                }

                play_pause.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(mp.isPlaying()){
                            play_pause.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_black));
                            pause();
                        }

                        else{

                            play_pause.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_black));

                            play();


                        }

                    }

                });




            }

            private void play() {
                seekBar.setClickable(false);
                mp.start();


                finalTime = mp.getDuration();
                startTime = mp.getCurrentPosition();
                if (oneTimeOnly == 0) {
                    seekBar.setMax((int) finalTime);
                    oneTimeOnly = 1;
                }
                //Muziğin toplamda ne kadar süre oldugunu  endTimeField controller ına yazdırıyoruz...
                total_duration.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                        toMinutes((long) finalTime)))
                );
                //Muziğin başladıgı andan itibaren gecen süreyi ,startTimeField controller ına yazdırıyoruz...
                current_duration.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                        toMinutes((long) startTime)))
                );
                //Muziğin hangi sürede oldugunu gostermek icin, seekbar kullarak gosteriyoruz...
                seekBar.setProgress((int) startTime);
                myHandler.postDelayed(UpdateSongTime, 100);
                //pause.setImageDrawable(getResources().getDrawable(R.mipmap.ic_pause));
                //pause.setEnabled(true);
                //play.setEnabled(false);


            }
            Runnable UpdateSongTime = new Runnable() {
                public void run() {
                    startTime = mp.getCurrentPosition();
                    total_duration.setText(String.format("%d min, %d sec",
                            TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                            TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                            toMinutes((long) finalTime)))
                    );
                    current_duration.setText(String.format("%d min, %d sec",
                            TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                            TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                            toMinutes((long) startTime)))
                    );
                    //Muziğin hangi sürede oldugunu gostermek icin, seekbar kullarak gosteriyoruz...
                    seekBar.setProgress((int)startTime);
                    myHandler.postDelayed(this, 100);
                }
            };

            public  void pause(){

                mp.pause();
            }

        });dialog.show();

    }












            private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(false);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(this);
        mMenuDialogFragment.setItemLongClickListener(this);
    }

    private List<MenuObject> getMenuObjects() {
        // You can use any [resource, bitmap, drawable, color] as image:
        // item.setResource(...)
        // item.setBitmap(...)
        // item.setDrawable(...)
        // item.setColor(...)
        // You can set image ScaleType:
        // item.setScaleType(ScaleType.FIT_XY)
        // You can use any [resource, drawable, color] as background:
        // item.setBgResource(...)
        // item.setBgDrawable(...)
        // item.setBgColor(...)
        // You can use any [color] as text color:
        // item.setTextColor(...)
        // You can set any [color] as divider color:
        // item.setDividerColor(...)

        List<MenuObject> menuObjects = new ArrayList<>();

        MenuObject close = new MenuObject();
        close.setResource(R.drawable.cancelm);

        MenuObject template = new MenuObject("Şablon");
        template.setResource(R.drawable.boook);

        MenuObject color = new MenuObject("Renk");
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.paintbucket);
        color.setBitmap(b);

        MenuObject font = new MenuObject("Yazı Tipi");
        BitmapDrawable bd = new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(),R.drawable.text));
        font.setDrawable(bd);

        MenuObject music = new MenuObject("Müzik");
        music.setResource(R.drawable.musicplayer);

        MenuObject save= new MenuObject("Kaydet");
        save.setResource(R.drawable.save);


           menuObjects.add(close);
           menuObjects.add(template);
           menuObjects.add(color);
           menuObjects.add(font);
           menuObjects.add(music);
           menuObjects.add(save);





        return menuObjects;
    }

    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mToolBarTextView = (TextView) findViewById(R.id.text_view_toolbar_title);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {

            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
      /*  mToolbar.setNavigationIcon(R.mipmap.ic_launcher_round);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });*/
           String titlem="InWhiter";
        SpannableString s = new SpannableString(titlem);
        s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimaryDark)), 0, titlem.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mToolBarTextView.setText(s);
    }

    protected void addFragment(Fragment fragment, boolean addToBackStack, int containerId) {
        invalidateOptionsMenu();
        String backStackName = fragment.getClass().getName();
        boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStackName, 0);
        if (!fragmentPopped) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(containerId, fragment, backStackName)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            if (addToBackStack)
                transaction.addToBackStack(backStackName);
            transaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu:
                if (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                    mMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mMenuDialogFragment != null && mMenuDialogFragment.isAdded()) {
            mMenuDialogFragment.dismiss();
        } else {
            finish();
        }
    }

    @Override
    public void onMenuItemClick(View clickedView, int position) {
        if(position==1){

            themeSelected();
        }
        else if(position==2){
            colorSelected();
        }
        else if(position==3){
            fontSelected();
        }
        else if(position==4){
            MusicAdd();
        }
        else if(position==5){
            registerLayout();
        }
       // Toast.makeText(this, "Clicked on position: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMenuItemLongClick(View clickedView, int position) {
       // Toast.makeText(this, "Long clicked on position: " + position, Toast.LENGTH_SHORT).show();
    }
}
