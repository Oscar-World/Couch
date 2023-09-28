package com.example.couch.Data;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RankData {

    @Expose
    @SerializedName("modeNum") public int modeNum;

    @Expose
    @SerializedName("ranking") public int ranking;

    @Expose
    @SerializedName("nickname") public String nickname;

    @Expose
    @SerializedName("score") public int score;


    public RankData(int modeNum, int ranking, String nickname, int score) {
        this.modeNum = modeNum;
        this.ranking = ranking;
        this.nickname = nickname;
        this.score = score;
    }

    public int getModeNum() {
        return modeNum;
    }

    public void setModeNum(int modeNum) {
        this.modeNum = modeNum;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
