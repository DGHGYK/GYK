package com.inwhiter.inviteapp.project.ActivityG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inwhiter.inviteapp.project.ModelG.Guest;
import com.inwhiter.inviteapp.project.ModelG.GuestListSingleton;
import com.inwhiter.inviteapp.project.ModelG.GuestStatus;
import com.inwhiter.inviteapp.project.R;

public class AddManuallyActivity extends AppCompatActivity {

    TextView explanation;
    EditText name;
    EditText phoneNumber;
    Button addManually;
    String inviteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_manually);
        String titlem=getSupportActionBar().getTitle().toString();

        SpannableString s = new SpannableString(titlem);
        s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimaryDark)), 0, titlem.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(s);

        Bundle bundle= getIntent().getExtras();
        if(bundle!=null){
            inviteId = bundle.getString("inviteId");
        }

        explanation = (TextView) findViewById(R.id.tv_addManually_explanation);
        name = (EditText) findViewById(R.id.et_addManually_name);
        phoneNumber = (EditText) findViewById(R.id.et_addManually_phoneNumber);
        addManually = (Button) findViewById(R.id.bt_addManually_add);
        explanation.setText("Rehberinizde bulunmayan kişileri buradan davetli listenize ekleyebilirsiniz.");

        addManually.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText()==null || name.getText().toString().equals("")){
                    Toast.makeText(getBaseContext(),"Davetiyenin kişiye özel gönderilmesi için davetli adını girmek zorunludur.", Toast.LENGTH_LONG).show();
                }else{
                    if(GuestListSingleton.getInst().isSameNumber( phoneNumber.getText().toString()))
                    {
                        InputMethodManager inputManager = (InputMethodManager)
                                getSystemService(Context.INPUT_METHOD_SERVICE);

                        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                        Toast.makeText(getBaseContext(),"Bu numara zaten davetli listenizde", Toast.LENGTH_LONG).show();
                    }else{
                        //davetli kişi verisi veritabanına kaydedilir
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference guestRef = database.getReference("guest");

                        String guestId = guestRef.push().getKey();
                        Guest in = new Guest(guestId, inviteId, 0, name.getText().toString(), phoneNumber.getText().toString(), new GuestStatus());
                        //Singleton listeye yeni kişi eklenir
                        GuestListSingleton.getInst().getguestList().add(in);

                        guestRef.child(guestId).setValue(in);

                        Intent intent = new Intent(AddManuallyActivity.this, GuestActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });

        }
    }

