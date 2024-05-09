//package com.example.testing;
//
//import android.content.Intent;
//import android.graphics.drawable.AnimationDrawable;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Looper;
//import android.view.View;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class EgglogGameplay extends AppCompatActivity {
//
//    ImageButton btnTest;
//    private boolean isResume;
//
//    //sadsdasdsad
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // Set up click listener for starting new activity
//        ImageButton imageButton = findViewById(R.id.start);
//        imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(EgglogGameplay.this, EgglogGameplay.class);
//                startActivity(intent);
//            }
//        });
//
//        // Set up click listener for button animation
//        btnTest = findViewById(R.id.start);
//        btnTest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Start the new activity
//                Intent intent = new Intent(EgglogGameplay.this, EgglogGameplay.class);
//                startActivity(intent);
//
//                // Change the button state to "down"
//                isResume = true;
//                btnTest.setImageResource(R.drawable.down);
//
//                // Post a delayed action to change the button state back to "up" after a delay
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        isResume = false;
//                        btnTest.setImageResource(R.drawable.up); // Change to "up" drawable
//                    }
//                }, 2000); // 2000 milliseconds = 2 seconds (adjust as needed)
//            }
//        });
//    }
//}