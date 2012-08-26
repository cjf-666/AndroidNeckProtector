package org.footoo.cjflsq.neck;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class TestViewPager extends ViewPager{
	
	public TestViewPager(Context context) {
		super(context);
	}
	
	public TestViewPager(Context context,AttributeSet paramAttributeSet){
		super(context, paramAttributeSet);
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		return false;
	}
}
