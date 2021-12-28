package com.example.manhinhchinh.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manhinhchinh.Activity.BreakFast;
import com.example.manhinhchinh.Activity.MainActivity;
import com.example.manhinhchinh.Activity.DetailActivity;
import com.example.manhinhchinh.Module.FoodModule;
import com.example.manhinhchinh.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FoodAdapter extends  RecyclerView.Adapter<FoodAdapter.UserViewHolder> implements Filterable {

    private Context bConText;
    private List<FoodModule> mListFood;
    private List<FoodModule> mListFoodOld;

    int ketQua = 0;
    int dem = 0;

    public FoodAdapter(Context bConText, List<FoodModule> mListFood) {
        this.bConText = bConText;
        this.mListFood = mListFood;
        this.mListFoodOld = mListFood;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        FoodModule food = mListFood.get(position);
        if (food == null){
            return;
        }
        Picasso.with(bConText).load(food.getPicture()).into(holder.imgFood);
        holder.tvName.setText(food.getFoodName());
        holder.tvCalo.setText(food.getCalories());
        //ban goc
//        holder.layout_item_food.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onClickGoToDetail(food);
//            }
//        });
        holder.layout_food_and_calo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickGoToDetail(food);
            }
        });
        holder.imgFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickGoToDetail(food);
            }
        });

        holder.btnAnSang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.food.add(food);
                dem++;
                Intent intent = new Intent(bConText, MainActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("pass_calo_food", String.valueOf(ketQua));
                bundle1.putString("pass_calo_food", String.valueOf(dem));
                intent.putExtras(bundle1);
                bConText.startActivity(intent);

//                onClickPassFood(food);
            }
        });
    }

    public static void processCar(ArrayList<FoodModule> foods){
        int totalAmount=0;
        for (int i=0; i<foods.size(); i++){
            totalAmount = Integer.parseInt(foods.get(i).getCalories());
        }
    }

    private void onClickGoToDetail(FoodModule food) {
        Intent intent = new Intent(bConText, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_food", food);
        intent.putExtras(bundle);
        bConText.startActivity(intent);
    }
    public void onClickPassFood(FoodModule food) {

        Intent intent1 = new Intent(bConText,MainActivity.class);
        Bundle bundle1 = new Bundle();
//        bundle1.putString("pass_name_food", food.getName());
        bundle1.putString("pass_calo_food", food.getCalories());
        intent1.putExtras(bundle1);
        bConText.startActivity(intent1);
    }


    @Override
    public int getItemCount() {
        if (mListFood != null){
            return mListFood.size();
        }
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout layout_item_food;
        private LinearLayout layout_food_and_calo;
        private CircleImageView imgFood;
        private TextView tvName;
        private TextView tvCalo;
        private Button btnAnSang;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            layout_item_food = itemView.findViewById(R.id.layout_item_food);
            layout_food_and_calo = itemView.findViewById(R.id.layout_food_and_calo);
            imgFood = itemView.findViewById(R.id.img_food);
            tvName = itemView.findViewById(R.id.tv_name);
            tvCalo = itemView.findViewById(R.id.tv_calo);
            btnAnSang = itemView.findViewById(R.id.btnAnSang);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()){
                    mListFood = mListFoodOld;
                } else {
                    List<FoodModule> list = new ArrayList<>();
                    for (FoodModule food : mListFoodOld){
                        if (food.getFoodName().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(food);
                        }
                    }

                    mListFood = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mListFood;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mListFood = (List<FoodModule>) results.values;
                notifyDataSetChanged();
            }
        };
    }

}