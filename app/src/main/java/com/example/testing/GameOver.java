package com.example.testing;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameOver extends AppCompatActivity {

    ImageButton btnTest;
    private boolean isResume;
    TextView finalScore;
    ImageView highScoreDrawable;
    ImageView scoreDrawable;
    int highScore;
    private boolean isMusicInGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
        Intent intent = getIntent();

        MediaPlayer mp = MediaPlayer.create(GameOver.this, R.raw.gameover);
        isMusicInGame = getIntent().getBooleanExtra("musicGame", true);
        if (isMusicInGame) mp.start();

        highScoreDrawable = (ImageView) findViewById(R.id.highscore);
        scoreDrawable = (ImageView) findViewById(R.id.score);

        highScore = getIntent().getIntExtra("highScore", 1500);
        if (intent.getIntExtra("score_pass",0)>= highScore)  {
            scoreDrawable.setVisibility(View.GONE);
        } else {
            highScoreDrawable.setVisibility(View.GONE);
        }

        finalScore = (TextView)findViewById(R.id.scoreValue);
        finalScore.setText(String.valueOf(intent.getIntExtra("score_pass",0)));

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
                intent.putExtra("gameMode",getIntent().getStringExtra("gameMode"));
                intent.putExtra("musicGame",isMusicInGame);
                startActivity(intent);

                mp.pause();

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
                intent.putExtra("musicGame",isMusicInGame);
                startActivity(intent);

                mp.pause();

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