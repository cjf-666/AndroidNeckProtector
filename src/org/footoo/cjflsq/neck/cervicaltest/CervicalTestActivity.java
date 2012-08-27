package org.footoo.cjflsq.neck.cervicaltest;

import org.footoo.cjflsq.neck.viewpager.TestPagerAdapter;
import org.footoo.cjflsq.neck.userscore.ScoreManager;
import org.footoo.cjflsq.neck.R;

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
import android.content.Intent;
import android.view.Window;
import android.widget.Button;

public class CervicalTestActivity extends FragmentActivity {
    private TestViewPager mPager;

    TestPagerAdapter mPagerAdapter;

    private int offset = 0;// 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号
    private int bmpW;// 动画图片宽度
    private GestureDetector gestureDetector;

    private int[] score;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.test_activity_layout);
	
	Button mButton = (Button) findViewById(R.id.issue_yes_button);
	mButton.setOnClickListener(new ButtonOnClickListener(1));
        mButton = (Button) findViewById(R.id.issue_no_button);
	mButton.setOnClickListener(new ButtonOnClickListener(0));
	
	initViewPager();
    }

    private void initViewPager() {
	mPagerAdapter = new TestPagerAdapter(getSupportFragmentManager());
	gestureDetector = new GestureDetector(new MainViewTouch());
		
	mPager = (TestViewPager) findViewById(R.id.mPager);
	mPager.setAdapter(mPagerAdapter);
	mPager.setOnPageChangeListener(new MyPageListener());
	mPager.setCurrentItem(currIndex);

	score = new int[30];
	for (int i = 0; i <= 14; i++) {
	    score[i] = 2;
	}
	for (int i = 15; i <= 24; i++) {
	    score[i] = 4;
	}
	for (int i = 25; i <= 29; i++) {
	    score[i] = 6;
	}
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
	if (gestureDetector.onTouchEvent(event)) {
	    event.setAction(MotionEvent.ACTION_CANCEL);
	}
	return super.dispatchTouchEvent(event);
    }
	
    private class ButtonOnClickListener implements OnClickListener {
	private int index;
	
	public ButtonOnClickListener(int num) {
	    index = num;
	}
	
	@Override
        public void onClick(View v) {
	    if (index == 1) {
		new ScoreManager(getString(R.string.cervical_test_score_filename).toString()).deductScore(score[currIndex]);
	    }
	    mPager.setCurrentItem(currIndex + 1);
	}    
    }

    private class MainViewTouch extends SimpleOnGestureListener {
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
	    return false;
	}
    }

    private class MyPageListener implements OnPageChangeListener {

	@Override
	public void onPageSelected(int arg0) {
	    currIndex = arg0;
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}
    }
}