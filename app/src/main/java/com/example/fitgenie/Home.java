package com.example.fitgenie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitgenie.Fragments.AssistantFragment;
import com.example.fitgenie.Fragments.NutrientChoiceFragment;
import com.example.fitgenie.Fragments.NutritionFragment;
import com.example.fitgenie.Fragments.SocialFragment;
import com.example.fitgenie.Fragments.StatsFragment;
import com.example.fitgenie.Fragments.WorkoutFragment;
import com.example.fitgenie.databinding.ActivityHomeBinding;
import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {
    public ActivityHomeBinding binding;
    public String name, email, gender, goal, wtype, level;
    TextView textView;
    public Toolbar toolbar;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        textView = findViewById(R.id.textView);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        textView.setText("Hello "+name);


        replacefragment(new WorkoutFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch(item.getItemId()){
                case R.id.workout:
                    replacefragment(new WorkoutFragment());
                    break;
                case R.id.nutrition:
                    replacefragment(new NutrientChoiceFragment());
                    break;
                case R.id.assistant:
                    replacefragment(new SocialFragment());
                    break;
                case R.id.stats:
                    replacefragment(new StatsFragment());
                    break;
            }
            return true;
        });
    }
    public void replacefragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.logout)
        {
            firebaseAuth = FirebaseAuth.getInstance();

            Intent intent = new Intent(Home.this, MainActivity.class);
            startActivity(intent);

            firebaseAuth.signOut();
        }
        else
        {
//            Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Check out this cool Application");
            intent.putExtra(Intent.EXTRA_TEXT, "Your application link here");
            startActivity(Intent.createChooser(intent, "Share via"));
        }
        return true;
    }
    public void init()
    {
        gender = getIntent().getStringExtra("gender");
        goal = getIntent().getStringExtra("goal");
        wtype = getIntent().getStringExtra("type");
        level = getIntent().getStringExtra("level");
    }

}