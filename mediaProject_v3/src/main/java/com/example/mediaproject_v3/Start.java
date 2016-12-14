package com.example.mediaproject_v3;




import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class Start extends Activity {

    private ViewPager myViewPager;

    private List<View> list;

    private TextView dot1,dot2,dot3;

    private Button startButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.start);
        initDot();
        initViewPager();
    }

    private void initDot(){
        dot1=(TextView) this.findViewById(R.id.textView1);
        dot2=(TextView) this.findViewById(R.id.textView2);
        dot3=(TextView) this.findViewById(R.id.textView3);
    }

    @SuppressWarnings("deprecation")
	@SuppressLint("InflateParams")
	private void initViewPager(){
        myViewPager=(ViewPager) this.findViewById(R.id.viewPager);
        list=new ArrayList<View>();

        LayoutInflater inflater=getLayoutInflater();

        View view =inflater.inflate(R.layout.lay3, null);

        list.add(inflater.inflate(R.layout.lay1, null));
        list.add(inflater.inflate(R.layout.lay2, null));
        list.add(view);

        myViewPager.setAdapter(new MyPagerAdapter(list));

        myViewPager.setOnPageChangeListener(new MyPagerChangeListener());

        startButton=(Button) view.findViewById(R.id.start);

        startButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
            	finish();
            }
        });


    }

    class MyPagerAdapter extends PagerAdapter {
        public List<View> mListViews;

        public MyPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(mListViews.get(arg1));
        }

        @Override
        public void finishUpdate(View arg0) {
        }

        @Override
        public int getCount() {
            return mListViews.size();
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(mListViews.get(arg1), 0);
            return mListViews.get(arg1);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == (arg1);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
        }
    }

    class MyPagerChangeListener implements OnPageChangeListener{


        @Override
        public void onPageSelected(int arg0) {
            // TODO Auto-generated method stub
            switch (arg0) {
            case 0:
                dot1.setTextColor(Color.WHITE);
                dot2.setTextColor(Color.BLACK);
                dot3.setTextColor(Color.BLACK);
                break;

            case 1:
                dot1.setTextColor(Color.BLACK);
                dot2.setTextColor(Color.WHITE);
                dot3.setTextColor(Color.BLACK);
                break;

            case 2:
                dot1.setTextColor(Color.BLACK);
                dot2.setTextColor(Color.BLACK);
                dot3.setTextColor(Color.WHITE);
                break;

            }
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }



    }
}