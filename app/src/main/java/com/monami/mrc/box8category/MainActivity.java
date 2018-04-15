package com.monami.mrc.box8category;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

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


        //Load JSON Data
        try{
            JSONObject obj = new JSONObject(loadJsonData());
            JSONObject items_obj = obj.getJSONObject("items");

            Iterator<String> iterator = items_obj.keys();
            while (iterator.hasNext()){
                DataModel dataModel = new DataModel();
                categoryName = iterator.next();
                dataModel.setCategoryName(categoryName);
                categoryList.add(dataModel);
            }        }catch (JSONException e){
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
