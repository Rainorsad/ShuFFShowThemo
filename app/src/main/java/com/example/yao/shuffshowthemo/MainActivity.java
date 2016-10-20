package com.example.yao.shuffshowthemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.jude.rollviewpager.RollPagerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager vp;
    private List<View> views;
    private View v1,v2,v3,v4,v5;
    private Handler handler;
    int index=0;//0是从左往右，1是从右往左
    private static final int[] imags = new int[]{R.drawable.pikaqiu,R.drawable.bbb,R.drawable.ccc,R.drawable.dddd,R.drawable.eee};

    private RollPagerView roo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vp = (ViewPager) findViewById(R.id.viewpager);

        views = new ArrayList<>();
        LayoutInflater lay = getLayoutInflater();
        v1 = lay.inflate(R.layout.item_onelayout,null);
        v2 = lay.inflate(R.layout.item_twolayout,null);
        v3 = lay.inflate(R.layout.item_threelayout,null);
        v4 = lay.inflate(R.layout.item_fourlayout,null);
        v5 = lay.inflate(R.layout.item_fivelayout,null);

        views.add(v1);
        views.add(v2);
        views.add(v3);
        views.add(v4);
        views.add(v5);

        vp.setAdapter(new MyPageAdapter());
//        boolean b = handler.sendMessageDelayed(0, 3000);

        if (handler==null){
            handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    int i = vp.getCurrentItem();
                    if (index==0){
                        if (i<imags.length){
                            i++;
                            if (i==imags.length){
                                index=1;
                            }
                        }
                    }else {
                        if (i>0){
                            i--;
                            if (i==0){
                                index=0;
                            }
                        }
                    }
                    vp.setCurrentItem(i);
                    handler.sendEmptyMessageDelayed(1,1000);
                }
            };
            handler.sendEmptyMessageDelayed(1,1000);
        }
    }

    class MyPageAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return imags.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));

            View v = views.get(position);
            v.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()){
                        case MotionEvent.ACTION_DOWN: //按下
                            handler.removeCallbacksAndMessages(null);//删除handler中所有消息和回调
                            break;
                        case MotionEvent.ACTION_UP: //松开
                            handler.sendEmptyMessageDelayed(1,1000);
                            break;
                        case MotionEvent.ACTION_CANCEL: //滑动
                            handler.sendEmptyMessageDelayed(1,1000);
                            break;
                    }
                    return true;
                }
            });
            return views.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
