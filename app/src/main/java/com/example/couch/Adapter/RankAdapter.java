package com.example.couch.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.couch.Data.RankData;
import com.example.couch.R;

import java.util.ArrayList;


public class RankAdapter extends RecyclerView.Adapter<RankAdapter.ViewHolder> {

    String TAG = "채팅방 어댑터";
    ArrayList<RankData> arrayList;
    Context context;

    @Override
    public RankAdapter.ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ranklist, parent, false);
        RankAdapter.ViewHolder viewHolder = new RankAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RankAdapter.ViewHolder holder,int position) {
        Log.d(TAG, "onBindViewHolder() 호출됨");
        holder.onBind(arrayList.get(holder.getAdapterPosition()));
    }

    public void setItemRankData(ArrayList<RankData> list) {
        Log.d(TAG, "setGameList() 호출됨");

        this.arrayList = list;

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView rankingText;
        TextView nicknameText;
        TextView scoreText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rankingText = itemView.findViewById(R.id.rankRanking_Text);
            nicknameText = itemView.findViewById(R.id.rankScore_Text);
            scoreText = itemView.findViewById(R.id.rankScore_Text);

        }

        void onBind(RankData item) {

            rankingText.setText(String.valueOf(item.getRanking()));
            nicknameText.setText(item.getNickname());
            scoreText.setText(item.getScore());

        } // onBind



    } // ViewHolder

}
