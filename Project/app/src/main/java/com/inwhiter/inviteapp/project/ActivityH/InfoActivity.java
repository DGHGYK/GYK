package com.inwhiter.inviteapp.project.ActivityH;


import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.inwhiter.inviteapp.project.BusineesH.DatePickerFragment;
import com.inwhiter.inviteapp.project.ModelG.Info;
import com.inwhiter.inviteapp.project.R;
import com.inwhiter.inviteapp.project.BusineesH.TimePickerFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class InfoActivity extends AppCompatActivity {
    Button btn_bilgi_save, btn_bilgi_imageload, btn_bilgi_videoload;
    ImageButton ibtn_bilgi_maintext, ibtn_bilgi_date, ibtn_bilgi_time;
    EditText et_bilgi_title, et_bilgi_maintext, et_bilgi_family1, et_bilgi_family2, et_bilgi_adress, et_bilgi_tag;
    TextView tv_bilgi_time, tv_bilgi_date;
    Context context = this;

    String SimageUri = null;
    String SvideoUri = null;

    Info info;

    private final static int REQUEST_SELECT_IMAGE = 200;
    ImageView foto;
    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.h_activity_info);


        btn_bilgi_save = (Button) findViewById(R.id.btn_bilgi_save);
        btn_bilgi_imageload = (Button) findViewById(R.id.btn_bilgi_imageload);
        btn_bilgi_videoload = (Button) findViewById(R.id.btn_bilgi_videoload);


        ibtn_bilgi_maintext = (ImageButton) findViewById(R.id.ibtn_bilgi_maintext);
        ibtn_bilgi_date = (ImageButton) findViewById(R.id.ibtn_bilgi_date);
        ibtn_bilgi_time = (ImageButton) findViewById(R.id.ibtn_bilgi_time);


        et_bilgi_title = (EditText) findViewById(R.id.et_bilgi_title);
        et_bilgi_maintext = (EditText) findViewById(R.id.et_bilgi_maintext);
        et_bilgi_family1 = (EditText) findViewById(R.id.et_bilgi_family1);
        et_bilgi_family2 = (EditText) findViewById(R.id.et_bilgi_family2);
        et_bilgi_adress = (EditText) findViewById(R.id.et_bilgi_adress1);


        et_bilgi_tag = (EditText) findViewById(R.id.et_bilgi_tag);

        tv_bilgi_time = (TextView) findViewById(R.id.tv_bilgi_time);
        tv_bilgi_date = (TextView) findViewById(R.id.tv_bilgi_date);


        /*verilen moda göre bilgi aktarımın şablon ekranına aktarıyor*/
        /*her bir seçim için şablon ekranına ayrı bir menu gönderiyor*/

        if (getIntent().getExtras().get("mood").equals("template")) {
            btn_bilgi_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    info = new Info( et_bilgi_title.getText().toString(),et_bilgi_maintext.getText().toString(),et_bilgi_family1.getText().toString(),
                            et_bilgi_family2.getText().toString(),et_bilgi_adress.getText().toString(),et_bilgi_tag.getText().toString(),tv_bilgi_date.getText().toString(), tv_bilgi_time.getText().toString());

                    Intent intent = new Intent(InfoActivity.this, TemplateActivity.class);
                    Toast.makeText(InfoActivity.this, "hazır şablon davetiye  sayfası yükleniyor", Toast.LENGTH_SHORT).show();
                    intent.putExtra("info", info);
                    intent.putExtra("menu", "template");/*şablon ekranında hazır şablon menüsü oluşsun diye*/
                    startActivity(intent);
                }
            });

                        /*hazır şablon seçildiğince foto ve video ekleme butonu kaybolsun*/
                        View video = findViewById(R.id.btn_bilgi_videoload);
                        video.setVisibility(View.INVISIBLE);
                        View image = findViewById(R.id.btn_bilgi_imageload);
                        image.setVisibility(View.INVISIBLE);


        } else if (getIntent().getExtras().get("mood").equals("image")) {

            btn_bilgi_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    info = new Info( et_bilgi_title.getText().toString(),et_bilgi_maintext.getText().toString(),et_bilgi_family1.getText().toString(),
                            et_bilgi_family2.getText().toString(),et_bilgi_adress.getText().toString(),et_bilgi_tag.getText().toString(),tv_bilgi_date.getText().toString(), tv_bilgi_time.getText().toString());

                    Intent intent = new Intent(InfoActivity.this, TemplateActivity.class);
                    Toast.makeText(InfoActivity.this, "foto davetiye sayfası yükleniyor", Toast.LENGTH_SHORT).show();
                    intent.putExtra("info", info);
                    intent.putExtra("foto", SimageUri);
                    intent.putExtra("menu", "camera");
                    startActivity(intent);

                }
            });

                    /* foto seçilince video butonu kaybolsun*/
                    View video = findViewById(R.id.btn_bilgi_videoload);
                    video.setVisibility(View.INVISIBLE);


        } else if (getIntent().getExtras().get("mood").equals("video")) {

            btn_bilgi_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    info = new Info( et_bilgi_title.getText().toString(),et_bilgi_maintext.getText().toString(),et_bilgi_family1.getText().toString(),
                            et_bilgi_family2.getText().toString(),et_bilgi_adress.getText().toString(),et_bilgi_tag.getText().toString(),tv_bilgi_date.getText().toString(), tv_bilgi_time.getText().toString());

                    Intent intent = new Intent(InfoActivity.this, TemplateActivity.class);
                    Toast.makeText(InfoActivity.this, "video davetiye sayfası yükleniyor", Toast.LENGTH_SHORT).show();
                    intent.putExtra("info", info);
                    intent.putExtra("menu", "video");
                    intent.putExtra("video", SvideoUri);


                    startActivity(intent);

                }
            });
                    /*video seçince foto butonu kaybolsun*/
                    View image = findViewById(R.id.btn_bilgi_imageload);
                    image.setVisibility(View.INVISIBLE);
        }


