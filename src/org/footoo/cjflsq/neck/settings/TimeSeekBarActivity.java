package org.footoo.cjflsq.neck.settings;

import org.footoo.cjflsq.neck.R;
import org.footoo.cjflsq.neck.MainActivity;
import org.footoo.cjflsq.neck.MyApplication;
import org.footoo.cjflsq.neck.system.TimeService;

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
import android.view.KeyEvent;

public class TimeSeekBarActivity extends Activity implements OnSeekBarChangeListener {

    @Override
	public void onCreate(Bundle b) {
	super.onCreate(b);
	mMinTime = 5;
	mMaxTime = 20;
	mDefaultTime = 5;

	timeCate = MyApplication.getAppContext().getString(R.string.pref_key_time);
	onCate = MyApplication.getAppContext().getString(R.string.pref_key_on);
	mSharedPreferences = MyApplication.getAppContext().getSharedPreferences(MyApplication.getAppContext().getString(R.string.settings_preferences_filename), Context.MODE_PRIVATE);
	mEditor = mSharedPreferences.edit();

	// Get current value from settings
	mCurrentTime = mSharedPreferences.getInt(timeCate, mDefaultTime);

	setContentView(R.layout.time_seekbar_scrollview);
	
	Button yesBtn = (Button) findViewById(R.id.time_yes_button);
	yesBtn.setOnClickListener(new YesOnClickListener());

	Button noBtn = (Button) findViewById(R.id.time_no_button);
	noBtn.setOnClickListener(new NoOnClickListener());

	offBtn = (Button) findViewById(R.id.noti_off_button);
	offBtn.setOnClickListener(new OffOnClickListener());
	
	int on = MyApplication.getAppContext().getSharedPreferences(getString(R.string.settings_preferences_filename), 0).getInt(getString(R.string.pref_key_on),0);
	if (on != 0) {
	    isOn = 0;
	    offBtn.setText("OFF");
	}
	else {
	    isOn = 1;
	    offBtn.setText("ON");
	}

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


    private class YesOnClickListener implements OnClickListener {
	@Override 
	    public void onClick(View v) {
	    mEditor.putInt(timeCate, mCurrentTime);
	    mEditor.commit();
	    mEditor.putInt(onCate, isOn);
	    mEditor.commit();
	    TimeSeekBarActivity.this.finish();
	}
    }


    private class NoOnClickListener implements OnClickListener {
	@Override 
	    public void onClick(View v) {
	    TimeSeekBarActivity.this.finish();
	}
    }

    private class OffOnClickListener implements OnClickListener {
	@Override 
	public void onClick(View v) {
	    if (isOn != 0) {
		offBtn.setText("OFF");
		isOn = 0;
	    }
	    else {
		offBtn.setText("ON");
		isOn = 1;
	    }
	}
    }

    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	if (keyCode == KeyEvent.KEYCODE_BACK) {
	    TimeSeekBarActivity.this.finish();
	}
	return super.onKeyDown(keyCode, event);
    }

    private int mDefaultTime;
    private int mMinTime;
    private int mMaxTime;
    private int mCurrentTime;

    TextView mValueText;
    
    private SeekBar mSeekBar;
    private SharedPreferences mSharedPreferences;	
    private Editor mEditor;
    private String timeCate;
    private String onCate;

    private int isOn;

    Button offBtn;
}