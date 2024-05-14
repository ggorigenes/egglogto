package com.example.testing;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
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
import androidx.core.content.res.ResourcesCompat;

import com.example.testing.model.Egg;
import com.example.testing.model.Stat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameTesting extends AppCompatActivity {
    int textIndex=0;
    private List<ImageView> mEggs = new ArrayList<>();
    private ImageView mGround;
    private ImageView mBasket;
    private long mDuration = 1800L;
    private Random random;
    private RelativeLayout eggsContainer;
    private Handler mHandler;
    private Runnable mSpawnEggRunnable;
    private int life =  2;
    private int score =  0;
    private  int difficulty = 0;
    private int difficultyLimit = 100 ;
    private  int fallSpeeder = 50;
    private  int spawnSpeeder =  0;
    private String  eggType;
    TextView scoreDraw;
    TextView chickenClickedDraw;
    RelativeLayout lifeContainer;
    Stat stat;
    VideoView videoView;
    int[] basketLocation = new int[2];
    int[] eggLocation = new int[2];
    String gameMode;

    boolean isMusicInGame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        isMusicInGame  = getIntent().getBooleanExtra("musicGame",true);
        MediaPlayer mp = MediaPlayer.create(GameTesting.this, R.raw.game);
        if (isMusicInGame) mp.start();


        this.gameMode= intent.getStringExtra("gameMode");
        resetStat(this.gameMode);

        setContentView(R.layout.activity_game_testing);
        scoreDraw = (TextView)findViewById(R.id.score);
        chickenClickedDraw = (TextView)findViewById(R.id.chickenCount);
        lifeContainer = (RelativeLayout)findViewById(R.id.lifeContainer);
        TextView higScoreDisplay = (TextView) findViewById(R.id.highscore);
        higScoreDisplay.setText("High Score: " + stat.getHighScore() );
        mGround = findViewById(R.id.ground);
        eggsContainer = findViewById(R.id.eggsContainer);
        mBasket = findViewById(R.id.basket);

        random = new Random();
        mHandler = new Handler();
        mSpawnEggRunnable = new Runnable() {
            @Override
            public void run() {
                if (life <= 0) {
                    callGameOverPage();
                    mp.pause();
                }
                    System.out.println("mBasket.getHeight(): " + mBasket.getHeight());
                    System.out.println("mBasket.getHeight(): " + mBasket.getHeight());
                if  (score >=  900) {
                    //DoubleSpawn
                    int  firstEggXScale = 0;
                    if (random.nextInt(99) < 9 && score >= 100) {
                        firstEggXScale  =  spawnRandomEggInternal(0) + random.nextInt(400 + 1 - 200) +   200;

                    } else if (random.nextInt(99) < 19 && score >= 500) {
                        firstEggXScale  =  spawnHatInternal(0) + random.nextInt(400 + 1 - 200) +   200;
                    }

                    else {
                        firstEggXScale  =  spawnEggInternal(0) + random.nextInt(400 + 1 - 200) +   200;
                    }

                    if (firstEggXScale > 930 ) {
                        spawnEggInternal(1);
                    }   else  {
                        spawnEggInternal(firstEggXScale);
                    }
                } else  {
                    //SingleSpawn
                    if (random.nextInt(99) < 9 && score >= 100) {
                        spawnRandomEggInternal(0);
                    } else if (random.nextInt(99) < 19 && score >= 500) {
                        spawnHatInternal(0);
                    } else {
                        spawnEggInternal(0) ;
                    }
                }
                //chickenSpawn
                if (random.nextInt(99) < 14  && score>= 300) {
                    spawnChickenInternal(0);
                }
                mHandler.postDelayed(this,2500-spawnSpeeder);
            }
        };
        mHandler.postDelayed(mSpawnEggRunnable,2000);

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

    private int spawnEggInternal(int eggXScale) {
        System.out.println(fallSpeeder);
        System.out.println(difficulty);
        if (score >= stat.getDifficultyLimit()  &&  stat.getDifficulty()  < 1100) {
            stat.setDifficulty(difficulty  +  7);
            stat.setDifficultyLimit(stat.getDifficultyLimit() + 200);
            stat.setFallSpeeder(stat.getFallSpeeder() + stat.getFallSpeederIncrease());
            stat.setSpawnSpeeder(stat.getSpawnSpeeder() + stat.getSpawnSpeederIncrease());
        }

        ImageView egg = new ImageView(this);


        int eggChance =  random.nextInt(99);


        if (eggChance< 3) {
            egg.setImageResource(R.drawable.egg3);
            eggType  =  "gold";
        } else if (eggChance < 35 + difficulty) {
            egg.setImageResource(R.drawable.egg2);
            eggType  =  "rotten";
        } else {
            egg.setImageResource(R.drawable.egg);
            eggType  =  "normal";
        }


        Egg eggStat = new Egg(eggType);


        egg.setScaleX(3f);
        egg.setScaleY(3f);
        egg.setBackgroundColor(Color.TRANSPARENT);
        egg.setVisibility(View.VISIBLE);
        egg.setTag(eggType);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                100,
                100);
//        float startX = random.nextInt(getWindowManager().getDefaultDisplay().getWidth() - egg.getWidth());

        System.out.println("xscalemax: "  + ((mBasket.getWidth() - 150) - egg.getWidth()));
        System.out.println("egg.getWidth(): "  +egg.getWidth());

        int startX;
        if (eggXScale == 0 ) {
            startX= random.nextInt((mBasket.getWidth()-150) - egg.getWidth());
        }else {
            startX = eggXScale;
        }



        float startY = 100;
        params.leftMargin = (int) startX;
        params.topMargin = (int) startY;
        egg.setLayoutParams(params);
        egg.setY(startY);


        mEggs.add(egg);
        eggsContainer.addView(egg, params);

        mBasket.getLocationOnScreen(basketLocation);
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
                                        eggStat.setClick(true);

                                        MediaPlayer mp = MediaPlayer.create(GameTesting.this, R.raw.click);
                                        mp.start();

                                        if (egg.getTag().equals("normal") || egg.getTag().equals("gold")) {
                                            life--;
                                            if(life==1) {
                                                lifeContainer.getChildAt(1).setVisibility(View.INVISIBLE);

                                            } else if(life==0){
                                                lifeContainer.getChildAt(0).setVisibility(View.INVISIBLE);
                                            }
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

                            if (!eggStat.isClick()) {
                                switch (egg.getTag().toString()) {
                                    case "gold":
                                        score = score + stat.getGoldEggScore();
                                        spawnTextInternal(startX + 40,850,("+"+ stat.getNormalEggScore()));
                                        break;

                                    case "normal":
                                        score = score + stat.getNormalEggScore();
                                        spawnTextInternal(startX + 40,850,("+"+ stat.getNormalEggScore()));
                                        break;

                                    case "rotten":
                                        life--;
                                        if(life==1) {
                                            lifeContainer.getChildAt(1).setVisibility(View.INVISIBLE);

                                        } else if(life==0){
                                            lifeContainer.getChildAt(0).setVisibility(View.INVISIBLE);
                                        }
                                }
                            }
                            scoreDraw.setText("Score:" + score);
                        }

                    }

                    @Override
                    public void onAnimationCancel(@NonNull Animator animator) {
                    }

                    @Override
                    public void onAnimationRepeat(@NonNull Animator animator) {
                    }
                });


        animateFall(animator);
        System.out.println("startX: "  +startX);
        return  (int) startX;
    }

    private int spawnRandomEggInternal(int eggXScale) {
        System.out.println(fallSpeeder);
        System.out.println(difficulty);
        if (score >= stat.getDifficultyLimit()  &&  stat.getDifficulty()  < 1100) {
            stat.setDifficulty(difficulty  +  7);
            stat.setDifficultyLimit(stat.getDifficultyLimit() + 200);
            stat.setFallSpeeder(stat.getFallSpeeder() + stat.getFallSpeederIncrease());
            stat.setSpawnSpeeder(stat.getSpawnSpeeder() + stat.getSpawnSpeederIncrease());
        }

        ImageView egg = new ImageView(this);


        int eggChance =  random.nextInt(99);

        egg.setImageResource(R.drawable.egg4);

        if (eggChance < 24) {
            eggType  =  "add_life";
        } else if (eggChance < 39) {
            eggType  =  "minus_life";
        } else if (eggChance < 54) {
            eggType  =  "gold";
        } else {
            eggType  =  "normal";
        }


        Egg eggStat = new Egg(eggType);


        egg.setScaleX(3f);
        egg.setScaleY(3f);
        egg.setBackgroundColor(Color.TRANSPARENT);
        egg.setVisibility(View.VISIBLE);
        egg.setTag(eggType);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                100,
                100);
