package com.example.sprintproject.views;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.example.sprintproject.R;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_DURATION = 3000; // 3000 milliseconds = 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Find the ImageView
        ImageView logoImageView = findViewById(R.id.logo2);

        // Load the rotation animation
        Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_animation);

        // Start the rotation animation on the logo
        logoImageView.startAnimation(rotateAnimation);

        // Handler to transition to MainActivity after SPLASH_DURATION
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start MainActivity
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                // Finish SplashActivity so user can't go back to it
                finish();
            }
        }, SPLASH_DURATION);
    }
}