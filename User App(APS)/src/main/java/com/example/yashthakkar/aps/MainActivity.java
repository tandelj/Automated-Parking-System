package com.example.yashthakkar.aps;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ImageView img;
    TextView text;
   // Animation bottom;
    private static int WELCOME_TIMEOUT=2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img=(ImageView) findViewById(R.id.img);
        text=(TextView) findViewById(R.id.text);
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(img, View.ALPHA,  0.0f, 0.89f);
        fadeOut.setDuration(3500);
        // ObjectAnimator fadeIn = ObjectAnimator.ofFloat(img, "alpha", .3f, 1f);
        //fadeIn.setDuration(2000);
        final AnimatorSet mAnimationSet = new AnimatorSet();

        mAnimationSet.play(fadeOut);
        mAnimationSet.start();
        //bottom = AnimationUtils.loadAnimation(this,R.anim.lefttoright);
        //text.setAnimation(bottom);

        new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run() {
                                          Intent welcome = new Intent(MainActivity.this , LoginActivity.class);
                                          startActivity(welcome);

                                          overridePendingTransition(R.anim.bottomtotop,R.anim.toptodown);
                                          finish();
                                      }
                                  }
                , WELCOME_TIMEOUT);

    }
}
