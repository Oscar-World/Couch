package abled.sonja.couch.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import abled.sonja.couch.Data.Database;
import abled.sonja.couch.Data.Tier;
import abled.sonja.couch.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class Mode2Activity extends AppCompatActivity {

    String TAG = "모드2 액티비티";

    TextView[][] three = new TextView[3][3];
    TextView[][] four = new TextView[4][4];
    TextView[][] five = new TextView[5][5];
    TextView[][] six = new TextView[6][6];
    TextView[][] seven = new TextView[7][7];
    TextView scoreView, finalScoreView, tierText, rankText;
    GridLayout layout3, layout4, layout5, layout6, layout7;
    FrameLayout gameLayout, overLayout;
    ImageView btnRestart, btnHome;
    ProgressBar pB_Time, pB_BonusTime;

    String soundStatus;
    String message = "";
    int combo, r, g, b, row, column;
    int score = 0;
    int stage = 0;
    int colorA = 44;
    int arrayCount = 3;
    int time = 5;
    int getLanguageNum = 0;
    long dataSize = 0;
    long backPressedTime = 0;
    boolean status = false;
    boolean sound = true;
    boolean check1 = false;
    boolean check2 = false;

    Random random = new Random();
    Handler handler, handler2;
    SharedPreferences soundShared, languageShared;
    FirebaseDatabase database;
    DatabaseReference ref;
    AdView adView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode2);

        handler = new Handler();
        handler2 = new Handler();
        setLayout();
        btnInitialize();
        setVariable();
        setView();
        correct();
        setColor();
        setTime();
        playing();
        setLanguage();

    } // onCreate()


    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() 호출됨");
        sound = false;
    } // onPause()

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() 호출됨");
        sound = false;
    } // onStop()


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() 호출됨");
        sound = false;
    } // onDestroy()


    /*
    변수 초기화
     */
    public void setVariable() {

        scoreView = findViewById(R.id.scoreView);

        gameLayout = findViewById(R.id.gameLayout);
        overLayout = findViewById(R.id.overLayout);

        finalScoreView = findViewById(R.id.finalScoreView);

        btnRestart = findViewById(R.id.btnRestart);
        btnHome = findViewById(R.id.btnHome);

        pB_Time = (ProgressBar) findViewById(R.id.pB_Mod2Time);
        pB_Time.setMax(5);
        pB_Time.setProgress(5);

        pB_BonusTime = (ProgressBar) findViewById(R.id.pB_Mod2BonusTime);
        pB_BonusTime.setMax(5);
        pB_BonusTime.setProgress(5);

        soundShared = getSharedPreferences("sound", MODE_PRIVATE);
        soundStatus = soundShared.getString("sound", "");

        tierText = findViewById(R.id.tier_Text);
        rankText = findViewById(R.id.rank_Text);

        languageShared = getSharedPreferences("language", MODE_PRIVATE);
        getLanguageNum = languageShared.getInt("num", -1);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("score");

        adView = findViewById(R.id.mode2AdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

    } // setVariable()


    /*
    뷰 초기화
     */
    public void setView() {

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mode2Activity.this, Mode2Activity.class);
                startActivity(i);
                finish();
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    } // setView()


    /*
    시스템 뒤로가기 버튼 클릭 이벤트
     */
    @Override
    public void onBackPressed() {

        if (System.currentTimeMillis() - backPressedTime > 2000) {

            backPressedTime = System.currentTimeMillis();
            Toast.makeText(Mode2Activity.this, message, Toast.LENGTH_SHORT).show();

        } else {

            finish();

        }

    } // onBackPressed()


    /*
    언어 세팅
     */
    public void setLanguage() {

        if (getLanguageNum == -1) {
            message = getString(R.string.backPressed_EN);
        } else if (getLanguageNum == 0) {
            message = getString(R.string.backPressed_EN);
        } else if (getLanguageNum == 1) {
            message = getString(R.string.backPressed_KR);
        } else if (getLanguageNum == 2) {
            message = getString(R.string.backPressed_CN1);
        } else if (getLanguageNum == 3) {
            message = getString(R.string.backPressed_CN2);
        } else if (getLanguageNum == 4) {
            message = getString(R.string.backPressed_JP);
        }

    } // setLanguage()


    /*
    버튼(타일) 초기화
     */
    public void btnInitialize() {

        three[0][0] = findViewById(R.id.three00);
        three[0][1] = findViewById(R.id.three01);
        three[0][2] = findViewById(R.id.three02);
        three[1][0] = findViewById(R.id.three10);
        three[1][1] = findViewById(R.id.three11);
        three[1][2] = findViewById(R.id.three12);
        three[2][0] = findViewById(R.id.three20);
        three[2][1] = findViewById(R.id.three21);
        three[2][2] = findViewById(R.id.three22);

        four[0][0] = findViewById(R.id.four00);
        four[0][1] = findViewById(R.id.four01);
        four[0][2] = findViewById(R.id.four02);
        four[0][3] = findViewById(R.id.four03);
        four[1][0] = findViewById(R.id.four10);
        four[1][1] = findViewById(R.id.four11);
        four[1][2] = findViewById(R.id.four12);
        four[1][3] = findViewById(R.id.four13);
        four[2][0] = findViewById(R.id.four20);
        four[2][1] = findViewById(R.id.four21);
        four[2][2] = findViewById(R.id.four22);
        four[2][3] = findViewById(R.id.four23);
        four[3][0] = findViewById(R.id.four30);
        four[3][1] = findViewById(R.id.four31);
        four[3][2] = findViewById(R.id.four32);
        four[3][3] = findViewById(R.id.four33);

        five[0][0] = findViewById(R.id.five00);
        five[0][1] = findViewById(R.id.five01);
        five[0][2] = findViewById(R.id.five02);
        five[0][3] = findViewById(R.id.five03);
        five[0][4] = findViewById(R.id.five04);
        five[1][0] = findViewById(R.id.five10);
        five[1][1] = findViewById(R.id.five11);
        five[1][2] = findViewById(R.id.five12);
        five[1][3] = findViewById(R.id.five13);
        five[1][4] = findViewById(R.id.five14);
        five[2][0] = findViewById(R.id.five20);
        five[2][1] = findViewById(R.id.five21);
        five[2][2] = findViewById(R.id.five22);
        five[2][3] = findViewById(R.id.five23);
        five[2][4] = findViewById(R.id.five24);
        five[3][0] = findViewById(R.id.five30);
        five[3][1] = findViewById(R.id.five31);
        five[3][2] = findViewById(R.id.five32);
        five[3][3] = findViewById(R.id.five33);
        five[3][4] = findViewById(R.id.five34);
        five[4][0] = findViewById(R.id.five40);
        five[4][1] = findViewById(R.id.five41);
        five[4][2] = findViewById(R.id.five42);
        five[4][3] = findViewById(R.id.five43);
        five[4][4] = findViewById(R.id.five44);

        six[0][0] = findViewById(R.id.six00);
        six[0][1] = findViewById(R.id.six01);
        six[0][2] = findViewById(R.id.six02);
        six[0][3] = findViewById(R.id.six03);
        six[0][4] = findViewById(R.id.six04);
        six[0][5] = findViewById(R.id.six05);
        six[1][0] = findViewById(R.id.six10);
        six[1][1] = findViewById(R.id.six11);
        six[1][2] = findViewById(R.id.six12);
        six[1][3] = findViewById(R.id.six13);
        six[1][4] = findViewById(R.id.six14);
        six[1][5] = findViewById(R.id.six15);
        six[2][0] = findViewById(R.id.six20);
        six[2][1] = findViewById(R.id.six21);
        six[2][2] = findViewById(R.id.six22);
        six[2][3] = findViewById(R.id.six23);
        six[2][4] = findViewById(R.id.six24);
        six[2][5] = findViewById(R.id.six25);
        six[3][0] = findViewById(R.id.six30);
        six[3][1] = findViewById(R.id.six31);
        six[3][2] = findViewById(R.id.six32);
        six[3][3] = findViewById(R.id.six33);
        six[3][4] = findViewById(R.id.six34);
        six[3][5] = findViewById(R.id.six35);
        six[4][0] = findViewById(R.id.six40);
        six[4][1] = findViewById(R.id.six41);
        six[4][2] = findViewById(R.id.six42);
        six[4][3] = findViewById(R.id.six43);
        six[4][4] = findViewById(R.id.six44);
        six[4][5] = findViewById(R.id.six45);
        six[5][0] = findViewById(R.id.six50);
        six[5][1] = findViewById(R.id.six51);
        six[5][2] = findViewById(R.id.six52);
        six[5][3] = findViewById(R.id.six53);
        six[5][4] = findViewById(R.id.six54);
        six[5][5] = findViewById(R.id.six55);

        seven[0][0] = findViewById(R.id.seven00);
        seven[0][1] = findViewById(R.id.seven01);
        seven[0][2] = findViewById(R.id.seven02);
        seven[0][3] = findViewById(R.id.seven03);
        seven[0][4] = findViewById(R.id.seven04);
        seven[0][5] = findViewById(R.id.seven05);
        seven[0][6] = findViewById(R.id.seven06);
        seven[1][0] = findViewById(R.id.seven10);
        seven[1][1] = findViewById(R.id.seven11);
        seven[1][2] = findViewById(R.id.seven12);
        seven[1][3] = findViewById(R.id.seven13);
        seven[1][4] = findViewById(R.id.seven14);
        seven[1][5] = findViewById(R.id.seven15);
        seven[1][6] = findViewById(R.id.seven16);
        seven[2][0] = findViewById(R.id.seven20);
        seven[2][1] = findViewById(R.id.seven21);
        seven[2][2] = findViewById(R.id.seven22);
        seven[2][3] = findViewById(R.id.seven23);
        seven[2][4] = findViewById(R.id.seven24);
        seven[2][5] = findViewById(R.id.seven25);
        seven[2][6] = findViewById(R.id.seven26);
        seven[3][0] = findViewById(R.id.seven30);
        seven[3][1] = findViewById(R.id.seven31);
        seven[3][2] = findViewById(R.id.seven32);
        seven[3][3] = findViewById(R.id.seven33);
        seven[3][4] = findViewById(R.id.seven34);
        seven[3][5] = findViewById(R.id.seven35);
        seven[3][6] = findViewById(R.id.seven36);
        seven[4][0] = findViewById(R.id.seven40);
        seven[4][1] = findViewById(R.id.seven41);
        seven[4][2] = findViewById(R.id.seven42);
        seven[4][3] = findViewById(R.id.seven43);
        seven[4][4] = findViewById(R.id.seven44);
        seven[4][5] = findViewById(R.id.seven45);
        seven[4][6] = findViewById(R.id.seven46);
        seven[5][0] = findViewById(R.id.seven50);
        seven[5][1] = findViewById(R.id.seven51);
        seven[5][2] = findViewById(R.id.seven52);
        seven[5][3] = findViewById(R.id.seven53);
        seven[5][4] = findViewById(R.id.seven54);
        seven[5][5] = findViewById(R.id.seven55);
        seven[5][6] = findViewById(R.id.seven56);
        seven[6][0] = findViewById(R.id.seven60);
        seven[6][1] = findViewById(R.id.seven61);
        seven[6][2] = findViewById(R.id.seven62);
        seven[6][3] = findViewById(R.id.seven63);
        seven[6][4] = findViewById(R.id.seven64);
        seven[6][5] = findViewById(R.id.seven65);
        seven[6][6] = findViewById(R.id.seven66);

    } // btninit()


    /*
    게임 플레이 메인 로직
     */
    public void playing() {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int x = i;
                int y = j;
                three[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (x == row && y == column) {

                            startMediaPlayer(R.raw.correct);

                            pB_Time.setProgress(5);
                            if (!status) {

                                notCombo();

                            } else {

                                pB_Time.setVisibility(View.GONE);
                                pB_BonusTime.setVisibility(View.VISIBLE);

                                row = random.nextInt(3);
                                column = random.nextInt(3);

                                bonusSetColor();

                                score += 100;
                                scoreView.setText(""+score);

                            }
                        } else {

                            startMediaPlayer(R.raw.wrong);
                            sound = false;
                            gameOver();

                        }
                    }
                });
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int x = i;
                int y = j;
                four[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (x == row && y == column) {

                            startMediaPlayer(R.raw.correct);

                            pB_Time.setProgress(5);
                            if (combo == 14) {

                                doCombo();

                            } else {

                                notCombo();

                            }

                        } else {
                            startMediaPlayer(R.raw.wrong);
                            sound = false;
                            gameOver();
                        }
                    }
                });
            }
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                int x = i;
                int y = j;
                five[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (x == row && y == column) {

                            startMediaPlayer(R.raw.correct);

                            pB_Time.setProgress(5);

                            if (combo == 14) {

                                doCombo();

                            } else {

                                notCombo();

                            }

                        } else {
                            startMediaPlayer(R.raw.wrong);
                            sound = false;
                            gameOver();
                        }
                    }
                });
            }
        }

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                int x = i;
                int y = j;
                six[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (x == row && y == column) {

                            startMediaPlayer(R.raw.correct);

                            pB_Time.setProgress(5);

                            if (combo == 14) {

                                doCombo();

                            } else {

                                notCombo();

                            }

                        } else {
                            startMediaPlayer(R.raw.wrong);
                            sound = false;
                            gameOver();
                        }
                    }
                });
            }
        }

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                int x = i;
                int y = j;
                seven[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d(TAG, "arrayCount : " + arrayCount);
                        Log.d(TAG, "arrayCount2 : " + row + " " + column);
                        if (x == row && y == column) {

                            startMediaPlayer(R.raw.correct);

                            pB_Time.setProgress(5);

                            if (combo == 14) {

                                doCombo();

                            } else {

                                notCombo();

                            }

                        } else {
                            startMediaPlayer(R.raw.wrong);
                            sound = false;
                            gameOver();
                        }
                    }
                });
            }
        }

    } // playing()


    /*
    보너스 게임 진행 시 콤보 안 쌓임
     */
    public void notCombo() {

        correct();
        stage++;
        setColor();
        levelColor();
        levelArray();
        score += 100;
        scoreView.setText(""+score);
        combo++;
        handler.removeMessages(0);
        time = 5;
        setTime();

    } // notCombo()


    /*
    게임 진행 시 콤보 쌓임
     */
    public void doCombo() {

        stage++;
        time = 5;

        handler.removeMessages(0);
        handler2.removeMessages(0);

        pB_Time.setVisibility(View.GONE);
        pB_BonusTime.setVisibility(View.VISIBLE);

        bonusStage();
        bonusSetColor();
        score += 100;
        scoreView.setText(""+score);

    } // doCombo()


    /*
    음악 재생
     */
    public void startMediaPlayer(int id) {

        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(Mode2Activity.this, id);
        mediaPlayer.setLooping(false);
        setSound(mediaPlayer);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
            }
        });

        mediaPlayer.start();

    } // startMediaPlayer()


    /*
    타일 색상 세팅
     */
    public void setColor() {

        r = random.nextInt(230);
        g = random.nextInt(230);
        b = random.nextInt(230);
        Log.v("Color", "R : " + r);
        Log.v("Color", "G : " + g);
        Log.v("Color", "B : " + b);

        if (stage < 10) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    three[i][j].setBackgroundColor(Color.rgb(r, g, b));
                    three[i][j].setTextColor(Color.rgb(r, g, b));
                }
            }
            three[row][column].setBackgroundColor(Color.argb(255 - colorA, r, g, b));
        } else if (stage < 20) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    four[i][j].setBackgroundColor(Color.rgb(r, g, b));
                    four[i][j].setTextColor(Color.rgb(r, g, b));
                }
            }
            four[row][column].setBackgroundColor(Color.argb(255 - colorA, r, g, b));

        } else if (stage < 30) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    five[i][j].setBackgroundColor(Color.rgb(r, g, b));
                    five[i][j].setTextColor(Color.rgb(r, g, b));
                }
            }
            five[row][column].setBackgroundColor(Color.argb(255 - colorA, r, g, b));

        } else if (stage < 40) {
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    six[i][j].setBackgroundColor(Color.rgb(r, g, b));
                    six[i][j].setTextColor(Color.rgb(r, g, b));
                }
            }
            six[row][column].setBackgroundColor(Color.argb(255 - colorA, r, g, b));

        } else {
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 7; j++) {
                    seven[i][j].setBackgroundColor(Color.rgb(r, g, b));
                    seven[i][j].setTextColor(Color.rgb(r, g, b));
                }
            }
            seven[row][column].setBackgroundColor(Color.argb(255 - colorA, r, g, b));
        }

    } // setColor()


    /*
    정답 맞췄을 때
     */
    public void correct() {

        if (stage == 10) {
            arrayCount++;
        } else if (stage == 20) {
            arrayCount++;
        } else if (stage == 30) {
            arrayCount++;
        } else if (stage == 40) {
            arrayCount++;
        }
        row = random.nextInt(arrayCount);
        column = random.nextInt(arrayCount);

    } // correct()


    /*
    레이아웃 초기화
     */
    public void setLayout() {

        layout3 = findViewById(R.id.layout3);
        layout4 = findViewById(R.id.layout4);
        layout5 = findViewById(R.id.layout5);
        layout6 = findViewById(R.id.layout6);
        layout7 = findViewById(R.id.layout7);

    } // setLayout()


    /*
    보너스 스테이지 진입
     */
    public void bonusStage() {

        startMediaPlayer(R.raw.bonus);

        layout3.setVisibility(View.VISIBLE);
        layout4.setVisibility(View.GONE);
        layout5.setVisibility(View.GONE);
        layout6.setVisibility(View.GONE);
        layout7.setVisibility(View.GONE);

        status = true;

        row = random.nextInt(3);
        column = random.nextInt(3);

        setBonusTime();

    } // bonusStage()


    /*
    보너스 스테이지 색상 세팅
     */
    public void bonusSetColor() {

        r = random.nextInt(230);
        g = random.nextInt(230);
        b = random.nextInt(230);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                three[i][j].setBackgroundColor(Color.rgb(r, g, b));
                three[i][j].setTextColor(Color.rgb(r, g, b));
            }
        }
        three[row][column].setBackgroundColor(Color.argb(211, r, g, b));

    } // bonusSetColor()


    /*
    난이도에 따른 레이아웃 변경
     */
    public void levelArray() {

        if (stage < 10) {
            layout3.setVisibility(View.VISIBLE);
            layout4.setVisibility(View.GONE);
            layout5.setVisibility(View.GONE);
            layout6.setVisibility(View.GONE);
            layout7.setVisibility(View.GONE);
        } else if (stage < 20) {
            layout3.setVisibility(View.GONE);
            layout4.setVisibility(View.VISIBLE);
            layout5.setVisibility(View.GONE);
            layout6.setVisibility(View.GONE);
            layout7.setVisibility(View.GONE);
        } else if (stage < 30) {
            layout3.setVisibility(View.GONE);
            layout4.setVisibility(View.GONE);
            layout5.setVisibility(View.VISIBLE);
            layout6.setVisibility(View.GONE);
            layout7.setVisibility(View.GONE);
        } else if (stage < 40) {
            layout3.setVisibility(View.GONE);
            layout4.setVisibility(View.GONE);
            layout5.setVisibility(View.GONE);
            layout6.setVisibility(View.VISIBLE);
            layout7.setVisibility(View.GONE);
        } else {
            layout3.setVisibility(View.GONE);
            layout4.setVisibility(View.GONE);
            layout5.setVisibility(View.GONE);
            layout6.setVisibility(View.GONE);
            layout7.setVisibility(View.VISIBLE);
        }

    } // levelArray()


    /*
    난이도에 따른 색상 차이 조정
     */
    public void levelColor() {

        if (stage % 5 == 0) {
            colorA -= 4;
        }

    } // levelColor()


    /*
    게임 오버
     */
    public void gameOver() {

        ArrayList<Integer> list = new ArrayList<>();
        Tier tier = new Tier();
        Database db = new Database(score);

        getData(list, tier, db);

        finalScoreView.setText("SCORE : " + score);
        gameLayout.setVisibility(View.GONE);
        overLayout.setVisibility(View.VISIBLE);

    } // gameOver()


    /*
    DB의 점수 랭킹 데이터 불러오기
     */
    public void getData(ArrayList<Integer> list, Tier tier, Database db) {

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (!check1) {

                    dataSize = snapshot.child("mode2").getChildrenCount();
                    Log.d(TAG, "dataSize1 : " + dataSize);

                    ref.child("mode2").child(String.valueOf(dataSize+1)).setValue(db);

                    setData(list, tier, db);
                    check1 = true;

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onCancelled: " + error.toException());
            }
        });

    } // getData()


    /*
    DB에 데이터 저장
     */
    public void setData(ArrayList<Integer> list, Tier tier, Database db) {

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (!check2) {

                    dataSize = snapshot.child("mode2").getChildrenCount();
                    Log.d(TAG, "dataSize3 : " + dataSize);

                    for (int i = 1; i <= dataSize; i++) {

                        Database db2 = snapshot.child("mode2").child(String.valueOf(i)).getValue(Database.class);
                        Log.d(TAG, "db.getNum() : " + db2.getScore());
                        list.add(db2.getScore());

                    }

                    tier.checkDB(list, score);
                    tierText.setText(tier.getTier());
                    rankText.setText(String.valueOf(tier.getResult()));

                    check2 = true;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onCancelled: " + error.toException());
            }
        });

    } // setData()


    /*
    제한 시간 세팅
     */
    private void setTime() {

        int i = 1;

        for (i = 1; i <= 6; i++) {


            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    pB_Time.incrementProgressBy(-1);
                    time--;

                    if (time == 0) {
                        if (!status) {
                            MediaPlayer wrongMediaPlayer = new MediaPlayer();
                            wrongMediaPlayer = MediaPlayer.create(Mode2Activity.this, R.raw.wrong);
                            wrongMediaPlayer.setLooping(false);
                            setSound(wrongMediaPlayer);
                            wrongMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mediaPlayer) {
                                    mediaPlayer.release();
                                }
                            });
                            if (sound) {
                                wrongMediaPlayer.start();
                            }
                            gameOver();
                        }
                    }
                }
            }, 1000 * i);

        }


    } // setTime()


    /*
    보너스 스테이지 제한 시간 세팅
     */
    private void setBonusTime() {

        int i = 0;
        pB_BonusTime.setProgress(5);
        for (i = 1; i <= 6; i++) {

            handler2.postDelayed(new Runnable() {
                @Override
                public void run() {

                    pB_BonusTime.incrementProgressBy(-1);
                    time--;
                    if (time == -1) {
                        if (stage % 10 != 0) {
                            correct();
                        }
                        setColor();
                        levelArray();
                        status = false;
                        combo = 0;
                        time = 5;

                        handler.removeMessages(0);
                        setTime();

                        pB_BonusTime.setVisibility(View.GONE);
                        pB_Time.setVisibility(View.VISIBLE);
                    }
                }
            }, 1000 * i);

        }

    } // setBonusTime()


    /*
    음소거 · 해제 세팅
     */
    public void setSound(MediaPlayer mediaPlayer) {

        if (soundStatus.equals("off")) {
            mediaPlayer.setVolume(0,0);
        } else {
            mediaPlayer.setVolume(1,1);
        }

    } // setSound()

}