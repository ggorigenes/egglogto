package com.example.testing;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameOver extends AppCompatActivity {


    TextView finalScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.game_over);

        finalScore = (TextView)findViewById(R.id.scoreValue);

        Intent intent = getIntent();
        finalScore.setText(intent.getStringExtra("score_pass"));


        ImageButton restartButton = findViewById(R.id.restart);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickRestart();
            }
        });

        ImageButton quitButton = findViewById(R.id.exit);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickQuit();
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