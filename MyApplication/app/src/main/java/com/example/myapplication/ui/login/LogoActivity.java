package com.example.myapplication.ui.login;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;

/**
 * This activity represents a logo screen that is displayed for a short duration
 * before navigating to the login screen.
 * It uses a Handler to delay the transition to the {@link LoginActivity}.
 *
 * @author Haoxuan Xu
 */

public class LogoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }, 2000);
    }
}
