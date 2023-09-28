package com.example.couch.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.couch.R;

public class MainActivity extends AppCompatActivity {

    String TAG = "메인 액티비티";

    Button gameMode1Btn;
    Button gameMode2Btn;
    Button gameMode3Btn;
//    Button checkRankingBtn;

    Animation appear;

    Handler handler;

    ImageView iv_couch_c;
    ImageView iv_couch_olor;
    ImageView iv_couch_t;
    ImageView iv_couch_ouch;
    Animation animation;
    ImageView setUpImage;

    FrameLayout settingLayout;
    TextView languageChoiceText;
    ImageButton settingBackBtn;
    TextView settingText;
    TextView soundText;
    TextView languageText;

    String[] language = {"English","한국어","中文(简体)","中文(繁體)","日本語"};

    int languageNum = 0;
    int getLanguageNum = 0;
    SharedPreferences languageShared;
    SharedPreferences.Editor languageEditor;

    FrameLayout ruleLayout;
    TextView ruleText;
    TextView ruleTimeText;
    TextView ruleLifeText;
    TextView ruleBonusText;
    CheckBox ruleCheckBox;
    Button ruleBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        gameMode1Btn = findViewById(R.id.gameMode1_Btn);
        gameMode2Btn = findViewById(R.id.gameMode2_Btn);
        gameMode3Btn = findViewById(R.id.gameMode3_Btn);
//        checkRankingBtn = findViewById(R.id.checkRanking_Btn);

        appear = AnimationUtils.loadAnimation(MainActivity.this, R.anim.logoappear);

        handler = new Handler();

        setUpImage = findViewById(R.id.setUp_Image);
        settingLayout = findViewById(R.id.setUp_FrameLayout);
        languageChoiceText = findViewById(R.id.language_Text);
        settingBackBtn = findViewById(R.id.settingBack_Btn);
        settingText = findViewById(R.id.settingText);
        soundText = findViewById(R.id.soundText);
        languageText = findViewById(R.id.languageText);

        setAnim();

        languageShared = getSharedPreferences("language", MODE_PRIVATE);
        languageEditor = languageShared.edit();

        ruleLayout = findViewById(R.id.mode1Rule_FrameLayout);
        ruleText = findViewById(R.id.mode1Rule_Text);
        ruleBtn = findViewById(R.id.mode1Rule_Btn);
        ruleTimeText = findViewById(R.id.mode1RuleTime_Text);
        ruleLifeText = findViewById(R.id.mode1RuleLife_Text);
        ruleBonusText = findViewById(R.id.mode1RuleBonus_Text);
        ruleCheckBox = findViewById(R.id.mode1Rule_CheckBox);

        setLanguage();

    } // setVariable()


    public void setAnim() {

        iv_couch_c = (ImageView) findViewById(R.id.iv_couch_c);
        iv_couch_olor = (ImageView) findViewById(R.id.iv_couch_olor);
        iv_couch_t = (ImageView) findViewById(R.id.iv_couch_t);
        iv_couch_ouch = (ImageView) findViewById(R.id.iv_couch_ouch);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_iv_c);
        iv_couch_c.startAnimation(animation);
        iv_couch_olor.startAnimation(animation);
        iv_couch_t.startAnimation(animation);
        iv_couch_ouch.startAnimation(animation);
        animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_iv_olor);
        iv_couch_olor.startAnimation(animation);
        iv_couch_t.startAnimation(animation);
        animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_iv_ouch);
        iv_couch_c.startAnimation(animation);
        animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_iv_t);
        iv_couch_ouch.startAnimation(animation);

    }

    public void setView() {

        gameMode1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(MainActivity.this, Mode1Activity.class);
//                startActivity(i);
                ruleLayout.setVisibility(View.VISIBLE);
            }
        });

        gameMode2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Mode2Activity.class);
                startActivity(i);
            }
        });

        gameMode3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Mode3Activity.class);
                startActivity(i);
            }
        });

