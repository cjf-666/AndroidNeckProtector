package org.footoo.cjflsq.neck.settings;

import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.SeekBar;

public class TimeSeekBarPreference extends DialogPreference implements OnSeekBarChangeListener {
    public TimeSeekBarPreference (Context context, AttributeSet attrs) {
	super(context, attrs);
	mMinValue = attrs.getAttributeIntValue(PREFERENCE_NS, ATTR_MIN_VALUE, DEFAULT_MIN_VALUE);
	mMaxValue = attrs.getAttributeIntValue(PREFERENCE_NS, ATTR_MAX_VALUE, DEFAULT_MAX_VALUE);
	mDefaultValue = attrs.getAttributeIntValue(ANDROID_NS, ATTR_DEFAULT_VALUE, DEFAULT_CURRENT_VALUE);
    }

    @Override
    protected View onCreateDialogView() {

	// Get current value from settings
	mCurrentValue = getPersistedInt(mDefaultValue);

	// Inflate layout
	LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	View view = inflater.inflate(R.layout.time_interval_seekbar_layout, null);

	// Put minimum and maximum
	//((TextView) view.findViewById(R.id.min_value)).setText(Integer.toString(mMinValue));
	//((TextView) view.findViewById(R.id.max_value)).setText(Integer.toString(mMaxValue));

	// Setup SeekBar
	mSeekBar = (SeekBar) view.findViewById(R.id.time_interval_seekbar);
	mSeekBar.setMax(mMaxValue - mMinValue);
	mSeekBar.setProgress(mCurrentValue - mMinValue);
	mSeekBar.setOnSeekBarChangeListener(this);

	// Put current value
	//mValueText = (TextView) view.findViewById(R.id.current_value);
	//mValueText.setText(Integer.toString(mCurrentValue));
	
	return view;
    }
    
    
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
	mCurrentValue = value + mMinValue;
	//mValueText.setText(Integer.toString(mCurrentValue));
    }
    
    @Override
    protected void onDialogClosed(boolean positiveResult) {
	super.onDialogClosed(positiveResult);

	if (!positiveResult) {
	    return;
	}
	if (shouldPersist()) {
	    persistInt(mCurrentValue);
	}
	
	notifyChanged();
    }

    private int mDefaultValue;
    private int mMinValue;
    private int mMaxValue;
    private int mCurrentValue;

    private static final String PREFERENCE_NS = "http://schemas.android.com/apk/res/org.footoo.cjflsq.neck.settings.TimeSeekBarPreference";
    private static final String ANDROID_NS = "http://schemas.android.com/apk/res/android";
    private static final String ATTR_DEFAULT_VALUE = "defaultValue";
    private static final String ATTR_MIN_VALUE = "minValue";
    private static final String ATTR_MAX_VALUE = "maxValue";
}