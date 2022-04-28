package com.baursaq.baursaq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private Button login;
    private TextView signin;
    private FirebaseAuth rAuth;
    private TextView username;
    private EditText password2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.reg_email);
        password = findViewById(R.id.reg_password);
        login = findViewById(R.id.reg_login);
        signin = findViewById(R.id.Sign_In);
        rAuth = FirebaseAuth.getInstance();
        username = findViewById(R.id.username);
        password2=findViewById(R.id.reg_password2);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Email can't be empty", Toast.LENGTH_SHORT).show();
                }
                else if (username.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Email can't be empty", Toast.LENGTH_SHORT).show();
                }
                else if (password.getText().toString().isEmpty() ){
                    Toast.makeText(MainActivity.this, "Password can't be empty", Toast.LENGTH_SHORT).show();
                }
                else if (!password.getText().toString().equals(password2.getText().toString())){
                    Toast.makeText(MainActivity.this, "Passwords doesn't match", Toast.LENGTH_SHORT).show();
                }
                else{
                    rAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){

                                        Intent intent = new Intent(MainActivity.this, UserActivity.class);
                                        intent.putExtra("Username", username.getText().toString());

                                        startActivity(intent);

                                    }
                                    else
                                    {
                                        Toast.makeText(MainActivity.this, "Incorrect email or password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                }
            }
        });



    }

    }

