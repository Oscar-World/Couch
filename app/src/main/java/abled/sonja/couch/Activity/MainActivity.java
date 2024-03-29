package abled.sonja.couch.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import abled.sonja.couch.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    String TAG = "메인 액티비티";
    String localLanguage;
    String[] language = {"English","한국어","中文(简体)","中文(繁體)","日本語"};
    int languageNum = 0;
    int getLanguageNum = 0;

    FrameLayout settingLayout, rule1Layout, rule2Layout, rule3Layout;
    LinearLayout mainLayout;
    Button gameMode1Btn, gameMode2Btn, gameMode3Btn, rule1OkBtn, rule2OkBtn, rule3OkBtn;
    ImageView iv_couch_c, iv_couch_olor, iv_couch_t, iv_couch_ouch, setUpImage;
    ImageButton settingBackBtn, rule1BackBtn, rule2BackBtn, rule3BackBtn, soundOffImage, soundOnImage;
    TextView languageChoiceText, settingText, soundText, languageText, rule1Text, rule1BonusText,
            rule1BonusTitleText, rule2Text, rule2BonusText, rule2BonusTitleText, rule3Text, rule3BonusText, rule3BonusTitleText;
    CheckBox rule1CheckBox, rule2CheckBox, rule3CheckBox;
    Handler handler;
    Animation appear, animation;
    SharedPreferences languageShared, ruleShared, soundShared;
    SharedPreferences.Editor languageEditor, ruleEditor, soundEditor;
    AdView adView;
    Locale locale;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setVariable();
        setView();

    } // onCreate()


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() 호출됨");
        mainLayout.setVisibility(View.VISIBLE);
    } // onStart()


    /*
    시스템 뒤로가기 클릭 이벤트
     */
    @Override
    public void onBackPressed() {

        if (settingLayout.getVisibility() == View.VISIBLE | rule1Layout.getVisibility() == View.VISIBLE
                | rule2Layout.getVisibility() == View.VISIBLE | rule3Layout.getVisibility() == View.VISIBLE) {

            settingLayout.clearAnimation();
            rule1Layout.clearAnimation();
            rule2Layout.clearAnimation();
            rule3Layout.clearAnimation();
            settingLayout.setVisibility(View.GONE);
            rule1Layout.setVisibility(View.GONE);
            rule2Layout.setVisibility(View.GONE);
            rule3Layout.setVisibility(View.GONE);
            mainLayout.setVisibility(View.VISIBLE);

        } else {

            finish();

        }

    } // onBackPressed()


    /*
    변수 초기화
     */
    public void setVariable() {

        gameMode1Btn = findViewById(R.id.gameMode1_Btn);
        gameMode2Btn = findViewById(R.id.gameMode2_Btn);
        gameMode3Btn = findViewById(R.id.gameMode3_Btn);

        appear = AnimationUtils.loadAnimation(MainActivity.this, R.anim.logoappear);

        handler = new Handler();

        mainLayout = findViewById(R.id.main_Layout);

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
        ruleShared = getSharedPreferences("rule", MODE_PRIVATE);
        ruleEditor = ruleShared.edit();
        soundShared = getSharedPreferences("sound", MODE_PRIVATE);
        soundEditor = soundShared.edit();

        rule1Layout = findViewById(R.id.mode1Rule_FrameLayout);
        rule1BackBtn = findViewById(R.id.mode1RuleBack_Btn);
        rule1Text = findViewById(R.id.mode1Rule_Text);
        rule1OkBtn = findViewById(R.id.mode1RuleOk_Btn);
        rule1BonusText = findViewById(R.id.mode1RuleBonus_Text);
        rule1CheckBox = findViewById(R.id.mode1Rule_CheckBox);
        rule1BonusTitleText = findViewById(R.id.mode1RuleBonusTitle_Text);

        rule2Layout = findViewById(R.id.mode2Rule_FrameLayout);
        rule2BackBtn = findViewById(R.id.mode2RuleBack_Btn);
        rule2Text = findViewById(R.id.mode2Rule_Text);
        rule2OkBtn = findViewById(R.id.mode2RuleOk_Btn);
        rule2BonusText = findViewById(R.id.mode2RuleBonus_Text);
        rule2CheckBox = findViewById(R.id.mode2Rule_CheckBox);
        rule2BonusTitleText = findViewById(R.id.mode2RuleBonusTitle_Text);

        rule3Layout = findViewById(R.id.mode3Rule_FrameLayout);
        rule3BackBtn = findViewById(R.id.mode3RuleBack_Btn);
        rule3Text = findViewById(R.id.mode3Rule_Text);
        rule3OkBtn = findViewById(R.id.mode3RuleOk_Btn);
        rule3BonusText = findViewById(R.id.mode3RuleBonus_Text);
        rule3CheckBox = findViewById(R.id.mode3Rule_CheckBox);
        rule3BonusTitleText = findViewById(R.id.mode3RuleBonusTitle_Text);

        soundOffImage = findViewById(R.id.settingSoundOff_Image);
        soundOnImage = findViewById(R.id.settingSoundOn_Image);

        setSoundImage();
        setLanguage();

        AppearThread thread = new AppearThread();
        thread.start();
        ArrayList<Integer> list = new ArrayList<>();

    } // setVariable()


    /*
    애니메이션 초기화
     */
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

    } // setAnim()


    /*
    뷰 초기화
     */
    public void setView() {

        // 게임 모드 1 버튼 클릭
        gameMode1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String checkShared = ruleShared.getString("mode1", "");

                if (checkShared.equals("checked")) {

                    Intent i = new Intent(MainActivity.this, Mode1Activity.class);
                    startActivity(i);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                } else {

                    rule1Layout.startAnimation(appear);
                    rule1Layout.setVisibility(View.VISIBLE);
                    mainLayout.setVisibility(View.GONE);

                }

            }
        });

        // 게임 모드 2 버튼 클릭
        gameMode2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String checkShared = ruleShared.getString("mode2", "");

                if (checkShared.equals("checked")) {

                    Intent i = new Intent(MainActivity.this, Mode2Activity.class);
                    startActivity(i);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                } else {

                    rule2Layout.startAnimation(appear);
                    rule2Layout.setVisibility(View.VISIBLE);
                    mainLayout.setVisibility(View.GONE);

                }

            }
        });

        // 게임 모드 3 버튼 클릭
        gameMode3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String checkShared = ruleShared.getString("mode3", "");

                if (checkShared.equals("checked")) {

                    Intent i = new Intent(MainActivity.this, Mode3Activity.class);
                    startActivity(i);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                } else {

                    rule3Layout.startAnimation(appear);
                    rule3Layout.setVisibility(View.VISIBLE);
                    mainLayout.setVisibility(View.GONE);

                }

            }
        });

        // 설정 이미지 클릭
        setUpImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                settingLayout.setVisibility(View.VISIBLE);
                settingLayout.startAnimation(appear);
                mainLayout.setVisibility(View.GONE);

            }
        });

        // 설정 레이아웃 false 리턴 (설정 안해줄 시, 겹치는 뷰 터치되는 오류 발생)
        settingLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });

        // 언어 변경 텍스트뷰 클릭
        languageChoiceText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                languageDlg();
            }
        });

        // 설정 레이아웃 뒤로가기 버튼 클릭
        settingBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingLayout.clearAnimation();
                settingLayout.setVisibility(View.GONE);
                mainLayout.setVisibility(View.VISIBLE);
            }
        });

        // 게임 모드 1 규칙 레이아웃 확인 버튼 클릭
        rule1OkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rule1Layout.setVisibility(View.GONE);

                if (rule1CheckBox.isChecked()) {

                    ruleEditor.putString("mode1", "checked");
                    ruleEditor.commit();

                }

                mainLayout.setVisibility(View.GONE);
                Intent i = new Intent(MainActivity.this, Mode1Activity.class);
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });

        // 게임 모드 1 규칙 레이아웃 뒤로가기 버튼 클릭
        rule1BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rule1Layout.clearAnimation();
                rule1Layout.setVisibility(View.GONE);
                mainLayout.setVisibility(View.VISIBLE);
            }
        });

        // 게임 모드 2 규칙 레이아웃 확인 버튼 클릭
        rule2OkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rule2Layout.clearAnimation();
                rule2Layout.setVisibility(View.GONE);

                if (rule2CheckBox.isChecked()) {

                    ruleEditor.putString("mode2", "checked");
                    ruleEditor.commit();

                }

                mainLayout.setVisibility(View.GONE);
                Intent i = new Intent(MainActivity.this, Mode2Activity.class);
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });

        // 게임 모드 2 규칙 레이아웃 뒤로가기 버튼 클릭
        rule2BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rule2Layout.clearAnimation();
                rule2Layout.setVisibility(View.GONE);
                mainLayout.setVisibility(View.VISIBLE);
            }
        });

        // 게임 모드 3 규칙 레이아웃 확인 버튼 클릭
        rule3OkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rule3Layout.clearAnimation();
                rule3Layout.setVisibility(View.GONE);

                if (rule3CheckBox.isChecked()) {

                    ruleEditor.putString("mode3", "checked");
                    ruleEditor.commit();

                }

                mainLayout.setVisibility(View.GONE);
                Intent i = new Intent(MainActivity.this, Mode3Activity.class);
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });

        // 게임 모드 3 규칙 레이아웃 뒤로가기 버튼 클릭
        rule3BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rule3Layout.clearAnimation();
                rule3Layout.setVisibility(View.GONE);
                mainLayout.setVisibility(View.VISIBLE);
            }
        });

        // 설정 레이아웃 음소거 이미지 클릭
        soundOffImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                soundEditor.putString("sound", "off");
                soundEditor.commit();

                soundOffImage.setVisibility(View.GONE);
                soundOnImage.setVisibility(View.VISIBLE);

            }
        });

        // 설정 레이아웃 음소거 해제 이미지 클릭
        soundOnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                soundEditor.putString("sound", "on");
                soundEditor.commit();

                startMediaPlayer(R.raw.correct);

                soundOnImage.setVisibility(View.GONE);
                soundOffImage.setVisibility(View.VISIBLE);

            }
        });

    } // setView()


    /*
    설정 - 언어 변경 다이얼로그
     */
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


    /*
    변경된 언어 저장
     */
    public void saveLanguage(int num) {

        languageEditor.putInt("num", num);
        languageEditor.commit();

        setLanguage();

    } // saveLanguage()


    /*
    기기의 기본 설정된 언어 불러오기
     */
    public void getLocalLanguage() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = getApplicationContext().getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = getApplicationContext().getResources().getConfiguration().locale;
        }

        localLanguage = locale.getLanguage();

        if (localLanguage.equals("en")) {

            setLanguageEN();
            languageEditor.putInt("num", 0);
            languageEditor.commit();

        } else if (localLanguage.equals("ko")) {

            setLanguageKR();
            languageEditor.putInt("num", 1);
            languageEditor.commit();

        } else if (localLanguage.equals("zh")) {

            setLanguageCN1();
            languageEditor.putInt("num", 2);
            languageEditor.commit();

        } else if (localLanguage.equals("ja")) {

            setLanguageJP();
            languageEditor.putInt("num", 4);
            languageEditor.commit();

        } else {

            setLanguageEN();

        }

    } // getLocalLanguage()


    /*
    저장된 언어 값을 앱에 세팅
     */
    public void setLanguage() {

        getLanguageNum = languageShared.getInt("num", -1);

        if (getLanguageNum == -1) {

            getLocalLanguage();

        } else if (getLanguageNum == 0) {

            //영어
            setLanguageEN();

        } else if (getLanguageNum == 1) {

            //한국어
            setLanguageKR();

        } else if (getLanguageNum == 2) {

            //중국어(간체)
            setLanguageCN1();

        } else if (getLanguageNum == 3) {

            //중국어(번체)
            setLanguageCN2();

        } else if (getLanguageNum == 4) {

            //일본어
            setLanguageJP();

        }

    } // setLanguage()


    /*
    언어 - 영어 적용
     */
    public void setLanguageEN() {

        //영어
        gameMode1Btn.setText(getString(R.string.mode1Text_EN));
        gameMode2Btn.setText(getString(R.string.mode2Text_EN));
        gameMode3Btn.setText(getString(R.string.mode3Text_EN));
        settingText.setText(getString(R.string.setting_EN));
        soundText.setText(getString(R.string.setSound_EN));
        languageText.setText(getString(R.string.setLanguage_EN));
        languageChoiceText.setText(getString(R.string.language_EN));
        rule1OkBtn.setText(getString(R.string.ok_EN));
        rule1CheckBox.setText(getString(R.string.checkBox_EN));
        rule1Text.setText(getString(R.string.rule_EN));
        rule1BonusText.setText(getString(R.string.info_mode1_EN));
        rule1BonusTitleText.setText(R.string.bonus_EN);
        rule2OkBtn.setText(getString(R.string.ok_EN));
        rule2CheckBox.setText(getString(R.string.checkBox_EN));
        rule2Text.setText(getString(R.string.rule_EN));
        rule2BonusText.setText(getString(R.string.info_mode2_EN));
        rule2BonusTitleText.setText(R.string.bonus_EN);
        rule3OkBtn.setText(getString(R.string.ok_EN));
        rule3CheckBox.setText(getString(R.string.checkBox_EN));
        rule3Text.setText(getString(R.string.rule_EN));
        rule3BonusText.setText(getString(R.string.info_mode3_EN));
        rule3BonusTitleText.setText(R.string.fever_EN);

    } // setLanguageEn()


    /*
    언어 - 한국어 적용
     */
    public void setLanguageKR() {

        //한국어
        gameMode1Btn.setText(getString(R.string.mode1Text_KR));
        gameMode2Btn.setText(getString(R.string.mode2Text_KR));
        gameMode3Btn.setText(getString(R.string.mode3Text_KR));
        settingText.setText(getString(R.string.setting_KR));
        soundText.setText(getString(R.string.setSound_KR));
        languageText.setText(getString(R.string.setLanguage_KR));
        languageChoiceText.setText(getString(R.string.language_KR));
        rule1OkBtn.setText(getString(R.string.ok_KR));
        rule1CheckBox.setText(getString(R.string.checkBox_KR));
        rule1Text.setText(getString(R.string.rule_KR));
        rule1BonusText.setText(getString(R.string.info_mode1_KR));
        rule1BonusTitleText.setText(R.string.bonus_KR);
        rule2OkBtn.setText(getString(R.string.ok_KR));
        rule2CheckBox.setText(getString(R.string.checkBox_KR));
        rule2Text.setText(getString(R.string.rule_KR));
        rule2BonusText.setText(getString(R.string.info_mode2_KR));
        rule2BonusTitleText.setText(R.string.bonus_KR);
        rule3OkBtn.setText(getString(R.string.ok_KR));
        rule3CheckBox.setText(getString(R.string.checkBox_KR));
        rule3Text.setText(getString(R.string.rule_KR));
        rule3BonusText.setText(getString(R.string.info_mode3_KR));
        rule3BonusTitleText.setText(R.string.fever_KR);

    } // setLanguageKR()


    /*
    언어 - 중국어 간체 적용
     */
    public void setLanguageCN1() {

        //중국어(간체)
        gameMode1Btn.setText(getString(R.string.mode1Text_CN1));
        gameMode2Btn.setText(getString(R.string.mode2Text_CN1));
        gameMode3Btn.setText(getString(R.string.mode3Text_CN1));
        settingText.setText(getString(R.string.setting_CN1));
        soundText.setText(getString(R.string.setSound_CN1));
        languageText.setText(getString(R.string.setLanguage_CN1));
        languageChoiceText.setText(getString(R.string.language_CN1));
        rule1OkBtn.setText(getString(R.string.ok_CN1));
        rule1CheckBox.setText(getString(R.string.checkBox_CN1));
        rule1Text.setText(getString(R.string.rule_CN1));
        rule1BonusText.setText(getString(R.string.info_mode1_CN1));
        rule1BonusTitleText.setText(R.string.bonus_CN1);
        rule2OkBtn.setText(getString(R.string.ok_CN1));
        rule2CheckBox.setText(getString(R.string.checkBox_CN1));
        rule2Text.setText(getString(R.string.rule_CN1));
        rule2BonusText.setText(getString(R.string.info_mode2_CN1));
        rule2BonusTitleText.setText(R.string.bonus_CN1);
        rule3OkBtn.setText(getString(R.string.ok_CN1));
        rule3CheckBox.setText(getString(R.string.checkBox_CN1));
        rule3Text.setText(getString(R.string.rule_CN1));
        rule3BonusText.setText(getString(R.string.info_mode3_CN1));
        rule3BonusTitleText.setText(R.string.fever_CN1);

    } // setLanguageCN1()


    /*
    언어 - 중국어 번체 적용
     */
    public void setLanguageCN2() {

        //중국어(번체)
        gameMode1Btn.setText(getString(R.string.mode1Text_CN2));
        gameMode2Btn.setText(getString(R.string.mode2Text_CN2));
        gameMode3Btn.setText(getString(R.string.mode3Text_CN2));
        settingText.setText(getString(R.string.setting_CN2));
        soundText.setText(getString(R.string.setSound_CN2));
        languageText.setText(getString(R.string.setLanguage_CN2));
        languageChoiceText.setText(getString(R.string.language_CN2));
        rule1OkBtn.setText(getString(R.string.ok_CN2));
        rule1CheckBox.setText(getString(R.string.checkBox_CN2));
        rule1Text.setText(getString(R.string.rule_CN2));
        rule1BonusText.setText(getString(R.string.info_mode1_CN2));
        rule1BonusTitleText.setText(R.string.bonus_CN2);
        rule2OkBtn.setText(getString(R.string.ok_CN2));
        rule2CheckBox.setText(getString(R.string.checkBox_CN2));
        rule2Text.setText(getString(R.string.rule_CN2));
        rule2BonusText.setText(getString(R.string.info_mode2_CN2));
        rule2BonusTitleText.setText(R.string.bonus_CN2);
        rule3OkBtn.setText(getString(R.string.ok_CN2));
        rule3CheckBox.setText(getString(R.string.checkBox_CN2));
        rule3Text.setText(getString(R.string.rule_CN2));
        rule3BonusText.setText(getString(R.string.info_mode3_CN2));
        rule3BonusTitleText.setText(R.string.fever_CN2);

    } // setLanguageCN2()


    /*
    언어 - 일본어 적용
     */
    public void setLanguageJP() {

        //일본어
        gameMode1Btn.setText(getString(R.string.mode1Text_JP));
        gameMode2Btn.setText(getString(R.string.mode2Text_JP));
        gameMode3Btn.setText(getString(R.string.mode3Text_JP));
        settingText.setText(getString(R.string.setting_JP));
        soundText.setText(getString(R.string.setSound_JP));
        languageText.setText(getString(R.string.setLanguage_JP));
        languageChoiceText.setText(getString(R.string.language_JP));
        rule1OkBtn.setText(getString(R.string.ok_JP));
        rule1CheckBox.setText(getString(R.string.checkBox_JP));
        rule1Text.setText(getString(R.string.rule_JP));
        rule1BonusText.setText(getString(R.string.info_mode1_JP));
        rule1BonusTitleText.setText(R.string.bonus_JP);
        rule2OkBtn.setText(getString(R.string.ok_JP));
        rule2CheckBox.setText(getString(R.string.checkBox_JP));
        rule2Text.setText(getString(R.string.rule_JP));
        rule2BonusText.setText(getString(R.string.info_mode2_JP));
        rule2BonusTitleText.setText(R.string.bonus_JP);
        rule3OkBtn.setText(getString(R.string.ok_JP));
        rule3CheckBox.setText(getString(R.string.checkBox_JP));
        rule3Text.setText(getString(R.string.rule_JP));
        rule3BonusText.setText(getString(R.string.info_mode3_JP));
        rule3BonusTitleText.setText(R.string.fever_JP);

    } // setLanguageJP()


    /*
    현재 음소거 · 해제 상태에 따라 이미지 변환
     */
    public void setSoundImage() {

        if (soundShared.getString("sound", "").equals("off")) {
            soundOffImage.setVisibility(View.GONE);
            soundOnImage.setVisibility(View.VISIBLE);
        } else {
            soundOnImage.setVisibility(View.GONE);
            soundOffImage.setVisibility(View.VISIBLE);
        }

    } // setSoundImage()


    /*
    음악 재생
     */
    public void startMediaPlayer(int id) {

        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(MainActivity.this, id);
        mediaPlayer.setLooping(false);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
            }
        });

        mediaPlayer.start();

    } // startMediaPlayer()


    /*
    점차 나타나는 애니메이션 스레드
     */
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

                    gameMode1Btn.startAnimation(appear);
                    gameMode2Btn.startAnimation(appear);
                    gameMode3Btn.startAnimation(appear);
                    setUpImage.startAnimation(appear);

                    MobileAds.initialize(MainActivity.this);
                    adView = findViewById(R.id.mainAdView);
                    AdRequest adRequest = new AdRequest.Builder().build();
                    adView.loadAd(adRequest);

                }
            });

        }

    } // AppearThread


}