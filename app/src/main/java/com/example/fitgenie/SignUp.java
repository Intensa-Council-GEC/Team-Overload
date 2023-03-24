package com.example.fitgenie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitgenie.Database.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    RelativeLayout signup;
    TextView login_insignup;
    EditText signupid, signuppassword, signupname;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signup = findViewById(R.id.signup);
        login_insignup = findViewById(R.id.login_insignup);
        signupname = findViewById(R.id.signupname);
        signupid = findViewById(R.id.signupid);
        signuppassword = findViewById(R.id.signuppassword);

        firebaseAuth = FirebaseAuth.getInstance();

        User user = new User();

        FirebaseDatabase firebaseDb = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = firebaseDb.getReference().child("Users");

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = signupname.getText().toString().trim();
                String mail = signupid.getText().toString().trim();
                String password = signuppassword.getText().toString().trim();

                if(mail.isEmpty()||password.isEmpty()||name.isEmpty())
                {
                    Toast.makeText(SignUp.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                }
                else if(password.length()<7)
                {
                    Toast.makeText(SignUp.this, "Password must be greater than 7 characters!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //register user to firebase
                    firebaseAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(SignUp.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                                user.setName(name);
                                user.setEmail(mail);
                                user.setSetprofile(false);
                                databaseRef.push().setValue(user);
                                sendEmailVerification();
                            }
                            else
                            {
                                Toast.makeText(SignUp.this, "Failed to Register", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        login_insignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, LogIn.class);
                startActivity(intent);
            }
        });
    }
    private void sendEmailVerification()
    {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(SignUp.this, "Verification email sent! Verify and login again.", Toast.LENGTH_SHORT).show();
                    firebaseAuth.signOut();
                    finish();
                    Intent intent = new Intent(SignUp.this, LogIn.class);
                    startActivity(intent);
                }
            });
        }
        else
        {
            Toast.makeText(this, "Failed to send verification email!", Toast.LENGTH_SHORT).show();
        }
    }
}