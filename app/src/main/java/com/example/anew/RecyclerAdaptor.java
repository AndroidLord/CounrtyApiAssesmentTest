package com.example.anew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerAdaptor extends RecyclerView.Adapter<RecyclerAdaptor.ViewHolder> {


   ArrayList<CountryModel> list;
    Context context;

    OnUserItemClickListener onUserItemClickListener;
    public RecyclerAdaptor(ArrayList<CountryModel> contryModelArrayList, Context context) {
        this.list = contryModelArrayList;
        this.context = context;
    }

    public interface OnUserItemClickListener {
        void onItemClick(int position);

    }

    @NonNull
    @Override
    public RecyclerAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdaptor.ViewHolder holder, int position) {

        CountryModel countryModel = list.get(position);

        holder.countryName.setText(countryModel.getCountryName());
        holder.countryCode.setText(countryModel.getCountryCode());

        Glide.with(context).load(countryModel.getImage()).into(holder.image);


        if(SelectedCountry.getInstance().getCountryModelsList().isEmpty()){
            int color = ContextCompat.getColor(context, R.color.white);
            holder.itemView.setBackgroundColor(color);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int color = ContextCompat.getColor(context, R.color.selectedRecyclerView);
                holder.itemView.setBackgroundColor(color);

                SelectedCountry.getInstance().setCountryModelsList(countryModel);
//                onUserItemClickListener.onItemClick(holder.getAdapterPosition());


                if(!SelectedCountry.getInstance().getCountryModelsList().isEmpty()){

                    StringBuilder builder = new StringBuilder();
                    for (int i=0;i<SelectedCountry.getInstance().getCountryModelsList().size();i++){
                        builder.append(SelectedCountry.getInstance().getCountryModelsList().get(i).countries_name).append("\n");
                    }
                    Toast.makeText(context, "Selected Item: " + builder.toString(), Toast.LENGTH_SHORT).show();
                }

            }

         });
    }

    @Override
    public int getItemCount() {

        if(!list.isEmpty()){
            return list.size();
        }

        return 0;
    }



    void updateList(ArrayList<CountryModel> countryModels){
        list.clear();
        list = countryModels;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView countryName, countryCode;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            countryName = itemView.findViewById(R.id.countryName_item);
            countryCode = itemView.findViewById(R.id.countryCode_item);
            image = itemView.findViewById(R.id.image_item);

        }
    }
}
