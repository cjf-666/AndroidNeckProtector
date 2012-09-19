package org.footoo.cjflsq.neck.settings;

import org.footoo.cjflsq.neck.R;
import org.footoo.cjflsq.neck.MainActivity;
import org.footoo.cjflsq.neck.MyApplication;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.SeekBar;
import android.content.Context;
import android.view.View;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.widget.TextView;

public class TimeSeekBarActivity extends Activity implements OnSeekBarChangeListener {

    @Override
    public void onCreate(Bundle b) {
	super.onCreate(b);
	mMinTime = 5;
	mMaxTime = 20;
	mDefaultTime = 5;

	timeCate = MyApplication.getAppContext().getString(R.string.pref_key_time);
	mSharedPreferences = MyApplication.getAppContext().getSharedPreferences(MyApplication.getAppContext().getString(R.string.settings_preferences_filename), Context.MODE_PRIVATE);
	mEditor = mSharedPreferences.edit();

	// Get current value from settings
	mCurrentTime = mSharedPreferences.getInt(timeCate, mDefaultTime);

	setContentView(R.layout.time_seekbar_scrollview);
	
	Button yesBtn = (Button) findViewById(R.id.time_yes_button);
	yesBtn.setOnClickListener(new yesOnClickListener());

	Button noBtn = (Button) findViewById(R.id.time_no_button);
	noBtn.setOnClickListener(new noOnClickListener());

	// Put minimum and maximum
	((TextView) findViewById(R.id.min_time)).setText(Integer.toString(mMinTime));
	((TextView) findViewById(R.id.max_time)).setText(Integer.toString(mMaxTime));

	// Setup SeekBar
	mSeekBar = (SeekBar) findViewById(R.id.time_interval_seekbar);
	mSeekBar.setMax(mMaxTime - mMinTime);
	mSeekBar.setProgress(mCurrentTime - mMinTime);
	mSeekBar.setOnSeekBarChangeListener(this);

	//Put current value
	mValueText = (TextView) findViewById(R.id.now_time);
	mValueText.setText(Integer.toString(mCurrentTime));
    }
    
    @Override
	public void onStopTrackingTouch(SeekBar s) {
    }
    @Override
	public void onStartTrackingTouch(SeekBar s) {
    } 
    
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
	mCurrentTime = progress + mMinTime;
	mValueText.setText(Integer.toString(mCurrentTime));
    }


    private class yesOnClickListener implements OnClickListener {
	@Override 
	public void onClick(View v) {
	    mEditor.putInt(timeCate, mCurrentTime);
	    mEditor.commit();
	    Intent intent = new Intent(TimeSeekBarActivity.this, MainActivity.class);
	    startActivity(intent);
	    TimeSeekBarActivity.this.finish();
	}
    }


      private class noOnClickListener implements OnClickListener {
	@Override 
	public void onClick(View v) {
	    Intent intent = new Intent(TimeSeekBarActivity.this, MainActivity.class);
	    startActivity(intent);
	    TimeSeekBarActivity.this.finish();
	}
    }

    private int mDefaultTime;
    private int mMinTime;
    private int mMaxTime;
    private int mCurrentTime;

    TextView mValueText;
    
    private SeekBar mSeekBar;

    private static final String PREFERENCE_NS = "http://schemas.android.com/apk/res/org.footoo.cjflsq.neck";
    private static final String ANDROID_NS = "http://schemas.android.com/apk/res/android";
    private static final String ATTR_DEFAULT_TIME = "defaultValue";
    private static final String ATTR_MIN_TIME = "minTime";
    private static final String ATTR_MAX_TIME = "maxTime";
    private static final int DEFAULT_MIN_TIME = 5;
    private static final int DEFAULT_MAX_TIME = 20;
    private static final int DEFAULT_CURRENT_TIME = 5;

    private SharedPreferences mSharedPreferences;	
    private Editor mEditor;
    private String timeCate;
}