//        float startX = random.nextInt(getWindowManager().getDefaultDisplay().getWidth() - egg.getWidth());

        System.out.println("xscalemax: "  + ((mBasket.getWidth() - 150) - egg.getWidth()));
        System.out.println("egg.getWidth(): "  +egg.getWidth());

        int startX;
        if (eggXScale == 0 ) {
            startX= random.nextInt((mBasket.getWidth()-150) - egg.getWidth());
        }else {
            startX = eggXScale;
        }



        float startY = 100;
        params.leftMargin = (int) startX;
        params.topMargin = (int) startY;
        egg.setLayoutParams(params);
        egg.setY(startY);


        mEggs.add(egg);

        eggsContainer.addView(egg, params);

        mBasket.getLocationOnScreen(basketLocation);
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
                                        eggStat.setClick(true);

                                        MediaPlayer mp = MediaPlayer.create(GameTesting.this, R.raw.click);
                                        mp.start();
                                        egg.getLocationOnScreen(eggLocation);
                                        System.out.println("random.getTag().toString: " + egg.getTag().toString());
                                        switch (egg.getTag().toString()) {
                                            case "gold":
                                                score = score + stat.getGoldEggScore();
                                                spawnTextInternal(startX + 40,850,("+"+ stat.getNormalEggScore()));
                                                break;

                                            case "normal":
                                                score = score + stat.getNormalEggScore();
                                                spawnTextInternal(startX + 40,850,("+"+ stat.getNormalEggScore()));
                                                break;

                                            case "minus_life":
                                                life--;
                                                if(life==1) {
                                                    lifeContainer.getChildAt(1).setVisibility(View.INVISIBLE);

                                                } else if(life==0){
                                                    lifeContainer.getChildAt(0).setVisibility(View.INVISIBLE);
                                                }

                                                break;

                                            case "add_life":
                                                if(life==1) {
                                                    life++;
                                                    if (life==2) {
                                                        lifeContainer.getChildAt(1).setVisibility(View.VISIBLE);
                                                    }
                                                }

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

                            if (!eggStat.isClick()) {
                                score = score - 300;
                                spawnTextInternal(startX + 40,850,("-"+ 300));
                            }
                            scoreDraw.setText("Score:" + score);
                        }

                    }

                    @Override
                    public void onAnimationCancel(@NonNull Animator animator) {
                    }

                    @Override
                    public void onAnimationRepeat(@NonNull Animator animator) {
                    }
                });


        animateFall(animator);
        System.out.println("startX: "  +startX);
        return  (int) startX;
    }

    private int spawnHatInternal(int eggXScale) {
        System.out.println(fallSpeeder);
        System.out.println(difficulty);
        if (score >= stat.getDifficultyLimit()  &&  stat.getDifficulty() < 1100) {
            stat.setDifficulty(difficulty  +  7);
            stat.setDifficultyLimit(stat.getDifficultyLimit() + 200);
            stat.setFallSpeeder(stat.getFallSpeeder() + stat.getFallSpeederIncrease());
            stat.setSpawnSpeeder(stat.getSpawnSpeeder() + stat.getSpawnSpeederIncrease());
        }
        ImageView egg = new ImageView(this);
        int eggChance =  random.nextInt(99);

        egg.setImageResource(R.drawable.hat);

        eggType = "hat";

        Egg eggStat = new Egg(eggType);


        egg.setScaleX(3f);
        egg.setScaleY(3f);
        egg.setBackgroundColor(Color.TRANSPARENT);
        egg.setVisibility(View.VISIBLE);
        egg.setTag(eggType);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                100,
                100);
