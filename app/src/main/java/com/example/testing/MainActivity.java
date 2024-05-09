package com.example.testing;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    ImageButton btnTest;
    private boolean isResume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton imageButton = findViewById(R.id.start);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GameTesting.class);
                startActivity(intent);
            }
        });

        btnTest = findViewById(R.id.start);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameTesting.class);
                startActivity(intent);

                isResume = true;
                btnTest.setImageResource(R.drawable.down);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isResume = false;
                        btnTest.setImageResource(R.drawable.up); // Change to "up" drawable
                    }
                }, 2000); // 2000 milliseconds = 2 seconds (adjust as needed)
            }
        });

    }

    private void startGameTestingActivity() {
        Intent intent = new Intent(MainActivity.this, GameTesting.class);
        startActivity(intent);
    }
}