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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class ForgotPassword extends AppCompatActivity {
    RelativeLayout recoverpassword;
    TextView login_resetpassword;
    EditText resetid, resetpassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        recoverpassword = findViewById(R.id.recoverpassword);
        login_resetpassword = findViewById(R.id.login_resetpassword);
        resetid = findViewById(R.id.resetid);
        resetpassword = findViewById(R.id.resetpassword);

        firebaseAuth = FirebaseAuth.getInstance();

        recoverpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = resetid.getText().toString().trim();
                String password = resetpassword.getText().toString().trim();
                if(mail.isEmpty())
                    Toast.makeText(ForgotPassword.this, "Enter your email id!", Toast.LENGTH_SHORT).show();
                else
                {
                    //send mail to recover password
                    firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(ForgotPassword.this, "Password recovery mail sent!", Toast.LENGTH_SHORT).show();
                                finish();
                                Intent intent = new Intent(ForgotPassword.this, MainActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(ForgotPassword.this, "User account doesnt exist!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        login_resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotPassword.this, LogIn.class);
                startActivity(intent);
            }
        });
    }
}