package com.example.testing;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameTesting extends AppCompatActivity {


    // TODO: add life system
    private List<ImageView> mEggs = new ArrayList<>();
    private ImageView mGround;
    private ImageView mBasket;
    private long mDuration = 1800L;
    private Random random;
    private RelativeLayout eggsContainer;
    private int eggIndex = 0;
    private Handler mHandler;
    private Runnable mSpawnEggRunnable;
    private int life =  2;
    private int score =  0;
    private  int difficulty = 0;
    private int difficultyLimit = 100 ;
    private  int fallSpeeder = 0;
    private  int spawnSpeeder =  0;


    private String  eggType;
    TextView scoreDraw;
    RelativeLayout lifeContainer;
    boolean isClick= false;
    VideoView videoView;
    int[] basketLocation = new int[2];
    int[] eggLocation = new int[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        resetStat();

        setContentView(R.layout.activity_game_testing);
        scoreDraw = (TextView)findViewById(R.id.score);
        lifeContainer = (RelativeLayout)findViewById(R.id.lifeContainer);
        mGround = findViewById(R.id.ground);
        eggsContainer = findViewById(R.id.eggsContainer);
        mBasket = findViewById(R.id.basket);

        random = new Random();
        mHandler = new Handler();
        mSpawnEggRunnable = new Runnable() {
            @Override
            public void run() {
                spawnEggInternal();
            }
        };
        mHandler.postDelayed(mSpawnEggRunnable, 2000);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mSpawnEggRunnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mSpawnEggRunnable);
    }

    private void spawnEggInternal() {
        isClick = false;
        System.out.println(fallSpeeder);
        System.out.println(difficulty);
        if (score >= difficultyLimit  &&  difficultyLimit < 1100) {
            difficulty =  difficulty  +  7;
            difficultyLimit = difficultyLimit + 200;
            fallSpeeder  = fallSpeeder + 160;
            spawnSpeeder = spawnSpeeder + 80;
        }

        ImageView egg = new ImageView(this);

        int eggChance =  random.nextInt(99);

        if (eggChance< 2) {
            egg.setImageResource(R.drawable.egg3);
            eggType  =  "gold";
        } else if (eggChance < 35 + difficulty) {
            egg.setImageResource(R.drawable.egg2);
            eggType  =  "rotten";
        } else {
            egg.setImageResource(R.drawable.egg);
            eggType  =  "normal";
        }
        egg.setScaleX(3f);
        egg.setScaleY(3f);
        egg.setBackgroundColor(Color.TRANSPARENT);
        egg.setVisibility(View.VISIBLE);
        egg.setTag(eggIndex);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                100,
                100);
//        float startX = random.nextInt(getWindowManager().getDefaultDisplay().getWidth() - egg.getWidth());
        float startX = random.nextInt((mBasket.getWidth()-150) - egg.getWidth());
        float startY = 100;
        params.leftMargin = (int) startX;
        params.topMargin = (int) startY;
        egg.setLayoutParams(params);
        egg.setY(startY);
        mEggs.add(egg);

        eggsContainer.addView(egg, params);

        egg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag = (Integer) v.getTag();
                mEggs.remove(tag);
                eggsContainer.removeView(egg);

            }
        });
        animateFall(egg);
    }

    private void animateFall(final ImageView egg) {
        mBasket.getLocationOnScreen(basketLocation);
//        float newY = mGround.getY() + mGround.getHeight() - egg.getHeight() / 2 + mGround.getTop() + 1000;
        float newY = basketLocation[1];
        ViewPropertyAnimator animator = egg.animate()
                .translationY(newY)
                .setInterpolator(new AccelerateInterpolator())
                .setDuration(mDuration - fallSpeeder)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        egg.post(new Runnable() {
                            @Override
                            public void run() {
                                egg.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        mEggs.remove(egg);
                                        eggsContainer.removeView(egg);
                                        isClick = true;

                                        if (eggType.equals("normal") || eggType.equals("gold")) {
                                            lifeContainer.removeView(lifeContainer.getChildAt(life-1));
                                            life --;
                                        }
                                    }
                                });
                            }
                        });
                    }

                    @Override
                    public void onAnimationEnd(@NonNull Animator animator) {
                         egg.getLocationOnScreen(eggLocation);
                        if ( eggLocation[1] >= basketLocation[1]) {
                            mEggs.remove(egg);
                            eggsContainer.removeView(egg);

                            if (!isClick) {
                                switch (eggType) {
                                    case "gold":
                                        score = score + 50;
                                        break;

                                    case "normal":
                                        score = score + 10;
                                        break;

                                    case "rotten":
                                    lifeContainer.removeView(lifeContainer.getChildAt(life-1));
                                    life --;
                                }
                            }
                            scoreDraw.setText("Score:" + score);
                        }

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (life <= 0) {
                                    callGameOverPage();
                                } else  {
                                        spawnEggInternal();

                                }

                            }
                        }, 500 - spawnSpeeder);
                    }

                    @Override
                    public void onAnimationCancel(@NonNull Animator animator) {
                    }

                    @Override
                    public void onAnimationRepeat(@NonNull Animator animator) {
                    }
                });
        animator.start();
    }

    private void callGameOverPage () {

        Intent gameOverIntent = new Intent(GameTesting.this, GameOver.class);
        gameOverIntent.putExtra("score_pass", score);
        startActivity(gameOverIntent);

    }

    private void resetStat() {
        life =  2;
        score =  0;
        difficulty = 0;
        difficultyLimit = 100 ;
        fallSpeeder = 0;
        spawnSpeeder = 0;
        eggType =  null;
    }
}