package com.finke.androidtraining;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {
    private final static String TAG = DisplayMessageActivity.class.getName();

    private ImageView mSoccerBall;
    private boolean mSoccerBallAnimating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(message);

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_display_message);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        textView.setLayoutParams(params);
        layout.addView(textView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");

        if(mSoccerBall == null) {
            mSoccerBall = (ImageView)findViewById(R.id.soccerBallImage);
        }
        mSoccerBallAnimating = true;
        bounceRight();
    }

    private void bounceRight() {
        if(!mSoccerBallAnimating) { return; }
        mSoccerBall.post(new Runnable() {
            @Override
            public void run() {
                ViewGroup parent = (ViewGroup)mSoccerBall.getParent();
                mSoccerBall.animate()
                        .x(parent.getWidth() - parent.getPaddingLeft() - mSoccerBall.getPaddingLeft() - mSoccerBall.getWidth())
                        .setDuration(1000)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                bounceLeft();
                            }
                        });
            }
        });
    }

    private void bounceLeft() {
        if(!mSoccerBallAnimating) { return; }
        mSoccerBall.post(new Runnable() {
            @Override
            public void run() {
                ViewGroup parent = (ViewGroup)mSoccerBall.getParent();
                mSoccerBall.animate()
                        .x(parent.getPaddingLeft())
                        .setDuration(1000)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                bounceRight();
                            }
                        });
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");

        mSoccerBallAnimating = false;
        mSoccerBall.clearAnimation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
