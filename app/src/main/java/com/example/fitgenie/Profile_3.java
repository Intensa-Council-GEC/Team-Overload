package com.example.fitgenie;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class Profile_3 extends AppCompatActivity {
    String gender;
    String goal, name, email;
    CheckBox weighted, bodyweight, none;
    Button next;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile4);

        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        gender = getIntent().getStringExtra("gender");
        goal = getIntent().getStringExtra("goal");

        weighted = findViewById(R.id.weighted);
        bodyweight = findViewById(R.id.bodyweight);
        none = findViewById(R.id.none);
        next = findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile_3.this, Profile_4.class);
                intent.putExtra("name", name);
                intent.putExtra("email", email);
                intent.putExtra("gender", gender);
                intent.putExtra("goal", goal);
                if(weighted.isChecked())
                {
                    intent.putExtra("type", "weighted");
                }
                else if(none.isChecked())
                {
                    intent.putExtra("type", "none");
                }
                else
                {
                    intent.putExtra("type", "bodyweight");
                }
                startActivity(intent);
            }
        });
    }
}