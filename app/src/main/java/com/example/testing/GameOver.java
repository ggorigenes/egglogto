package com.example.testing;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameOver extends AppCompatActivity {

    ImageButton btnTest;
    private boolean isResume;
    TextView finalScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);

        finalScore = (TextView)findViewById(R.id.scoreValue);

        Intent intent = getIntent();
        finalScore.setText(intent.getStringExtra("score_pass"));

        ImageButton imageButton = findViewById(R.id.restart);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameOver.this, GameTesting.class);
                startActivity(intent);
            }
        });

        ImageButton restartButton = findViewById(R.id.restart);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickRestart();

                Intent intent = new Intent(GameOver.this, GameTesting.class);
                startActivity(intent);

                isResume = true;
                restartButton.setImageResource(R.drawable.down2);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isResume = false;
                        restartButton.setImageResource(R.drawable.up2); // Change to "up" drawable
                    }
                }, 2000); // 2000 milliseconds = 2 seconds (adjust as needed)
            }
        });

        ImageButton quitButton = findViewById(R.id.exit);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickQuit();

                Intent intent = new Intent(GameOver.this, MainActivity.class);
                startActivity(intent);

                isResume = true;
                quitButton.setImageResource(R.drawable.down1);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isResume = false;
                        quitButton.setImageResource(R.drawable.up1); // Change to "up" drawable
                    }
                }, 2000); // 2000 milliseconds = 2 seconds (adjust as needed)
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    private void clickRestart() {
        Intent intent = new Intent(GameOver.this, GameTesting.class);
        startActivity(intent);
    }

    private void clickQuit() {
        Intent intent = new Intent(GameOver.this, MainActivity.class);
        startActivity(intent);
    }
}