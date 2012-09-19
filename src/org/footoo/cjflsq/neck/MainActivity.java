package org.footoo.cjflsq.neck;

import org.footoo.cjflsq.neck.R;
import org.footoo.cjflsq.neck.sns.SNSSupport;
import org.footoo.cjflsq.neck.sns.SNSShareActivity;
import org.footoo.cjflsq.neck.system.TimeService;
import org.footoo.cjflsq.neck.settings.TimeSettingScrollView;
import org.footoo.cjflsq.neck.settings.SizeCallBackForSetting;
import org.footoo.cjflsq.neck.settings.SizeCallBack;

import android.app.Activity;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ImageView;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;
import android.view.KeyEvent;
import android.graphics.Color;
import android.widget.LinearLayout;

import java.lang.Thread;
import java.lang.Integer;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
        setContentView(R.layout.activity_main);
	
	Button snsButton = (Button) findViewById(R.id.sns_share_button);
	snsButton.setOnClickListener(new SnsOnClickListener());

	Button knButton = (Button) findViewById(R.id.knowledge_button);
        knButton.setOnClickListener(new KNOnClickListener());

	/*mScrollView = (TimeSettingScrollView) findViewById(R.id.seekbar_scroll_view);
 
	  mSeekBar = getLayoutInflater().inflate(R.layout.time_seekbar_scrollview, null);
	  timeScrollButton = (Button) mSeekBar.findViewById(R.id.time_scroll_button);
	  timeScrollButton.setOnClickListener(new TimeOnClickListener());
		
	  View leftView = new View(this);
	  leftView.setBackgroundColor(Color.TRANSPARENT);
	  children = new View[]{leftView, mSeekBar};
	  mScrollView.initViews(children, new SizeCallBackForSetting(timeScrollButton), (LinearLayout) findViewById(R.id.home_page_view));
	  mScrollView.setTimeBtn(this.timeScrollButton);*/
	
	presentScore();
    }

    @Override
	public void onResume() {
	super.onResume();
	presentScore();
    }

    private void presentScore() {
	int mScore;
	ProgressBar mProgressBar;
	ImageView mImageView;
	mScore = getSharedPreferences(getString(R.string.score_filename).toString(), 0).getInt(getString(R.string.everyday_score).toString(), 99);
	
	mImageView = (ImageView) findViewById(R.id.score_image_1);
	setImageView(mImageView, mScore % 10);
	mScore /= 10;
	mImageView = (ImageView) findViewById(R.id.score_image_0);
	setImageView(mImageView, mScore % 10);
    }

    private void setImageView(ImageView imv, int num) {
	switch (num) {
	case 0:
	    imv.setImageResource(R.drawable.green_0);
	    break;
	case 1:
	    imv.setImageResource(R.drawable.green_1);
	    break;
	case 2:
	    imv.setImageResource(R.drawable.green_2);
	    break;
	case 3:
	    imv.setImageResource(R.drawable.green_3);
	    break;
	case 4:
	    imv.setImageResource(R.drawable.green_4);
	    break;
	case 5:
	    imv.setImageResource(R.drawable.green_5);
	    break;
	case 6:
	    imv.setImageResource(R.drawable.green_6);
	    break;
	case 7:
	    imv.setImageResource(R.drawable.green_7);
	    break;
	case 8:
	    imv.setImageResource(R.drawable.green_8);
	    break;
	case 9:
	    imv.setImageResource(R.drawable.green_9);
	    break;
	}
    }

    private class SnsOnClickListener implements OnClickListener {
	@Override 
	public void onClick(View v) {
	    Intent intent = new Intent(MainActivity.this, SNSShareActivity.class);
	    startActivity(intent);
	    MainActivity.this.finish();
	}
    }

    private class TimeOnClickListener implements OnClickListener {
	@Override
	public void onClick(View v) {
	}
    }

    private class KNOnClickListener implements OnClickListener {
	@Override
	public void onClick(View v) {
	    Intent intent = new Intent(MainActivity.this, KnowActivity.class);
	    startActivity(intent);
	    MainActivity.this.finish();
	}
    }

    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	if(keyCode == KeyEvent.KEYCODE_BACK){
	}
	return super.onKeyDown(keyCode, event);
    }	
}