package com.example.couch.Data;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

public class Tier {

    String TAG = "티어 클래스";
    long rankSize = 0;
    String tier = "";
    double percentage = 0;

    public Tier(String tier, double percentage) {

        this.tier = tier;
        this.percentage = percentage;

    }

    public Tier() {

    }

    public void checkDB(ArrayList<Integer> list, int score) {

        // DB에 내 점수 등록 후 모든 리스트 읽기.
        // 반복문으로 리스트에 넣고 리스트 정렬(내림차순)
        // 백분율 먼저 구하기
        // 백분율로 티어 구하기
        rankSize = list.size();
        Collections.sort(list, Collections.reverseOrder());
        double index = list.indexOf(score) + 1.0;
        percentage = index / rankSize * 100;

        Log.d(TAG, "list.indexOf : " + list.indexOf(score));
        Log.d(TAG, "rankSize : " + rankSize);
        Log.d(TAG, "index / rankSize : " + index / rankSize);

        if (list.indexOf(score) == 0) {
            tier = "GOAT";
            percentage = 0.0;
        } else if (score == 0) {
            tier = "Stone";
            percentage = 100.0;
        } else if (percentage <= 4) {
            tier = "Challenger";
        } else if (percentage <= 11) {
            tier = "Master";
        } else if (percentage <= 23) {
            tier = "Diamond";
        } else if (percentage <= 40) {
            tier = "Emerald";
        } else if (percentage <= 60) {
            tier = "Platinum";
        } else if (percentage <= 77) {
            tier = "Gold";
        } else if (percentage <= 89) {
            tier = "Silver";
        } else if (percentage <= 96) {
            tier = "Bronze";
        } else if (percentage <= 100) {
            tier = "Iron";
        }

    } // checkDB()

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

}
