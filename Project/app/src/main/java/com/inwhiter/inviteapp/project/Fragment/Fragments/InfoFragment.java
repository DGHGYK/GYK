package com.inwhiter.inviteapp.project.Fragment.Fragments;


import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.inwhiter.inviteapp.project.BusineesH.DatePickerFragment;
import com.inwhiter.inviteapp.project.BusineesH.TimePickerFragment;
import com.inwhiter.inviteapp.project.Fragment.BaseFragment;
import com.inwhiter.inviteapp.project.Fragment.FragmentController;
import com.inwhiter.inviteapp.project.ModelG.Info;
import com.inwhiter.inviteapp.project.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends BaseFragment {


    Button btn_bilgi_save, btn_bilgi_imageload, btn_bilgi_videoload;
    ImageButton ibtn_bilgi_maintext, ibtn_bilgi_date, ibtn_bilgi_time;
    EditText et_bilgi_title, et_bilgi_maintext, et_bilgi_family1, et_bilgi_family2, et_bilgi_adress, et_bilgi_tag;
    TextView tv_bilgi_time, tv_bilgi_date;

    String SimageUri = null;
    String SvideoUri = null;
    String data;
    String mood;

    Info info;

    private final static int REQUEST_SELECT_IMAGE = 200;
    ImageView foto;
    Dialog dialog;
    public InfoFragment() {
        // Required empty public constructor
    }
    public static InfoFragment newInstance(Bundle args) {//bundle ile topicten değer aldığı için
        InfoFragment fragment = new InfoFragment();
        fragment.setArguments(args);
        return fragment;

    }
    @Override
    protected int getFID() {

        return R.layout.fragment_info;
    }

    @Override
    protected void init() {
/*        String title=getActivity().getActionBar().getTitle().toString();

        SpannableString s = new SpannableString(title);
        s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimaryDark)), 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
       getActivity().getActionBar().setTitle(s);*/


        Bundle bundle = this.getArguments();//topicten gelen mood değerini aldık
        if (bundle != null) {
            mood= bundle.getString("mood");
        }

        btn_bilgi_save = (Button) getActivity().findViewById(R.id.btn_bilgi_save);
        btn_bilgi_imageload = (Button) getActivity().findViewById(R.id.btn_bilgi_imageload);
        btn_bilgi_videoload = (Button) getActivity().findViewById(R.id.btn_bilgi_videoload);


        ibtn_bilgi_maintext = (ImageButton) getActivity().findViewById(R.id.ibtn_bilgi_maintext);
        ibtn_bilgi_date = (ImageButton) getActivity().findViewById(R.id.ibtn_bilgi_date);
        ibtn_bilgi_time = (ImageButton) getActivity().findViewById(R.id.ibtn_bilgi_time);


        et_bilgi_title = (EditText) getActivity().findViewById(R.id.et_bilgi_title);
        et_bilgi_maintext = (EditText) getActivity().findViewById(R.id.et_bilgi_maintext);
        et_bilgi_family1 = (EditText) getActivity().findViewById(R.id.et_bilgi_family1);
        et_bilgi_family2 = (EditText) getActivity().findViewById(R.id.et_bilgi_family2);
        et_bilgi_adress = (EditText) getActivity().findViewById(R.id.et_bilgi_adress1);


        et_bilgi_tag = (EditText) getActivity().findViewById(R.id.et_bilgi_tag);

        tv_bilgi_time = (TextView) getActivity().findViewById(R.id.tv_bilgi_time);
        tv_bilgi_date = (TextView) getActivity().findViewById(R.id.tv_bilgi_date);


        /*verilen moda göre bilgi aktarımın şablon ekranına aktarıyor*/
        /*her bir seçim için şablon ekranına ayrı bir menu gönderiyor*/

        if (mood.equals("template")) {
            btn_bilgi_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    info = new Info( et_bilgi_title.getText().toString(),et_bilgi_maintext.getText().toString(),et_bilgi_family1.getText().toString(),
                            et_bilgi_family2.getText().toString(),et_bilgi_adress.getText().toString(),et_bilgi_tag.getText().toString(),tv_bilgi_date.getText().toString(), tv_bilgi_time.getText().toString());

                    Bundle bundle=new Bundle();
                    //Bundle bundle2=new Bundle();
                    bundle.putParcelable("info",info);
                    bundle.putString("menu", "template");
                    listener.changeFragment(FragmentController.TEMPLATE,bundle);//template e değerler gönderdik
                   // listener.changeFragment(FragmentController.TEMPLATE,bundle2);
              //      Intent intent = new Intent(InfoActivity.this, TemplateActivity2.class);
                    //   data= getIntent().getExtras().get("mood").toString();

                    //Intent intent2 = new Intent(InfoActivity.this, MainFragment.class);
                   //intent2.putExtra("menu","template");
                    //startActivityFromFragment(MainFragment,);

                   // intent.putExtra("info", info);

                    //intent.putExtra("menu", "template");/*şablon ekranında hazır şablon menüsü oluşsun diye*/
                   // startActivity(intent);
                }
            });

                        /*hazır şablon seçildiğince foto ve video ekleme butonu kaybolsun*/
            View video = getActivity().findViewById(R.id.btn_bilgi_videoload);
            video.setVisibility(View.INVISIBLE);
            View image = getActivity().findViewById(R.id.btn_bilgi_imageload);
            image.setVisibility(View.INVISIBLE);


        } else if (mood.equals("image")) {

            btn_bilgi_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    info = new Info( et_bilgi_title.getText().toString(),et_bilgi_maintext.getText().toString(),et_bilgi_family1.getText().toString(),
                            et_bilgi_family2.getText().toString(),et_bilgi_adress.getText().toString(),et_bilgi_tag.getText().toString(),tv_bilgi_date.getText().toString(), tv_bilgi_time.getText().toString());


                    Bundle bundle=new Bundle();
                   // Bundle bundle2=new Bundle();
                   // Bundle bundle3=new Bundle();
                    bundle.putParcelable("info",info);
                    bundle.putString("menu", "camera");
                    bundle.putString("foto", SimageUri);
                    listener.changeFragment(FragmentController.TEMPLATE,bundle);
                    //listener.changeFragment(FragmentController.TEMPLATE,bundle2);
                    //listener.changeFragment(FragmentController.TEMPLATE,bundle3);
                    /* Intent intent = new Intent(InfoActivity.this, TemplateActivity2.class);

                    intent.putExtra("info", info);
                    intent.putExtra("foto", SimageUri);
                    intent.putExtra("menu", "camera");
                    startActivity(intent);*/

                }
            });

                    /* foto seçilince video butonu kaybolsun*/
            View video = getActivity().findViewById(R.id.btn_bilgi_videoload);
            video.setVisibility(View.INVISIBLE);


        } else if (mood.equals("video")) {

            btn_bilgi_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    info = new Info( et_bilgi_title.getText().toString(),et_bilgi_maintext.getText().toString(),et_bilgi_family1.getText().toString(),
                            et_bilgi_family2.getText().toString(),et_bilgi_adress.getText().toString(),et_bilgi_tag.getText().toString(),tv_bilgi_date.getText().toString(), tv_bilgi_time.getText().toString());

                   
                    Bundle bundle=new Bundle();
                   // Bundle bundle2=new Bundle();
                   // Bundle bundle3=new Bundle();
                    bundle.putParcelable("info",info);
                    bundle.putString("menu", "video");
                    bundle.putString("foto", SimageUri);
                    listener.changeFragment(FragmentController.TEMPLATE,bundle);
                    //listener.changeFragment(FragmentController.TEMPLATE,bundle2);
                    //listener.changeFragment(FragmentController.TEMPLATE,bundle3);

                }
            });
                    /*video seçince foto butonu kaybolsun*/
            View image = getActivity().findViewById(R.id.btn_bilgi_imageload);
            image.setVisibility(View.INVISIBLE);
        }


