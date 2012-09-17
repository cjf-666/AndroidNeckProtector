package org.footoo.cjflsq.neck.settings;

import org.footoo.cjflsq.neck.R;

import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.SeekBar;
import android.preference.DialogPreference;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.SeekBar;

public class TimeSeekBarPreference extends DialogPreference implements OnSeekBarChangeListener {
    public TimeSeekBarPreference (Context context, AttributeSet attrs) {
	super(context, attrs);
	mMinTime = attrs.getAttributeIntValue(PREFERENCE_NS, ATTR_MIN_TIME, DEFAULT_MIN_TIME);
	mMaxTime = attrs.getAttributeIntValue(PREFERENCE_NS, ATTR_MAX_TIME, DEFAULT_MAX_TIME);
	mDefaultTime = attrs.getAttributeIntValue(ANDROID_NS, ATTR_DEFAULT_TIME, DEFAULT_CURRENT_TIME);
    }

    @Override
    protected View onCreateDialogView() {

	// Get current value from settings
	mCurrentTime = getPersistedInt(mDefaultTime);

	// Inflate layout
	LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	View view = inflater.inflate(R.layout.time_seekbar_scrollview, null);

	// Put minimum and maximum
	//((TextView) view.findViewById(R.id.min_value)).setText(Integer.toString(mMinTime));
	//((TextView) view.findViewById(R.id.max_value)).setText(Integer.toString(mMaxTime));

	// Setup SeekBar
	mSeekBar = (SeekBar) view.findViewById(R.id.time_interval_seekbar);
	mSeekBar.setMax(mMaxTime - mMinTime);
	mSeekBar.setProgress(mCurrentTime - mMinTime);
	mSeekBar.setOnSeekBarChangeListener(this);

	// Put current value
	//mValueText = (TextView) view.findViewById(R.id.current_value);
	//mValueText.setText(Integer.toString(mCurrentTime));
	
	return view;
    }
    
    
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
	mCurrentTime = progress + mMinTime;
	//mValueText.setText(Integer.toString(mCurrentTime));
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    }
    
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
	super.onDialogClosed(positiveResult);

	if (!positiveResult) {
	    return;
	}
	if (shouldPersist()) {
	    persistInt(mCurrentTime);
	}
	
	notifyChanged();
    }

    private int mDefaultTime;
    private int mMinTime;
    private int mMaxTime;
    private int mCurrentTime;
    
    private SeekBar mSeekBar;

    private static final String PREFERENCE_NS = "http://schemas.android.com/apk/res/org.footoo.cjflsq.neck";
    private static final String ANDROID_NS = "http://schemas.android.com/apk/res/android";
    private static final String ATTR_DEFAULT_TIME = "defaultValue";
    private static final String ATTR_MIN_TIME = "minTime";
    private static final String ATTR_MAX_TIME = "maxTime";
    private static final int DEFAULT_MIN_TIME = 5;
    private static final int DEFAULT_MAX_TIME = 20;
    private static final int DEFAULT_CURRENT_TIME = 10;
}