//        checkRankingBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(MainActivity.this, RankActivity.class);
//                startActivity(i);
//            }
//        });

        setUpImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                settingLayout.setVisibility(View.VISIBLE);
                settingLayout.startAnimation(appear);

            }
        });

        settingLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });

        languageChoiceText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                languageDlg();
            }
        });

        settingBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingLayout.clearAnimation();
                settingLayout.setVisibility(View.GONE);
            }
        });

        AppearThread thread = new AppearThread();
        thread.start();


        ruleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ruleLayout.setVisibility(View.GONE);

                if (ruleCheckBox.isChecked()) {



                }

            }
        });

    } // setView()


    public void languageDlg() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(getString(R.string.setLanguage_KR))
                .setSingleChoiceItems(language, getLanguageNum, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        languageNum = i;
                    }
                })
                .setPositiveButton("완료", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        saveLanguage(languageNum);
                    }
                })
                .setNegativeButton("취소", null)
                .setCancelable(false)
                .create()
                .show();

    } // languageDlg()


    public void saveLanguage(int num) {

        languageEditor.putInt("num", num);
        languageEditor.commit();

        setLanguage();

    }


    public void setLanguage() {

        getLanguageNum = languageShared.getInt("num", -1);

        if (getLanguageNum == -1 | getLanguageNum == 0) {

            //영어
            gameMode1Btn.setText(getString(R.string.mode1Text_EN));
            gameMode2Btn.setText(getString(R.string.mode2Text_EN));
            gameMode3Btn.setText(getString(R.string.mode3Text_EN));
            settingText.setText(getString(R.string.setting_EN));
            soundText.setText(getString(R.string.setSound_EN));
            languageText.setText(getString(R.string.setLanguage_EN));
            languageChoiceText.setText(getString(R.string.language_EN));
            ruleBtn.setText(getString(R.string.ok_EN));
            ruleCheckBox.setText(getString(R.string.checkBox_EN));
            ruleText.setText(getString(R.string.rule_EN));
            ruleBonusText.setText(getString(R.string.info_mode1_EN));


        } else if (getLanguageNum == 1) {

            //한국어
            gameMode1Btn.setText(getString(R.string.mode1Text_KR));
            gameMode2Btn.setText(getString(R.string.mode2Text_KR));
            gameMode3Btn.setText(getString(R.string.mode3Text_KR));
            settingText.setText(getString(R.string.setting_KR));
            soundText.setText(getString(R.string.setSound_KR));
            languageText.setText(getString(R.string.setLanguage_KR));
            languageChoiceText.setText(getString(R.string.language_KR));
            ruleBtn.setText(getString(R.string.ok_KR));
            ruleCheckBox.setText(getString(R.string.checkBox_KR));
            ruleText.setText(getString(R.string.rule_KR));
            ruleBonusText.setText(getString(R.string.info_mode1_KR));

        } else if (getLanguageNum == 2) {

            //중국어(간체)
            gameMode1Btn.setText(getString(R.string.mode1Text_CN1));
            gameMode2Btn.setText(getString(R.string.mode2Text_CN1));
            gameMode3Btn.setText(getString(R.string.mode3Text_CN1));
            settingText.setText(getString(R.string.setting_CN1));
            soundText.setText(getString(R.string.setSound_CN1));
            languageText.setText(getString(R.string.setLanguage_CN1));
            languageChoiceText.setText(getString(R.string.language_CN1));
            ruleBtn.setText(getString(R.string.ok_CN1));
            ruleCheckBox.setText(getString(R.string.checkBox_CN1));
            ruleText.setText(getString(R.string.rule_CN1));
            ruleBonusText.setText(getString(R.string.info_mode1_CN1));

        } else if (getLanguageNum == 3) {

            //중국어(번체)
            gameMode1Btn.setText(getString(R.string.mode1Text_CN2));
            gameMode2Btn.setText(getString(R.string.mode2Text_CN2));
            gameMode3Btn.setText(getString(R.string.mode3Text_CN2));
            settingText.setText(getString(R.string.setting_CN2));
            soundText.setText(getString(R.string.setSound_CN2));
            languageText.setText(getString(R.string.setLanguage_CN2));
            languageChoiceText.setText(getString(R.string.language_CN2));
            ruleBtn.setText(getString(R.string.ok_CN2));
            ruleCheckBox.setText(getString(R.string.checkBox_CN2));
            ruleText.setText(getString(R.string.rule_CN2));
            ruleBonusText.setText(getString(R.string.info_mode1_CN2));

        } else if (getLanguageNum == 4) {

            //일본어
            gameMode1Btn.setText(getString(R.string.mode1Text_JP));
            gameMode2Btn.setText(getString(R.string.mode2Text_JP));
            gameMode3Btn.setText(getString(R.string.mode3Text_JP));
            settingText.setText(getString(R.string.setting_JP));
            soundText.setText(getString(R.string.setSound_JP));
            languageText.setText(getString(R.string.setLanguage_JP));
            languageChoiceText.setText(getString(R.string.language_JP));
            ruleBtn.setText(getString(R.string.ok_JP));
            ruleCheckBox.setText(getString(R.string.checkBox_JP));
            ruleText.setText(getString(R.string.rule_JP));
            ruleBonusText.setText(getString(R.string.info_mode1_JP));

        }

    } // setLanguage()


    public class AppearThread extends Thread {

        public void run() {

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            handler.post(new Runnable() {
                @Override
                public void run() {

                    gameMode1Btn.setVisibility(View.VISIBLE);
                    gameMode2Btn.setVisibility(View.VISIBLE);
                    gameMode3Btn.setVisibility(View.VISIBLE);
                    setUpImage.setVisibility(View.VISIBLE);
//                    checkRankingBtn.setVisibility(View.VISIBLE);

                    gameMode1Btn.startAnimation(appear);
                    gameMode2Btn.startAnimation(appear);
                    gameMode3Btn.startAnimation(appear);
                    setUpImage.startAnimation(appear);
//                    checkRankingBtn.startAnimation(appear);

                }
            });

        }

    }

}