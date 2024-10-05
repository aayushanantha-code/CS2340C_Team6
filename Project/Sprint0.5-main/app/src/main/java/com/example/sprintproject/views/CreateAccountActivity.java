package com.example.sprintproject.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.sprintproject.R;
import com.example.sprintproject.model.User;
import com.example.sprintproject.viewmodels.CreateAccountViewModel;
import com.example.sprintproject.viewmodels.LoginViewModel;

public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        Button btnCreateAccount = findViewById(R.id.createAccount_button);
        EditText passwordInput = findViewById(R.id.create_password_edit);
        EditText usernameInput = findViewById(R.id.create_username_edit);

        //Clicking Create Account Button
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String password = passwordInput.getText().toString().trim();
                String username = usernameInput.getText().toString().trim();
                if (!password.isEmpty() && !username.isEmpty()) {
                    CreateAccountViewModel viewModel = new CreateAccountViewModel(username, password);
                    Intent createAccountIntent = new Intent(CreateAccountActivity.this, MainActivity.class);
                    startActivity(createAccountIntent);
                } else {
                    //error
                }
            }
        });
    }
}
