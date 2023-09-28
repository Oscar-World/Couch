package com.example.couch.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.couch.Adapter.RankAdapter;
import com.example.couch.Data.RankData;
import com.example.couch.Interface.ApiClient;
import com.example.couch.Interface.ApiInterface;
import com.example.couch.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RankActivity extends AppCompatActivity {

    String TAG = "랭킹 액티비티";

    TextView mode1OnText;
    TextView mode2OnText;
    TextView mode3OnText;
    TextView mode1OffText;
    TextView mode2OffText;
    TextView mode3OffText;

    RecyclerView recyclerView;
    ArrayList<RankData> arrayList;
    RankAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

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

        mode1OnText = findViewById(R.id.rankMode1On_Text);
        mode2OnText = findViewById(R.id.rankMode2On_Text);
        mode3OnText = findViewById(R.id.rankMode3On_Text);
        mode1OffText = findViewById(R.id.rankMode1Off_Text);
        mode2OffText = findViewById(R.id.rankMode1Off_Text);
        mode3OffText = findViewById(R.id.rankMode1Off_Text);

        recyclerView = findViewById(R.id.rank_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        arrayList = new ArrayList<>();
        adapter = new RankAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setItemRankData(arrayList);

    } // setVariable()


    public void setView() {

        mode1OffText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickMode1();
            }
        });


        mode2OffText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickMode2();
            }
        });


        mode3OffText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickMode3();
            }
        });


    } // setView()


    public void onClickMode1() {

        mode1OffText.setVisibility(View.GONE);
        mode1OnText.setVisibility(View.VISIBLE);
        mode2OffText.setVisibility(View.VISIBLE);
        mode2OnText.setVisibility(View.GONE);
        mode3OffText.setVisibility(View.VISIBLE);
        mode3OnText.setVisibility(View.GONE);

        getRank(1);

    } // onClickMode1()


    public void onClickMode2() {

        mode1OffText.setVisibility(View.VISIBLE);
        mode1OnText.setVisibility(View.GONE);
        mode2OffText.setVisibility(View.GONE);
        mode2OnText.setVisibility(View.VISIBLE);
        mode3OffText.setVisibility(View.VISIBLE);
        mode3OnText.setVisibility(View.GONE);

        getRank(2);

    } // onClickMode2()


    public void onClickMode3() {

        mode1OffText.setVisibility(View.VISIBLE);
        mode1OnText.setVisibility(View.GONE);
        mode2OffText.setVisibility(View.VISIBLE);
        mode2OnText.setVisibility(View.GONE);
        mode3OffText.setVisibility(View.GONE);
        mode3OnText.setVisibility(View.VISIBLE);

        getRank(3);

    } // onClickMode3()

    public void getRank (int modeNum) {

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ArrayList<RankData>> call = apiInterface.getRank(modeNum);
        call.enqueue(new Callback<ArrayList<RankData>>() {
            @Override
            public void onResponse(Call<ArrayList<RankData>> call, Response<ArrayList<RankData>> response) {

                if (response.isSuccessful()) {
                    Log.d(TAG, "getRank - onResponse isSuccessful");

                    ArrayList<RankData> data = response.body();

                    if (data.size() > 0) {

                        for (int i = 0; i <data.size(); i++) {

                            int modeNum = data.get(i).getModeNum();
                            int ranking = data.get(i).getRanking();
                            String nickname = data.get(i).getNickname();
                            int score = data.get(i).getScore();

                            RankData rankData = new RankData(modeNum, ranking, nickname, score);

                            arrayList.add(rankData);

                        }
                        recyclerView.setVisibility(View.VISIBLE);
                        adapter.setItemRankData(arrayList);


                    } else {
                        Log.d(TAG, "data.size() == 0");
                    }

                } else {
                    Log.d(TAG, "getRank - onResponse isFailure");
                }

            }

            @Override
            public void onFailure(Call<ArrayList<RankData>> call, Throwable t) {
                Log.d(TAG, "getRank - onFailure");
            }
        });
    }


}