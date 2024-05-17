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

public class GameModeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modes);

        TextView easyView = findViewById(R.id.easy);

        easyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseGameMode("Easy");
            }
        });

        TextView mediumView = findViewById(R.id.medium);

        mediumView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseGameMode("Medium");
            }
        });

        TextView hardView = findViewById(R.id.hard);

        hardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseGameMode("Hard");
            }
        });

        ImageButton backButton = findViewById(R.id.cross);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickExitButton();
            }
        });


    }

    private void chooseGameMode(String gameMode)  {

        System.out.println("chooseGameMode: " + gameMode);
        Intent intent = new Intent(GameModeActivity.this, MainActivity.class);
        intent.putExtra("gameMode", gameMode);
        intent.putExtra("name", getIntent().getStringExtra("name"));
        startActivity(intent);
    }

    private void clickExitButton()  {
        Intent intent = new Intent(GameModeActivity.this, MainActivity.class);
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
