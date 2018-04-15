package com.monami.mrc.box8category;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
public class ProductsActivity extends AppCompatActivity {
    String categoryName;
    private String productName;
    private String productPrice;
    private String productDesc;
    private String productType;
    private String categoryNameSelected;
    ArrayList<HashMap<String, String>> productsDataList;
    ArrayList<DataModel> productList = new ArrayList<>();
    DataModel dataModel;
    ProductsAdapter productsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.products_relativelayout);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.products_recyclerview);
        categoryNameSelected = getIntent().getStringExtra("categoryName");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Menu");
        getSupportActionBar().setTitle("Menu");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        try{
            JSONObject obj = new JSONObject(loadJsonData());
            JSONObject items_obj = obj.getJSONObject("items");

            if(items_obj.has(categoryNameSelected)){
                JSONArray products_array = items_obj.getJSONArray(categoryNameSelected);
                for(int i = 0; i < products_array.length(); i++){
                    JSONObject product_obj = products_array.getJSONObject(i);
                    dataModel = new DataModel();
                    productName = product_obj.getString("name");
                    productDesc = product_obj.getString("description");
                    productPrice = String.valueOf(product_obj.getInt("price"));
                    productType = product_obj.getString("type");
                    dataModel.setProductName(productName);
                    dataModel.setProductDesc(productDesc);
                    dataModel.setProductPrice(productPrice);
                    dataModel.setProductType(productType);
                    productList.add(dataModel);
                }

            }        }catch (JSONException e){
            e.printStackTrace();
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        productsAdapter = new ProductsAdapter(this, productList);
        recyclerView.setAdapter(productsAdapter);

    }
    //Read Products Data from JSON File
    public String loadJsonData(){
        String json = null;
        try{
            InputStream inputStream = getAssets().open("categorydata.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
        return json;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
