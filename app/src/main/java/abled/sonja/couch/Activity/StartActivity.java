package abled.sonja.couch.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import abled.sonja.couch.R;

public class StartActivity extends AppCompatActivity {

    String TAG = "시작 액티비티";

    ImageView teamLogo;
    ImageView ableDLogo;
    Animation appear;
    Animation disappear;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        setVariable();
        setView();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() 호출됨");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() 호출됨");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() 호출됨");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() 호출됨");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart() 호출됨");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() 호출됨");
    }


    public void setVariable() {

        teamLogo = findViewById(R.id.startLogo_Image);
        ableDLogo = findViewById(R.id.startAbleD_Image);

        appear = AnimationUtils.loadAnimation(StartActivity.this, R.anim.logoappear);
        disappear = AnimationUtils.loadAnimation(StartActivity.this, R.anim.logodisappear);

        handler = new Handler();

    } // setVariable()


    public void setView() {

        teamLogo.startAnimation(appear);
        ableDLogo.startAnimation(appear);

        NextThread nextThread = new NextThread();
        nextThread.start();

    } // setView()


    public class NextThread extends Thread {

        public void run() {

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            handler.post(new Runnable() {
                @Override
                public void run() {

                    teamLogo.startAnimation(disappear);
                    ableDLogo.startAnimation(disappear);
                    teamLogo.setVisibility(View.GONE);
                    ableDLogo.setVisibility(View.GONE);

                }
            });

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent i = new Intent(StartActivity.this, MainActivity.class);
            startActivity(i);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();

        }

    }


}