package com.monami.mrc.box8category;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity {
    private Context mContext;
    RelativeLayout mRelativeLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    DataModel categoryDataModel;
    ArrayList<DataModel> al = new ArrayList<>();

    ViewPager viewpager;
    PagerAdapter adapter;
    ImageButton leftNav;
    ImageButton rightNav;
    ArrayList<Integer> bannerImages = new ArrayList<>();
    private static final Integer[] IMAGES = {R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4, R.drawable.img5};
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    private String categoryName;
    private String productName;
    private String productPrice;
    private String productDesc;
    private String productType;
    ArrayList<HashMap<String, String>> categoryDataList;
    ArrayList<DataModel> categoryList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
        ScrollView scrollView = findViewById(R.id.scrollview);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Box8 Home");
        getSupportActionBar().setTitle("Box8 Home");

//        al.add(new DataModel(R.drawable.img1, "Fusion Box"));
//        al.add(new DataModel(R.drawable.img2, "Curries"));
//        al.add(new DataModel(R.drawable.img3, "Biryani"));
//        al.add(new DataModel(R.drawable.img4, "Wraps"));
//        al.add(new DataModel(R.drawable.img5, "Ice Cream"));


        //Load JSON Data
        try{
            JSONObject obj = new JSONObject(loadJsonData());
            JSONObject items_obj = obj.getJSONObject("items");
//            DataModel dataModel = new DataModel();
            Iterator<String> iterator = items_obj.keys();
            while (iterator.hasNext()){
                DataModel dataModel = new DataModel();
                categoryName = iterator.next();
                dataModel.setCategoryName(categoryName);
                categoryList.add(dataModel);

            }


//            JSONArray resultArray = obj.getJSONArray("result");
//
//            categoryDataList = new ArrayList<>();
//            HashMap<String, String> dataMap;
//
//            for(int i = 0; i < resultArray.length(); i++){
//                JSONObject jsonData = resultArray.getJSONObject(i);
////                categoryDataModel = new DataModel();
//
//                categoryName = jsonData.getString("category");
//
////                JSONArray productsArray = jsonData.getJSONArray("products");
////                for(int j = 0; j < productsArray.length(); j++){
////                    JSONObject products_obj = productsArray.getJSONObject(i);
////                    productName = products_obj.getString("name");
////                    productDesc = products_obj.getString("description");
////                    productPrice = String.valueOf(products_obj.getInt("price"));
////                    productType = products_obj.getString("type");
////
////
////                }
//                dataMap = new HashMap<>();
//                dataMap.put("categoryName", categoryName);
////                dataMap.put("productName", productName);
////                dataMap.put("productDesc", productDesc);
////                dataMap.put("productPrice", productPrice);
////                dataMap.put("productType", productType);
//                categoryDataList.add(dataMap);
//
//            }

        }catch (JSONException e){
            e.printStackTrace();
        }



        //Banner Images
        for(int i = 0; i < IMAGES.length; i++){
            bannerImages.add(IMAGES[i]);
        }
        viewpager = (ViewPager) findViewById(R.id.view_pager);
        adapter = new ViewPagerAdapter(mContext, bannerImages);
        viewpager.setAdapter(adapter);
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        leftNav = (ImageButton)findViewById(R.id.left_btn);
        rightNav = (ImageButton)findViewById(R.id.right_btn);
        leftNav.setColorFilter(Color.WHITE);
        rightNav.setColorFilter(Color.WHITE);
        leftNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tab = viewpager.getCurrentItem();
                if (tab > 0) {
                    tab--;
                    viewpager.setCurrentItem(tab);
                } else if (tab == 0) {
                    viewpager.setCurrentItem(tab);
                }
            }
        });
        rightNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tab = viewpager.getCurrentItem();
                tab++;
                viewpager.setCurrentItem(tab);
            }
        });
        indicator.setViewPager(viewpager);
        NUM_PAGES = IMAGES.length;
//        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                currentPage = position;
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
////                Toast.makeText(getApplicationContext(), "context changed", Toast.LENGTH_SHORT).show();
////                if(state == ViewPager.SCROLL_STATE_IDLE){
////                    int pageCount = bannerImages.length;
////                    if(currentPage == 0){
////                        viewpager.setCurrentItem(pageCount - 1, false);
////                    }else if(currentPage == pageCount - 1){
////                        viewpager.setCurrentItem(0, false);
////                    }
////                }
//
//            }
//        });
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            @Override
            public void run() {
                if(currentPage == NUM_PAGES){
                    currentPage = 0;
                }
                viewpager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, 2000, 2000);

        //Category grids
        mRelativeLayout = (RelativeLayout)findViewById(R.id.relative_layout);
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);

//        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        CategoryAdapter categoryAdapter = new CategoryAdapter(getApplicationContext(), al);
//        mRecyclerView.setAdapter(categoryAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 4 ? 2 : 1;

            }
        });



        mRecyclerView.setLayoutManager(gridLayoutManager);
        mAdapter = new CategoryAdapter(mContext, categoryList);
        mRecyclerView.setAdapter(mAdapter);
        scrollView.smoothScrollTo(0, 0);




    }

    //Read Category Data from JSON File
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



}
