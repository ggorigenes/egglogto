package com.example.testing;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameTesting extends AppCompatActivity {


    // TODO: add life system
    private List<ImageView> mEggs = new ArrayList<>();
    private ImageView mGround;
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
    private  int speeder = 0;
    private String  eggType;
    TextView scoreDraw;
    RelativeLayout lifeContainer;
    boolean isClick= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        resetStat();

        setContentView(R.layout.activity_game_testing);
        scoreDraw = (TextView)findViewById(R.id.score);
        lifeContainer = (RelativeLayout)findViewById(R.id.lifeContainer);
        mGround = findViewById(R.id.ground);
        eggsContainer = findViewById(R.id.eggsContainer);

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

        if (score >= difficultyLimit  &&  difficultyLimit < 1100) {
            difficulty =  difficulty  +  7;
            difficultyLimit = difficultyLimit + 200;
            speeder  = speeder + 160;
        }

        ImageView egg = new ImageView(this);

        int eggChance =  random.nextInt(99);

        if (eggChance< 5) {
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
        float startX = random.nextInt(getWindowManager().getDefaultDisplay().getWidth() - egg.getWidth());
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
        float newY = mGround.getY() + mGround.getHeight() - egg.getHeight() / 2 + mGround.getTop() + 1000;

        ViewPropertyAnimator animator = egg.animate()
                .translationY(newY)
                .setInterpolator(new AccelerateInterpolator())
                .setDuration(mDuration - speeder)
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
                        if (egg.getY() > mGround.getY() + mGround.getHeight() - egg.getHeight() / 2) {
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
                            } else {
                                isClick = false;
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
                        }, 500);
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
        gameOverIntent.putExtra("score_pass", String.valueOf(score));
        startActivity(gameOverIntent);

    }

    private void resetStat() {
        life =  2;
        score =  0;
        difficulty = 0;
        difficultyLimit = 100 ;
        speeder = 0;
        eggType =  null;
    }
}