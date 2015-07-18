
package com.example.menudemo;

import com.example.menudemo.indicator.CirclePageIndicator;
import com.example.menudemo.indicator.CirclePageIndicator.IndicatorItem;

import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MenuDialog extends DialogFragment {

    private ViewPager viewpager;

    private CirclePageIndicator tabStrip;
    
    private List<IndicatorItem> indicatorItems = new ArrayList<IndicatorItem>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getDialog() != null) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        IndicatorItem item1 = new IndicatorItem();
        IndicatorItem item2 = new IndicatorItem();
        IndicatorItem item3 = new IndicatorItem();
        item1.setLabel("歌曲");
        item2.setLabel("歌词");
        item3.setLabel("写真");
        indicatorItems.add(item1);
        indicatorItems.add(item2);
        indicatorItems.add(item3);
        
        View root = inflater.inflate(R.layout.menu_layout, container, false);
        viewpager = (ViewPager) root.findViewById(R.id.viewPager);
        viewpager.setAdapter(new MenuPageAdapter());
        tabStrip = (CirclePageIndicator) root.findViewById(R.id.tabstrip);
        tabStrip.setViewPager(viewpager);
        tabStrip.setIndicatorList(indicatorItems);
        return root;

    }

    @SuppressWarnings("deprecation")
    @Override
    public void onStart() {
        super.onStart();

        // change dialog width
        if (getDialog() != null) {

            int fullWidth = getDialog().getWindow().getAttributes().width;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                Display display = getActivity().getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                fullWidth = size.x;
            } else {
                Display display = getActivity().getWindowManager().getDefaultDisplay();
                fullWidth = display.getWidth();
            }

            final int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                    .getDisplayMetrics());

            int w = fullWidth - padding;
            int h = getDialog().getWindow().getAttributes().height;

            getDialog().getWindow().setLayout(w, h);
        }
    }
    
    public class MenuPageAdapter extends PagerAdapter {

        private final int[] ICONS = {
                R.drawable.ic_launcher_gplus, R.drawable.ic_launcher_gmail,
                R.drawable.ic_launcher_gmaps
        };

        @Override
        public int getCount() {
            return ICONS.length;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object view) {
            container.removeView((View) view);
        }

        @Override
        public boolean isViewFromObject(View v, Object o) {
            return v == ((View) o);
        }

        @Override
        public Object instantiateItem(View container, int position) {
            ImageView v = new ImageView(getActivity());
//            v.setText("PAGE " + (position + 1));
            v.setImageResource(ICONS[position]);
            v.setScaleType(ScaleType.CENTER_INSIDE);
//            v.setGravity(Gravity.CENTER);
//            v.setTextColor(Color.WHITE);
            ((ViewGroup)container).addView(v);
            return v;
        }

    }

}
