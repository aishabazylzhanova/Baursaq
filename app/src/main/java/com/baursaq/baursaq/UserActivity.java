package com.baursaq.baursaq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserActivity extends AppCompatActivity {
    private TextView user;
    private Button add;
    private Button all;
    private Button my;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        user = findViewById(R.id.user);
        Intent intent = getIntent();
        String name = intent.getStringExtra("Username");
        user.setText(name);
        add=findViewById(R.id.add);
        all=findViewById(R.id.all);
        my=findViewById(R.id.my);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, RecipesActivity.class);
                startActivity(intent);
            }
        });

    }
}