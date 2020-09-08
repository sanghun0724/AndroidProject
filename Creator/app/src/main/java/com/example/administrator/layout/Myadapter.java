package com.example.administrator.layout;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.StringDef;
import androidx.recyclerview.widget.RecyclerView;

 public class Myadapter extends RecyclerView.Adapter<Myadapter.ViewHolder> {
     final int REQUEST_CODE=1;
     private Context mcontext;
     private ArrayList<Item> item;
     private int lastPosition = -1;
     private AdapterView HI;
      int ID =111;
      private AdapterView.OnItemClickListener mListerner=null;

     public Myadapter(Context context, ArrayList items) {
         this.mcontext = context;
         this.item = items;
     }

     public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
         this.mListerner=listener;
     }


     @NonNull
     @Override
     public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
         ViewHolder viewHolder = new ViewHolder(view);
         return viewHolder;
     }
     @Override
     public int getItemCount() {
         return item.size();
     }
     @Override
     public void onBindViewHolder(ViewHolder holder, final int position) {

         holder.imageView1.setImageResource(item.get(position).image1);
         holder.imageView2.setImageResource(item.get(position).image2);
         holder.name.setText(item.get(position).name);
         holder.comapny.setText(item.get(position).company);
         holder.imageButton.
            setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                Intent intent =new Intent(v.getContext(),Ceo.class);
                intent.putExtra("NUMBER",position);
                intent.putExtra("NAME",item.get(position).getName());
                intent.putExtra("COMPANY",item.get(position).getCompany());
                intent.putExtra("BIG",item.get(position).getImage1());
                intent.putExtra("SMALL",item.get(position).getImage2());
                v.getContext().startActivity(intent);

                  //Toast.makeText(v.getContext(), "클릭 되었습니다.", Toast.LENGTH_SHORT).show();
              }
          });


         setAnimation(holder.imageView1, position);
         setAnimation(holder.imageView2, position);
         setAnimation(holder.name, position);
         setAnimation(holder.comapny, position);
         setAnimation(holder.imageButton,position);
     }



     private void setAnimation(View viewToAnimate, int position) {
         if (position > lastPosition) {
             Animation animation = AnimationUtils.loadAnimation(mcontext, android.R.anim.slide_in_left);
             viewToAnimate.startAnimation(animation);
             lastPosition = position;
         }
     }

   public class ViewHolder extends RecyclerView.ViewHolder {
         public ImageView imageView1;
         public ImageView imageView2;
         public TextView name;
         public TextView comapny;
         public ImageButton imageButton;


         public ViewHolder(View view) {
             super(view);
             imageButton=(ImageButton)view.findViewById(R.id.button_view);
             imageView1 = (ImageView) view.findViewById(R.id.character);
             imageView2 = (ImageView) view.findViewById(R.id.small_man);
             name = (TextView) view.findViewById(R.id.tex1);
             comapny = (TextView) view.findViewById(R.id.tex2);


         }

     }
 }

