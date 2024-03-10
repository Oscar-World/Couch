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

    ImageView teamLogo, ableDLogo;
    Animation appear, disappear;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        setVariable();
        setView();

    } // onCreate()

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() 호출됨");
    }


    /*
    변수 초기화
     */
    public void setVariable() {

        teamLogo = findViewById(R.id.startLogo_Image);
        ableDLogo = findViewById(R.id.startAbleD_Image);

        appear = AnimationUtils.loadAnimation(StartActivity.this, R.anim.logoappear);
        disappear = AnimationUtils.loadAnimation(StartActivity.this, R.anim.logodisappear);

        handler = new Handler();

    } // setVariable()


    /*
    뷰 초기화
     */
    public void setView() {

        teamLogo.startAnimation(appear);
        ableDLogo.startAnimation(appear);

        NextThread nextThread = new NextThread();
        nextThread.start();

    } // setView()


    /*
    딜레이 후 화면 전환 스레드
     */
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

    } // NextThread


}