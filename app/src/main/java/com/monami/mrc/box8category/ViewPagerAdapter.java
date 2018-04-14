package com.monami.mrc.box8category;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {
//    int[] bannerImage;
    ArrayList<Integer> bannerImage = new ArrayList<>();
    LayoutInflater inflater;
    Context context;
    int position = 3;

    public ViewPagerAdapter(Context context, ArrayList<Integer> bannerImage){
        this.context = context;
        this.bannerImage = bannerImage;
    }

    @Override
    public int getCount() {
        return bannerImage.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (RelativeLayout)object;
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.banner_image, container, false);
        ((ViewPager)container).addView(view);
        imageView = (ImageView)view.findViewById(R.id.pager_img);
        imageView.setImageResource(bannerImage.get(position));
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);

    }

//    @Override
//    public float getPageWidth(int position) {
//        return .20f;
//    }
}
