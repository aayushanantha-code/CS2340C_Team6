package com.example.sprintproject.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
<<<<<<< HEAD
import android.widget.Button;
=======
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
>>>>>>> origin/main

import com.example.sprintproject.R;
import com.example.sprintproject.model.User;
import com.example.sprintproject.viewmodels.LoginViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference userDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

<<<<<<< HEAD
        findViewById(R.id.welcome_enter).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);

        });

        findViewById(R.id.welcome_close).setOnClickListener(v -> {
            finish();
            System.exit(0);
=======
        Button createAccountButton = findViewById(R.id.createAccount_button);

        createAccountButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent createAccountIntent = new Intent(MainActivity.this, CreateAccountActivity.class);
                startActivity(createAccountIntent);
            }
        });

        Button loginButton = findViewById(R.id.login_button);
        EditText passwordInput = findViewById(R.id.password_edit);
        EditText usernameInput = findViewById(R.id.username_edit);

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String password = passwordInput.getText().toString().trim();
                String username = usernameInput.getText().toString().trim();

                //make sure fields aren't empty
                if (!password.isEmpty() && !username.isEmpty()) {

                    userDatabase = FirebaseDatabase.getInstance().getReference();
                    userDatabase.child("users").child(username).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Check if Username and Password match a User on Firebase
                            if (dataSnapshot.exists() && dataSnapshot.getValue(User.class).getPassword().equals(password)) {
                                Intent loginIntent = new Intent(MainActivity.this, LogisticsActivity.class);
                                startActivity(loginIntent);
                            } else {
                                // Add an error here (probably return some red text saying "Username/Password Incorrect"
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Failure
                        }
                    });
                }
            }
>>>>>>> origin/main
        });
    }
}