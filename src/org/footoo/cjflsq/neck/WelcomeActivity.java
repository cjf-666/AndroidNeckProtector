package org.footoo.cjflsq.neck;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.*;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import org.footoo.cjflsq.neck.welcomepage.WelcomeFragment1;

import java.util.ArrayList;

public class WelcomeActivity extends FragmentActivity {
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
            imageView.setLayoutParams(new LayoutParams(20, 10));
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

        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        viewPager.setOnPageChangeListener(new MyListener());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        /*if (gestureDetector.onTouchEvent(event)) {
            event.setAction(MotionEvent.ACTION_CANCEL);
        }*/
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


    class MyAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragments = new ArrayList<Fragment>();

        @Override
        public int getCount() {
            return fragments.size();  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public void finishUpdate(ViewGroup container) {
            super.finishUpdate(container);    //To change body of overridden methods use File | Settings | File Templates.
        }

        public MyAdapter(FragmentManager fm) {
            super(fm);    //To change body of overridden methods use File | Settings | File Templates.
            WelcomeFragment1 a = new WelcomeFragment1();
            fragments.add(a);
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return super.isViewFromObject(view, object);    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
            super.restoreState(state, loader);    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public Parcelable saveState() {
            return super.saveState();    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public void startUpdate(ViewGroup container) {
            super.startUpdate(container);    //To change body of overridden methods use File | Settings | File Templates.
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

    void GoToMainActivity() {
        Intent i = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
