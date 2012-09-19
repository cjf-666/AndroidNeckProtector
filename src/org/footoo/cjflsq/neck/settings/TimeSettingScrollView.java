package org.footoo.cjflsq.neck.settings;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class TimeSettingScrollView extends HorizontalScrollView {

    /* 当前控件 */
    private TimeSettingScrollView me;

    /* HomePage */
    private LinearLayout homePage;

    /* 菜单状态 */
    public static boolean seekBarOut;

    /* 扩展宽度 */
    private final int ENLARGE_WIDTH = 70;

    /* 菜单的宽度 */
    private int seekBarWidth;

    /* 手势动作最开始时的x坐标 */
    private float lastMotionX = -1;

    /* 按钮 */
    private Button scrollBtn;

    /* 当前滑动的位置 */
    private int current;

    private int scrollToViewPos;

    public TimeSettingScrollView(Context context) {
        super(context);
        init();
    }

    public TimeSettingScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TimeSettingScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setHorizontalFadingEdgeEnabled(false);
        setVerticalFadingEdgeEnabled(false);
        me = this;
        me.setVisibility(View.INVISIBLE);
        seekBarOut = true;
    }

    public void initViews(View[] children, SizeCallBack sizeCallBack, LinearLayout homePage) {
        this.homePage = homePage;
        ViewGroup parent = (ViewGroup) getChildAt(0);

        for (int i = 0; i < children.length; i++) {
            children[i].setVisibility(View.INVISIBLE);
            parent.addView(children[i]);
        }

        OnGlobalLayoutListener onGlLayoutistener = new SeekBarOnGlobalLayoutListener(parent, children, sizeCallBack);
        getViewTreeObserver().addOnGlobalLayoutListener(onGlLayoutistener);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //Log.e("caojingfan", "onInterceptTouchEvent");
        return false;
    }

    /**
     * 设置按钮
     *
     * @param btn
     */
    public void setTimeBtn(Button btn) {
        scrollBtn = btn;
    }

    public void clickTimeBtn() {

        if (!seekBarOut) {
            seekBarWidth = 0;
        } else {
            seekBarWidth = homePage.getMeasuredWidth() - scrollBtn.getMeasuredWidth() - ENLARGE_WIDTH;
        }
        seekBarSlide();
    }

    /**
     * 滑动出菜单
     */
    private void seekBarSlide() {

        if (seekBarWidth == 0) {
            seekBarOut = true;
        } else {
            seekBarOut = false;
        }
        me.smoothScrollTo(seekBarWidth, 0);
     /* if (seekBarOut == true) {
            this.scrollBtn.setBackgroundResource(R.drawable.menu_fold);
	} else {
	    this.scrollBtn.setBackgroundResource(R.drawable.menu_unfold);
	}*/
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        //Log.v("caojingfan", new Integer(l).toString());
        super.onScrollChanged(l, t, oldl, oldt);
        if (l < (homePage.getMeasuredWidth() - scrollBtn.getMeasuredWidth() - ENLARGE_WIDTH) / 2) {
            seekBarWidth = 0;
        } else {
            //Log.v("caojingfan", new Integer(homePage.getWidth()).toString());
            seekBarWidth = homePage.getWidth() - scrollBtn.getMeasuredWidth() - ENLARGE_WIDTH;
        }
        this.current = l;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //Log.e("caojingfan", "onTouchEvent");
        int x = (int) ev.getRawX();

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
	    /* 手指按下时的x坐标 */
            lastMotionX = (int) ev.getRawX();
        }
        if ((current == 0 && x < scrollToViewPos) || (current == scrollToViewPos * 2 && x > ENLARGE_WIDTH)) {
            return false;
        }
        if (seekBarOut == false && lastMotionX > 20) {
            return true;
        } else {
            if (ev.getAction() == MotionEvent.ACTION_UP) {
                seekBarSlide();
                return false;
            }
        }
        return super.onTouchEvent(ev);
    }

    /****************************************************/
    /*-												   -*/
    /*-			Class 			Area				   -*/
    /*-												   -*/

    /**
     * ************************************************
     */

    public class SeekBarOnGlobalLayoutListener implements OnGlobalLayoutListener {

        private ViewGroup parent;
        private View[] children;
        // private int scrollToViewIndex = 0;
        private SizeCallBack sizeCallBack;

        public SeekBarOnGlobalLayoutListener(ViewGroup parent, View[] children, SizeCallBack sizeCallBack) {

            this.parent = parent;
            this.children = children;
            this.sizeCallBack = sizeCallBack;
        }

        @Override
        public void onGlobalLayout() {
            // TODO Auto-generated method stub
            me.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            sizeCallBack.onGlobalLayout();
            parent.removeViewsInLayout(0, children.length);
            int width = me.getMeasuredWidth();
            int height = me.getMeasuredHeight();

            int[] dims = new int[2];
            scrollToViewPos = 0;

            for (int i = 0; i < children.length; i++) {
                sizeCallBack.getViewSize(i, width, height, dims);
                children[i].setVisibility(View.VISIBLE);

                parent.addView(children[i], dims[0], dims[1]);
                if (i == 0) {
                    scrollToViewPos += dims[0];
                }
            }
            // if(firstLoad){
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    me.scrollBy(scrollToViewPos, 0);

			/* 视图不是中间视图 */
                    me.setVisibility(View.VISIBLE);
                    homePage.setVisibility(View.VISIBLE);
                }
            });
            // }

        }
    }

}
