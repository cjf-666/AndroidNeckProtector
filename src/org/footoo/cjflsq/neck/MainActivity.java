package org.footoo.cjflsq.neck;

import org.footoo.cjflsq.neck.viewpager.MyViewPagerAdapter;
import org.footoo.cjflsq.neck.settings.SettingsActivity;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;

public class MainActivity extends FragmentActivity {
    private MyViewPager mPager;

    MyViewPagerAdapter viewAdapter;
    private ImageView cursor;// 动画图片
    private TextView t1, t2, t3;// 页卡头标
    private ImageView t4;
    private int offset = 0;// 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号
    private int bmpW;// 动画图片宽度
    private GestureDetector gestureDetector;
    private int flaggingWidth;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.activity_main);
	//viewAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
	//gestureDetector = new GestureDetector(new MainViewTouch());
	DisplayMetrics dm = new DisplayMetrics();
	getWindowManager().getDefaultDisplay().getMetrics(dm);
	flaggingWidth = dm.widthPixels / 4;
		
	//mPager = (MyViewPager) findViewById(R.id.mPager);
	//mPager.setAdapter(viewAdapter);
	//mPager.setOnPageChangeListener(new MyPageListener());
	//InitImageView();
	//t1 = (TextView) findViewById(R.id.infoText);
	//t2 = (TextView) findViewById(R.id.settingText);
	//t3 = (TextView) findViewById(R.id.aboutText);
	//t4 = (ImageView) findViewById(R.id.settingsImg);
	//t1.setOnClickListener(new MyTabOnClickListener(0));
	//t2.setOnClickListener(new MyTabOnClickListener(1));
	//t3.setOnClickListener(new MyTabOnClickListener(2));
	//t4.setOnClickListener(new SettingsOnClickListener());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
	//if (gestureDetector.onTouchEvent(event)) {
	   // event.setAction(MotionEvent.ACTION_CANCEL);
	//}
	return super.dispatchTouchEvent(event);
    }
	
    private class MainViewTouch extends SimpleOnGestureListener {
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				   float velocityY) {
	    if (Math.abs(e1.getX() - e2.getX()) > Math.abs(e1.getY()
							   - e2.getY())
		&& (e1.getX() - e2.getX() <= (-flaggingWidth) || e1
		    .getX() - e2.getX() >= flaggingWidth)) {
		if (e1.getX() - e2.getX() >= flaggingWidth) {
		    if (currIndex < 2){
			mPager.setCurrentItem(currIndex + 1);
			return true;
		    }
		}else{
		    if (currIndex > 0){
			mPager.setCurrentItem(currIndex - 1);
			return true;
		    }
		}
	    }
	    return false;
	}
    }
	
    /**
     * 初始化动画
     */
    private void InitImageView() {
	bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.anim)
	    .getWidth();// 获取图片宽度
	DisplayMetrics dm = new DisplayMetrics();
	getWindowManager().getDefaultDisplay().getMetrics(dm);
	int screenW = dm.widthPixels;// 获取分辨率宽度
	offset = (screenW / 3 - bmpW) / 2;// 计算偏移量
	Matrix matrix = new Matrix();
	matrix.postTranslate(offset, 0);
	cursor.setImageMatrix(matrix);// 设置动画初始位置
    }

    class MyTabOnClickListener implements OnClickListener {
	private int index = 0;

	public MyTabOnClickListener(int i) {
	    index = i;
	}

	@Override
	public void onClick(View arg0) {
	    // TODO Auto-generated method stub
	    mPager.setCurrentItem(index);
	}

    }

    class SettingsOnClickListener implements OnClickListener {
	@Override
        public void onClick(View v) {
	    Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
	    startActivity(settingsIntent);
	}
    }

    class MyPageListener implements OnPageChangeListener {

	@Override
	public void onPageSelected(int arg0) {
	    int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
	    int two = one * 2;// 页卡1 -> 页卡3 偏移量
	    Animation animation = null;
	    switch (arg0) {
	    case 0:
		if (currIndex == 1) {
		    animation = new TranslateAnimation(one, 0, 0, 0);
		} else if (currIndex == 2) {
		    animation = new TranslateAnimation(two, 0, 0, 0);
		}
		break;
	    case 1:
		if (currIndex == 0) {
		    animation = new TranslateAnimation(0, one, 0, 0);
		} else if (currIndex == 2) {
		    animation = new TranslateAnimation(two, one, 0, 0);
		}
		break;
	    case 2:
		if (currIndex == 0) {
		    animation = new TranslateAnimation(offset, two, 0, 0);
		} else if (currIndex == 1) {
		    animation = new TranslateAnimation(one, two, 0, 0);
		}
		break;
	    }
	    currIndex = arg0;
	    animation.setFillAfter(true);// True:图片停在动画结束位置
	    animation.setDuration(300);
	    cursor.startAnimation(animation);
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}
    }
}