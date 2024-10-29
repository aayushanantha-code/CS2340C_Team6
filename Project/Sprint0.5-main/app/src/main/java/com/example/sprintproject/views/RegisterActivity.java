package com.example.sprintproject.views;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sprintproject.R;
import com.example.sprintproject.viewmodels.CreateAccountViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    private DatabaseReference userDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        //Clicking Login Button (Goes back to Login Screen)
        findViewById(R.id.register_login_button).setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        Button btnCreateAccount = findViewById(R.id.register_register_button);
        EditText passwordInput = findViewById(R.id.register_password_edit);
        EditText usernameInput = findViewById(R.id.register_username_edit);
        TextView usernameTaken = findViewById(R.id.username_taken);

        //Clicking Create Account Button
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String password = passwordInput.getText().toString().trim();
                String username = usernameInput.getText().toString().trim();
                if (!password.isEmpty() && !username.isEmpty()) {
                    userDatabase = FirebaseDatabase.getInstance().getReference("users");
                    userDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            boolean usernameExists = false;

                            for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                String existingUsername = userSnapshot.child("userID").
                                        getValue(String.class);
                                if (existingUsername != null && existingUsername.equals(username)) {
                                    usernameExists = true;
                                    break;
                                }
                            }

                            if (usernameExists) {
                                //Notify username is taken
                                usernameTaken.setVisibility(View.VISIBLE);
                            } else {
                                //Continue to make a new Account
                                CreateAccountViewModel account =
                                        new CreateAccountViewModel(username, password);
                                Intent createAccountIntent =
                                        new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(createAccountIntent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            //error
                        }
                    });
                }
            }
        });
    }
}
