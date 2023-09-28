package com.example.couch.Interface;

import com.example.couch.Data.RankData;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("couch_GetRank.php")
    Call<ArrayList<RankData>> getRank (
            @Query("modeNum") int modeNum
    );

    @GET("couch_RankInsert.php")
    Call<String> insertRank (
            @Query("nickname") String nickname,
            @Query("score") int score
    );

}

