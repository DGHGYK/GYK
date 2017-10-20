package com.inwhiter.inviteapp.project.ActivityG;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.inwhiter.inviteapp.project.ActivityH.MenuActivity;
import com.inwhiter.inviteapp.project.R;

public class SignUpActivity extends AppCompatActivity {
    EditText email, password, password2;
    Button signup;
    TextView login;

    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);

       /* ActionBar ab=getSupportActionBar();
        ab.hide();*/


        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        email=(EditText)findViewById(R.id.et_signup_email);
        password =(EditText)findViewById(R.id.et_signup_password);
        password2=(EditText)findViewById(R.id.et_signup_password2);
        signup = (Button)findViewById(R.id.bt_signup_signup);
        login = (TextView) findViewById(R.id.tv_signup_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.getText().toString().equals(password2.getText().toString())) {
                    mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information

                                        currentUser = mAuth.getCurrentUser();
                                        Log.d("Fire", currentUser.getEmail());
                                        //Notification gönderilebilmesi için kullanıcı telefonun tokenı firebasee kaydedilir.
                                      
                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        DatabaseReference userRef = database.getReference("user");
                                        userRef.child(currentUser.getUid()).child("token").setValue(FirebaseInstanceId.getInstance().getToken());
                                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                                        startActivity(intent);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("Fire", "createUserWithEmail:failure");

                                    }

                                }
                            });
                }else{
                    Toast.makeText(getBaseContext(),"Girdiğiniz şifreler aynı olmalıdır.", Toast.LENGTH_LONG).show();
                }
                String s = password.getText().toString();

            /*    if(s.length() < 6){
                    password.setError("Sifreyi en az 6 karakter giriniz!");
                    password2.setError("Sifreyi en az 6 karakter giriniz!");
                } else {
                    // ...
                    Toast.makeText(getBaseContext(),"Kaydiniz Gerceklestirilmistir.", Toast.LENGTH_LONG).show();
                }*/
            }
        });

    }

}
