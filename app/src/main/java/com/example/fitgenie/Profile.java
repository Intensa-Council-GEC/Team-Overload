package com.example.fitgenie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class Profile extends AppCompatActivity {
    ConstraintLayout gender_container;
    CheckBox male, female;
    JSONObject jsonObject = new JSONObject();
    TextView heading;
    Button next;
    String name, email;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);

        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");

        gender_container = findViewById(R.id.gender_container);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        heading = findViewById(R.id.heading);
        next = findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, Profile_2.class);
                intent.putExtra("name", name);
                intent.putExtra("email", email);
                if(male.isChecked())
                {
                    intent.putExtra("gender", "male");
                }
                else if(female.isChecked())
                {
                    intent.putExtra("gender", "female");
                }
                startActivity(intent);
            }
        });


    }
}