package com.inwhiter.inviteapp.project.Fragment.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inwhiter.inviteapp.project.Fragment.BaseFragment;
import com.inwhiter.inviteapp.project.Fragment.FragmentController;
import com.inwhiter.inviteapp.project.ModelG.GuestListSingleton;
import com.inwhiter.inviteapp.project.R;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GuestEditFragment extends BaseFragment {
    EditText name, phoneNumber;
    Spinner numberOfPeople, answer;
    CheckBox softcopy, hardcopy;
    Button edit;
    int position;
    String inviteId;


    public GuestEditFragment() {
        // Required empty public constructor
    }


    public static GuestEditFragment newInstance(Bundle args){
        GuestEditFragment fragment = new GuestEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getFID() {
        return R.layout.fragment_guest_edit;
    }

    @Override
    protected void init() {

        name= (EditText) getActivity().findViewById(R.id.et_guestEdit_name);
        phoneNumber= (EditText) getActivity().findViewById(R.id.et_guestEdit_phoneNumber);
        numberOfPeople = (Spinner) getActivity().findViewById(R.id.sp_guestEdit_numberOfPeople);
        answer = (Spinner) getActivity().findViewById(R.id.sp_guestEdit_answer);
        softcopy = (CheckBox) getActivity().findViewById(R.id.cb_guestEdit_softcopy);
        hardcopy = (CheckBox) getActivity().findViewById(R.id.cb_guestEdit_hardcopy);
        edit = (Button) getActivity().findViewById(R.id.bt_guestEdit_edit);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            position = bundle.getInt("position");
            inviteId = bundle.getString("inviteId");
        }
    }


    @Override
    protected void handlers() {

        softcopy.setChecked(GuestListSingleton.getInst().getGuestList().get(position).getStatus().isSoftcopy());
        hardcopy.setChecked(GuestListSingleton.getInst().getGuestList().get(position).getStatus().isHardcopy());

        final List<String> numberOfPeopleList = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, numberOfPeopleList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numberOfPeople.setAdapter(dataAdapter);
        int nopSpinnerPosition = dataAdapter.getPosition(String.valueOf(GuestListSingleton.getInst().getGuestList().get(position).getStatus().getNumberOfPeople()));
        numberOfPeople.setSelection(nopSpinnerPosition);


        List<String> answerList = Arrays.asList("", "Geliyor", "Belki", "Gelmiyor");
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, answerList);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        answer.setAdapter(dataAdapter2);
        int answerSpinnerPosition = dataAdapter2.getPosition(GuestListSingleton.getInst().getGuestList().get(position).getStatus().getAnswer());
        answer.setSelection(answerSpinnerPosition);

        softcopy.setChecked(true);

        name.setText(GuestListSingleton.getInst().getGuestList().get(position).getName());
        phoneNumber.setText(GuestListSingleton.getInst().getGuestList().get(position).getPhoneNumber());


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuestListSingleton.getInst().getGuestList().get(position).setName(name.getText().toString());
                GuestListSingleton.getInst().getGuestList().get(position).setPhoneNumber(phoneNumber.getText().toString());
                GuestListSingleton.getInst().getGuestList().get(position).getStatus().setAnswer(answer.getSelectedItem().toString());
                GuestListSingleton.getInst().getGuestList().get(position).getStatus().setNumberOfPeople(Integer.parseInt(numberOfPeople.getSelectedItem().toString()));
                GuestListSingleton.getInst().getGuestList().get(position).getStatus().setHardcopy(hardcopy.isChecked());
                GuestListSingleton.getInst().getGuestList().get(position).getStatus().setSoftcopy(softcopy.isChecked());


                //veritabanındaki davetli verilerini değiştirme
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference guestRef = database.getReference("guest");
                guestRef.child(GuestListSingleton.getInst().getGuestList().get(position).getGuestId()).setValue(GuestListSingleton.getInst().getGuestList().get(position));


                Bundle bundle = new Bundle();
                bundle.putString("inviteId", inviteId);
                listener.changeFragment(FragmentController.GUEST, bundle);

            }
        });

    }
    }
