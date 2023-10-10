package com.example.couch.Data;

import java.util.ArrayList;

public class Tier {

    long rankSize = 0;
    String tier = "";
    double percentage = 0;
    ArrayList<Integer> list = new ArrayList<>();

    public Tier(String tier, double percentage) {

        this.tier = tier;
        this.percentage = percentage;

    }

    public void checkDB(int score, int mode) {

        // DB에 내 점수 등록 후 모든 리스트 읽기.
        // 반복문으로 리스트에 넣고 리스트 정렬(내림차순)
        // 백분율 먼저 구하기
        // 백분율로 티어 구하기
        rankSize = list.size();
        percentage = (list.indexOf(score)+1) / rankSize * 100;

        if (list.indexOf(score) == 0) {
            tier = "goat";
        } else if (score == 0) {
            tier = "stone";
        } else if (percentage <= 4) {
            tier = "challenger";
        } else if (percentage <= 11) {
            tier = "master";
        } else if (percentage <= 23) {
            tier = "diamond";
        } else if (percentage <= 40) {
            tier = "emerald";
        } else if (percentage <= 60) {
            tier = "platinum";
        } else if (percentage <= 77) {
            tier = "gold";
        } else if (percentage <= 89) {
            tier = "silver";
        } else if (percentage <= 96) {
            tier = "bronze";
        } else if (percentage <= 100) {
            tier = "iron";
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
