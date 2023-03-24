package com.example.fitgenie;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class Profile_2 extends AppCompatActivity {
    String gender, name, email;
    CheckBox loosefat, tone, muscle;
    Button next;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile3);

        gender = getIntent().getStringExtra("gender");
        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");

        loosefat = findViewById(R.id.fatloss);
        tone = findViewById(R.id.tone);
        muscle = findViewById(R.id.muscle);
        next = findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile_2.this, Profile_3.class);
                intent.putExtra("name", name);
                intent.putExtra("email", email);
                intent.putExtra("gender", gender);
                if(loosefat.isChecked())
                {
                    intent.putExtra("goal", "fat loss");
                }
                else if(tone.isChecked())
                {
                    intent.putExtra("goal", "tone");
                }
                else
                {
                    intent.putExtra("goal", "muscle");
                }
                startActivity(intent);
            }
        });
    }
}