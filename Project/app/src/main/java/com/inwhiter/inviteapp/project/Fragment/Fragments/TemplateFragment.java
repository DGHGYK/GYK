package com.inwhiter.inviteapp.project.Fragment.Fragments;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.inwhiter.inviteapp.project.BusineesH.CustomAdaptorMediaplayer;
import com.inwhiter.inviteapp.project.Fragment.BaseFragment;
import com.inwhiter.inviteapp.project.Fragment.FragmentController;
import com.inwhiter.inviteapp.project.ModelG.Info;
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
import java.util.List;
import java.util.concurrent.TimeUnit;

import yuku.ambilwarna.AmbilWarnaDialog;

public class TemplateFragment extends BaseFragment  implements OnMenuItemClickListener, OnMenuItemLongClickListener {
    private static final String TAG = "DEMO-REARRANGEABLE-LOUT";
    public static String inviteId;
    private RearrangeableLayout root;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;

    private StorageReference mStorageRef;

    int id;//hangi layouttayız ?
    public List<Media> playlist = new ArrayList<Media>();
    public ListView lv_playlist;
    public ImageView icon_play;
    public MediaPlayer mp = null;
    public double startTime = 0;
    public double finalTime = 0;
    public int oneTimeOnly = 0;
    public Handler myHandler = new Handler();
    public SeekBar seekBar;
    public ImageView play_pause, on_off;
    public TextView current_duration, total_duration, current_music;
    Button btn_ok;
    Bundle bundleInfo,bundleFont,bundleColor;
    int sablonid=0;

    TextView s1_title, s1_maintext, s1_family1, s1_family2, s1_adress, s1_tag, s1_time, s1_date;//sablon1 için
    TextView s2_title, s2_maintext, s2_family1, s2_family2, s2_adress, s2_tag, s2_time, s2_date;//şablon2 için
    TextView s3_title, s3_maintext, s3_family1, s3_family2, s3_adress, s3_tag, s3_time, s3_date;//şablon3 için

    TextView c1_title, c1_maintext, c1_family1, c1_family2, c1_adress, c1_tag, c1_time, c1_date;//cameradan foto layout için

    TextView v1_title, v1_maintext, v1_family1, v1_family2, v1_adress, v1_tag, v1_time, v1_date;//cameradan video layout için

    VideoView v1_video;

    Info info;
    Bundle bnd;
    String font;

    Uri foto, video;
    int DefaultColor;
    RadioButton rb_bonbonRegularfont, rb_solenafont, rb_glaresomefont, rb_geckofont;//fontlar
    Typeface face, face2, face3, face4;//fonttipi
    Drawable image = null;

    ImageView invalidation1, invalidation2, invalidation3, c1_foto;
    Context context = getActivity();

    String menu,title, maintext, family1, family2, date, time, adress, tag, fotoS, videoS;

    private FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;