//        float startX = random.nextInt(getWindowManager().getDefaultDisplay().getWidth() - egg.getWidth());

        System.out.println("xscalemax: "  + ((mBasket.getWidth() - 150) - egg.getWidth()));
        System.out.println("egg.getWidth(): "  +egg.getWidth());

        int startX;
        if (eggXScale == 0 ) {
            startX= random.nextInt((mBasket.getWidth()-150) - egg.getWidth());
        }else {
            startX = eggXScale;
        }



        float startY = 100;
        params.leftMargin = (int) startX;
        params.topMargin = (int) startY;
        egg.setLayoutParams(params);
        egg.setY(startY);


        mEggs.add(egg);

        eggsContainer.addView(egg, params);

        mBasket.getLocationOnScreen(basketLocation);
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

                                        eggStat.setClick(true);

                                        MediaPlayer mp = MediaPlayer.create(GameTesting.this, R.raw.click);
                                        mp.start();

                                        System.out.println("random.getTag().toString: " + egg.getTag().toString());

                                        if (eggStat.getClickIsClickCount()<=2) {
                                            eggStat.setClickIsClickCount(eggStat.getClickIsClickCount()+1);
                                        }

                                        if (eggStat.getClickIsClickCount()>=2) {
                                            mEggs.remove(egg);
                                            eggsContainer.removeView(egg);
                                            score = score + 100;
                                            spawnTextInternal(startX + 40,850,("+"+ 100));
                                            scoreDraw.setText("Score:" + score);
                                        }
                                        eggStat.setClick(false);

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

                            if (eggStat.getClickIsClickCount()<2) {
                                score = score - 100;
                                spawnTextInternal(startX + 40,850,("-"+100));
                            }
                            scoreDraw.setText("Score:" + score);
                        }

                    }

                    @Override
                    public void onAnimationCancel(@NonNull Animator animator) {
                    }

                    @Override
                    public void onAnimationRepeat(@NonNull Animator animator) {
                    }
                });


        animateFall(animator);
        System.out.println("startX: "  +startX);
        return  (int) startX;
    }

    private int spawnChickenInternal(int eggXScale) {
        System.out.println(fallSpeeder);
        System.out.println(difficulty);
        if (score >= stat.getDifficultyLimit()  &&  stat.getDifficulty() < 1100) {
            stat.setDifficulty(difficulty  +  7);
            stat.setDifficultyLimit(stat.getDifficultyLimit() + 200);
            stat.setFallSpeeder(stat.getFallSpeeder() + stat.getFallSpeederIncrease());
            stat.setSpawnSpeeder(stat.getSpawnSpeeder() + stat.getSpawnSpeederIncrease());
        }
        ImageView egg = new ImageView(this);
        int eggChance =  random.nextInt(99);

        egg.setImageResource(R.drawable.chicken);

        eggType = "chicken";

        Egg eggStat = new Egg(eggType);

        egg.setScaleX(3f);
        egg.setScaleY(3f);
        egg.setBackgroundColor(Color.TRANSPARENT);
        egg.setVisibility(View.VISIBLE);
        egg.setTag(eggType);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                80,
                80);
