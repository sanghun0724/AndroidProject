package com.example.administrator.layout;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.administrator.Database;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> implements Filterable {
    private ArrayList<Product_list> product_list;
    private ArrayList<Product_list> filteredproduct_list;
    private Context context;
    private CoordinatorLayout coordinatorLayout;

//    private SharedPreferences sharedPreferences;
    private String PREMIUM = "premium";
    private static boolean isRemove;

    ProductAdapter(ArrayList<Product_list> product_list, Context context, CoordinatorLayout coordinatorLayout) {
        this.product_list = product_list;
        this.filteredproduct_list = product_list;
        this.context = context;
        this.coordinatorLayout = coordinatorLayout;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list, parent, false);
//       sharedPreferences = context.getSharedPreferences("Appstrorage", Context.MODE_PRIVATE);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Product_list product_list=filteredproduct_list.get(position);

        holder.tv_engWord.setText(product_list.getEngWord());
        holder.tv_engPro.setText(product_list.getEngPro());
        holder.tv_korMean.setText(product_list.getKorMean());
        holder.tv_korMean2.setText(product_list.getKorMean2());
        holder.tv_exKor.setText(product_list.getExKor());
        holder.tv_exEng.setText(product_list.getExEng());
        holder.tv_exEngHidden.setText(product_list.getExEngHidden());
        holder.tv_numWord.setText(String.valueOf(product_list.getId()));
        holder.tv_allNumWord.setText(String.valueOf(Database.ALL_ITEM_COUNT));


    }

    @Override
    public int getItemCount() {
        return filteredproduct_list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout bottom, main_upper;
        TextView tv_engWord, tv_engPro, tv_korMean, tv_korMean2, tv_exKor, tv_exEng, tv_exEngHidden, tv_allNumWord, tv_numWord;
        AdView mAdView;
        Button button;
        ImageView fav_btn;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            fav_btn = itemView.findViewById(R.id.bookmark);
            tv_engWord = itemView.findViewById(R.id.tv_engword);
            tv_engPro = itemView.findViewById(R.id.tv_engPro);
            tv_korMean = itemView.findViewById(R.id.tv_korMean);
            tv_korMean2 = itemView.findViewById(R.id.tv_korMean2);
            tv_exKor = itemView.findViewById(R.id.tv_exKor);
            tv_exEng = itemView.findViewById(R.id.tv_exEng);
            tv_exEngHidden = itemView.findViewById(R.id.tv_exEngHidden);
            tv_allNumWord = itemView.findViewById(R.id.tv_allWord);
            tv_numWord = itemView.findViewById(R.id.tv_numWord);

            main_upper = itemView.findViewById(R.id.main_upper);
            bottom = itemView.findViewById(R.id.layout_mainbottom);

        }
    }

}