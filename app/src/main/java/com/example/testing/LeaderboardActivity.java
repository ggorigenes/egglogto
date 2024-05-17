package com.example.testing;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.testing.model.Player;
import com.example.testing.model.util.LeaderboardService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class LeaderboardActivity extends AppCompatActivity {
    TextView currentGameMode;
    String gameMode = "Easy";

    RelativeLayout playerNames;
    RelativeLayout playerScore;

    LeaderboardService leaderboardService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard);

        Intent intent = getIntent();
        gameMode = intent.getStringExtra("gameMode");
        playerNames = findViewById(R.id.player_name_container);
        playerScore =findViewById(R.id.player_score_container);

        currentGameMode = (TextView) findViewById(R.id.difficultyLeaderboard);

        currentGameMode.setText("Difficulty: " + gameMode);

        List<Player> players = null;
        try {
            players = leaderboardService.readHighScoreFile(getApplicationContext().getFilesDir()+"/leaderboard/"+gameMode+".csv");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        AtomicInteger topPlayerNameMargin = new AtomicInteger(60);
        players.forEach((n) ->  {
            addPlayerName(topPlayerNameMargin.get(),n.getName());
            addPlayerScore(topPlayerNameMargin.get(),String.valueOf(n.getScore()));
            topPlayerNameMargin.set(topPlayerNameMargin.get() + 70);
        });


    }

    private void addPlayerName(int y, String textInput)   {
        TextView text = new TextView(this);
        text.setBackgroundColor(Color.TRANSPARENT);
        text.setTextColor(Color.BLACK);
        text.setText(textInput);
        text.setTextSize(30);
        text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        Typeface typeface = ResourcesCompat.getFont(this, R.font.minecraft);
        text.setTypeface(typeface);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                400,
                150);

        params.leftMargin = 30;
        params.topMargin = y;
        text.setLayoutParams(params);
        text.setY(y);

        playerNames.addView(text);

    }

    private void addPlayerScore( int y, String textInput)   {
        TextView text = new TextView(this);

        text.setBackgroundColor(Color.TRANSPARENT);
        text.setTextColor(Color.BLACK);
        text.setText(textInput);
        text.setTextSize(30);
        text.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
        Typeface typeface = ResourcesCompat.getFont(this, R.font.minecraft);
        text.setTypeface(typeface);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                400,
                150);

        params.topMargin = y;
        text.setLayoutParams(params);
        text.setY(y);

        playerScore.addView(text);

    }


    private void clickExitButton()  {
        Intent intent = new Intent(LeaderboardActivity.this, MainActivity.class);
        intent.putExtra("name", getIntent().getStringExtra("name"));
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
