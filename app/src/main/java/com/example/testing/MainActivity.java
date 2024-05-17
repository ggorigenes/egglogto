package com.example.testing;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testing.model.util.LeaderboardService;

import java.io.Serializable;


public class MainActivity extends AppCompatActivity {

    ImageButton startButton;
    ImageButton modeButton;
    ImageButton LeaderboardButton;
    ImageButton startUp;
    ImageButton startDown;
    private boolean isResume;
    TextView currentGameMode;
    EditText nameEditText;

    String name = "Guest";
    String gameMode  =  "Easy";
    MediaPlayer mp;

    ImageView volumeUp;
    ImageView volumeDown;
    boolean isMusicInGame = true;

    LeaderboardService leaderboardService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mp =MediaPlayer.create(MainActivity.this, R.raw.start);
        if (getIntent().getStringExtra("gameMode") != null)   {
            gameMode = getIntent().getStringExtra("gameMode");
            isMusicInGame = getIntent().getBooleanExtra("musicGame",true);
            name = getIntent().getStringExtra("name");

        }

        leaderboardService.createLeaderboardFile(getApplicationContext());

        if (isMusicInGame) mp.start();

        currentGameMode = (TextView) findViewById(R.id.difficultyDisplay);

        currentGameMode.setText("Difficulty: " + gameMode);

        startButton = findViewById(R.id.startUp);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startGameTestingActivity();
            }
        });

        modeButton = findViewById(R.id.modesUp);

        modeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGameModeActivity();
            }
        });

        LeaderboardButton  = findViewById(R.id.leaderboard_button);
        LeaderboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startLeaderboardActivity();
            }
        });

        volumeUp = findViewById(R.id.on);
        volumeUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.stop();
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
                mp.setLooping(true);
                isMusicInGame = true;
                volumeUp.setVisibility(View.VISIBLE);
                volumeDown.setVisibility(View.GONE);

            }
        });

        nameEditText  = (EditText) findViewById(R.id.text);

        nameEditText.setText(name);
        nameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                    name = nameEditText.getText().toString();
                    nameEditText.setText(name);
                }
            }
        });

        name = nameEditText.getText().toString();

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(MainActivity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void startGameTestingActivity() {
        Intent intent = new Intent(MainActivity.this, GameTesting.class);
        intent.putExtra("gameMode",gameMode);
        intent.putExtra("musicGame",isMusicInGame);
        intent.putExtra("name",nameEditText.getText().toString());
        mp.stop();

        isResume = true;
        startButton.setImageResource(R.drawable.start_down);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isResume = false;
                startButton.setImageResource(R.drawable.start_up);
            // Change to "up" drawable
            }
            // 2000 milliseconds = 2 seconds (adjust as needed)
        }, 2000);

        startActivity(intent);
    }

    private void startGameModeActivity() {
        Intent intent = new Intent(MainActivity.this, GameModeActivity.class);
        intent.putExtra("name",nameEditText.getText().toString());
        isResume = true;
        modeButton.setImageResource(R.drawable.modes_down);
        mp.stop();
        startActivity(intent);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isResume = false;
                modeButton.setImageResource(R.drawable.modes_up);

                // Change to "up" drawable
            }
            // 2000 milliseconds = 2 seconds (adjust as needed)
        }, 2000);

    }

   private void startLeaderboardActivity() {
        Intent intent = new Intent(MainActivity.this, LeaderboardActivity.class);
        intent.putExtra("gameMode", gameMode);
       intent.putExtra("name",nameEditText.getText().toString());
        isResume = true;
       LeaderboardButton.setImageResource(R.drawable.leaderboard_down);
        mp.stop();
        startActivity(intent);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isResume = false;
                LeaderboardButton.setImageResource(R.drawable.leaderboard_up);
                // Change to "up" drawable
            }
            // 2000 milliseconds = 2 seconds (adjust as needed)
        }, 2000);

    }

    @Override
    protected void onResume() {
        mp.start();

        super.onResume();
    }

    @Override
    protected void onPause() {
        mp.pause();
        super.onPause();
    }




}