    public TemplateFragment newInstance(Bundle args) {//bundle ile infodan gelen bilgileri almak için
        TemplateFragment fragment = new TemplateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getFID() {
//        Bundle bundle=this.getArguments();
//        String menuuuu=bundle.getString("menu");
//        Toast.makeText(getActivity(), "infodan gelen manu adı :"+menuuuu , Toast.LENGTH_SHORT).show();

        return R.layout.fragment_template;
    }

    @Override
    protected void init() {
        setHasOptionsMenu(true);
        Bundle bundle = this.getArguments();
        info = bundle.getParcelable("info");
        menu = bundle.getString("menu");

        bundleInfo=new Bundle();
        bundleInfo.putParcelable("info",info);
        bundleInfo.putInt("Color",-16185079);

        if (menu.equals("template")) {

            Toast.makeText(getActivity(), "infodan gelenler menu:" + menu, Toast.LENGTH_SHORT).show();
            listener.selectTemplate(FragmentController.TEMPLATE1, bundleInfo);
            contextMenu();


        }
    }
//
//
////        //girilen değerler veri tabanına kaydedilir
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference inviteRef = database.getReference("invite");
//
//        mAuth = FirebaseAuth.getInstance();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        Invite invite = new Invite(currentUser.getUid(), info, 1, "", getArguments().getString("menu"), Calendar.getInstance().getTime(), new ArrayList<String>());
//        if (inviteId == null || inviteId.equals("")) {
//            inviteId = inviteRef.push().getKey();
//        }
//        inviteRef.child(inviteId).setValue(invite);

//         /*seçeneğe göre layoutların çağırımı*/resim ve videoyu eklediğinde ekle
        /*REARREGENABLE LAYOUT FUNCTION*/

  /*      root.setChildPositionListener(new RearrangeableLayout.ChildPositionListener() {
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
        });*//*
        }*/

    //}//}

    public void contextMenu(){
        /* context menu*/
        fragmentManager = getActivity().getSupportFragmentManager();
        initToolbar();
        initMenuFragment();
    }

//    /*fontSelected function*/
   public void fontSelected() {
       final Dialog dialog = new Dialog(getActivity());

       dialog.setContentView(R.layout.h_custom_dialog_font);
       dialog.setTitle("Font");


       rb_bonbonRegularfont = (RadioButton) dialog.findViewById(R.id.rb_bilgi_bonbon);
       rb_solenafont = (RadioButton) dialog.findViewById(R.id.rb_bilgi_solenafont);
       rb_glaresomefont = (RadioButton) dialog.findViewById(R.id.rb_bilgi_glarosomefont);
       rb_geckofont = (RadioButton) dialog.findViewById(R.id.rb_bilgi_geckofont);


//        /*fontlar*/
       face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/BonbonRegular.otf");
       rb_bonbonRegularfont.setTypeface(face);
       face2 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/solena.otf");
       rb_solenafont.setTypeface(face2);
       face3 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Glaresome.otf");
       rb_glaresomefont.setTypeface(face3);
       face4 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Gecko.ttf");
       rb_geckofont.setTypeface(face4);



        final Button ok = (Button) dialog.findViewById(R.id.btn_cdfont_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rb_bonbonRegularfont.isChecked()) {
                    font="face1";


             } else if (rb_solenafont.isChecked()) {
                   font="face2";

                } else if (rb_glaresomefont.isChecked()) {
                   font="face3";

                } else if (rb_geckofont.isChecked()) {
                   font="face4";
                }
                bundleInfo.putString("font",font);
                listener.selectTemplate(String.valueOf(FragmentController.getInstance().currentFragment), bundleInfo);
                Toast.makeText(getActivity(),"gönderilen font: "+font, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();


    }

    /*colorSelected function*/
    public void colorSelected() {
        OpenColorPickerDialog(false);
    }

    public void OpenColorPickerDialog(boolean AlphaSupport) {

        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(getActivity(), DefaultColor, AlphaSupport, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog ambilWarnaDialog, int color) {
                //bundleColor=new Bundle();
                DefaultColor = color;
                bundleInfo.putInt("Color",color);

                    listener.selectTemplate(String.valueOf(FragmentController.getInstance().currentFragment), bundleInfo);
                    Toast.makeText(getActivity(), "gönderilen color1 kod" + color, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {
                Toast.makeText(getActivity(), "Color Picker Closed", Toast.LENGTH_SHORT).show();
            }
        });
        ambilWarnaDialog.show();
    }

//    /*layout screenshot*/
    public void registerLayout() {


/*        Intent intent=new Intent(TemplateActivity2.this,LayoutSS.class);
        intent.putExtra("inviteId", inviteId);*/

        byte[] encoded=takeScreenshot();

        Bundle bundle = new Bundle();
        bundle.putString("inviteId", inviteId);
        bundle.putByteArray("ss", encoded);
        // TODO: 9.11.2017 Layoutss oluşturulduktan sonra burası değiştirilecek.
        listener.changeFragment(FragmentController.LAYOUTSS,bundle);
        Toast.makeText(getActivity(), "SS ALINDI", Toast.LENGTH_SHORT).show();



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
       // rootView=FragmentController.getInstance().getCurrentFragment();
       //   rootView=FragmentController.getInstance().getFragment(FragmentController.getInstance().getCurrentFragment());
        if(FragmentController.getInstance().getCurrentFragment()==FragmentController.TEMPLATE1)rootView=getActivity().findViewById(R.id.sablon1);
      //else Toast.makeText(getActivity(), "SS ALInamadı", Toast.LENGTH_SHORT).show();

        else if(FragmentController.getInstance().getCurrentFragment()==FragmentController.TEMPLATE2) rootView=getActivity().findViewById(R.id.sablon2);
        else if(FragmentController.getInstance().getCurrentFragment()==FragmentController.TEMPLATE3) rootView=getActivity().findViewById(R.id.sablon3);
     //  // else if(id==R.layout.h_camerabir) rootView=getActivity().findViewById(R.id.camera1);
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
//
//    /*fotoLayoutSelected function*/
//    public void fotoLayoutSelected(){
//
//        //setContentView(R.layout.h_camerabir);
//        contextMenu();
//
//        fotoS=getArguments().getString("foto");
//        foto=Uri.parse(fotoS);//GAMZE FIRABASE E ATACAĞIN RESİM
//
//        c1_title = (TextView) getActivity().findViewById(R.id.tv_camera_title);
//        c1_title.setText(title);
//
//        c1_maintext = (TextView) getActivity().findViewById(R.id.tv_camera_maintext);
//        c1_maintext.setText(maintext);
//
//        c1_family1 = (TextView) getActivity().findViewById(R.id.tv_camera_family1);
//        c1_family1.setText(family1);
//
//        c1_family2 = (TextView) getActivity().findViewById(R.id.tv_camera_family2);
//        c1_family2.setText(family2);
//
//        c1_adress = (TextView) getActivity().findViewById(R.id.tv_camera_adress);
//        c1_adress.setText(adress);
//
//        c1_time = (TextView) getActivity().findViewById(R.id.tv_camera_time);
//        c1_time.setText(time);
//
//        c1_date = (TextView) getActivity().findViewById(R.id.tv_camera_date);
//        c1_date.setText(date);
//
//        c1_tag = (TextView) getActivity().findViewById(R.id.tv_camera_tag);
//        c1_tag.setText(tag);
//
//        c1_foto = (ImageView) getActivity().findViewById(R.id.iv_camera_foto);
//        c1_foto.setImageURI(foto);
//
//
//
//
//    }
//    /*videoLayoutSelected*/
//    public void videoLayoutSelected(){
//
//        //setContentView(R.layout.h_video1);
//
//        videoS=getArguments().getString("video");
//        video=Uri.parse(videoS);//GAMZE FIREBASE ATACAĞIN VİDEO
//
//        v1_title = (TextView) getActivity().findViewById(R.id.tv_video_title);
//        v1_title.setText(title);
//
//        v1_maintext = (TextView) getActivity().findViewById(R.id.tv_video_maintext);
//        v1_maintext.setText(maintext);
//
//        v1_family1 = (TextView) getActivity().findViewById(R.id.tv_video_family1);
//        v1_family1.setText(family1);
//
//        v1_family2 = (TextView) getActivity().findViewById(R.id.tv_video_family2);
//        v1_family2.setText(family2);
//
//        v1_adress = (TextView) getActivity().findViewById(R.id.tv_video_adress);
//        v1_adress.setText(adress);
//
//        v1_time = (TextView) getActivity().findViewById(R.id.tv_video_time);
//        v1_time.setText(time);
//
//        v1_date = (TextView) getActivity().findViewById(R.id.tv_video_date);
//        v1_date.setText(date);
//
//        v1_tag = (TextView) getActivity().findViewById(R.id.tv_video_tag);
//        v1_tag.setText(tag);
//
//        v1_video=(VideoView) getActivity().findViewById(R.id.vv_video);
//        v1_video.setVideoURI(video);
//        v1_video.start();
//
//    }
//
//
//
//    /*themeSelected function*/
    public void themeSelected() {

        final Dialog dialog_theme = new Dialog(getActivity());

        dialog_theme.setContentView(R.layout.h_custom_dilaog_sablon_choose);
        dialog_theme.setTitle("şablon");


        invalidation1 = (ImageView) dialog_theme.findViewById(R.id.iv_cd_sablon_choose_inva1);
        invalidation2 = (ImageView) dialog_theme.findViewById(R.id.iv_cd_sablon_choose_inva2);
        invalidation3 = (ImageView) dialog_theme.findViewById(R.id.iv_cd_sablon_choose_inva3);



        invalidation1.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "şablon1 seçildi", Toast.LENGTH_SHORT).show();
                listener.selectTemplate(FragmentController.TEMPLATE1,bundleInfo);
                sablonid=1;

            }
        });


