package com.ogrg.techtown.vocavocca;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private List<FavoriteList> favoriteLists;
    Context context;
    int standard = 0;//정상, 가리기 1

    //생성자
    public FavoriteAdapter(List<FavoriteList> favoriteLists, Context context) {
        this.favoriteLists = favoriteLists;
        this.context = context;
    }

    // 기본으로 제공되는 오버라이딩 메서드
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_list, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final FavoriteList fl = favoriteLists.get(position);
        holder.engWord.setText(fl.getEngWord());
        holder.korMean.setText(fl.getKorMean());
        holder.korMean2.setText(fl.getKorMean2());

    }

    @Override
    public int getItemCount() {
        return favoriteLists.size();
    }

    public void removeItem(int position) {
        final FavoriteList fl = favoriteLists.get(position);
        MainActivity.favoriteDatabase.favoriteDao().delete(fl);

        favoriteLists.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView engWord, korMean, korMean2;
        RelativeLayout viewBackground, viewForeground;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
                engWord = itemView.findViewById(R.id.fav_tv_engWord);
                korMean = itemView.findViewById(R.id.fav_tv_korMean);
                korMean2 = itemView.findViewById(R.id.fav_tv_korMean2);

                viewBackground = itemView.findViewById(R.id.view_background);
                viewForeground = itemView.findViewById(R.id.view_foreground);
        }
    }
}