/* ********************************************************************************/


        //TIMEPICKER CALL
        ibtn_bilgi_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "TimePicker");


            }
        });

 /* *************************************************************************************************/

        // DATEPICKER CALL
        ibtn_bilgi_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment dFragment = new DatePickerFragment();

                // Show the date picker dialog fragment
                dFragment.show(getFragmentManager(), "Date Picker");

            }
        });

/* *********************************************************************************************/

        /* MAINTEXT SELECTED TEXT DIALOG WİNDOW*/

        ibtn_bilgi_maintext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);

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
                        Toast.makeText(InfoActivity.this, "Metin1 seçildi", Toast.LENGTH_SHORT).show();
                    }
                });
                maintext2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        et_bilgi_maintext.setText(maintext2.getText());
                        Toast.makeText(InfoActivity.this, "Metin2 seçildi", Toast.LENGTH_SHORT).show();
                    }
                });
                maintext3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        et_bilgi_maintext.setText(maintext3.getText());
                        Toast.makeText(InfoActivity.this, "Metin3 seçildi", Toast.LENGTH_SHORT).show();
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
                dialog = new Dialog(context);

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
        });}
                public Intent resimSecimiIntent() {

                    Uri outputFileUri = getCaptureImageOutputUri();

                    List<Intent> allIntents = new ArrayList<>();
                    PackageManager packageManager = getPackageManager();

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
                    File getImage = getExternalCacheDir();
                    if (getImage != null) {
                        outputFileUri = Uri.fromFile(new File(getImage.getPath(), "pickImageResult.jpeg"));
                    }
                    return outputFileUri;
                }

                @Override
                protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                    if (resultCode == Activity.RESULT_OK) {
                        Uri videoUri=data.getData();
                        SvideoUri=videoUri.toString();

                        if (requestCode == REQUEST_SELECT_IMAGE) {
                            Uri imageUri =getPickImageResultUri(data);
                            SimageUri=imageUri.toString();

                /*if(btn_crop.isClickable()){
                    startCropActivity(imageUri);


                }*/


                            //startCropActivity(imageUri);
                            foto.setImageURI(imageUri);

                          // String fotoname=String.valueOf(foto.getTag()) ;
                            Toast.makeText(InfoActivity.this, "seçim yapıldı", Toast.LENGTH_SHORT).show();

                        }


                    }
                }

               public Uri getPickImageResultUri(Intent data) {
                    boolean isCamera = true;
                    if (data != null && data.getData() != null) {
                        String action = data.getAction();
                        isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
                    }
                    return isCamera ? getCaptureImageOutputUri() : data.getData();
                }






/* ********************************************************/
/*


        /* ****************************************************************************************/



        /* ******************************************************************************************/













    }


