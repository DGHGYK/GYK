package com.inwhiter.inviteapp.project.ActivityG;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inwhiter.inviteapp.project.ModelG.InviteeListSingleton;
import com.inwhiter.inviteapp.project.R;

import java.util.Arrays;
import java.util.List;

public class InviteeEditActivity extends AppCompatActivity {

    EditText name, phoneNumber;
    Spinner numberOfPeople, answer;
    CheckBox softcopy, hardcopy;
    Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitee_edit);

        Bundle extras = getIntent().getExtras();
        final int position = extras.getInt("position");

        name= (EditText) findViewById(R.id.et_inviteeEdit_name);
        phoneNumber= (EditText) findViewById(R.id.et_inviteeEdit_phoneNumber);
        numberOfPeople = (Spinner) findViewById(R.id.sp_inviteeEdit_numberOfPeople);
        answer = (Spinner) findViewById(R.id.sp_inviteeEdit_answer);
        softcopy = (CheckBox) findViewById(R.id.cb_inviteeEdit_softcopy);
        hardcopy = (CheckBox) findViewById(R.id.cb_inviteeEdit_hardcopy);
        edit = (Button) findViewById(R.id.bt_inviteeEdit_edit);

        softcopy.setChecked(InviteeListSingleton.getInst().getInviteeList().get(position).getStatus().isSoftcopy());
        hardcopy.setChecked(InviteeListSingleton.getInst().getInviteeList().get(position).getStatus().isHardcopy());

        final List<String> numberOfPeopleList = Arrays.asList("1","2","3","4","5","6","7","8","9","10");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, numberOfPeopleList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numberOfPeople.setAdapter(dataAdapter);
        int nopSpinnerPosition = dataAdapter.getPosition(String.valueOf(InviteeListSingleton.getInst().getInviteeList().get(position).getStatus().getNumberOfPeople()));
        numberOfPeople.setSelection(nopSpinnerPosition);


        List<String> answerList = Arrays.asList("","Geliyor", "Belki", "Gelmiyor");
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, answerList);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        answer.setAdapter(dataAdapter2);
        int answerSpinnerPosition = dataAdapter2.getPosition(InviteeListSingleton.getInst().getInviteeList().get(position).getStatus().getAnswer());
        answer.setSelection(answerSpinnerPosition);

        softcopy.setChecked(true);

        name.setText(InviteeListSingleton.getInst().getInviteeList().get(position).getName());
        phoneNumber.setText(InviteeListSingleton.getInst().getInviteeList().get(position).getPhoneNumber());


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InviteeListSingleton.getInst().getInviteeList().get(position).setName(name.getText().toString());
                InviteeListSingleton.getInst().getInviteeList().get(position).setPhoneNumber(phoneNumber.getText().toString());
                InviteeListSingleton.getInst().getInviteeList().get(position).getStatus().setAnswer(answer.getSelectedItem().toString());
                InviteeListSingleton.getInst().getInviteeList().get(position).getStatus().setNumberOfPeople(Integer.parseInt(numberOfPeople.getSelectedItem().toString()));
                InviteeListSingleton.getInst().getInviteeList().get(position).getStatus().setHardcopy(hardcopy.isChecked());
                InviteeListSingleton.getInst().getInviteeList().get(position).getStatus().setSoftcopy(softcopy.isChecked());


                //veritabanındaki davetli verilerini değiştirme
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference inviteeRef = database.getReference("invitee");
                inviteeRef.child(InviteeListSingleton.getInst().getInviteeList().get(position).getInviteeId()).setValue(InviteeListSingleton.getInst().getInviteeList().get(position));


                Intent intent = new Intent(getBaseContext(), InviteeActivity.class);
                startActivity(intent);

            }
        });



    }
}
