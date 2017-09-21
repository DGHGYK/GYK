package com.inwhiter.inviteapp.project.ActivityD;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.inwhiter.inviteapp.project.R;

/**
 * Created by ankara_user_1 on 9/18/2017.
 */

public class PrintActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner papertype; //spnr_paper_type
    Spinner papersize;// spnr_paper_size
    Spinner cardunitprice; //spnr_card_unit_price
    Spinner envelopecolor; //spnr_envelope_color
    Spinner envelopeprice; //spnr_envelope_price
    Spinner zarfastarfiyati; //spnr_zarf_astar_price
    EditText recipient; //txt_recipient
    EditText adress; //txt_adress
    EditText ordernote; //txt_order_note
    TextView textcargo; //txt_cargo
    Button giveorder; //btn_order


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d_activity_print);


        papertype = (Spinner) findViewById(R.id.spnr_paper_type);
        papersize = (Spinner) findViewById(R.id.spnr_paper_size);
        cardunitprice = (Spinner) findViewById(R.id.spnr_card_unit_price);
        envelopecolor = (Spinner) findViewById(R.id.spnr_envelope_color);
        envelopeprice = (Spinner) findViewById(R.id.spnr_envelope_price);
        zarfastarfiyati = (Spinner) findViewById(R.id.spnr_zarf_astar_price);
        recipient = (EditText) findViewById(R.id.txt_recipient);
        adress = (EditText) findViewById(R.id.txt_adress);
        ordernote = (EditText) findViewById(R.id.txt_order_note);
        textcargo = (TextView) findViewById(R.id.txt_cargo);
        giveorder = (Button) findViewById(R.id.btn_order);

        giveorder.setText("SEPETE EKLE");

        textcargo.setText("Davetiyeniz ERTESİ GÜN KARGOYA TESLİM!");



        giveorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                //giveorder.setText("Davetiyeniz Sepete Eklendi!");
                // if(giveorder.get()==null || recipient.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(),"Davetiyenizin Siparisi alinmistir.", Toast.LENGTH_LONG).show();
             //  Toast.makeText(PrintActivity.this, "Davetiyenizin Siparisi alinmistir.", Toast.LENGTH_SHORT).show();
              //  System.out.println("Button Clicked");

               /* public void toastMsg() {

                    Toast toast = Toast.makeText(this, "ff", Toast.LENGTH_LONG);
                    toast.show();

                }
                public void  displayToastMsg(View v) {

                    toastMsg("Hello how are you today!!");

                }*/
            }
        });



        /*giveorder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) { // Buton her tıklandığında bu fonksiyon çalışır

                //Yapmak istediğin işlemler
                //  Toast.makeText(getBaseContext(),"Davetiyenizin Siparisi alinmistir.", Toast.LENGTH_LONG).show();

                Toast.makeText(PrintActivity.this, "Davetiyenizin Siparisi alinmistir.", Toast.LENGTH_SHORT).show();
            }
        });*/


        ArrayAdapter adapter1= ArrayAdapter.createFromResource(this,R.array.spnr_paper_type_array, android.R.layout.simple_spinner_item);
        papertype.setAdapter(adapter1);
        papertype.setOnItemSelectedListener(this);

        ArrayAdapter adapter2= ArrayAdapter.createFromResource(this,R.array.spnr_paper_size_array, android.R.layout.simple_spinner_item);
        papersize.setAdapter(adapter2);
        papersize.setOnItemSelectedListener(this);

        ArrayAdapter adapter3= ArrayAdapter.createFromResource(this,R.array.spnr_card_unit_price_array, android.R.layout.simple_spinner_item);
        cardunitprice.setAdapter(adapter3);
        cardunitprice.setOnItemSelectedListener(this);

        ArrayAdapter adapter4= ArrayAdapter.createFromResource(this,R.array.spnr_envelope_color_array, android.R.layout.simple_spinner_item);
        envelopecolor.setAdapter(adapter4);
        envelopecolor.setOnItemSelectedListener(this);

        ArrayAdapter adapter5= ArrayAdapter.createFromResource(this,R.array.spnr_envelope_price_array, android.R.layout.simple_spinner_item);
        envelopeprice.setAdapter(adapter5);
        envelopeprice.setOnItemSelectedListener(this);

        ArrayAdapter adapter6= ArrayAdapter.createFromResource(this,R.array.spnr_zarf_astar_price_array, android.R.layout.simple_spinner_item);
        zarfastarfiyati.setAdapter(adapter6);
        zarfastarfiyati.setOnItemSelectedListener(this);



        giveorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recipient.getText()==null || recipient.getText().toString().equals("")){
                    Toast.makeText(getBaseContext(),"Davetiyenin kişiye özel gönderilmesi için davetli adını girmek zorunludur.", Toast.LENGTH_LONG).show();
                }else{
                    if(adress.getText()==null || adress.getText().toString().equals("")){
                        Toast.makeText(getBaseContext(),"Davetiyenin kişiye özel gönderilmesi için davetlinin adresini girmek zorunludur.", Toast.LENGTH_LONG).show();

                           /*(InviteeListSingleton.getInst().isSameNumber( phoneNumber.getText().toString()))
                   {
                       InputMethodManager inputManager = (InputMethodManager)
                               getSystemService(Context.INPUT_METHOD_SERVICE);

                       inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                               InputMethodManager.HIDE_NOT_ALWAYS);
                       Toast.makeText(getBaseContext(),"Bu numara zaten davetli listenizde", Toast.LENGTH_LONG).show();
                   }*/

                  /* else{
                           //davetli kişi verisi veritabanına kaydedilir
                       FirebaseDatabase database = FirebaseDatabase.getInstance();
                       DatabaseReference inviteeRef = database.getReference("invitee");

                       String inviteeId = inviteeRef.push().getKey();
                       Invitee in = new Invitee(inviteeId, TemplateActivity.inviteId, 0, name.getText().toString(), phoneNumber.getText().toString(), new InviteeStatus());
                       //Singleton listeye yeni kişi eklenir
                       InviteeListSingleton.getInst().getInviteeList().add(in);

                       inviteeRef.child(inviteeId).setValue(in);

                       Intent intent = new Intent(AddManuallyActivity.this, InviteeActivity.class);
                       startActivity(intent);
                       }*/

                    }
                }
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView mytext= (TextView) view;
       // Toast.makeText(this, mytext.getText() + " " + "Secildi", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, mytext.getText() + " ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}




