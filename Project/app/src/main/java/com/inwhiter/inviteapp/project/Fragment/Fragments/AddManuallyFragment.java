package com.inwhiter.inviteapp.project.Fragment.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inwhiter.inviteapp.project.Fragment.BaseFragment;
import com.inwhiter.inviteapp.project.Fragment.FragmentController;
import com.inwhiter.inviteapp.project.ModelG.Guest;
import com.inwhiter.inviteapp.project.ModelG.GuestListSingleton;
import com.inwhiter.inviteapp.project.ModelG.GuestStatus;
import com.inwhiter.inviteapp.project.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddManuallyFragment extends BaseFragment {
    TextView explanation;
    EditText name;
    EditText phoneNumber;
    Button addManually;
    String inviteId;

    public AddManuallyFragment() {
        // Required empty public constructor
    }


    public static AddManuallyFragment newInstance(Bundle args){
        AddManuallyFragment fragment = new AddManuallyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getFID() {
        return R.layout.fragment_add_manually;
    }

    @Override
    protected void init() {
        explanation = (TextView) getActivity().findViewById(R.id.tv_addManually_explanation);
        name = (EditText) getActivity().findViewById(R.id.et_addManually_name);
        phoneNumber = (EditText) getActivity().findViewById(R.id.et_addManually_phoneNumber);
        addManually = (Button) getActivity().findViewById(R.id.bt_addManually_add);
        explanation.setText("Rehberinizde bulunmayan kişileri buradan davetli listenize ekleyebilirsiniz.");
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            inviteId= bundle.getString("inviteId");
        }
    }


    @Override
    protected void handlers() {

        addManually.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText()==null || name.getText().toString().equals("")){
                    Toast.makeText(getActivity(),"Davetiyenin kişiye özel gönderilmesi için davetli adını girmek zorunludur.", Toast.LENGTH_LONG).show();
                }else{
                    if(GuestListSingleton.getInst().isSameNumber( phoneNumber.getText().toString()))
                    {
                        /*InputMethodManager inputManager = (InputMethodManager)
                                getSystemService(Context.INPUT_METHOD_SERVICE);

                        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);*/
                        Toast.makeText(getActivity(),"Bu numara zaten davetli listenizdedir.", Toast.LENGTH_LONG).show();
                    }else{
                        //davetli kişi verisi veritabanına kaydedilir
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference guestRef = database.getReference("guest");
                        String guestId = guestRef.push().getKey();
                        Guest in = new Guest(guestId, inviteId, 0, name.getText().toString(), phoneNumber.getText().toString(), new GuestStatus());
                        //Singleton listeye yeni kişi eklenir
                        //GuestListSingleton.getInst().getGuestList().add(in);

                        guestRef.child(guestId).setValue(in);

                        DatabaseReference inviteRef = database.getReference("invite").child(inviteId).child("guestIds");
                        inviteRef.child(guestId).setValue(0);

                        Bundle bundle= new Bundle();
                        bundle.putString("inviteId", inviteId);
                        listener.changeFragment(FragmentController.GUEST, bundle);
                    }
                }
            }
        });

    }
}
