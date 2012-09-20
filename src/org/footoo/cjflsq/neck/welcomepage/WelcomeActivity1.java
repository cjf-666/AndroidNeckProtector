package org.footoo.cjflsq.neck.welcomepage;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import org.footoo.cjflsq.neck.R;

public class WelcomeActivity1 extends Activity{
    PopupWindow mPopupWindow;
    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.activity_main);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.4f;
        getWindow().setAttributes(lp);
        mImageView = (ImageView)LayoutInflater.from(this).inflate(R.layout.welcome_item1, null).findViewById(R.id.score_image_0);

        mImageView.post(new Runnable() {
            public void run() {
                final int[] location = new int[2];
                mImageView.getLocationOnScreen(location);
                //mPopupWindow = new PopupWindow(welcome_view[0], mImageView.getWidth() * 2, mImageView.getHeight() + 20);
                mPopupWindow = new PopupWindow(LayoutInflater.from(WelcomeActivity1.this).inflate(R.layout.welcome_item1, null), WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                mPopupWindow.showAtLocation(mImageView, Gravity.LEFT | Gravity.TOP, location[0], location[1]);
            }
        });
    }
}
