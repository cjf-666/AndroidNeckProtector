package org.footoo.cjflsq.neck;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;

public class WelcomeActivity extends Activity {
    ViewPager viewPager;
    ArrayList<View> list;
    ViewGroup main, group;
    ImageView imageView;
    ImageView[] imageViews;
    int currentitem = 0;
    private int flaggingWidth;
    private GestureDetector gestureDetector;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        gestureDetector = new GestureDetector(new WelcomeViewTouch());
        LayoutInflater inflater = getLayoutInflater();
        list = new ArrayList<View>();
        list.add(inflater.inflate(R.layout.welcome_item1, null));
        list.add(inflater.inflate(R.layout.welcome_item2, null));
        list.add(inflater.inflate(R.layout.welcome_item3, null));
        list.add(inflater.inflate(R.layout.welcome_item4, null));



        imageViews = new ImageView[list.size()];
        ViewGroup main = (ViewGroup) inflater.inflate(R.layout.welcome_page, null);
        // group是R.layou.main中的负责包裹小圆点的LinearLayout.
        ViewGroup group = (ViewGroup) main.findViewById(R.id.viewGroup);

        viewPager = (ViewPager) main.findViewById(R.id.viewPager);

        for (int i = 0; i < list.size(); i++) {
            imageView = new ImageView(WelcomeActivity.this);
            imageView.setLayoutParams(new LayoutParams(20,10));
            imageView.setPadding(10, 0, 10, 0);
            imageViews[i] = imageView;
            if (i == 0) {
                // 默认进入程序后第一张图片被选中;
                imageViews[i].setBackgroundResource(R.drawable.guide_dot_white);
            } else {
                imageViews[i].setBackgroundResource(R.drawable.guide_dot_white);
            }
            group.addView(imageView);
        }

        setContentView(main);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        flaggingWidth = dm.widthPixels / 3;

        viewPager.setAdapter(new MyAdapter());
        viewPager.setOnPageChangeListener(new MyListener());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (gestureDetector.onTouchEvent(event)) {
            event.setAction(MotionEvent.ACTION_CANCEL);
        }
        return super.dispatchTouchEvent(event);
    }

    private class WelcomeViewTouch extends SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            if (currentitem == list.size() - 1) {
                if (Math.abs(e1.getX() - e2.getX()) > Math.abs(e1.getY()
                        - e2.getY())
                        && (e1.getX() - e2.getX() <= (-flaggingWidth) || e1
                        .getX() - e2.getX() >= flaggingWidth)) {
                    if (e1.getX() - e2.getX() >= flaggingWidth) {
                        GoToMainActivity();
                        return true;
                    }
                }
            }
            return false;
        }
    }


    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getItemPosition(Object object) {
            // TODO Auto-generated method stub
            return super.getItemPosition(object);
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            // TODO Auto-generated method stub
            ((ViewPager) arg0).removeView(list.get(arg1));
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            // TODO Auto-generated method stub
            ((ViewPager) arg0).addView(list.get(arg1));
            return list.get(arg1);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
            // TODO Auto-generated method stub

        }

        @Override
        public Parcelable saveState() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void finishUpdate(View arg0) {
            // TODO Auto-generated method stub

        }
    }

    class MyListener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int arg0) {
            for (int i = 0; i < imageViews.length; i++) {
                imageViews[arg0]
                        .setBackgroundResource(R.drawable.guide_dot_white);
                if (arg0 != i) {
                    imageViews[i]
                            .setBackgroundResource(R.drawable.guide_dot_white);
                }
            }
            currentitem = arg0;
        }

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            GoToMainActivity();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    void GoToMainActivity(){
        finish();
    }
}
