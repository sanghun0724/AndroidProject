package com.ogrg.techtown.vocavocca;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.snackbar.Snackbar;
import com.ogrg.techtown.vocavocca.common.DefineValue;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>implements Filterable {
    private ArrayList<Product_List> product_lists;
    private ArrayList<Product_List> filteredProduct_lists;
    private Context context;
    private CoordinatorLayout linearLayout;

    private SharedPreferences pref;
    private String PREMIUM = "premium";
    private static boolean isRemoved;

    ProductAdapter(ArrayList<Product_List> product_lists, Context context, CoordinatorLayout linearLayout) {
        this.product_lists = product_lists;
        this.filteredProduct_lists = product_lists;
        this.context = context;
        this.linearLayout = linearLayout;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list, parent, false);
        pref = context.getSharedPreferences("AppStorage", Context.MODE_PRIVATE);
        isRemoved = pref.getBoolean(PREMIUM,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
//        final Product_List productList = product_lists.get(position);
        final Product_List productList = filteredProduct_lists.get(position);

        holder.tv_engWord.setText(productList.getEngWord());
        holder.tv_engPro.setText(productList.getEngPro());
        holder.tv_korMean.setText(productList.getKorMean());
        holder.tv_korMean2.setText(productList.getKorMean2());
        holder.tv_exKor.setText(productList.getExKor());
        holder.tv_exEng.setText(productList.getExEng());
        holder.tv_exEngHidden.setText(productList.getExEngHidden());
        holder.tv_numWord.setText(String.valueOf(productList.getId()));
        holder.tv_allNumWord.setText(String.valueOf(DefineValue.ALL_ITEM_COUNT));
        holder.mAdView.setBackgroundColor(Color.WHITE);

        if (MainActivity.favoriteDatabase.favoriteDao().isFavorite(productList.getId())==1)
            holder.fav_btn.setImageResource(R.drawable.green_filled_bookmark);
        else
            holder.fav_btn.setImageResource(R.drawable.grean_bookrmark);

        holder.fav_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavoriteList favoriteList = new FavoriteList();

                long a = getItemId(position);
                Log.i("TEST","getItemId / getItemViewType  = " + a );

                int id = productList.getId();
                String engWord = productList.getEngWord();
                String korMean = productList.getKorMean();
                String korMean2 = productList.getKorMean2();
                String exKor = productList.getExKor();
                String exEng = productList.getExEng();
                String exEngHidden = productList.getExEngHidden();


                favoriteList.setId(id);
                favoriteList.setEngWord(engWord);
                favoriteList.setKorMean(korMean);
                favoriteList.setKorMean2(korMean2);
                favoriteList.setExKor(exKor);
                favoriteList.setExEng(exEng);
                favoriteList.setExEngHidden(exEngHidden);
                
                if (MainActivity.favoriteDatabase.favoriteDao().isFavorite(id) != 1) {//fav리스트에 없었으면
                    holder.fav_btn.setImageResource(R.drawable.green_filled_bookmark);//체크표시
                    MainActivity.favoriteDatabase.favoriteDao().addData(favoriteList);//추가하고
                    Snackbar snackbar = Snackbar
                            .make(linearLayout, "\" "+engWord + "\" 을 보관함에 저장하였습니다. ", Snackbar.LENGTH_LONG);
                    snackbar.setAction("보기", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            context.startActivity(new Intent(context, FavoriteActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                            // undo is selected, restore the deleted item
                        }
                    });
//                        View snackbarView = snackbar.getView();
//                        snackbarView.setBackgroundColor(colorId);
//                        TextView textView = snackbarView.findViewById(R.id.snackbar_text);
//                        // set no of text line
//                        textView.setMaxLines(2);
//                        //set text color
//                        textView.setTextColor(colorId);
//                        //set text size
//                        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
                    snackbar.setActionTextColor(Color.parseColor("#05F26C"));
                    snackbar.show();
                    notifyDataSetChanged();

                } else {
                    holder.fav_btn.setImageResource(R.drawable.grean_bookrmark);
                    MainActivity.favoriteDatabase.favoriteDao().delete(favoriteList);
                    notifyDataSetChanged();

                }
            }
        });

        holder.btnFillColor.setOnClickListener(new View.OnClickListener(){
            Animation fillColor = AnimationUtils.loadAnimation(context,R.anim.fill_background);
            int isPressed = 0;
            @Override
            public void onClick(View v) {
                if(isPressed == 0) { //가리기 버튼 눌렀을 때
                    holder.layouBottom.setBackground(context.getDrawable(R.drawable.cover_up));
//                    holder.layouBottom.setBackgroundColor(Color.parseColor("#f86625"));
                    holder.tv_exEng.setVisibility(View.GONE);
                    holder.tv_exEngHidden.setVisibility(View.VISIBLE);
                    holder.layout_mainUpper.setVisibility(View.INVISIBLE);
                    isPressed = 1;
                }else if(isPressed == 1){
                    holder.layouBottom.setBackgroundColor(Color.parseColor("#ffffff"));
                    holder.tv_exEng.setVisibility(View.VISIBLE);
                    holder.tv_exEngHidden.setVisibility(View.GONE);
                    holder.layout_mainUpper.setVisibility(View.VISIBLE);
                    isPressed = 0;
                }
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return filteredProduct_lists.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredProduct_lists = product_lists;
                } else {

                    ArrayList<Product_List> filteredList = new ArrayList<>();
                    for (Product_List row : product_lists) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getEngWord().toLowerCase().contains(charString.toLowerCase()) || row.getKorMean().toLowerCase().contains(charSequence)||row.getKorMean2().toLowerCase().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    filteredProduct_lists = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredProduct_lists;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredProduct_lists = (ArrayList<Product_List>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

     static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layouBottom,layout_mainUpper;
        Button btnFillColor;
        ImageView fav_btn;
        TextView  tv_engWord, tv_engPro,tv_korMean,tv_korMean2,tv_exKor,tv_exEng,tv_exEngHidden, tv_allNumWord, tv_numWord;
        AdView mAdView;

         ViewHolder(@NonNull View itemView){
            super(itemView);
            fav_btn = itemView.findViewById(R.id.fav_btn);
            tv_engWord = itemView.findViewById(R.id.tv_engWord);
            tv_engPro = itemView.findViewById(R.id.tv_engPro);
            tv_korMean = itemView.findViewById(R.id.tv_korMean);
            tv_korMean2 = itemView.findViewById(R.id.tv_korMean2);
            tv_exKor = itemView.findViewById(R.id.tv_exKor);
            tv_exEng = itemView.findViewById(R.id.tv_exEng);
            tv_exEngHidden = itemView.findViewById(R.id.tv_exEngHidden);
            tv_allNumWord = itemView.findViewById(R.id.tv_allWord);
            tv_numWord = itemView.findViewById(R.id.tv_numWord);

//            progressBar = itemView.findViewById(R.id.progressBar);

            btnFillColor = itemView.findViewById(R.id.btn_fillColor);
            layout_mainUpper = itemView.findViewById(R.id.layout_mainUpper);
            layouBottom = itemView.findViewById(R.id.layout_mainBottom);
             MobileAds.initialize(itemView.getContext(), itemView.getResources().getString(R.string.admob_app_id));
             mAdView = itemView.findViewById(R.id.adViewMain);
             AdRequest adRequest = new AdRequest.Builder().build();
             mAdView.loadAd(adRequest);

             if (isRemoved) {
                 mAdView.setVisibility(View.GONE);
             }
        }
    }
}


