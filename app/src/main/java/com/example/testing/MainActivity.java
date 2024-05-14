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

import java.io.Serializable;


public class MainActivity extends AppCompatActivity {

    ImageButton startButton;
    ImageButton modeButton;
    ImageButton settingButton;
    private boolean isResume;
    TextView currentGameMode;


    String gameMode  =  "Easy";
    MediaPlayer mp;

    ImageView volumeUp;
    ImageView volumeDown;
    boolean isMusicInGame = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mp =MediaPlayer.create(MainActivity.this, R.raw.start);
        if (getIntent().getStringExtra("gameMode") != null)   {
            gameMode = getIntent().getStringExtra("gameMode");
            isMusicInGame = getIntent().getBooleanExtra("musicGame",true);

        }


        if (isMusicInGame) mp.start();

        currentGameMode = (TextView) findViewById(R.id.difficultyDisplay);

        currentGameMode.setText("Difficulty: " + gameMode);


        startButton = findViewById(R.id.start);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startGameTestingActivity();
            }
        });

        modeButton = findViewById(R.id.modes);

        modeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGameModeActivity();
            }
        });

        volumeUp = findViewById(R.id.on);
        volumeUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.pause();
                isMusicInGame = false;
                volumeDown.setVisibility(View.VISIBLE);
                volumeUp.setVisibility(View.GONE);

            }
        });

        volumeDown = findViewById(R.id.off);
        volumeDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
                isMusicInGame = true;
                volumeUp.setVisibility(View.VISIBLE);
                volumeDown.setVisibility(View.GONE);

            }
        });

    }

    private void startGameTestingActivity() {
        Intent intent = new Intent(MainActivity.this, GameTesting.class);
        intent.putExtra("gameMode",gameMode);
        intent.putExtra("musicGame",isMusicInGame);
        mp.pause();
        startActivity(intent);

        isResume = true;
        startButton.setImageResource(R.drawable.down);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isResume = false;
                startButton.setImageResource(R.drawable.up); // Change to "up" drawable
            }
            // 2000 milliseconds = 2 seconds (adjust as needed)
        }, 1000);
    }

    private void startGameModeActivity() {
        Intent intent = new Intent(MainActivity.this, GameModeActivity.class);
        startActivity(intent);
        mp.pause();
        isResume = true;
        modeButton.setImageResource(R.drawable.mdown);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isResume = false;
                modeButton.setImageResource(R.drawable.mup); // Change to "up" drawable
            }
            // 2000 milliseconds = 2 seconds (adjust as needed)
        }, 1000);
    }


//    private void startSettingsActivity() {
//        Intent intent = new Intent(MainActivity.this, SettingActivity.class);
//        startActivity(intent);
//
//
//        isResume = true;
//        settingButton.setImageResource(R.drawable.sdown);
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                isResume = false;
//                settingButton.setImageResource(R.drawable.sup); // Change to "up" drawable
//            }
//            // 2000 milliseconds = 2 seconds (adjust as needed)
//        }, 1000);
//    }



}