/* ********************************************************************************/


        //TIMEPICKER CALL
        ibtn_bilgi_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getActivity().getFragmentManager(), "TimePicker"); // TODO: 14.11.2017 bunları sor hocaya nasıl olcak


            }
        });

 /* *************************************************************************************************/

        // DATEPICKER CALL
        ibtn_bilgi_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment dFragment = new DatePickerFragment();

                // Show the date picker dialog fragment
                dFragment.show(getActivity().getFragmentManager(), "Date Picker");//// TODO: 14.11.2017  info

            }
        });

/* *********************************************************************************************/

        /* MAINTEXT SELECTED TEXT DIALOG WİNDOW*/

        ibtn_bilgi_maintext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());

                dialog.setContentView(R.layout.h_customer_dialog_sablon_maintext);
                dialog.setTitle("Hazır Ana Metin");

                final TextView maintext1 = (TextView) dialog.findViewById(R.id.tv_cd_maintext1);
                final TextView maintext2 = (TextView) dialog.findViewById(R.id.tv_cd_maintext2);
                final TextView maintext3 = (TextView) dialog.findViewById(R.id.tv_cd_maintext3);
                final Button ok = (Button) dialog.findViewById(R.id.btn_ok);
                maintext1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        et_bilgi_maintext.setText(maintext1.getText());

                    }
                });
                maintext2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        et_bilgi_maintext.setText(maintext2.getText());

                    }
                });
                maintext3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        et_bilgi_maintext.setText(maintext3.getText());

                    }
                });
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();


            }
        });

        /* **************************************************************************************/

        /*ADD VIDEO CUSTOM DIALOG CALL*/
        btn_bilgi_videoload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 0);
            }
        });



            /* CAMERA SELECTED FOTO DIALOG WİNDOW*/

        btn_bilgi_imageload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(getActivity());

                dialog.setContentView(R.layout.h_custom_dialog_fotoadd);
                dialog.setTitle("Foto Ekle");

                final Button ok= (Button) dialog.findViewById(R.id.btn_ok);
                foto=(ImageView) dialog.findViewById(R.id.iv_cd_fotoadd);
                final Button selected= (Button) dialog.findViewById(R.id.btn_cd_fotoadd);

                selected.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivityForResult(resimSecimiIntent(), REQUEST_SELECT_IMAGE);

                    }
                });
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();



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
        return inflater.inflate(R.layout.fragment_info, container, false);
    }


    public Intent resimSecimiIntent() {

        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getActivity().getPackageManager();// TODO: 14.11.2017

        Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");

        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }

    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getActivity().getExternalCacheDir();// TODO: 14.11.2017
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "pickImageResult.jpeg"));
        }
        return outputFileUri;
    }

  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Uri videoUri=data.getData();
            SvideoUri=videoUri.toString();

            if (requestCode == REQUEST_SELECT_IMAGE) {
                Uri imageUri =getPickImageResultUri(data);
                SimageUri=imageUri.toString();

                /*if(btn_crop.isClickable()){
                    startCropActivity(imageUri);


                }


                //startCropActivity(imageUri);
                foto.setImageURI(imageUri);

                // String fotoname=String.valueOf(foto.getTag()) ;


            }


        }
    }*/

    public Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null && data.getData() != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }
        return isCamera ? getCaptureImageOutputUri() : data.getData();
    }
}

