package com.example.demouser.dice;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final int MAX_DICE_NUM = 6;
    protected int userTurnScore = 0;
    protected int userTotalScore = 0;
    protected int cptTurnScore = 0;
    protected int cptTotalScore = 0;

    protected ImageView diceImageView;
    protected Drawable diceDrawable;
    protected TextView userTotalView, userTurnView, cptTotalView, cptTurnView;
    protected Button rollBtn, holdBtn;
    protected WebView rollingDice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        diceImageView = findViewById(R.id.diceImage);
        final Resources res = getResources();

//        rollingDice = (WebView) findViewById(R.id.wv);
////        rollingDice.setWebViewClient(new myWebClient());
//
//        rollingDice.loadUrl("https://giphy.com/gifs/l3q2yYNt8DXoyKRdm");

        diceDrawable = res.getDrawable(R.drawable.dice1);

        userTotalView = findViewById(R.id.userTotal);
        userTurnView = findViewById(R.id.userTurn);
        cptTotalView = findViewById(R.id.cptTotal);
        cptTurnView = findViewById(R.id.cptTurn);

        rollBtn = findViewById(R.id.rollButton);
        holdBtn = findViewById(R.id.holdButton);
        Button resetBtn = findViewById(R.id.resetButton);

        rollBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roll();
                }}
        );

        holdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hold();
                }
                }
        );

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                }
                }
        );
    }

    protected void roll(){
        int diceNum = rollHelper();

        if (diceNum == 1) {
            userTurnScore = 0;
            userTurnView.setText("Your turn score: 0");

            computerTurn();
        }
        else {
            userTurnScore += diceNum;
            userTurnView.setText("Your turn score: "+ userTurnScore);
        }
    }

    protected int rollHelper() {
        Random rand = new Random();
        int diceNum = rand.nextInt(MAX_DICE_NUM) + 1;
        final String dice = "dice" + diceNum;

        diceImageView.setImageResource(getResources().getIdentifier(dice,"drawable", getPackageName()));

        return diceNum;
    }

    protected void hold(){
        userTotalScore += userTurnScore;
        userTotalView.setText("Your score: " + userTotalScore);
        // the turn is end
        userTurnScore = 0;
        userTurnView.setText("Your turn score: "+ userTurnScore);

        computerTurn();
    }

    protected void reset(){
        userTotalScore = 0;
        userTurnScore = 0;
        cptTurnScore = 0;
        cptTotalScore = 0;
        userTotalView.setText("Your score: 0");
        userTurnView.setText("Your turn score: 0");
        cptTotalView.setText("Computer score: 0");
    }

    protected boolean computerTurnHelper() {
        int diceNum = rollHelper();
        //Log.i("test", "dice number" +diceNum);
        if (diceNum == 1) {
            cptTurnView.setText("Computer Turn Score: 0");
            cptTurnScore = 0;
            return false;
        }
        else {
            cptTurnScore += diceNum;
            cptTurnView.setText("Computer Turn Score: " + cptTurnScore);
            return true;

        }
    }

    protected void computerTurn() {

        rollBtn.setEnabled(false);
        holdBtn.setEnabled(false);

        final Handler handler = new Handler();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                //Log.i("test", "Goes in the handler");
                if (computerTurnHelper() && cptTurnScore < 20) {
                    handler.postDelayed(this,1000);
                }
                else {
                    cptTotalScore += cptTurnScore;
                    cptTotalView.setText("Computer Score: " + cptTotalScore);
                    cptTurnScore = 0;
                    cptTurnView.setText("Computer Turn Score: 0");
                }
            }
        };

        handler.postDelayed(r,1000);

        //Log.i("test", "Gets to the setText part");

        rollBtn.setEnabled(true);
        holdBtn.setEnabled(true);
    }
}
