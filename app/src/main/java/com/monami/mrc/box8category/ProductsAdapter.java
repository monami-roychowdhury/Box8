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
public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsDataHolder>{
    //    private ArrayList arrayList;

    private ArrayList<DataModel> productList;
    private Context context;
    private String PLACEHOLDER_IMAGE_URL = "http://via.placeholder.com/950x400.png";
    private static MyClickListener myClickListener;
    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public ProductsAdapter(Context context,ArrayList<DataModel> productList){
        this.context = context;
        this.productList = productList;
    }
    public class ProductsDataHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView productName;
        ImageView productImage;
        TextView productDesc;
        TextView productPrice;
        TextView productType;
        ImageView productTypeImage;
        Context mContext;
        public ProductsDataHolder(final View itemView){
            super(itemView);
            productName = (TextView)itemView.findViewById(R.id.product_name);
            productImage = (ImageView)itemView.findViewById(R.id.product_img);
            productDesc = (TextView)itemView.findViewById(R.id.product_desc);
            productPrice = (TextView)itemView.findViewById(R.id.product_price);
            productTypeImage = (ImageView)itemView.findViewById(R.id.product_type_img);


        }
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Context context = view.getContext();
            Intent intent = new Intent(context, ProductsActivity.class);
            intent.putExtra("categoryName", productList.get(position).getCategoryName());
            context.startActivity(intent);
        }

    }
    @Override
    public ProductsAdapter.ProductsDataHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items,parent,false);
        ProductsDataHolder vh = new ProductsDataHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ProductsDataHolder holder, final int position){        holder.productName.setText(productList.get(position).getProductName());
        holder.productDesc.setText(productList.get(position).getProductDesc());
        holder.productPrice.setText(productList.get(position).getProductPrice());

        Picasso.get().load(PLACEHOLDER_IMAGE_URL.toString()).into(holder.productImage);
        if(productList.get(position).getProductType().equals("veg")){
            Picasso.get().load(R.drawable.veg).into(holder.productTypeImage);
        }else{
            Picasso.get().load(R.drawable.nonveg).into(holder.productTypeImage);
        }

    }
    @Override
    public int getItemCount(){
        return productList.size();
    }
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
    public interface MyClickListener {
        public void onItemClick(View v, int position);
    }
}
