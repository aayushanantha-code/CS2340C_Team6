package com.example.sprintproject.views;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.sprintproject.R;
import com.example.sprintproject.viewmodels.CreateAccountViewModel;

public class RegisterActivity extends AppCompatActivity {

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

        //Clicking Create Account Button
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String password = passwordInput.getText().toString().trim();
                String username = usernameInput.getText().toString().trim();
                if (!password.isEmpty() && !username.isEmpty()) {
                    CreateAccountViewModel viewModel = new CreateAccountViewModel(username, password);
                    Intent createAccountIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(createAccountIntent);
                } else {
                    //error
                }
            }
        });
    }
}
