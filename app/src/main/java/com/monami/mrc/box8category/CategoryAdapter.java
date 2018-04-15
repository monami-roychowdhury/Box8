package com.monami.mrc.box8category;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryDataHolder>{

    private ArrayList<DataModel> categoryList;
    private Context context;
    private String PLACEHOLDER_IMAGE_URL = "http://via.placeholder.com/600x600.png";
    private static MyClickListener myClickListener;
    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public CategoryAdapter(Context context,ArrayList<DataModel> categoryList){
        this.context = context;
        this.categoryList = categoryList;
    }
    public class CategoryDataHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
         TextView categoryName;
         ImageView categoryImage;
         Context mContext;
         public CategoryDataHolder(final View itemView){
             super(itemView);
             categoryName = (TextView)itemView.findViewById(R.id.category_name);
             categoryImage = (ImageView)itemView.findViewById(R.id.category_img);
             itemView.setOnClickListener(this);
         }
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Context context = view.getContext();
            Intent intent = new Intent(context, ProductsActivity.class);
            intent.putExtra("categoryName", categoryList.get(position).getCategoryName());
            context.startActivity(intent);
        }

    }
    @Override
    public CategoryAdapter.CategoryDataHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_grid,parent,false);
        CategoryDataHolder vh = new CategoryDataHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CategoryDataHolder holder, final int position){

        holder.categoryName.setText(categoryList.get(position).getCategoryName());
        Picasso.get().load(PLACEHOLDER_IMAGE_URL.toString()).into(holder.categoryImage);

    }
    @Override
    public int getItemCount(){
        return categoryList.size();
    }
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
    public interface MyClickListener {
        public void onItemClick(View v, int position);
    }
}

