package com.inwhiter.inviteapp.project.ActivityG;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inwhiter.inviteapp.project.ModelG.GuestListSingleton;
import com.inwhiter.inviteapp.project.R;

import java.util.Arrays;
import java.util.List;

public class GuestEditActivity extends AppCompatActivity {

    EditText name, phoneNumber;
    Spinner numberOfPeople, answer;
    CheckBox softcopy, hardcopy;
    Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_edit);
        String titlem=getSupportActionBar().getTitle().toString();

        SpannableString s = new SpannableString(titlem);
        s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimaryDark)), 0, titlem.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(s);

        Bundle extras = getIntent().getExtras();
        final int position = extras.getInt("position");

        name= (EditText) findViewById(R.id.et_guestEdit_name);
        phoneNumber= (EditText) findViewById(R.id.et_guestEdit_phoneNumber);
        numberOfPeople = (Spinner) findViewById(R.id.sp_guestEdit_numberOfPeople);
        answer = (Spinner) findViewById(R.id.sp_guestEdit_answer);
        softcopy = (CheckBox) findViewById(R.id.cb_guestEdit_softcopy);
        hardcopy = (CheckBox) findViewById(R.id.cb_guestEdit_hardcopy);
        edit = (Button) findViewById(R.id.bt_guestEdit_edit);

        softcopy.setChecked(GuestListSingleton.getInst().getguestList().get(position).getStatus().isSoftcopy());
        hardcopy.setChecked(GuestListSingleton.getInst().getguestList().get(position).getStatus().isHardcopy());

        final List<String> numberOfPeopleList = Arrays.asList("1","2","3","4","5","6","7","8","9","10");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, numberOfPeopleList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numberOfPeople.setAdapter(dataAdapter);
        int nopSpinnerPosition = dataAdapter.getPosition(String.valueOf(GuestListSingleton.getInst().getguestList().get(position).getStatus().getNumberOfPeople()));
        numberOfPeople.setSelection(nopSpinnerPosition);


        List<String> answerList = Arrays.asList("","Geliyor", "Belki", "Gelmiyor");
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, answerList);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        answer.setAdapter(dataAdapter2);
        int answerSpinnerPosition = dataAdapter2.getPosition(GuestListSingleton.getInst().getguestList().get(position).getStatus().getAnswer());
        answer.setSelection(answerSpinnerPosition);

        softcopy.setChecked(true);

        name.setText(GuestListSingleton.getInst().getguestList().get(position).getName());
        phoneNumber.setText(GuestListSingleton.getInst().getguestList().get(position).getPhoneNumber());


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuestListSingleton.getInst().getguestList().get(position).setName(name.getText().toString());
                GuestListSingleton.getInst().getguestList().get(position).setPhoneNumber(phoneNumber.getText().toString());
                GuestListSingleton.getInst().getguestList().get(position).getStatus().setAnswer(answer.getSelectedItem().toString());
                GuestListSingleton.getInst().getguestList().get(position).getStatus().setNumberOfPeople(Integer.parseInt(numberOfPeople.getSelectedItem().toString()));
                GuestListSingleton.getInst().getguestList().get(position).getStatus().setHardcopy(hardcopy.isChecked());
                GuestListSingleton.getInst().getguestList().get(position).getStatus().setSoftcopy(softcopy.isChecked());


                //veritabanındaki davetli verilerini değiştirme
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference guestRef = database.getReference("guest");
                guestRef.child(GuestListSingleton.getInst().getguestList().get(position).getGuestId()).setValue(GuestListSingleton.getInst().getguestList().get(position));


                Intent intent = new Intent(getBaseContext(), GuestActivity.class);
                startActivity(intent);

            }
        });



    }
}
