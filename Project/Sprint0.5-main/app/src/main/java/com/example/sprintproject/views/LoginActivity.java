package com.example.sprintproject.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sprintproject.R;
import com.example.sprintproject.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.SharedPreferences;
import android.content.Context;

public class LoginActivity extends AppCompatActivity {

    private DatabaseReference userDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.register_button).setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        Button loginButton = findViewById(R.id.login_button);
        EditText passwordInput = findViewById(R.id.password_edit);
        EditText usernameInput = findViewById(R.id.username_edit);
        TextView incorrectText = findViewById(R.id.incorrect_input);

        loginButton.setOnClickListener(new View.OnClickListener() {
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
                                String userId = dataSnapshot.getKey();

                                SharedPreferences sharedPreferences = getSharedPreferences("MyApp", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("userId", userId);
                                editor.apply();

//                                Intent loginIntent = new Intent(LoginActivity.this, LogisticsActivity.class);
//                                loginIntent.putExtra("username", username); // Pass the username
//                                Intent numberIntent = new Intent(LoginActivity.this, LogisticsActivity.class);
//                                numberIntent.putExtra("number", "0"); // Pass the username
//                                Intent loginNavigation = new Intent(LoginActivity.this, BottomNavigationActivity.class);
//                                loginNavigation.putExtra("username", username); // Pass the username
//                                startActivity(loginIntent);
                                Intent loginIntent = new Intent(LoginActivity.this, LogisticsActivity.class);
                                loginIntent.putExtra("username", username); // Pass the username
                                loginIntent.putExtra("number", "0"); // Pass the "number" data
                                startActivity(loginIntent);

                            } else {
                                incorrectText.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Failure
                        }
                    });
                }
            }
        });
    }
}