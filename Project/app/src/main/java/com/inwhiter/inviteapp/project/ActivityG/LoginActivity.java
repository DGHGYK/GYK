package com.inwhiter.inviteapp.project.ActivityG;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
import com.inwhiter.inviteapp.project.ActivityH.MenuActivity;
import com.inwhiter.inviteapp.project.R;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button login;
    TextView signup;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    //String email, sifre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText) findViewById(R.id.et_login_email);
        password = (EditText) findViewById(R.id.et_login_password);
        login = (Button) findViewById(R.id.bt_login_login);
        signup = (TextView) findViewById(R.id.tv_login_signup);


        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(),MenuActivity.class);
            startActivity(intent);
        }

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(email.getText().toString()) && !TextUtils.isEmpty(password.getText().toString())) {
                    mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("Fire", "signInWithEmail:success");
                                        currentUser = mAuth.getCurrentUser();
                                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(getBaseContext(), "Giriş başarısız oldu", Toast.LENGTH_LONG).show();
                                        Log.w("FA", "signInWithEmail:failure", task.getException());

                                    }

                                }
                            });

                } else {
                    Toast.makeText(getBaseContext(), "İstenen bilgileri eksiksiz giriniz.", Toast.LENGTH_LONG).show();
                }


                String s = password.getText().toString();

                if(s.length() < 6){
                    password.setError("Sifreyi en az 6 karakter giriniz!");
                } else {
                    // ...
                    Toast.makeText(getBaseContext(),"Girisiniz yapiliyor.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