        invalidation2.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "şablon2 seçildi", Toast.LENGTH_SHORT).show();
                listener.selectTemplate(FragmentController.TEMPLATE2,bundleInfo);
                sablonid=2;
               // listener.changeFragment(FragmentController.TEMPLATE1,bundleInfo);

            }
        });

        invalidation3.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "şablon3 seçildi", Toast.LENGTH_SHORT).show();
                listener.selectTemplate(FragmentController.TEMPLATE3,bundleInfo);
                sablonid=3;

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
        final Dialog dialog = new Dialog(getActivity());

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

        final CustomAdaptorMediaplayer adp=new CustomAdaptorMediaplayer(getActivity(), playlist);
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
//                        database = FirebaseDatabase.getInstance();
//                        DatabaseReference musicRef = database.getReference("invite").child(inviteId).child("info").child("music");
//                        musicRef.setValue(firebase_music_name);
                        Toast.makeText(getActivity(),media.getMusic_name()+ " seçildi", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getActivity(),firebase_music_name+ " seçildi", Toast.LENGTH_SHORT).show();

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

                    mp = MediaPlayer.create(getActivity(), R.raw.dm);
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
                    mp = MediaPlayer.create(getActivity(), R.raw.happy1);
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
                    mp = MediaPlayer.create(getActivity(), R.raw.happy2);
                    current_music.setText("Happy2");
                    play();

                }
                else if(media.getMusic_name().equals("happy3"))

                {
                    if (mp != null) {
                        play_pause.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_black));

                        mp.reset();


                    }
                    mp = MediaPlayer.create(getActivity(), R.raw.happy3);
                    current_music.setText("Happy3");
                    play();

                }
                else if(media.getMusic_name().equals("slow1"))

                {
                    if (mp != null) {
                        play_pause.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_black));

                        mp.reset();


                    }
                    mp = MediaPlayer.create(getActivity(), R.raw.slow1);
                    current_music.setText("slow1");
                    play();

                }
                else if(media.getMusic_name().equals("slow2"))

                {
                    if (mp != null) {
                        play_pause.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_black));

                        mp.reset();


                    }
                    mp = MediaPlayer.create(getActivity(), R.raw.slow2);
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

            @SuppressLint("DefaultLocale")
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
                @SuppressLint("DefaultLocale")
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

        });
        dialog.show();
    }

    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        Log.d(TAG, "initMenuFragment: " + getMenuObjects());
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
        Toolbar mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        TextView mToolBarTextView = (TextView) getActivity().findViewById(R.id.text_view_toolbar_title);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {

            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
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
        getActivity().invalidateOptionsMenu();
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
    protected void handlers() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu:
                mMenuDialogFragment.show(fragmentManager,"ContextMenuDialogFragment");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMenuItemClick(View clickedView, int position) {
        if(position==1){
            Toast.makeText(getActivity(), "şablon seçme tıklandı" , Toast.LENGTH_SHORT).show();
            themeSelected();
        }
        else if(position==2){
            Toast.makeText(getActivity(), "renk seçme tıklandı" , Toast.LENGTH_SHORT).show();
            //if(FragmentController.TEMPLATE1)
            colorSelected();
        }
        else if(position==3){
            Toast.makeText(getActivity(), "font seçme tıklandı" , Toast.LENGTH_SHORT).show();
            fontSelected();
        }
        else if(position==4){
            Toast.makeText(getActivity(), "müzik ekleme tıklandı" , Toast.LENGTH_SHORT).show();
            MusicAdd();
        }
        else if(position==5){
            Toast.makeText(getActivity(), "ss alma tıklandı" , Toast.LENGTH_SHORT).show();
            registerLayout();
        }
        // Toast.makeText(this, "Clicked on position: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMenuItemLongClick(View clickedView, int position) {
        // Toast.makeText(this, "Long clicked on position: " + position, Toast.LENGTH_SHORT).show();
    }
}
