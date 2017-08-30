package com.example.hatice.project.ActivityG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hatice.project.ModelG.Invitee;
import com.example.hatice.project.ModelG.InviteeListSingleton;
import com.example.hatice.project.R;

public class AddManuallyActivity extends AppCompatActivity {

    TextView explanation;
    EditText name;
    EditText phoneNumber;
    Button addManually;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_manually);

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
                    if(InviteeListSingleton.getInst().isSameNumber( phoneNumber.getText().toString()))
                    {
                        InputMethodManager inputManager = (InputMethodManager)
                                getSystemService(Context.INPUT_METHOD_SERVICE);

                        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                        Toast.makeText(getBaseContext(),"Bu numara zaten davetli listenizde", Toast.LENGTH_LONG).show();
                    }else{
                        Invitee in = new Invitee("10", name.getText().toString(), phoneNumber.getText().toString(), null);
                        InviteeListSingleton.getInst().getInviteeList().add(in);
                        Intent intent = new Intent(AddManuallyActivity.this, InviteeActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });

        }
    }

