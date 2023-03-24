package com.example.fitgenie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.fitgenie.Database.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Profile_4 extends AppCompatActivity {
    String gender;
    String goal, type, name, email;
    CheckBox beginner, amateur, expert;
    Button next;
    static List<User> userList;
    ArrayList<Object> key = new ArrayList<Object>();
    long value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile5);

        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        gender = getIntent().getStringExtra("gender");
        goal = getIntent().getStringExtra("goal");
        type = getIntent().getStringExtra("type");

        beginner = findViewById(R.id.beginner);
        amateur = findViewById(R.id.amateur);
        expert = findViewById(R.id.expert);
        next = findViewById(R.id.next);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Users");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                value = (int) snapshot.getChildrenCount();
                userList = new ArrayList<User>();
                for (DataSnapshot child: snapshot.getChildren()) {
                    userList.add(child.getValue(User.class));
                    key.add(child.getKey());
                }

                Log.d("value", "Created: "+ userList.get(0).getName());

                for(int i=0;i<value;i++)
                {
                    if(userList.get(i).getEmail().equals(email))
                    {
                        userList.get(i).setSetprofile(true);
                        userList.get(i).setEmail(email);
                        userList.get(i).setName(name);

                        databaseReference.child((String) key.get(i)).setValue(userList.get(i));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile_4.this, "Fail!", Toast.LENGTH_SHORT).show();
            }
        });




        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile_4.this, Home.class);
                intent.putExtra("name", name);
                intent.putExtra("email", email);
                intent.putExtra("gender", gender);
                intent.putExtra("goal", goal);
                intent.putExtra("type", type);
                if(beginner.isChecked())
                {
                    intent.putExtra("level", "beginner");
                }
                else if(amateur.isChecked())
                {
                    intent.putExtra("level", "amateur");
                }
                else
                {
                    intent.putExtra("level", "expert");
                }

                startActivity(intent);
            }
        });
    }
}