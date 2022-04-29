package com.baursaq.baursaq.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.baursaq.baursaq.Interface.ItemClickListener;
import com.baursaq.baursaq.R;

public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtRecipeName, txtRecipeDescription;
    public ImageView imageView;
    public ItemClickListener listener;
    public  RecipeViewHolder(View itemView){
        super(itemView);
        imageView = itemView.findViewById(R.id.recipe_image);
        txtRecipeName= itemView.findViewById(R.id.recipe_name);
        txtRecipeDescription= itemView.findViewById(R.id.recipe_description);

    }
    public  void setItemClickListener(ItemClickListener listener){
        this.listener = listener;
    }


    @Override
    public void onClick(View view) {

    }
}
