package com.example.testing;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameTesting extends AppCompatActivity {

    private List<ImageView> mEggs = new ArrayList<>();
    private ImageView mGround;
    private long mDuration = 1800L;
    private Random random;
    private RelativeLayout eggsContainer;
    private int eggIndex = 0;
    private Handler mHandler;
    private Runnable mSpawnEggRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_testing);

        random = new Random();

        mGround = findViewById(R.id.ground);

        eggsContainer = findViewById(R.id.eggsContainer);

        mHandler = new Handler();
        mSpawnEggRunnable = new Runnable() {
            @Override
            public void run() {
                spawnEggInternal();
            }
        };
        mHandler.postDelayed(mSpawnEggRunnable, 50);
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
        if (eggIndex >= 10) {
            return;
        }

        // Create a new egg
        ImageView egg = new ImageView(this);
        if (random.nextInt(8) == 0) {
            egg.setImageResource(R.drawable.egg3);
        } else if (random.nextInt(3) == 0) {
            egg.setImageResource(R.drawable.egg);
        } else {
            egg.setImageResource(R.drawable.egg2);
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

                eggIndex--;
            }
        });

        animateFall(egg);

        eggIndex++;
    }

    private void animateFall(final ImageView egg) {
        float newY = mGround.getY() + mGround.getHeight() - egg.getHeight() / 2 + mGround.getTop() + 1000;

        ViewPropertyAnimator animator = egg.animate()
                .translationY(newY)
                .setInterpolator(new AccelerateInterpolator())
                .setDuration(mDuration)
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

                                        eggIndex--;
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

                            eggIndex--;

                            if (eggIndex == 0) {
                                eggIndex = 0;
                            }
                        }

                        // Schedule the next egg spawn
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                spawnEggInternal();
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

}