//        float startX = random.nextInt(getWindowManager().getDefaultDisplay().getWidth() - egg.getWidth());

        System.out.println("xscalemax: "  + ((mBasket.getWidth() - 150) - egg.getWidth()));
        System.out.println("egg.getWidth(): "  +egg.getWidth());

        int startX;
        int startY;
        startY = random.nextInt(800+1 -300) + 300;
        if (eggXScale == 0 ) {
            startX= random.nextInt((mBasket.getWidth()-150) - egg.getWidth());
        }else {
            startX = eggXScale;
        }

        params.leftMargin = startX;
        params.topMargin = startY;
        egg.setLayoutParams(params);
        egg.setY(startY);


        mEggs.add(egg);

        eggsContainer.addView(egg, params);

        ViewPropertyAnimator animator = egg.animate()
                .setDuration(2000)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        egg.post(new Runnable() {
                            @Override
                            public void run() {
                                egg.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        eggStat.setClick(true);

                                        MediaPlayer mp = MediaPlayer.create(GameTesting.this, R.raw.click);
                                        mp.start();

                                        mEggs.remove(egg);
                                        eggsContainer.removeView(egg);
                                        stat.setChickenClicked(stat.getChickenClicked() + 1);
                                        chickenClickedDraw.setText(String.valueOf(stat.getChickenClicked()));

                                        System.out.println("random.getTag().toString: " + egg.getTag().toString());
                                        System.out.println("life: " + life);

                                        if (stat.getChickenClicked()>=3) {
                                            stat.setChickenClicked(0);
                                            chickenClickedDraw.setText(String.valueOf(stat.getChickenClicked()));
                                            if(life==1) {
                                                life++;
                                                if (life==2) {
                                                    lifeContainer.getChildAt(1).setVisibility(View.VISIBLE);
                                                }
                                            }

                                        }
                                    }
                                });
                            }
                        });
                    }

                    @Override
                    public void onAnimationEnd(@NonNull Animator animator) {
                        mEggs.remove(egg);
                        eggsContainer.removeView(egg);
                    }

                    @Override
                    public void onAnimationCancel(@NonNull Animator animator) {
                    }

                    @Override
                    public void onAnimationRepeat(@NonNull Animator animator) {
                    }
                });


        animateFall(animator);
        System.out.println("startX: "  +startX);
        return  (int) startX;
    }

    private void spawnTextInternal(int x, int y, String textInput) {
        TextView text = new TextView(this);

        text.setScaleX(2f);
        text.setScaleY(2f);
        text.setBackgroundColor(Color.TRANSPARENT);
        text.setVisibility(View.GONE);
        text.setText(textInput);
        text.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
        Typeface typeface = ResourcesCompat.getFont(this, R.font.minecraft);
        text.setTypeface(typeface);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                80,
                80);


        params.leftMargin = x;
        params.topMargin = y;
        text.setLayoutParams(params);
        text.setY(y);

        if(text.getParent() != null) {
            eggsContainer.removeView(text);
        }
        eggsContainer.addView(text, params);

        ViewPropertyAnimator animator = text.animate()
                .setDuration(500)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        text.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void onAnimationEnd(@NonNull Animator animator) {
                        text.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(@NonNull Animator animator) {
                    }

                    @Override
                    public void onAnimationRepeat(@NonNull Animator animator) {
                    }
                });

        animateFall(animator);

    }




    private void animateFall(ViewPropertyAnimator animator) {
        animator.start();
    }


    private void callGameOverPage () {

        Intent gameOverIntent = new Intent(GameTesting.this, GameOver.class);
        gameOverIntent.putExtra("score_pass", score);
        gameOverIntent.putExtra("highScore", stat.getHighScore());
        gameOverIntent.putExtra("gameMode",this.gameMode);
        gameOverIntent.putExtra("musicGame",isMusicInGame);
        startActivity(gameOverIntent);
        finish();

    }

    private void resetStat(String gameMode) {

        Stat stat = new Stat(gameMode);
        this.stat = stat;
        life =  2;
        score =  0;


    }

}